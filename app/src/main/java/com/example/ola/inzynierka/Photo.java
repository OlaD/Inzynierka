package com.example.ola.inzynierka;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class Photo {

    public Category category;
    public ImageView imageView;
    public TableRow.LayoutParams layoutParams;
    public boolean isCorrect;

    Photo(Context context, int width, int height, int marginTop, int marginRight){
        imageView = new ImageView(context);
        layoutParams = new TableRow.LayoutParams(width, height);
        layoutParams.setMargins(0, marginTop, marginRight, 0);
        imageView.setLayoutParams(layoutParams);
        imageView.setBackgroundColor(Color.WHITE);
    }

    public void setOnClickListener(View.OnClickListener photoClickListener)
    {
        imageView.setOnClickListener(photoClickListener);
    }

}
