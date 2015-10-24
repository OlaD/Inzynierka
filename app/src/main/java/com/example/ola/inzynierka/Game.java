package com.example.ola.inzynierka;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Game {
    private TextView excerciseDescription;
    private int displayedPhotosCount;
    private int categoriesSize;
    private Photo[] displayedPhotos;
    private boolean[] chosenCategories;
    private Category[] allCategories;
    private List<Category> categoriesToLearn;
    private Category currentCategoryToLearn;
    private Activity activity;

    private int screenWidth;
    private int screenHeight;
    private int correctPhotoId;
    private ImageView correctPhoto;
    private Button buttonNext;
    private Stack<Integer> indexesOfPhotosPlaces;
    private boolean successWithFirstClick;

    private Timer timer;

    Game() {}
    Game(Activity activity, int displayedPhotosCount, int categoriesSize) {
        this.displayedPhotosCount = displayedPhotosCount;
        this.categoriesSize = categoriesSize;
        this.activity = activity;
        displayedPhotos = new Photo[displayedPhotosCount];
        chosenCategories = new boolean[categoriesSize];
        indexesOfPhotosPlaces = new Stack<Integer>();
        successWithFirstClick = true;
        categoriesToLearn = new ArrayList<>();



    }


    public void start() {
        addSampleCategories();
        drawGameInterface(displayedPhotosCount);
        startExercise(ExcerciseStrategy.START_NEW);
    }



    public void startExercise(ExcerciseStrategy action) {
        switch (action) {
            case START_NEW: {
                initializeExercise();

                int currentCategoryToLearnIndex = chooseRandomCategoryToLearn();
                currentCategoryToLearn = categoriesToLearn.get(currentCategoryToLearnIndex);
                choosePhotoForCategory(currentCategoryToLearn, indexesOfPhotosPlaces.pop(), true);

                chooseOtherCategories();
                askForAnswer();
                setTimer(5);
                break;
            }
            case REPEAT_CATEGORY_TO_LEARN:
            {
                chooseOtherCategories();
                askForAnswer();
                setTimer(5);
                break;
            }
            case REPEAT_THE_SAME:
            {
                askForAnswer();
                setTimer(5);
                break;
            }
        }



    }

    private void setTimer(Integer time) {
        timer = new Timer(this);
        timer.execute(time);
    }

    private void askForAnswer() {
        excerciseDescription = (TextView) activity.findViewById(R.id.questionTextView);
        excerciseDescription.setText("Gdzie jest " + currentCategoryToLearn.name + "?");
    }

    private void initializeExercise() {
        assetRandomPhotosOrder();
        successWithFirstClick = true;
        for (int i = 0; i < categoriesSize; i++) {
            chosenCategories[i] = false;
        }
    }

    private void assetRandomPhotosOrder() {
        int[] temp = new int[displayedPhotosCount];
        for(int i = 0; i < displayedPhotosCount; ++i){
            temp[i] = i;
        }
        permutate(temp, displayedPhotosCount);
        for(int i = 0; i < displayedPhotosCount; ++i){
            indexesOfPhotosPlaces.push(temp[i]);
        }
    }

    private void chooseOtherCategories() {

        int chosenCategoriesNumber = 1;
        while(chosenCategoriesNumber < displayedPhotosCount) {
            int randInt = randomIndexFromAllCategories();
            if(chosenCategories[randInt] == true || allCategories[randInt].name == currentCategoryToLearn.name) {
                continue;
            }
            chosenCategories[randInt] = true;
            choosePhotoForCategory(allCategories[randInt], indexesOfPhotosPlaces.pop(), false);
            chosenCategoriesNumber++;
        }
    }

    private int randomIndexFromAllCategories() {
        Random rand = new Random();
        return rand.nextInt(allCategories.length);
    }

    private int chooseRandomCategoryToLearn() {
        Random rand = new Random();
        return rand.nextInt(categoriesToLearn.size());
    }

    private void choosePhotoForCategory(Category category, int index, boolean isCorrect) {
        Random rand = new Random();
        int randInt = rand.nextInt(category.elementsNumber);
        String imageName = category.name + randInt;
        int id = activity.getResources().getIdentifier(imageName, "drawable", activity.getPackageName());
        displayedPhotos[index].imageView.setImageResource(id);
        if (isCorrect) {
            displayedPhotos[index].setOnClickListener(rightPhotoClickListener);
            correctPhotoId = id;
        }
        else {
            displayedPhotos[index].setOnClickListener(wrongPhotoClickListener);
        }

    }

    private void drawGameInterface(int displayedPhotosCount) {

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

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
            rightAnswerChoosen();
        }
    };


    public View.OnClickListener wrongPhotoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View clickedPhoto) {
            wrongAnswerChoosen();
        }
    };

    public void wrongAnswerChoosen() {
        timer.cancel(true);
        successWithFirstClick = false;
        excerciseDescription.setText("Zle!!!! BLEEEEE!!!");

        exposeRightPhoto(ExcerciseStrategy.REPEAT_THE_SAME);
    }

    public void rightAnswerChoosen() {
        timer.cancel(true);
        excerciseDescription.setText("Dobrze");

        if(successWithFirstClick){
            categoriesToLearn.remove(currentCategoryToLearn);
        }

        exposeRightPhoto(ExcerciseStrategy.START_NEW);
    }

    private void exposeRightPhoto(ExcerciseStrategy strategy) {
        RelativeLayout layout = (RelativeLayout) activity.findViewById(R.id.layoutGame);
        correctPhoto = new ImageView(activity);
        RelativeLayout.LayoutParams imageLayoutParams = new RelativeLayout.LayoutParams(screenWidth-200, screenHeight-250);
        imageLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        correctPhoto.setLayoutParams(imageLayoutParams);
        correctPhoto.setImageResource(correctPhotoId);
        layout.addView(correctPhoto);
        for (int i = 0; i < displayedPhotosCount; i++) {
            displayedPhotos[i].imageView.setVisibility(View.INVISIBLE);
        }
        buttonNext = new Button(activity);
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        buttonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        buttonNext.setText("Dalej");
        buttonNext.setOnClickListener(new NextPhotoClickListener(strategy));
        layout.addView(buttonNext);
    }
//do wywalenia do osobnego pliku
    public class NextPhotoClickListener implements View.OnClickListener
    {
        ExcerciseStrategy strategy;
        public NextPhotoClickListener(ExcerciseStrategy strategy) {
            this.strategy = strategy;
        }

        @Override
        public void onClick(View clickedPhoto)
        {
            for (int i = 0; i < displayedPhotosCount; i++) {
                displayedPhotos[i].imageView.setVisibility(View.VISIBLE);
            }
            correctPhoto.setVisibility(View.GONE);
            buttonNext.setVisibility(View.GONE);

            startExercise(strategy);
        }
    }

    /*
    public View.OnClickListener nextPhotoListener = new View.OnClickListener( {
        @Override
        public void onClick(View clickedPhoto) {

            for (int i = 0; i < displayedPhotosCount; i++) {
                displayedPhotos[i].imageView.setVisibility(View.VISIBLE);
            }
            correctPhoto.setVisibility(View.GONE);
            buttonNext.setVisibility(View.GONE);

            startExercise();
        }
    };*/

    private void addSampleCategories() {
        //ktos wie czy w JAVIE jest coś takiego fajnego jak inicializatory (jak w C#)?? xd
        allCategories = new Category[4];
        Category cat1 = new Category();
        cat1.name = "lalka";
        cat1.elementsNumber = 3;
        Category cat11 = new Category();
        cat11.name = "lalka";
        cat11.elementsNumber = 3;
        allCategories[0] = cat1;
        categoriesToLearn.add(cat11);
        Category cat2 = new Category();
        cat2.name = "pies";
        cat2.elementsNumber = 3;
        allCategories[1] = cat2;
        Category cat22 = new Category();
        cat22.name = "pies";
        cat22.elementsNumber = 3;
        categoriesToLearn.add(cat22);
        Category cat3 = new Category();
        cat3.name = "samochod";
        cat3.elementsNumber = 3;
        allCategories[2] = cat3;
        Category cat33 = new Category();
        cat33.name = "samochod";
        cat33.elementsNumber = 3;
        categoriesToLearn.add(cat33);
        Category cat4 = new Category();
        cat4.name = "ser";
        cat4.elementsNumber = 3;
        allCategories[3] = cat4;
        Category cat44 = new Category();
        cat44.name = "ser";
        cat44.elementsNumber = 3;
        categoriesToLearn.add(cat44);
    }

    public void swap(int[] table, int a, int b){
        int c = table[a];
        table[a] = table[b];
        table[b] = c;
    }

    public void permutate(int[] table, int routeTimes)
    {
        Random rand = new Random();
        int n = table.length;
        for(int i = 0; i < routeTimes; ++i){
            int a = rand.nextInt(n);
            int b = rand.nextInt(n);

            swap(table,a,b);
        }
    }

}
