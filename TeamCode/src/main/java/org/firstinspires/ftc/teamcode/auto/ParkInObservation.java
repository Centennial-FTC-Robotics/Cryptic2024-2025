package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.Cryptic.Robot;
import org.Cryptic.Subsystems.DrivetrainNoRR;

@Autonomous (name="Strafe_To_Park")
public class ParkInObservation extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot();
        robot.initialize(this);

        waitForStart();

        long currentTime = System.currentTimeMillis();
        int start = 1200;

        while (System.currentTimeMillis() - currentTime < start) {
            robot.dtNoRR.drive(0, -0.5, 0);
        }
    }
}




