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
        PRIME,
        CLOSE_CLAW,
        POSITION_TO_SCORE,
        RELEASE_SPECIMEN,
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
                robot.outtake.fullRetract();
                specimenState = specimenStates.PRIME;
                break;
            case PRIME:
                if (hasBeenTime(100)) {
                    robot.outtake.intakeSpecimenDefaultPosition();
                    specimenState = specimenStates.CLOSE_CLAW;
                    initTime();
                }
                break;
            case CLOSE_CLAW:
                robot.outtake.claw.closeCLaw();
                initTime();
                specimenState = specimenStates.POSITION_TO_SCORE;
                break;
            case POSITION_TO_SCORE:
                //if(hasBeenTime(400)) {
                robot.outtake.armAngle = 121;
                robot.outtake.clawAngle = 75;
                robot.outtake.clawYaw = 0;
                robot.outtake.fullExtend();
                robot.outtake.claw.closeCLaw();
                robot.verticalSlides.slidesTarget = 1345;

                specimenState = specimenStates.RELEASE_SPECIMEN;
                //}
                break;
            case RELEASE_SPECIMEN:
                specimenState = specimenStates.RESET;
                robot.outtake.claw.openClaw();
                robot.outtake.clawAngle -=15;
                robot.outtake.fullRetract();

                initTime();
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