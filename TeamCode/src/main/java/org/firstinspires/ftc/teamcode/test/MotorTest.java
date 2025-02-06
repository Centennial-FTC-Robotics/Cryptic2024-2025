package org.firstinspires.ftc.teamcode.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp
public class MotorTest extends LinearOpMode {

    public DcMotorEx motor;
    public DcMotorEx motor2;

    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotorEx.class, "slideLeft");
        motor2 = hardwareMap.get(DcMotorEx.class, "slideRight");
        FtcDashboard dashboard = FtcDashboard.getInstance();
        motor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Motor Current", motor.getCurrent(CurrentUnit.AMPS));
            packet.put("Motor2 Current", motor2.getCurrent(CurrentUnit.AMPS));
            packet.put("Motor Position", motor.getCurrentPosition());
            packet.put("Gamepad", gamepad1.right_stick_y);
            packet.put("status", "alive");

            motor.setPower(gamepad1.right_stick_y);
            motor2.setPower(gamepad1.left_stick_y);

            dashboard.sendTelemetryPacket(packet);

        }
    }
}
