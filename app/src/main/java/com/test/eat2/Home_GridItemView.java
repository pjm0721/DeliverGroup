package com.test.eat2;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Home_GridItemView extends LinearLayout {
    ImageView imageView;
    TextView textView;

    public Home_GridItemView(Context context){
        super(context);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.home_item_style, this, true);

        imageView = findViewById(R.id.imgView);
        textView = findViewById(R.id.txtView);
    }

    public void setImage(int resId){
        imageView.setImageResource(resId);
    }

    public void setTeamName(String teamName){
        textView.setText(teamName);
    }

}
