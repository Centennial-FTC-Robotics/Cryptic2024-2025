package org.Cryptic.Commands;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.Cryptic.Robot;
import org.Cryptic.Subsystem;

public class SampleAutoActions extends Subsystem {

    private long startTime;

    public void init(LinearOpMode opmode) throws InterruptedException {

    }

    private void initTime(){
        startTime = System.currentTimeMillis();
    }
    public boolean hasBeenTime(int mili){
        return System.currentTimeMillis() - startTime >= mili;
    }

    public class positionToScore implements Action {
        private boolean initialized = false;
        private Robot robot = new Robot();

        public positionToScore (Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
                robot.outtake.outtakeSampleState = 0;
            }

            robot.outtake.outtakeSample();

            robot.verticalSlides.slidesTarget = 2050;

            return false;
        }
    }

    public Action positionToScore(Robot robot) {
        return new positionToScore(robot);
    }

    public class dropSample implements Action {
        private boolean initialized = false;
        private Robot robot = new Robot();

        public dropSample (Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
                robot.outtake.outtakeSampleSequenceState = 0;
                robot.outtake.currentActionSequence = "Outtake Sample";
            }

            robot.outtake.outtakeSampleSequence();

            return !(robot.outtake.currentActionSequence.equals(""));
        }
    }

    public Action dropSample (Robot robot) {
        return new dropSample(robot);
    }

    public class reset implements Action {
        private boolean initialized = false;
        private Robot robot = new Robot();

        public reset (Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
            }
            robot.outtake.defaultPos();
            robot.verticalSlides.retractSlides();
            return false;
        }
    }

    public Action reset (Robot robot) {
        return new reset(robot);
    }

    public class positionToIntake implements Action {
        private boolean initialized = false;
        private Robot robot = new Robot();

        public positionToIntake (Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
                robot.clawArm.closeCLaw();
                initTime();
            }

            robot.outtake.fullExtend();
            robot.verticalSlides.retractSlides();

            if (hasBeenTime(100)) {
                robot.outtake.clawAngle = 90;
                robot.outtake.armAngle = 151;
            }

            if (hasBeenTime(250)) {
                robot.clawArm.openClaw();
            }
            return !(hasBeenTime(350));
        }
    }

    public Action positionToIntake (Robot robot) {
        return new positionToIntake(robot);
    }

    public class grabSample implements Action {
        private boolean initialized = false;
        private Robot robot = new Robot();

        public grabSample (Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
                robot.outtake.armAngle  +=8;
                robot.outtake.clawAngle -=17;
                initTime();
            }

            if (hasBeenTime(200)) {
                robot.clawArm.closeCLaw();
            }

            return !(hasBeenTime(350));
        }
    }

    public Action grabSample (Robot robot) {
        return new grabSample(robot);
    }


    public class rotateClaw implements Action {
        private boolean initialized = false;
        private Robot robot = new Robot();
        private int angle = 0;

        public rotateClaw (Robot robot, int angle) {
            this.robot = robot;
            this.angle = angle;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
            }

            if (angle == 90) {
                robot.outtake.clawSpinLeft();
            } else if (angle == 180) {
                robot.outtake.clawSpinLeft();
            }

            if (angle == -90) {
                robot.outtake.clawSpinRight();
            } else if (angle == -180) {
                robot.outtake.clawSpinRight();
            }

            return false;
        }
    }

    public Action rotateClaw (Robot robot, int angle) {
        return new rotateClaw(robot, angle);
    }

}
