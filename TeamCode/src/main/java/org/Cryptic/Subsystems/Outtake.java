package org.Cryptic.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.Cryptic.Subsystem;

@Config
public class Outtake extends Subsystem {

    public Servo clawServo;
    public Servo extendServo;
    public Servo rightClawServo;
    public Servo leftClawServo;
    public Servo leftArmServo;
    public Servo rightArmServo;

    public final double maxExtend = .95;
    public final double minExtend = .22;

    public boolean extendValue;
    public OuttakePitchState armAngle;
    public int clawRotate;

    // Claw stuff
    public static double clawOpenVal = 0.45, clawClosedVal = 0.80;

    public int spinCounter = 0;

    private LinearOpMode opmode;

    public enum OuttakePitchState {
        FRONT,
        SAMPLE,
        BACK
    }

    public void init(LinearOpMode opmode) throws InterruptedException {
        this.opmode = opmode;

        clawServo = opmode.hardwareMap.get(Servo.class, "clawServo");
        rightClawServo = opmode.hardwareMap.get(Servo.class, "leftClawServo");
        leftClawServo = opmode.hardwareMap.get(Servo.class, "rightClawServo");
        leftArmServo = opmode.hardwareMap.get(Servo.class, "leftArmServo");
        rightArmServo = opmode.hardwareMap.get(Servo.class, "rightArmServo");
        extendServo = opmode.hardwareMap.get(Servo.class, "extendServo");

        rightClawServo.setDirection(Servo.Direction.REVERSE);

        clawGrab(false);
    }

    public void clawGrab(boolean grab){
        if(grab){
            clawServo.setPosition(clawClosedVal);
        }else{
            clawServo.setPosition(clawOpenVal);
        }
    }

    public void setExtend(boolean extended) {
        if (extended) {
            extendServo.setPosition(maxExtend);
        } else {
            extendServo.setPosition(minExtend);
        }
    }

    public void setArmAngle(OuttakePitchState pos) {
        if (pos == OuttakePitchState.FRONT) {
            leftArmServo.setPosition(0);
            rightArmServo.setPosition(0);
        }
        else if (pos == OuttakePitchState.SAMPLE) {
            leftArmServo.setPosition(0.7);
            rightArmServo.setPosition(0.7);
        } else if (pos == OuttakePitchState.BACK) {
            leftArmServo.setPosition(1.0);
            rightArmServo.setPosition(1.0);
        }
    }

    public void setClawPos(int pitch){
        pitch = Range.clip(pitch,0,180);
        Double pitchVal = Range.scale(pitch,0.0,180.0,0.0,1.0);
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

    public void update() {
        spinCounter = Math.max(-2,spinCounter);
        spinCounter = Math.min(2,spinCounter);
        setExtend(extendValue);
        setArmAngle(armAngle);
        setClawPos(clawRotate);
    }



}
