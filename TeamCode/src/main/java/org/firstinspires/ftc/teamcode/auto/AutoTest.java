package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.Cryptic.Robot;

@Autonomous
public class AutoTest extends LinearOpMode {

    public DcMotorEx driveBR;
    public DcMotorEx driveBL;
    public DcMotorEx driveFR;
    public DcMotorEx driveFL;

    public void runOpMode() throws InterruptedException {
        driveBL = hardwareMap.get(DcMotorEx.class, "leftBack");
        driveBR = hardwareMap.get(DcMotorEx.class, "rightBack");
        driveFL = hardwareMap.get(DcMotorEx.class, "leftFront");
        driveFR = hardwareMap.get(DcMotorEx.class, "rightFront");

        driveBL.setDirection(DcMotorSimple.Direction.REVERSE);

        driveBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        driveBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Robot robot = new Robot();
        robot.initialize(this);


        waitForStart();

        long cool = System.currentTimeMillis();
        while((System.currentTimeMillis()-cool) < 2000){
            driveBL.setPower(1);
            driveBR.setPower(1);
            driveFL.setPower(1);
            driveFR.setPower(1);
        }
    }
}
