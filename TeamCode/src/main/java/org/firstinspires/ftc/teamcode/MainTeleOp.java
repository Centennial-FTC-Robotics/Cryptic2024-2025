package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.Cryptic.Robot;
import org.Cryptic.Subsystems.Intake;

@Config
@TeleOp (name = "MainTeleOp")
public class MainTeleOp extends LinearOpMode {

    public static double ledColor;

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot();
        robot.initialize(this);

        GamepadEx drivePad = new GamepadEx(gamepad1);
        GamepadEx intakePad = new GamepadEx(gamepad2);

        ToggleButtonReader bReader = new ToggleButtonReader(
                drivePad, GamepadKeys.Button.B
        );

        ToggleButtonReader bIntakeReader = new ToggleButtonReader(
                intakePad, GamepadKeys.Button.B
        );

        double slowMode = 0.0;

        FtcDashboard dashboard = FtcDashboard.getInstance();

        waitForStart();
        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();

            drivePad.readButtons();
            intakePad.readButtons();
            bReader.readValue();
            bIntakeReader.readValue();

            robot.intake.update();
            robot.intakeSlides.update();
            robot.outtake.update();
            robot.verticalSlides.update();
            // robot.outtake.update?

            robot.intake.LEDs.setPosition(ledColor);

            if (gamepad1.right_trigger >= 0.05) {
                slowMode = Range.clip((1.0 - gamepad1.right_trigger),0, 1);
            } else {
                slowMode = 1.0;
            }

            robot.dt.drivebase.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y * slowMode,
                            -gamepad1.left_stick_x * slowMode
                    ),
                    -gamepad1.right_stick_x * slowMode
            ));

            packet.put("Color: ", robot.intake.getColor());
            packet.put("SPECIMEN STATE: ", robot.specimenCommands.getSpecimenState());

            // Extend Slides
            if (drivePad.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
                robot.verticalSlides.incrementSlidePos(1);
            }

            // Retract Slides
            if (drivePad.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
                robot.verticalSlides.retractSlides();
            }

            // Outtake Forward


            // Outtake Back


            // Position Outtake down for transfer
            if (drivePad.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)) {
                // Need to change Axon positioning to 0 to 270
            }

            if (intakePad.wasJustPressed(GamepadKeys.Button.Y)) {
                robot.specimenCommands.specimenUpdate();
            }

            if(intakePad.wasJustPressed(GamepadKeys.Button.B)){
                robot.outtake.intakeClawSample();
            }

            if(drivePad.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)){
                robot.outtake.outtakeSample();
            }

            if(drivePad.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
                robot.intake.incTransferIntakeOuttakeState();
            }


            // GAMEPAD 2

            // Move Horizontal Slides Manually
            robot.intakeSlides.setManualSlidePower(intakePad.getRightY());

            // Increment Horizontal Slides
            if (intakePad.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
                robot.intakeSlides.incrementSlidePos(1);
            }

            // Retract Horizontal Slides
            if (intakePad.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
                robot.intakeSlides.retractSlides();

            }


            // Set Up Intake for Transfer
            //robot.intake.setPrimeIntake(bIntakeReader.getState());

            // Move Intake Rollers
            double intakePower = intakePad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - (intakePad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)*0.4);
            if (robot.intake.inc < 5 && robot.intake.inc != 0) { // this is kinda retarded but whatever
                robot.intake.pitchState = Intake.PitchState.TRANSFER;
            } else if (robot.intakeSlides.slidesMotor.getCurrentPosition() <= 100){
                robot.intake.pitchState = Intake.PitchState.STOWED;
            } else if (intakePower > 0.1) {
                robot.intake.pitchState = Intake.PitchState.DOWN;
            } else {
                robot.intake.pitchState = Intake.PitchState.UP;
            }

            robot.intake.setIntakePower(intakePower);

            if(intakePad.wasJustPressed(GamepadKeys.Button.A)){
                if(robot.outtake.intakeClawSampleState ==2){
                    robot.outtake.currentActionSequence=  "Intake Claw Sample";
                    robot.outtake.intakeClawSampleSequenceState = 0;
                }
            }
            // Spin Claw
            if (intakePad.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
                //robot.intake.gamepadToTransferIntakeOuttake();
                robot.outtake.clawSpinLeft();
            }
            if (intakePad.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
                //robot.intake.gamepadToTransferIntakeOuttake();
                robot.outtake.clawSpinRight();
            }

            if (intakePad.wasJustPressed(GamepadKeys.Button.X)) {
                if (robot.clawArm.clawOpened == true) {
                    robot.clawArm.closeCLaw();
                } else if (robot.clawArm.clawOpened == false) {
                    robot.clawArm.openClaw();
                }
            }

            dashboard.sendTelemetryPacket(packet);

        }
    }
}

/*
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            double d = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            if (robot.slides.pos > 1800) {
                slowModeAdjust = 0.55;
            } else {
                slowModeAdjust = Range.clip(1 - drivePad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER), 0.3, 1);
            }


            // debug
            telemetry.addData("Slow Mode Value: ", slowModeAdjust);

            robot.dt.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, slowModeAdjust);


            //robot.dt.drivebase.setDrivePowers(new PoseVelocity2d(
            //        new Vector2d(
            //                -gamepad1.left_stick_y,
            //                -gamepad1.left_stick_x
            //        ),
            //        -gamepad1.right_stick_x
            //));


            //robot.dt.driveBL.setPower((y - x + rx) / d);
            //robot.dt.driveBR.setPower((y + x - rx) / d);
            //robot.dt.driveFL.setPower((y + x + rx) / d);
            //robot.dt.driveFR.setPower((y - x - rx) / d);

            // 2100 max slide value
            //robot.slides.setManualSlidePower(gamepad2.right_stick_y);

//            hangMotor.setPower(gamepad2.left_stick_y);
            if(intakePad.wasJustPressed(GamepadKeys.Button.A)) {
                robot.slides.incrementSlidePos(1);

            }


            if(intakePad.wasJustPressed(GamepadKeys.Button.B)) {

                if(robot.intake.extended){
                    robot.intake.fullRetract();
                }
                else{
                    robot.intake.fullExtend();
                }

            }



            gamepad1.x;
            intakePad.wasJustPressed(GamepadKeys.Button.X);


            if(intakePad.wasJustPressed(GamepadKeys.Button.X)) {
                robot.intake.defaultPosition();
            }

            if(intakePad.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)){
                robot.intake.armAngle=270;

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


            robot.slides.update();
            robot.intake.update();

            telemetry.update();

        }
        */