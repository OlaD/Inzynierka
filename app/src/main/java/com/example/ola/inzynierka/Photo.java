package com.example.ola.inzynierka;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Photo {

    public Category category;
    public ImageView imageView;
    public RelativeLayout.LayoutParams layoutParams;
    int wysokosc;
    int szerokosc;
    //View.OnClickListener photoClickedListener;

    Photo(Context context, int szerokosc, int wysokosc){
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
        layoutParams = new RelativeLayout.LayoutParams(szerokosc, wysokosc);
        imageView.setLayoutParams(layoutParams);


    }
    public void setOnClickListener(View.OnClickListener photoClickListener)
    {
        imageView.setOnClickListener(photoClickListener);
    }
}
