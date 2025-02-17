package org.firstinspires.ftc.teamcode.auto.testing;

import com.acmerobotics.dashboard.config.Config;
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
@Autonomous
public class activePushSpecimen extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Robot robot = new Robot();
        robot.initialize(this);

        double t = 23.5;
        double offset = 5.0;
        double rungY = 40;
        double observationY = 59.3;
        Pose2d initialPose = new Pose2d((t * -0.5 - 2.75), (t * 2.5 + 1.00), Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder preload = drive.actionBuilder(initialPose)
                .stopAndAdd(robot.specimenActions.specimenScore(robot))
                .splineToConstantHeading(new Vector2d(-8, rungY), Math.toRadians(270))
                .stopAndAdd(robot.specimenActions.specimenRelease(robot));

        TrajectoryActionBuilder pushFirst = preload.endTrajectory().fresh()
                .afterDisp(10, robot.specimenActions.extendActiveIntake2(robot, 500))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-32+offset, 40, Math.toRadians(230)),Math.toRadians(215))
                .stopAndAdd(robot.specimenActions.lowerActiveIntake(robot))
                .setTangent(Math.toRadians(20))
                .splineToLinearHeading(new Pose2d(-32+offset, 44, Math.toRadians(150)),Math.toRadians(160))
                .stopAndAdd(robot.specimenActions.ActiveIntakeUp(robot));

        TrajectoryActionBuilder pushSecond = pushFirst.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-40+offset, 40), Math.toRadians(230))
                .stopAndAdd(robot.specimenActions.lowerActiveIntake(robot))
                .setTangent(Math.toRadians(20))
                .splineToLinearHeading(new Pose2d(-40+offset, 44, Math.toRadians(150)),Math.toRadians(160))
                .stopAndAdd(robot.specimenActions.ActiveIntakeUp(robot));

        TrajectoryActionBuilder pushThird = pushSecond.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-47+offset, 40), Math.toRadians(230))
                .stopAndAdd(robot.specimenActions.lowerActiveIntake(robot))
                .setTangent(Math.toRadians(20))
                .splineToLinearHeading(new Pose2d(-47+offset, 44, Math.toRadians(150)),Math.toRadians(160))
                .stopAndAdd(robot.specimenActions.ActiveIntakeUp(robot))
                .stopAndAdd(robot.specimenActions.retractActiveIntake(robot));

        TrajectoryActionBuilder scoreFirst = pushThird.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-38, 58), Math.toRadians(270))
                .stopAndAdd(robot.specimenActions.liftOuttakeSlightly(robot))
                .afterDisp(10, robot.specimenActions.specimenScore(robot))
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(8,rungY), Math.toRadians(270))
                .stopAndAdd(robot.specimenActions.specimenRelease(robot));

        TrajectoryActionBuilder scoreSecond = scoreFirst.endTrajectory().fresh()
                .afterDisp(10, robot.specimenActions.specimenIntake(robot))
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-38, 58),Math.toRadians(90))
                .stopAndAdd(robot.specimenActions.specimenGrab(robot))
                .stopAndAdd(robot.specimenActions.liftOuttakeSlightly(robot))
                .afterDisp(10, robot.specimenActions.specimenScore(robot))
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(4, rungY),Math.toRadians(270))
                .stopAndAdd(robot.specimenActions.specimenRelease(robot));

        TrajectoryActionBuilder scoreThird = scoreSecond.endTrajectory().fresh()
                .afterDisp(10, robot.specimenActions.specimenIntake(robot))
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-38, 58),Math.toRadians(90))
                .stopAndAdd(robot.specimenActions.specimenGrab(robot))
                .stopAndAdd(robot.specimenActions.liftOuttakeSlightly(robot))
                .afterDisp(10, robot.specimenActions.specimenScore(robot))
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(0, rungY),Math.toRadians(270))
                .stopAndAdd(robot.specimenActions.specimenRelease(robot));

        TrajectoryActionBuilder scoreFourth = scoreThird.endTrajectory().fresh()
                .afterDisp(10, robot.specimenActions.specimenIntake(robot))
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-38, 58),Math.toRadians(90))
                .stopAndAdd(robot.specimenActions.specimenGrab(robot))
                .stopAndAdd(robot.specimenActions.liftOuttakeSlightly(robot))
                .afterDisp(10, robot.specimenActions.specimenScore(robot))
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-4, rungY),Math.toRadians(270))
                .stopAndAdd(robot.specimenActions.specimenRelease(robot));

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(
                                preload.build(),
                                pushFirst.build(),
                                pushSecond.build(),
                                pushThird.build(),
                                scoreFirst.build(),
                                scoreSecond.build(),
                                scoreThird.build(),
                                scoreFourth.build()

                        ),
                        robot.specimenActions.robotUpdate(robot)
                )

        );
    }
}