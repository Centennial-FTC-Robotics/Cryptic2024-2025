package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.Cryptic.Robot;
import org.Cryptic.Subsystems.DrivetrainNoRR;

@Autonomous (name = "Red_Ob_Spec_Push")
public class RedObservationSpecimenAndPush extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot();
        robot.initialize(this);

        waitForStart();

        // Drive forwards
        robot.dtNoRR.driveDistance(-24);

        //robot.outtake.specimenScoreAuto();

        robot.dtNoRR.driveDistance(-12);

        // MOVE SAMPLES TO OBSERVATION ZONE
        robot.dtNoRR.strafeDistance(-24);

        robot.dtNoRR.turnToHeading(90);

        robot.dtNoRR.strafeDistance(36);

        robot.dtNoRR.driveDistance(-12);

        robot.dtNoRR.strafeDistance(-36);

    }
}
