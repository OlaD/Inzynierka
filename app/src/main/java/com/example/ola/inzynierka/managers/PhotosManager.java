package com.example.ola.inzynierka.managers;

import android.app.Activity;
import android.widget.ImageView;

import com.example.ola.inzynierka.Category;

import java.util.List;
import java.util.Random;

/**
 * Created by zolwo_000 on 05.11.2015.
 */
public class PhotosManager {

    //private List<String> photosNames;
    public PhotosManager()//List photosNames)
    {
        //this.photosNames = photosNames;
    }

    public int getPhotoIdForCategory(Category category, Activity activity, boolean generalization) {
        /*
        Random rand = new Random();
        int randInt = rand.nextInt(category.elementsNumber);
        String imageName = category.name + randInt;
        int id = activity.getResources().getIdentifier(imageName, "drawable", activity.getPackageName());
        return id;*/

        List<String> photosNames;
        if(generalization){
            photosNames = category.photosGeneralizationNames;
        }
        else {
            photosNames = category.photosNames;
        }
        Random rand = new Random();
        int randInt = rand.nextInt(photosNames.size());
        String imageName = photosNames.get(randInt);
        int id = activity.getResources().getIdentifier(imageName, "drawable", activity.getPackageName());
        return id;
    }
}

