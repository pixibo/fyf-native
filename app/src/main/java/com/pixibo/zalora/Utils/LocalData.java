package com.pixibo.zalora.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LocalData {

    private SharedPreferences prefs;

    public LocalData(Context context) {

        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setHeight(String height) {
        prefs.edit().putString("height", height).apply();
    }

    public String getHeight() {
        return prefs.getString("height","");
    }

    public void setWeight(String weight) {
        prefs.edit().putString("weight", weight).apply();
    }

    public String getWeight() {
        return prefs.getString("weight","");
    }

    public void setAge(String age) {
        prefs.edit().putString("age", age).apply();
    }

    public String getAge() {
        return prefs.getString("age","");
    }

    public void setBust(String bust) {
        prefs.edit().putString("bust", bust).apply();
    }

    public String getBust() {
        return prefs.getString("bust","");
    }

    public void setRegion(String region) {
        prefs.edit().putString("region", region).apply();
    }

    public String getRegion() {
        return prefs.getString("region","");
    }

    public void setBand(String band) {
        prefs.edit().putString("band", band).apply();
    }

    public String getBand() {
        return prefs.getString("band","");
    }

    public void setCup(String cup) {
        prefs.edit().putString("cup", cup).apply();
    }

    public String getCup() {
        return prefs.getString("cup","");
    }

    public void setBrand(String brand) {
        prefs.edit().putString("brand", brand).apply();
    }

    public String getBrand() {
        return prefs.getString("brand","");
    }

    public void setBrandRange(String brandRange) {
        prefs.edit().putString("brandRange", brandRange).apply();
    }

    public String getBrandRange() {
        return prefs.getString("brandRange","");
    }

    public void setsizeType(String brandBand) {
        prefs.edit().putString("brandBand", brandBand).apply();
    }

    public String getsizeType() {
        return prefs.getString("brandBand","");
    }

    public void setBrandSize(String brandSize) {
        prefs.edit().putString("brandSize", brandSize).apply();
    }

    public String getBrandSize() {
        return prefs.getString("brandSize","");
    }

    public void setFitPreference(String fitPreference) {
        prefs.edit().putString("fitPreference", fitPreference).apply();
    }

    public String getFitPreference() {
        return prefs.getString("fitPreference","");
    }


}
