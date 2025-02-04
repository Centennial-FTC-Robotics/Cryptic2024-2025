package org.Cryptic.Subsystems;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.Cryptic.Subsystem;

@Config
public class Outtake extends Subsystem {
    public Servo extendServo;

    public static final double maxExtend = .26;
    public static final double minExtend = .95;

    public boolean extendValue;

    public ClawArm claw;

    private long startTime;


    public static int armAngle;

    public static int clawAngle;

    public static int clawYaw;

    public static double gripperAngle = 0.66;

    public String currentActionSequence = "";


    private LinearOpMode opmode;

    public Outtake(ClawArm a){
        this.claw = a;
    }

    public void init(LinearOpMode opmode) throws InterruptedException {
        this.opmode = opmode;

        extendServo = opmode.hardwareMap.get(Servo.class, "extendServo");

        defaultPos();
        update();
        setExtend(false);
        claw.closeCLaw();
        sleep(700);

    }

    private void initTime(){
        startTime = System.currentTimeMillis();
    }

    public boolean hasBeenTime(int mili){
        return System.currentTimeMillis() - startTime >= mili;
    }



    public void setExtend(boolean extended) {
        if (extended) {
            extendServo.setPosition(maxExtend);
        } else {
            extendServo.setPosition(minExtend);
        }
    }

    public void fullExtend(){
        extendServo.setPosition(maxExtend-.05);
    }

    public void defaultPos(){

        armAngle = 90;
        clawAngle = 120;
        clawYaw = 0;
        setExtend(false);

    }



    public void intakeSpecimenDefaultPosition() {
        armAngle = 140;
        clawAngle = 0;


        clawYaw = 0;
        claw.openClaw();
        //setExtend(true);
        robot.verticalSlides.retractSlides();

    }

    int outtakeSampleState = 0;
    public void outtakeSample(){

        if(outtakeSampleState == 0){
            armAngle = 50; //30
            clawAngle = 0;
            clawYaw = 0;
            setExtend(false);
            claw.closeCLaw();


        }

        else if(outtakeSampleState == 1){
            currentActionSequence = "Outtake Sample";
        }

        else if(outtakeSampleState >= 2){
            currentActionSequence = "";
            outtakeSampleState = 0;
            outtakeSampleSequenceState = 0;



        }


        outtakeSampleState +=1;


    }

    int outtakeSampleSequenceState = 0;

    private void outtakeSampleSequence(){



        if(outtakeSampleSequenceState ==0 && currentActionSequence.equals( "Outtake Sample")){
            armAngle = 40; //15
            clawAngle = 0;
            clawYaw = 0;
            setExtend(false);
            claw.closeCLaw();

            outtakeSampleSequenceState +=1;
            initTime();
        }

        if(outtakeSampleSequenceState == 1 && hasBeenTime(150)){
            claw.openClaw();

            outtakeSampleSequenceState +=1;
            initTime();
        }

        if(outtakeSampleSequenceState == 2 && hasBeenTime(150)){
            armAngle = 30;

            outtakeSampleSequenceState +=1;
            initTime();
        }

        if(outtakeSampleSequenceState == 3 && hasBeenTime(200)){
            defaultPos();


            robot.verticalSlides.retractSlides();
            outtakeSampleSequenceState = 0;
            outtakeSampleState = 0;
            currentActionSequence = "";
            initTime();
        }


    }

    public void update() {

        if(currentActionSequence.equals("Outtake Sample")){
            outtakeSampleSequence();
        }


        claw.setGripperPos(gripperAngle);

        claw.setArmAngle(armAngle);

        claw.setClawPos(clawAngle,clawYaw);



    }

    /*
    public void specimenScoreAuto () throws InterruptedException {
        armAngle = 10;
        clawAngle = 65;
        clawYaw = 0;
        setExtend(false);
        claw.closeCLaw();
        robot.verticalSlides.slidesTarget = 400;

        specimenSequence = 2;
        currentActionSequence = "Outtake Specimen";
        specimenState = 2;
        update();

        Thread.sleep(1000);

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 1000) {
            robot.verticalSlides.update();
        }

        startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 500) {
            robot.dtNoRR.drive(0.5, 0, 0);
        }
        robot.dtNoRR.drive(0,0,0);

        startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 1000) {
            defaultPos();
            update();
            robot.verticalSlides.retractSlides();
            robot.verticalSlides.update();
        }

    }
    */

}