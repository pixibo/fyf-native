package com.pixibo.zalora.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.pixibo.zalora.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Utils {
    public static ProgressDialog progress;
    public static AsyncTask<String, Void, String> asnservice;


    public static enum TYPE {
        ValidateUserUid,ValidateUserAltId,BrandSuggestion,BrandSizes,SizeFromApi,MergeProfile,ResetProfile,UpdateProfile,BodyProfile,
        Track,BraSize,GetSize,ConversionTracking,BrandCategorySuggestion,BrandSizeSuggestion,UpdateData,NewBrand,validateSKU;
    }


    public static void showToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }



    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    @SuppressLint("HardwareIds")
    public static String deviceID(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

       // Secure.getString(PixiboActivity.this.getContentResolver(), Secure.ANDROID_ID)
        return id;
    }



    public static void showLoading(Context mContext, final AsyncTask<String, Void, String> service, boolean cancelable) {
        progress = new ProgressDialog(mContext, R.style.NewDialog);
        progress.setCancelable(cancelable);
        progress.setCanceledOnTouchOutside(false);
        asnservice = service;
        progress.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                if (service != null)
                    service.cancel(true);
            }
        });
        progress.setCanceledOnTouchOutside(cancelable);
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progress.show();
    }

    public static void showLoading(Context mContext, boolean cancelable) {
        showLoading(mContext, null, cancelable);
    }





    public static void hideLoading() {
        if (progress != null)
            progress.dismiss();
    }


    public static boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }

    public static boolean isValidMobile(CharSequence phone)
    {
        boolean ismobile = false;
        try {
            ismobile =  Patterns.PHONE.matcher(phone).matches();
        } catch (Exception e) {
            Utils.hideLoading();

            e.printStackTrace();

        }
        return ismobile ;
    }

    public static boolean isCheckPassword(String password, String confirmPassword)
    {
        boolean pstatus = false;
        if (confirmPassword != null && password != null)
        {
            if (password.equals(confirmPassword))
            {
                pstatus = true;
            }
        }
        return pstatus;
    }


    public static void applyDim(@NonNull ViewGroup parent, float dimAmount){
        Drawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * dimAmount));

        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }

    public static void clearDim(@NonNull ViewGroup parent) {
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }

    public static String centimeterToFeet(String centemeter) {
        int feetPart = 0;
        int inchesPart = 0;
        if(!TextUtils.isEmpty(centemeter)) {
            double dCentimeter = Double.valueOf(centemeter);
            feetPart = (int) Math.floor((dCentimeter / 2.54) / 12);
        }
        if (feetPart == 0)
        {
            return "";
        }
        else
        {
            return String.format("%d", feetPart);
        }

    }

    public static String centimeterToFeetInch(String centemeter) {
        int feetPart = 0;
        int inchesPart = 0;
        if(!TextUtils.isEmpty(centemeter)) {
            double dCentimeter = Double.valueOf(centemeter);
            feetPart = (int) Math.floor((dCentimeter / 2.54) / 12);
            inchesPart = (int) Math.ceil((dCentimeter / 2.54) - (feetPart * 12));
        }
        if (inchesPart == 0)
        {
            return "";
        }
        else
        {
            return String.format("%d", inchesPart);
        }

    }

    public static String centimeterToInch(String centemeter) {
        int inchesPart = 0;
        if(!TextUtils.isEmpty(centemeter)) {
            double dCentimeter = Double.valueOf(centemeter);
            inchesPart = (int) Math.ceil((dCentimeter * 0.393));
        }
        if (inchesPart == 0)
        {
            return "";
        }
        else
        {
            return String.format("%d", inchesPart);
        }

    }

    public static String InchTocentimeter(String inch) {

        int cmPart = 0;
        if(!TextUtils.isEmpty(inch)) {
            double dCm = Double.valueOf(inch);
            cmPart = (int) Math.floor((dCm * 2.54));
        }
        if (cmPart == 0)
        {
            return "";
        }
        else
        {
            return String.format("%d", cmPart);
        }

    }

    public static String kgTolb(String kg) {
        int lbPart = 0;
        if(!TextUtils.isEmpty(kg)) {
            double dKg = Double.valueOf(kg);
            lbPart = (int) Math.floor(dKg * 2.205);

        }
        if (lbPart == 0)
        {
            return "";
        }
        else
        {
            return String.format("%d", lbPart);
        }

    }

    public static String lbTokg(String lb) {
        int kgPart = 0;
        if(!TextUtils.isEmpty(lb)) {
            double dKg = Double.valueOf(lb);
            kgPart = (int) (Math.ceil(dKg * .453));

        }
        if (kgPart == 0)
        {
            return "";
        }
        else
        {
            return String.format("%d", kgPart);
        }

    }

    public static String  convertFeetandInchesToCentimeter(String feet, String inches) {
        double heightInFeet = 0;
        double heightInInches = 0;
        int cm = 0 ;
        try {
            if (feet != null && feet.trim().length() != 0) {
                heightInFeet = Double.parseDouble(feet);
            }
            if (inches != null && inches.trim().length() != 0) {
                heightInInches = Double.parseDouble(inches);
            }
        } catch (NumberFormatException nfe) {

        }

        cm  = (int) Math.floor ((heightInFeet * 30.48) + (heightInInches * 2.54));


        if (cm == 0)
        {
            return "";
        }
        else
        {
            return String.format("%d", cm) ;
        }


    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), 0);
        } else {
            inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public static void showSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }



    public static String convertApparelType(String text ,String gender ,Context context)
    {
        String singularText = "";

        switch (text) {
            case "Tops":

                if (gender.equals("female"))
                {
                    singularText = context.getResources().getString(R.string.apparel_female_top);
                }
                else
                {
                    singularText = context.getResources().getString(R.string.apparel_male_top);
                }

                break;

            case "Dresses":
                singularText = context.getResources().getString(R.string.apparel_female_dress);
                break;

            case "Coats":
                singularText =context.getResources().getString(R.string.apparel_female_coat);
                break;

            case "Jeans":
                singularText = context.getResources().getString(R.string.apparel_female_jeans);
                break;

            case "Outwear":
                if (gender.equals("female"))
                {
                    singularText = context.getResources().getString(R.string.apparel_female_outerwear);
                }
                else
                {
                    singularText = context.getResources().getString(R.string.apparel_male_outerwear);;
                }
                break;

            case "Pants":
                if (gender.equals("female"))
                {
                    singularText = context.getResources().getString(R.string.apparel_female_pant);
                }
                else
                {
                    singularText = context.getResources().getString(R.string.apparel_male_pant);;
                }
                break;

            case "Shirts":

                singularText = context.getResources().getString(R.string.apparel_male_shirt);;

                break;

            case "Skirts":
                singularText = "skirt";
                break;

            case "Shorts":
                if (gender.equals("female"))
                {
                    singularText = context.getResources().getString(R.string.apparel_female_short);
                }
                else
                {
                    singularText = context.getResources().getString(R.string.apparel_male_short);;
                }

                break;

            case "Shoes":
                singularText = context.getResources().getString(R.string.apparel_shoes);
                break;

            case "Bra":
                singularText = context.getResources().getString(R.string.apparel_bra);
                break;

            case "Jumpsuits":
                singularText = context.getResources().getString(R.string.apparel_female_jumpsuit);
                break;

            default:
                singularText = text.toLowerCase();
                break;
        }

        return singularText;

    }


}
