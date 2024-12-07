package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.Cryptic.Subsystems.Drivetrain;
import org.Cryptic.Subsystems.IMU;

@TeleOp(name="debug")
public class debug extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        //Drivetrain dt = new Drivetrain();
        IMU imu = new IMU();

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("First Angle", imu.revIMU.getAngles()[0]);
            telemetry.addData("First Angle", imu.revIMU.getAngles()[1]);
            telemetry.addData("First Angle", imu.revIMU.getAngles()[2]);

            //telemetry.addData("Ticks", dt.inchesToTicks(24));
            telemetry.update();
        }
    }
}
