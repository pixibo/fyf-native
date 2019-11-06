package com.pixibo.zalora;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pixibo.zalora.Apparel.ApparelFlow;
import com.pixibo.zalora.Utils.Utils;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    PixiboActivity pixiboActivity = new PixiboActivity();

    private String clientId = "qe3uhcp1kh11";
    private String skuId = "UN337US0SU6QMY";
    private String altId = "";
//    private String altId = "10115632608494085";
    private String uID = "";
    private String preferredLanguage = "en";
    private String [] availableSizeList = {"S","M","L","XL","UK 16"};

    private LinearLayout layout_button;
    private TextView tv_find_my_size;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout_button = findViewById(R.id.layout_button);
        tv_find_my_size = findViewById(R.id.tv_find_my_size);

        uID = Utils.deviceID(this);

        Locale locale;
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        switch (preferredLanguage)
        {
            case "en":
                locale = new Locale("en");
                break;
            case "in":
                locale = new Locale("in");
                break;
            case "hk":
                locale = new Locale("zh","HK");
                break;
            case "tw":
                locale = new Locale("zh","TW");
                break;
            default:
                locale = new Locale("en");
                break;
        }

        conf.setLocale(locale);
        res.updateConfiguration(conf, dm);


        pixiboActivity.get_final_size(clientId,skuId,altId,tv_find_my_size,layout_button,this);


        layout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PixiboActivity.class);
                intent.putExtra("clientId",clientId);
                intent.putExtra("skuId",skuId);
                intent.putExtra("altId",altId);
                intent.putExtra("uID",uID);
                intent.putExtra("preferredLanguage",preferredLanguage);
                intent.putExtra("availableSizeList",availableSizeList);
                intent.putExtra("isNew",true);
                startActivityForResult(intent,112);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 112) {

            if(resultCode == Activity.RESULT_OK){

                boolean recommended = data.getBooleanExtra("recommended",false);

                String result = null;

                result = data.getStringExtra("result");

                setButtonText(result,recommended);

                if (data.hasExtra("addToBag"))
                {
                    //TODO Implement logic for Add to Bag
                }
            }

        }
    }


    public void setButtonText(String size,boolean isRecommended)
    {

        String returnedText;

        SpannableString content;


        Log.e("size",size);
        Log.e("isRecommended", String.valueOf(isRecommended));

        if(isRecommended){

            returnedText = getResources().getString(R.string.your_size) +" "+size;
            content = new SpannableString(returnedText);

        }
        else if (!size.equals("") && !isRecommended)
        {

            returnedText = getResources().getString(R.string.check_your_fit);
            content = new SpannableString(returnedText);
        }
        else
        {
            returnedText = getResources().getString(R.string.find_my_size);
            content = new SpannableString(returnedText);
        }

        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        tv_find_my_size.setText(content);

        layout_button.setVisibility(View.VISIBLE);
    }

}
