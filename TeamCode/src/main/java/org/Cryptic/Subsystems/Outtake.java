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
        Thread.sleep(700);


    }

    private void initTime(){
        startTime = System.currentTimeMillis();
    }

    private boolean hasBeenTime(int mili){
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
        clawAngle = 180;
        clawYaw = 0;
        setExtend(false);

    }



    public void intakeSpecimenDefaultPosition(){
        armAngle = 140;
        clawAngle = 90;
        clawYaw = 0;
        setExtend(true);
        claw.openClaw();
        robot.verticalSlides.retractSlides();

    }
    private int specimenSequence = 0;

    public void intakeSpecimen(){


        if(specimenSequence==0) {
            intakeSpecimenDefaultPosition();
        }

        else if(specimenSequence == 1){
            currentActionSequence = "Intake Specimen";
        }
        else if(specimenSequence == 2){
            currentActionSequence = "Outtake Specimen";
        }

        specimenSequence +=1;

        if(specimenSequence>3){
            specimenSequence = 0;
            defaultPos();
            robot.verticalSlides.retractSlides();
            specimenState = 0;
        }


    }

    private int specimenState = 0;
    private void intakeSpecimenSequence(){


        if(specimenState>1){
            specimenState = 0;
        }

        if(specimenState==0 && currentActionSequence.equals("Intake Specimen")) {
            intakeSpecimenDefaultPosition();
            claw.closeCLaw();
            fullExtend();
            specimenState +=1;
            initTime();

        }
        if (specimenState == 1 && hasBeenTime(100)){
            armAngle = 10;
            clawAngle = 155;
            clawYaw = 0;
            setExtend(false);
            claw.closeCLaw();
            robot.verticalSlides.slidesTarget = 400;

            specimenState += 1;
            initTime();

            currentActionSequence = "";

        }


    }

    private void outtakeSpecimenSequence(){



        if(specimenState==2 && currentActionSequence.equals("Outtake Specimen")) {
            robot.verticalSlides.slidesTarget += 105;
            armAngle+=15;
            clawAngle-=10;
            specimenState +=1;
            initTime();

        }
        if(specimenState == 3 && hasBeenTime(1100)){
            claw.openClaw();
            specimenState+=1;
            initTime();
        }
        if (specimenState == 4 && hasBeenTime(250)){

            initTime();
            robot.verticalSlides.retractSlides();

            specimenState = 0;
            currentActionSequence = "";

            intakeSpecimen();

        }

    }


    int outtakeSampleState = 0;
    public void outtakeSample(){

        if(outtakeSampleState == 0){
            armAngle = 30;
            clawAngle = 72;
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
            armAngle = 15;
            clawAngle = 72;
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

        if(currentActionSequence.equals("Intake Specimen")){
            intakeSpecimenSequence();
        }

        if(currentActionSequence.equals("Outtake Specimen")){
            outtakeSpecimenSequence();
        }

        if(currentActionSequence.equals("Outtake Sample")){
            outtakeSampleSequence();
        }


        claw.setGripperPos(gripperAngle);

        claw.setArmAngle(armAngle);

        claw.setClawPos(clawAngle,clawYaw);

    }
}