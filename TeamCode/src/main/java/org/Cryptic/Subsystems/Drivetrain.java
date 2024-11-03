package org.Cryptic.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.Cryptic.Subsystem;

public class Drivetrain extends Subsystem {

    public DcMotorEx driveBL;
    public DcMotorEx driveBR;
    public DcMotorEx driveFL;
    public DcMotorEx driveFR;

    public void init(LinearOpMode opMode) {
        driveBL = opMode.hardwareMap.get(DcMotorEx.class, "backLeft");
        driveBR = opMode.hardwareMap.get(DcMotorEx.class, "backRight");
        driveFL = opMode.hardwareMap.get(DcMotorEx.class, "frontLeft");
        driveFR = opMode.hardwareMap.get(DcMotorEx.class, "frontRight");

        driveBL.setDirection(DcMotorEx.Direction.REVERSE);
        //driveFL.setDirection(DcMotorEx.Direction.REVERSE);

    }

    public void initTeleOp() {
        driveBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        driveBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
}
