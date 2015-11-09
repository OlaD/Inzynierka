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
        cat1.elementsNumber = 6;
        Category cat2 = new Category();
        cat2.name = "pies";
        cat2.elementsNumber = 6;
        Category cat3 = new Category();
        cat3.name = "samochod";
        cat3.elementsNumber = 6;
        Category cat4 = new Category();
        cat4.name = "ser";
        cat4.elementsNumber = 6;
        Category cat5 = new Category();
        cat5.name = "chleb";
        cat5.elementsNumber = 6;
        Category cat6 = new Category();
        cat6.name = "cytryna";
        cat6.elementsNumber = 6;
        Category cat7 = new Category();
        cat7.name = "gruszka";
        cat7.elementsNumber = 6;
        Category cat8 = new Category();
        cat8.name = "jablko";
        cat8.elementsNumber = 6;
        Category cat9 = new Category();
        cat9.name = "kot";
        cat9.elementsNumber = 6;
        Category cat10 = new Category();
        cat10.name = "kubek";
        cat10.elementsNumber = 6;
        Category cat11 = new Category();
        cat11.name = "pilka";
        cat11.elementsNumber = 6;

        allCatogories.add(cat1);
        categoriesToLearn.add(cat1);
        allCatogories.add(cat2);
        categoriesToLearn.add(cat2);
        allCatogories.add(cat3);
        categoriesToLearn.add(cat3);
        allCatogories.add(cat4);
        categoriesToLearn.add(cat4);
        allCatogories.add(cat5);
        categoriesToLearn.add(cat5);
        allCatogories.add(cat6);
        categoriesToLearn.add(cat6);
        allCatogories.add(cat7);
        categoriesToLearn.add(cat7);
        allCatogories.add(cat8);
        categoriesToLearn.add(cat8);
        allCatogories.add(cat9);
        categoriesToLearn.add(cat9);
        allCatogories.add(cat10);
        categoriesToLearn.add(cat10);
        allCatogories.add(cat11);
        categoriesToLearn.add(cat11);

        allCategoriesManager.readCategories(allCatogories);
        categoriesToLearnManager.readCategories(categoriesToLearn);
    }
}
