package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.Cryptic.Robot;
import org.Cryptic.Subsystems.Intake;

@TeleOp
public class SampleOpMode extends LinearOpMode {
    private DcMotor motor;

    Robot robot = new Robot();


    @Override
    public void runOpMode() throws InterruptedException{
        motor = hardwareMap.get(DcMotor.class,"drivetrain");

        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        GamepadEx drivePad = new GamepadEx(gamepad1);
        GamepadEx intakePad = new GamepadEx(gamepad2);


        while (opModeIsActive()) {
            drivePad.readButtons();
            intakePad.readButtons();

            robot.intake.getColor();

            if(intakePad.wasJustPressed(GamepadKeys.Button.X)) {
                robot.intake.setIntakePitch(Intake.PitchState.DOWN);
            }

            if(intakePad.wasJustPressed(GamepadKeys.Button.A)){
                robot.intake.setIntakePitch(Intake.PitchState.UP);
            }

            if(intakePad.wasJustPressed(GamepadKeys.Button.Y)){
                robot.intake.setIntakePitch(Intake.PitchState.STOWED);
            }

            if(intakePad.wasJustPressed(GamepadKeys.Button.B)) {
                robot.intake.setPrimeIntake(!robot.intake.getPrimeIntake());
            }

            if(intakePad.wasJustPressed(GamepadKeys.Button.DPAD_UP)){
                if(robot.intake.getSlidesTarget()+0.2<=1.0)
                    robot.intake.setSlidesTarget(robot.intake.getSlidesTarget()+0.2);
            }

//            if(intakePad.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)){
//                if(robot.intake.getSlidesTarget()-0.2>=0.0)
//                    robot.intake.setSlidesTarget(robot.intake.getSlidesTarget()-0.2);
//            }
            
            if(intakePad.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)){
                robot.intake.setSlidesTarget(0.0);
            }

            robot.intake.setIntakePower(gamepad1.left_stick_y);



//            motor.setPower(gamepad1.left_stick_y);
        }
        //motor.setPower(0.0);

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER)
        ;
        motor.setTargetPosition(200);

        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motor.setPower(1.0);

        while (motor.isBusy()) {

        }

        motor.setPower(0.0);

        //motor.getCurrentPosition();
    }

}
