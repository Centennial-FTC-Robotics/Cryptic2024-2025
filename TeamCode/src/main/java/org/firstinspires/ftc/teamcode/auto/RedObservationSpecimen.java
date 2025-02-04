package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.Cryptic.Robot;
import org.Cryptic.Subsystems.DrivetrainNoRR;

@Config
@Autonomous (name = "Red_Ob_Specimen")
public class RedObservationSpecimen extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot();
        robot.initialize(this);

        robot.outtake.claw.closeCLaw();

        waitForStart();
        robot.outtake.claw.closeCLaw();


        // Robot is facing high chamber

        // Drive forwards
        //robot.dtNoRR.driveDistance(-40);

        long currentTime = System.currentTimeMillis();
        int start = 1200;

        while(System.currentTimeMillis() - currentTime <start) {
            robot.dtNoRR.driveFL.setPower(-0.5);
            robot.dtNoRR.driveFR.setPower(-0.5);
            robot.dtNoRR.driveBL.setPower(-0.5);
            robot.dtNoRR.driveBR.setPower(-0.5);
        }
        robot.dtNoRR.drive(0, 0, 0);

        sleep(1000);

        /*
        robot.outtake.specimenScoreAuto();
*/
        currentTime = System.currentTimeMillis();

        while(System.currentTimeMillis() - currentTime <start - 500) {
            robot.dtNoRR.driveFL.setPower(0.5);
            robot.dtNoRR.driveFR.setPower(0.5);
            robot.dtNoRR.driveBL.setPower(0.5);
            robot.dtNoRR.driveBR.setPower(0.5);
        }

        currentTime = System.currentTimeMillis();

        while(System.currentTimeMillis() - currentTime < start) {
            robot.dtNoRR.drive(0, -0.5, 0);
        }

        /*

        robot.dtNoRR.driveDistance(-24);

        // MOVE SAMPLES TO OBSERVATION ZONE
        // dt.strafeDistance(24)

        // Strafe to the Observation Zone
        robot.dtNoRR.strafeDistance(55);
        */

    }

}
