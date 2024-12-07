package org.firstinspires.ftc.teamcode.test;

import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Photon
@TeleOp (name="DeadWheelTest")
public class DeadWheelTest extends LinearOpMode {

    private DcMotorEx driveBL;
    private DcMotorEx driveBR;
    private DcMotorEx driveFL;
    private DcMotorEx driveFR;

    @Override
    public void runOpMode() throws InterruptedException {
        driveBL = hardwareMap.get(DcMotorEx.class, "leftBack");
        driveBR = hardwareMap.get(DcMotorEx.class, "rightBack");
        driveFL = hardwareMap.get(DcMotorEx.class, "leftFront");
        driveFR = hardwareMap.get(DcMotorEx.class, "rightFront");

        driveBL.setDirection(DcMotorSimple.Direction.REVERSE);

        driveBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        driveBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // par0: rightFront, negative
        // par1: rightBack, negative
        //perp: leftBack, negative
        // left is positive

        waitForStart();
        while(opModeIsActive()) {
            telemetry.addData("par0", driveFR.getCurrentPosition());
            telemetry.addData("par1", driveBR.getCurrentPosition());
            telemetry.addData("perp", driveBL.getCurrentPosition());
            telemetry.update();
        }
    }
}
