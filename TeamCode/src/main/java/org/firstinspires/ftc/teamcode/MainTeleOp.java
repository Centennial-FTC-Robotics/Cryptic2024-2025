package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
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

import org.Cryptic.Robot;

@Config
@TeleOp (name = "MainTeleOp")
public class MainTeleOp extends LinearOpMode {

    private DcMotorEx slideLeft;
    private DcMotorEx slideRight;
    private DcMotorEx hangMotor;

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot();

        robot.initialize(this);

        GamepadEx drivePad = new GamepadEx(gamepad1);
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
            drivePad.readButtons();

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            double d = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            robot.dt.drivebase.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x
                    ),
                    -gamepad1.right_stick_x
            ));

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
                robot.slides.retractSlides();
            }

            if(drivePad.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)){
                robot.intake.armAngle=268;
            }
            if(drivePad.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)){

                    if(!robot.intake.clawOpened) {
                        robot.intake.openClaw();
                    }
                    else if(robot.intake.clawOpened){
                        robot.intake.closeCLaw();
                    }

            }


            robot.slides.update();
            robot.intake.update();


        }
    }
}