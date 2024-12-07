package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.Cryptic.Robot;

@Photon
@Config
@TeleOp (name = "MainTeleOp")
public class MainTeleOp extends LinearOpMode {

    private DcMotorEx slideLeft;
    private DcMotorEx slideRight;
    private DcMotorEx hangMotor;
    public double slowModeAdjust = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot();

        robot.initialize(this);

        GamepadEx drivePad = new GamepadEx(gamepad1);

        waitForStart();
        while (opModeIsActive()) {
            drivePad.readButtons();

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            double d = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            if (robot.slides.pos > 1800) {
                slowModeAdjust = 0.6;
            } else {
                slowModeAdjust = Range.clip(1 - drivePad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER), 0, 1);
            }

            robot.dt.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, slowModeAdjust);

            /*
            robot.dt.drivebase.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x
                    ),
                    -gamepad1.right_stick_x
            ));
            */

            //robot.dt.driveBL.setPower((y - x + rx) / d);
            //robot.dt.driveBR.setPower((y + x - rx) / d);
            //robot.dt.driveFL.setPower((y + x + rx) / d);
            //robot.dt.driveFR.setPower((y - x - rx) / d);

            // 2100 max slide value
            //robot.slides.setManualSlidePower(gamepad2.right_stick_y);

//            hangMotor.setPower(gamepad2.left_stick_y);
            if(drivePad.wasJustPressed(GamepadKeys.Button.A)) {
                robot.slides.incrementSlidePos(1);

            }


            if(drivePad.wasJustPressed(GamepadKeys.Button.B)) {

                if(robot.intake.extended){
                    robot.intake.fullRetract();
                }
                else{
                    robot.intake.fullExtend();
                }

            }





            if(drivePad.wasJustPressed(GamepadKeys.Button.X)) {
                robot.intake.defaultPosition();
            }

            if(drivePad.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)){
                robot.intake.armAngle=270;
                robot.intake.clawRotate=40;
            }
            if(drivePad.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)){

                    if(!robot.intake.clawOpened) {
                        robot.intake.openClaw();
                    }
                    else if(robot.intake.clawOpened){
                        robot.intake.closeCLaw();
                    }

            }

            if(drivePad.wasJustPressed(GamepadKeys.Button.RIGHT_STICK_BUTTON)){
                robot.intake.intakeSample();
            }

            if(drivePad.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)){
                robot.intake.spinCounter+=1;
            }
            if(drivePad.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)){
                robot.intake.spinCounter-=1;
            }

            telemetry.addData("Arm servo angle: ", robot.intake.getArmAngle());
            telemetry.update();


            robot.slides.update();
            robot.intake.update();


        }
    }
}