package org.Cryptic;

import org.Cryptic.Subsystems.Drivetrain;
import org.Cryptic.Subsystems.Slides;
import org.Cryptic.Subsystems.Intake;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Robot {
    public Drivetrain dt = new Drivetrain();
    public Slides slides = new Slides();
    public Intake intake = new Intake();

    public Subsystem[] subsystems = new Subsystem[] {
            dt,
            slides,
            intake,
    };

    public void initialize(LinearOpMode opmode) throws InterruptedException {

        for(Subsystem subsystem : subsystems) {
            subsystem.preInit(opmode, this);
        }
    }
}