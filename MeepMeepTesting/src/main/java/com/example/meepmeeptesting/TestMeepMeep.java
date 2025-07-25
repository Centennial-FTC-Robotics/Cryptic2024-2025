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

public class TestMeepMeep {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 18)
                .build();
        double offset = 5.0;
        double rungY = 36;
        double observationY = 59.3;

        double t = 23.5;

        double scoreX = 54;
        double scoreY = 52;

        /*
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d((t*-0.5-2.75), (t*2.5 + 2.75), Math.toRadians(270)))
                .strafeToConstantHeading(new Vector2d(1, rungY-4))
                .strafeToLinearHeading(new Vector2d(-4, 45), Math.toRadians(195))
                .strafeToLinearHeading(new Vector2d(-32+offset, 40), Math.toRadians(210))
                .strafeToLinearHeading(new Vector2d(-32+offset, 44), Math.toRadians(135))
                .strafeToLinearHeading(new Vector2d(-32+offset, 40), Math.toRadians(205))
                .strafeToLinearHeading(new Vector2d(-40+offset, 40), Math.toRadians(210))
                .strafeToLinearHeading(new Vector2d(-40+offset, 44), Math.toRadians(135))
                .strafeToLinearHeading(new Vector2d(-38, observationY), Math.toRadians(270))
                .setReversed(false)
                .setTangent(Math.toRadians(-45))
                .strafeToConstantHeading(new Vector2d(9, rungY))
                .setReversed(true)
                .strafeToConstantHeading(new Vector2d(-38, observationY))
                .setReversed(false)
                .setTangent(Math.toRadians(-45))
                .strafeToConstantHeading(new Vector2d(9, rungY))
                .setReversed(true)
                .strafeToConstantHeading(new Vector2d(-38, observationY))
                .setReversed(false)
                .setTangent(Math.toRadians(-45))
                .strafeToConstantHeading(new Vector2d(9, rungY))
                .setReversed(true)
                .strafeToConstantHeading(new Vector2d(-38, observationY))
                        .build());

         */



        // Active Intake 5 Specimen Auto
        /*
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d((t*-0.5-2.75), (t*2.5 + 2.75), Math.toRadians(270)))
                //22.38 seconds
                        .waitSeconds(1)
                        // DRIVE FORWARDS SCORE PRELOAD
                        .splineToConstantHeading(new Vector2d(-8, rungY), Math.toRadians(270))

                        .setTangent(Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(-32+offset, 40, Math.toRadians(230)),Math.toRadians(215))


                        //.strafeToLinearHeading(new Vector2d(-32+offset, 44), Math.toRadians(135))
                        .setTangent(Math.toRadians(20))
                        .splineToLinearHeading(new Pose2d(-32+offset, 44, Math.toRadians(150)),Math.toRadians(160))
                        .strafeToLinearHeading(new Vector2d(-40+offset, 40), Math.toRadians(230))

                        //.strafeToLinearHeading(new Vector2d(-40+offset, 44), Math.toRadians(135))
                        .setTangent(Math.toRadians(20))
                        .splineToLinearHeading(new Pose2d(-40+offset, 44, Math.toRadians(150)),Math.toRadians(160))
                        .strafeToLinearHeading(new Vector2d(-47+offset, 40), Math.toRadians(230))

                        //.strafeToLinearHeading(new Vector2d(-44+offset, 44), Math.toRadians(120))
                        .setTangent(Math.toRadians(20))
                        .splineToLinearHeading(new Pose2d(-47+offset, 44, Math.toRadians(150)),Math.toRadians(160))

                        .strafeToLinearHeading(new Vector2d(-38, 58), Math.toRadians(270))
                        .setTangent(Math.toRadians(270))
                        .splineToConstantHeading(new Vector2d(8,rungY), Math.toRadians(270))

                        .setTangent(Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(-38, 58),Math.toRadians(90))
                        .setTangent(Math.toRadians(270))
                        .splineToConstantHeading(new Vector2d(4, rungY),Math.toRadians(270))

                        .setTangent(Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(-38, 58),Math.toRadians(90))
                        .setTangent(Math.toRadians(270))
                        .splineToConstantHeading(new Vector2d(0, rungY),Math.toRadians(270))

                        .setTangent(Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(-38, 58),Math.toRadians(90))
                        .setTangent(Math.toRadians(270))
                        .splineToConstantHeading(new Vector2d(-4, rungY),Math.toRadians(270))

                        .build());

         */





        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d((t*1.5-2.75), (t*2.5 + 2.75), Math.toRadians(180)))
                //9.74 seconds
                .strafeToLinearHeading(new Vector2d(scoreX, scoreY), Math.toRadians(225))
                .strafeToLinearHeading(new Vector2d(54,43),Math.toRadians(-104))
                .strafeToLinearHeading(new Vector2d(scoreX, scoreY), Math.toRadians(225))
                .strafeToLinearHeading(new Vector2d(54,43),Math.toRadians(-71))
                .strafeToLinearHeading(new Vector2d(scoreX, scoreY), Math.toRadians(225))
                .strafeToLinearHeading(new Vector2d(50,27),Math.toRadians(-1))
                .strafeToLinearHeading(new Vector2d(scoreX, scoreY), Math.toRadians(225))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(32,10,Math.toRadians(180)),Math.toRadians(180))
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(scoreX, scoreY, Math.toRadians(225)), Math.toRadians(45))
                .build());




        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
