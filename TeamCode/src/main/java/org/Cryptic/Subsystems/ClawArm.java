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
public class ClawArm extends Subsystem{

    public Servo clawServo;

    public Servo rightClawServo;
    public Servo leftClawServo;
    public Servo leftArmServo;
    public Servo rightArmServo;


    public static double gripperPos = 0.0;

    public boolean clawOpened;
    public final double clawCloseValue = .85;
    public final double clawOpenValue = 0.6;

    private LinearOpMode opmode;


    public void init(LinearOpMode opmode) throws InterruptedException {
        this.opmode = opmode;

        clawServo = opmode.hardwareMap.get(Servo.class, "clawServo");
        rightClawServo = opmode.hardwareMap.get(Servo.class, "leftClawServo");
        leftClawServo = opmode.hardwareMap.get(Servo.class, "rightClawServo");
        leftArmServo = opmode.hardwareMap.get(Servo.class, "leftArmServo");
        rightArmServo = opmode.hardwareMap.get(Servo.class, "rightArmServo");

        /*
        setClawPos(180,0);
        setGripperPos(clawOpenValue);
        setArmAngle(110);
         */


    }

    public void setGripperPos(double position) {
        clawServo.setPosition(Range.clip(position, clawOpenValue, clawCloseValue));
    }

    public void openClaw(){
        robot.outtake.gripperAngle = clawOpenValue;
        clawOpened=true;
    }
    public void closeCLaw(){
        robot.outtake.gripperAngle = clawCloseValue;
        clawOpened=false;
    }

    public boolean getClawOpenState(){
        return clawOpened;
    }
    public void setClawPos(int pitch,int spinCounter){
        pitch = Range.clip(pitch,0,130);
        double pitchVal = Range.scale(pitch,0.0,180.0,0.0,1.0);
        double spinVal = 0;
        if(spinCounter ==0){
            spinVal = 0;
        }
        else if(spinCounter == -1){
            spinVal = -.25;
        }
        else if(spinCounter == -2){
            spinVal = -.5;
        }
        else if(spinCounter ==1){
            spinVal = .25;
        }
        else if(spinCounter == 2){
            spinVal = .5;
        }
        leftClawServo.setPosition(Range.clip((1-pitchVal)+spinVal,0,1));
        rightClawServo.setPosition(Range.clip(((pitchVal)) + spinVal,0,1));
    }


    public void setArmAngle(int angle){
        int maxValue = 270;


        angle = Range.clip(angle,0,maxValue);
        double armValue = Range.scale(angle,0,270,0,1);

        rightArmServo.setPosition(armValue);
        leftArmServo.setPosition(armValue);
    }

    public class OpenClaw implements Action {
        @Override
        public boolean run (@NonNull TelemetryPacket telemetryPacket) {
            openClaw();
            return true;
        }
    }
}
