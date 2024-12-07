package org.firstinspires.ftc.teamcode.test;

import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Photon
@TeleOp (name="TicksTest")
public class TicksTest extends LinearOpMode {

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

        // par0: rightFront
        // par1: rightBack
        //perp: leftBack

        driveBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        while(opModeIsActive()) {
            telemetry.addData("par0", ticksToInches(-driveFR.getCurrentPosition()));
            telemetry.addData("par1", ticksToInches(-driveBR.getCurrentPosition()));
            telemetry.addData("perp", ticksToInches(-driveBL.getCurrentPosition()));
            telemetry.update();
        }
    }

    public double ticksToInches(int ticks) {
        double wheelDiameter = 38;
        int countsPerRev = 8192;
        double wheelCircum = Math.PI * (wheelDiameter / 25.4);
        double ticksToIn = 8192 / wheelCircum;
        return (ticks / ticksToIn);
    }
}
