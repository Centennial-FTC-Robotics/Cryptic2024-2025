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
@Autonomous
public class betterIntakeSample extends LinearOpMode {
    public Action intakeSequence(Robot robot, int dis1, int dis2) {
        robot.specimenActions.extendActiveIntake2(robot,dis1);
        robot.specimenActions.ActiveIntakeRun(robot);
        robot.specimenActions.extendActiveIntake2(robot,dis2);
        sleep(700);
        robot.specimenActions.ActiveIntakeUp(robot);
        robot.sampleActions.transfer(robot);
        robot.specimenActions.retractActiveIntake(robot);
        robot.sampleActions.positionToScore(robot);
        return null;
    }

    @Override
    public void runOpMode() throws InterruptedException {

        Robot robot = new Robot();
        robot.initialize(this);
        Pose2d initialPose = new Pose2d(32.5, 61.5, Math.toRadians(180));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder putTheFriesInTheBag = drive.actionBuilder(initialPose)
                .stopAndAdd(robot.sampleActions.positionToScore(robot))
                .strafeToLinearHeading(new Vector2d(54, 52), Math.toRadians(225))
                .stopAndAdd(robot.sampleActions.dropSample(robot))
                .stopAndAdd(robot.sampleActions.reset(robot));

        TrajectoryActionBuilder first = putTheFriesInTheBag.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(54,43),Math.toRadians(-104));

        TrajectoryActionBuilder second = first.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(54,43),Math.toRadians(-71));

        TrajectoryActionBuilder third = second.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(50,27),Math.toRadians(-1));

        TrajectoryActionBuilder hang = third.endTrajectory().fresh()
                .stopAndAdd(robot.sampleActions.hangSlides(robot))
                .setTangent(Math.toRadians(270))
                .strafeToSplineHeading(new Vector2d(50, 10), Math.toRadians(180));


        Action score = putTheFriesInTheBag.build();
        Action firstSample = first.build();
        Action secondSample = second.build();
        Action thirdSample = third.build();
        Action park = hang.build();

        waitForStart();
        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(
                                score,
                                firstSample,
                                intakeSequence(robot, 350, 550),
                                score,
                                secondSample,
                                intakeSequence(robot, 300, 500),
                                score,
                                thirdSample,
                                intakeSequence(robot, 200, 300),
                                score,
                                park
                        ),
                        robot.specimenActions.robotUpdate(robot)
                )
        );
    }
}