package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.Cryptic.Commands.AutoActions;
import org.Cryptic.Robot;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous
public class RoadRunnerTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Robot robot = new Robot();
        robot.initialize(this);

        double t = 23.5;
        Pose2d initialPose = new Pose2d((t*-0.5 - 2.75), (t*2.5 + 2.75), Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder driveForwards = drive.actionBuilder(initialPose)
                .splineToConstantHeading(new Vector2d(-4, 33), Math.toRadians(270));
        TrajectoryActionBuilder pushSamples = driveForwards.endTrajectory().fresh()
                .setTangent(Math.toRadians(135))
                // Move to side
                .splineToConstantHeading(new Vector2d(-37, 36.16), Math.toRadians(270))
                // Move up
                .splineToConstantHeading(new Vector2d(-38, 18), Math.toRadians(270))
                // Move right
                .splineToConstantHeading(new Vector2d(-45, 12), Math.toRadians(90))
                // Push
                .splineToConstantHeading(new Vector2d(-45, 50), Math.toRadians(90))
                .setTangent(Math.toRadians(270))
                // Move Up
                .splineToConstantHeading(new Vector2d(-45, 18), Math.toRadians(270))
                // Move right
                .splineToConstantHeading(new Vector2d(-55, 12), Math.toRadians(90))
                // Push
                .splineToConstantHeading(new Vector2d(-55, 50), Math.toRadians(90))
                .setTangent(Math.toRadians(270))
                // Move Up
                .splineToConstantHeading(new Vector2d(-55, 18), Math.toRadians(270))
                // Move right
                .splineToConstantHeading(new Vector2d(-58, 12), Math.toRadians(90))
                // Push
                .splineToConstantHeading(new Vector2d(-58, 50), Math.toRadians(90))
                .setTangent(Math.toRadians(270))
                // Move back
                //.splineToConstantHeading(new Vector2d(-55, 47), Math.toRadians(270))
                // Prepare for specimens
                .splineToConstantHeading(new Vector2d(-38, 42.5), Math.toRadians(90))
                .setTangent(Math.toRadians(90));

                /*
                // Prepare for specimens
                .splineTo(new Vector2d(-38, 42.5), Math.toRadians(90))
                .waitSeconds(1)
                // Head into Observation Zone
                .splineTo(new Vector2d(-38, 58), Math.toRadians(90))

                // Pick up Specimen
                .waitSeconds(1)
                .setTangent(Math.toRadians(270))
                // Go to rung
                .splineToConstantHeading(new Vector2d(-9.73, 35.77), Math.toRadians(270))
                // Hang specimen
                .waitSeconds(1)
                .setTangent(Math.toRadians(90))
                // Go Back to Observation Zone
                .splineToConstantHeading(new Vector2d(-38, 58), Math.toRadians(90))

                // Pick up Specimen
                .waitSeconds(1)
                .setTangent(Math.toRadians(270))
                // Go to rung
                .splineToConstantHeading(new Vector2d(-9.73, 35.77), Math.toRadians(270))
                // Hang specimen
                .waitSeconds(1)
                .setTangent(Math.toRadians(90))
                // Go Back to Observation Zone
                .splineToConstantHeading(new Vector2d(-38, 58), Math.toRadians(90))

                // Pick up Specimen
                .waitSeconds(1)
                .setTangent(Math.toRadians(270))
                // Go to rung
                .splineToConstantHeading(new Vector2d(-9.73, 35.77), Math.toRadians(270))
                // Hang specimen
                .waitSeconds(1)
                .setTangent(Math.toRadians(90));

                 */

        TrajectoryActionBuilder driveIntoObservation = pushSamples.endTrajectory().fresh()
                // Let Outtake finish moving
                .waitSeconds(0.4)
                // Head into Observation Zone
                .splineTo(new Vector2d(-38, 58), Math.toRadians(90));

        TrajectoryActionBuilder driveToRung = driveIntoObservation.endTrajectory().fresh()
                .setTangent(Math.toRadians(0))
                // Go to rung
                .splineToConstantHeading(new Vector2d(4, 50), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(4, 33), Math.toRadians(270));

        TrajectoryActionBuilder driveToObservation2 = driveToRung.endTrajectory().fresh()
                .setTangent(Math.toRadians(90))
                // Go Back to Observation Zone
                .splineToConstantHeading(new Vector2d(10, 45), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-38, 58), Math.toRadians(180));


        Action driveForwardsA = driveForwards.build();
        Action pushSamplesA = pushSamples.build();
        Action driveIntoObservationA = driveIntoObservation.build();
        Action driveToRungA = driveToRung.build();
        Action driveToObservation2A = driveToObservation2.build();

        while (!isStopRequested() && !opModeIsActive()) {

        }

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                    new SequentialAction(
                        robot.autoActions.specimenScore(robot),
                        driveForwardsA,
                        robot.autoActions.specimenRelease(robot),
                        pushSamplesA,
                        robot.autoActions.specimenIntake(robot),
                        driveIntoObservationA,
                        robot.autoActions.specimenGrab(robot),
                        robot.autoActions.specimenScore(robot),
                        driveToRungA,
                        robot.autoActions.specimenRelease(robot),
                        driveToObservation2A
                            ),
                    robot.autoActions.robotUpdate(robot)
                )
        );
    }
}
