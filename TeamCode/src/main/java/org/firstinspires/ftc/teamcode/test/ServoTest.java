package org.firstinspires.ftc.teamcode.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Config
@TeleOp
public class ServoTest extends LinearOpMode {

    public static String hardwareName = "transferServo";
    public static double servoPos = 0.0;
    public Servo servo;

    @Override
    public void runOpMode() {
        servo = hardwareMap.get(Servo.class, hardwareName);
        FtcDashboard dashboard = FtcDashboard.getInstance();

        waitForStart();

        while (opModeIsActive()) {

            TelemetryPacket packet = new TelemetryPacket();
            packet.put("status", "alive");

            servo.setPosition(servoPos);

            dashboard.sendTelemetryPacket(packet);

        }
    }
}
