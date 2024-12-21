package org.Cryptic.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.Cryptic.Subsystem;
import org.Cryptic.util.Globals;

@Config
public class Intake extends Subsystem {
    public static double P = 0;
    public static double I = 0;
    public static double D = 0;
    public static int targetErrorThreshold = 0;
    public static double velErrorThreshold = 0;

    public static double slidesTarget;

    public static double maxSlideSpeed;

    public static double pitchDown = 0.0;
    public static double pitchUp = 0.0;
    public static double pitchStowed = 0.0;

    public static double primed = 0.0;
    public static double notPrimed = 0.0;


    public enum PitchState {
        DOWN, UP, STOWED
    }

    @Override
    public void init(LinearOpMode opmode) throws InterruptedException {
        // TODO: Initialize all components
        // TODO: Wait a second for servos to initialize

        setIntakePitch(PitchState.STOWED);
        setPrimeIntake(false);
        setIntakePower(0);
        setSlidesTarget(0);
    }
    public Globals.SampleColor getColor() {
        return null;
        // TODO: Return color that color sensor detects (unknown if no color is detected)
    }

    public void setIntakePitch(PitchState cool) {
        // TODO: Move the active intake servos based off pitch state
    }

    public void setPrimeIntake(boolean primed) {
        // TODO: Move servo to set up the intake for transfer to outtake
    }

    public void setIntakePower(double power) {
        // TODO: Continuous Rotation servo go spinny positive is intake negative is expel
    }

    public void setSlidesTarget (double value) {
        // TODO: Convert value (1.0 to 0.0) to encoder ticks for slides, and set that to PID target
    }

    public void update() {
        // TODO: Use PID to move slides and obey max speed
    }

    public boolean areSlidesFinished() {
        // TODO: Return whether slides are within certain threshold and motor velocity is close to 0
        return false;
    }

    // horizontal slides
    // pitching intake
    // actually intaking
    // priming
    // color detection
}