package com.pixibo.zalora.Utils.WebResources;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.pixibo.zalora.Utils.Utils;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class PATCH_TOKEN extends AsyncTask<String, Void, String> {

    private String url, result = "",token;
    private int responseCode ;
    private JSONObject formBody;
    private Result resultListener;
    private Utils.TYPE Servicetype;
    private Context context;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public PATCH_TOKEN(Context context, String url, JSONObject formBody, Utils.TYPE Servicetype, Result resultListener,String token) {
        Log.e("URLS ",Servicetype+" "+url);
        this.context = context;
        this.formBody = formBody;
        this.url = url;
        this.resultListener = resultListener;
        this.Servicetype = Servicetype;
        this.token = token;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {



        OkHttpClient.Builder b = new OkHttpClient.Builder();
        b.connectTimeout(30, TimeUnit.SECONDS);
        b.readTimeout(30, TimeUnit.SECONDS);
        b.writeTimeout(30, TimeUnit.SECONDS);
        OkHttpClient client = b.build();


        @SuppressLint("SimpleDateFormat")
        RequestBody body = RequestBody.create(JSON, String.valueOf(formBody));
        Request request = null;
        try {
            request = new Request.Builder()
                    .addHeader("Content-Type","application/json")
                    .addHeader("Authorization",token)
                    .url(url)
                    .patch(body)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                result = response.toString();
                responseCode = response.code() ;
                if(result.equals("")|| result.equals("null")|| result.length()==0){
                    // Util.showToast(context, "Something went wrong. Try later.");
                }
            }else {

            }
            result = response.body().string();
            responseCode = response.code() ;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(result.equals("")|| result.equals("null")|| result.length()==0){
            //Util.showToast(context, "Something went wrong. Try later.");
        }
        Log.e("PATCH_TOKEN", "Response : "+responseCode+" Request= " + Servicetype + " Response = " + s);


        resultListener.getWebResponse(s, Servicetype,responseCode);

    }

    private static String bodyToString(final Request request) {

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (Exception e) {
            return "did not work";
        }
    }
}