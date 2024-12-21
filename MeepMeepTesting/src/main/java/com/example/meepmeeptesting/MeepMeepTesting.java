package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 18)
                .build();

        double t = 23.5;


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
        // 4 Specimen Auto
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

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}