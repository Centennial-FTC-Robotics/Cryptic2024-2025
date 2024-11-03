package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.Cryptic.Robot;

@TeleOp (name = "SampleTeleOp")
public class SampleTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot();

        robot.initialize(this);
        robot.dt.initTeleOp();

        waitForStart();
        while(opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            double d = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            robot.dt.driveBL.setPower((y - x + rx) / d);
            robot.dt.driveBR.setPower((y + x - rx) / d);
            robot.dt.driveFL.setPower((y + x + rx) / d);
            robot.dt.driveFR.setPower((y - x - rx) / d);
        }
    }
}