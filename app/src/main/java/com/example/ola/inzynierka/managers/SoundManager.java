package com.example.ola.inzynierka.managers;

import android.app.Activity;
import android.media.MediaPlayer;

import com.example.ola.inzynierka.Category;
import com.example.ola.inzynierka.SoundType;

/**
 * Created by zolwo_000 on 05.11.2015.
 */
public class SoundManager {

    MediaPlayer exerciseMediaPlayer;    // pierwsza część polecenia, dobrze/źle
    MediaPlayer categoryMediaPlayer;    // druga część polecenia (nazwa kategorii w mianowniku/bierniku)
    
    public SoundManager()
    {
        
    }

    public void setSound(SoundType soundType, Activity activity, Category category) {
/*
        if (answerMediaPlayer != null && answerMediaPlayer.isPlaying()) {
            answerMediaPlayer.stop();
        }
        if (exerciseMediaPlayer != null && exerciseMediaPlayer.isPlaying()) {
            exerciseMediaPlayer.stop();
        }*/

        int resId;
        switch (soundType) {
            case EXERCISE0:
                resId = activity.getResources().getIdentifier(category.name + "_m", "raw", activity.getPackageName());
                exerciseMediaPlayer = MediaPlayer.create(activity, resId);
                break;
            case EXERCISE1:
                resId = activity.getResources().getIdentifier("exercise1", "raw", activity.getPackageName());
                exerciseMediaPlayer = MediaPlayer.create(activity, resId);
                resId = activity.getResources().getIdentifier(category.name + "_m", "raw", activity.getPackageName());
                categoryMediaPlayer = MediaPlayer.create(activity, resId);
                exerciseMediaPlayer.setNextMediaPlayer(categoryMediaPlayer);
                break;
            case EXERCISE2:
                resId = activity.getResources().getIdentifier("exercise2", "raw", activity.getPackageName());
                exerciseMediaPlayer = MediaPlayer.create(activity, resId);
                if (category.isAccusative)
                    resId = activity.getResources().getIdentifier(category.name + "_b", "raw", activity.getPackageName());
                else
                    resId = activity.getResources().getIdentifier(category.name + "_m", "raw", activity.getPackageName());
                categoryMediaPlayer = MediaPlayer.create(activity, resId);
                exerciseMediaPlayer.setNextMediaPlayer(categoryMediaPlayer);
                break;
            case CORRECT:
                resId = activity.getResources().getIdentifier("dobrze", "raw", activity.getPackageName());
                exerciseMediaPlayer = MediaPlayer.create(activity, resId);
                resId = activity.getResources().getIdentifier(category.name + "_m", "raw", activity.getPackageName());
                categoryMediaPlayer = MediaPlayer.create(activity, resId);
                exerciseMediaPlayer.setNextMediaPlayer(categoryMediaPlayer);
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
        categoryMediaPlayer.stop();
        categoryMediaPlayer.release();
    }

}
