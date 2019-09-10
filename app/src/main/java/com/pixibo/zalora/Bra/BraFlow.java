package com.pixibo.zalora.Bra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pixibo.zalora.R;
import com.pixibo.zalora.Utils.LocalData;

public class BraFlow extends AppCompatActivity {

    private String category = "";
    private String gender = "";
    private String preferredLanguage = "";
    private String clientId = "";
    private String skuId = "";
    private String altId = "";
    private String uID = "";
    private String [] availableSizeList ;

    private LocalData localData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bra_flow);


        localData = new LocalData(this);

        Intent intent = getIntent();

        category = intent.getStringExtra("dataType");
        gender = intent.getStringExtra("gender");

        clientId = intent.getStringExtra("clientId");
        skuId = intent.getStringExtra("skuId");
        altId = intent.getStringExtra("altId");
        uID = intent.getStringExtra("uID");
        preferredLanguage = intent.getStringExtra("preferredLanguage");
        availableSizeList = intent.getStringArrayExtra("availableSizeList");


    }
}
