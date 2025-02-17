package org.firstinspires.ftc.teamcode.auto.testing;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.Cryptic.Robot;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous (name="Sample Auto (RUN THIS)")
public class intakeSampleAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Robot robot = new Robot();
        robot.initialize(this);

        double t = 23.5;
        Pose2d initialPose = new Pose2d((t*1.5-2.75), (t*2.5 + 2.75), Math.toRadians(180));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        double scoreX = 54;
        double scoreY = 52;

        TrajectoryActionBuilder driveToScore = drive.actionBuilder(initialPose)
                .stopAndAdd(robot.sampleActions.positionToScore(robot))
                .strafeToSplineHeading(new Vector2d(scoreX, scoreY), Math.toRadians(225))
                .stopAndAdd(robot.sampleActions.dropSample(robot))
                .stopAndAdd(robot.sampleActions.reset(robot));

        TrajectoryActionBuilder intakeFirstSample = driveToScore.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(54,43),Math.toRadians(-103))
                // wait because extendActiveIntake runs instantly
                .stopAndAdd(robot.specimenActions.extendActiveIntake2(robot,300))
                .stopAndAdd(robot.specimenActions.ActiveIntakeRun(robot))
                .stopAndAdd(robot.specimenActions.extendActiveIntake2(robot,500))
                .waitSeconds(0.8)
                .stopAndAdd(robot.specimenActions.ActiveIntakeUp(robot))
                .stopAndAdd(robot.sampleActions.transfer(robot))
                .stopAndAdd(robot.specimenActions.retractActiveIntake(robot))

                .stopAndAdd(robot.sampleActions.positionToScore(robot))
                .strafeToSplineHeading(new Vector2d(scoreX, scoreY), Math.toRadians(225))
                .stopAndAdd(robot.sampleActions.dropSample(robot))
                .stopAndAdd(robot.sampleActions.reset(robot))

                .strafeToLinearHeading(new Vector2d(54,43),Math.toRadians(-71))
                .stopAndAdd(robot.specimenActions.extendActiveIntake2(robot,350))
                .stopAndAdd(robot.specimenActions.ActiveIntakeRun(robot))
                .stopAndAdd(robot.specimenActions.extendActiveIntake2(robot,550))
                .waitSeconds(0.8)
                .stopAndAdd(robot.specimenActions.ActiveIntakeUp(robot))
                .stopAndAdd(robot.sampleActions.transfer(robot))
                .stopAndAdd(robot.specimenActions.retractActiveIntake(robot))

                .stopAndAdd(robot.sampleActions.positionToScore(robot))
                .strafeToSplineHeading(new Vector2d(scoreX, scoreY), Math.toRadians(225))
                .stopAndAdd(robot.sampleActions.dropSample(robot))
                .stopAndAdd(robot.sampleActions.reset(robot))

                .strafeToLinearHeading(new Vector2d(50,27),Math.toRadians(-1))
                .stopAndAdd(robot.specimenActions.extendActiveIntake2(robot,250))
                .stopAndAdd(robot.specimenActions.ActiveIntakeRun(robot))
                .stopAndAdd(robot.specimenActions.extendActiveIntake2(robot,350))
                .waitSeconds(0.8)
                .stopAndAdd(robot.specimenActions.ActiveIntakeUp(robot))
                .stopAndAdd(robot.sampleActions.transfer(robot))
                .stopAndAdd(robot.specimenActions.retractActiveIntake(robot))

                .stopAndAdd(robot.sampleActions.positionToScore(robot))
                .strafeToSplineHeading(new Vector2d(scoreX, scoreY), Math.toRadians(225))
                .stopAndAdd(robot.sampleActions.dropSample(robot))
                .stopAndAdd(robot.sampleActions.reset(robot))

                .strafeToSplineHeading(new Vector2d(50, 10), Math.toRadians(180));


        Action driveToScoreA = driveToScore.build();
        Action intakeFirstSampleA = intakeFirstSample.build();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(
                                driveToScoreA,
                                intakeFirstSampleA
                        ),
                        robot.specimenActions.robotUpdate(robot)
                )

        );
    }
}