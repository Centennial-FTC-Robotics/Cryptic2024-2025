package org.Cryptic.Subsystems;

import com.arcrobotics.ftclib.hardware.RevIMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.Cryptic.Subsystem;

public class IMU extends Subsystem {

    public static RevIMU revIMU;

    public void init(LinearOpMode opmode) {

        revIMU = new RevIMU(opmode.hardwareMap);
        revIMU.init();
    }

}