package org.Cryptic.util;

import android.widget.GridLayout;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.Cryptic.Commands.SpecimenCommands;
import org.Cryptic.Robot;
import org.Cryptic.Subsystems.Intake;
import org.Cryptic.Subsystems.Outtake;
import org.Cryptic.Subsystems.VerticalSlides;

public class AutoActions {

    public class intakeSpecimen implements Action {
        private boolean initialized = false;

        Robot robot = new Robot();
        public intakeSpecimen (Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                robot.specimenCommands.setSpecimenState(SpecimenCommands.specimenStates.DEFAULT);
                initialized = true;
            }

            robot.outtake.update();
            robot.clawArm.update();
            robot.verticalSlides.update();

            robot.specimenCommands.specimenUpdate();

            return (robot.specimenCommands.getSpecimenState() == SpecimenCommands.specimenStates.POSITION_TO_SCORE);
        }
    }
}
