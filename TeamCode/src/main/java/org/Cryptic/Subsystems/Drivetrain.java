package org.Cryptic.Subsystems;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.Cryptic.Subsystem;
import org.firstinspires.ftc.teamcode.MecanumDrive;

public class Drivetrain extends Subsystem {

    private DcMotorEx driveBL;
    private DcMotorEx driveBR;
    private DcMotorEx driveFL;
    private DcMotorEx driveFR;

    public MecanumDrive drivebase;

    public LinearOpMode opmode;

    // Dead Wheel Variables
    double wheelDiameter = 38;
    int countsPerRev = 8192;
    double wheelCircum = Math.PI * (wheelDiameter / 25.4);
    double ticksOverIn = 8192 / wheelCircum;

    public void init(LinearOpMode opMode) {
        drivebase = new MecanumDrive(opMode.hardwareMap, new Pose2d(0, 0, 0));
    }

    public void drive(double drive, double strafe, double turn, double speedMult) {
        robot.dt.drivebase.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        -(drive * speedMult),
                        -(strafe * speedMult)
                ),
                -(turn * speedMult)
        ));
    }

    public void initNoRoadRunner(LinearOpMode opMode) {
        this.opmode = opMode;
        driveBL = opMode.hardwareMap.get(DcMotorEx.class, "leftBack");
        driveBR = opMode.hardwareMap.get(DcMotorEx.class, "rightBack");
        driveFL = opMode.hardwareMap.get(DcMotorEx.class, "leftFront");
        driveFR = opMode.hardwareMap.get(DcMotorEx.class, "rightFront");

        driveBL.setDirection(DcMotorSimple.Direction.REVERSE);

        driveBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        driveBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // par0: rightFront, negative
        // par1: rightBack, negative
        //perp: leftBack, negative
        // left is positive
    }

    public double ticksToInches(int ticks) {
        return (ticks / ticksOverIn);
    }

    public int inchesToTicks(double inches) {
        return (int) Math.round(inches * ticksOverIn);
    }

    public void driveDistance(double distanceInch) {

        long startTime = System.currentTimeMillis();
        long timeout = (long)(4000*Math.abs(distanceInch)/24.);

        resetEncoders();

        double driveP = 0.0001;
//        double headingP = 0.3;


        int targetDist = inchesToTicks(distanceInch);

        int currentPos = getEncoderTicks()[1];
        int error = targetDist - currentPos;

        long lastTime = System.currentTimeMillis()-1;
        int lastPos = currentPos;

        while(opmode.opModeIsActive() && (Math.abs(error) > 500 ||
                Math.abs((currentPos-lastPos)/(System.currentTimeMillis()-lastTime)) > 0.00001)
                && System.currentTimeMillis()-startTime < timeout) {

            lastTime = System.currentTimeMillis();
            lastPos = currentPos;

            currentPos = getEncoderTicks()[1];
            error = targetDist - currentPos;

            double drivePower = Range.clip(error*driveP, -.4, .4);
/*
            double currentAngle = robot.imu.revIMU.getHeading();
            double turnError = currentAngle - targetHeading;

            double turnPower = Range.clip(turnError*headingP, -headingLimit, headingLimit);

            opmode.telemetry.addData("Mode", "STRAIGHT");
            opmode.telemetry.addData("TargetHeading", targetHeading);
            opmode.telemetry.addData("CurrrentHeading", currentAngle);
            opmode.telemetry.addData("TargetPos", targetDist);
            opmode.telemetry.addData("CurrentPos", currentPos);
            opmode.telemetry.update();

            drive(drivePower, 0, turnPower, false);
            */
        }

    }

    public void resetEncoders() {
        driveBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public int[] getEncoderTicks() {
        return new int[]{
                -driveFR.getCurrentPosition(),
                -driveBR.getCurrentPosition(),
                -driveBL.getCurrentPosition()
        };
    }
}
