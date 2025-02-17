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
public class SpecimenAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Robot robot = new Robot();
        robot.initialize(this);

        //(t*-0.5 - 2.75)

        double t = 23.5;
        Pose2d initialPose = new Pose2d((t*-0.5 - 2.75), (t*2.5 + 1.00), Math.toRadians(270));
        Pose2d pushSamplesEnd = new Pose2d(-55.5, 58.5, Math.toRadians(270));
        Pose2d rungEnd = new Pose2d(0, 33, Math.toRadians(270));
        Pose2d observationEnd = new Pose2d(-38, 58, Math.toRadians(270));
        Pose2d rungEnd2 = new Pose2d(3, 33, Math.toRadians(270));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        // Y position needed to clip Specimen onto the rung
        double rungY = 37;
        double observationY = 58;

        TrajectoryActionBuilder driveForwards = drive.actionBuilder(initialPose)
                .strafeToConstantHeading(new Vector2d(-7, 50))
                .splineToConstantHeading(new Vector2d(-4, rungY), Math.toRadians(270));

        TrajectoryActionBuilder pushSamples = driveForwards.endTrajectory().fresh()
                .afterDisp(10,robot.specimenActions.specimenIntake(robot))
                .setTangent(Math.toRadians(135))
                // Move to side
                .splineToConstantHeading(new Vector2d(-20, 50), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-38, 36), Math.toRadians(270))
                // Move up
                .splineToConstantHeading(new Vector2d(-38, 18), Math.toRadians(270))
                // Move up and right slightly
                //.splineToConstantHeading(new Vector2d(-42, 12), Math.toRadians(180), new TranslationalVelConstraint(20))
                // Move down and right slightly
                //.splineToConstantHeading(new Vector2d(-45, 18), Math.toRadians(90), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-45, 12), Math.toRadians(90))
                // Push
                .splineToConstantHeading(new Vector2d(-45, 50), Math.toRadians(90))
                .setTangent(Math.toRadians(270))
                // Move Up
                .splineToConstantHeading(new Vector2d(-45, 18), Math.toRadians(270), null, new ProfileAccelConstraint(-40, 40))
                // Move up and right slightly
                //.splineToConstantHeading(new Vector2d(-50, 12), Math.toRadians(180), new TranslationalVelConstraint(20))
                // Move down and right slightly
                //.splineToConstantHeading(new Vector2d(-55.5, 18), Math.toRadians(90), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-55, 12), Math.toRadians(90))
                // Push
                .splineToConstantHeading(new Vector2d(-55.5, observationY), Math.toRadians(90));

        TrajectoryActionBuilder driveToRung = pushSamples.endTrajectory().fresh()
                .setTangent(Math.toRadians(-45))
                .afterDisp(15, robot.specimenActions.specimenScore(robot))
                .strafeToConstantHeading(new Vector2d(-2, 50))
                .splineToConstantHeading(new Vector2d(2, rungY), Math.toRadians(270));

        double releaseDelay = 0.5;

        TrajectoryActionBuilder driveToObservation = driveToRung.endTrajectory().fresh()
                .waitSeconds(releaseDelay)
                .setReversed(true)
                .afterDisp(10, robot.specimenActions.specimenIntake(robot))
                .strafeToConstantHeading(new Vector2d(-38, observationY));

        TrajectoryActionBuilder driveToRung2 = driveToObservation.endTrajectory().fresh()
                .setReversed(false)
                .setTangent(Math.toRadians(-45))
                .afterDisp(10, robot.specimenActions.specimenScore(robot))
                .strafeToConstantHeading(new Vector2d(4, 50))
                .splineToConstantHeading(new Vector2d(7, rungY), Math.toRadians(270));

        TrajectoryActionBuilder driveToObservation2 = driveToRung2.endTrajectory().fresh()
                .waitSeconds(releaseDelay)
                .setReversed(true)
                .afterDisp(10, robot.specimenActions.specimenIntake(robot))
                .strafeToConstantHeading(new Vector2d(-38, observationY));

        TrajectoryActionBuilder driveToRung3 = driveToObservation2.endTrajectory().fresh()
                .setReversed(false)
                .setTangent(Math.toRadians(-45))
                .afterDisp(10, robot.specimenActions.specimenScore(robot))
                .strafeToConstantHeading(new Vector2d(0, 50))
                .splineToConstantHeading(new Vector2d(13, rungY), Math.toRadians(270));

        TrajectoryActionBuilder driveToObservation3 = driveToRung3.endTrajectory().fresh()
                .waitSeconds(releaseDelay)
                .setReversed(true)
                .afterDisp(10, robot.specimenActions.specimenIntake(robot))
                .strafeToConstantHeading(new Vector2d(-38, observationY));

        Action driveForwardsA = driveForwards.build();
        Action pushSamplesA = pushSamples.build();
        Action driveToRungA = driveToRung.build();
        Action driveToObservationA = driveToObservation.build();
        Action driveToRung2A = driveToRung2.build();
        Action driveToObservation2A = driveToObservation2.build();
        Action driveToRung3A = driveToRung3.build();
        Action driveToObservation3A = driveToObservation3.build();

        while (!isStopRequested() && !opModeIsActive()) {

        }

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                    new SequentialAction(
                        // PRELOAD SCORE
                        robot.specimenActions.specimenScore(robot),
                        driveForwardsA,

                        // PUSH SAMPLES
                        robot.specimenActions.specimenRelease(robot),
                        pushSamplesA,
                        robot.specimenActions.specimenGrab(robot),
                        // Lift the outtake slightly to lift specimen off the wall
                        robot.specimenActions.liftOuttakeSlightly(robot),

                        // SECOND TIME SCORING
                        driveToRungA,
                        new ParallelAction(
                            robot.specimenActions.specimenRelease(robot),
                            driveToObservationA
                        ),
                        robot.specimenActions.specimenGrab(robot),
                        robot.specimenActions.liftOuttakeSlightly(robot),

                        // THIRD TIME SCORING
                        driveToRung2A,
                        // REPEAT STUFF BEFORE TO CYCLE AGAIN
                        new ParallelAction(
                            robot.specimenActions.specimenRelease(robot),
                            driveToObservation2A
                        ),
                        robot.specimenActions.specimenGrab(robot),
                        robot.specimenActions.liftOuttakeSlightly(robot),

                        // FOURTH TIME SCORING
                        driveToRung3A,
                        // PARK IN OBSERVATION ZONE
                        new ParallelAction(
                            robot.specimenActions.specimenRelease(robot),
                            driveToObservation3A
                        )
                            ),
                    robot.specimenActions.robotUpdate(robot)
                )
        );
    }
}
