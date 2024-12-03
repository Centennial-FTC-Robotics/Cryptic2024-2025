package org.Cryptic;

import org.Cryptic.Subsystems.Drivetrain;
import org.Cryptic.Subsystems.Slides;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Robot {
    public Drivetrain dt = new Drivetrain();
    public Slides slides = new Slides();

    public Subsystem[] subsystems = new Subsystem[] {
            dt,
            slides
    };

    public void initialize(LinearOpMode opmode) throws InterruptedException {

        for(Subsystem subsystem : subsystems) {
            subsystem.init(opmode);
        }
    }
}
