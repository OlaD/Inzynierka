package com.example.ola.inzynierka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // dynamiczne robienie przycisków - w przyszłości pewnie w jakieś osobnej funkcji
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layoutMenuGlowne);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        Button buttonRzeczowniki = new Button(this);
        buttonRzeczowniki.setId(R.id.buttonRzeczowniki);    // IDki w pliku res->values->ids.xml
        buttonRzeczowniki.setText("Rzeczowniki");
        layout.addView(buttonRzeczowniki, layoutParams);
        View.OnClickListener buttonRzeczownikiListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
            }
        };
        buttonRzeczowniki.setOnClickListener(buttonRzeczownikiListener);

        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams2.addRule(RelativeLayout.BELOW, buttonRzeczowniki.getId());
        layoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);
        Button buttonCzasowniki = new Button(this);
        buttonCzasowniki.setId(R.id.buttonCzasowniki);
        buttonCzasowniki.setText("Czasowniki");
        layout.addView(buttonCzasowniki, layoutParams2);
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
