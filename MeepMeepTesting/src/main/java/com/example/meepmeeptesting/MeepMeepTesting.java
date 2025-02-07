package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.CompositeVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Vector;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 18)
                .build();

        double t = 23.5;


        // Sample Auto
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d((t*1.5-2.75), (t*2.5 + 2.75), Math.toRadians(180)))
                .waitSeconds(2)
                .strafeToSplineHeading(new Vector2d(57, 54), Math.toRadians(225))
                .strafeToSplineHeading(new Vector2d(48, 42), Math.toRadians(270))
                .strafeToSplineHeading(new Vector2d(57, 54), Math.toRadians(225))
                .strafeToSplineHeading(new Vector2d(48, 40), Math.toRadians(305))
                .strafeToSplineHeading(new Vector2d(57, 54), Math.toRadians(225))
                .strafeToSplineHeading(new Vector2d(52, 25), Math.toRadians(0))
                .strafeToSplineHeading(new Vector2d(57, 54), Math.toRadians(225))
                .strafeToSplineHeading(new Vector2d(50, 10), Math.toRadians(180))
                .setTangent(Math.toRadians(180))
                .lineToX(30)

                /*
                .setReversed(true)
                .splineTo(new Vector2d(30, 40), Math.toRadians(145))
                .setReversed(false)
                .splineTo(new Vector2d(50, 50), Math.toRadians(45))
                .setReversed(true)
                .splineTo(new Vector2d(35, 35), Math.toRadians(160))
                .setReversed(false)
                .splineTo(new Vector2d(50, 50), Math.toRadians(45))
                .setReversed(true)
                .strafeToSplineHeading(new Vector2d(45, 24), Math.toRadians(0))
                */
                .build());


        double rungY = 36;


        /*
        // (NEW) 3 Specimen Auto
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d((t*-0.5-2.75), (t*2.5 + 2.75), Math.toRadians(270)))
                .waitSeconds(1)
                // DRIVE FORWARDS
                .strafeToConstantHeading(new Vector2d(-7, 50))
                .splineToConstantHeading(new Vector2d(-4, rungY), Math.toRadians(270))

                // PUSH SAMPLES
                .setTangent(Math.toRadians(135))
                // Move to side
                .splineToConstantHeading(new Vector2d(-37, 36.16), Math.toRadians(270))
                // Move up
                .splineToConstantHeading(new Vector2d(-38, 18), Math.toRadians(270))
                // Move up and right slightly
                .splineToConstantHeading(new Vector2d(-42, 12), Math.toRadians(180), new TranslationalVelConstraint(20))
                // Move down and right slightly
                .splineToConstantHeading(new Vector2d(-45, 18), Math.toRadians(90), new TranslationalVelConstraint(20))
                // Push
                .splineToConstantHeading(new Vector2d(-45, 50), Math.toRadians(90))
                .setTangent(Math.toRadians(270))
                // Move Up
                .splineToConstantHeading(new Vector2d(-45, 18), Math.toRadians(270), null, new ProfileAccelConstraint(-40, 40))
                // Move up and right slightly
                .splineToConstantHeading(new Vector2d(-50, 12), Math.toRadians(180), new TranslationalVelConstraint(20))
                // Move down and right slightly
                .splineToConstantHeading(new Vector2d(-55.5, 18), Math.toRadians(90), new TranslationalVelConstraint(20))
                // Push
                .splineToConstantHeading(new Vector2d(-55.5, 58.5), Math.toRadians(90))

                // DRIVE TO RUNG
                .setTangent(Math.toRadians(-45))
                .strafeToConstantHeading(new Vector2d(-10, 45))
                .splineToConstantHeading(new Vector2d(0, rungY), Math.toRadians(270))

                // Go to rung
                //.splineToConstantHeading(new Vector2d(0, 40), Math.toRadians(270))
                //.splineToConstantHeading(new Vector2d(4, 50), Math.toRadians(270))
                //.splineToConstantHeading(new Vector2d(0, rungY), Math.toRadians(270))

                // DRIVE TO OBSERVATION
                .setReversed(true)
                .strafeToConstantHeading(new Vector2d(-38, 58))


                // DRIVE TO RUNG
                .setReversed(false)
                .setTangent(Math.toRadians(-45))
                .strafeToConstantHeading(new Vector2d(-10, 50))
                .splineToConstantHeading(new Vector2d(10, rungY), Math.toRadians(270))

                // DRIVE TO OBSERVATION
                .setReversed(true)
                .strafeToConstantHeading(new Vector2d(-38, 58))

                .setReversed(false)
                .setTangent(Math.toRadians(-45))
                .strafeToConstantHeading(new Vector2d(0, 50))
                .splineToConstantHeading(new Vector2d(20, rungY), Math.toRadians(270))

                .setReversed(true)
                .strafeToConstantHeading(new Vector2d(-38, 58))

                .build());
*/

        // 4 Sample Auto
        /*
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(t*1.5, t*2.5, Math.toRadians(180)))
                .strafeTo(new Vector2d(t*1.5, t*2))
                .strafeToLinearHeading(new Vector2d(t*2, t*2), Math.toRadians(220))
                .strafeTo(new Vector2d(t*2.2, t*2.2))
                .strafeToLinearHeading(new Vector2d(t*2, t*2), Math.toRadians(220))
                .turnTo(Math.toRadians(270))
                // Cycle
                .turnTo(Math.toRadians(220))
                .strafeTo(new Vector2d(t*2.2, t*2.2))
                .strafeToLinearHeading(new Vector2d(t*2, t*2), Math.toRadians(220))
                .turnTo(Math.toRadians(295))
                // Cycle
                .turnTo(Math.toRadians(220))
                .strafeTo(new Vector2d(t*2.2, t*2.2))
                .strafeToLinearHeading(new Vector2d(t*2, t*2), Math.toRadians(220))
                .turnTo(Math.toRadians(315))
                // Cycle
                .turnTo(Math.toRadians(220))
                .strafeTo(new Vector2d(t*2.2, t*2.2))
                .strafeToLinearHeading(new Vector2d(t*2, t*2), Math.toRadians(220))

                .turnTo(Math.toRadians(180))
                .strafeTo(new Vector2d(t*2, t*0.5))
                .strafeTo(new Vector2d(t*1.2, t*0.5))

                .build());

         */
        /*
        // (OLD) 4 Specimen Auto
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(t*-0.5, t*2.5, Math.toRadians(270)))
                .lineToY(t*1.7)
                //.waitSeconds(1)
                .turn(Math.toRadians(-70))
                .lineToY(t*1.55)
                //.waitSeconds(1)
                .setTangent(Math.toRadians(180))
                // 140 is turning to Observation Zone, 200 is turning to samples
                .lineToXSplineHeading(t*-1.2, Math.toRadians(140))
                .lineToXSplineHeading(t*-1.5,Math.toRadians(200))
                .lineToXSplineHeading(t*-1.8, Math.toRadians(120))
                .lineToXSplineHeading(t*-2.1, Math.toRadians(200))
                .lineToXSplineHeading(t*-2.0, Math.toRadians(110))
                .turnTo(Math.toRadians(270))
                // Grabbing specimen pos
                .strafeTo(new Vector2d(t * -1.5, t * 2.5))
                // Scoring specimen pos
                .strafeTo(new Vector2d(t * -0.5, t * 1.7))
                // Grabbing specimen pos
                .strafeTo(new Vector2d(t * -1.5, t * 2.5))
                // Scoring specimen pos
                .strafeTo(new Vector2d(t * -0.5, t * 1.7))
                // Grabbing specimen pos
                .strafeTo(new Vector2d(t * -1.5, t * 2.5))
                // Scoring specimen pos
                .strafeTo(new Vector2d(t * -0.5, t * 1.7))

                // Park
                .strafeTo(new Vector2d(t * -2.0, t * 2.5))
                .build());
*/
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}