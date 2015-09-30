package com.example.ola.inzynierka;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class Game {
    private TextView excerciseDescription;
    private int displayedPhotosCount;
    private int categoriesSize;
    private Photo[] displayedPhotos;
    private boolean[] chosenCategories;
    private Category[] categories;
    private Category currentCategory;
    private Activity activity;

    Game() {}
    Game(Activity activity, int displayedPhotosCount, int categoriesSize) {
        this.displayedPhotosCount = displayedPhotosCount;
        this.categoriesSize = categoriesSize;
        this.activity = activity;
        displayedPhotos = new Photo[displayedPhotosCount];
        chosenCategories = new boolean[categoriesSize];
    }

    public void showPhotosSet() {
        addSampleCategories();

        int currentCategoryIndex = chooseRandomCategory();

        currentCategory = categories[currentCategoryIndex];
        chosenCategories[currentCategoryIndex] = true;

        RelativeLayout layout = (RelativeLayout) activity.findViewById(R.id.layoutGra);

        drawGameInterface(displayedPhotosCount);

        choosePhotoForCategory(currentCategory, 0, rightPhotoClickListener);
        choosePhotosForRandomCategories();

        for (int i = 0; i < displayedPhotosCount; i++) {
            layout.addView(displayedPhotos[i].imageView);
        }

        excerciseDescription = (TextView) activity.findViewById(R.id.questionTextView);
        excerciseDescription.setText("Gdzie jest " + currentCategory.name + "?");
    }

    private void choosePhotosForRandomCategories() {

        int chosenCategoriesNumber = 1;
        while(chosenCategoriesNumber < displayedPhotosCount) {
            int randInt = chooseRandomCategory();
            if(chosenCategories[randInt] == true) {
                continue;
            }
            chosenCategories[randInt] = true;
            choosePhotoForCategory(categories[randInt], chosenCategoriesNumber, wrongPhotoClickListener);

            chosenCategoriesNumber++;
        }
    }

    private int chooseRandomCategory() {
        Random rand = new Random();
        return rand.nextInt(categories.length);
    }

    private void choosePhotoForCategory(Category category, int index, View.OnClickListener listener) {
        Random rand = new Random();
        int randInt = rand.nextInt(category.elementsNumber);
        String imageName = category.name + randInt;
        int id = activity.getResources().getIdentifier(imageName, "drawable", activity.getPackageName());
        displayedPhotos[index].imageView.setImageResource(id);
        displayedPhotos[index].setOnClickListener(listener);

    }

    private void drawGameInterface(int liczbaZdjec) {

        int szerokoscZdjec = 0;
        int wysokoscZdjec = 0;

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int szerokoscEkranu = size.x;
        int wysokoscEkranu = size.y;

//jest cos takiego jak TableLayout...to chyba na dłuzsza mete bedzie wygodniejsze
        switch (liczbaZdjec) {
            case 3:
                szerokoscZdjec = szerokoscEkranu/2-75;
                wysokoscZdjec = wysokoscEkranu/2;
                for (int i=0; i<3; i++){
                    displayedPhotos[i] = new Photo(activity, szerokoscZdjec, wysokoscZdjec);
                }
                displayedPhotos[0].imageView.setId(R.id.zdjecie1);

                displayedPhotos[1].layoutParams.leftMargin = 25;
                displayedPhotos[1].layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.zdjecie1);

                displayedPhotos[2].layoutParams.addRule(RelativeLayout.BELOW, R.id.zdjecie1);
                displayedPhotos[2].layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

                break;
        }// switch

    }// rysujInterfejsGry()


    View.OnClickListener rightPhotoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View clickedPhoto) {
            rightAnswerChoosen(clickedPhoto);
        }
    };


    public View.OnClickListener wrongPhotoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View clickedPhoto) {
            wrongAnswerChoosen(clickedPhoto);
        }
    };

    public void wrongAnswerChoosen(View clickedPhoto) {
        excerciseDescription.setText("Zle!!!! BLEEEEE!!!");
    }

    public void rightAnswerChoosen(View clickedPhoto) {
        excerciseDescription.setText("Dobrze");
    }


    private void addSampleCategories() {
        //ktos wie czy w JAVIE jest coś takiego fajnego jak inicializatory (jak w C#)?? xd
        categories = new Category[4];
        Category cat1 = new Category();
        cat1.name = "lalka";
        cat1.elementsNumber = 1;
        categories[0] = cat1;
        Category cat2 = new Category();
        cat2.name = "pies";
        cat2.elementsNumber = 1;
        categories[1] = cat2;
        Category cat3 = new Category();
        cat3.name = "samochod";
        cat3.elementsNumber = 1;
        categories[2] = cat3;
        Category cat4 = new Category();
        cat4.name = "ser";
        cat4.elementsNumber = 1;
        categories[3] = cat4;
    }

}
