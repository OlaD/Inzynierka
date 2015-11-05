package com.example.ola.inzynierka;

import com.example.ola.inzynierka.managers.CategoriesManager;

import java.util.ArrayList;

/**
 * Created by zolwo_000 on 05.11.2015.
 */
public class CategoriesReader {

    CategoriesReader()
    {


    }

    public void read(CategoriesManager allCategoriesManager, CategoriesManager categoriesToLearnManager)
    {
        ArrayList<Category> allCatogories = new ArrayList<>();
        ArrayList<Category> categoriesToLearn = new ArrayList<>();

        Category cat1 = new Category();
        cat1.name = "lalka";
        cat1.elementsNumber = 3;
        Category cat2 = new Category();
        cat2.name = "pies";
        cat2.elementsNumber = 3;
        Category cat3 = new Category();
        cat3.name = "samochod";
        cat3.elementsNumber = 3;
        Category cat4 = new Category();
        cat4.name = "ser";
        cat4.elementsNumber = 3;

        allCatogories.add(cat1);
        categoriesToLearn.add(cat1);
        allCatogories.add(cat2);
        categoriesToLearn.add(cat2);
        allCatogories.add(cat3);
        categoriesToLearn.add(cat3);
        allCatogories.add(cat4);
        categoriesToLearn.add(cat4);

        allCategoriesManager.readCategories(allCatogories);
        categoriesToLearnManager.readCategories(categoriesToLearn);
    }
}
