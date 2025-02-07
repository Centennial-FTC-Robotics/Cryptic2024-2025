package org.Cryptic.Commands;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.Cryptic.Robot;
import org.Cryptic.Subsystem;

public class AutoActions extends Subsystem {

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

    public class specimenScore implements Action {
        private boolean initialized = false;
        private Robot robot = new Robot();

        public specimenScore (Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
                robot.specimenCommands.setSpecimenState(SpecimenCommands.specimenStates.POSITION_TO_SCORE);
                robot.specimenCommands.specimenUpdate();
            }
            packet.put("RAN SPECIMEN SCORE", "");

            packet.put("CURRENT SPECIMEN STATE", robot.specimenCommands.getSpecimenState());

            return false;
        }
    }

    public Action specimenScore(Robot robot) {
        return new specimenScore(robot);
    }

    public class specimenRelease implements Action {
        private boolean initialized = false;
        private Robot robot = new Robot();

        public specimenRelease (Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
                robot.specimenCommands.setSpecimenState(SpecimenCommands.specimenStates.RELEASE_SPECIMEN);
                robot.specimenCommands.specimenUpdate();
            }

            if (robot.specimenCommands.hasBeenTime(300)) {
                robot.specimenCommands.specimenUpdate();
            }

            packet.put("RAN SPECIMEN RELEASE", "");

            packet.put("CURRENT SPECIMEN STATE", robot.specimenCommands.getSpecimenState());

            return !(robot.specimenCommands.getSpecimenState() == SpecimenCommands.specimenStates.DEFAULT);
        }
    }

    public Action specimenRelease(Robot robot) {
        return new specimenRelease(robot);
    }

    public class specimenIntake implements Action {
        private boolean initialized = false;
        private Robot robot = new Robot();

        public specimenIntake (Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
                robot.specimenCommands.setSpecimenState(SpecimenCommands.specimenStates.DEFAULT);
                robot.specimenCommands.specimenUpdate();
            }

            if (robot.specimenCommands.hasBeenTime(100)) {
                robot.specimenCommands.specimenUpdate();
            }

            packet.put("RAN SPECIMEN INTAKE", "");

            packet.put("CURRENT SPECIMEN STATE", robot.specimenCommands.getSpecimenState());

            return !(robot.specimenCommands.getSpecimenState() == SpecimenCommands.specimenStates.CLOSE_CLAW);
        }
    }

    public Action specimenIntake(Robot robot) {
        return new specimenIntake(robot);
    }

    public class specimenGrab implements Action {
        private boolean initialized = false;
        private Robot robot = new Robot();

        int count = 0;

        public specimenGrab (Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
                robot.specimenCommands.setSpecimenState(SpecimenCommands.specimenStates.CLOSE_CLAW);
                robot.specimenCommands.specimenUpdate();
            }

            count++;
            packet.put("RAN SPECIMEN GRAB", count);

            packet.put("CURRENT SPECIMEN STATE", robot.specimenCommands.getSpecimenState());

            return !(robot.specimenCommands.hasBeenTime(300));
        }
    }

    public Action specimenGrab(Robot robot) {
        return new specimenGrab(robot);
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

}
