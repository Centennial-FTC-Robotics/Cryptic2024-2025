package org.firstinspires.ftc.teamcode.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.Cryptic.Robot;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Config
@TeleOp
public class IntakeTest extends LinearOpMode {

    public static String hardwareName = "leftPitchServo";
    public static double pos = 0.0;
    public static double testPos = 0.0;
    public Servo testServo;

    public static int clawPitch = 0;

    @Override
    public void runOpMode() throws InterruptedException{
        FtcDashboard dashboard = FtcDashboard.getInstance();

        Robot robot = new Robot();
        robot.intake.init(this);
        GamepadEx drivePad = new GamepadEx(gamepad1);
        ToggleButtonReader bReader = new ToggleButtonReader(
                drivePad, GamepadKeys.Button.B
        );

        testServo = hardwareMap.get(Servo.class, hardwareName);

        waitForStart();

        while (opModeIsActive()) {
            drivePad.readButtons();
            bReader.readValue();

            TelemetryPacket packet = new TelemetryPacket();
            packet.put("pos", pos);
            packet.put("Left Motor Current", robot.verticalSlides.slideMotorL.getCurrent(CurrentUnit.AMPS));
            packet.put("Right Motor Current", robot.verticalSlides.slideMotorR.getCurrent(CurrentUnit.AMPS));
            //packet.put("Gamepad Button B", bReader.getState());
            packet.put("status", "alive");

            //robot.outtake.clawServo.setPosition(pos);

            testServo.setPosition(testPos);


            //robot.intake.setIntakePitch();

            if (drivePad.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
                robot.verticalSlides.incrementSlidePos(1);
            }

            if (drivePad.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
                robot.verticalSlides.retractSlides();
            }

            //robot.outtake.setClawPitch(clawPitch);
            dashboard.sendTelemetryPacket(packet);

        }
    }
}