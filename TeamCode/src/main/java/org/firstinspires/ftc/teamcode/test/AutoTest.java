package org.firstinspires.ftc.teamcode.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.Cryptic.Robot;
import org.Cryptic.Subsystems.DrivetrainNoRR;

@Config
@TeleOp
public class AutoTest extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        Robot robot = new Robot();
        robot.initialize(this);

        waitForStart();

        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("FrontLeft", robot.dtNoRR.driveFL.getCurrentPosition());
            /*
            packet.put("FrontRight", dt.driveFR.getCurrentPosition());
            packet.put("BackLeft", dt.driveBL.getCurrentPosition());
            packet.put("BackRight", dt.driveBR.getCurrentPosition());
*/
            dashboard.sendTelemetryPacket(packet);
        }
    }
}
