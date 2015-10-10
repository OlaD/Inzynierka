package com.example.ola.inzynierka;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class Photo {

    public Category category;
    public ImageView imageView;
    public TableRow.LayoutParams layoutParams;
    //View.OnClickListener photoClickedListener;

    Photo(Context context, int width, int height){
        imageView = new ImageView(context);
        layoutParams = new TableRow.LayoutParams(width, height);
        imageView.setLayoutParams(layoutParams);
    }

    public void setOnClickListener(View.OnClickListener photoClickListener)
    {
        imageView.setOnClickListener(photoClickListener);
    }
}
