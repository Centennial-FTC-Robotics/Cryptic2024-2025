package org.Cryptic.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.Cryptic.Subsystem;

public class Slides extends Subsystem {

    public static double slideP = 0.005;
    public static double slideI = 0.0001;
    public static double slideD = 0;
    public static double slideF = 0.1;
    public static int errorThreshold = 5;
    public final int intakeArmThreshold = 900;
    public boolean canOuttake;
    public static double pivotHeight = 150;

    public static double pivotFlat = 0.42;
    public static double pivotUp = 0.73;

    public static double maxDownSpeed = 0.35;

    public int slidesTarget = 50;

    public int errorSum = 0;
    public int lastError = 0;
    public long lastTime = 0;

    public static int wheelOutDir = 1;

    public DcMotorEx slideMotorL;
    public DcMotorEx slideMotorR;

    public Servo arm;

    public CRServo wheel;

    public static int off = 0;
    public int[] targets = {50, 500-off, 1100-off, 1500-off, 2150-off};
    public double manualPower = 0;

    public int pos = -1;

    private LinearOpMode opmode;

    public void init(LinearOpMode opmode) throws InterruptedException {
        this.opmode = opmode;

        slideMotorL = opmode.hardwareMap.get(DcMotorEx.class, "slideLeft");
        slideMotorR = opmode.hardwareMap.get(DcMotorEx.class, "slideRight");

        slideMotorL.setDirection(DcMotorSimple.Direction.REVERSE);

        slideMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        slideMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        slideMotorR.setPower(0);
        slideMotorL.setPower(0);

        Thread.sleep(700);

        slideMotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slideMotorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void incrementSlidePos(int inc) {
        for(int i = 0; i < targets.length; i++) {
            if(targets[i] >= slidesTarget) {
                slidesTarget = targets[Range.clip((i+inc), 0, targets.length-1)];
                break;
            }
        }
    }

    public void retractSlides() {
        slidesTarget = 200;
    }

    public void setWheel(double power) {
        wheel.setPower(power);
    }

    public void setManualSlidePower(double power) {
        if(Math.abs(power) < 0.05 || (power < 0 && pos < targets[1])) {
            this.manualPower = 0;
            return;
        }
        this.manualPower = power;
    }


    public void update() {

        long t = System.currentTimeMillis();

        if(lastTime == 0) {
            lastTime = t-1;
        }



        pos = -slideMotorR.getCurrentPosition();
        double error = slidesTarget - pos;

        canOuttake= pos >= intakeArmThreshold;

        double speed = (double)(error-lastError)/(double)(t-lastTime);

        if(Math.abs(error) < errorThreshold) {
            errorSum = 0;
        } else {
            errorSum += error;
        }

        double power;
        if(Math.abs(manualPower) > 0.05) {
            power = Range.clip(manualPower + slideF, -1, 1);
            slidesTarget = pos;
        } else {
            power = Range.clip(error*slideP + errorSum*slideI + +speed*slideD + slideF,
                    -maxDownSpeed, 1);
        }

        slideMotorL.setPower(power);
        slideMotorR.setPower(power);

        lastTime = t;
    }

}