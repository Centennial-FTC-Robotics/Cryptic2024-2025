package org.Cryptic;

import org.Cryptic.Subsystems.ClawArm;
import org.Cryptic.Subsystems.Drivetrain;
import org.Cryptic.Subsystems.IMU;
import org.Cryptic.Subsystems.Outtake;
import org.Cryptic.Subsystems.VerticalSlides;
import org.Cryptic.Subsystems.Intake;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Robot {
    public Drivetrain dt = new Drivetrain();
    public VerticalSlides verticalSlides = new VerticalSlides();
    public Intake intake = new Intake();
    public ClawArm clawArm = new ClawArm();
    public Outtake outtake = new Outtake(clawArm);
    public IMU imu = new IMU();

    public Subsystem[] subsystems = new Subsystem[] {
            dt,
            verticalSlides,
            intake,
            clawArm,
            outtake
            //imu
    };

    public void initialize(LinearOpMode opmode) throws InterruptedException {

        for(Subsystem subsystem : subsystems) {
            subsystem.preInit(opmode, this);
        }
    }
}
