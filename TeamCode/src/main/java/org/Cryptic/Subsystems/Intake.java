package org.Cryptic.Subsystems;


import android.app.Notification;


import androidx.annotation.NonNull;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


import org.Cryptic.Subsystem;
import org.Cryptic.util.Globals;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;


import kotlin.OverloadResolutionByLambdaReturnType;


@Config
public class Intake extends Subsystem {


    public static double slidesTarget;


    public static double maxSlideSpeed;


    public static double pitchDown = 0.32;
    public static double pitchUp = 0.45;
    public static double pitchStowed = 0.32;
    public static double pitchTransfer = .40;
    public static double pitchPush = 0.32;


    private boolean isPrimed;


    public enum PitchState {
        DOWN, UP, STOWED, TRANSFER, PUSH
    }


    public Servo leftPitchServo;
    public Servo rightPitchServo;
    public Servo transferServo;
    public DcMotorEx intakeMotor;
    public ColorSensor color;
    public Servo LEDs;


    public enum States {
        PRIME_INTAKE,
        PRIME_OUTTAKE,
        POSITION_DOWN,
        MOVE_INTAKE,
        GRAB_SAMPLE,
        LIFT,
        MOVE_OUTTAKE
    }

    public static PitchState pitchState = PitchState.STOWED;
    public static boolean primed = false;

    public static Globals.SampleColor ALLIANCE_COLOR = Globals.SampleColor.UNKNOWN;

    public static States transferState = null;

    public static int inc = 0;

    public static boolean useLimitSwitch = false;

    @Override
    public void init(LinearOpMode opmode) throws InterruptedException {
        // TODO: Initialize all components
        // TODO: Wait a second for servos to initialize


        color = opmode.hardwareMap.get(ColorSensor.class, "color");


        intakeMotor = opmode.hardwareMap.get(DcMotorEx.class, "intakeMotor");
        transferServo = opmode.hardwareMap.get(Servo.class, "transferServo");
        leftPitchServo = opmode.hardwareMap.get(Servo.class, "leftPitchServo");
        rightPitchServo = opmode.hardwareMap.get(Servo.class, "rightPitchServo");


        LEDs = opmode.hardwareMap.get(Servo.class, "leds");


        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);


        pitchState = PitchState.STOWED;
        transferState = null;
        primed = false;
        update();

        inc = 0;


        setIntakePower(0);
    }
    public Globals.SampleColor getColor() {
        if (color.red() > 120 && color.green() > 120) {
            return Globals.SampleColor.YELLOW;
        }
        else if (color.red() > 120) {
            return Globals.SampleColor.RED;
        }
        else if (color.blue() > 100) {
            return Globals.SampleColor.BLUE;
        }
        //else if (color.red() < 30 && color.green() < 30 && color.blue() < 30){
        //    return Globals.SampleColor.UNKNOWN;
        //}
        else {
            return Globals.SampleColor.UNKNOWN;
        }
    }


    public void setIntakePitch(PitchState cool) {
        // TODO: Move the active intake servos based off pitch state
        switch (cool) {
            case DOWN:
                leftPitchServo.setPosition(pitchDown);
                rightPitchServo.setPosition(1-pitchDown);
                break;
            case UP:
                leftPitchServo.setPosition(pitchUp);
                rightPitchServo.setPosition(1-pitchUp);
                break;
            case STOWED:
                leftPitchServo.setPosition(pitchStowed);
                rightPitchServo.setPosition(1-pitchStowed);
                break;
            case TRANSFER:
                leftPitchServo.setPosition(pitchTransfer);
                rightPitchServo.setPosition(1-pitchTransfer);
                break;
            case PUSH:
                leftPitchServo.setPosition(pitchPush);
                rightPitchServo.setPosition(1-pitchPush);
                break;
        }
    }


    public void setPrimeIntake(boolean primed) {
        // TODO: Move servo to set up the intake for transfer to outtake
        if (primed) {
            transferServo.setPosition(0.35);
        } else {
            transferServo.setPosition(0.8);
        }
        isPrimed = primed;
    }


    public void setIntakePower(double power) {
        intakeMotor.setPower(power);
        if(power>.1){
            //pitchState = PitchState.DOWN;
            primed = false;
        }
    }


    public boolean getPrimeIntake(){
        return isPrimed;
    }


    public void update() {
        setIntakePitch(pitchState);
        setPrimeIntake(primed);
    }


    public void transferUpdate () {
        switch (transferState) {
            case PRIME_INTAKE:
                pitchState = PitchState.TRANSFER;
                primed = false;
                robot.intakeSlides.setSlidesTarget(270);
                robot.outtake.clawYaw = 2;
                robot.outtake.armAngle = 90;
                robot.outtake.clawAngle = 120;

                robot.verticalSlides.retractSlides();

                break;
            case PRIME_OUTTAKE:
                robot.outtake.fullRetract();
                primed = true;
                robot.outtake.gripperAngle = 0.6;
                robot.outtake.clawAngle = 90;
                robot.outtake.armAngle = 120;

                break;
            case POSITION_DOWN:
                robot.outtake.armAngle=135;
                robot.outtake.clawAngle = 85;
                break;
            case MOVE_INTAKE:
                if (useLimitSwitch) {
                    while (!robot.clawArm.clawLimitSwitch.getState()) {
                        robot.intakeSlides.slidesMotor.setPower(-0.5);
                    }
                    robot.intakeSlides.setSlidesTarget(robot.intakeSlides.slidesMotor.getCurrentPosition());
                } else {
                    robot.intakeSlides.setSlidesTarget(215);
                }
                break;
            case GRAB_SAMPLE:
                robot.clawArm.closeCLaw();
                break;
            case LIFT:
                robot.verticalSlides.slidesTarget=400;
                break;
            case MOVE_OUTTAKE:
                robot.outtake.clawAngle = 90;
                robot.outtake.clawYaw = 0;
                robot.outtake.armAngle = 30;
                primed = false;
                break;
        }
    }

    public void incTransferIntakeOuttakeState () {
        // Pretty jank but it converts a gamepad press into a State
        inc+=1;
        if(inc>6){
            inc = 1;
        }
        int count = 0;
        for (States i : States.values()) {
            count += 1;
            if (inc == count) {
                transferState = i;
                transferUpdate();
            }
        }
    }
}
