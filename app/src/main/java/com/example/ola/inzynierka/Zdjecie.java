package com.example.ola.inzynierka;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Zdjecie {

    public String kategoria;
    public ImageView imageView;
    public RelativeLayout.LayoutParams layoutParams;
    int wysokosc;
    int szerokosc;

    Zdjecie(Context context, int szerokosc, int wysokosc){
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
        layoutParams = new RelativeLayout.LayoutParams(szerokosc, wysokosc);
        imageView.setLayoutParams(layoutParams);
    }
}
