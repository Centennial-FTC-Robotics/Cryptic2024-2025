package org.Cryptic.Commands;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.Cryptic.Robot;
import org.Cryptic.Subsystem;
import org.Cryptic.Subsystems.Intake;

public class BaseActions extends Subsystem {
    public void init(LinearOpMode opmode) throws InterruptedException {

    }

    public class SetSpecimenState implements Action {
        private boolean initialized = false;
        private Robot robot = new Robot();
        private long startTime = System.currentTimeMillis();
        private double power = 0.0;
        private SpecimenCommands.specimenStates specimenState = null;

        public SetSpecimenState(Robot robot, SpecimenCommands.specimenStates specimenState) {
            this.robot = robot;
            this.specimenState = specimenState;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
                robot.specimenCommands.setSpecimenState(specimenState);
                robot.specimenCommands.specimenUpdate();
                startTime = System.currentTimeMillis();
            }

            return false;
        }
    }
    public Action SetSpecimenState (Robot robot, SpecimenCommands.specimenStates specimenState) {
        return new SetSpecimenState(robot, specimenState);
    }

    public class SetIntakePitch implements Action {
        private boolean initialized = false;
        private Robot robot = new Robot();
        private long startTime = System.currentTimeMillis();
        private Intake.PitchState pitchState = Intake.PitchState.STOWED;

        public SetIntakePitch(Robot robot, Intake.PitchState pitchState) {
            this.robot = robot;
            this.pitchState = pitchState;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
                robot.intake.pitchState = pitchState;
                startTime = System.currentTimeMillis();
            }

            return !(System.currentTimeMillis() - startTime >= 200);
        }
    }
    public Action SetIntakePitch (Robot robot, Intake.PitchState pitchState) {
        return new SetIntakePitch(robot, pitchState);
    }

    public class SetIntakePower implements Action {
        private boolean initialized = false;
        private Robot robot = new Robot();
        private long startTime = System.currentTimeMillis();
        private double power = 0.0;

        public SetIntakePower(Robot robot, double power) {
            this.robot = robot;
            this.power = power;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
                robot.intake.setIntakePower(power);
                startTime = System.currentTimeMillis();
            }

            return false;
        }
    }
    public Action SetIntakePower (Robot robot, double power) {
        return new SetIntakePower(robot, power);
    }
}
