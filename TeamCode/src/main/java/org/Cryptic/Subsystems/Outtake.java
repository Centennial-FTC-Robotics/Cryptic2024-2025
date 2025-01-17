package org.Cryptic.Subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.Cryptic.Subsystem;

@Config
public class Outtake extends Subsystem {
    public Servo extendServo;

    public final double maxExtend = .22;
    public final double minExtend = .95;

    public boolean extendValue;

    public ClawArm claw;


    public int armAngle;

    public int clawAngle;

    public int clawYaw;

    private LinearOpMode opmode;

    public Outtake(ClawArm a){
        this.claw = a;
    }


    public void init(LinearOpMode opmode) throws InterruptedException {
        this.opmode = opmode;

        extendServo = opmode.hardwareMap.get(Servo.class, "extendServo");

        defaultPos();
        claw.openClaw();

        setExtend(false);





    }


    public void setExtend(boolean extended) {
        if (extended) {
            extendServo.setPosition(maxExtend);
        } else {
            extendServo.setPosition(minExtend);
        }
    }

    public void defaultPos(){

        armAngle = 90;
        clawAngle = 90;
        clawYaw = 0;

    }
    private int specimenState = 0;
    public void intakeSpecimenPos(){
        specimenState+=1;
        if(specimenState>5){
            specimenState = 0;
        }

        if(specimenState==0) {
            armAngle = 6;
            clawAngle = 72;
            clawYaw = 0;
            setExtend(false);
            claw.openClaw();
            robot.verticalSlides.retractSlides();
        }
        else if(specimenState == 1){
            armAngle = 6;
            clawAngle = 72;
            clawYaw = 0;
            setExtend(false);
            claw.closeCLaw();
        }
        else if(specimenState == 2){
            armAngle = 100;
            clawAngle = 150;
            clawYaw = 0;
            setExtend(true);
            claw.closeCLaw();
        }
        else if(specimenState == 3){
            setExtend(true);
            claw.closeCLaw();
            robot.verticalSlides.slidesTarget = 850;
            clawAngle = 90;
            armAngle = 121;
        }
        else if(specimenState == 4){
            robot.verticalSlides.slidesTarget = 990;

        }else if(specimenState == 5){
            claw.openClaw();
        }



    }

    public void update() {


        claw.setArmAngle(armAngle);

        claw.setClawPos(clawAngle,clawYaw);

    }
}
