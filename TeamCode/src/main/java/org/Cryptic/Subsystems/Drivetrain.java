package org.Cryptic.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.Cryptic.Subsystem;
import org.firstinspires.ftc.teamcode.MecanumDrive;

public class Drivetrain extends Subsystem {

    public MecanumDrive drivebase;

    public void init(LinearOpMode opMode) {
        //drivebase = new MecanumDrive(opMode.hardwareMap);
    }
}
