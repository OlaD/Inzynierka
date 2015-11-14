package com.example.ola.inzynierka;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ola.inzynierka.managers.AnimationManager;
import com.example.ola.inzynierka.managers.CategoriesManager;
import com.example.ola.inzynierka.managers.PhotosManager;
import com.example.ola.inzynierka.managers.SoundManager;

import java.util.Random;
import java.util.Stack;

public class Exercise {
    private TextView excerciseDescription;
    private int displayedPhotosCount;
    private int categoriesSize;
    private Photo[] displayedPhotos;

    private Category currentCategoryToLearn;
    private Activity activity;

    private int screenWidth;
    private int screenHeight;
    private int correctPhotoResId;
    private ImageView correctPhoto;
    private ImageButton buttonNext;
    private Button buttonGeneralization;
    private Button buttonRefresh;

    private ImageButton soundTube;

    private Stack<Integer> indexesOfPhotosPlaces;
    private boolean successWithFirstClick;
    private boolean hintShown;
    private boolean repeated;

    private Timer timer;
    private int timeForHint;
    private int timeForAnswer;
    private HintType hintType = HintType.BORDER;
    private SoundType exerciseSoundType = SoundType.EXERCISE2;
    private boolean wrongAnswerSound = false;

    private AnimationManager animationManager;
    private CategoriesManager categoriesToLearnManager;
    private CategoriesManager allCategoriesManager;
    private PhotosManager photosManager;
    private SoundManager soundManager;
    private int learningMode = 1; //na razie tylko 1 - terapeuty
    private int exerciseCounter = 0;
    private int correctAnswersCounter = 0;//na ile cwiczen odpowiedzano poprawnie...do sprawdzania czy dziecko umie dana kategorie
    private int exerciseRepeats = 1;// ...ile razy automatyczny ma powtarzać ćwiczenie dla jednej kategorii

    Exercise() {}
    Exercise(Activity activity, int displayedPhotosCount, int categoriesSize, int timeForHint, int timeForAnswer) {
        this.displayedPhotosCount = displayedPhotosCount;
        this.categoriesSize = categoriesSize;
        this.timeForHint = timeForHint;
        this.timeForAnswer = timeForAnswer;
        this.activity = activity;


        this.displayedPhotos = new Photo[displayedPhotosCount];

        this.indexesOfPhotosPlaces = new Stack<>();

        this.animationManager = new AnimationManager(activity);

        CategoriesReader categoriesReader = new CategoriesReader();
        this.categoriesToLearnManager = new CategoriesManager();
        this.allCategoriesManager = new CategoriesManager();
        this.photosManager = new PhotosManager();
        this.soundManager = new SoundManager();

        categoriesReader.read(allCategoriesManager,categoriesToLearnManager);

        //Na pewno potrzebne?? może do inicjalizatora
        successWithFirstClick = true;
        hintShown = false;
        repeated = false;

    }


    public void start() {
        drawGameInterface(displayedPhotosCount);
        startExercise(ExerciseStrategy.START_NEW);
    }


    int param = 2;
    boolean learning = false;
    boolean generalization = false;
    public void startExercise(ExerciseStrategy action) {
        if (categoriesToLearnManager.getCategoriesNumber() == 0) {
            Toast.makeText(activity, "Brak kategorii do nauki", Toast.LENGTH_SHORT).show();
            activity.finish();
            return;
        }

        generalization = false;
        if (learningMode == 2) {
            if (!learning) {
                if (exerciseCounter >= exerciseRepeats) {
                    if (correctAnswersCounter == exerciseRepeats) {
                        action = ExerciseStrategy.GENERALIZATION;
                        generalization = true;
                    } else {
                        learning = true;
                        exerciseCounter = 0;
                        action = ExerciseStrategy.REPEAT_CATEGORY_TO_LEARN;
                    }
                } else {
                    //pusto
                }
            } else if (exerciseCounter >= param) {
                exerciseCounter = 0;
                correctAnswersCounter = 0;
                learning = false;
                action = ExerciseStrategy.REPEAT_CATEGORY_TO_LEARN;
            } else {
                //action = ExerciseStrategy.REPEAT_CATEGORY_TO_LEARN;
            }
        }



        switch (action) {
            case START_NEW: {
                initializeExercise();
                chooseCategoryToLearn();
                choosePhotoForCategory(currentCategoryToLearn, indexesOfPhotosPlaces.pop(),false);
                choosePhotosForRandomCategories(displayedPhotosCount - 1);
                break;
            }
            case REPEAT_CATEGORY_TO_LEARN:
            {
                initializeExercise();
                currentCategoryToLearn.currentlyLearned = true;
                currentCategoryToLearn.isUsed = true;
                choosePhotoForCategory(currentCategoryToLearn, indexesOfPhotosPlaces.pop(),false);
                choosePhotosForRandomCategories(displayedPhotosCount - 1);
                break;
            }
            case REPEAT_THE_SAME:
            {
                hintShown = false;
                successWithFirstClick = true;
                break;
            }
            case GENERALIZATION:
            {
                initializeExercise();
                currentCategoryToLearn.currentlyLearned = true;
                currentCategoryToLearn.isUsed = true;
                choosePhotoForCategory(currentCategoryToLearn, indexesOfPhotosPlaces.pop(),true);
                choosePhotosForRandomCategories(displayedPhotosCount - 1);
                break;
            }
        }
        //askForAnswer();
        soundTube.setOnClickListener(soundTubeExerciseListener);
        soundManager.setSound(exerciseSoundType, activity, currentCategoryToLearn);
        soundManager.startPlay();
        setTimer();

    }


    private void chooseCategoryToLearn() {
        currentCategoryToLearn = categoriesToLearnManager.getRandomCategory();
        currentCategoryToLearn.currentlyLearned = true;
        currentCategoryToLearn.isUsed = true;
    }

    private void setTimer() {
        timer = new Timer(this);
        timer.execute(timeForHint, timeForAnswer);
    }

    /*private void askForAnswer() {
        excerciseDescription = (TextView) activity.findViewById(R.id.questionTextView);
        excerciseDescription.setText("Gdzie jest " + currentCategoryToLearn.name + "?");
    }*/


    private void initializeExercise() {
        assetRandomPhotosOrder();
        successWithFirstClick = true;
        hintShown = false;
        repeated = false;
        allCategoriesManager.resetCategories();
    }

    private void assetRandomPhotosOrder() {
        int[] temp = new int[displayedPhotosCount];
        for(int i = 0; i < displayedPhotosCount; ++i){
            temp[i] = i;
        }
        permute(temp, displayedPhotosCount);
        for(int i = 0; i < displayedPhotosCount; ++i){
            indexesOfPhotosPlaces.push(temp[i]);
        }
    }



    private void choosePhotosForRandomCategories(int number) {
        int chosenCategoriesNumber = 0;
        while(chosenCategoriesNumber < number) {
            Category category = allCategoriesManager.getRandomCategory();
            choosePhotoForCategory(category, indexesOfPhotosPlaces.pop(), false);
            category.isUsed = true;
            chosenCategoriesNumber++;
        }
    }

    private void choosePhotoForCategory(Category category, int index, boolean generalization) {
        int photoId = photosManager.getPhotoIdForCategory(category,activity, generalization);
        displayedPhotos[index].imageView.setImageResource(photoId);
        setPhotoListener(category, index, photoId);
    }

    private void setPhotoListener(Category category, int index, int photoId) {
        if (category.currentlyLearned) {
            displayedPhotos[index].setOnClickListener(rightPhotoClickListener);
            displayedPhotos[index].isCorrect = true;
            correctPhotoResId = photoId;
        }
        else {
            displayedPhotos[index].setOnClickListener(wrongPhotoClickListener);
            displayedPhotos[index].isCorrect = false;
        }
    }


    private void drawGameInterface(int displayedPhotosCount) {
        buttonNext = (ImageButton) activity.findViewById(R.id.buttonNext);
        buttonGeneralization = (Button) activity.findViewById(R.id.buttonGeneralization);
        buttonRefresh = (Button) activity.findViewById(R.id.buttonRefresh);
        soundTube = (ImageButton) activity.findViewById(R.id.buttonSoundTube);

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
                photoWidth = screenWidth/3;
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
            case 6:
                rowCount = 2;
                photosPerRow = 3;
                rowMargin = 20;
                columnMargin = 20;
                photoWidth = screenWidth/4;
                photoHeight = screenHeight/4;
                break;
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
                rows[i].addView(displayedPhotos[tmp].frameLayout);
                tmp++;
            }
        }

        // Expose right photo
        RelativeLayout layout = (RelativeLayout) activity.findViewById(R.id.layoutGame);
        correctPhoto = new ImageView(activity);
        RelativeLayout.LayoutParams imageLayoutParams = new RelativeLayout.LayoutParams(screenWidth-200, screenHeight-250);
        imageLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        correctPhoto.setLayoutParams(imageLayoutParams);
        correctPhoto.setVisibility(View.GONE);
        correctPhoto.setBackgroundColor(Color.WHITE);
        layout.addView(correctPhoto);
    }

    View.OnClickListener soundTubeExerciseListener = new View.OnClickListener() {
        @Override
        public void onClick(View clickedPhoto) {
            soundManager.setSound(exerciseSoundType, activity, currentCategoryToLearn);
            soundManager.startPlay();
        }
    };

    View.OnClickListener soundTubeCorrectAnswerListener = new View.OnClickListener() {
        @Override
        public void onClick(View clickedPhoto) {
            soundManager.setSound(SoundType.CORRECT, activity, currentCategoryToLearn);
            soundManager.startPlay();
        }
    };

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
            //excerciseDescription.setText("Zle!!!! BLEEEEE!!!");
            //soundManager.unload();
            if (wrongAnswerSound) {
                soundManager.setSound(SoundType.WRONG, activity, null);
                soundManager.startPlay();
            }
        }
    };

    public void showHint() {
        hintShown = true;
        for (int i = 0; i < displayedPhotosCount; i++) {
            if (hintType == HintType.FADE && !displayedPhotos[i].isCorrect) {
                displayedPhotos[i].imageView.setImageAlpha(50);
            }
            if (hintType == HintType.BORDER && displayedPhotos[i].isCorrect) {
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

        if(!hintShown)
        {
            showHint();
        }
        /*else
        {}
            exposeRightPhoto(ExerciseStrategy.REPEAT_THE_SAME);
        */
    }
public boolean flag = false;
    public void rightAnswerChosen() {
        timer.cancel(true);
        //excerciseDescription.setText("Dobrze");

        if(learningMode == 1){
            exposeRightPhoto(ExerciseStrategy.REPEAT_CATEGORY_TO_LEARN);
        }
        else if(successWithFirstClick){
            if(!repeated && !hintShown) {

                if (generalization) {
                    categoriesToLearnManager.removeCategory(currentCategoryToLearn);
                    correctAnswersCounter = 0;
                    exerciseCounter = 0;
                    exposeRightPhoto(ExerciseStrategy.START_NEW);
                }else {

                    exposeRightPhoto(ExerciseStrategy.REPEAT_CATEGORY_TO_LEARN);
                    ++exerciseCounter;
                    ++correctAnswersCounter;
                }

            }
            else {
                exposeRightPhoto(ExerciseStrategy.REPEAT_CATEGORY_TO_LEARN);
                if(generalization){
                    correctAnswersCounter = 0;
                    exerciseCounter = 0;
                }
                ++exerciseCounter;
            }
        }
        else
        {
            if(generalization) {
                correctAnswersCounter = 0;
                exerciseCounter = 0;
                exposeRightPhoto(ExerciseStrategy.REPEAT_CATEGORY_TO_LEARN);
            } else {
                exposeRightPhoto(ExerciseStrategy.REPEAT_THE_SAME);
                //++exerciseCounter;
            }
        }
        //soundManager.unload();
        soundManager.setSound(SoundType.CORRECT, activity, currentCategoryToLearn);
        soundManager.startPlay();
        flag = true;

    }

    private void exposeRightPhoto(ExerciseStrategy strategy) {
        for (int i = 0; i < displayedPhotosCount; i++) {
            displayedPhotos[i].frameLayout.setVisibility(View.GONE);
        }

        correctPhoto.setImageResource(correctPhotoResId);
        correctPhoto.setVisibility(View.VISIBLE);

        buttonNext.bringToFront();
        buttonGeneralization.bringToFront();
        buttonRefresh.bringToFront();

        Animation animation = animationManager.getRandomAnimation();
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                buttonNext.setVisibility(View.VISIBLE);
                if (learningMode == 1) {
                    buttonGeneralization.setVisibility(View.VISIBLE);
                    buttonRefresh.setVisibility(View.VISIBLE);
                }
            }
        });

        soundTube.setOnClickListener(soundTubeCorrectAnswerListener);
        buttonNext.setOnClickListener(new NextPhotoClickListener(strategy));
        buttonGeneralization.setOnLongClickListener(new NextPhotoClickListener(ExerciseStrategy.GENERALIZATION));
        buttonRefresh.setOnLongClickListener(new NextPhotoClickListener(ExerciseStrategy.REPEAT_THE_SAME));
        correctPhoto.startAnimation(animation);
    }


    public class NextPhotoClickListener implements View.OnClickListener, View.OnLongClickListener
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
            buttonGeneralization.setVisibility(View.GONE);
            buttonRefresh.setVisibility(View.GONE);

            correctPhoto.clearAnimation();

            startExercise(strategy);
        }

        @Override
        public boolean onLongClick(View v) {
            onClick(v);
            return true;
        }
    }


    public void swap(int[] table, int a, int b){
        int c = table[a];
        table[a] = table[b];
        table[b] = c;
    }

    public void permute(int[] table, int routeTimes)
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
