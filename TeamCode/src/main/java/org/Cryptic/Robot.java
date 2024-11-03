package org.Cryptic;

import org.Cryptic.Subsystems.Drivetrain;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Robot {
    public Drivetrain dt = new Drivetrain();

    public Subsystem[] subsystems = new Subsystem[] {
            dt
    };

    public void initialize(LinearOpMode opmode) throws InterruptedException {

        for(Subsystem subsystem : subsystems) {
            subsystem.init(opmode);
        }
    }
}
