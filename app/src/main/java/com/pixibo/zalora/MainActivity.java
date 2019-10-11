package com.pixibo.zalora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pixibo.zalora.Apparel.ApparelFlow;
import com.pixibo.zalora.Utils.Utils;

public class MainActivity extends AppCompatActivity {

    PixiboActivity pixiboActivity = new PixiboActivity();

    private String clientId = "sl8zvzsjelpg";
    private String skuId = "BC421AADC2CE9DGS";
    private String altId = "10214810760805751";
    private String uID = "";
    private String preferredLanguage = "en";

    private LinearLayout layout_button;
    private TextView tv_find_my_size;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout_button = findViewById(R.id.layout_button);
        tv_find_my_size = findViewById(R.id.tv_find_my_size);

        uID = Utils.deviceID(this);


        pixiboActivity.get_final_size(clientId,skuId,altId,tv_find_my_size,layout_button,this);



        layout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PixiboActivity.class);
                startActivityForResult(intent,112);
            }
        });
    }


}
