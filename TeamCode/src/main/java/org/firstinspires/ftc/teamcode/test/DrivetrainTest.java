package org.firstinspires.ftc.teamcode.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.Cryptic.Robot;
import org.Cryptic.Subsystems.DrivetrainNoRR;

@Config
@TeleOp
public class DrivetrainTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        Robot robot = new Robot();
        robot.initialize(this);
        DrivetrainNoRR dt = new DrivetrainNoRR();
        dt.init(this);

        waitForStart();

        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("FrontLeft", dt.driveFL.getCurrentPosition());
            packet.put("FrontRight", dt.driveFR.getCurrentPosition());
            packet.put("BackLeft", dt.driveBL.getCurrentPosition());
            packet.put("BackRight", dt.driveBR.getCurrentPosition());

            dt.drive(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x);

            dashboard.sendTelemetryPacket(packet);
        }
    }
}
