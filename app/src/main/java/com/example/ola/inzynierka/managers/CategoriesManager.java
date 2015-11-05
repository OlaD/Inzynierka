package com.example.ola.inzynierka.managers;

import android.widget.Toast;

import com.example.ola.inzynierka.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zolwo_000 on 05.11.2015.
 */
public class CategoriesManager {

    private List<Category> categories;

    public CategoriesManager(List<Category> categories)
    {
        this.categories = categories;
    }
    public CategoriesManager(){
        //categories = new ArrayList<>();
    };

    public void readCategories(List<Category> categories)
    {
        this.categories = categories;
    }

    public int getCategoriesNumber() {
        return categories.size();
}

    public Category getRandomCategory()
    {
        Random rand = new Random();
        int index;
        do {
            index = rand.nextInt(getCategoriesNumber());
        }while(categories.get(index).isUsed);

        Category category = categories.get(index);
        //category.isUsed = true;
        return category;
    }

    public void resetCategories()
    {
        for (Category category : categories)
        {
            category.isUsed = false;
            category.currentlyLearned = false;
        }
    }

    public void removeCategory(Category categoryToRemove)
    {
        categories.remove(categoryToRemove);
    }

}
