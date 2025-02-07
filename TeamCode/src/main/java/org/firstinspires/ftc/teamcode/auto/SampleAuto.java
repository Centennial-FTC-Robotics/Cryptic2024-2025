package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.Cryptic.Robot;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous
public class SampleAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Robot robot = new Robot();
        robot.initialize(this);

        double t = 23.5;
        Pose2d initialPose = new Pose2d((t*1.5-2.75), (t*2.5 + 2.75), Math.toRadians(180));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder driveToScore = drive.actionBuilder(initialPose)
                .strafeToSplineHeading(new Vector2d(57, 54), Math.toRadians(225));

        TrajectoryActionBuilder firstSample = driveToScore.endTrajectory().fresh()
                .strafeToSplineHeading(new Vector2d(48, 42), Math.toRadians(270));

        TrajectoryActionBuilder driveToScore2 = firstSample.endTrajectory().fresh()
                .strafeToSplineHeading(new Vector2d(57, 54), Math.toRadians(225));

        TrajectoryActionBuilder secondSample = driveToScore2.endTrajectory().fresh()
                .strafeToSplineHeading(new Vector2d(48, 40), Math.toRadians(305));

        TrajectoryActionBuilder driveToScore3 = secondSample.endTrajectory().fresh()
                .strafeToSplineHeading(new Vector2d(57, 54), Math.toRadians(225));

        TrajectoryActionBuilder thirdSample = driveToScore3.endTrajectory().fresh()
                .strafeToSplineHeading(new Vector2d(52, 25), Math.toRadians(0));

        TrajectoryActionBuilder driveToScore4 = thirdSample.endTrajectory().fresh()
                .strafeToSplineHeading(new Vector2d(57, 54), Math.toRadians(225));

        TrajectoryActionBuilder prepareToPark = driveToScore4.endTrajectory().fresh()
                .strafeToSplineHeading(new Vector2d(50, 10), Math.toRadians(180));

        TrajectoryActionBuilder park = prepareToPark.endTrajectory().fresh()
                .setTangent(Math.toRadians(180))
                .lineToX(30);
    }
}