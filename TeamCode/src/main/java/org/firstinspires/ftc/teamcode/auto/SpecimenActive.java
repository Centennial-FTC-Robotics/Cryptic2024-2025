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

import org.Cryptic.Commands.AutoActions;
import org.Cryptic.Robot;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous
public class SpecimenActive extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Robot robot = new Robot();
        robot.initialize(this);

        double t = 23.5;
        Pose2d initialPose = new Pose2d((t*-0.5 - 2.75), (t*2.5 + 1.00), Math.toRadians(270));
        Pose2d pushSamplesEnd = new Pose2d(-55.5, 58.5, Math.toRadians(270));
        Pose2d rungEnd = new Pose2d(0, 33, Math.toRadians(270));
        Pose2d observationEnd = new Pose2d(-38, 58, Math.toRadians(270));
        Pose2d rungEnd2 = new Pose2d(3, 33, Math.toRadians(270));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        // Y position needed to clip Specimen onto the rung
        double rungY = 40;
        double observationY = 59.3;

        TrajectoryActionBuilder driveForwards = drive.actionBuilder(initialPose)
                // Offset this guy by 4 idk why tbh
                .strafeToConstantHeading(new Vector2d(1, rungY-4));
        //.splineToConstantHeading(new Vector2d(-4, rungY-1.5), Math.toRadians(270));

        double offset = 5.0;
        TrajectoryActionBuilder intakeSamples = driveForwards.endTrajectory().fresh()
                // Go back to clear outtake from rung
                //.lineToY(40)
                // Get in position to intake
                .strafeToLinearHeading(new Vector2d(-4, 45), Math.toRadians(195))
                // Intake sample
                .stopAndAdd(robot.autoActions.extendActiveIntake(robot))
                .afterDisp(12, robot.autoActions.ActiveIntakeRun(robot))
                .strafeToLinearHeading(new Vector2d(-32+offset, 40), Math.toRadians(210))
                .stopAndAdd(robot.autoActions.ActiveIntakeUp(robot))
                // Strafe to Observation zone
                .strafeToLinearHeading(new Vector2d(-32+offset, 44), Math.toRadians(135))
                // Expel sample
                .stopAndAdd(robot.autoActions.ActiveIntakeExpel(robot))
                .stopAndAdd(robot.autoActions.ActiveIntakeUp(robot))
                // Strafe to second sample
                .strafeToLinearHeading(new Vector2d(-32+offset, 40), Math.toRadians(205))
                .stopAndAdd(robot.autoActions.ActiveIntakeRun(robot))
                .strafeToLinearHeading(new Vector2d(-40+offset, 40), Math.toRadians(210))
                .stopAndAdd(robot.autoActions.ActiveIntakeUp(robot))
                // Strafe to Observation zone
                .strafeToLinearHeading(new Vector2d(-40+offset, 44), Math.toRadians(135))
                .stopAndAdd(robot.autoActions.ActiveIntakeExpel(robot))
                .stopAndAdd(robot.autoActions.ActiveIntakeUp(robot))
                // Strafe to third sample
                //.strafeToLinearHeading(new Vector2d(-40+offset, 40), Math.toRadians(203))
                //.stopAndAdd(robot.autoActions.ActiveIntakeRun(robot))
                //.strafeToLinearHeading(new Vector2d(-47+offset, 40), Math.toRadians(207))
                //.stopAndAdd(robot.autoActions.ActiveIntakeUp(robot))
                //.strafeToLinearHeading(new Vector2d(-44+offset, 44), Math.toRadians(120))
                //.stopAndAdd(robot.autoActions.ActiveIntakeExpel(robot))
                //.stopAndAdd(robot.autoActions.ActiveIntakeUp(robot))
                .stopAndAdd(robot.autoActions.retractActiveIntake(robot));

        TrajectoryActionBuilder driveToObservation = intakeSamples.endTrajectory().fresh()
                .stopAndAdd(robot.autoActions.specimenIntake(robot))
                .strafeToLinearHeading(new Vector2d(-38, observationY), Math.toRadians(270))
                .stopAndAdd(robot.autoActions.specimenGrab(robot));

        // SECOND SPECIMEN
        TrajectoryActionBuilder driveToRung = driveToObservation.endTrajectory().fresh()
                .stopAndAdd(robot.autoActions.liftOuttakeSlightly(robot))
                .setReversed(false)
                .setTangent(Math.toRadians(-45))
                .afterDisp(10, robot.autoActions.specimenScore(robot))
                .strafeToConstantHeading(new Vector2d(9, rungY));

        TrajectoryActionBuilder driveToObservation2 = driveToRung.endTrajectory().fresh()
                .stopAndAdd(robot.autoActions.specimenRelease(robot))
                .setReversed(true)
                .afterDisp(10, robot.autoActions.specimenIntake(robot))
                .strafeToConstantHeading(new Vector2d(-38, observationY))
                .stopAndAdd(robot.autoActions.specimenGrab(robot));

        // THIRD SPECIMEN
        TrajectoryActionBuilder driveToRung2 = driveToObservation2.endTrajectory().fresh()
                .stopAndAdd(robot.autoActions.liftOuttakeSlightly(robot))
                .setReversed(false)
                .setTangent(Math.toRadians(-45))
                .afterDisp(10, robot.autoActions.specimenScore(robot))
                .strafeToConstantHeading(new Vector2d(9, rungY));

        TrajectoryActionBuilder driveToObservation3 = driveToRung2.endTrajectory().fresh()
                .stopAndAdd(robot.autoActions.specimenRelease(robot))
                .setReversed(true)
                .afterDisp(10, robot.autoActions.specimenIntake(robot))
                .strafeToConstantHeading(new Vector2d(-38, observationY))
                .stopAndAdd(robot.autoActions.specimenGrab(robot));

        // FOURTH SPECIMEN
        TrajectoryActionBuilder driveToRung3 = driveToObservation3.endTrajectory().fresh()
                .stopAndAdd(robot.autoActions.liftOuttakeSlightly(robot))
                .setReversed(false)
                .setTangent(Math.toRadians(-45))
                .afterDisp(10, robot.autoActions.specimenScore(robot))
                .strafeToConstantHeading(new Vector2d(9, rungY));

        TrajectoryActionBuilder driveToObservation4 = driveToRung3.endTrajectory().fresh()
                .stopAndAdd(robot.autoActions.specimenRelease(robot))
                .setReversed(true)
                .afterDisp(10, robot.autoActions.specimenIntake(robot))
                .strafeToConstantHeading(new Vector2d(-38, observationY));

        Action driveForwardsA = driveForwards.build();
        Action intakeSamplesA = intakeSamples.build();
        Action driveToObservationA = driveToObservation.build();
        Action driveToRungA = driveToRung.build();
        Action driveToObservation2A = driveToObservation2.build();
        Action driveToRung2A = driveToRung2.build();
        Action driveToObservation3A = driveToObservation3.build();
        Action driveToRung3A = driveToRung3.build();
        Action driveToObservation4A = driveToObservation4.build();

        while (!isStopRequested() && !opModeIsActive()) {

        }

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(
                                // PRELOAD SCORE
                                robot.autoActions.specimenScore(robot),
                                driveForwardsA,

                                robot.autoActions.specimenRelease(robot),
                                intakeSamplesA,
                                driveToObservationA,
                                driveToRungA,
                                driveToObservation2A,
                                driveToRung2A,
                                driveToObservation3A,
                                driveToRung3A,
                                driveToObservation4A
                        ),
                        robot.autoActions.robotUpdate(robot)
                )
        );
    }
}
