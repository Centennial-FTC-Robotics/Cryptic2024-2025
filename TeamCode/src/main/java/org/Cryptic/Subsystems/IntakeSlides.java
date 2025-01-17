package org.Cryptic.Subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.Cryptic.Subsystem;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Config
public class IntakeSlides extends Subsystem {

    public static double sTarget;

    public static int off = 0;
    public int maxSlideValue = 610;
    public int[] targets = {10, 150-off, 300-off, 450-off, maxSlideValue-off};

    public static int pos;
    public static double power;
    public double manualPower = 0;

    public int errorSum = 0;
    public int lastError = 0;
    public long lastTime = 0;

    public static double maxBackSpeed = 0.8;
    public static double maxForwardSpeed = 1.0;

    public static double slideP = 0.005;
    public static double slideI = 0.0;
    public static double slideD = 0;
    public static double slideF = 0.0;
    public static int errorThreshold = 5;

    public DcMotorEx slidesMotor;

    public void init(LinearOpMode opmode) throws InterruptedException {
        slidesMotor = opmode.hardwareMap.get(DcMotorEx.class, "slidesMotor");

        slidesMotor.setDirection(DcMotorSimple.Direction.FORWARD);


        while(slidesMotor.getCurrent(CurrentUnit.AMPS) < 3 && opmode.opModeInInit()) {
            slidesMotor.setPower(-0.4);
        }

        slidesMotor.setPower(0);

        Thread.sleep(700);

        slidesMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slidesMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void setSlidesTarget(int target) {
        sTarget = Range.clip(target, 0, maxSlideValue);
    }

    public void incrementSlidePos(int inc) {
        for(int i = 0; i < targets.length; i++) {
            if(targets[i] >= sTarget) {
                sTarget = targets[Range.clip((i+inc), 0, targets.length-1)];
                break;
            }
        }
    }

    public void retractSlides() {
        sTarget = 10;
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

    public boolean areSlidesFinished() {
        return (Math.abs(pos - sTarget) < errorThreshold);
    }

    public void update() {
        // TODO: Use PID to move slides and obey max speed
        long t = System.currentTimeMillis();

        if(lastTime == 0) {
            lastTime = t-1;
        }

        pos = slidesMotor.getCurrentPosition();
        double error = sTarget - pos;

        double speed = (double)(error-lastError)/(double)(t-lastTime);

        if(Math.abs(error) < errorThreshold) {
            errorSum = 0;
        } else {
            errorSum += error;
        }

        Telemetry tel =  FtcDashboard.getInstance().getTelemetry();
        tel.addData("Horizontal Slides Target", sTarget);
        tel.addData("Horizontal Slides Pos", pos);
        tel.update();

        if(Math.abs(manualPower) > 0.05) {
            power = Range.clip(manualPower, -0.5, 0.5);
            sTarget = pos;
        } else {
            power = Range.clip(error*slideP + errorSum*slideI + speed*slideD + slideF,
                    -maxBackSpeed, maxForwardSpeed);
        }

        slidesMotor.setPower(power);

        lastTime = t;

    }

}
