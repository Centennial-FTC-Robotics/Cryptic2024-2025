package org.firstinspires.ftc.teamcode.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp
public class ServoTest extends LinearOpMode {

    public Servo cool;
    public static String hardwareName = "leftClawServo";
    public static double pos = 0.0;

    @Override
    public void runOpMode() {
        cool = hardwareMap.get(Servo.class, "leftClawServo");
        FtcDashboard dashboard = FtcDashboard.getInstance();

        waitForStart();

        while (opModeIsActive()) {

            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Servo Position", cool.getPosition());
            packet.put("Hardware name", hardwareName);
            packet.put("pos", pos);
            packet.put("status", "alive");

            cool.setPosition(pos);

            dashboard.sendTelemetryPacket(packet);

        }
    }
}