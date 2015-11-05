package com.example.ola.inzynierka;

import com.example.ola.inzynierka.patterns.command.Command;

/**
 * Created by zolwo_000 on 30.10.2015.
 */
public class ExerciseManager2 {

    private Command mode;
    public void setMode(Command mode)
    {
        this.mode = mode;
    }
    public void doExercise()
    {
        mode.doExercise();
    }
}
