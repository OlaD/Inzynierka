package com.example.ola.inzynierka;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class Photo {

    public Category category;
    public ImageView imageView;
    public boolean isCorrect;

    public FrameLayout frameLayout;

    Photo(Context context, int width, int height, int marginTop, int marginRight){

        imageView = new ImageView(context);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        imageView.setBackgroundColor(Color.WHITE);

        frameLayout = new FrameLayout(context);
        frameLayout.addView(imageView);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(width, height);
        layoutParams.setMargins(0, marginTop, marginRight, 0);
        frameLayout.setLayoutParams(layoutParams);
        addBorder(3);
    }

    public void setOnClickListener(View.OnClickListener photoClickListener)
    {
        imageView.setOnClickListener(photoClickListener);
    }

    public void addBorder(int borderWidth) {
        frameLayout.setPadding(borderWidth, borderWidth, borderWidth, borderWidth);
        frameLayout.setBackgroundColor(Color.BLACK);
    }
}
