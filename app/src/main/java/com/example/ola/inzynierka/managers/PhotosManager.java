package com.example.ola.inzynierka.managers;

import android.app.Activity;
import android.widget.ImageView;

import com.example.ola.inzynierka.Category;

import java.util.Random;

/**
 * Created by zolwo_000 on 05.11.2015.
 */
public class PhotosManager {

    public PhotosManager()
    {

    }

    public int getPhotoIdForCategory(Category category, Activity activity) {
        Random rand = new Random();
        int randInt = rand.nextInt(category.elementsNumber);
        String imageName = category.name + randInt;
        int id = activity.getResources().getIdentifier(imageName, "drawable", activity.getPackageName());
        return id;
    }
}

