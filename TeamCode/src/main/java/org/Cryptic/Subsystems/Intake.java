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
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.Cryptic.Subsystem;
import org.Cryptic.util.Globals;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import kotlin.OverloadResolutionByLambdaReturnType;

@Config
public class Intake extends Subsystem {
    public static double P = 0;
    public static double I = 0;
    public static double D = 0;
    public static int targetErrorThreshold = 0;
    public static double velErrorThreshold = 0;

    public static double slidesTarget;

    public static double maxSlideSpeed;

    public static double pitchDown = 0.0;
    public static double pitchUp = 0.0;
    public static double pitchStowed = 0.0;

    private boolean isPrimed;
    private double sTarget;

    public enum PitchState {
        DOWN, UP, STOWED
    }

    public static int off = 0;
    public int[] targets = {0, 350-off, 420-off, 500-off, 580-off, 660-off, 740-off, 820-off,
            900-off, 980-off};
    public int maxSlidesValue = 980;

    public double manualPower = 0;

    // PID STUFF
    public static int pos;
    public static double power;

    public int errorSum = 0;
    public int lastError = 0;
    public long lastTime = 0;

    public static double slideP = 0.005;
    public static double slideI = 0.0001;
    public static double slideD = 0;
    public static double slideF = 0.1;
    public static int errorThreshold = 5;

    public Servo leftPitchServo;
    public Servo rightPitchServo;
    public Servo transferServo;
    public DcMotorEx intakeMotor;
    public DcMotorEx slidesMotor;
    public ColorSensor color;

    @Override
    public void init(LinearOpMode opmode) throws InterruptedException {
        // TODO: Initialize all components
        // TODO: Wait a second for servos to initialize

        color = opmode.hardwareMap.get(ColorSensor.class, "color");
        intakeMotor = opmode.hardwareMap.get(DcMotorEx.class, "intakeMotor");
        slidesMotor = opmode.hardwareMap.get(DcMotorEx.class, "slidesMotor");
        transferServo = opmode.hardwareMap.get(Servo.class, "transferServo");
        leftPitchServo = opmode.hardwareMap.get(Servo.class, "leftPitchServo");
        rightPitchServo = opmode.hardwareMap.get(Servo.class, "rightPitchServo");

        while(slidesMotor.getCurrent(CurrentUnit.AMPS) < 3 && opmode.opModeInInit()) {
            slidesMotor.setPower(-0.4);
        }

        slidesMotor.setPower(0);

        Thread.sleep(700);

        slidesMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slidesMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        setIntakePitch(PitchState.STOWED);
        setPrimeIntake(false);
        setIntakePower(0);
        setSlidesTarget(0);
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
                rightPitchServo.setPosition(pitchDown);
                break;
            case UP:
                leftPitchServo.setPosition(pitchUp);
                rightPitchServo.setPosition(pitchUp);
                break;
            case STOWED:
                leftPitchServo.setPosition(pitchStowed);
                rightPitchServo.setPosition(pitchStowed);
        }
    }

    public void setPrimeIntake(boolean primed) {
        // TODO: Move servo to set up the intake for transfer to outtake
        if (primed) {
            transferServo.setPosition(0.55);
        } else {
            transferServo.setPosition(0.8);
        }
        isPrimed = primed;
    }

    public void setIntakePower(double power) {
        intakeMotor.setPower(power);
    }

    public void setSlidesTarget (double value) {
        // TODO: Convert value (1.0 to 0.0) to encoder ticks for slides, and set that to PID target
        sTarget = (int)(980 * value);
    }

    public void incrementSlidesTarget(double value) {
        sTarget += value;
    }

    public void retractSlides() {
        sTarget = 10;
    }

    public void update() {
        // TODO: Use PID to move slides and obey max speed
        long t = System.currentTimeMillis();

        if(lastTime == 0) {
            lastTime = t-1;
        }


        pos = -slidesMotor.getCurrentPosition();
        double error = sTarget - pos;

        double speed = (double)(error-lastError)/(double)(t-lastTime);

        if(Math.abs(error) < errorThreshold) {
            errorSum = 0;
        } else {
            errorSum += error;
        }

        Telemetry tel =  FtcDashboard.getInstance().getTelemetry();
        tel.addData("target", sTarget);
        tel.addData("pos", pos);
        tel.update();

        if(Math.abs(manualPower) > 0.05) {
            power = Range.clip(manualPower + slideF, -1, 1);
            sTarget = pos;
        } else {
            power = Range.clip(error*slideP + errorSum*slideI + +speed*slideD + slideF,
                    -1, 1);
        }

        slidesMotor.setPower(power);

        lastTime = t;
    }

    public boolean areSlidesFinished() {
        return (Math.abs(pos - slidesTarget) < errorThreshold);
    }

    public boolean getPrimeIntake(){
        return isPrimed;
    }

    public double getSlidesTarget(){
        return sTarget;
    }

    public void setManualSlidePower(double power) {
        if(Math.abs(power) < 0.05 || (power < 0 && pos < targets[1])) {
            this.manualPower = 0;
            return;
        }
        this.manualPower = power;
    }

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
            setSlidesTarget(980);
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

}

