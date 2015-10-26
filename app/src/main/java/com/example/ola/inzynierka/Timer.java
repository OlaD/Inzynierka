package com.example.ola.inzynierka;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * Created by zolwo_000 on 24.10.2015.
 */
public class Timer extends AsyncTask<Integer, Void, Void> {

    Game callingGame;

    public Timer(Game callingGame) {
        this.callingGame = callingGame;
    }

    @Override
    protected Void doInBackground(Integer... params) {
        try {
            Thread.sleep(params[0] * 1000);
            publishProgress();
            Thread.sleep(params[1] * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values){
        callingGame.showHint();
    }

    @Override
    protected void onPostExecute(Void result) {
        callingGame.wrongAnswerChoosen();
    }
}
