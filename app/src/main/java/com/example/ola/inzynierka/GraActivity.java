package com.example.ola.inzynierka;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import java.util.Random;

public class GraActivity extends AppCompatActivity {

    private int photosWorkingSetSize;
    private Zdjecie[] photosWorkingSet;
    private int photosNumber;
    private boolean[] chosenCategories;
    private Category[] categories;
    private Category currentCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gra);



        showPhotosSet();
    }

    private void showPhotosSet() {
        photosWorkingSetSize = 3;
        photosNumber = 4;
        photosWorkingSet = new Zdjecie[photosWorkingSetSize];
        chosenCategories = new boolean[photosNumber];

        addSampleCotegories();

        rysujInterfejsGry(photosWorkingSetSize);

        int currentCategoryIndex = chooseRandomCategory();

        currentCategory = categories[currentCategoryIndex];
        chosenCategories[currentCategoryIndex] = true;

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layoutGra);

        choosePhotoForCategory(currentCategory,0);
        choosePhotosForRandomCategories();

        for (int i = 0; i < photosWorkingSetSize; i++) {
            layout.addView(photosWorkingSet[i].imageView);
        }
    }

    private void addSampleCotegories() {
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

    private void choosePhotosForRandomCategories() {

        int chosenCategoriesNumber = 1;
        while(chosenCategoriesNumber < photosWorkingSetSize) {
            int randInt = chooseRandomCategory();
            if(chosenCategories[randInt] == true) {
                continue;
            }
            chosenCategories[randInt] = true;
            choosePhotoForCategory(categories[randInt], chosenCategoriesNumber);


            chosenCategoriesNumber++;
        }
    }

    private int chooseRandomCategory() {
        Random rand = new Random();
        return rand.nextInt(categories.length);
    }

    private void choosePhotoForCategory(Category category,int index) {
        Random rand = new Random();
        int randInt = rand.nextInt(category.elementsNumber);
        String imageName = category.name + randInt;
        int id = getResources().getIdentifier(imageName, "drawable", getPackageName());
        photosWorkingSet[index].imageView.setImageResource(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gra, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void rysujInterfejsGry(int liczbaZdjec) {

        int szerokoscZdjec = 0;
        int wysokoscZdjec = 0;

        Display display = getWindowManager().getDefaultDisplay();
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
                    photosWorkingSet[i] = new Zdjecie(this, szerokoscZdjec, wysokoscZdjec);
                }
                photosWorkingSet[0].imageView.setId(R.id.zdjecie1);

                photosWorkingSet[1].layoutParams.leftMargin = 25;
                photosWorkingSet[1].layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.zdjecie1);

                photosWorkingSet[2].layoutParams.addRule(RelativeLayout.BELOW, R.id.zdjecie1);
                photosWorkingSet[2].layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

                break;
        }// switch

    }// rysujInterfejsGry()



}
