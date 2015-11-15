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

        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> generalizationList = new ArrayList<>();
        String base = "lalka";
        for(int i = 0; i < 4; ++i)
        {
            nameList.add(base + i);
        }
        for(int i = 4; i < 6; ++i)
        {
            generalizationList.add(base + i);
        }
        Category cat1 = new Category(nameList, generalizationList);
        cat1.name = base;
        cat1.elementsNumber = 6;
        cat1.isAccusative = true;

        nameList = new ArrayList<>();
        generalizationList = new ArrayList<>();
        base = "pies";
        for(int i = 0; i < 4; ++i)
        {
            nameList.add(base + i);
        }
        for(int i = 4; i < 6; ++i)
        {
            generalizationList.add(base + i);
        }
        Category cat2 = new Category(nameList, generalizationList);
        cat2.name = base;
        cat2.elementsNumber = 6;
        cat2.isAccusative = true;

        nameList = new ArrayList<>();
        generalizationList = new ArrayList<>();
        base = "samochod";
        for(int i = 0; i < 4; ++i)
        {
            nameList.add(base + i);
        }
        for(int i = 4; i < 6; ++i)
        {
            generalizationList.add(base + i);
        }
        Category cat3 = new Category(nameList, generalizationList);
        cat3.name = base;
        cat3.elementsNumber = 6;
        cat3.isAccusative = false;

        nameList = new ArrayList<>();
        generalizationList = new ArrayList<>();
        base = "ser";
        for(int i = 0; i < 4; ++i)
        {
            nameList.add(base + i);
        }
        for(int i = 4; i < 6; ++i)
        {
            generalizationList.add(base + i);
        }
        Category cat4 = new Category(nameList, generalizationList);
        cat4.name = base;
        cat4.elementsNumber = 6;
        cat4.isAccusative = false;

        nameList = new ArrayList<>();
        generalizationList = new ArrayList<>();
        base = "chleb";
        for(int i = 0; i < 4; ++i)
        {
            nameList.add(base + i);
        }
        for(int i = 4; i < 6; ++i)
        {
            generalizationList.add(base + i);
        }
        Category cat5 = new Category(nameList, generalizationList);
        cat5.name = base;
        cat5.elementsNumber = 6;
        cat5.isAccusative = false;

        nameList = new ArrayList<>();
        generalizationList = new ArrayList<>();
        base = "cytryna";
        for(int i = 0; i < 4; ++i)
        {
            nameList.add(base + i);
        }
        for(int i = 4; i < 6; ++i)
        {
            generalizationList.add(base + i);
        }
        Category cat6 = new Category(nameList, generalizationList);
        cat6.name = base;
        cat6.elementsNumber = 6;
        cat6.isAccusative = true;

        nameList = new ArrayList<>();
        generalizationList = new ArrayList<>();
        base = "gruszka";
        for(int i = 0; i < 4; ++i)
        {
            nameList.add(base + i);
        }
        for(int i = 4; i < 6; ++i)
        {
            generalizationList.add(base + i);
        }
        Category cat7 = new Category(nameList, generalizationList);
        cat7.name = base;
        cat7.elementsNumber = 6;
        cat7.isAccusative = true;

        nameList = new ArrayList<>();
        generalizationList = new ArrayList<>();
        base = "jablko";
        for(int i = 0; i < 4; ++i)
        {
            nameList.add(base + i);
        }
        for(int i = 4; i < 6; ++i)
        {
            generalizationList.add(base + i);
        }
        Category cat8 = new Category(nameList, generalizationList);
        cat8.name = base;
        cat8.elementsNumber = 6;
        cat8.isAccusative = false;

        nameList = new ArrayList<>();
        generalizationList = new ArrayList<>();
        base = "kot";
        for(int i = 0; i < 4; ++i)
        {
            nameList.add(base + i);
        }
        for(int i = 4; i < 6; ++i)
        {
            generalizationList.add(base + i);
        }
        Category cat9 = new Category(nameList, generalizationList);
        cat9.name = base;
        cat9.elementsNumber = 6;
        cat9.isAccusative = true;

        nameList = new ArrayList<>();
        generalizationList = new ArrayList<>();
        base = "kubek";
        for(int i = 0; i < 4; ++i)
        {
            nameList.add(base + i);
        }
        for(int i = 4; i < 6; ++i)
        {
            generalizationList.add(base + i);
        }
        Category cat10 = new Category(nameList, generalizationList);
        cat10.name = base;
        cat10.elementsNumber = 6;
        cat10.isAccusative = false;

        nameList = new ArrayList<>();
        generalizationList = new ArrayList<>();
        base = "pilka";
        for(int i = 0; i < 4; ++i)
        {
            nameList.add(base + i);
        }
        for(int i = 4; i < 6; ++i)
        {
            generalizationList.add(base + i);
        }
        Category cat11 = new Category(nameList, generalizationList);
        cat11.name = base;
        cat11.elementsNumber = 6;
        cat11.isAccusative = true;

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
