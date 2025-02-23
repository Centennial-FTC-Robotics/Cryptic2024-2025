package org.firstinspires.ftc.teamcode.auto.testing;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.Cryptic.Robot;

@Config
@TeleOp
public class LimitSwitchTest extends LinearOpMode {
    DigitalChannel cool = null;
    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot();
        robot.initialize(this);

        cool = hardwareMap.get(DigitalChannel.class, "clawLimitSwitch");
        cool.setMode(DigitalChannel.Mode.INPUT);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Limit Switch State", cool.getState());
            telemetry.update();
        }


    }
}
