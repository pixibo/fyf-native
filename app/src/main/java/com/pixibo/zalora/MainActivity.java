package com.pixibo.zalora;



import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pixibo.zalora.Apparel.ApparelFlow;
import com.pixibo.zalora.Footwear.FootwearFlow;
import com.pixibo.zalora.Utils.Utils;
import com.pixibo.zalora.Utils.WebResources.GET;
import com.pixibo.zalora.Utils.WebResources.NetworkUtils;
import com.pixibo.zalora.Utils.WebResources.POST;
import com.pixibo.zalora.Utils.WebResources.Result;
import com.pixibo.zalora.Utils.WebResources.URI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Locale;

import static com.pixibo.zalora.Utils.Utils.TYPE.MergeProfile;
import static com.pixibo.zalora.Utils.Utils.TYPE.ResetProfile;
import static com.pixibo.zalora.Utils.Utils.TYPE.SizeFromApi;
import static com.pixibo.zalora.Utils.Utils.TYPE.UpdateProfile;
import static com.pixibo.zalora.Utils.Utils.TYPE.ValidateUserAltId;
import static com.pixibo.zalora.Utils.Utils.TYPE.ValidateUserUid;

public class MainActivity extends AppCompatActivity implements Result {

    private LinearLayout layout_button;
    private String clientId = "qe3uhcp1kh11";
    private String skuId = "A9D56SH00ED8EDGS";
    private String altId = "";
    private String uID = "";
    private String preferredLanguage = "en";
    private String sizeUrl = null;
    private String response = null;

    private TextView tv_find_my_size;

    //  private String uId = Utils.deviceID(this);

    String[] apparel_array = new String[]{"Tops", "Dresses", "Coats", "Jeans", "Jumpsuits", "Outwear", "Pants", "Shirts", "Skirts", "Shorts"};
    String[] footwear_array = new String[]{"Shoes"};
    String[] lingerie_array = new String[]{"Bra"};

    String dataType = "";
    String gender = "";

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = getIntent();

        uID = Utils.deviceID(this);


        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(preferredLanguage)); // API 17+ only.
        res.updateConfiguration(conf, dm);


        layout_button = findViewById(R.id.layout_button);
        tv_find_my_size = findViewById(R.id.tv_find_my_size);


        layout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Arrays.asList(apparel_array).contains(dataType))
                {
                    intent = new Intent(MainActivity.this, ApparelFlow.class);
                    intent.putExtra("dataType",dataType);
                    intent.putExtra("gender",gender);
                    intent.putExtra("clientId",clientId);
                    intent.putExtra("skuId",skuId);
                    intent.putExtra("altId",altId);
                    intent.putExtra("uID",uID);
                    intent.putExtra("isEdit",false);
                    intent.putExtra("isNew",false);
                    startActivityForResult(intent,111);
                }

                else if (Arrays.asList(footwear_array).contains(dataType))
                {
                    intent = new Intent(MainActivity.this, FootwearFlow.class);
                    intent.putExtra("dataType",dataType);
                    intent.putExtra("gender",gender);
                    intent.putExtra("clientId",clientId);
                    intent.putExtra("skuId",skuId);
                    intent.putExtra("altId",altId);
                    intent.putExtra("uID",uID);
                    startActivityForResult(intent,111);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Lingerie",Toast.LENGTH_SHORT).show();
                }



            }
        });


        validate_user(clientId,skuId);

    }


    private void validate_user(String clientId , String skuId) {


        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {

                if (altId.equals(""))
                {
                    GET get = new GET(this, URI.validate +clientId+"/"+skuId+"?uid="+uID , ValidateUserUid, this);
                    get.execute();
                }
                else
                {
                    GET get = new GET(this, URI.validate +clientId+"/"+skuId+"?uid="+altId , ValidateUserAltId, this);
                    get.execute();
                }

            } else {
                Utils.showToast(this,getResources().getString(R.string.no_internet));
            }

        } catch (Exception e) {
            //Utils.hideLoading();
            Utils.showToast(this,getResources().getString(R.string.something_wrong));
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
        }
    }


    private void fetchSizeFromApi(String url) {


        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {
                GET get = new GET(this, url , SizeFromApi, this);
                // Utils.showLoading(SettingActivity.this, false);
                get.execute();
            } else {
                Utils.showToast(this,getResources().getString(R.string.no_internet));
            }

        } catch (Exception e) {
            //Utils.hideLoading();
            Utils.showToast(this,getResources().getString(R.string.something_wrong));
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
        }
    }


    private void altIdHasProfile(String altId, String uID) {


        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {
                GET get = new GET(this, "https://discoverysvc.pixibo.com/merge/users/"+altId+"/"+uID , MergeProfile, this);
                // Utils.showLoading(SettingActivity.this, false);
                get.execute();
            } else {
                Utils.showToast(this,getResources().getString(R.string.no_internet));
            }

        } catch (Exception e) {
            //Utils.hideLoading();
            Utils.showToast(this,getResources().getString(R.string.something_wrong));
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
        }
    }


    private void resetClientProfile(String clientId, String uID) {


        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {
                GET get = new GET(this, "https://discoverysvc.pixibo.com/reset/"+clientId+"/"+uID , ResetProfile, this);
                // Utils.showLoading(SettingActivity.this, false);
                get.execute();
            } else {
                Utils.showToast(this,getResources().getString(R.string.no_internet));
            }

        } catch (Exception e) {
            //Utils.hideLoading();
            Utils.showToast(this,getResources().getString(R.string.something_wrong));
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
        }
    }



    private void updateCreateUserData( String uID) {


        try {

            JSONObject body = new JSONObject();

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {

                if (altId.equals(""))
                {
                    body.put("uid",uID);
                    body.put("gender",gender);

                    POST post = new POST(this, "https://discoverysvc.pixibo.com/uid" , body,UpdateProfile, this);
                    post.execute();
                }
                else
                {
                    body.put("uid",uID);
                    body.put("gender",gender);
                    body.put("altId",altId);

                    POST post = new POST(this, "https://discoverysvc.pixibo.com/uid" , body,UpdateProfile, this);
                    post.execute();
                }

            } else {
                Utils.showToast(this,getResources().getString(R.string.no_internet));
            }

        } catch (Exception e) {
            //Utils.hideLoading();
            Utils.showToast(this,getResources().getString(R.string.something_wrong));
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
        }
    }




    @Override
    public void getWebResponse(String result, Utils.TYPE type, int statusCode) {

        switch (type)
        {
            case ValidateUserUid:
                try
                {

                    if (statusCode == 200)
                    {


                    JSONObject userObject = new JSONObject(result);

                        if (userObject.has("type")) {

                            dataType = userObject.optString("type");

                            gender = userObject.optString("gender");

                            if(userObject.has("userInfo"))
                            {
                                JSONObject userInfoObject = new JSONObject(userObject.optString("userInfo"));

                                if (userInfoObject.optString("uid").equals(null))
                                {
                                    updateCreateUserData(uID);
                                }


                                if(Arrays.asList(apparel_array).contains(dataType)){

                                    if(gender.equals("male"))
                                    {
                                        sizeUrl = getApparelMaleUrl(userInfoObject,apparel_array, clientId, skuId, uID);

                                        if(sizeUrl != null){
                                            fetchSizeFromApi(sizeUrl);
                                        }
                                        else
                                        {
                                            layout_button.setVisibility(View.VISIBLE);
                                            SpannableString content;
                                            content = new SpannableString(getResources().getString(R.string.find_my_size));
                                            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                            tv_find_my_size.setText(content);
                                        }
                                    }
                                    else if(gender.equals("female"))
                                    {
                                        sizeUrl = getApparelFemaleUrl(userInfoObject,apparel_array, clientId, skuId, uID);

                                        if(sizeUrl != null){
                                            fetchSizeFromApi(sizeUrl);
                                        }
                                        else
                                        {
                                            layout_button.setVisibility(View.VISIBLE);
                                            SpannableString content;
                                            content = new SpannableString(getResources().getString(R.string.find_my_size));
                                            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                            tv_find_my_size.setText(content);
                                        }

                                    }

                                }

//                                else if (Arrays.asList(footwear_array).contains(type))
//                                {
//                                    if(gender.equals("male")){
//                                        sizeUrl = getMaleFootwearUrl(userInfoObject,footwear_array, clientId, skuId, uID);
//                                    }
//                                    else if(gender.equals("female")){
//                                        sizeUrl = getFemaleFootwearUrl(userInfoObject,footwear_array, clientId, skuId, uID);
//                                    }
//                                }
//
//                                else if (Arrays.asList(lingerie_array).contains(type))
//                                {
//                                    sizeUrl = null;
//                                }
//

                            }
                            else
                            {

                                layout_button.setVisibility(View.VISIBLE);
                                SpannableString content;
                                content = new SpannableString("FIND MY SIZE");
                                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                tv_find_my_size.setText(content);
                            }


                        }
                        else
                        {
                            layout_button.setVisibility(View.VISIBLE);
                            SpannableString content;
                            content = new SpannableString("FIND MY SIZE");
                            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                            tv_find_my_size.setText(content);
                        }

                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;


            case ValidateUserAltId:
                try
                {

                    if (statusCode == 200)
                    {


                    JSONObject userObject = new JSONObject(result);

                        if (userObject.has("type")) {

                            dataType = userObject.optString("type");
                            gender = userObject.optString("gender");

                            if(userObject.has("userInfo"))
                            {
                                JSONObject userInfoObject = new JSONObject(userObject.optString("userInfo"));

                                if (!userInfoObject.optString("uid").equals(null))
                                {
                                    altIdHasProfile(userInfoObject.optString("altId"),userInfoObject.optString("uid"));
                                }
                                else
                                {
                                    updateCreateUserData(uID);
                                }

                                Log.e("type",dataType);
                                Log.e("gender",gender);

                                if(Arrays.asList(apparel_array).contains(dataType)){

                                    if(gender.equals("male"))
                                    {
                                        Log.e("Gender inside",gender);

                                        sizeUrl = getApparelMaleUrl(userInfoObject,apparel_array, clientId, skuId, uID);
                                        if(sizeUrl != null){
                                            fetchSizeFromApi(sizeUrl);
                                        }
                                        else
                                        {
                                            layout_button.setVisibility(View.VISIBLE);
                                            SpannableString content;
                                            content = new SpannableString("FIND MY SIZE");
                                            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                            tv_find_my_size.setText(content);
                                        }
                                    }
                                    else if(gender.equals("female"))
                                    {
                                        Log.e("Gender inside",gender);

                                        sizeUrl = getApparelFemaleUrl(userInfoObject,apparel_array, clientId, skuId, uID);

                                        if(sizeUrl != null){

                                            fetchSizeFromApi(sizeUrl);
                                        }
                                        else
                                        {
                                            layout_button.setVisibility(View.VISIBLE);
                                            SpannableString content;
                                            content = new SpannableString("FIND MY SIZE");
                                            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                            tv_find_my_size.setText(content);
                                        }


                                    }

                                }

//                                else if (Arrays.asList(footwear_array).contains(type))
//                                {
//                                    if(gender.equals("male")){
//                                        sizeUrl = getMaleFootwearUrl(userInfoObject,footwear_array, clientId, skuId, uID);
//                                    }
//                                    else if(gender.equals("female")){
//                                        sizeUrl = getFemaleFootwearUrl(userInfoObject,footwear_array, clientId, skuId, uID);
//                                    }
//                                }
//
//                                else if (Arrays.asList(lingerie_array).contains(type))
//                                {
//                                    sizeUrl = null;
//                                }
//

                            }
                            else
                            {

                                layout_button.setVisibility(View.VISIBLE);
                                SpannableString content;
                                content = new SpannableString("FIND MY SIZE");
                                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                tv_find_my_size.setText(content);
                            }


                        }
                        else
                        {
                            layout_button.setVisibility(View.VISIBLE);
                            SpannableString content;
                            content = new SpannableString("FIND MY SIZE");
                            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                            tv_find_my_size.setText(content);
                        }

                    }

                    else
                    {

                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;

            case SizeFromApi:
                try
                {
                    if (statusCode == 200)
                    {
                        JSONObject sizeObject = new JSONObject(result);
                        if(sizeObject.has("fys")){
                            JSONArray fysArray = new JSONArray(sizeObject.optString("fys"));
                            if(fysArray.length() > 0){
                                JSONObject fysObject = fysArray.getJSONObject(0);
                                boolean recommended =fysObject.optBoolean("recommended");

                                if(recommended){
                                   // Log.e("size",fysObject.optString("size"));
                                    setButtonText(fysObject.optString("size"),recommended);
                                }
                                else{
                                  //  Log.e("size","not_recommended");
                                    setButtonText(null,recommended);
                                }


                            }
                        }

                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;

            case MergeProfile:
                try
                {
                    if (statusCode == 200)
                    {
                        JSONObject object = new JSONObject(result);

                        String [] ids = {object.optString("uid"),uID};

                        for (String id : ids) {

                            if (!id.equals(object.optString("uid"))) {
                                resetClientProfile(clientId,id);
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;

            case ResetProfile:
                try
                {
                    if (statusCode == 200)
                    {
                        JSONObject object = new JSONObject(result);

//                        String [] ids = {object.optString("uid"),object.optString("altId")};
//
//                        for (String id : ids) {
//
//                            if (id.equals(object.optString("uid"))) {
//                                resetClientProfile(clientId,id);
//                            }
//                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 111) {

            if(resultCode == Activity.RESULT_OK){
                boolean recommended = data.getBooleanExtra("recommended",false);
                String result = data.getStringExtra("result");
                SpannableString content;
                String returnedText = "";


                Log.e("recommended", String.valueOf(recommended));
                Log.e("result",result);

                if(recommended){
                    returnedText = "Your size is "+result;
                    content = new SpannableString(returnedText);

                }
                else{
                    returnedText = "CHECK YOUR FIT";
                    content = new SpannableString(returnedText);
                }

                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

                tv_find_my_size.setText(content);


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


    public void updateData(boolean recommended ,String result )
    {
        SpannableString content;
        String returnedText = "";


        Log.e("recommended", String.valueOf(recommended));
        Log.e("result",result);

        if(recommended){
            returnedText = "Your size is "+result;
            content = new SpannableString(returnedText);

        }
        else{
            returnedText = "CHECK YOUR FIT";
            content = new SpannableString(returnedText);
        }

        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        tv_find_my_size.setText(content);


    }


    public String getApparelFemaleUrl(JSONObject userInfoObject, String[] apparel_array, String clientId, String skuId, String uID) throws JSONException {
        JSONObject whoisObject = new JSONObject(userInfoObject.optString("whois"));
        String url = null;

        if(whoisObject.has("female")){

            JSONObject femaleObject = new JSONObject(whoisObject.optString("female"));

            String ht = femaleObject.optString("height");
            String wt = femaleObject.optString("weight");
            String age = femaleObject.optString("age");

            String ftp = femaleObject.optString("fitPreference");
            String rg = femaleObject.optString("region");
            String bs = femaleObject.optString("band");
            String cu = femaleObject.optString("cup");
            String bu = femaleObject.optString("bust");

            String brand = "";
            String range = "";
            String sizeType = "";
            String size = "";

            JSONArray femaleReferenceBrandsArray = new JSONArray(femaleObject.optString("referenceBrands"));

            if(femaleReferenceBrandsArray.length() > 0){
                JSONObject femaleReferenceBrandsObject = null;
                for (int i = 0; i<femaleReferenceBrandsArray.length(); i++){
                    JSONObject referenceObject = femaleReferenceBrandsArray.getJSONObject(i);
                    String category= referenceObject.optString("category");
                    if(Arrays.asList(apparel_array).contains(category)){
                        femaleReferenceBrandsObject = referenceObject;
                    }
                }

                if(femaleReferenceBrandsObject != null){
                    brand = femaleReferenceBrandsObject.optString("brand");
                    range = femaleReferenceBrandsObject.optString("range");
                    sizeType = femaleReferenceBrandsObject.optString("sizeType");
                    size = femaleReferenceBrandsObject.optString("size");
                }

            }

            if(ftp.equals("")){
                ftp = "0";
            }
            if(!ht.equals("") && !wt.equals("") && !age.equals("")){
                if(bu == null || bu == ""){
                    if(!rg.equals("") && !bs.equals("") && !cu.equals("")){
                        url = "https://sizeguidev2.pixibo.com/asset/"+clientId+"/"+skuId+"?uid="+uID+"&ht="+ht+"&wt="+wt+"&age="+age+"&ftp="+ftp+"&rg="+rg+"&bs="+bs+"&cu="+cu+"&brand="+brand+"&range="+range+"&sizeType="+sizeType+"&size="+size;
                    }
                }
                else{
                    if(!bu.equals("")){
                        url = "https://sizeguidev2.pixibo.com/asset/"+clientId+"/"+skuId+"?uid="+uID+"&ht="+ht+"&wt="+wt+"&age="+age+"&ftp="+ftp+"&bu="+bu+"&brand="+brand+"&range="+range+"&sizeType="+sizeType+"&size="+size;
                    }
                }
            }


        }

        return url;
    }


    public String getApparelMaleUrl(JSONObject userInfoObject, String[] apparel_array, String clientId, String skuId, String uID) throws JSONException {

        JSONObject whoisObject = new JSONObject(userInfoObject.optString("whois"));
        String url = null;
        if(whoisObject.has("male")){

            JSONObject maleObject = new JSONObject(whoisObject.optString("male"));

            String ht = maleObject.optString("height");
            String wt = maleObject.optString("weight");
            String age = maleObject.optString("age");

            String ftp = maleObject.optString("fitPreference");
            String rg = maleObject.optString("region");
            String bs = maleObject.optString("band");
            String cu = maleObject.optString("cup");
            String brand = "";
            String range = "";
            String sizeType = "";
            String size = "";
            JSONArray maleReferenceBrandsArray = new JSONArray(maleObject.optString("referenceBrands"));
            if(maleReferenceBrandsArray.length() > 0){
                JSONObject maleReferenceBrandsObject = null;
                for (int i = 0; i<maleReferenceBrandsArray.length(); i++){
                    JSONObject referenceObject = maleReferenceBrandsArray.getJSONObject(i);
                    String category= referenceObject.optString("category");
                    if(Arrays.asList(apparel_array).contains(category)){
                        maleReferenceBrandsObject = referenceObject;
                    }
                }

                if(maleReferenceBrandsObject != null){
                    brand = maleReferenceBrandsObject.optString("brand");
                    range = maleReferenceBrandsObject.optString("range");
                    sizeType = maleReferenceBrandsObject.optString("sizeType");
                    size = maleReferenceBrandsObject.optString("size");
                }

            }
            if(ftp.equals("")){
                ftp = "0";
            }

            if(!ht.equals("") && !wt.equals("") && !age.equals("")){
                url = "https://sizeguidev2.pixibo.com/asset/"+clientId+"/"+skuId+"?uid="+uID+"&ht="+ht+"&wt="+wt+"&age="+age+"&ftp="+ftp+"&rg="+rg+"&bs="+bs+"&cu="+cu+"&brand="+brand+"&range="+range+"&sizeType="+sizeType+"&size="+size;
            }
        }

        return url;
    }


    public void setButtonText(String size,boolean isRecommended)
    {
        String returnedText;

        if(size == null){
            returnedText = getResources().getString(R.string.find_my_size);
        }
        else if(size.equals("not_recommended") ){
            returnedText = getResources().getString(R.string.check_your_fit);
        }
        else if(size.equals("invalid_sku")){
            returnedText = "invalid_sku";
        }
        else {
            returnedText = getResources().getString(R.string.your_size) +" "+size;
        }


        SpannableString content;

        if(isRecommended){

            content = new SpannableString(returnedText);

        }
        else{
            content = new SpannableString(returnedText);
        }

        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        tv_find_my_size.setText(content);
        layout_button.setVisibility(View.VISIBLE);
    }


}
