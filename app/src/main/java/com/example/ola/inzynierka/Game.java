package com.example.ola.inzynierka;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
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

        drawGameInterface(displayedPhotosCount);

        choosePhotoForCategory(currentCategory, 0, rightPhotoClickListener);
        choosePhotosForRandomCategories();

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

    private void drawGameInterface(int displayedPhotosCount) {

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;

        TableLayout tableLayout = (TableLayout) activity.findViewById(R.id.gameTable);
        TableRow[] rows;
        int rowCount = 0;
        int photosPerRow = 0;
        int rowMarginTop = 0;
        int photoWidth = 0;
        int photoHeight = 0;

        switch (displayedPhotosCount) {
            case 2:
                rowCount = 1;
                photosPerRow = 2;
                rowMarginTop = 20;
                photoWidth = screenWidth/2;
                photoHeight = screenHeight/2;
                break;
            case 3:
                rowCount = 1;
                photosPerRow = 3;
                rowMarginTop = 20;
                photoWidth = screenWidth/4;
                photoHeight = screenHeight/4;
                break;
            case 4:
                rowCount = 2;
                photosPerRow = 2;
                rowMarginTop = 20;
                photoWidth = screenWidth/3;
                photoHeight = screenHeight/3;
                break;
            /*case 6:
                rowCount = 2;
                photosPerRow = 3;
                rowMarginTop = 20;
                photoWidth = screenWidth/4;
                photoHeight = screenHeight/4;
                break;*/
        }

        for (int i = 0; i < displayedPhotosCount; i++) {
            displayedPhotos[i] = new Photo(activity, photoWidth, photoHeight);
        }

        rows = new TableRow[rowCount];
        for (int i = 0; i < rowCount; i++) {
            rows[i] = new TableRow(activity);
            tableLayout.addView(rows[i]);
            TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, rowMarginTop, 0, 0);
            rows[i].setLayoutParams(layoutParams);
        }

        int tmp = 0;
        for(int i = 0; i < rowCount; i++) {
            for (int j = 0; j < photosPerRow; j++) {
                rows[i].addView(displayedPhotos[tmp].imageView);
                tmp++;
            }
        }

    }

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
        cat1.elementsNumber = 2;
        categories[0] = cat1;
        Category cat2 = new Category();
        cat2.name = "pies";
        cat2.elementsNumber = 2;
        categories[1] = cat2;
        Category cat3 = new Category();
        cat3.name = "samochod";
        cat3.elementsNumber = 2;
        categories[2] = cat3;
        Category cat4 = new Category();
        cat4.name = "ser";
        cat4.elementsNumber = 2;
        categories[3] = cat4;
    }

}
