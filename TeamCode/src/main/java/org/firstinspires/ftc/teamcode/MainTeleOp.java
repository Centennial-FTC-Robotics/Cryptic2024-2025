package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.Cryptic.Robot;

@TeleOp (name = "MainTeleOp")
public class MainTeleOp extends LinearOpMode {

    private DcMotorEx slideLeft;
    private DcMotorEx slideRight;
    private DcMotorEx hangMotor;

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot();

        robot.initialize(this);
        //robot.dt.initTeleOp();

        //jfyfyf

        /*
        slideLeft = hardwareMap.get(DcMotorEx.class, "slideLeft");
        slideRight = hardwareMap.get(DcMotorEx.class, "slideRight");
        */
        hangMotor = hardwareMap.get(DcMotorEx.class, "hangMotor");

        /*
        slideLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        */
        hangMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        /*
        slideLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        */
        hangMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //slideRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            double d = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            //robot.dt.driveBL.setPower((y - x + rx) / d);
            //robot.dt.driveBR.setPower((y + x - rx) / d);
            //robot.dt.driveFL.setPower((y + x + rx) / d);
            //robot.dt.driveFR.setPower((y - x - rx) / d);

            // 2100 max slide value
            //robot.slides.setManualSlidePower(gamepad2.right_stick_y);

            hangMotor.setPower(gamepad2.left_stick_y);

            if(gamepad2.x) {
                robot.slides.incrementSlidePos(1);
            }
            if(gamepad2.a) {
                robot.slides.incrementSlidePos(2);
            }
        }
    }
}