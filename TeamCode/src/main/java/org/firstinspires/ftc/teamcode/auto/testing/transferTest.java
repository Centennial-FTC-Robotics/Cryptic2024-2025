package org.firstinspires.ftc.teamcode.auto.testing;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.Cryptic.Commands.SpecimenCommands;
import org.Cryptic.Robot;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous
public class transferTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Robot robot = new Robot();
        robot.initialize(this);

        double t = 23.5;
        Pose2d initialPose = new Pose2d((t * 1.5 - 2.75), (t * 2.5 + 2.75), Math.toRadians(270));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder driveToScore = drive.actionBuilder(initialPose)
                .stopAndAdd(robot.specimenActions.specimenScore(robot))
                .waitSeconds(1)
                .afterTime(0.0, robot.specimenActions.specimenRelease(robot))
                .afterTime(5, robot.specimenActions.specimenIntake(robot))
                .waitSeconds(0.2)
                .lineToY(50);
                /*
                .stopAndAdd(robot.autoActions.extendActiveIntake2(robot,350))
                .waitSeconds(2)
                .stopAndAdd(robot.autoActions.ActiveIntakeRun(robot))
                .waitSeconds(4)
                .stopAndAdd(robot.autoActions.extendActiveIntake2(robot,550))
                .waitSeconds(6)
                .stopAndAdd(robot.autoActions.ActiveIntakeUp(robot));
                 */

        Action driveToScoreA = driveToScore.build();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(
                                driveToScoreA
                        ),
                        robot.specimenActions.robotUpdate(robot)
                )

        );
    }
}
