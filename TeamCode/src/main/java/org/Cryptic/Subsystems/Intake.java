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

    private boolean startIntake;

    public final double maxExtend = .95;
    public final double minExtend = .22;

    public final double clawCloseValue = .13;
    public final double clawOpenValue = 0;
    public boolean clawOpened = false;

    public double extendValue;

    public int armAngle;



    public int clawRotate;
    public int clawSpin;

    private boolean defaultPos;






    public void init(LinearOpMode opmode) throws InterruptedException {
        this.opmode = opmode;


        clawServo = opmode.hardwareMap.get(Servo.class, "clawServo");
        rightClawServo = opmode.hardwareMap.get(Servo.class, "leftClawServo");
        leftClawServo = opmode.hardwareMap.get(Servo.class, "rightClawServo");
        leftArmServo = opmode.hardwareMap.get(Servo.class, "leftArmServo");
        rightArmServo = opmode.hardwareMap.get(Servo.class, "rightArmServo");
        extendServo = opmode.hardwareMap.get(Servo.class, "extendServo");

        closeCLaw();

        defaultDiff();

        fullRetract();
        setArmAngle(10);


        Thread.sleep(700);


    }

    public void straightDiff(){

        clawRotate = 0;
    }

    public void defaultDiff(){

        clawRotate = 70;
    }

    public void defaultPosition(){
        defaultPos=true;

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

            spinVal+=.05;


            leftClawServo.setPosition(Range.clip((1-pitchVal)+spinVal,0,1));
            rightClawServo.setPosition(Range.clip(((pitchVal)) + spinVal,0,1));


    }

    public void setArmAngle(int angle){
        int maxValue = 270;



        if (robot.slides.pos<1000 || extendValue>10){
            maxValue = 40;
        }





        angle = Range.clip(angle,0,maxValue);
        double armValue = Range.scale(angle,0,270,0,1);

        rightArmServo.setPosition(armValue);
        leftArmServo.setPosition(1-armValue);
    }

    public int getArmAngle(){

        return (int)Range.scale(rightArmServo.getPosition(),0,1,0,180);
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

    public void intakeSample(){

        startIntake=true;
    }



    public void openClaw(){
        clawServo.setPosition(clawOpenValue);
        clawOpened=true;
    }
    public void closeCLaw(){
        clawServo.setPosition(clawCloseValue);
        clawOpened=false;
    }

    public void intakePosition()
    {
        armAngle=25;
        clawRotate = 55;

    }

    public void intakeExtendPosition(){
        armAngle=22;
        clawRotate = 58;
    }
    // Class-level variables
    private long defaultPosStartTime = 0;

    private long intakeStartTime = 0;

    private int intakePos = 0;

    public int spinCounter = 0;

    public void update() {

        spinCounter = Math.max(-2,spinCounter);
        spinCounter = Math.min(2,spinCounter);




        if(startIntake){

            if(robot.slides.pos>300){
                startIntake=false;
                return;
            }

            if(intakeStartTime==0){

                defaultDiff();
                armAngle = 10;
                openClaw();

                intakeStartTime = System.currentTimeMillis();


                intakePos+=1;

            }

            if(System.currentTimeMillis() - intakeStartTime>300){
                intakePos+=1;
                intakeStartTime=System.currentTimeMillis();
            }

            if(intakePos==2){

                if(extendValue<30) {
                    intakePosition();
                }else{
                    intakeExtendPosition();
                }
            }

            if(intakePos==3){
                closeCLaw();
            }
            if(intakePos==4){
                defaultDiff();
                armAngle = 10;

                spinCounter=0;
                intakePos = 0;
                intakeStartTime =0;
                startIntake = false;
            }




        }
        if (defaultPos) {
            if (defaultPosStartTime == 0) {
                spinCounter =0;
                // Start the timer when entering default position mode
                defaultPosStartTime = System.currentTimeMillis();
                armAngle = 10; // Set arm angle to 10
                extendValue = 0; // Retract the extension
                defaultDiff(); // Set claw position
                setArmAngle(10);
            }
            if (robot.slides.pos > 1800) {
                robot.slides.retractSlides();
                defaultPos = false; // Reset default position mode
                defaultPosStartTime = 0; // Reset timer
            }

            // Wait for 2 seconds before retracting slides
            else if (System.currentTimeMillis() - defaultPosStartTime > 700) {

                    setArmAngle(10);
                    robot.slides.retractSlides();
                    defaultPos = false; // End default position mode
                    defaultPosStartTime = 0; // Reset timer

            }
        }

        if (armAngle > 200) {
            straightDiff();
        }

        extended = !(extendValue < 5);
        this.setExtend(extendValue);
        this.setArmAngle(armAngle);
        this.setClawPos(clawRotate);
    }



}
