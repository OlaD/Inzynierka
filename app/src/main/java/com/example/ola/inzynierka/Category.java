package com.example.ola.inzynierka;

import java.util.List;

/**
 * Created by zolwo_000 on 25.09.2015.
 */
public class Category {
    public String name;
    public int elementsNumber;
    public boolean currentlyLearned;
    public boolean isUsed;
    public boolean isAccusative;    // czy biernik różni się od mianownika
    public List<String> photosNames;
    public List<String> photosGeneralizationNames;

    public Category(List photosNames, List photosGeneralizationNames)
    {
        this.photosNames = photosNames;
        this.photosGeneralizationNames = photosGeneralizationNames;
        this.currentlyLearned = false;
        this.isUsed = false;
    }

}
