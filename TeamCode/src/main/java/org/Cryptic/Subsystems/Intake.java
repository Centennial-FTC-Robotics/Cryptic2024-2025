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

    public static double pitchDown = 0.5;
    public static double pitchUp = 0.7;
    public static double pitchStowed = 0.7;
    public static double pitchTransfer = .6;

    private boolean isPrimed;

    public enum PitchState {
        DOWN, UP, STOWED, TRANSFER
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
        POSITION_INTAKE,
        GRAB_SAMPLE,
        OUTTAKE_UP,
        MOVE_OUTTAKE
    }


    public static PitchState pitchState = PitchState.UP;
    public static boolean primed = false;

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

        setIntakePitch(PitchState.UP);
        setPrimeIntake(false);
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
                leftPitchServo.setPosition(pitchUp);
                rightPitchServo.setPosition(1-pitchUp);
                break;
        }
    }

    public void setPrimeIntake(boolean primed) {
        // TODO: Move servo to set up the intake for transfer to outtake
        if (primed) {
            transferServo.setPosition(0.5);
        } else {
            transferServo.setPosition(0.7);
        }
        isPrimed = primed;
    }

    public void setIntakePower(double power) {
        intakeMotor.setPower(power);
        if(power>.1){
            pitchState = PitchState.DOWN;
            primed = false;

        }else{
            pitchState = pitchState.UP;

        }
    }

    public boolean getPrimeIntake(){
        return isPrimed;
    }

    public void update() {
        setIntakePitch(pitchState);
        setPrimeIntake(primed);
    }


    public void transferIntakeOuttake (States cool) {
        switch (cool) {
            case PRIME_INTAKE:
                pitchState = PitchState.UP;
                primed = false;
                robot.intakeSlides.setSlidesTarget(300);

                break;
            case PRIME_OUTTAKE:
                robot.outtake.fullRetract();
                primed = true;
                robot.outtake.gripperAngle = 0.87;
                robot.outtake.clawAngle = 84;
                robot.outtake.armAngle = 140;
                robot.outtake.clawYaw = -2;
                robot.outtake.claw.openClaw();
                robot.verticalSlides.retractSlides();
                break;
            case POSITION_INTAKE:
                robot.intakeSlides.setSlidesTarget(185);

                break;
            case GRAB_SAMPLE:
                robot.outtake.armAngle+=6;
                robot.clawArm.closeCLaw();
                break;
            case OUTTAKE_UP:
                robot.verticalSlides.slidesTarget+=400;
                robot.intakeSlides.setSlidesTarget(205);
                transferServo.setPosition(.6);

                break;
            case MOVE_OUTTAKE:
                robot.outtake.clawAngle = 90;
                robot.outtake.clawYaw = 0;
                robot.outtake.armAngle = 30;
                primed = false;
                break;
        }
    }

    public static int sampleState = 0;

    public void gamepadToTransferIntakeOuttake () {
        // Pretty jank but it converts a gamepad press into a State
        sampleState+=1;
        if(sampleState>6){
            sampleState = 1;
        }
        int count = 0;
        for (States i : States.values()) {
            count += 1;
            if (sampleState == count) {
                transferIntakeOuttake(i);
            }
        }
    }
    /*
    public class IntakeSlidesUpdate implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            update();
            return true;
        }
    }

    public class ExtendIntakeToPosition implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            setIntakePitch(PitchState.DOWN);
            setSlidesTarget(610);
            return !areSlidesFinished();
        }
    }

    public class RetractIntake implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            setSlidesTarget(20);
            setIntakePitch(PitchState.STOWED);
            return !areSlidesFinished();
        }
    }
     */

}

