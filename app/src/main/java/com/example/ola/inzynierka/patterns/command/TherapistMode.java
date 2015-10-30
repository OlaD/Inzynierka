package com.example.ola.inzynierka.patterns.command;

import com.example.ola.inzynierka.Exercise;

/**
 * Created by zolwo_000 on 30.10.2015.
 */
public class TherapistMode implements Command {

    private Exercise exercise;

    public TherapistMode(Exercise exercise){
        this.exercise = exercise;
    }
    @Override
    public void doExercise() {

    }
}
