package com.example.ola.inzynierka.managers;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.ola.inzynierka.R;

import java.util.Random;

/**
 * Created by zolwo_000 on 05.11.2015.
 */
public class AnimationManager {

    private Animation[] animations;

    public AnimationManager(Activity activity)
    {
        animations = new Animation[4];
        animations[0] = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.rotate);
        animations[1] = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.blink);
        animations[2] = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.scale);
        animations[3] = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.move);
    }

    public Animation getRandomAnimation(){
        int n = animations.length;
        Random rand = new Random();
        return animations[rand.nextInt(n)];

    }
}
