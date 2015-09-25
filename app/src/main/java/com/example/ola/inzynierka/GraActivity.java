package com.example.ola.inzynierka;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

public class GraActivity extends AppCompatActivity {

    private int liczbaZdjec;
    private Zdjecie[] zdjecia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gra);

        liczbaZdjec = 3;
        zdjecia = new Zdjecie[liczbaZdjec];

        rysujInterfejsGry(liczbaZdjec);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layoutGra);

        zdjecia[0].imageView.setImageResource(R.drawable.pies);
        zdjecia[1].imageView.setImageResource(R.drawable.ser);
        zdjecia[2].imageView.setImageResource(R.drawable.samochod);

        for (int i=0; i<liczbaZdjec; i++) {
            layout.addView(zdjecia[i].imageView);
        }
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

        switch (liczbaZdjec) {
            case 3:
                szerokoscZdjec = szerokoscEkranu/2-75;
                wysokoscZdjec = wysokoscEkranu/2;
                for (int i=0; i<3; i++){
                    zdjecia[i] = new Zdjecie(this, szerokoscZdjec, wysokoscZdjec);
                }
                zdjecia[0].imageView.setId(R.id.zdjecie1);

                zdjecia[1].layoutParams.leftMargin = 25;
                zdjecia[1].layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.zdjecie1);

                zdjecia[2].layoutParams.addRule(RelativeLayout.BELOW, R.id.zdjecie1);
                zdjecia[2].layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

                break;
        }// switch

    }// rysujInterfejsGry()

}
