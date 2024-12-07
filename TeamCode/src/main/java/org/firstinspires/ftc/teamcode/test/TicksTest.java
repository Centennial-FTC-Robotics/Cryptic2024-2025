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
        driveBL = hardwareMap.get(DcMotorEx.class, "backLeft");
        driveBR = hardwareMap.get(DcMotorEx.class, "backRight");
        driveFL = hardwareMap.get(DcMotorEx.class, "frontLeft");
        driveFR = hardwareMap.get(DcMotorEx.class, "frontRight");

        driveBL.setDirection(DcMotorSimple.Direction.REVERSE);

        driveBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // par0: rightFront
        // par1: rightBack
        //perp: leftBack

        waitForStart();
        while(opModeIsActive()) {
            telemetry.addData("par0", ticksToInches(driveFR.getCurrentPosition()));
            telemetry.addData("par1", ticksToInches(driveBR.getCurrentPosition()));
            telemetry.addData("perp", ticksToInches(driveBL.getCurrentPosition()));
            telemetry.update();
        }
    }

    public double ticksToInches(int ticks) {
        double wheelDiameter = 38;
        int countsPerRev = 8192;
        double wheelCircum = 2 * Math.PI * wheelDiameter;
        double ticksToMM = countsPerRev / wheelCircum;
        double ticksToIn = ticksToMM / 25.4;
        return ticks * ticksToIn;
    }
}
