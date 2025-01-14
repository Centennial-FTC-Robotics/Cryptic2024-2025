package org.Cryptic.util;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.Cryptic.Subsystems.Intake;
import org.Cryptic.Subsystems.Outtake;
import org.Cryptic.Subsystems.VerticalSlides;

public class AutoActions {

    public class PrimeOuttakeForSpecimens implements Action {

        public PrimeOuttakeForSpecimens (Outtake outtake, VerticalSlides verticalSlides) {

        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            ;
            return true;
        }

    }
}
