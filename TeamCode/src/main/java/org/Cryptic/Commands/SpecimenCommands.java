package org.Cryptic.Commands;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.Cryptic.Robot;
import org.Cryptic.Subsystem;
import org.Cryptic.Subsystems.Outtake;
import org.Cryptic.util.Globals;

@Config
public class SpecimenCommands extends Subsystem {

    private long startTime;

    public enum RobotActions {
        IDLE,
        INTAKE_SPECIMEN,
        SCORE_SPECIMEN
    }

    public enum specimenStates {
        DEFAULT,
        EXTEND,
        POSITION_CLAW,
        POSITION_TO_SCORE,
        OPEN_CLAW,
        RESET
    }

    specimenStates specimenState;
    RobotActions currentAction;

    public void init(LinearOpMode opmode) throws InterruptedException {
        specimenState = specimenStates.DEFAULT;
        currentAction = RobotActions.INTAKE_SPECIMEN;
    }

    private void initTime(){
        startTime = System.currentTimeMillis();
    }
    public boolean hasBeenTime(int mili){
        return System.currentTimeMillis() - startTime >= mili;
    }

    public void setSpecimenState (specimenStates cool) {
        specimenState = cool;
    }

    public specimenStates getSpecimenState () {
        return specimenState;
    }

    public RobotActions getCurrentAction () {
        return currentAction;
    }


    public void specimenUpdate() {
        switch (specimenState) {
            case DEFAULT:
                initTime();
                robot.outtake.extendServo.setPosition(0.6);
                specimenState = specimenStates.EXTEND;
                break;
            case EXTEND:
                if (hasBeenTime(100)) {
                    robot.outtake.intakeSpecimenDefaultPosition();
                    specimenState = specimenStates.POSITION_CLAW;
                }
                break;
            case POSITION_CLAW:
                if (hasBeenTime(200)) {
                    robot.outtake.armAngle = 9;
                    robot.outtake.clawAngle = 65;
                    robot.outtake.clawYaw = 0;
                    robot.outtake.setExtend(false);
                    robot.outtake.claw.closeCLaw();
                    robot.verticalSlides.slidesTarget = 403;

                    specimenState = specimenStates.POSITION_TO_SCORE;
                }
                break;
            case POSITION_TO_SCORE:
                robot.verticalSlides.slidesTarget += 105;
                robot.outtake.armAngle += 15;
                robot.outtake.clawAngle -= 11;
                specimenState = specimenStates.OPEN_CLAW;
                initTime();
                break;
            case OPEN_CLAW:
                if (hasBeenTime(1100)) {
                    robot.outtake.claw.openClaw();
                    specimenState = specimenStates.RESET;
                    initTime();
                }
                break;
            case RESET:
                if (hasBeenTime(250)) {
                    initTime();
                    robot.verticalSlides.retractSlides();

                    specimenState = specimenStates.DEFAULT;
                }
                break;
        }
    }
}


            /*
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
            specimenState = specimenStates.DEFAULT;
        }
        */