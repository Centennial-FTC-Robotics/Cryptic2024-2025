package org.Cryptic.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.Cryptic.Subsystem;

@Config
public class Outtake extends Subsystem {
    public Servo extendServo;

    public final double maxExtend = .95;
    public final double minExtend = .22;

    public boolean extendValue;

    public ClawArm claw;


    public int armAngle;

    public int clawAngle;

    public int clawYaw;

    private LinearOpMode opmode;


    public void init(LinearOpMode opmode) throws InterruptedException {
        this.opmode = opmode;

        claw = new ClawArm();

        claw.init(opmode);

        extendServo = opmode.hardwareMap.get(Servo.class, "extendServo");

        defaultPos();

    }


    public void setExtend(boolean extended) {
        if (extended) {
            extendServo.setPosition(maxExtend);
        } else {
            extendServo.setPosition(minExtend);
        }
    }

    public void defaultPos(){
        claw.openClaw();
        armAngle = 90;
        clawAngle = 90;
        clawYaw = -1;

    }



    public void update() {
        claw.openClaw();

        claw.setArmAngle(armAngle);

        claw.setClawPos(clawAngle,clawYaw);


    }



}
