package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.Cryptic.Subsystems.Drivetrain;

@Autonomous (name="RedNetZone")
public class RedNet extends LinearOpMode {

    private DcMotorEx driveBL;
    private DcMotorEx driveBR;
    private DcMotorEx driveFL;
    private DcMotorEx driveFR;

    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain dt = new Drivetrain();
        dt.init(this);

        waitForStart();

        dt.ticksToInches(2);


    }
}
