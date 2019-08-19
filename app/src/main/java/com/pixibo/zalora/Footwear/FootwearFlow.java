package com.pixibo.zalora.Footwear;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.pixibo.zalora.R;

public class FootwearFlow extends AppCompatActivity {


    private TextView tv_fav_category;


    private String category = "[Brandname]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footwear_flow);


        tv_fav_category = findViewById(R.id.tv_fav_category);



        tv_fav_category.setText(getResources().getString(R.string.footwear_pick_category)+" "+category+" "+getResources().getString(R.string.footwear_pick_category_2));
    }
}
