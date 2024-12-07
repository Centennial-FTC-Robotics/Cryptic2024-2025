package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.Cryptic.Robot;
import org.Cryptic.Subsystems.Drivetrain;

@Autonomous (name="RedNetZone")
public class RedNet extends LinearOpMode {

    private DcMotorEx driveBL;
    private DcMotorEx driveBR;
    private DcMotorEx driveFL;
    private DcMotorEx driveFR;

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot();
        robot.initialize(this);

        waitForStart();

        robot.imu.revIMU.reset();
        robot.dt.strafeDistance(12);

        robot.dt.turnToHeading(25);
        // Raise slides up a little
        // Extend Hori slides
        // Arm Down
        // Claw Close
        // Arm Up
        // Retract Hori slides

        robot.dt.turnToHeading(105);
        robot.dt.driveDistance(-10);
        // Raise slides to max
        // Arm Back
        // Diffy Back
        robot.dt.driveDistance(-14);
        // Claw Open
        robot.dt.driveDistance(14);
        // Arm Forward
        // Diffy Forward
        // Slides down
        robot.dt.driveDistance(10);

        // Go Park
        robot.dt.turnToHeading(90);
        robot.dt.driveDistance(36);
        robot.dt.turnToHeading(180);
        robot.dt.driveDistance(12);


    }
}
