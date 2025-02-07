package org.Cryptic.Subsystems;

import static java.lang.Thread.sleep;

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

    public static final double maxExtend = .25;
    public static final double minExtend = .96;

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

        robot.clawArm.clawServo.setPosition(robot.clawArm.clawCloseValue);

        defaultPos();
        claw.closeCLaw();
        update();
        fullRetract();
        sleep(700);

    }

    private void initTime(){
        startTime = System.currentTimeMillis();
    }

    public boolean hasBeenTime(int mili){
        return System.currentTimeMillis() - startTime >= mili;
    }



    public void setExtend(int extend) {
        double val = Range.scale(extend,0.0,100.0,maxExtend,minExtend);
        extendServo.setPosition(Range.clip(val, minExtend,maxExtend));
    }

    public void fullExtend(){
        setExtend(100);
    }

    public void fullRetract(){
        setExtend(0);
    }

    public void defaultPos(){

        armAngle = 90;
        clawAngle = 120;
        clawYaw = 0;
        fullRetract();

    }



    public void intakeSpecimenDefaultPosition() {
        armAngle = 3;
        clawAngle = 0;


        clawYaw = 0;
        claw.openClaw();
        //fullExtend();
        robot.verticalSlides.retractSlides();

    }

    public int outtakeSampleState = 0;
    public void outtakeSample(){

        if(outtakeSampleState == 0){
            armAngle = 50; //30
            clawAngle = 0;
            clawYaw = 0;
            fullRetract();
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

    public int intakeClawSampleState = 0;

    public void clawSpinRight(){

        clawYaw-=1;

        if(clawYaw<-2){
            clawYaw = -2;
        }

    }
    public void clawSpinLeft(){
        clawYaw+=1;

        if(clawYaw >2){
            clawYaw =2;
        }

    }

    public void intakeClawSample(){

        if(intakeClawSampleState>4){
            intakeClawSampleState = 0;
        }

        if(intakeClawSampleState==0){

            clawAngle = 90;
            fullExtend();
            robot.clawArm.closeCLaw();
            robot.verticalSlides.retractSlides();
        }
        if(intakeClawSampleState ==1){
            armAngle = 151;
            robot.clawArm.openClaw();
            initTime();
        }
        if(intakeClawSampleState==2){
            clawAngle = 20;
        }

        else if(intakeClawSampleState == 3){
            defaultPos();
            fullRetract();
        }else if(intakeClawSampleState ==4){
            intakeClawSampleState = -1;
            intakeClawSampleSequenceState = 0;
            currentActionSequence = "";
        }


        intakeClawSampleState+=1;
    }

    public int intakeClawSampleSequenceState = 0;

    public void intakeClawSequence(){



        if(intakeClawSampleSequenceState ==0 && currentActionSequence.equals("Intake Claw Sample") ){
            robot.clawArm.openClaw();
            armAngle  +=8;
            clawAngle -=17;
            intakeClawSampleSequenceState +=1;
            initTime();
        }
        else if(intakeClawSampleSequenceState ==1 && hasBeenTime(175)){
            robot.clawArm.closeCLaw();
            intakeClawSampleSequenceState +=1;
            initTime();
        }

        else if(intakeClawSampleSequenceState ==2 && hasBeenTime(200)){
            armAngle-=8;
            clawAngle+=15;
            clawYaw = 0;
            intakeClawSampleSequenceState+=1;
        }
        else if(intakeClawSampleSequenceState == 3){
            intakeClawSampleSequenceState =0;
            currentActionSequence = "";
        }
    }


    public int outtakeSampleSequenceState = 0;

    public void outtakeSampleSequence(){



        if(outtakeSampleSequenceState ==0 && currentActionSequence.equals( "Outtake Sample")){
            armAngle = 40; //15
            clawAngle = 0;
            clawYaw = 0;
            fullRetract();
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
        if(currentActionSequence.equals("Intake Claw Sample")){
            intakeClawSequence();
        }


        claw.setGripperPos(gripperAngle);

        claw.setArmAngle(armAngle+5);

        claw.setClawPos(clawAngle,clawYaw);



    }

    /*
    public void specimenScoreAuto () throws InterruptedException {
        armAngle = 10;
        clawAngle = 65;
        clawYaw = 0;
        fullRetract();
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