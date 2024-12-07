package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.Cryptic.Robot;

@Autonomous (name="BlueNetZone (USE RED ALL THE TIME)")
public class BlueNet extends LinearOpMode {

    private DcMotorEx driveBL;
    private DcMotorEx driveBR;
    private DcMotorEx driveFL;
    private DcMotorEx driveFR;

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot();
        robot.initialize(this);
        robot.dt.initNoRoadRunner(this);

        waitForStart();

        robot.imu.revIMU.reset();
        robot.dt.strafeDistance(12);

        robot.intake.defaultPosition();
        robot.slides.retractSlides();

        long currentTime = System.currentTimeMillis();

        while(System.currentTimeMillis()-currentTime<1000){
            robot.intake.update();
            robot.slides.update();
        }

        robot.dt.turnToHeading(25);
        // Raise slides up a little
        // Extend Hori slides

        robot.intake.fullExtend();

        while(robot.intake.extendAndUp){
            robot.intake.update();
        }

        robot.intake.intakeSample();

        while(robot.intake.startIntake){
            robot.intake.update();
        }

        robot.intake.fullRetract();

        while(robot.intake.extendAndDown){
            robot.intake.update();
        }
        // Arm Down
        // Claw Close
        // Arm Up
        // Retract Hori slides


        robot.dt.turnToHeading(150);
        //robot.dt.driveDistance(-7);
        // Raise slides to max
        robot.slides.incrementSlidePos(6);

        while(robot.slides.pos<2025){
            robot.slides.incrementSlidePos(1);
            robot.slides.update();
        }
        // Arm Back
        robot.intake.armAngle=270;

        long startTime = System.currentTimeMillis();

        while(System.currentTimeMillis()-startTime<1200){
            robot.intake.update();
        }
        // Diffy Back
        //robot.dt.driveDistance(7);
        // Claw Open

        long startsTime = System.currentTimeMillis();

        while(System.currentTimeMillis()-startsTime<200){
            robot.intake.openClaw();
        }

        //robot.dt.driveDistance(14);
        // Arm Forward


        robot.intake.defaultPosition();
        robot.slides.retractSlides();
        while(robot.intake.defaultPos || robot.slides.pos>200){
            robot.intake.update();
            robot.slides.update();
        }
        // Diffy Forward
        // Slides down
        //robot.dt.driveDistance(7);

        // Go Park
        robot.dt.turnToHeading(90);
        robot.dt.driveDistance(36);
        robot.dt.turnToHeading(180);
        robot.dt.driveDistance(12);


    }
}

