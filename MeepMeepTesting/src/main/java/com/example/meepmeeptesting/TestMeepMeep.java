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

        double t = 23.5;

        double scoreX = 54;
        double scoreY = 52;

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
                .splineToLinearHeading(new Pose2d(25,10,Math.toRadians(180)),Math.toRadians(180))
                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
