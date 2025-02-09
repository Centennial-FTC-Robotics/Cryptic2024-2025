package org.Cryptic.Commands;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.Cryptic.Robot;
import org.Cryptic.Subsystem;

public class SampleActions extends Subsystem {

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
                robot.outtake.outtakeSample();
                initTime();
            }


            robot.verticalSlides.slidesTarget = 2050;

            return !(hasBeenTime(300));
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

            if (hasBeenTime(150)) {
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
                robot.outtake.armAngle  +=12;
                robot.outtake.clawAngle -=19;
                initTime();
            }

            if (hasBeenTime(300)) {
                robot.clawArm.closeCLaw();
            }

            if (hasBeenTime(450)) {
                robot.outtake.armAngle -= 12;
                robot.outtake.clawAngle += 19;
                robot.outtake.clawYaw = 0;
            }

            return !(hasBeenTime(500));
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
                initTime();
            }

            // RIGHT SPINS IT CLOCKWISE I THINK
            if (angle == 45) {
                robot.outtake.clawSpinRight();
            } else if (angle == 90) {
                robot.outtake.clawSpinRight();
            }

            if (angle == -45) {
                robot.outtake.clawSpinLeft();
            } else if (angle == -90) {
                robot.outtake.clawSpinLeft();

            }

            return !(hasBeenTime(500));
        }
    }

    public Action rotateClaw (Robot robot, int angle) {
        return new rotateClaw(robot, angle);
    }

    public class transfer implements Action {

        private boolean initialized = false;
        private Robot robot = new Robot();

        private int increment = 1;

        public transfer(Robot robot) {
            this.robot = robot;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
                robot.intake.incTransferIntakeOuttakeState();
                robot.intake.inc = 0;
                initTime();
            }
            if (hasBeenTime(400 * increment)) {
                robot.intake.incTransferIntakeOuttakeState();
                increment += 1;
            }
            return !(increment == 8);
            //return !(System.currentTimeMillis() - startTime >= 500);
        }

    }
    public Action transfer(Robot robot) {
        return new transfer(robot);
    }

}
