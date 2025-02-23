package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.AccelConstraint;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.Cryptic.Commands.SpecimenActions;
import org.Cryptic.Commands.SpecimenCommands;
import org.Cryptic.Robot;
import org.Cryptic.util.Globals;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous (name="Specimen Auto (RUN THIS)")
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

        VelConstraint vel = new TranslationalVelConstraint(60);
        AccelConstraint accel = new ProfileAccelConstraint(-30, 50);

        // Y position needed to clip Specimen onto the rung
        double rungY = 35.5;
        //double observationY = 59.7;
        double observationX = -34;
        double observationY = 60.2;

        TrajectoryActionBuilder driveForwards = drive.actionBuilder(initialPose)
                .stopAndAdd(robot.specimenActions.specimenScore(robot))
                .waitSeconds(0.2)
                // Offset this guy by 2 idk why tbh
                .strafeToConstantHeading(new Vector2d(-5, rungY));
        //.splineToConstantHeading(new Vector2d(-4, rungY-1.5), Math.toRadians(270));

        double offset = 5.0;

        /*
        TrajectoryActionBuilder pushSamplesNew = driveForwards.endTrajectory().fresh()
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-38, 36, Math.toRadians(90)), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-38, 18), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-47, 12), Math.toRadians(90))
                // PUSH USING ACTIVE INTAKE
                .stopAndAdd(robot.specimenActions.setIntakePitch(robot, Intake.PitchState.PUSH)) // takes 0.2 seconds
                .stopAndAdd(robot.specimenActions.extendActiveIntake2(robot, 600))
                .strafeToConstantHeading(new Vector2d(-47, 32))
                //.stopAndAdd(robot.specimenActions.setIntakePower(robot,-0.3))
                //.stopAndAdd(robot.specimenActions.setIntakePitch(robot, Intake.PitchState.DOWN))
                .stopAndAdd(robot.specimenActions.retractActiveIntake(robot))
                .strafeToConstantHeading(new Vector2d(-47, 12))

                .strafeToConstantHeading(new Vector2d(-57, 12))

                .stopAndAdd(robot.specimenActions.setIntakePitch(robot, Intake.PitchState.PUSH)) // takes 0.2 seconds
                .stopAndAdd(robot.specimenActions.extendActiveIntake2(robot, 600))
                .strafeToConstantHeading(new Vector2d(-57, 32))
                //.stopAndAdd(robot.specimenActions.setIntakePower(robot,-0.3))
                //.stopAndAdd(robot.specimenActions.setIntakePitch(robot, Intake.PitchState.DOWN))
                .stopAndAdd(robot.specimenActions.retractActiveIntake(robot))
                .strafeToConstantHeading(new Vector2d(-57, 12))

                .strafeToConstantHeading(new Vector2d(-62, 12))
                .setReversed(false)
                .strafeToConstantHeading(new Vector2d(-62, 45))
                .strafeToConstantHeading(new Vector2d(-50, 45));
        */

        TrajectoryActionBuilder intakeSamples = driveForwards.endTrajectory().fresh()
                .afterDisp(0.0, robot.specimenActions.specimenRelease(robot))
                .lineToY(40)

                // Intake sample
                .afterDisp(18, robot.specimenActions.extendActiveIntake(robot))
                .afterDisp(20, robot.specimenActions.ActiveIntakeRun(robot))
                .strafeToLinearHeading(new Vector2d(-33+offset, 40), Math.toRadians(210))
                .stopAndAdd(robot.specimenActions.ActiveIntakeUp(robot))
                // Strafe to Observation zone
                .strafeToLinearHeading(new Vector2d(-32+offset, 44), Math.toRadians(145))
                // Expel sample
                .stopAndAdd(robot.specimenActions.ActiveIntakeExpel(robot))
                .stopAndAdd(robot.specimenActions.ActiveIntakeUp(robot))
                // Strafe to second sample
                .strafeToLinearHeading(new Vector2d(-32+offset, 40), Math.toRadians(200))
                .stopAndAdd(robot.specimenActions.ActiveIntakeRun(robot))
                .strafeToLinearHeading(new Vector2d(-41+offset, 40), Math.toRadians(205))
                .stopAndAdd(robot.specimenActions.ActiveIntakeUp(robot))
                // Strafe to Observation zone
                .strafeToLinearHeading(new Vector2d(-40+offset, 44), Math.toRadians(145))
                .stopAndAdd(robot.specimenActions.ActiveIntakeExpel(robot))
                .stopAndAdd(robot.specimenActions.ActiveIntakeUp(robot))
                // Strafe to third sample
                .strafeToLinearHeading(new Vector2d(-40+offset, 40), Math.toRadians(203))
                .stopAndAdd(robot.specimenActions.ActiveIntakeRun(robot))
                .strafeToLinearHeading(new Vector2d(-48+offset, 40), Math.toRadians(205))
                .stopAndAdd(robot.specimenActions.ActiveIntakeUp(robot))
                .strafeToLinearHeading(new Vector2d(-44+offset, 44), Math.toRadians(140))
                .stopAndAdd(robot.specimenActions.ActiveIntakeExpel(robot))
                .stopAndAdd(robot.specimenActions.ActiveIntakeUp(robot))
                .stopAndAdd(robot.specimenActions.retractActiveIntake(robot));

        TrajectoryActionBuilder driveToObservation = intakeSamples.endTrajectory().fresh()
                .stopAndAdd(robot.specimenActions.specimenIntake(robot))
                // Give space so robot doesn't knock over specimen
                .strafeToLinearHeading(new Vector2d(observationX, observationY - 4), Math.toRadians(270))
                .strafeToConstantHeading(new Vector2d(observationX, observationY))
                //.strafeToLinearHeading(new Vector2d(-38, observationY), Math.toRadians(270))
                .stopAndAdd(robot.specimenActions.specimenGrab(robot));

        // SECOND SPECIMEN
        TrajectoryActionBuilder driveToRung = driveToObservation.endTrajectory().fresh()
                .stopAndAdd(robot.specimenActions.liftOuttakeSlightly(robot))
                .setReversed(false)
                .setTangent(Math.toRadians(-45))
                .afterDisp(2, robot.specimenActions.specimenScore(robot))
                //.strafeToLinearHeading(new Vector2d(-1, rungY), Math.toRadians(285));
                .strafeToConstantHeading(new Vector2d(0, rungY));

        TrajectoryActionBuilder driveToObservation2 = driveToRung.endTrajectory().fresh()
                .stopAndAdd(robot.specimenActions.specimenRelease(robot))
                .setReversed(true)
                .afterDisp(10, robot.specimenActions.specimenIntake(robot))
                //.strafeToLinearHeading(new Vector2d(-38, observationY), Math.toRadians(270))
                .strafeToConstantHeading(new Vector2d(observationX, observationY))
                .stopAndAdd(robot.specimenActions.specimenGrab(robot));

        // THIRD SPECIMEN
        TrajectoryActionBuilder driveToRung2 = driveToObservation2.endTrajectory().fresh()
                .stopAndAdd(robot.specimenActions.liftOuttakeSlightly(robot))
                .setReversed(false)
                .setTangent(Math.toRadians(-45))
                .afterDisp(2, robot.specimenActions.specimenScore(robot))
                //.strafeToLinearHeading(new Vector2d(0, rungY), Math.toRadians(285));
                .strafeToConstantHeading(new Vector2d(0, rungY));

        TrajectoryActionBuilder driveToObservation3 = driveToRung2.endTrajectory().fresh()
                .stopAndAdd(robot.specimenActions.specimenRelease(robot))
                .setReversed(true)
                .afterDisp(10, robot.specimenActions.specimenIntake(robot))
                //.strafeToLinearHeading(new Vector2d(-38, observationY), Math.toRadians(270))
                .strafeToConstantHeading(new Vector2d(observationX, observationY))
                .stopAndAdd(robot.specimenActions.specimenGrab(robot));

        // FOURTH SPECIMEN
        TrajectoryActionBuilder driveToRung3 = driveToObservation3.endTrajectory().fresh()
                .stopAndAdd(robot.specimenActions.liftOuttakeSlightly(robot))
                .setReversed(false)
                .setTangent(Math.toRadians(-45))
                .afterDisp(2, robot.specimenActions.specimenScore(robot))
                //.strafeToLinearHeading(new Vector2d(0, rungY), Math.toRadians(285));
                .strafeToConstantHeading(new Vector2d(0, rungY));

        TrajectoryActionBuilder driveToObservation4 = driveToRung3.endTrajectory().fresh()
                .afterDisp(0.0, robot.specimenActions.specimenRelease(robot))
                .setReversed(true)
                .afterDisp(10, robot.specimenActions.specimenIntake(robot))
                //.strafeToLinearHeading(new Vector2d(-38, observationY), Math.toRadians(270))
                .strafeToConstantHeading(new Vector2d(observationX, observationY))
                .stopAndAdd(robot.specimenActions.specimenGrab(robot));

        // FIFTH SPECIMEN!!!!!!!!
        TrajectoryActionBuilder driveToRung4 = driveToObservation4.endTrajectory().fresh()
                .stopAndAdd(robot.specimenActions.liftOuttakeSlightly(robot))
                .setReversed(false)
                .setTangent(Math.toRadians(-45))
                .afterDisp(2, robot.specimenActions.specimenScore(robot))
                .strafeToConstantHeading(new Vector2d(0, rungY));
                //.strafeToLinearHeading(new Vector2d(0, rungY), Math.toRadians(285));

        // Quick Park
        TrajectoryActionBuilder quickPark = driveToRung4.endTrajectory().fresh()
                .afterDisp(0.0, robot.specimenActions.specimenRelease(robot))
                .setReversed(true)
                .afterDisp(10, robot.specimenActions.specimenIntake(robot))
                .afterDisp(15, robot.specimenActions.extendActiveIntake(robot))
                .strafeToLinearHeading(new Vector2d(-22, 50), Math.toRadians(145));

        Action driveForwardsA = driveForwards.build();
        //Action pushSamplesNewA = pushSamplesNew.build();
        Action intakeSamplesA = intakeSamples.build();
        Action driveToObservationA = driveToObservation.build();
        Action driveToRungA = driveToRung.build();
        Action driveToObservation2A = driveToObservation2.build();
        Action driveToRung2A = driveToRung2.build();
        Action driveToObservation3A = driveToObservation3.build();
        Action driveToRung3A = driveToRung3.build();
        Action driveToObservation4A = driveToObservation4.build();
        Action driveToRung4A = driveToRung4.build();
        Action quickParkA = quickPark.build();

        GamepadEx drivePad = new GamepadEx(gamepad1);
        // Turn off automatic expel by default
        robot.intake.ALLIANCE_COLOR = Globals.SampleColor.UNKNOWN;
        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.addData("CURRENT ALLIANCE COLOR", robot.intake.ALLIANCE_COLOR);
            telemetry.addLine("PRESS B (CIRCLE) ON GAMEPAD1 TO CHANGE ALLIANCE COLOR");
            telemetry.update();
            if (drivePad.wasJustPressed(GamepadKeys.Button.B)) {
                if (robot.intake.ALLIANCE_COLOR == Globals.SampleColor.UNKNOWN) {
                    robot.intake.ALLIANCE_COLOR = Globals.SampleColor.BLUE;
                } else if (robot.intake.ALLIANCE_COLOR == Globals.SampleColor.BLUE) {
                    robot.intake.ALLIANCE_COLOR = Globals.SampleColor.RED;
                } else if (robot.intake.ALLIANCE_COLOR == Globals.SampleColor.RED) {
                    robot.intake.ALLIANCE_COLOR = Globals.SampleColor.BLUE;
                }
            }
            drivePad.readButtons();
        }

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(
                                // PRELOAD SCORE
                                driveForwardsA,
                                intakeSamplesA,
                                driveToObservationA,
                                driveToRungA,
                                driveToObservation2A,
                                driveToRung2A,
                                driveToObservation3A,
                                driveToRung3A,
                                driveToObservation4A,
                                driveToRung4A,
                                quickParkA
                        ),
                        robot.specimenActions.robotUpdate(robot)
                )
        );
    }
}
