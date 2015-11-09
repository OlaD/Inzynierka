package com.example.ola.inzynierka;

/**
 * Created by zolwo_000 on 25.09.2015.
 */
public class Category {
    public String name;
    public int elementsNumber;
    public boolean currentlyLearned;
    public boolean isUsed;
    public boolean isAccusative;    // czy biernik różni się od mianownika

    public Category()
    {
        this.currentlyLearned = false;
        this.isUsed = false;
    }

}
