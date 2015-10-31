package com.example.ola.inzynierka;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Exercise {
    private TextView excerciseDescription;
    private int displayedPhotosCount;
    private int categoriesSize;
    private Photo[] displayedPhotos;
    private boolean[] chosenCategories;
    //private Category[] allCategories;
    private List<Category> allCategories;
    private List<Category> categoriesToLearn;
    private Category currentCategoryToLearn;
    private Activity activity;

    private int screenWidth;
    private int screenHeight;
    private int correctPhotoResId;
    private ImageView correctPhoto;
    private ImageButton buttonNext;
    private Stack<Integer> indexesOfPhotosPlaces;
    private boolean successWithFirstClick;
    private boolean hintShown;
    private boolean repeated;

    private Timer timer;
    private int timeForHint;
    private int timeForAnswer;
    private HintType hintType = HintType.BORDER;

    private Animation[] animations;

    Exercise() {}
    Exercise(Activity activity, int displayedPhotosCount, int categoriesSize, int timeForHint, int timeForAnswer) {
        this.displayedPhotosCount = displayedPhotosCount;
        this.categoriesSize = categoriesSize;
        this.timeForHint = timeForHint;
        this.timeForAnswer = timeForAnswer;
        this.activity = activity;
        this.allCategories = new ArrayList<>();
        displayedPhotos = new Photo[displayedPhotosCount];
        chosenCategories = new boolean[categoriesSize];
        indexesOfPhotosPlaces = new Stack<Integer>();
        successWithFirstClick = true;
        hintShown = false;
        repeated = false;
        categoriesToLearn = new ArrayList<>();


    }


    public void start() {
        addSampleCategories();
        addAnimations();
        drawGameInterface(displayedPhotosCount);
        startExercise(ExerciseStrategy.START_NEW);
    }



    public void startExercise(ExerciseStrategy action) {

        switch (action) {
            case START_NEW: {
                initializeExercise();
                chooseCategoryToLearn();

                choosePhotosForRandomCategories(displayedPhotosCount - 1);
                askForAnswer();
                setTimer();
                break;
            }
            case REPEAT_CATEGORY_TO_LEARN:
            {
                choosePhotosForRandomCategories(displayedPhotosCount - 1);
                askForAnswer();
                setTimer();
                break;
            }
            case REPEAT_THE_SAME:
            {
                hintShown = false;
                successWithFirstClick = true;
                askForAnswer();
                setTimer();
                break;
            }
        }

    }

    private void chooseCategoryToLearn() {
        currentCategoryToLearn = getRandomCategory(categoriesToLearn);
        currentCategoryToLearn.toLearn = true;
        choosePhotoForCategory(currentCategoryToLearn, indexesOfPhotosPlaces.pop());
    }

    private void setTimer() {
        timer = new Timer(this);
        timer.execute(timeForHint, timeForAnswer);
    }

    private void askForAnswer() {
        excerciseDescription = (TextView) activity.findViewById(R.id.questionTextView);
        excerciseDescription.setText("Gdzie jest " + currentCategoryToLearn.name + "?");
    }

    private void initializeExercise() {
        assetRandomPhotosOrder();
        successWithFirstClick = true;
        hintShown = false;
        repeated = false;
        for (int i = 0; i < categoriesSize; i++) {
            chosenCategories[i] = false;
        }
        for(int i = 0; i < allCategories.size(); ++i) {
            allCategories.get(i).chosen = false;
            allCategories.get(i).toLearn = false;
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

    private Category getRandomCategory(List<Category> categoryList)
    {
        Random rand = new Random();
        int index;
        do {
            index = rand.nextInt(categoryList.size());
        }while(categoryList.get(index).chosen);

        Category category = categoryList.get(index);
        category.chosen = true;
        return category;
    }

    private void choosePhotosForRandomCategories(int number) {

        int chosenCategoriesNumber = 0;
        while(chosenCategoriesNumber < number) {
            Category category = getRandomCategory(allCategories);
            choosePhotoForCategory(category, indexesOfPhotosPlaces.pop());
            chosenCategoriesNumber++;
        }
    }




    private void choosePhotoForCategory(Category category, int index) {
        Random rand = new Random();
        int randInt = rand.nextInt(category.elementsNumber);
        String imageName = category.name + randInt;
        int id = activity.getResources().getIdentifier(imageName, "drawable", activity.getPackageName());
        displayedPhotos[index].imageView.setImageResource(id);
        displayedPhotos[index].isCorrect = category.toLearn;
        if (category.toLearn) {
            displayedPhotos[index].setOnClickListener(rightPhotoClickListener);
            correctPhotoResId = id;
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
        int rowMargin = 0;
        int columnMargin = 0;
        int photoWidth = 0;
        int photoHeight = 0;

        switch (displayedPhotosCount) {
            case 2:
                rowCount = 1;
                photosPerRow = 2;
                rowMargin = 20;
                columnMargin = 20;
                photoWidth = screenWidth/2;
                photoHeight = screenHeight/2;
                break;
            case 3:
                rowCount = 1;
                photosPerRow = 3;
                rowMargin = 20;
                columnMargin = 20;
                photoWidth = screenWidth/4;
                photoHeight = screenHeight/4;
                break;
            case 4:
                rowCount = 2;
                photosPerRow = 2;
                rowMargin = 20;
                columnMargin = 20;
                photoWidth = screenWidth/3;
                photoHeight = screenHeight/3;
                break;
            /*case 6:
                rowCount = 2;
                photosPerRow = 3;
                rowMargin = 20;
                columnMargin = 20;
                photoWidth = screenWidth/4;
                photoHeight = screenHeight/4;
                break;*/
        }

        for (int i = 0; i < displayedPhotosCount; i++) {
            displayedPhotos[i] = new Photo(activity, photoWidth, photoHeight, rowMargin, columnMargin);
        }

        rows = new TableRow[rowCount];
        for (int i = 0; i < rowCount; i++) {
            rows[i] = new TableRow(activity);
            tableLayout.addView(rows[i]);
        }

        int tmp = 0;
        for(int i = 0; i < rowCount; i++) {
            for (int j = 0; j < photosPerRow; j++) {
                rows[i].addView(displayedPhotos[tmp].frameLayout);//.imageView);
                tmp++;
            }
        }
    }

    View.OnClickListener rightPhotoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View clickedPhoto) {
            rightAnswerChosen();
        }
    };


    public View.OnClickListener wrongPhotoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View clickedPhoto) {
            wrongAnswerChosen();
        }
    };

    public void showHint() {
        hintShown = true;
        for (int i = 0; i < displayedPhotosCount; i++) {
            if (hintType == HintType.FADE && displayedPhotos[i].isCorrect == false) {
                displayedPhotos[i].imageView.setImageAlpha(50);
            }
            if (hintType == HintType.BORDER && displayedPhotos[i].isCorrect == true) {
                displayedPhotos[i].addBorder(10);
                break;
            }
        }

    }

    public void timeOut(){
        timer.cancel(true);
        wrongAnswerChosen();
    }

    public void wrongAnswerChosen() {
        successWithFirstClick = false;
        repeated = true;
        excerciseDescription.setText("Zle!!!! BLEEEEE!!!");
        if(hintShown == false)
        {
            showHint();
        }
        /*else
        {}
            exposeRightPhoto(ExerciseStrategy.REPEAT_THE_SAME);
        */
    }

    public void rightAnswerChosen() {
        timer.cancel(true);
        excerciseDescription.setText("Dobrze");

        if(successWithFirstClick == true){
            if(repeated == false && hintShown == false
                    ) {
                categoriesToLearn.remove(currentCategoryToLearn);
            }

            exposeRightPhoto(ExerciseStrategy.START_NEW);

        }
        else
        {
            exposeRightPhoto(ExerciseStrategy.REPEAT_THE_SAME);
        }


    }

    private void exposeRightPhoto(ExerciseStrategy strategy) {
        RelativeLayout layout = (RelativeLayout) activity.findViewById(R.id.layoutGame);
        correctPhoto = new ImageView(activity);
        RelativeLayout.LayoutParams imageLayoutParams = new RelativeLayout.LayoutParams(screenWidth-200, screenHeight-250);
        imageLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
//        imageLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        correctPhoto.setLayoutParams(imageLayoutParams);
        correctPhoto.setImageResource(correctPhotoResId);
        layout.addView(correctPhoto);
        for (int i = 0; i < displayedPhotosCount; i++) {
            displayedPhotos[i].frameLayout.setVisibility(View.INVISIBLE);
        }

        buttonNext = new ImageButton(activity);
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(300, 200);
        buttonNext.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        buttonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        buttonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        buttonNext.setImageResource(R.drawable.garrow);
        buttonNext.setOnClickListener(new NextPhotoClickListener(strategy));
        buttonNext.setBackgroundColor(Color.TRANSPARENT);
        layout.addView(buttonNext, buttonLayoutParams);

        Random rand = new Random();
        correctPhoto.startAnimation(animations[rand.nextInt(animations.length)]);
    }

//do wywalenia do osobnego pliku
    public class NextPhotoClickListener implements View.OnClickListener
    {
        ExerciseStrategy strategy;
        public NextPhotoClickListener(ExerciseStrategy strategy) {
            this.strategy = strategy;
        }

        @Override
        public void onClick(View clickedPhoto)
        {
            for (int i = 0; i < displayedPhotosCount; i++) {
                displayedPhotos[i].frameLayout.setVisibility(View.VISIBLE);
                displayedPhotos[i].imageView.setImageAlpha(255);
                displayedPhotos[i].addBorder(3);
            }
            correctPhoto.setVisibility(View.GONE);
            buttonNext.setVisibility(View.GONE);

            correctPhoto.clearAnimation();

            startExercise(strategy);
        }
    }

    private void addAnimations() {
        animations = new Animation[4];
        animations[0] = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.rotate);
        animations[1] = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.blink);
        animations[2] = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.scale);
        animations[3] = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.move);
    }

    private void addSampleCategories() {
        //ktos wie czy w JAVIE jest coś takiego fajnego jak inicializatory (jak w C#)?? xd

        Category cat1 = new Category();
        cat1.name = "lalka";
        cat1.elementsNumber = 3;
        categoriesToLearn.add(cat1);
        allCategories.add(cat1);
        Category cat2 = new Category();
        cat2.name = "pies";
        cat2.elementsNumber = 3;
        categoriesToLearn.add(cat2);
        allCategories.add(cat2);
        Category cat3 = new Category();
        cat3.name = "samochod";
        cat3.elementsNumber = 3;
        categoriesToLearn.add(cat3);
        allCategories.add(cat3);
        Category cat4 = new Category();
        cat4.name = "ser";
        cat4.elementsNumber = 3;
        categoriesToLearn.add(cat4);
        allCategories.add(cat4);
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
