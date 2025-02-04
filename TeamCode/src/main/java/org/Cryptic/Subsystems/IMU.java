package org.Cryptic.Subsystems;

import com.arcrobotics.ftclib.hardware.RevIMU;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.Cryptic.Subsystem;

public class IMU extends Subsystem {

    public static RevIMU revIMU;

    public void init(LinearOpMode opmode) {

        /*
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection = RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD;

        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        */
        revIMU = new RevIMU(opmode.hardwareMap);
        revIMU.init();
        //revIMU.invertGyro();

    }

}