package org.Cryptic.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.Cryptic.Subsystem;

public class Intake extends Subsystem {
    private LinearOpMode opmode;

    public Servo extendServo;
    public Servo clawServo;
    public Servo rightClawServo;
    public Servo leftClawServo;
    public Servo leftArmServo;
    public Servo rightArmServo;

    public boolean extended = false;
    public final double maxExtend = .95;
    public final double minExtend = .22;

    public final double clawCloseValue = .4;
    public final double clawOpenValue = .2;
    public boolean clawOpened = false;

    public double extendValue;




    public void init(LinearOpMode opmode) throws InterruptedException {
        this.opmode = opmode;


        clawServo = opmode.hardwareMap.get(Servo.class, "clawServo");
        rightClawServo = opmode.hardwareMap.get(Servo.class, "leftClawServo");
        leftClawServo = opmode.hardwareMap.get(Servo.class, "rightClawServo");
        leftArmServo = opmode.hardwareMap.get(Servo.class, "leftArmServo");
        rightArmServo = opmode.hardwareMap.get(Servo.class, "rightArmServo");
        extendServo = opmode.hardwareMap.get(Servo.class, "extendServo");

        closeCLaw();

        leftClawServo.setPosition(.3);

        rightClawServo.setPosition(.7);

        fullRetract();
        setArmAngle(0);


        Thread.sleep(700);


    }

    public void setArmAngle(int angle){
        int maxValue = 270;

        if (!robot.slides.canOuttake){
            maxValue = 60;
        }


        angle = Range.clip(angle,0,maxValue);
        double armValue = Range.scale(angle,0,270,0,1);

        rightArmServo.setPosition(armValue);
        leftArmServo.setPosition(1-armValue);
    }




    public void setExtend(double extend){
        extend = Range.clip(extend,0.0,100.0);
        double extendFinalValue = Range.scale(extend,0.0,100.0,minExtend,maxExtend);
        extendServo.setPosition(extendFinalValue);
    }

    public void fullExtend(){
        extendValue=100.0;
    }

    public void fullRetract(){
        extendValue=0.0;
        extended=false;
    }

    public void openClaw(){
        clawServo.setPosition(clawOpenValue);
        clawOpened=true;
    }
    public void closeCLaw(){
        clawServo.setPosition(clawCloseValue);
        clawOpened=false;
    }

    public void update(){

        extended= !(extendValue < 5);
        this.setExtend(extendValue);


    }


}
