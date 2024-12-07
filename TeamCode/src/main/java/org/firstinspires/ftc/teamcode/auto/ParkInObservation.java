package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.Cryptic.Robot;

@Autonomous (name="ParkInObservation")
public class ParkInObservation extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot();
        robot.initialize(this);
        robot.dt.initNoRoadRunner(this);

        waitForStart();

        robot.dt.driveDistance(24);
    }
}
