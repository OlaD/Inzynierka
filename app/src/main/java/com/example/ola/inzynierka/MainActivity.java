package com.example.ola.inzynierka;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {

    //na razie tworze tutaj tablicę stringów lobalnie...bedzie ona reprezentowac czesci mowy, które maja byc dostepne do cwiczenia...docelowo, odczytane z bazy/pliku konfiguracyjnego
    private String[] partsOfSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        partsOfSpeech = new String[2];
        partsOfSpeech[0] = "czasowniki";
        partsOfSpeech[1] = "rzeczowniki";

        int numberOfButtons = partsOfSpeech.length;

        // dynamiczne robienie przycisków - w przyszłości pewnie w jakieś osobnej funkcji, zależne od parametru
        //RelativeLayout buttonsLayout = (RelativeLayout) findViewById(R.id.layoutMenuGlowne);
        //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //layoutParams.addRule(RelativeLayout.LayoutParams.WRAP_CONTENT);
        //RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
        //        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );



        LinearLayout buttonsLayout = (LinearLayout) findViewById(R.id.buttonsLayout);
        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //buttonsLayout.setLayoutParams(params);
        buttonsLayout.setOrientation(LinearLayout.VERTICAL);

        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, GraActivity.class);
                startActivity(intent);
            }
        };

        for(int i = 0; i < numberOfButtons; i++) {

            Button button = new Button(getApplicationContext());
            button.setText(partsOfSpeech[i]);
            button.setId(i);
            buttonsLayout.addView(button);
            button.setOnClickListener(buttonListener);
        }







/*
        Button buttonRzeczowniki = new Button(this);
        buttonRzeczowniki.setId(R.id.buttonRzeczowniki);    // IDki w pliku res->values->ids.xml
        buttonRzeczowniki.setText("Rzeczowniki");
        layout.addView(buttonRzeczowniki, layoutParams);
        */


        /*buttonRzeczowniki.setOnClickListener(buttonListener);

        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams2.addRule(RelativeLayout.BELOW, buttonRzeczowniki.getId());
        layoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);
        Button buttonCzasowniki = new Button(this);
        buttonCzasowniki.setId(R.id.buttonCzasowniki);
        buttonCzasowniki.setText("Czasowniki");
        layout.addView(buttonCzasowniki, layoutParams2);


        buttonCzasowniki.setOnClickListener(buttonListener);*/
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
