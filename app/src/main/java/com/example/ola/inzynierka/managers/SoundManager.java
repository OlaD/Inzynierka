package com.example.ola.inzynierka.managers;

import android.app.Activity;
import android.media.MediaPlayer;

import com.example.ola.inzynierka.SoundType;

/**
 * Created by zolwo_000 on 05.11.2015.
 */
public class SoundManager {

    MediaPlayer answerMediaPlayer;
    MediaPlayer exerciseMediaPlayer;
    
    public SoundManager()
    {
        
    }

    public void setSound(SoundType soundType, Activity activity, String categoryName) {
/*
        if (answerMediaPlayer != null && answerMediaPlayer.isPlaying()) {
            answerMediaPlayer.stop();
        }
        if (exerciseMediaPlayer != null && exerciseMediaPlayer.isPlaying()) {
            exerciseMediaPlayer.stop();
        }*/


        int resId;
        switch (soundType) {
            case EXERCISE1:
                resId = activity.getResources().getIdentifier(categoryName + "1", "raw", activity.getPackageName());
                exerciseMediaPlayer = MediaPlayer.create(activity, resId);
                break;
            case CATEGORY1:
                resId = activity.getResources().getIdentifier(categoryName + "0", "raw", activity.getPackageName());
                exerciseMediaPlayer = MediaPlayer.create(activity, resId);
                break;
            case CORRECT:
                resId = activity.getResources().getIdentifier("dobrze", "raw", activity.getPackageName());
                exerciseMediaPlayer = MediaPlayer.create(activity, resId);

                resId = activity.getResources().getIdentifier(categoryName + "0", "raw", activity.getPackageName());
                answerMediaPlayer = MediaPlayer.create(activity, resId);

                exerciseMediaPlayer.setNextMediaPlayer(answerMediaPlayer);

                break;
            case WRONG:
                resId = activity.getResources().getIdentifier("zle", "raw", activity.getPackageName());
                exerciseMediaPlayer = MediaPlayer.create(activity, resId);
                break;
        }
        //exerciseMediaPlayer.start();

    }

    public void startPlay()
    {
        exerciseMediaPlayer.start();
        /*if(answerMediaPlayer == null) {
            exerciseMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    unload();
                }
            });
        }
        else
        {
            answerMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    unload();
                }
            });
        }*/
    }
/*
    public boolean isPlaying()
    {
        return exerciseMediaPlayer.isPlaying() || answerMediaPlayer.isPlaying();
    }
*/
    public void unload() {
        exerciseMediaPlayer.stop();
        exerciseMediaPlayer.release();
        answerMediaPlayer.stop();
        answerMediaPlayer.release();
    }

}
