package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.Cryptic.Robot;
import org.Cryptic.util.Globals;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

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

        robot.dt.init(this);

        GamepadEx drivePad = new GamepadEx(gamepad1);
        GamepadEx intakePad = new GamepadEx(gamepad2);

        //robot.dt.initTeleOp();

        //jfyfyf

        /*
        slideLeft = hardwareMap.get(DcMotorEx.class, "slideLeft");
        slideRight = hardwareMap.get(DcMotorEx.class, "slideRight");
        */
//        hangMotor = hardwareMap.get(DcMotorEx.class, "hangMotor");

        /*
        slideLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        */


        /*
        slideLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        */
//        hangMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //slideRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();
            drivePad.readButtons();
            intakePad.readButtons();

            robot.dt.drivebase.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x
                    ),
                    -gamepad1.right_stick_x
            ));



            /*
            if (robot.intake.getColor() == Globals.SampleColor.BLUE) {
                telemetry.addData("Color", "BLUE");
            }
            if (robot.intake.getColor() == Globals.SampleColor.RED) {
                telemetry.addData("Color", "RED");
            }
            if (robot.intake.getColor() == Globals.SampleColor.YELLOW) {
                telemetry.addData("Color", "YELLOW");
            }

            robot.intake.intakeMotor.setPower(intakePad.getLeftY());
            */
            //robot.intake.setSlidesPower(intakePad.getRightY());
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
    }
}