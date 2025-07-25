package org.Cryptic.Commands;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.Cryptic.Robot;
import org.Cryptic.Subsystem;
import org.Cryptic.Subsystems.Intake;

public class SpecimenActions extends Subsystem {

    public void init(LinearOpMode opmode) throws InterruptedException {

    }

    public class robotUpdate implements Action {
        private boolean initialized = false;
        private Robot robot = new Robot();

        public robotUpdate (Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
            }
            robot.intake.update();
            robot.intakeSlides.update();
            robot.outtake.update();
            robot.verticalSlides.update();

            // MUST BE SET TO TRUE IF YOU WANT IT TO RUN ALL THE TIME
            return true;
        }
    }

    public Action robotUpdate(Robot robot) {
        return new robotUpdate(robot);
    }

    public Action specimenScore(Robot robot) {
        return new SequentialAction(
                robot.baseActions.SetSpecimenState(robot, SpecimenCommands.specimenStates.POSITION_TO_SCORE)
        );
    }

    public Action specimenRelease(Robot robot) {
        return new SequentialAction(
                robot.baseActions.SetSpecimenState(robot, SpecimenCommands.specimenStates.RELEASE_SPECIMEN),
                new SleepAction(0.5),
                robot.baseActions.SetSpecimenState(robot, SpecimenCommands.specimenStates.RESET)
        );
    }

    public Action specimenIntake(Robot robot) {
        return new SequentialAction(
                robot.baseActions.SetSpecimenState(robot, SpecimenCommands.specimenStates.DEFAULT),
                new SleepAction(0.1),
                robot.baseActions.SetSpecimenState(robot, SpecimenCommands.specimenStates.PRIME)
            );
    }

    public Action specimenGrab(Robot robot) {
        return new SequentialAction(
                robot.baseActions.SetSpecimenState(robot, SpecimenCommands.specimenStates.CLOSE_CLAW),
                new SleepAction(0.3)
        );
    }

    public class liftOuttakeSlightly implements Action {
        private boolean initialized = false;
        private Robot robot = new Robot();
        private long startTime = System.currentTimeMillis();

        public liftOuttakeSlightly (Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
                robot.outtake.armAngle = 45;
                startTime = System.currentTimeMillis();
            }

            return !(System.currentTimeMillis() - startTime >= 100);
        }

    }

    public Action liftOuttakeSlightly(Robot robot) {
        return new liftOuttakeSlightly(robot);
    }

    public class extendActiveIntake implements Action {

        private boolean initialized = false;
        private Robot robot = new Robot();
        private long startTime = System.currentTimeMillis();

        public extendActiveIntake(Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
                robot.intakeSlides.setSlidesTarget(600);
                // I think its better to start spinning the rollers while extending
                robot.intake.setIntakePower(0.5);

                startTime = System.currentTimeMillis();
            }

            return false;
            //return !(System.currentTimeMillis() - startTime >= 500);
        }

    }
    public Action extendActiveIntake(Robot robot) {
        return new extendActiveIntake(robot);
    }

    public class extendActiveIntake2 implements Action {

        private boolean initialized = false;
        private Robot robot = new Robot();
        private long startTime = System.currentTimeMillis();
        private int distance;

        public extendActiveIntake2(Robot robot, int distance) {
            this.robot = robot;
            this.distance = distance;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
                robot.intakeSlides.setSlidesTarget(distance);

                startTime = System.currentTimeMillis();
            }

            return false;
            //return !(System.currentTimeMillis() - startTime >= 500);
        }

    }
    public Action extendActiveIntake2(Robot robot, int distance) {
        return new extendActiveIntake2(robot,distance);
    }


    public class retractActiveIntake implements Action {

        private boolean initialized = false;
        private Robot robot = new Robot();
        private long startTime = System.currentTimeMillis();

        public retractActiveIntake(Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
                robot.intakeSlides.setSlidesTarget(0);
                robot.intake.pitchState = Intake.PitchState.STOWED;
                robot.intake.setIntakePower(0);
                robot.intake.primed = false;
                startTime = System.currentTimeMillis();
            }

            return !(System.currentTimeMillis() - startTime >= 200);
        }
    }

    public Action retractActiveIntake(Robot robot) {
            return new retractActiveIntake(robot);
        }



    public class ActiveIntakeRun implements Action {

        private boolean initialized = false;
        private Robot robot = new Robot();
        private long startTime = System.currentTimeMillis();

        public ActiveIntakeRun(Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;

                robot.intake.pitchState = Intake.PitchState.DOWN;
                robot.intake.setIntakePitch(Intake.PitchState.DOWN);
                robot.intake.setIntakePower(1);
                startTime = System.currentTimeMillis();
            }

            return !(System.currentTimeMillis() - startTime >= 100);
        }

    }
    public Action ActiveIntakeRun(Robot robot) {
        return new ActiveIntakeRun(robot);
    }


    public class lowerActiveIntake implements Action {

        private boolean initialized = false;
        private Robot robot = new Robot();
        private long startTime = System.currentTimeMillis();

        public lowerActiveIntake(Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;

                robot.intake.pitchState = Intake.PitchState.DOWN;
                robot.intake.setIntakePitch(Intake.PitchState.DOWN);
                startTime = System.currentTimeMillis();
            }

            return !(System.currentTimeMillis() - startTime >= 100);
        }

    }
    public Action lowerActiveIntake(Robot robot) {
        return new lowerActiveIntake(robot);
    }


    public class ActiveIntakeExpel implements Action {

        private boolean initialized = false;
        private Robot robot = new Robot();
        private long startTime = System.currentTimeMillis();

        public ActiveIntakeExpel(Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;

                robot.intake.pitchState = Intake.PitchState.UP;
                robot.intake.setIntakePower(-0.8);
                startTime = System.currentTimeMillis();
            }

            return !(System.currentTimeMillis() - startTime >= 400);
        }

    }

    public Action ActiveIntakeExpel(Robot robot) {
        return new ActiveIntakeExpel(robot);
    }


    public class ActiveIntakeUp implements Action {

        private boolean initialized = false;
        private Robot robot = new Robot();
        private long startTime = System.currentTimeMillis();

        public ActiveIntakeUp(Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;

                robot.intake.pitchState = Intake.PitchState.UP;
                robot.intake.setIntakePower(0);
                startTime = System.currentTimeMillis();
            }

            return false;
        }

    }

    public Action ActiveIntakeUp(Robot robot) {
        return new ActiveIntakeUp(robot);
    }

}
