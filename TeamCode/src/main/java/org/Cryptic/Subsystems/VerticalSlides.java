package org.Cryptic.Subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.Cryptic.Subsystem;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Config
public class VerticalSlides extends Subsystem {

    public static double slideP = 0.0003;
    public static double slideI = 0.0001;
    public static double slideD = 0.1;
    public static double slideF = 0.1;
    public static int errorThreshold = 5;
    public final int intakeArmThreshold = 900;
    public boolean canOuttake;
    public static double pivotHeight = 150;

    public static double pivotFlat = 0.42;
    public static double pivotUp = 0.73;

    public static double maxDownSpeed = 0.35;

    public int slidesTarget = 10;

    public int errorSum = 0;
    public int lastError = 0;
    public long lastTime = 0;

    public static int wheelOutDir = 1;

    public DcMotorEx slideMotorL;
    public DcMotorEx slideMotorR;

    public Servo arm;

    public CRServo wheel;

    public static int off = 0;

    public int[] targets = {10, 175, 500-off, 1100-off, 1500-off, 2155-off};
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


        while(slideMotorR.getCurrent(CurrentUnit.AMPS) < 3 && opmode.opModeInInit()) {
            slideMotorL.setPower(-0.4);
            slideMotorR.setPower(-0.4);
        }

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
        slidesTarget = 10;
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



        pos = slideMotorR.getCurrentPosition();
        double error = slidesTarget - pos;

        Telemetry tel =  FtcDashboard.getInstance().getTelemetry();
        tel.addData("Vertical Slides Target", slidesTarget);
        tel.addData("Vertical Slides Pos", pos);
        tel.update();

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

    public class VerticalSlidesUpdate implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            update();
            return true;
        }
    }
}