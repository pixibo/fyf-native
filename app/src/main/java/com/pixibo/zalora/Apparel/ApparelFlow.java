package com.pixibo.zalora.Apparel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pixibo.zalora.Adapter.BrandAdapter;
import com.pixibo.zalora.Model.BrandModel;
import com.pixibo.zalora.R;
import com.pixibo.zalora.Utils.LocalData;
import com.pixibo.zalora.Utils.Utils;
import com.pixibo.zalora.Utils.WebResources.GET;
import com.pixibo.zalora.Utils.WebResources.NetworkUtils;
import com.pixibo.zalora.Utils.WebResources.POST;
import com.pixibo.zalora.Utils.WebResources.Result;
import com.pixibo.zalora.Utils.WebResources.URI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.pixibo.zalora.Utils.Utils.TYPE.BodyProfile;
import static com.pixibo.zalora.Utils.Utils.TYPE.BraSize;
import static com.pixibo.zalora.Utils.Utils.TYPE.BrandSizes;
import static com.pixibo.zalora.Utils.Utils.TYPE.BrandSuggestion;
import static com.pixibo.zalora.Utils.Utils.TYPE.GetSize;
import static com.pixibo.zalora.Utils.Utils.TYPE.NewBrand;
import static com.pixibo.zalora.Utils.Utils.TYPE.Track;
import static com.pixibo.zalora.Utils.Utils.TYPE.UpdateData;
import static com.pixibo.zalora.Utils.Utils.TYPE.ValidateUserUid;


public class ApparelFlow extends AppCompatActivity implements View.OnClickListener, Result {


    private ArrayList<BrandModel> brandModelArrayList = new ArrayList<>();
    private BrandAdapter brandAdapter;
    private RecyclerView recycler_brand_suggestion;

    private EditText et_age,et_height,et_weight, et_height_in,et_bust,et_what_brand;

    private RelativeLayout layout_age,layout_weight,layout_height;
    private RelativeLayout layout_body_profile,layout_bra_profile,layout_bust,layout_brand,layout_brand_search,layout_brand_fit,layout_fit_preference,layout_brands,layout_result,layout_add;
    private RelativeLayout layout_height_cm,layout_height_ft,layout_weight_kg,layout_weight_lb,layout_bust_cm,layout_bust_in;
    private TextView tv_error_age,tv_error_height,tv_error_weight,tv_error_bust,tv_error_size;
    private TextView tv_continue,tv_bust_continue,tv_bra_continue,tv_brand_continue,tv_brand_fav_continue,tv_fit_continue;
    private TextView tv_height_cm,tv_height_ft,tv_weight_kg,tv_weight_lb,tv_quotes,tv_dquotes,tv_bust_cm,tv_bust_in;

    private RelativeLayout layout_band_1,layout_band_2,layout_band_3,layout_band_4,layout_band_5,layout_band_6,layout_band_7,layout_band_8,layout_band_9,layout_band_10,layout_band_11,layout_band_12;
    private TextView tv_band_1,tv_band_2,tv_band_3,tv_band_4,tv_band_5,tv_band_6,tv_band_7,tv_band_8,tv_band_9,tv_band_10,tv_band_11,tv_band_12;

    private RelativeLayout layout_cup_1,layout_cup_2,layout_cup_3,layout_cup_4,layout_cup_5,layout_cup_6,layout_cup_7,layout_cup_8,layout_cup_9,layout_cup_10,layout_cup_11,layout_cup_12,layout_cup_13;
    private TextView tv_cup_1,tv_cup_2,tv_cup_3,tv_cup_4,tv_cup_5,tv_cup_6,tv_cup_7,tv_cup_8,tv_cup_9,tv_cup_10,tv_cup_11,tv_cup_12,tv_cup_13;


    private RelativeLayout layout_brand_size_1,layout_brand_size_2,layout_brand_size_3,layout_brand_size_4,layout_brand_size_5,layout_brand_size_6,layout_brand_size_7,layout_brand_size_8,layout_brand_size_9,layout_brand_size_10,layout_brand_size_11,layout_brand_size_12,layout_brand_size_13,layout_brand_size_14,layout_brand_size_15,layout_brand_size_16,layout_brand_size_17,layout_brand_size_18,layout_brand_size_19,layout_brand_size_20,layout_brand_size_21,layout_brand_size_22;
    private TextView tv_brand_size_1,tv_brand_size_2,tv_brand_size_3,tv_brand_size_4,tv_brand_size_5,tv_brand_size_6,tv_brand_size_7,tv_brand_size_8,tv_brand_size_9,tv_brand_size_10,tv_brand_size_11,tv_brand_size_12,tv_brand_size_13,tv_brand_size_14,tv_brand_size_15,tv_brand_size_16,tv_brand_size_17,tv_brand_size_18,tv_brand_size_19,tv_brand_size_20,tv_brand_size_21,tv_brand_size_22;

    private RelativeLayout layout_range_1,layout_range_2,layout_range_3;
    private TextView tv_range_1,tv_range_2,tv_range_3;

    private TextView tv_fav_category,tv_size_fav_category,loader_text;

    private RelativeLayout layout_fit_tight,layout_fit_regular,layout_fit_loose;
    private TextView tv_fit_tight,tv_fit_regular,tv_fit_loose,tv_fit_text;


    private RelativeLayout layout_cup_next,layout_cup_back,layout_cup_next_2,layout_cup_back_2;

    private RelativeLayout layout_brand_size_next,layout_brand_size_back;
    private LinearLayout layout_cup_row1,layout_cup_row2,layout_cup_row3,layout_size_selection,layout_brand_band_selection,layout_height_bust;
    private LinearLayout layout_brand_size_row1,layout_brand_size_row2,layout_brand_size_row3,layout_brand_size_row4;

    private TextView tv_select_bra_size,tv_bust_select,tv_type_size,tv_brand_range;
    private TextView tv_au,tv_eu,tv_fr,tv_uk,tv_us;
    private TextView tv_brand_band_1,tv_brand_band_2,tv_brand_band_3,tv_brand_band_4,tv_brand_band_5,tv_brand_band_6,tv_brand_band_7,tv_brand_band_8;

    private TextView tv_back_bra_size,tv_back_bust,tv_back_brand,tv_back_brand_fav,tv_back_fit;
    private TextView tv_brand_skip,tv_brand_fav_skip;

    private RelativeLayout layout_close,layout_privacy,layout_header,layout_popup;

    private TextView tv_header_text,tv_privacy_policy,tv_description,tb_out_of_stock;

    private TextView tv_brand_1,tv_brand_2,tv_brand_3,tv_brand_4,tv_brand_5;

    Typeface bold_text ;
    Typeface normal_text ;

    String ht = "";
    String wt = "";
    String age = "0";
    String ftp = "2";
    String rg = "";
    String bs = "";
    String cu = "";
    String bu = "";
    String overbustUnit = "cm";
    private String height_cm = "0";
    private String height_ft = "0";
    private String height_in = "0";
    private String weight = "0";
    private String bust = "0";
    private String braSizeType = "";
    private String sizeType = "";
    private String band = "";
    private String cup = "";
    private String brandSize = "";
    private String brand = "";
    private String fitPreference = "2";
    private String category = "";
    private String gender = "";
    private String range = "";
    private String brandCharts = "";

    private String clientId = "";
    private String skuId = "";
    private String altId = "";
    private String uID = "";
    private String [] availableSizeList ;

    private String bust8Bit = "";
    private String waist8Bit = "";
    private String hip8Bit = "";
    private String preferredLanguage = "";
    private String size = "";

    private boolean isCM = true;
    private boolean isKG = true;
    private boolean isBust = false;
    private boolean isBustCM = true;
    private boolean isBrandSelected = false;
    private boolean isBrandCategorySelected = false;
    private boolean isEditFlow = false;
    private boolean isRecommended = false ;
    private boolean isNew = false ;
    private boolean isAgeFilled = true ;
    private boolean isWeightFilled = true ;
    private boolean isHeightFilled = true ;
    private boolean isSizeMatched = false;
    private boolean isReset = false;
    private boolean hasSize = false;
    private boolean brandFromResult = false;

    String[] apparel_array = new String[]{"Tops", "Dresses", "Coats", "Jeans", "Jumpsuits", "Outwear", "Pants", "Shirts", "Skirts", "Shorts"};
    String[] footwear_array = new String[]{"Shoes"};
    String[] lingerie_array = new String[]{"Bra"};


    private LinearLayout layout_pref_1,layout_pref_2,layout_pref_3,layout_pref_4,layout_pref_5,layout_pref_6,layout_pref_7,layout_pref_8,layout_start_over;
    private RelativeLayout layout_fit_bust,layout_fit_waist,layout_fit_hips,layout_loading,layout_error,layout_try_again;
    private TextView tv_fit_bust,tv_fit_waist,tv_fit_hips,tv_size,tv_not_fit,tv_suits_best,tv_fit_size,tv_edit,tv_add_cart,tv_brand_error,tv_error_band,tv_error_cup;

    private RelativeLayout layout_progress_1,layout_progress_2,layout_progress_3,layout_progress_4,layout_progress_5,layout_progress_6;





    private LocalData localData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apparel_flow);

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
        isNew = intent.getBooleanExtra("isNew",false);


        bold_text = ResourcesCompat.getFont(ApparelFlow.this, R.font.apercu_bold);
        normal_text = ResourcesCompat.getFont(ApparelFlow.this, R.font.apercu_regular);

        recycler_brand_suggestion = findViewById(R.id.recycler_brand_suggestion);

        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        et_height_in = findViewById(R.id.et_height_in);
        et_bust = findViewById(R.id.et_bust);
        et_what_brand = findViewById(R.id.et_what_brand);

        layout_progress_1 = findViewById(R.id.layout_progress_1);
        layout_progress_2 = findViewById(R.id.layout_progress_2);
        layout_progress_3 = findViewById(R.id.layout_progress_3);
        layout_progress_4 = findViewById(R.id.layout_progress_4);
        layout_progress_5 = findViewById(R.id.layout_progress_5);
        layout_progress_6 = findViewById(R.id.layout_progress_6);

        tv_header_text = findViewById(R.id.tv_header_text);

        tv_error_age = findViewById(R.id.tv_error_age);
        tv_error_height = findViewById(R.id.tv_error_height);
        tv_error_weight = findViewById(R.id.tv_error_weight);
        tv_error_bust = findViewById(R.id.tv_error_bust);
        tv_error_size = findViewById(R.id.tv_error_size);

        tv_quotes = findViewById(R.id.tv_quotes);
        tv_dquotes = findViewById(R.id.tv_dquotes);

        tv_continue = findViewById(R.id.tv_continue);
        tv_bust_continue = findViewById(R.id.tv_bust_continue);
        tv_bra_continue = findViewById(R.id.tv_bra_continue);
        tv_brand_continue = findViewById(R.id.tv_brand_continue);
        tv_brand_fav_continue = findViewById(R.id.tv_brand_fav_continue);
        tv_fit_continue = findViewById(R.id.tv_fit_continue);

        tv_brand_skip = findViewById(R.id.tv_brand_skip);
        tv_brand_fav_skip = findViewById(R.id.tv_brand_fav_skip);


        tv_select_bra_size = findViewById(R.id.tv_select_bra_size);
        tv_bust_select = findViewById(R.id.tv_bust_select);
        tv_type_size = findViewById(R.id.tv_type_size);

        layout_close = findViewById(R.id.layout_close);
        layout_privacy = findViewById(R.id.layout_privacy);
        layout_popup = findViewById(R.id.layout_popup);
        layout_header = findViewById(R.id.layout_header);

        tv_au = findViewById(R.id.tv_au);
        tv_eu = findViewById(R.id.tv_eu);
        tv_fr = findViewById(R.id.tv_fr);
        tv_uk = findViewById(R.id.tv_uk);
        tv_us = findViewById(R.id.tv_us);

        tv_privacy_policy = findViewById(R.id.tv_privacy_policy);
        tv_description = findViewById(R.id.tv_description);
        tb_out_of_stock = findViewById(R.id.tb_out_of_stock);

        tv_brand_1 = findViewById(R.id.tv_brand_1);
        tv_brand_2 = findViewById(R.id.tv_brand_2);
        tv_brand_3 = findViewById(R.id.tv_brand_3);
        tv_brand_4 = findViewById(R.id.tv_brand_4);
        tv_brand_5 = findViewById(R.id.tv_brand_5);


        tv_brand_band_1 = findViewById(R.id.tv_brand_band_1);
        tv_brand_band_2 = findViewById(R.id.tv_brand_band_2);
        tv_brand_band_3 = findViewById(R.id.tv_brand_band_3);
        tv_brand_band_4 = findViewById(R.id.tv_brand_band_4);
        tv_brand_band_5 = findViewById(R.id.tv_brand_band_5);
        tv_brand_band_6 = findViewById(R.id.tv_brand_band_6);
        tv_brand_band_7 = findViewById(R.id.tv_brand_band_7);
        tv_brand_band_8 = findViewById(R.id.tv_brand_band_8);


        tv_bust_cm = findViewById(R.id.tv_bust_cm);
        tv_bust_in = findViewById(R.id.tv_bust_in);

        tv_fav_category = findViewById(R.id.tv_fav_category);
        tv_size_fav_category = findViewById(R.id.tv_size_fav_category);
        tv_brand_range = findViewById(R.id.tv_brand_range);
        loader_text = findViewById(R.id.loader_text);

        tv_fit_tight = findViewById(R.id.tv_fit_tight);
        tv_fit_regular = findViewById(R.id.tv_fit_regular);
        tv_fit_loose = findViewById(R.id.tv_fit_loose);
        tv_fit_text = findViewById(R.id.tv_fit_text);

        tv_back_bra_size = findViewById(R.id.tv_back_bra_size);
        tv_back_bust = findViewById(R.id.tv_back_bust);


        tv_height_cm = findViewById(R.id.tv_height_cm);
        tv_height_ft = findViewById(R.id.tv_height_ft);
        tv_weight_kg = findViewById(R.id.tv_weight_kg);
        tv_weight_lb = findViewById(R.id.tv_weight_lb);


        layout_bust = findViewById(R.id.layout_bust);
        layout_brand = findViewById(R.id.layout_brand);
        layout_bra_profile = findViewById(R.id.layout_bra_profile);
        layout_size_selection = findViewById(R.id.layout_size_selection);
        layout_brand_band_selection = findViewById(R.id.layout_brand_band_selection);
        layout_brand_search = findViewById(R.id.layout_brand_search);
        layout_brand_fit = findViewById(R.id.layout_brand_fit);
        layout_fit_preference = findViewById(R.id.layout_fit_preference);
        layout_brands = findViewById(R.id.layout_brands);
        layout_result = findViewById(R.id.layout_result);
        layout_add = findViewById(R.id.layout_add);


        tv_back_brand = findViewById(R.id.tv_back_brand);
        tv_back_brand_fav = findViewById(R.id.tv_back_brand_fav);
        tv_back_fit = findViewById(R.id.tv_back_fit);

        layout_brand_size_next = findViewById(R.id.layout_brand_size_next);
        layout_brand_size_back = findViewById(R.id.layout_brand_size_back);
        layout_height_bust = findViewById(R.id.layout_height_bust);

        layout_fit_tight = findViewById(R.id.layout_fit_tight);
        layout_fit_regular = findViewById(R.id.layout_fit_regular);
        layout_fit_loose = findViewById(R.id.layout_fit_loose);

        layout_cup_next = findViewById(R.id.layout_cup_next);
        layout_cup_next_2 = findViewById(R.id.layout_cup_next_2);
        layout_cup_back = findViewById(R.id.layout_cup_back);
        layout_cup_back_2 = findViewById(R.id.layout_cup_back_2);

        layout_cup_row1 = findViewById(R.id.layout_cup_row1);
        layout_cup_row2 = findViewById(R.id.layout_cup_row2);
        layout_cup_row3 = findViewById(R.id.layout_cup_row3);

        layout_brand_size_row1 = findViewById(R.id.layout_brand_size_row1);
        layout_brand_size_row2 = findViewById(R.id.layout_brand_size_row2);
        layout_brand_size_row3 = findViewById(R.id.layout_brand_size_row3);
        layout_brand_size_row4 = findViewById(R.id.layout_brand_size_row4);


        tv_band_1 = findViewById(R.id.tv_band_1);
        tv_band_2 = findViewById(R.id.tv_band_2);
        tv_band_3 = findViewById(R.id.tv_band_3);
        tv_band_4 = findViewById(R.id.tv_band_4);
        tv_band_5 = findViewById(R.id.tv_band_5);
        tv_band_6 = findViewById(R.id.tv_band_6);
        tv_band_7 = findViewById(R.id.tv_band_7);
        tv_band_8 = findViewById(R.id.tv_band_8);
        tv_band_9 = findViewById(R.id.tv_band_9);
        tv_band_10 = findViewById(R.id.tv_band_10);
        tv_band_11 = findViewById(R.id.tv_band_11);
        tv_band_12 = findViewById(R.id.tv_band_12);

        layout_band_1 = findViewById(R.id.layout_band_1);
        layout_band_2 = findViewById(R.id.layout_band_2);
        layout_band_3= findViewById(R.id.layout_band_3);
        layout_band_4= findViewById(R.id.layout_band_4);
        layout_band_5= findViewById(R.id.layout_band_5);
        layout_band_6= findViewById(R.id.layout_band_6);
        layout_band_7= findViewById(R.id.layout_band_7);
        layout_band_8= findViewById(R.id.layout_band_8);
        layout_band_9= findViewById(R.id.layout_band_9);
        layout_band_10= findViewById(R.id.layout_band_10);
        layout_band_11= findViewById(R.id.layout_band_11);
        layout_band_12= findViewById(R.id.layout_band_12);


        tv_cup_1 = findViewById(R.id.tv_cup_1);
        tv_cup_2 = findViewById(R.id.tv_cup_2);
        tv_cup_3 = findViewById(R.id.tv_cup_3);
        tv_cup_4 = findViewById(R.id.tv_cup_4);
        tv_cup_5 = findViewById(R.id.tv_cup_5);
        tv_cup_6 = findViewById(R.id.tv_cup_6);
        tv_cup_7 = findViewById(R.id.tv_cup_7);
        tv_cup_8 = findViewById(R.id.tv_cup_8);
        tv_cup_9 = findViewById(R.id.tv_cup_9);
        tv_cup_10 = findViewById(R.id.tv_cup_10);
        tv_cup_11 = findViewById(R.id.tv_cup_11);
        tv_cup_12 = findViewById(R.id.tv_cup_12);
        tv_cup_13 = findViewById(R.id.tv_cup_13);

        layout_cup_1 = findViewById(R.id.layout_cup_1);
        layout_cup_2 = findViewById(R.id.layout_cup_2);
        layout_cup_3= findViewById(R.id.layout_cup_3);
        layout_cup_4= findViewById(R.id.layout_cup_4);
        layout_cup_5= findViewById(R.id.layout_cup_5);
        layout_cup_6= findViewById(R.id.layout_cup_6);
        layout_cup_7= findViewById(R.id.layout_cup_7);
        layout_cup_8= findViewById(R.id.layout_cup_8);
        layout_cup_9= findViewById(R.id.layout_cup_9);
        layout_cup_10= findViewById(R.id.layout_cup_10);
        layout_cup_11= findViewById(R.id.layout_cup_11);
        layout_cup_12= findViewById(R.id.layout_cup_12);
        layout_cup_13= findViewById(R.id.layout_cup_13);


        tv_brand_size_1 = findViewById(R.id.tv_brand_size_1);
        tv_brand_size_2 = findViewById(R.id.tv_brand_size_2);
        tv_brand_size_3 = findViewById(R.id.tv_brand_size_3);
        tv_brand_size_4 = findViewById(R.id.tv_brand_size_4);
        tv_brand_size_5 = findViewById(R.id.tv_brand_size_5);
        tv_brand_size_6 = findViewById(R.id.tv_brand_size_6);
        tv_brand_size_7 = findViewById(R.id.tv_brand_size_7);
        tv_brand_size_8 = findViewById(R.id.tv_brand_size_8);
        tv_brand_size_9 = findViewById(R.id.tv_brand_size_9);
        tv_brand_size_10 = findViewById(R.id.tv_brand_size_10);
        tv_brand_size_11 = findViewById(R.id.tv_brand_size_11);
        tv_brand_size_12 = findViewById(R.id.tv_brand_size_12);
        tv_brand_size_13 = findViewById(R.id.tv_brand_size_13);
        tv_brand_size_14 = findViewById(R.id.tv_brand_size_14);
        tv_brand_size_15 = findViewById(R.id.tv_brand_size_15);
        tv_brand_size_16 = findViewById(R.id.tv_brand_size_16);
        tv_brand_size_17 = findViewById(R.id.tv_brand_size_17);
        tv_brand_size_18 = findViewById(R.id.tv_brand_size_18);
        tv_brand_size_19 = findViewById(R.id.tv_brand_size_19);
        tv_brand_size_20 = findViewById(R.id.tv_brand_size_20);
        tv_brand_size_21 = findViewById(R.id.tv_brand_size_21);
        tv_brand_size_22 = findViewById(R.id.tv_brand_size_22);

        layout_range_1 = findViewById(R.id.layout_range_1);
        layout_range_2 = findViewById(R.id.layout_range_2);
        layout_range_3 = findViewById(R.id.layout_range_3);

        tv_range_1 = findViewById(R.id.tv_range_1);
        tv_range_2 = findViewById(R.id.tv_range_2);
        tv_range_3 = findViewById(R.id.tv_range_3);


        layout_brand_size_1 = findViewById(R.id.layout_brand_size_1);
        layout_brand_size_2 = findViewById(R.id.layout_brand_size_2);
        layout_brand_size_3= findViewById(R.id.layout_brand_size_3);
        layout_brand_size_4= findViewById(R.id.layout_brand_size_4);
        layout_brand_size_5= findViewById(R.id.layout_brand_size_5);
        layout_brand_size_6= findViewById(R.id.layout_brand_size_6);
        layout_brand_size_7= findViewById(R.id.layout_brand_size_7);
        layout_brand_size_8= findViewById(R.id.layout_brand_size_8);
        layout_brand_size_9= findViewById(R.id.layout_brand_size_9);
        layout_brand_size_10= findViewById(R.id.layout_brand_size_10);
        layout_brand_size_11= findViewById(R.id.layout_brand_size_11);
        layout_brand_size_12= findViewById(R.id.layout_brand_size_12);
        layout_brand_size_13= findViewById(R.id.layout_brand_size_13);
        layout_brand_size_14= findViewById(R.id.layout_brand_size_14);
        layout_brand_size_15= findViewById(R.id.layout_brand_size_15);
        layout_brand_size_16= findViewById(R.id.layout_brand_size_16);
        layout_brand_size_17= findViewById(R.id.layout_brand_size_17);
        layout_brand_size_18= findViewById(R.id.layout_brand_size_18);
        layout_brand_size_19= findViewById(R.id.layout_brand_size_19);
        layout_brand_size_20= findViewById(R.id.layout_brand_size_20);
        layout_brand_size_21= findViewById(R.id.layout_brand_size_21);
        layout_brand_size_22= findViewById(R.id.layout_brand_size_22);


        layout_age = findViewById(R.id.layout_age);
        layout_height = findViewById(R.id.layout_height);
        layout_weight = findViewById(R.id.layout_weight);

        layout_height_cm = findViewById(R.id.layout_height_cm);
        layout_height_ft = findViewById(R.id.layout_height_ft);
        layout_weight_kg = findViewById(R.id.layout_weight_kg);
        layout_weight_lb = findViewById(R.id.layout_weight_lb);
        layout_bust_cm = findViewById(R.id.layout_bust_cm);
        layout_bust_in = findViewById(R.id.layout_bust_in);




        layout_body_profile = findViewById(R.id.layout_body_profile);
        layout_bra_profile = findViewById(R.id.layout_bra_profile);



        layout_pref_1 = findViewById(R.id.layout_pref_1);
        layout_pref_2 = findViewById(R.id.layout_pref_2);
        layout_pref_3 = findViewById(R.id.layout_pref_3);
        layout_pref_4 = findViewById(R.id.layout_pref_4);
        layout_pref_5 = findViewById(R.id.layout_pref_5);
        layout_pref_6 = findViewById(R.id.layout_pref_6);
        layout_pref_7 = findViewById(R.id.layout_pref_7);
        layout_pref_8 = findViewById(R.id.layout_pref_8);


        layout_start_over = findViewById(R.id.layout_start_over);
        layout_fit_bust = findViewById(R.id.layout_fit_bust);
        layout_fit_waist = findViewById(R.id.layout_fit_waist);
        layout_fit_hips = findViewById(R.id.layout_fit_hips);
        layout_close = findViewById(R.id.layout_close);
        layout_loading = findViewById(R.id.layout_loading);
        layout_error = findViewById(R.id.layout_error);
        layout_try_again = findViewById(R.id.layout_try_again);

        tv_size = findViewById(R.id.tv_size);
        tv_not_fit = findViewById(R.id.tv_not_fit);
        tv_suits_best = findViewById(R.id.tv_suits_best);
        tv_fit_size = findViewById(R.id.tv_fit_size);
        tv_edit = findViewById(R.id.tv_edit);
        tv_add_cart = findViewById(R.id.tv_add_cart);
        tv_brand_error = findViewById(R.id.tv_brand_error);

        tv_error_band = findViewById(R.id.tv_error_band);
        tv_error_cup = findViewById(R.id.tv_error_cup);

        tv_fit_bust = findViewById(R.id.tv_fit_bust);
        tv_fit_waist = findViewById(R.id.tv_fit_waist);
        tv_fit_hips = findViewById(R.id.tv_fit_hips);

        et_age.setBackground(null);
        et_height.setBackground(null);
        et_weight.setBackground(null);
        et_height_in.setBackground(null);
        et_bust.setBackground(null);

        tv_quotes.setText("\'");
        tv_dquotes.setText("\"");


        if (preferredLanguage.equals("hk") || preferredLanguage.equals("tw"))
        {
            tv_description.setVisibility(View.GONE);
        }


        SpannableString content ,content2,content3, content4;

        content = new SpannableString(getResources().getString(R.string.apparel_result_edit));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv_edit.setText(content);


        content2 = new SpannableString(getResources().getString(R.string.dont_know_measurement));
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
        tv_bust_select.setText(content2);


        content3 = new SpannableString(getResources().getString(R.string.apparel_bra_select_));
        content3.setSpan(new UnderlineSpan(), 0, content3.length(), 0);
        tv_select_bra_size.setText(content3);


//        content4 = new SpannableString(getResources().getString(R.string.privacy_powered_text));
//        content4.setSpan(new UnderlineSpan(), 0, content4.length(), 0);
//        tv_privacy_policy.setText(content4);



        et_age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().trim().length() == 0)
                {
                    isAgeFilled = false ;
                }
                else
                {
                    isAgeFilled = true;
                }


                if (isAgeFilled && isHeightFilled && isWeightFilled)
                {
                    tv_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                }
                else
                {
                    tv_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
                }

                layout_age.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                tv_error_age.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().trim().length() == 0)
                {
                    isHeightFilled = false ;
                }
                else
                {
                    isHeightFilled = true;
                }


                if (isAgeFilled && isHeightFilled && isWeightFilled)
                {
                    tv_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                }
                else
                {
                    tv_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
                }

                layout_height.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                tv_error_height.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().trim().length() == 0)
                {
                    isWeightFilled = false ;
                }
                else
                {
                    isWeightFilled = true;
                }



                if (isAgeFilled && isHeightFilled && isWeightFilled)
                {
                    tv_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                }

                else
                {
                    tv_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
                }

                layout_weight.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                tv_error_weight.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_bust.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                layout_height_bust.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                tv_error_bust.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        et_age.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b)
                {
                    layout_age.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                    et_age.setSelection(et_age.getText().length());
                }
                else
                {
                    layout_age.setBackground(getResources().getDrawable(R.drawable.bg_edit_disabled));
                }
            }
        });

        et_height.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b)
                {
                    layout_height.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                    et_height.setSelection(et_height.getText().length());
                }
                else
                {
                    layout_height.setBackground(getResources().getDrawable(R.drawable.bg_edit_disabled));
                }
            }
        });

        et_weight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b)
                {
                    layout_weight.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                    et_weight.setSelection(et_weight.getText().length());
                }
                else
                {
                    layout_weight.setBackground(getResources().getDrawable(R.drawable.bg_edit_disabled));
                }
            }
        });


        et_what_brand.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b)
                {
                    et_what_brand.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                }
                else
                {
                    et_what_brand.setBackground(getResources().getDrawable(R.drawable.bg_edit_disabled));
                }
            }
        });


        et_what_brand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                tv_brand_continue.setBackgroundColor(getResources().getColor(R.color.color_background_button_disabled));
                et_what_brand.setTextColor(getResources().getColor(R.color.grey_3));

                clearAllBrands();

                if (charSequence.toString().trim().length()>=1)
                {
                    brand_filter(charSequence.toString());
                }

                if (!brand.equalsIgnoreCase(charSequence.toString()))
                {
                    isBrandSelected = false ;
                }


                Log.e("charSequence", String.valueOf(charSequence));
                Log.e("isBrandSelected", String.valueOf(isBrandSelected));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        layout_band_1.setOnClickListener(this);
        layout_band_2.setOnClickListener(this);
        layout_band_3.setOnClickListener(this);
        layout_band_4.setOnClickListener(this);
        layout_band_5.setOnClickListener(this);
        layout_band_6.setOnClickListener(this);
        layout_band_7.setOnClickListener(this);
        layout_band_8.setOnClickListener(this);
        layout_band_9.setOnClickListener(this);
        layout_band_10.setOnClickListener(this);
        layout_band_11.setOnClickListener(this);
        layout_band_12.setOnClickListener(this);



        layout_cup_1.setOnClickListener(this);
        layout_cup_2.setOnClickListener(this);
        layout_cup_3.setOnClickListener(this);
        layout_cup_4.setOnClickListener(this);
        layout_cup_5.setOnClickListener(this);
        layout_cup_6.setOnClickListener(this);
        layout_cup_7.setOnClickListener(this);
        layout_cup_8.setOnClickListener(this);
        layout_cup_9.setOnClickListener(this);
        layout_cup_10.setOnClickListener(this);
        layout_cup_11.setOnClickListener(this);
        layout_cup_12.setOnClickListener(this);
        layout_cup_13.setOnClickListener(this);


        tv_brand_band_1.setOnClickListener(this);
        tv_brand_band_2.setOnClickListener(this);
        tv_brand_band_3.setOnClickListener(this);
        tv_brand_band_4.setOnClickListener(this);
        tv_brand_band_5.setOnClickListener(this);
        tv_brand_band_6.setOnClickListener(this);
        tv_brand_band_7.setOnClickListener(this);
        tv_brand_band_8.setOnClickListener(this);



        layout_brand_size_1.setOnClickListener(this);
        layout_brand_size_2.setOnClickListener(this);
        layout_brand_size_3.setOnClickListener(this);
        layout_brand_size_4.setOnClickListener(this);
        layout_brand_size_5.setOnClickListener(this);
        layout_brand_size_6.setOnClickListener(this);
        layout_brand_size_7.setOnClickListener(this);
        layout_brand_size_8.setOnClickListener(this);
        layout_brand_size_9.setOnClickListener(this);
        layout_brand_size_10.setOnClickListener(this);
        layout_brand_size_11.setOnClickListener(this);
        layout_brand_size_12.setOnClickListener(this);
        layout_brand_size_13.setOnClickListener(this);
        layout_brand_size_14.setOnClickListener(this);
        layout_brand_size_15.setOnClickListener(this);
        layout_brand_size_16.setOnClickListener(this);
        layout_brand_size_17.setOnClickListener(this);
        layout_brand_size_18.setOnClickListener(this);
        layout_brand_size_19.setOnClickListener(this);
        layout_brand_size_20.setOnClickListener(this);
        layout_brand_size_21.setOnClickListener(this);
        layout_brand_size_22.setOnClickListener(this);


        layout_range_1.setOnClickListener(this);
        layout_range_2.setOnClickListener(this);
        layout_range_3.setOnClickListener(this);



        tv_au.setOnClickListener(this);
        tv_eu.setOnClickListener(this);
        tv_fr.setOnClickListener(this);
        tv_uk.setOnClickListener(this);
        tv_us.setOnClickListener(this);

        tv_brand_1.setOnClickListener(this);
        tv_brand_2.setOnClickListener(this);
        tv_brand_3.setOnClickListener(this);
        tv_brand_4.setOnClickListener(this);





        layout_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newBrandRequest(category,et_what_brand.getText().toString());

                et_what_brand.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                layout_add.setVisibility(View.GONE);
                tv_brand_error.setText(getResources().getString(R.string.brand_thanks));
                layout_brands.setVisibility(View.VISIBLE);
            }
        });



        tv_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wearepixibo.com/privacypolicy"));
                startActivity(browserIntent);
            }
        });

        layout_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_popup.setVisibility(View.GONE);
            }
        });


        layout_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (layout_popup.getVisibility() == View.VISIBLE)
                {
                    layout_popup.setVisibility(View.GONE);
                }
                else
                {
                    layout_popup.setVisibility(View.VISIBLE);
                }

            }
        });



        layout_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                tv_header_text.setVisibility(View.GONE);
                layout_header.setBackgroundColor(getResources().getColor(R.color.color_background));

                resetData();

                layout_body_profile.setVisibility(View.VISIBLE);

                layout_bra_profile.setVisibility(View.GONE);
                layout_bust.setVisibility(View.GONE);
                layout_brand.setVisibility(View.GONE);
                layout_brand_search.setVisibility(View.GONE);
                layout_fit_preference.setVisibility(View.GONE);
                layout_brands.setVisibility(View.GONE);
                layout_result.setVisibility(View.GONE);
                layout_error.setVisibility(View.GONE);
                progress(1);
            }
        });

        layout_fit_bust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearAll();
                setFit(bust8Bit);

                layout_fit_bust.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_fit_bust.setTextColor(getResources().getColor(R.color.color_text));

                layout_fit_waist.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                tv_fit_waist.setTextColor(getResources().getColor(R.color.grey_3));

                layout_fit_hips.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                tv_fit_hips.setTextColor(getResources().getColor(R.color.grey_3));
            }
        });

        layout_fit_waist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
                setFit(waist8Bit);

                layout_fit_bust.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                tv_fit_bust.setTextColor(getResources().getColor(R.color.grey_3));

                layout_fit_waist.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_fit_waist.setTextColor(getResources().getColor(R.color.color_text));

                layout_fit_hips.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                tv_fit_hips.setTextColor(getResources().getColor(R.color.grey_3));
            }
        });

        layout_fit_hips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
                setFit(hip8Bit);

                layout_fit_bust.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                tv_fit_bust.setTextColor(getResources().getColor(R.color.grey_3));

                layout_fit_waist.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                tv_fit_waist.setTextColor(getResources().getColor(R.color.grey_3));

                layout_fit_hips.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_fit_hips.setTextColor(getResources().getColor(R.color.color_text));
            }
        });



        layout_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("recommended",isRecommended);
                returnIntent.putExtra("result",size);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });



        tv_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("Screen","Result");
                Log.e("Action","Add to Bag");
                Log.e("Event","addBag");

                trackEvent(clientId,skuId,"click","pdp","addBag",uID);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("recommended",isRecommended);
                returnIntent.putExtra("result",size);
                returnIntent.putExtra("addToBag",true);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        });

        layout_fit_tight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                fitPreference = "1";

                tv_fit_tight.setTextColor(getResources().getColor(R.color.color_text));
                tv_fit_regular.setTextColor(getResources().getColor(R.color.grey_3));
                tv_fit_loose.setTextColor(getResources().getColor(R.color.grey_3));

                layout_fit_tight.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                layout_fit_regular.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                layout_fit_loose.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));

                tv_fit_text.setText(getResources().getString(R.string.apparel_last_fit_tight_text));
            }
        });



        layout_fit_regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fitPreference = "2";

                tv_fit_tight.setTextColor(getResources().getColor(R.color.grey_3));
                tv_fit_regular.setTextColor(getResources().getColor(R.color.color_text));
                tv_fit_loose.setTextColor(getResources().getColor(R.color.grey_3));

                layout_fit_tight.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                layout_fit_regular.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                layout_fit_loose.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));

                tv_fit_text.setText(getResources().getString(R.string.apparel_last_fit_regular_text));

            }
        });



        layout_fit_loose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fitPreference = "3";

                tv_fit_tight.setTextColor(getResources().getColor(R.color.grey_3));
                tv_fit_regular.setTextColor(getResources().getColor(R.color.grey_3));
                tv_fit_loose.setTextColor(getResources().getColor(R.color.color_text));

                layout_fit_tight.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                layout_fit_regular.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                layout_fit_loose.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));

                tv_fit_text.setText(getResources().getString(R.string.apparel_last_fit_loose_text));
            }
        });



        layout_height_cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!isCM)
                {
                    isCM = true ;

                    Log.e("et_height",et_height.getText().toString());
                    Log.e("et_height_in",et_height_in.getText().toString());

                    height_cm = Utils.convertFeetandInchesToCentimeter(et_height.getText().toString(),et_height_in.getText().toString());

                    Log.e("height_cm",height_cm);


                    setCM();

                    int maxLength = 5;
                    InputFilter[] fArray = new InputFilter[1];
                    fArray[0] = new InputFilter.LengthFilter(maxLength);
                    et_height.setFilters(fArray);


                    et_height.setText(height_cm);

                }


            }
        });

        layout_height_ft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (isCM)
                {
                    isCM = false ;

                    height_ft = Utils.centimeterToFeet(et_height.getText().toString());
                    height_in = Utils.centimeterToFeetInch(et_height.getText().toString());


                    setFT();

                    int maxLength = 1;
                    InputFilter[] fArray = new InputFilter[1];
                    fArray[0] = new InputFilter.LengthFilter(maxLength);
                    et_height.setFilters(fArray);
                }

            }
        });

        layout_weight_kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isKG)
                {
                    isKG = true;

                    weight = Utils.lbTokg(et_weight.getText().toString());

                    setKG();

                    et_weight.setText(weight);
                }


            }
        });

        layout_weight_lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isKG)
                {
                    isKG = false;

                    weight = Utils.kgTolb(et_weight.getText().toString());

                    setLB();

                    et_weight.setText(weight);
                }

            }
        });

        layout_bust_cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isBustCM)
                {
                    isBustCM = true;

                    bust = Utils.InchTocentimeter(et_bust.getText().toString());

                    tv_bust_in.setTypeface(normal_text);
                    tv_bust_in.setTextColor(getResources().getColor(R.color.grey));

                    tv_bust_cm.setTypeface(bold_text);
                    tv_bust_cm.setTextColor(getResources().getColor(R.color.black));

                    tv_error_bust.setVisibility(View.GONE);
                    layout_height_bust.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));

                    et_bust.setText(bust);
                }

            }
        });

        layout_bust_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isBustCM)
                {
                    isBustCM = false;

                    bust = Utils.centimeterToInch(et_bust.getText().toString());

                    tv_bust_cm.setTypeface(normal_text);
                    tv_bust_cm.setTextColor(getResources().getColor(R.color.grey));

                    tv_bust_in.setTypeface(bold_text);
                    tv_bust_in.setTextColor(getResources().getColor(R.color.black));

                    tv_error_bust.setVisibility(View.GONE);
                    layout_height_bust.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));

                    et_bust.setText(bust);
                }

            }
        });


        layout_cup_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_cup_row1.setVisibility(View.GONE);
                layout_cup_row2.setVisibility(View.VISIBLE);
                layout_cup_row3.setVisibility(View.GONE);
            }
        });


        layout_cup_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_cup_row1.setVisibility(View.VISIBLE);
                layout_cup_row2.setVisibility(View.GONE);
                layout_cup_row3.setVisibility(View.GONE);
            }
        });


        layout_cup_next_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_cup_row1.setVisibility(View.GONE);
                layout_cup_row2.setVisibility(View.GONE);
                layout_cup_row3.setVisibility(View.VISIBLE);
            }
        });


        layout_cup_back_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_cup_row1.setVisibility(View.GONE);
                layout_cup_row2.setVisibility(View.VISIBLE);
                layout_cup_row3.setVisibility(View.GONE);
            }
        });


        layout_brand_size_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_brand_size_row1.setVisibility(View.GONE);
                layout_brand_size_row2.setVisibility(View.GONE);
                layout_brand_size_row3.setVisibility(View.VISIBLE);
                layout_brand_size_row4.setVisibility(View.VISIBLE);
            }
        });

        layout_brand_size_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_brand_size_row1.setVisibility(View.VISIBLE);
                layout_brand_size_row2.setVisibility(View.VISIBLE);
                layout_brand_size_row3.setVisibility(View.GONE);
                layout_brand_size_row4.setVisibility(View.GONE);
            }
        });

        tv_select_bra_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBust = false;
                layout_bra_profile.setVisibility(View.VISIBLE);
                layout_bust.setVisibility(View.GONE);
            }
        });


        tv_bust_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBust = true;
                layout_bust.setVisibility(View.VISIBLE);
                layout_bra_profile.setVisibility(View.GONE);
            }
        });




        tv_type_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (tv_type_size.getText().toString().equalsIgnoreCase(getResources().getString(R.string.au)))
                {
                    tv_au.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                    tv_eu.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_fr.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_uk.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_us.setBackgroundColor(getResources().getColor(R.color.white));
                }

                if (tv_type_size.getText().toString().equalsIgnoreCase(getResources().getString(R.string.eu)))
                {
                    tv_au.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_eu.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                    tv_fr.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_uk.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_us.setBackgroundColor(getResources().getColor(R.color.white));
                }

                if (tv_type_size.getText().toString().equalsIgnoreCase(getResources().getString(R.string.fr)))
                {
                    tv_au.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_eu.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_fr.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                    tv_uk.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_us.setBackgroundColor(getResources().getColor(R.color.white));
                }

                if (tv_type_size.getText().toString().equalsIgnoreCase(getResources().getString(R.string.uk)))
                {
                    tv_au.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_eu.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_fr.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_uk.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                    tv_us.setBackgroundColor(getResources().getColor(R.color.white));
                }

                if (tv_type_size.getText().toString().equalsIgnoreCase(getResources().getString(R.string.us)))
                {
                    tv_au.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_eu.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_fr.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_uk.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_us.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                }

                if (layout_size_selection.getVisibility() == View.VISIBLE)
                {
                    layout_size_selection.setVisibility(View.GONE);
                    tv_type_size.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
                }
                else
                {
                    layout_size_selection.setVisibility(View.VISIBLE);
                    tv_type_size.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                }

            }
        });


        tv_brand_range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (layout_brand_band_selection.getVisibility() == View.VISIBLE)
                {
                    layout_brand_band_selection.setVisibility(View.GONE);
                    tv_brand_range.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
                }
                else
                {
                    layout_brand_band_selection.setVisibility(View.VISIBLE);
                    tv_brand_range.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                }
            }
        });


        layout_brand_fit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_brand_band_selection.setVisibility(View.GONE);
            }
        });


        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.hideSoftKeyboard(ApparelFlow.this);

                if (isCM)
                {
                    if (et_height.getText().toString().length() > 0)
                    {
                        height_cm = et_height.getText().toString();
                    }

                }
                else
                {
                    if (et_height.getText().toString().length() > 0)
                    {
                        height_cm = Utils.convertFeetandInchesToCentimeter(et_height.getText().toString(),et_height_in.getText().toString());
                    }
                }

                if (isKG)
                {
                    if (et_weight.getText().toString().length() > 0)
                    {
                        weight = et_weight.getText().toString();
                    }
                }
                else
                {
                    if (et_weight.getText().toString().length() > 0)
                    {
                        weight = Utils.lbTokg(et_weight.getText().toString());
                    }
                }

                if (et_age.getText().toString().length() > 0)
                {
                    age = et_age.getText().toString();
                }

                if (et_age.getText().toString().length() > 0)
                {

                    if (Integer.parseInt(et_age.getText().toString()) < 15 || Integer.parseInt(et_age.getText().toString()) > 80)
                    {
                        tv_error_age.setVisibility(View.VISIBLE);
                        tv_error_age.setText(getResources().getString(R.string.error_age));
                        layout_age.setBackground(getResources().getDrawable(R.drawable.bg_button_error));
                    }
                    else
                    {
                        tv_error_age.setVisibility(View.GONE);

                        if (Integer.parseInt(height_cm) < 140 || Integer.parseInt(height_cm) > 190)
                        {
                            tv_error_height.setVisibility(View.VISIBLE);
                            layout_height.setBackground(getResources().getDrawable(R.drawable.bg_button_error));

                            if (isCM)
                            {
                                tv_error_height.setText(getResources().getString(R.string.error_height_cm));
                            }
                            else
                            {
                                tv_error_height.setText(getResources().getString(R.string.error_height_ft));
                            }
                        }
                        else
                        {
                            tv_error_height.setVisibility(View.GONE);
                            layout_height.setBackground(getResources().getDrawable(R.drawable.bg_edit_disabled));
                            if (Integer.parseInt(weight) < 40 || Integer.parseInt(weight) > 110)
                            {
                                tv_error_weight.setVisibility(View.VISIBLE);
                                layout_weight.setBackground(getResources().getDrawable(R.drawable.bg_button_error));

                                if (isKG)
                                {
                                    tv_error_weight.setText(getResources().getString(R.string.error_weight_kg));
                                }
                                else
                                {
                                    tv_error_weight.setText(getResources().getString(R.string.error_weight_lb));
                                }

                            }
                            else
                            {


                                hasSize = false;
                                brandFromResult = false;

                                Log.e("Height",height_cm);
                                Log.e("weight",weight);
                                Log.e("age",age);



                                localData.setHeight(height_cm);
                                localData.setWeight(weight);
                                localData.setAge(age);

                                layout_weight.setBackground(getResources().getDrawable(R.drawable.bg_edit_disabled));

                                if (gender.equals("female"))
                                {
                                    progress(2);

                                    if (isReset)
                                    {
                                        setEU("","");
                                    }
                                    else if (!isEditFlow)
                                    {
                                        clearAllBand();
                                        clearAllCup();

                                        band = "";
                                        cup = "";

                                        tv_type_size.setText(getResources().getString(R.string.eu));

                                        setEU("","");
                                    }
                                    else if (isEditFlow)
                                    {
                                        switch (braSizeType.toLowerCase())
                                        {
                                            case "au":
                                                setAU(band.toLowerCase(),cup.toLowerCase());
                                                break;

                                            case "eu":
                                                setEU(band.toLowerCase(),cup.toLowerCase());
                                                break;

                                            case "fr":
                                                setFR(band.toLowerCase(),cup.toLowerCase());
                                                break;

                                            case "uk":
                                                setUK(band.toLowerCase(),cup.toLowerCase());
                                                break;

                                            case "us":
                                                setUS(band.toLowerCase(),cup.toLowerCase());
                                                break;
                                        }
                                    }


                                    tv_error_weight.setVisibility(View.GONE);
                                    layout_bra_profile.setVisibility(View.VISIBLE);
                                    layout_body_profile.setVisibility(View.GONE);



                                }
                                else if (gender.equals("male"))
                                {
                                    progress(3);
                                    tv_error_weight.setVisibility(View.GONE);
                                    layout_bra_profile.setVisibility(View.GONE);
                                    layout_body_profile.setVisibility(View.GONE);
                                    layout_brand.setVisibility(View.VISIBLE);

                                    tv_brand_error.setVisibility(View.GONE);
                                    layout_brand_search.setVisibility(View.GONE);

                                    setDefaultBrands();

                                    layout_brands.setVisibility(View.VISIBLE);


                                    if (altId.equals(""))
                                    {
                                        setBodyProfile(uID,height_cm,weight,age);
                                    }
                                    else
                                    {
                                        setBodyProfile(altId,height_cm,weight,age);
                                    }





                                }

                                Log.e("Screen","Body Profile");
                                Log.e("Action","Continue Button");
                                Log.e("Event","bodyProfile_continue");

                                trackEvent(clientId,skuId,"click","pdp","bodyProfile_continue",uID);


                              //  Utils.showToast(ApparelFlow.this,"Success");
                            }

                        }


                    }

                }
                else
                {
                    tv_error_age.setVisibility(View.VISIBLE);
                    tv_error_age.setText(getResources().getString(R.string.error_required));
                    layout_age.setBackground(getResources().getDrawable(R.drawable.bg_button_error));
                }





                if (et_height.getText().toString().length() > 0)
                {
                    tv_error_height.setVisibility(View.GONE);
                    layout_height.setBackground(getResources().getDrawable(R.drawable.bg_edit_disabled));

                    if (Integer.parseInt(height_cm) < 140 || Integer.parseInt(height_cm) > 190)
                    {
                        tv_error_height.setVisibility(View.VISIBLE);
                        layout_height.setBackground(getResources().getDrawable(R.drawable.bg_button_error));

                        if (isCM)
                        {
                            tv_error_height.setText(getResources().getString(R.string.error_height_cm));
                        }
                        else
                        {
                            tv_error_height.setText(getResources().getString(R.string.error_height_ft));
                        }
                    }
                }
                else
                {
                    layout_height.setBackground(getResources().getDrawable(R.drawable.bg_button_error));
                    tv_error_height.setVisibility(View.VISIBLE);
                    tv_error_height.setText(getResources().getString(R.string.error_required));
                }


                if (et_weight.getText().toString().length() > 0)
                {
                    tv_error_weight.setVisibility(View.GONE);
                    layout_weight.setBackground(getResources().getDrawable(R.drawable.bg_edit_disabled));

                    if (Integer.parseInt(weight) < 40 || Integer.parseInt(weight) > 110)
                    {
                        tv_error_weight.setVisibility(View.VISIBLE);
                        layout_weight.setBackground(getResources().getDrawable(R.drawable.bg_button_error));

                        if (isKG)
                        {
                            tv_error_weight.setText(getResources().getString(R.string.error_weight_kg));
                        }
                        else
                        {
                            tv_error_weight.setText(getResources().getString(R.string.error_weight_lb));
                        }
                    }
                }
                else
                {
                    tv_error_weight.setVisibility(View.VISIBLE);
                    layout_weight.setBackground(getResources().getDrawable(R.drawable.bg_button_error));
                    tv_error_weight.setText(getResources().getString(R.string.error_required));
                }

            }
        });


        tv_bust_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.hideSoftKeyboard(ApparelFlow.this);

                if (isBustCM)
                {
                    if (et_bust.getText().toString().length() > 0)
                    {
                        bust = et_bust.getText().toString();
                    }
                }
                else
                {
                    if (et_bust.getText().toString().length() > 0)
                    {
                        bust = Utils.InchTocentimeter(et_bust.getText().toString());
                    }
                }

                Log.e("bust",bust);

                if (et_bust.getText().toString().length() > 0)
                {

                    if (Integer.parseInt(bust) < 75 || Integer.parseInt(bust) > 155)
                    {
                        tv_error_bust.setVisibility(View.VISIBLE);
                        layout_height_bust.setBackground(getResources().getDrawable(R.drawable.bg_button_error));

                        if (isBustCM)
                        {
                            tv_error_bust.setText(getResources().getString(R.string.error_bust_cm));
                        }
                        else
                        {
                            tv_error_bust.setText(getResources().getString(R.string.error_bust_in));
                        }

                    }
                    else
                    {
                        tv_error_bust.setVisibility(View.GONE);
                        layout_height_bust.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));

//                        layout_bra_profile.setVisibility(View.VISIBLE);
//                        layout_body_profile.setVisibility(View.GONE);

                        layout_body_profile.setVisibility(View.GONE);
                        layout_bust.setVisibility(View.GONE);
                        layout_bra_profile.setVisibility(View.GONE);
                        layout_brand.setVisibility(View.VISIBLE);

                        tv_brand_error.setVisibility(View.GONE);
                        layout_brand_search.setVisibility(View.GONE);

                        setDefaultBrands();

                        layout_brands.setVisibility(View.VISIBLE);


                        localData.setBust(bust);

                        if (altId.equals(""))
                        {
                            setBustSize(uID,height_cm,weight,age,bust);
                        }
                        else
                        {
                            setBustSize(altId,height_cm,weight,age,bust);
                        }

                        trackEvent(clientId, skuId, "click", "pdp", "bra_continue",uID);




                     //   Utils.showToast(ApparelFlow.this,"Success");
                    }

                }
                else
                {
                    tv_error_bust.setVisibility(View.VISIBLE);
                    layout_height_bust.setBackground(getResources().getDrawable(R.drawable.bg_button_error));
                    tv_error_bust.setText(getResources().getString(R.string.error_required));
                }



            }
        });





        tv_back_bust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isBust = false;

                if (!isCM)
                {
                    et_height.setText(Utils.centimeterToFeet(localData.getHeight()));
                    et_height_in.setText(Utils.centimeterToFeetInch(localData.getHeight()));

//                    et_height.setText(height_ft);
//                    et_height_in.setText(height_in);
                }
                else
                {
                    et_height.setText(localData.getHeight());
                }

                if (!isKG)
                {
                    et_weight.setText(Utils.kgTolb(localData.getWeight()));

                }
                else
                {
                    et_weight.setText(localData.getWeight());
                }

                et_age.setText(localData.getAge());



                layout_body_profile.setVisibility(View.VISIBLE);
                progress(1);
                layout_bust.setVisibility(View.GONE);
                layout_bra_profile.setVisibility(View.GONE);
            }
        });





        tv_bra_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.hideSoftKeyboard(ApparelFlow.this);

                braSizeType = tv_type_size.getText().toString().toLowerCase();

                if (!band.equals("") && !cup.equals(""))
                {
                    Log.e("bra region",braSizeType);
                    Log.e("bra band",band);
                    Log.e("bra cup",cup);

                    localData.setRegion(braSizeType);
                    localData.setBand(band);
                    localData.setCup(cup);

                    if (altId.equals(""))
                    {
                        setBraSize(uID,height_cm,weight,age,braSizeType,band,cup);
                    }
                    else
                    {
                        setBraSize(altId,height_cm,weight,age,braSizeType,band,cup);
                    }

                    trackEvent(clientId, skuId, "click", "pdp", "bra_continue",uID);

                    tv_brand_error.setVisibility(View.GONE);
                    layout_brand_search.setVisibility(View.GONE);
                    tv_header_text.setVisibility(View.GONE);
                    layout_header.setBackground(null);

                    if (isReset && !brand.equals(""))
                    {
                        et_what_brand.setText(brand);
                        tv_brand_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                        checkBrandSelection(brand);
                        isBrandSelected = true;
                    }
                    else if (!isEditFlow)
                    {
                        isBrandSelected = false;
                        et_what_brand.setText(null);
                        tv_brand_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
                    }

                    progress(3);

                    layout_body_profile.setVisibility(View.GONE);
                    layout_bust.setVisibility(View.GONE);
                    layout_bra_profile.setVisibility(View.GONE);
                    tv_brand_error.setVisibility(View.GONE);
                    layout_brand_search.setVisibility(View.GONE);

                    setDefaultBrands();

                    checkBrandSelection(localData.getBrand());

                    layout_brands.setVisibility(View.VISIBLE);

                }

                else {

                    if (band.equals(""))
                    {
                        tv_error_band.setVisibility(View.VISIBLE);
                    }
                    if (cup.equals(""))
                    {
                        tv_error_cup.setVisibility(View.VISIBLE);
                    }
                }





            }
        });


        tv_back_bra_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isBust = false;

                if (!isCM)
                {
                    et_height.setText(Utils.centimeterToFeet(localData.getHeight()));
                    et_height_in.setText(Utils.centimeterToFeetInch(localData.getHeight()));
                }
                else
                {
                    et_height.setText(localData.getHeight());
                }


                if (!isKG)
                {
                    et_weight.setText(Utils.kgTolb(localData.getWeight()));

                }
                else
                {
                    et_weight.setText(localData.getWeight());
                }

                et_age.setText(localData.getAge());

                layout_body_profile.setVisibility(View.VISIBLE);
                layout_bra_profile.setVisibility(View.GONE);
                progress(1);
            }
        });



        tv_brand_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.hideSoftKeyboard(ApparelFlow.this);

                if (isBrandSelected)
                {

                    get_brand_sizes(gender,category,brand);

                    progress(4);

                    layout_body_profile.setVisibility(View.GONE);
                    layout_bust.setVisibility(View.GONE);
                    layout_bra_profile.setVisibility(View.GONE);
                    layout_brand.setVisibility(View.GONE);

                    localData.setBrand(brand);

                    if (isEditFlow && brandSize.equals(""))
                    {
                        get_brand_sizes(gender,category,brand);
                    }
                    else if (isReset && !localData.getBrandSize().equals(""))
                    {
                        tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                        if (preferredLanguage.equals("hk") || preferredLanguage.equals("tw"))
                        {
                            tv_size_fav_category.setText(getResources().getString(R.string.apparel_brand_fav_size)+" "+brand+" "+
                                    Utils.convertApparelType(category,gender, ApparelFlow.this)+ getResources().getString(R.string.apparel_brand_fav_size_3));
                        }
                        else
                        {
                            tv_size_fav_category.setText(getResources().getString(R.string.apparel_brand_fav_size)+" "+
                                    Utils.convertApparelType(category,gender, ApparelFlow.this)+" "+getResources().getString(R.string.apparel_brand_fav_size_2)+" "+brand+getResources().getString(R.string.apparel_brand_fav_size_3));
                        }

                    }
                    else if (!isEditFlow)
                    {
                        brandSize = "";

                        clearRangeButton();
                        clearAllBrandSize();

                        tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));


                        if (preferredLanguage.equals("hk") || preferredLanguage.equals("tw"))
                        {
                            tv_size_fav_category.setText(getResources().getString(R.string.apparel_brand_fav_size)+" "+brand+" "+
                                    Utils.convertApparelType(category,gender, ApparelFlow.this)+
                                    getResources().getString(R.string.apparel_brand_fav_size_3));
                        }
                        else
                        {
                            tv_size_fav_category.setText(getResources().getString(R.string.apparel_brand_fav_size)+" "+
                                    Utils.convertApparelType(category,gender, ApparelFlow.this)+" "+getResources().getString(R.string.apparel_brand_fav_size_2)+" "+
                                    brand+getResources().getString(R.string.apparel_brand_fav_size_3));
                        }
                    }


                    trackEvent(clientId, skuId, "click", "pdp", "refBrand_continue",uID);

                    Log.e("band",band);

                }

                else
                {
                    tv_brand_error.setText(getResources().getString(R.string.error_required));
                    tv_brand_error.setVisibility(View.VISIBLE);
                    layout_brands.setVisibility(View.GONE);
                    et_what_brand.setBackground(getResources().getDrawable(R.drawable.bg_button_error));
                }

            }
        });


        tv_brand_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progress(5);

                isBrandCategorySelected = false;

                layout_body_profile.setVisibility(View.GONE);
                layout_bust.setVisibility(View.GONE);
                layout_bra_profile.setVisibility(View.GONE);
                layout_brand.setVisibility(View.GONE);
                layout_brand_fit.setVisibility(View.GONE);
                layout_fit_preference.setVisibility(View.VISIBLE);

                tv_fit_tight.setTextColor(getResources().getColor(R.color.grey_3));
                tv_fit_regular.setTextColor(getResources().getColor(R.color.color_text));
                tv_fit_loose.setTextColor(getResources().getColor(R.color.grey_3));

                layout_fit_tight.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                layout_fit_regular.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                layout_fit_loose.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));

                if (isEditFlow)
                {
                    switch (fitPreference)
                    {
                        case "1":
                            tv_fit_size.setText(getResources().getString(R.string.apparel_last_fit_tight));
                            break;
                        case "2":
                            tv_fit_size.setText(getResources().getString(R.string.apparel_last_fit_regular));
                            break;
                        case "3":
                            tv_fit_size.setText(getResources().getString(R.string.apparel_last_fit_loose));
                            break;
                    }
                }


                trackEvent(clientId, skuId, "click", "pdp", "refBrand_skip",uID);

            }
        });


        tv_back_brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (gender.equals("female"))
                {
                    progress(2);

                    Log.e("bra bust",bust);
                    Log.e("bra bcupand",cup);
                    Log.e("bra braSizeType", braSizeType);
                    Log.e("bra band", band);

                    if (isBust)
                    {
                        layout_body_profile.setVisibility(View.GONE);
                        layout_bust.setVisibility(View.VISIBLE);
                        layout_bra_profile.setVisibility(View.GONE);
                        layout_brand.setVisibility(View.GONE);

                        if (!isBustCM)
                        {
                            et_bust.setText(Utils.centimeterToInch(localData.getBust()));
                        }
                        else
                        {
                            et_bust.setText(localData.getBust());
                        }
                    }
                    else
                    {
                        layout_body_profile.setVisibility(View.GONE);
                        layout_bust.setVisibility(View.GONE);
                        layout_bra_profile.setVisibility(View.VISIBLE);
                        layout_brand.setVisibility(View.GONE);


                        if (isEditFlow)
                        {
                            tv_bra_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                            //TODO back flow for bra page when user coming back

                            tv_type_size.setText(braSizeType.toUpperCase());

                            switch (braSizeType.toLowerCase())
                            {
                                case "au":
                                    setAU(band.toLowerCase(),cup.toLowerCase());
                                    break;

                                case "eu":
                                    setEU(band.toLowerCase(),cup.toLowerCase());
                                    break;

                                case "fr":
                                    setFR(band.toLowerCase(),cup.toLowerCase());
                                    break;

                                case "uk":
                                    setUK(band.toLowerCase(),cup.toLowerCase());
                                    break;

                                case "us":
                                    setUS(band.toLowerCase(),cup.toLowerCase());
                                    break;
                            }




                        }
                    }

                }

                else if (gender.equals("male"))
                {

                    if (!isCM)
                    {
                        et_height.setText(Utils.centimeterToFeet(localData.getHeight()));
                        et_height_in.setText(Utils.centimeterToFeetInch(localData.getHeight()));
                    }
                    else
                    {
                        et_height.setText(localData.getHeight());
                    }


                    if (!isKG)
                    {
                        et_weight.setText(Utils.kgTolb(localData.getWeight()));

                    }
                    else
                    {
                        et_weight.setText(localData.getWeight());
                    }

                    et_age.setText(localData.getAge());

                    progress(1);
                    layout_body_profile.setVisibility(View.VISIBLE);
                    layout_bust.setVisibility(View.GONE);
                    layout_bra_profile.setVisibility(View.GONE);
                    layout_brand.setVisibility(View.GONE);
                }

                tv_header_text.setVisibility(View.GONE);

                layout_header.setBackground(null);
            }
        });



        tv_brand_fav_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isBrandCategorySelected = false;

                progress(5);

                if (!isEditFlow)
                {
                    tv_fit_tight.setTextColor(getResources().getColor(R.color.grey_3));
                    tv_fit_regular.setTextColor(getResources().getColor(R.color.color_text));
                    tv_fit_loose.setTextColor(getResources().getColor(R.color.grey_3));

                    layout_fit_tight.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                    layout_fit_regular.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                    layout_fit_loose.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                }

                layout_body_profile.setVisibility(View.GONE);
                layout_bust.setVisibility(View.GONE);
                layout_bra_profile.setVisibility(View.GONE);
                layout_brand.setVisibility(View.GONE);
                layout_brand_fit.setVisibility(View.GONE);
                layout_fit_preference.setVisibility(View.VISIBLE);


                trackEvent(clientId, skuId, "click", "pdp", "refSize_skip",uID);

            }
        });

        tv_brand_fav_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.hideSoftKeyboard(ApparelFlow.this);

                if (!brandSize.equals(""))
                {


                    isBrandCategorySelected = true;

                    layout_body_profile.setVisibility(View.GONE);
                    layout_bust.setVisibility(View.GONE);
                    layout_bra_profile.setVisibility(View.GONE);
                    layout_brand.setVisibility(View.GONE);
                    layout_brand_fit.setVisibility(View.GONE);

                    layout_fit_preference.setVisibility(View.VISIBLE);

                    if (!isEditFlow)
                    {

                        sizeType = tv_brand_range.getText().toString().toLowerCase();

                        tv_fit_tight.setTextColor(getResources().getColor(R.color.grey_3));
                        tv_fit_regular.setTextColor(getResources().getColor(R.color.color_text));
                        tv_fit_loose.setTextColor(getResources().getColor(R.color.grey_3));

                        layout_fit_tight.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                        layout_fit_regular.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                        layout_fit_loose.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));

                        localData.setBrandRange(range);
                        localData.setsizeType(sizeType);
                        localData.setBrandSize(brandSize);
                    }


                    progress(5);



                    trackEvent(clientId, skuId, "click", "pdp", "refSize_continue",uID);



                    Log.e("Brand range",range);
                    Log.e("Brand band",sizeType);
                    Log.e("Brand size", brandSize);
                }
                else
                {
                    tv_error_size.setVisibility(View.VISIBLE);
                    tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
                }


            }
        });


        tv_back_brand_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_brand_search.setVisibility(View.GONE);


                setDefaultBrands();

                layout_brands.setVisibility(View.VISIBLE);

                progress(3);

                layout_body_profile.setVisibility(View.GONE);
                layout_bust.setVisibility(View.GONE);
                layout_bra_profile.setVisibility(View.GONE);
                layout_brand.setVisibility(View.VISIBLE);
                layout_brand_fit.setVisibility(View.GONE);


                tv_brand_error.setVisibility(View.GONE);
                layout_brand_search.setVisibility(View.GONE);


                if (isEditFlow)
                {
                    isBrandSelected = true;
                    brand = localData.getBrand();
                    checkBrandSelection(brand);
                    layout_brand_search.setVisibility(View.GONE);
                    tv_brand_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                }
                else if (isReset)
                {
                    isBrandSelected = true;
                    brand = localData.getBrand();
                    checkBrandSelection(brand);
                    layout_brand_search.setVisibility(View.GONE);
                    tv_brand_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                }


            }
        });



        tv_fit_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                isEditFlow = false;

                Log.e("fitPreference",fitPreference);

                localData.setFitPreference(fitPreference);


                Utils.hideSoftKeyboard(ApparelFlow.this);


                layout_fit_bust.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_fit_bust.setTextColor(getResources().getColor(R.color.color_text));

                layout_fit_waist.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                tv_fit_waist.setTextColor(getResources().getColor(R.color.grey_3));

                layout_fit_hips.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                tv_fit_hips.setTextColor(getResources().getColor(R.color.grey_3));

                switch (fitPreference)
                {
                    case "1":
                        tv_fit_size.setText(getResources().getString(R.string.apparel_last_fit_tight));
                        break;
                    case "2":
                        tv_fit_size.setText(getResources().getString(R.string.apparel_last_fit_regular));
                        break;
                    case "3":
                        tv_fit_size.setText(getResources().getString(R.string.apparel_last_fit_loose));
                        break;
                }


                if (fitPreference.equals(""))
                {
                    fitPreference = "2";
                }

                trackEvent(clientId, skuId, "click", "pdp", "fitPreference_continue",uID);


                if (altId.equals(""))
                {
                    getFinalSize(uID,localData.getHeight(),localData.getWeight(),localData.getAge(),localData.getFitPreference(),localData.getRegion(),localData.getBand(),localData.getCup(),
                            localData.getBrand(),localData.getBrandRange(),localData.getsizeType(),localData.getBrandSize(),localData.getBust());
                }
                else
                {
                    getFinalSize(altId,localData.getHeight(),localData.getWeight(),localData.getAge(),localData.getFitPreference(),localData.getRegion(),localData.getBand(),localData.getCup(),
                            localData.getBrand(),localData.getBrandRange(),localData.getsizeType(),localData.getBrandSize(),localData.getBust());
                }

                localData.setBrandRange(range);
                localData.setsizeType(sizeType);
                localData.setBrandSize(brandSize);


            }
        });




        tv_back_fit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isBrandCategorySelected)
                {

                    layout_body_profile.setVisibility(View.GONE);
                    layout_bust.setVisibility(View.GONE);
                    layout_bra_profile.setVisibility(View.GONE);
                    layout_brand.setVisibility(View.GONE);
                    layout_brand_fit.setVisibility(View.VISIBLE);
                    layout_fit_preference.setVisibility(View.GONE);
                    layout_result.setVisibility(View.GONE);

                    et_what_brand.setText(localData.getBrand());


                    if (isEditFlow)
                    {

                        clearRangeButton();

                        clearAllBrandSize();

                        clearSelectedBand();

                        get_brand_sizes(gender,category,brand);

                        tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                        if (preferredLanguage.equals("hk") || preferredLanguage.equals("tw"))
                        {
                            tv_size_fav_category.setText(getResources().getString(R.string.apparel_brand_fav_size)+" "+brand+" "+
                                    Utils.convertApparelType(category,gender, ApparelFlow.this)+ getResources().getString(R.string.apparel_brand_fav_size_3));
                        }
                        else
                        {
                            tv_size_fav_category.setText(getResources().getString(R.string.apparel_brand_fav_size)+" "+
                                    Utils.convertApparelType(category,gender, ApparelFlow.this)+" "+getResources().getString(R.string.apparel_brand_fav_size_2)+" "+brand+getResources().getString(R.string.apparel_brand_fav_size_3));
                        }

//                        clearRangeButton();
//
//                        clearAllBrandSize();
//
//                        clearSelectedBand();

                    }



                    progress(4);

                }
                else
                {

                    layout_body_profile.setVisibility(View.VISIBLE);
                    layout_bust.setVisibility(View.GONE);
                    layout_bra_profile.setVisibility(View.GONE);
                    layout_brand.setVisibility(View.GONE);
                    layout_brand_fit.setVisibility(View.GONE);
                    layout_fit_preference.setVisibility(View.GONE);
                    layout_result.setVisibility(View.GONE);


                    progress(1);


                    if (isEditFlow && gender.equals("female"))
                    {
                        layout_body_profile.setVisibility(View.GONE);
                        layout_bra_profile.setVisibility(View.VISIBLE);

                        progress(2);


                        tv_bra_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                        tv_type_size.setText(braSizeType.toUpperCase());

                        switch (braSizeType.toLowerCase())
                        {
                            case "au":
                                setAU(band.toLowerCase(),cup.toLowerCase());
                                break;

                            case "eu":
                                setEU(band.toLowerCase(),cup.toLowerCase());
                                break;

                            case "fr":
                                setFR(band.toLowerCase(),cup.toLowerCase());
                                break;

                            case "uk":
                                setUK(band.toLowerCase(),cup.toLowerCase());
                                break;

                            case "us":
                                setUS(band.toLowerCase(),cup.toLowerCase());
                                break;
                        }

                    }


                    if (isBrandSelected)
                    {

                        progress(3);

                        layout_body_profile.setVisibility(View.GONE);
                        layout_bust.setVisibility(View.GONE);
                        layout_bra_profile.setVisibility(View.GONE);
                        layout_brand.setVisibility(View.VISIBLE);
                        layout_brand_fit.setVisibility(View.GONE);
                        layout_fit_preference.setVisibility(View.GONE);
                        layout_result.setVisibility(View.GONE);

                        setDefaultBrands();

                        checkBrandSelection(localData.getBrand());

                        layout_brands.setVisibility(View.VISIBLE);

                    }


                }



            }
        });




        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                isEditFlow = true;
                hasSize = false;
                brandFromResult = false;

                progress(5);

                layout_result.setVisibility(View.GONE);
                layout_fit_preference.setVisibility(View.VISIBLE);


                trackEvent(clientId, skuId, "click", "pdp", "fitPreference_edit",uID);


                switch (localData.getFitPreference())
                {
                    case "1":

                        tv_fit_size.setText(getResources().getString(R.string.apparel_last_fit_tight));
                        tv_fit_tight.setTextColor(getResources().getColor(R.color.color_text));
                        tv_fit_regular.setTextColor(getResources().getColor(R.color.grey_3));
                        tv_fit_loose.setTextColor(getResources().getColor(R.color.grey_3));

                        layout_fit_tight.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                        layout_fit_regular.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                        layout_fit_loose.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));

                        tv_fit_text.setText(getResources().getString(R.string.apparel_last_fit_tight_text));

                        break;

                    case "2":

                        tv_fit_size.setText(getResources().getString(R.string.apparel_last_fit_regular));
                        tv_fit_tight.setTextColor(getResources().getColor(R.color.grey_3));
                        tv_fit_regular.setTextColor(getResources().getColor(R.color.color_text));
                        tv_fit_loose.setTextColor(getResources().getColor(R.color.grey_3));

                        layout_fit_tight.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                        layout_fit_regular.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                        layout_fit_loose.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));

                        tv_fit_text.setText(getResources().getString(R.string.apparel_last_fit_regular_text));


                        break;

                    case "3":

                        tv_fit_size.setText(getResources().getString(R.string.apparel_last_fit_loose));
                        tv_fit_tight.setTextColor(getResources().getColor(R.color.grey_3));
                        tv_fit_regular.setTextColor(getResources().getColor(R.color.grey_3));
                        tv_fit_loose.setTextColor(getResources().getColor(R.color.color_text));

                        layout_fit_tight.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                        layout_fit_regular.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
                        layout_fit_loose.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));

                        tv_fit_text.setText(getResources().getString(R.string.apparel_last_fit_loose_text));

                        break;
                }

            }
        });


        layout_start_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetData();
                progress(1);

                trackEvent(clientId, skuId, "click", "pdp", "startOver",uID);

                tv_header_text.setVisibility(View.GONE);

                layout_header.setBackgroundColor(getResources().getColor(R.color.color_background));

                layout_body_profile.setVisibility(View.VISIBLE);
                layout_bra_profile.setVisibility(View.GONE);
                layout_result.setVisibility(View.GONE);
                layout_fit_preference.setVisibility(View.GONE);

            }
        });



        layout_bra_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_size_selection.setVisibility(View.GONE);
                tv_type_size.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
            }
        });


        layout_brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_brand_search.setVisibility(View.GONE);
            }
        });

        brandAdapter = new BrandAdapter(brandModelArrayList, ApparelFlow.this, new BrandAdapter.onItemClickListener() {
            @Override
            public void onItemClick(BrandModel suggestions) {

                isEditFlow = false;

                isBrandSelected = true;
                brand = suggestions.getName();
                localData.setBrand(brand);

                et_what_brand.setText(suggestions.getName());
                et_what_brand.setSelection(et_what_brand.getText().length());
                et_what_brand.setTextColor(getResources().getColor(R.color.black));
                layout_brand_search.setVisibility(View.GONE);
                tv_brand_continue.setBackgroundColor(getResources().getColor(R.color.color_background_button_enable));

                checkBrandSelection(brand);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recycler_brand_suggestion.setItemAnimator(new DefaultItemAnimator());
        recycler_brand_suggestion.setLayoutManager(mLayoutManager);
        recycler_brand_suggestion.setAdapter(brandAdapter);

        if (preferredLanguage.equals("in"))
        {
            tv_fav_category.setText(getResources().getString(R.string.apparel_fit_fav_category)+" "+ Utils.convertApparelType(category,gender, ApparelFlow.this) +" favorit Anda");
        }
        else
        {
            tv_fav_category.setText(getResources().getString(R.string.apparel_fit_fav_category)+" "+ Utils.convertApparelType(category,gender, ApparelFlow.this));
        }


        //validate_user(clientId,skuId);

        getData();

        get_brands(gender,category);
    }



    public void clearAllRange()
    {

        layout_range_1.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_range_1.setTextColor(getResources().getColor(R.color.color_text_grey));


        layout_range_2.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_range_2.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_range_3.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_range_3.setTextColor(getResources().getColor(R.color.color_text_grey));

    }

    public void clearRangeButton()
    {
        layout_range_1.setVisibility(View.GONE);
        layout_range_2.setVisibility(View.GONE);
        layout_range_3.setVisibility(View.GONE);
    }


    public void clearAllBand()
    {

        isEditFlow = false;

        tv_error_band.setVisibility(View.GONE);

        layout_band_1.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_1.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_2.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_2.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_3.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_3.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_4.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_4.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_5.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_5.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_6.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_6.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_7.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_7.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_8.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_8.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_9.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_9.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_10.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_10.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_11.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_11.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_12.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_12.setTextColor(getResources().getColor(R.color.color_text_grey));

    }


    public void clearAllBrandSize()
    {

        tv_error_size.setVisibility(View.GONE);

        layout_brand_size_1.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_1.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_brand_size_2.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_2.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_brand_size_3.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_3.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_brand_size_4.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_4.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_brand_size_5.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_5.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_brand_size_6.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_6.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_brand_size_7.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_7.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_brand_size_8.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_8.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_brand_size_9.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_9.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_brand_size_10.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_10.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_brand_size_11.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_11.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_brand_size_12.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_12.setTextColor(getResources().getColor(R.color.color_text_grey));


        layout_brand_size_13.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_13.setTextColor(getResources().getColor(R.color.color_text_grey));


        layout_brand_size_14.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_14.setTextColor(getResources().getColor(R.color.color_text_grey));


        layout_brand_size_15.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_15.setTextColor(getResources().getColor(R.color.color_text_grey));


        layout_brand_size_16.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_16.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_brand_size_17.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_17.setTextColor(getResources().getColor(R.color.color_text_grey));


        layout_brand_size_18.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_18.setTextColor(getResources().getColor(R.color.color_text_grey));


        layout_brand_size_19.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_19.setTextColor(getResources().getColor(R.color.color_text_grey));


        layout_brand_size_20.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_20.setTextColor(getResources().getColor(R.color.color_text_grey));


        layout_brand_size_21.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_21.setTextColor(getResources().getColor(R.color.color_text_grey));


        layout_brand_size_22.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_size_22.setTextColor(getResources().getColor(R.color.color_text_grey));



    }



    public void checkBandCup(String band ,String cup)
    {
        if (!band.equals("") && !cup.equals(""))
        {
            tv_bra_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
        }
        else
        {
            tv_bra_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
        }
    }

    public void clearAllCup()
    {

        isEditFlow = false;
        tv_error_cup.setVisibility(View.GONE);

        layout_cup_1.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_1.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_2.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_2.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_3.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_3.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_4.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_4.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_5.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_5.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_6.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_6.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_7.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_7.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_8.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_8.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_9.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_9.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_10.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_10.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_11.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_11.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_12.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_12.setTextColor(getResources().getColor(R.color.color_text_grey));

    }


    public void clearAllBrands()
    {

        tv_brand_1.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_1.setTextColor(getResources().getColor(R.color.color_text_grey));

        tv_brand_2.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_2.setTextColor(getResources().getColor(R.color.color_text_grey));

        tv_brand_3.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_3.setTextColor(getResources().getColor(R.color.color_text_grey));

        tv_brand_4.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_4.setTextColor(getResources().getColor(R.color.color_text_grey));

        tv_brand_5.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_5.setTextColor(getResources().getColor(R.color.color_text_grey));
    }


    public void setAU(String braBand , String braCup)
    {


        clearAllBand();
        clearAllCup();


        switch (braBand)
        {
            case "8":
                tv_band_1.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "10":
                tv_band_2.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "12":
                tv_band_3.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "14":
                tv_band_4.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "16":
                tv_band_5.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "18":
                tv_band_6.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "20":
                tv_band_7.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_7.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "22":
                tv_band_8.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_8.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }



        switch (braCup)
        {
            case "aa":
                tv_cup_1.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "a":
                tv_cup_2.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "b":
                tv_cup_3.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "c":
                tv_cup_4.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "d":
                tv_cup_5.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "dd":
                tv_cup_6.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "e":
                tv_cup_7.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_7.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "f":
                tv_cup_8.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_8.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "g":
                tv_cup_9.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_9.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "h":
                tv_cup_10.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_10.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "i":
                tv_cup_11.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_11.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }


        tv_band_1.setText(getResources().getString(R.string.band_au_8));
        tv_band_2.setText(getResources().getString(R.string.band_au_10));
        tv_band_3.setText(getResources().getString(R.string.band_au_12));
        tv_band_4.setText(getResources().getString(R.string.band_au_14));
        tv_band_5.setText(getResources().getString(R.string.band_au_16));
        tv_band_6.setText(getResources().getString(R.string.band_au_18));
        tv_band_7.setText(getResources().getString(R.string.band_au_20));
        tv_band_8.setText(getResources().getString(R.string.band_au_22));
        layout_band_9.setVisibility(View.INVISIBLE);
        layout_band_10.setVisibility(View.INVISIBLE);
        layout_band_11.setVisibility(View.INVISIBLE);
        layout_band_12.setVisibility(View.INVISIBLE);


        tv_cup_1.setText(getResources().getString(R.string.cup_au_1));
        tv_cup_2.setText(getResources().getString(R.string.cup_au_2));
        tv_cup_3.setText(getResources().getString(R.string.cup_au_3));
        tv_cup_4.setText(getResources().getString(R.string.cup_au_4));
        tv_cup_5.setText(getResources().getString(R.string.cup_au_5));
        tv_cup_6.setText(getResources().getString(R.string.cup_au_6));
        tv_cup_7.setText(getResources().getString(R.string.cup_au_7));
        tv_cup_8.setText(getResources().getString(R.string.cup_au_8));
        tv_cup_9.setText(getResources().getString(R.string.cup_au_9));
        tv_cup_10.setText(getResources().getString(R.string.cup_au_10));
        tv_cup_11.setText(getResources().getString(R.string.cup_au_11));
        layout_cup_12.setVisibility(View.INVISIBLE);
        layout_cup_13.setVisibility(View.INVISIBLE);


        tv_au.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
        tv_eu.setBackgroundColor(getResources().getColor(R.color.white));
        tv_fr.setBackgroundColor(getResources().getColor(R.color.white));
        tv_uk.setBackgroundColor(getResources().getColor(R.color.white));
        tv_us.setBackgroundColor(getResources().getColor(R.color.white));

        tv_type_size.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

    }



    public void setEU(String braBand , String braCup)
    {
        tv_band_1.setText(getResources().getString(R.string.band_eu_8));
        tv_band_2.setText(getResources().getString(R.string.band_eu_10));
        tv_band_3.setText(getResources().getString(R.string.band_eu_12));
        tv_band_4.setText(getResources().getString(R.string.band_eu_14));
        tv_band_5.setText(getResources().getString(R.string.band_eu_16));
        tv_band_6.setText(getResources().getString(R.string.band_eu_18));
        tv_band_7.setText(getResources().getString(R.string.band_eu_20));
        tv_band_8.setText(getResources().getString(R.string.band_eu_22));
        layout_band_9.setVisibility(View.INVISIBLE);
        layout_band_10.setVisibility(View.INVISIBLE);
        layout_band_11.setVisibility(View.INVISIBLE);
        layout_band_12.setVisibility(View.INVISIBLE);

        tv_cup_1.setText(getResources().getString(R.string.cup_eu_1));
        tv_cup_2.setText(getResources().getString(R.string.cup_eu_2));
        tv_cup_3.setText(getResources().getString(R.string.cup_eu_3));
        tv_cup_4.setText(getResources().getString(R.string.cup_eu_4));
        tv_cup_5.setText(getResources().getString(R.string.cup_eu_5));
        tv_cup_6.setText(getResources().getString(R.string.cup_eu_6));
        tv_cup_7.setText(getResources().getString(R.string.cup_eu_7));
        tv_cup_8.setText(getResources().getString(R.string.cup_eu_8));
        tv_cup_9.setText(getResources().getString(R.string.cup_eu_9));
        tv_cup_10.setText(getResources().getString(R.string.cup_eu_10));
        tv_cup_11.setText(getResources().getString(R.string.cup_eu_11));
        layout_cup_12.setVisibility(View.INVISIBLE);
        layout_cup_13.setVisibility(View.INVISIBLE);




        switch (braBand)
        {
            case "65":
                tv_band_1.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "70":
                tv_band_2.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "75":
                tv_band_3.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "80":
                tv_band_4.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "85":
                tv_band_5.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "90":
                tv_band_6.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "95":
                tv_band_7.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_7.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "100":
                tv_band_8.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_8.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }



        switch (braCup)
        {
            case "a":
                tv_cup_1.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "b":
                tv_cup_2.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "c":
                tv_cup_3.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "d":
                tv_cup_4.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "e":
                tv_cup_5.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "f":
                tv_cup_6.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "g":
                tv_cup_7.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_7.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "h":
                tv_cup_8.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_8.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "i":
                tv_cup_9.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_9.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "j":
                tv_cup_10.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_10.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "k":
                tv_cup_11.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_11.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }


        tv_au.setBackgroundColor(getResources().getColor(R.color.white));
        tv_eu.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
        tv_fr.setBackgroundColor(getResources().getColor(R.color.white));
        tv_uk.setBackgroundColor(getResources().getColor(R.color.white));
        tv_us.setBackgroundColor(getResources().getColor(R.color.white));

        tv_type_size.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

    }


    public void setFR(String braBand , String braCup)
    {
        tv_band_1.setText(getResources().getString(R.string.band_fr_8));
        tv_band_2.setText(getResources().getString(R.string.band_fr_10));
        tv_band_3.setText(getResources().getString(R.string.band_fr_12));
        tv_band_4.setText(getResources().getString(R.string.band_fr_14));
        tv_band_5.setText(getResources().getString(R.string.band_fr_16));
        tv_band_6.setText(getResources().getString(R.string.band_fr_18));
        tv_band_7.setText(getResources().getString(R.string.band_fr_20));
        tv_band_8.setText(getResources().getString(R.string.band_fr_22));
        layout_band_9.setVisibility(View.INVISIBLE);
        layout_band_10.setVisibility(View.INVISIBLE);
        layout_band_11.setVisibility(View.INVISIBLE);
        layout_band_12.setVisibility(View.INVISIBLE);

        tv_cup_1.setText(getResources().getString(R.string.cup_fr_1));
        tv_cup_2.setText(getResources().getString(R.string.cup_fr_2));
        tv_cup_3.setText(getResources().getString(R.string.cup_fr_3));
        tv_cup_4.setText(getResources().getString(R.string.cup_fr_4));
        tv_cup_5.setText(getResources().getString(R.string.cup_fr_5));
        tv_cup_6.setText(getResources().getString(R.string.cup_fr_6));
        tv_cup_7.setText(getResources().getString(R.string.cup_fr_7));
        tv_cup_8.setText(getResources().getString(R.string.cup_fr_8));
        tv_cup_9.setText(getResources().getString(R.string.cup_fr_9));
        tv_cup_10.setText(getResources().getString(R.string.cup_fr_10));
        tv_cup_11.setText(getResources().getString(R.string.cup_fr_11));
        layout_cup_12.setVisibility(View.INVISIBLE);
        layout_cup_13.setVisibility(View.INVISIBLE);




        switch (braBand)
        {
            case "80":
                tv_band_1.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "85":
                tv_band_2.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "90":
                tv_band_3.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "95":
                tv_band_4.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "100":
                tv_band_5.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "105":
                tv_band_6.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "110":
                tv_band_7.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_7.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "115":
                tv_band_8.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_8.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }



        switch (braCup)
        {
            case "a":
                tv_cup_1.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "b":
                tv_cup_2.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "c":
                tv_cup_3.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "d":
                tv_cup_4.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "e":
                tv_cup_5.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "f":
                tv_cup_6.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "g":
                tv_cup_7.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_7.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "h":
                tv_cup_8.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_8.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "i":
                tv_cup_9.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_9.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "j":
                tv_cup_10.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_10.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "k":
                tv_cup_11.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_11.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }



        tv_au.setBackgroundColor(getResources().getColor(R.color.white));
        tv_eu.setBackgroundColor(getResources().getColor(R.color.white));
        tv_fr.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
        tv_uk.setBackgroundColor(getResources().getColor(R.color.white));
        tv_us.setBackgroundColor(getResources().getColor(R.color.white));

        tv_type_size.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

    }



    public void setUK(String braBand , String braCup)
    {
        tv_band_1.setText(getResources().getString(R.string.band_uk_8));
        tv_band_2.setText(getResources().getString(R.string.band_uk_10));
        tv_band_3.setText(getResources().getString(R.string.band_uk_12));
        tv_band_4.setText(getResources().getString(R.string.band_uk_14));
        tv_band_5.setText(getResources().getString(R.string.band_uk_16));
        tv_band_6.setText(getResources().getString(R.string.band_uk_18));
        tv_band_7.setText(getResources().getString(R.string.band_uk_20));
        tv_band_8.setText(getResources().getString(R.string.band_uk_22));
        layout_band_9.setVisibility(View.INVISIBLE);
        layout_band_10.setVisibility(View.INVISIBLE);
        layout_band_11.setVisibility(View.INVISIBLE);
        layout_band_12.setVisibility(View.INVISIBLE);


        tv_cup_1.setText(getResources().getString(R.string.cup_uk_1));
        tv_cup_2.setText(getResources().getString(R.string.cup_uk_2));
        tv_cup_3.setText(getResources().getString(R.string.cup_uk_3));
        tv_cup_4.setText(getResources().getString(R.string.cup_uk_4));
        tv_cup_5.setText(getResources().getString(R.string.cup_uk_5));
        tv_cup_6.setText(getResources().getString(R.string.cup_uk_6));
        tv_cup_7.setText(getResources().getString(R.string.cup_uk_7));
        tv_cup_8.setText(getResources().getString(R.string.cup_uk_8));
        tv_cup_9.setText(getResources().getString(R.string.cup_uk_9));
        tv_cup_10.setText(getResources().getString(R.string.cup_uk_10));
        tv_cup_11.setText(getResources().getString(R.string.cup_uk_11));
        layout_cup_12.setVisibility(View.INVISIBLE);
        layout_cup_13.setVisibility(View.INVISIBLE);




        switch (braBand)
        {
            case "30":
                tv_band_1.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "32":
                tv_band_2.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "34":
                tv_band_3.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "36":
                tv_band_4.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "38":
                tv_band_5.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "40":
                tv_band_6.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "42":
                tv_band_7.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_7.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "44":
                tv_band_8.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_8.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }



        switch (braCup)
        {
            case "a":
                tv_cup_1.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "b":
                tv_cup_2.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "c":
                tv_cup_3.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "d":
                tv_cup_4.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "dd":
                tv_cup_5.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "e":
                tv_cup_6.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "f":
                tv_cup_7.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_7.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "g":
                tv_cup_8.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_8.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "h":
                tv_cup_9.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_9.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "i":
                tv_cup_10.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_10.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "j":
                tv_cup_11.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_11.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }

        tv_au.setBackgroundColor(getResources().getColor(R.color.white));
        tv_eu.setBackgroundColor(getResources().getColor(R.color.white));
        tv_fr.setBackgroundColor(getResources().getColor(R.color.white));
        tv_uk.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
        tv_us.setBackgroundColor(getResources().getColor(R.color.white));

        tv_type_size.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
    }



    public void setUS(String braBand , String braCup)
    {
        tv_band_1.setText(getResources().getString(R.string.band_us_8));
        tv_band_2.setText(getResources().getString(R.string.band_us_10));
        tv_band_3.setText(getResources().getString(R.string.band_us_12));
        tv_band_4.setText(getResources().getString(R.string.band_us_14));
        tv_band_5.setText(getResources().getString(R.string.band_us_16));
        tv_band_6.setText(getResources().getString(R.string.band_us_18));
        tv_band_7.setText(getResources().getString(R.string.band_us_20));
        tv_band_8.setText(getResources().getString(R.string.band_us_22));
        layout_band_9.setVisibility(View.INVISIBLE);
        layout_band_10.setVisibility(View.INVISIBLE);
        layout_band_11.setVisibility(View.INVISIBLE);
        layout_band_12.setVisibility(View.INVISIBLE);


        tv_cup_1.setText(getResources().getString(R.string.cup_us_1));
        tv_cup_2.setText(getResources().getString(R.string.cup_us_2));
        tv_cup_3.setText(getResources().getString(R.string.cup_us_3));
        tv_cup_4.setText(getResources().getString(R.string.cup_us_4));
        tv_cup_5.setText(getResources().getString(R.string.cup_us_5));
        tv_cup_6.setText(getResources().getString(R.string.cup_us_6));
        tv_cup_7.setText(getResources().getString(R.string.cup_us_7));
        tv_cup_8.setText(getResources().getString(R.string.cup_us_8));
        tv_cup_9.setText(getResources().getString(R.string.cup_us_9));
        tv_cup_10.setText(getResources().getString(R.string.cup_us_10));
        tv_cup_11.setText(getResources().getString(R.string.cup_us_11));
        layout_cup_12.setVisibility(View.INVISIBLE);
        layout_cup_13.setVisibility(View.INVISIBLE);




        switch (braBand)
        {
            case "30":
                tv_band_1.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "32":
                tv_band_2.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "34":
                tv_band_3.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "36":
                tv_band_4.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "38":
                tv_band_5.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "40":
                tv_band_6.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "42":
                tv_band_7.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_7.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "44":
                tv_band_8.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_8.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }



        switch (braCup)
        {
            case "a":
                tv_cup_1.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "b":
                tv_cup_2.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "c":
                tv_cup_3.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "d":
                tv_cup_4.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "dd":
                tv_cup_5.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "ddd":
                tv_cup_6.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "e":
                tv_cup_7.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_7.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "f":
                tv_cup_8.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_8.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "g":
                tv_cup_9.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_9.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "h":
                tv_cup_10.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_10.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "i":
                tv_cup_11.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_11.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }

        tv_au.setBackgroundColor(getResources().getColor(R.color.white));
        tv_eu.setBackgroundColor(getResources().getColor(R.color.white));
        tv_fr.setBackgroundColor(getResources().getColor(R.color.white));
        tv_uk.setBackgroundColor(getResources().getColor(R.color.white));
        tv_us.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));

        tv_type_size.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

    }

    public void  setBrandRange(String range)
    {

        if (range.equalsIgnoreCase("regular"))
        {
            setBrandRangeRegular(brandCharts,0);
        }

        if (range.equalsIgnoreCase("plus"))
        {
            setBrandRangePlus(brandCharts,0);
        }

    }



    public void setBrandRangeRegular(String result , int position)
    {


        clearSelectedBand();

        try {

            JSONObject object = new JSONObject(result);

            if (object.has("regular"))
            {

                JSONArray regular = new JSONArray(object.getString("regular"));

                for (int i = 0; i < regular.length(); i++)
                {

                    JSONObject obj = regular.getJSONObject(i);

                    if (i == position)
                    {
                        setBrandBandSize(obj.getString("charts"),"");

                        setBrandBand(object.getString("regular"));
                    }
                }


            }




        } catch (JSONException e) {
            e.printStackTrace();
        }


        switch (position)
        {
            case 0:
                tv_brand_band_1.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                tv_brand_range.setText(tv_brand_band_1.getText().toString());

                break;

            case 1:
                tv_brand_band_2.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                break;

            case 2:
                tv_brand_band_3.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                break;

            case 3:
                tv_brand_band_4.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                break;

            case 4:
                tv_brand_band_5.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                break;

            case 5:
                tv_brand_band_6.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                break;

            case 6:
                tv_brand_band_7.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                break;

            case 7:
                tv_brand_band_8.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                break;
        }

        layout_brand_band_selection.setVisibility(View.GONE);

        tv_brand_range.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

    }



    public void setBrandRangeBand(String result , String range , String band , String sizeType , String brandSize)
    {

        Log.e("result",result);
        Log.e("range",range);
        Log.e("band",band);
        Log.e("sizeType",sizeType);
        Log.e("brandSize",brandSize);


        try {

            JSONObject object = new JSONObject(result);

            if (object.has(range))
            {

                JSONArray rangeArray = new JSONArray(object.getString(range));

                for (int i = 0; i < rangeArray.length(); i++)
                {

                    JSONObject obj = rangeArray.getJSONObject(i);

                    if (obj.getString("region").toLowerCase().equals(sizeType.toLowerCase()))
                    {

                        tv_brand_range.setText(obj.getString("region"));

                        setBrandBandSize(obj.getString("charts"),brandSize);

                        setBrandBand(object.getString("regular"));

                    }
                }


            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        layout_brand_band_selection.setVisibility(View.GONE);

        tv_brand_range.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

    }

    public void setBrandRangePlus(String result , int position)
    {

        clearSelectedBand();

        try {

            JSONObject object = new JSONObject(result);

            if (object.has("plus"))
            {



                JSONArray plus = new JSONArray(object.getString("plus"));

                layout_range_1.setVisibility(View.VISIBLE);

                for (int i = 0; i < plus.length(); i++)
                {

                    JSONObject obj = plus.getJSONObject(i);

                    if (i == position)
                    {
                        setBrandBandSize(obj.getString("charts"),"");

                        setBrandBand(object.getString("plus"));
                    }
                }
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }


        switch (position)
        {
            case 0:
                tv_brand_band_1.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                tv_brand_range.setText(tv_brand_band_1.getText().toString());
                break;

            case 1:
                tv_brand_band_2.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                break;

            case 2:
                tv_brand_band_3.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                break;

            case 3:
                tv_brand_band_4.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                break;

            case 4:
                tv_brand_band_5.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                break;

            case 5:
                tv_brand_band_6.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                break;

            case 6:
                tv_brand_band_7.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                break;

            case 7:
                tv_brand_band_8.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                break;
        }

        layout_brand_band_selection.setVisibility(View.GONE);
        tv_brand_range.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

    }


    public void clearSelectedBand()
    {
        tv_brand_band_1.setBackgroundColor(getResources().getColor(R.color.white));
        tv_brand_band_2.setBackgroundColor(getResources().getColor(R.color.white));
        tv_brand_band_3.setBackgroundColor(getResources().getColor(R.color.white));
        tv_brand_band_4.setBackgroundColor(getResources().getColor(R.color.white));
        tv_brand_band_5.setBackgroundColor(getResources().getColor(R.color.white));
        tv_brand_band_6.setBackgroundColor(getResources().getColor(R.color.white));
        tv_brand_band_7.setBackgroundColor(getResources().getColor(R.color.white));
        tv_brand_band_8.setBackgroundColor(getResources().getColor(R.color.white));



        tv_brand_band_1.setVisibility(View.GONE);
        tv_brand_band_2.setVisibility(View.GONE);
        tv_brand_band_3.setVisibility(View.GONE);
        tv_brand_band_4.setVisibility(View.GONE);
        tv_brand_band_5.setVisibility(View.GONE);
        tv_brand_band_6.setVisibility(View.GONE);
        tv_brand_band_7.setVisibility(View.GONE);
        tv_brand_band_8.setVisibility(View.GONE);




        layout_brand_size_1.setVisibility(View.INVISIBLE);
        layout_brand_size_2.setVisibility(View.INVISIBLE);
        layout_brand_size_3.setVisibility(View.INVISIBLE);
        layout_brand_size_4.setVisibility(View.INVISIBLE);
        layout_brand_size_5.setVisibility(View.INVISIBLE);
        layout_brand_size_6.setVisibility(View.INVISIBLE);
        layout_brand_size_7.setVisibility(View.INVISIBLE);
        layout_brand_size_8.setVisibility(View.INVISIBLE);
        layout_brand_size_9.setVisibility(View.INVISIBLE);
        layout_brand_size_10.setVisibility(View.INVISIBLE);
        layout_brand_size_11.setVisibility(View.INVISIBLE);
        layout_brand_size_12.setVisibility(View.INVISIBLE);
        layout_brand_size_13.setVisibility(View.INVISIBLE);
        layout_brand_size_14.setVisibility(View.INVISIBLE);
        layout_brand_size_15.setVisibility(View.INVISIBLE);
        layout_brand_size_16.setVisibility(View.INVISIBLE);
        layout_brand_size_17.setVisibility(View.INVISIBLE);
        layout_brand_size_18.setVisibility(View.INVISIBLE);
        layout_brand_size_19.setVisibility(View.INVISIBLE);
        layout_brand_size_20.setVisibility(View.INVISIBLE);
        layout_brand_size_21.setVisibility(View.INVISIBLE);
        layout_brand_size_22.setVisibility(View.INVISIBLE);

        layout_brand_size_next.setVisibility(View.INVISIBLE);

        layout_brand_size_row1.setVisibility(View.VISIBLE);
        layout_brand_size_row2.setVisibility(View.VISIBLE);
        layout_brand_size_row3.setVisibility(View.GONE);
        layout_brand_size_row4.setVisibility(View.GONE);

    }


    public void setBrandBand(String result)
    {
        try {

            JSONArray range = new JSONArray(result);


            for (int i = 0; i < range.length(); i++)
            {

                JSONObject obj = range.getJSONObject(i);

                if ( i == 0)
                {
                    tv_brand_band_1.setText(obj.getString("region"));
                    tv_brand_band_1.setVisibility(View.VISIBLE);
                }

                if (i == 1)
                {
                    tv_brand_band_2.setText(obj.getString("region"));
                    tv_brand_band_2.setVisibility(View.VISIBLE);
                }

                if (i == 2)
                {
                    tv_brand_band_3.setText(obj.getString("region"));
                    tv_brand_band_3.setVisibility(View.VISIBLE);
                }

                if (i == 3)
                {
                    tv_brand_band_4.setText(obj.getString("region"));
                    tv_brand_band_4.setVisibility(View.VISIBLE);
                }

                if (i == 4)
                {
                    tv_brand_band_5.setText(obj.getString("region"));
                    tv_brand_band_5.setVisibility(View.VISIBLE);
                }

                if (i == 5)
                {
                    tv_brand_band_6.setText(obj.getString("region"));
                    tv_brand_band_6.setVisibility(View.VISIBLE);
                }

                if (i == 6)
                {
                    tv_brand_band_7.setText(obj.getString("region"));
                    tv_brand_band_7.setVisibility(View.VISIBLE);
                }

                if (i == 7)
                {
                    tv_brand_band_8.setText(obj.getString("region"));
                    tv_brand_band_8.setVisibility(View.VISIBLE);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void setBrandBandSize(String result,String brandSize)
    {
        try {



            JSONArray charts = new JSONArray(result);

            for (int j = 0; j < charts.length(); j++)
            {
                JSONObject obj1 = charts.getJSONObject(j);


                if (obj1.getString("size").toLowerCase().equals(brandSize.toLowerCase()))
                {
                    Log.e("Position", String.valueOf(j));
                    isSizeMatched = true;
                }


                if (j == 0)
                {
                    layout_brand_size_1.setVisibility(View.VISIBLE);
                    tv_brand_size_1.setText(obj1.getString("size"));

                    if (isSizeMatched)
                    {
                        setSize(tv_brand_size_1,layout_brand_size_1);
                    }

                }

                if (j == 1)
                {
                    layout_brand_size_2.setVisibility(View.VISIBLE);
                    tv_brand_size_2.setText(obj1.getString("size"));
                    if (isSizeMatched)
                    {
                        setSize(tv_brand_size_2,layout_brand_size_2);
                    }
                }

                if (j == 2)
                {
                    layout_brand_size_3.setVisibility(View.VISIBLE);
                    tv_brand_size_3.setText(obj1.getString("size"));
                    if (isSizeMatched)
                    {
                        setSize(tv_brand_size_3,layout_brand_size_3);
                    }
                }

                if (j == 3)
                {
                    layout_brand_size_4.setVisibility(View.VISIBLE);
                    tv_brand_size_4.setText(obj1.getString("size"));
                    if (isSizeMatched)
                    {
                        setSize(tv_brand_size_4,layout_brand_size_4);
                    }
                }

                if (j == 4)
                {
                    layout_brand_size_5.setVisibility(View.VISIBLE);
                    tv_brand_size_5.setText(obj1.getString("size"));
                    if (isSizeMatched)
                    {
                        setSize(tv_brand_size_5,layout_brand_size_5);
                    }
                }

                if (j == 5)
                {
                    layout_brand_size_6.setVisibility(View.VISIBLE);
                    tv_brand_size_6.setText(obj1.getString("size"));
                    if (isSizeMatched)
                    {
                        setSize(tv_brand_size_6,layout_brand_size_6);
                    }
                }

                if (j == 6)
                {
                    layout_brand_size_7.setVisibility(View.VISIBLE);
                    tv_brand_size_7.setText(obj1.getString("size"));
                    if (isSizeMatched)
                    {
                        setSize(tv_brand_size_7,layout_brand_size_7);
                    }
                }

                if (j == 7)
                {
                    layout_brand_size_8.setVisibility(View.VISIBLE);
                    tv_brand_size_8.setText(obj1.getString("size"));
                    if (isSizeMatched)
                    {
                        setSize(tv_brand_size_8,layout_brand_size_8);
                    }
                }

                if (j == 8)
                {
                    layout_brand_size_9.setVisibility(View.VISIBLE);
                    tv_brand_size_9.setText(obj1.getString("size"));
                    if (isSizeMatched)
                    {
                        setSize(tv_brand_size_9,layout_brand_size_9);
                    }
                }

                if (j == 9)
                {
                    layout_brand_size_10.setVisibility(View.VISIBLE);
                    tv_brand_size_10.setText(obj1.getString("size"));
                    if (isSizeMatched)
                    {
                        setSize(tv_brand_size_10,layout_brand_size_10);
                    }
                }

                if (j == 10)
                {
                    layout_brand_size_11.setVisibility(View.VISIBLE);
                    tv_brand_size_11.setText(obj1.getString("size"));
                    if (isSizeMatched)
                    {
                        setSize(tv_brand_size_11,layout_brand_size_11);
                    }
                }

                if (j == 11)
                {
                    layout_brand_size_12.setVisibility(View.VISIBLE);
                    tv_brand_size_12.setText(obj1.getString("size"));
                    if (isSizeMatched)
                    {
                        setSize(tv_brand_size_12,layout_brand_size_12);
                    }
                }

                if (j == 12)
                {
                    layout_brand_size_next.setVisibility(View.VISIBLE);
                    layout_brand_size_13.setVisibility(View.VISIBLE);
                    tv_brand_size_13.setText(obj1.getString("size"));
                     if (isSizeMatched)
                    {
                        setSize(tv_brand_size_13,layout_brand_size_13);
                    }
                }

                if (j == 13)
                {
                    layout_brand_size_14.setVisibility(View.VISIBLE);
                    tv_brand_size_14.setText(obj1.getString("size"));
                     if (isSizeMatched)
                    {
                        setSize(tv_brand_size_14,layout_brand_size_14);
                    }
                }

                if (j == 14)
                {
                    layout_brand_size_15.setVisibility(View.VISIBLE);
                    tv_brand_size_15.setText(obj1.getString("size"));
                     if (isSizeMatched)
                    {
                        setSize(tv_brand_size_15,layout_brand_size_15);
                    }
                }

                if (j == 15)
                {
                    layout_brand_size_16.setVisibility(View.VISIBLE);
                    tv_brand_size_16.setText(obj1.getString("size"));
                     if (isSizeMatched)
                    {
                        setSize(tv_brand_size_16,layout_brand_size_16);
                    }
                }

                if (j == 16)
                {
                    layout_brand_size_17.setVisibility(View.VISIBLE);
                    tv_brand_size_17.setText(obj1.getString("size"));
                     if (isSizeMatched)
                    {
                        setSize(tv_brand_size_17,layout_brand_size_17);
                    }
                }

                if (j == 17)
                {
                    layout_brand_size_18.setVisibility(View.VISIBLE);
                    tv_brand_size_18.setText(obj1.getString("size"));
                     if (isSizeMatched)
                    {
                        setSize(tv_brand_size_18,layout_brand_size_18);
                    }
                }

                if (j == 18)
                {
                    layout_brand_size_19.setVisibility(View.VISIBLE);
                    tv_brand_size_19.setText(obj1.getString("size"));
                     if (isSizeMatched)
                    {
                        setSize(tv_brand_size_19,layout_brand_size_19);
                    }
                }

                if (j == 19)
                {
                    layout_brand_size_20.setVisibility(View.VISIBLE);
                    tv_brand_size_20.setText(obj1.getString("size"));
                     if (isSizeMatched)
                    {
                        setSize(tv_brand_size_20,layout_brand_size_20);
                    }
                }

                if (j == 20)
                {
                    layout_brand_size_21.setVisibility(View.VISIBLE);
                    tv_brand_size_21.setText(obj1.getString("size"));
                     if (isSizeMatched)
                    {
                        setSize(tv_brand_size_21,layout_brand_size_21);
                    }
                }

                if (j == 21)
                {
                    layout_brand_size_22.setVisibility(View.VISIBLE);
                    tv_brand_size_22.setText(obj1.getString("size"));
                     if (isSizeMatched)
                    {
                        setSize(tv_brand_size_22,layout_brand_size_22);
                    }
                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setSize(TextView textView , RelativeLayout relativeLayout)
    {
        textView.setTextColor(getResources().getColor(R.color.color_text));
        relativeLayout.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));

        isSizeMatched = false;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case  R.id.layout_band_1:

                clearAllBand();

                layout_band_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_1.setTextColor(getResources().getColor(R.color.color_text));

                band = tv_band_1.getText().toString().toLowerCase();

                checkBandCup(band,cup);
                break;

            case R.id.layout_band_2:

                clearAllBand();

                layout_band_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_2.setTextColor(getResources().getColor(R.color.color_text));

                band = tv_band_2.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;

            case R.id.layout_band_3:

                clearAllBand();

                layout_band_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_3.setTextColor(getResources().getColor(R.color.color_text));

                band = tv_band_3.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;

            case R.id.layout_band_4:

                clearAllBand();

                layout_band_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_4.setTextColor(getResources().getColor(R.color.color_text));

                band = tv_band_4.getText().toString().toLowerCase();

                break;

            case R.id.layout_band_5:

                clearAllBand();

                layout_band_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_5.setTextColor(getResources().getColor(R.color.color_text));

                band = tv_band_5.getText().toString().toLowerCase();

                checkBandCup(band,cup);
                break;

            case R.id.layout_band_6:

                clearAllBand();

                layout_band_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_6.setTextColor(getResources().getColor(R.color.color_text));

                band = tv_band_6.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;

            case R.id.layout_band_7:

                clearAllBand();

                layout_band_7.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_7.setTextColor(getResources().getColor(R.color.color_text));

                band = tv_band_7.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;

            case R.id.layout_band_8:

                clearAllBand();

                layout_band_8.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_8.setTextColor(getResources().getColor(R.color.color_text));

                band = tv_band_8.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;

            case R.id.layout_band_9:

                clearAllBand();

                layout_band_9.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_9.setTextColor(getResources().getColor(R.color.color_text));

                band = tv_band_9.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;

            case R.id.layout_band_10:

                clearAllBand();

                layout_band_10.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_10.setTextColor(getResources().getColor(R.color.color_text));

                band = tv_band_10.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;

            case R.id.layout_band_11:

                clearAllBand();

                layout_band_11.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_11.setTextColor(getResources().getColor(R.color.color_text));

                band = tv_band_11.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;

            case R.id.layout_band_12:

                clearAllBand();

                layout_band_12.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_12.setTextColor(getResources().getColor(R.color.color_text));

                band = tv_band_12.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;

            case R.id.layout_cup_1:

                clearAllCup();

                layout_cup_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_1.setTextColor(getResources().getColor(R.color.color_text));

                cup = tv_cup_1.getText().toString().toLowerCase();

                checkBandCup(band,cup);
                break;

            case R.id.layout_cup_2:

                clearAllCup();

                layout_cup_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_2.setTextColor(getResources().getColor(R.color.color_text));

                cup = tv_cup_2.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;

            case R.id.layout_cup_3:

                clearAllCup();

                layout_cup_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_3.setTextColor(getResources().getColor(R.color.color_text));

                cup = tv_cup_3.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;

            case R.id.layout_cup_4:

                clearAllCup();

                layout_cup_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_4.setTextColor(getResources().getColor(R.color.color_text));

                cup = tv_cup_4.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;

            case R.id.layout_cup_5:

                clearAllCup();

                layout_cup_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_5.setTextColor(getResources().getColor(R.color.color_text));

                cup = tv_cup_5.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;

            case R.id.layout_cup_6:

                clearAllCup();

                layout_cup_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_6.setTextColor(getResources().getColor(R.color.color_text));

                cup = tv_cup_6.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;

            case R.id.layout_cup_7:

                clearAllCup();

                layout_cup_7.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_7.setTextColor(getResources().getColor(R.color.color_text));

                cup = tv_cup_7.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;

            case R.id.layout_cup_8:

                clearAllCup();

                layout_cup_8.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_8.setTextColor(getResources().getColor(R.color.color_text));

                cup = tv_cup_8.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;

            case R.id.layout_cup_9:

                clearAllCup();

                layout_cup_9.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_9.setTextColor(getResources().getColor(R.color.color_text));

                cup = tv_cup_9.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;


            case R.id.layout_cup_10:

                clearAllCup();

                layout_cup_10.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_10.setTextColor(getResources().getColor(R.color.color_text));

                cup = tv_cup_10.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;


            case R.id.layout_cup_11:

                clearAllCup();

                layout_cup_11.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_11.setTextColor(getResources().getColor(R.color.color_text));

                cup = tv_cup_11.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;


            case R.id.layout_cup_12:

                clearAllCup();

                layout_cup_12.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_12.setTextColor(getResources().getColor(R.color.color_text));

                cup = tv_cup_12.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;


            case R.id.layout_cup_13:

                clearAllCup();

                layout_cup_13.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_13.setTextColor(getResources().getColor(R.color.color_text));

                cup = tv_cup_13.getText().toString().toLowerCase();
                checkBandCup(band,cup);
                break;


            case R.id.tv_au:

                clearAllBand();
                clearAllCup();

                layout_cup_row1.setVisibility(View.VISIBLE);
                layout_cup_row2.setVisibility(View.GONE);
                layout_cup_row3.setVisibility(View.GONE);

                cup = "";
                band = "";

                checkBandCup(band,cup);

                setAU("","");
                tv_type_size.setText(getResources().getString(R.string.au));
                layout_size_selection.setVisibility(View.GONE);


                break;



            case R.id.tv_eu:

                clearAllBand();
                clearAllCup();

                layout_cup_row1.setVisibility(View.VISIBLE);
                layout_cup_row2.setVisibility(View.GONE);
                layout_cup_row3.setVisibility(View.GONE);

                cup = "";
                band = "";

                checkBandCup(band,cup);

                setEU("","");

                tv_type_size.setText(getResources().getString(R.string.eu));
                layout_size_selection.setVisibility(View.GONE);

                break;



            case R.id.tv_fr:

                clearAllBand();
                clearAllCup();

                cup = "";
                band = "";

                layout_cup_row1.setVisibility(View.VISIBLE);
                layout_cup_row2.setVisibility(View.GONE);
                layout_cup_row3.setVisibility(View.GONE);

                setFR("","");

                checkBandCup(band,cup);

                tv_type_size.setText(getResources().getString(R.string.fr));
                layout_size_selection.setVisibility(View.GONE);

                break;

            case R.id.tv_uk:

                clearAllBand();
                clearAllCup();

                cup = "";
                band = "";

                layout_cup_row1.setVisibility(View.VISIBLE);
                layout_cup_row2.setVisibility(View.GONE);
                layout_cup_row3.setVisibility(View.GONE);

                setUK("","");

                checkBandCup(band,cup);
                tv_type_size.setText(getResources().getString(R.string.uk));
                layout_size_selection.setVisibility(View.GONE);

                break;

            case R.id.tv_us:

                clearAllBand();
                clearAllCup();

                cup = "";
                band = "";

                layout_cup_row1.setVisibility(View.VISIBLE);
                layout_cup_row2.setVisibility(View.GONE);
                layout_cup_row3.setVisibility(View.GONE);

                setUS("","");

                checkBandCup(band,cup);
                tv_type_size.setText(getResources().getString(R.string.us));
                layout_size_selection.setVisibility(View.GONE);

                break;


            case R.id.layout_brand_size_1:

                clearAllBrandSize();

                layout_brand_size_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_1.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));


                brandSize = tv_brand_size_1.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_2:

                clearAllBrandSize();

                layout_brand_size_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_2.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_2.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_3:

                clearAllBrandSize();

                layout_brand_size_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_3.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_3.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_4:

                clearAllBrandSize();

                layout_brand_size_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_4.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_4.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_5:

                clearAllBrandSize();

                layout_brand_size_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_5.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_5.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_6:

                clearAllBrandSize();

                layout_brand_size_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_6.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_6.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_7:

                clearAllBrandSize();

                layout_brand_size_7.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_7.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_7.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_8:

                clearAllBrandSize();

                layout_brand_size_8.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_8.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_8.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_9:

                clearAllBrandSize();

                layout_brand_size_9.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_9.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_9.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_10:

                clearAllBrandSize();

                layout_brand_size_10.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_10.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_10.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_11:

                clearAllBrandSize();

                layout_brand_size_11.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_11.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_11.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_12:

                clearAllBrandSize();

                layout_brand_size_12.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_12.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_12.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_13:

                clearAllBrandSize();

                layout_brand_size_13.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_13.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_13.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_14:

                clearAllBrandSize();

                layout_brand_size_14.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_14.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_14.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_15:

                clearAllBrandSize();

                layout_brand_size_15.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_15.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_15.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_16:

                clearAllBrandSize();

                layout_brand_size_16.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_16.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_16.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_17:

                clearAllBrandSize();

                layout_brand_size_17.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_17.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_17.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_18:

                clearAllBrandSize();

                layout_brand_size_18.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_18.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_18.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_19:

                clearAllBrandSize();

                layout_brand_size_19.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_19.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_19.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_20:

                clearAllBrandSize();

                layout_brand_size_20.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_20.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_20.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_21:

                clearAllBrandSize();

                layout_brand_size_21.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_21.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_21.getText().toString().toLowerCase();
                break;


            case R.id.layout_brand_size_22:

                clearAllBrandSize();

                layout_brand_size_22.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_22.setTextColor(getResources().getColor(R.color.color_text));
                tv_brand_fav_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                brandSize = tv_brand_size_22.getText().toString().toLowerCase();
                break;


            case R.id.layout_range_1:

                clearAllRange();

                layout_range_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_range_1.setTextColor(getResources().getColor(R.color.color_text));

                range = tv_range_1.getText().toString().toLowerCase();

                setBrandRange(range);

                break;


            case R.id.layout_range_2:

                clearAllRange();

                layout_range_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_range_2.setTextColor(getResources().getColor(R.color.color_text));

                range = tv_range_2.getText().toString().toLowerCase();

                setBrandRange(range);
                break;


            case R.id.layout_range_3:

                clearAllRange();

                layout_range_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_range_3.setTextColor(getResources().getColor(R.color.color_text));

                range = tv_range_3.getText().toString().toLowerCase();

                setBrandRange(range);
                break;



            case R.id.tv_brand_band_1:

                clearAllBrandSize();

                layout_brand_size_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_1.setTextColor(getResources().getColor(R.color.color_text));

                tv_brand_range.setText(tv_brand_band_1.getText().toString());
                tv_brand_range.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

                layout_brand_band_selection.setVisibility(View.GONE);

                sizeType = tv_brand_band_1.getText().toString().toLowerCase();


                if (range.equalsIgnoreCase("regular"))
                {
                    setBrandRangeRegular(brandCharts,0);
                }

                if (range.equalsIgnoreCase("plus"))
                {
                    setBrandRangePlus(brandCharts,0);
                }

                break;


            case R.id.tv_brand_band_2:

                clearAllBrandSize();

                layout_brand_size_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_1.setTextColor(getResources().getColor(R.color.color_text));

                tv_brand_range.setText(tv_brand_band_2.getText().toString());
                layout_brand_band_selection.setVisibility(View.GONE);
                tv_brand_range.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

                sizeType = tv_brand_band_2.getText().toString().toLowerCase();

                if (range.equalsIgnoreCase("regular"))
                {
                    setBrandRangeRegular(brandCharts,1);
                }

                if (range.equalsIgnoreCase("plus"))
                {
                    setBrandRangePlus(brandCharts,1);
                }
                break;


            case R.id.tv_brand_band_3:

                clearAllBrandSize();

                layout_brand_size_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_1.setTextColor(getResources().getColor(R.color.color_text));


                tv_brand_range.setText(tv_brand_band_3.getText().toString());
                layout_brand_band_selection.setVisibility(View.GONE);
                tv_brand_range.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

                sizeType = tv_brand_band_3.getText().toString().toLowerCase();

                if (range.equalsIgnoreCase("regular"))
                {
                    setBrandRangeRegular(brandCharts,2);
                }

                if (range.equalsIgnoreCase("plus"))
                {
                    setBrandRangePlus(brandCharts,2);
                }

                break;


            case R.id.tv_brand_band_4:

                clearAllBrandSize();

                layout_brand_size_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_1.setTextColor(getResources().getColor(R.color.color_text));

                tv_brand_range.setText(tv_brand_band_4.getText().toString());
                layout_brand_band_selection.setVisibility(View.GONE);
                tv_brand_range.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

                sizeType = tv_brand_band_4.getText().toString().toLowerCase();

                if (range.equalsIgnoreCase("regular"))
                {
                    setBrandRangeRegular(brandCharts,3);
                }

                if (range.equalsIgnoreCase("plus"))
                {
                    setBrandRangePlus(brandCharts,3);
                }

                break;



            case R.id.tv_brand_band_5:

                clearAllBrandSize();

                layout_brand_size_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_1.setTextColor(getResources().getColor(R.color.color_text));

                tv_brand_range.setText(tv_brand_band_5.getText().toString());
                layout_brand_band_selection.setVisibility(View.GONE);
                tv_brand_range.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

                sizeType = tv_brand_band_5.getText().toString().toLowerCase();


                if (range.equalsIgnoreCase("regular"))
                {
                    setBrandRangeRegular(brandCharts,4);
                }

                if (range.equalsIgnoreCase("plus"))
                {
                    setBrandRangePlus(brandCharts,4);
                }

                break;



            case R.id.tv_brand_band_6:

                clearAllBrandSize();

                layout_brand_size_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_1.setTextColor(getResources().getColor(R.color.color_text));

                tv_brand_range.setText(tv_brand_band_6.getText().toString());
                layout_brand_band_selection.setVisibility(View.GONE);
                tv_brand_range.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

                sizeType = tv_brand_band_6.getText().toString().toLowerCase();



                if (range.equalsIgnoreCase("regular"))
                {
                    setBrandRangeRegular(brandCharts,5);
                }

                if (range.equalsIgnoreCase("plus"))
                {
                    setBrandRangePlus(brandCharts,5);
                }

                break;



            case R.id.tv_brand_band_7:

                clearAllBrandSize();

                layout_brand_size_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_1.setTextColor(getResources().getColor(R.color.color_text));


                tv_brand_range.setText(tv_brand_band_7.getText().toString());
                layout_brand_band_selection.setVisibility(View.GONE);
                tv_brand_range.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

                sizeType = tv_brand_band_7.getText().toString().toLowerCase();


                if (range.equalsIgnoreCase("regular"))
                {
                    setBrandRangeRegular(brandCharts,6);
                }

                if (range.equalsIgnoreCase("plus"))
                {
                    setBrandRangePlus(brandCharts,6);
                }

                break;



            case R.id.tv_brand_band_8:

                clearAllBrandSize();

                layout_brand_size_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_size_1.setTextColor(getResources().getColor(R.color.color_text));



                tv_brand_range.setText(tv_brand_band_8.getText().toString());
                layout_brand_band_selection.setVisibility(View.GONE);
                tv_brand_range.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

                sizeType = tv_brand_band_8.getText().toString().toLowerCase();


                if (range.equalsIgnoreCase("regular"))
                {
                    setBrandRangeRegular(brandCharts,7);
                }

                if (range.equalsIgnoreCase("plus"))
                {
                    setBrandRangePlus(brandCharts,7);
                }

                break;



            case R.id.tv_brand_1:

                clearAllBrands();

                brand = tv_brand_1.getText().toString();

                isBrandSelected = true;
                isEditFlow = false;

                et_what_brand.setText(tv_brand_1.getText().toString());
                et_what_brand.setTextColor(getResources().getColor(R.color.black));
                et_what_brand.setSelection(et_what_brand.getText().length());
                layout_brand_search.setVisibility(View.GONE);
                tv_brand_continue.setBackgroundColor(getResources().getColor(R.color.color_background_button_enable));

                tv_brand_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_1.setTextColor(getResources().getColor(R.color.color_text));




                break;


            case R.id.tv_brand_2:

                clearAllBrands();

                brand = tv_brand_2.getText().toString();

                isBrandSelected = true;
                isEditFlow = false;

                et_what_brand.setText(tv_brand_2.getText().toString());
                et_what_brand.setTextColor(getResources().getColor(R.color.black));
                et_what_brand.setSelection(et_what_brand.getText().length());
                layout_brand_search.setVisibility(View.GONE);
                tv_brand_continue.setBackgroundColor(getResources().getColor(R.color.color_background_button_enable));

                tv_brand_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_2.setTextColor(getResources().getColor(R.color.color_text));

                break;


            case R.id.tv_brand_3:

                clearAllBrands();

                brand = tv_brand_3.getText().toString();

                isBrandSelected = true;
                isEditFlow = false;

                et_what_brand.setText(tv_brand_3.getText().toString());
                et_what_brand.setTextColor(getResources().getColor(R.color.black));
                et_what_brand.setSelection(et_what_brand.getText().length());
                layout_brand_search.setVisibility(View.GONE);
                tv_brand_continue.setBackgroundColor(getResources().getColor(R.color.color_background_button_enable));

                tv_brand_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_3.setTextColor(getResources().getColor(R.color.color_text));

                break;


            case R.id.tv_brand_4:

                clearAllBrands();

                brand = tv_brand_4.getText().toString();

                isBrandSelected = true;
                isEditFlow = false;

                et_what_brand.setText(tv_brand_4.getText().toString());
                et_what_brand.setTextColor(getResources().getColor(R.color.black));
                et_what_brand.setSelection(et_what_brand.getText().length());
                layout_brand_search.setVisibility(View.GONE);
                tv_brand_continue.setBackgroundColor(getResources().getColor(R.color.color_background_button_enable));

                tv_brand_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_4.setTextColor(getResources().getColor(R.color.color_text));

                break;

        }
    }


    void brand_filter(String text){

        List<BrandModel> temp = new ArrayList();

        temp.clear();

        for(BrandModel d: brandModelArrayList){
            if(d.getName().toLowerCase().startsWith(text.toLowerCase())){

                if (temp.size() < 4)
                {
                    temp.add(d);
                }

                et_what_brand.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                layout_add.setVisibility(View.GONE);
                tv_brand_error.setVisibility(View.GONE);
                layout_brands.setVisibility(View.VISIBLE);

            }

            if (temp.size() == 0)
            {
                tv_brand_error.setText(getResources().getString(R.string.brand_not_in_list));
                layout_add.setVisibility(View.VISIBLE);
                tv_brand_error.setVisibility(View.VISIBLE);
                layout_brands.setVisibility(View.INVISIBLE);
                et_what_brand.setBackground(getResources().getDrawable(R.drawable.bg_button_error));
            }
        }
        brandAdapter.updateList(temp);
        brandAdapter.notifyDataSetChanged();

        layout_brand_search.setVisibility(View.VISIBLE);
    }


    private void getData() {


        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {

                if (altId.equals(""))
                {
                    GET get = new GET(this, "https://discoverysvc.pixibo.com/uid/"+uID , ValidateUserUid, this);
                    get.execute();
                }
                else
                {
                    GET get = new GET(this, "https://discoverysvc.pixibo.com/uid/"+altId , ValidateUserUid, this);
                    get.execute();
                }

            } else {

                layout_error.setVisibility(View.VISIBLE);
                layout_loading.setVisibility(View.GONE);
                Utils.showToast(this,getResources().getString(R.string.no_internet));
            }

        } catch (Exception e) {
            //Utils.hideLoading();
            Utils.showToast(this,getResources().getString(R.string.something_wrong));
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
        }
    }


    private void get_brands(String gender ,String category) {


        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {
                GET get = new GET(this, URI.base2 +"brands/list?gender="+gender.toLowerCase()+"&category="+category.toLowerCase() , BrandSuggestion, this);
                // Utils.showLoading(SettingActivity.this, false);
                get.execute();
            } else {
                Utils.showToast(this,getResources().getString(R.string.no_internet));
            }

        } catch (Exception e) {
            //Utils.hideLoading();
            layout_error.setVisibility(View.VISIBLE);
            layout_loading.setVisibility(View.GONE);
            Utils.showToast(this,getResources().getString(R.string.something_wrong));
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
        }
    }


    private void get_brand_sizes(String gender ,String category,String brand) {

        if (!hasSize)
        {
            layout_loading.setVisibility(View.VISIBLE);
            loader_text.setVisibility(View.INVISIBLE);
        }

        try {

        if (NetworkUtils.getInstance(this).isConnectedToInternet()) {
            GET get = new GET(this, URI.base2 +"brands/charts/"+gender.toLowerCase()+"/"+category.toLowerCase()+"/"+brand , BrandSizes, this);
            // Utils.showLoading(SettingActivity.this, false);
            get.execute();
        } else {
            layout_error.setVisibility(View.VISIBLE);
            layout_loading.setVisibility(View.GONE);
            Utils.showToast(this,getResources().getString(R.string.no_internet));
        }

    } catch (Exception e) {
        //Utils.hideLoading();
        Utils.showToast(this,getResources().getString(R.string.something_wrong));
        e.printStackTrace();
        Log.e("Exception",e.getMessage());
    }
}


    private void trackEvent(String clientID ,String SKUID,String eventType,String page,String event,String uid ) {

        Log.e("Event Track: eventType",eventType);
        Log.e("Event Track: page",page);
        Log.e("Event Track: event",event);

        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {
                GET get = new GET(this, "https://sizeguidev2.pixibo.com/event/"+clientID+"/"+SKUID+"?eventType="+eventType+"&page="+page+"&event="+event+"&uid="+uid+"&source=app" , Track, this);
                // Utils.showLoading(SettingActivity.this, false);
                get.execute();
            } else {
                layout_error.setVisibility(View.VISIBLE);
                layout_loading.setVisibility(View.GONE);
                Utils.showToast(this,getResources().getString(R.string.no_internet));
            }

        } catch (Exception e) {
            //Utils.hideLoading();
            Utils.showToast(this,getResources().getString(R.string.something_wrong));
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
        }
    }


    private void setBodyProfile(String uID,String ht, String wt , String age) {


        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {

                GET get = new GET(this, "https://sizeguidev2.pixibo.com/asset/"+clientId+"/"+skuId+"?uid="+uID+"&ht="+ht+"&wt="+wt+"&age="+age+"&ftp=2" , BodyProfile, this);
                get.execute();

            } else {
                layout_error.setVisibility(View.VISIBLE);
                layout_loading.setVisibility(View.GONE);
                Utils.showToast(this,getResources().getString(R.string.no_internet));
            }

        } catch (Exception e) {
            //Utils.hideLoading();
            Utils.showToast(this,getResources().getString(R.string.something_wrong));
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
        }
    }


    private void setBraSize(String uID,String ht, String wt , String age ,String rg ,String bs , String cu) {

        layout_loading.setVisibility(View.VISIBLE);
        loader_text.setVisibility(View.INVISIBLE);

        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {

                GET get = new GET(this, "https://sizeguidev2.pixibo.com/asset/"+clientId+"/"+skuId+"?uid="+uID+"&ht="+ht+"&wt="+wt+"&age="+age+"&ftp=0"+"&rg="+rg+"&bs="+bs+"&cu="+cu , BraSize, this);
                get.execute();

            } else {
                layout_error.setVisibility(View.VISIBLE);
                layout_loading.setVisibility(View.GONE);
                Utils.showToast(this,getResources().getString(R.string.no_internet));
            }

        } catch (Exception e) {
            //Utils.hideLoading();
            Utils.showToast(this,getResources().getString(R.string.something_wrong));
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
        }
    }


    private void setBustSize(String uID,String ht, String wt , String age ,String bu ) {
        layout_loading.setVisibility(View.VISIBLE);
        loader_text.setVisibility(View.INVISIBLE);

        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {

                GET get = new GET(this, "https://sizeguidev2.pixibo.com/asset/"+clientId+"/"+skuId+"?uid="+uID+"&ht="+ht+"&wt="+wt+"&age="+age+"&ftp=2&bu="+bu , BraSize, this);
                get.execute();

            } else {
                Utils.showToast(this,getResources().getString(R.string.no_internet));
                layout_error.setVisibility(View.VISIBLE);
                layout_loading.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            //Utils.hideLoading();
            Utils.showToast(this,getResources().getString(R.string.something_wrong));
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
        }
    }


    private void newBrandRequest(String category, String brandName) {


        try {

            JSONObject object = new JSONObject();

            object.put("clientId",clientId);
            object.put("uid",uID);
            object.put("category",category);
            object.put("brandName",brandName);

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {

                POST post = new POST(this, "https://discoverysvc.pixibo.com/brand" , object,NewBrand, this);
                post.execute();

            } else {
                layout_error.setVisibility(View.VISIBLE);
                layout_loading.setVisibility(View.GONE);
                Utils.showToast(this,getResources().getString(R.string.no_internet));
            }

        } catch (Exception e) {
            //Utils.hideLoading();
            Utils.showToast(this,getResources().getString(R.string.something_wrong));
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
        }
    }



    private void getFinalSize(String uID, String ht, String wt , String age , String ftp , String rg , String bs , String cu , String brand , String range , String sizeType , String size , String bu) {

        layout_loading.setVisibility(View.VISIBLE);
        loader_text.setVisibility(View.VISIBLE);

        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {

                if (bu.equals(""))
                {
                    GET get = new GET(this, "https://sizeguidev2.pixibo.com/asset/"+clientId+"/"+skuId+"?uid="+uID+"&ht="+ht+"&wt="+wt+"&age="+age+"&ftp="+ftp+"&rg="+rg+"&bs="+bs+"&cu="+cu+"&brand="+brand+"&range="+range+"&sizeType="+sizeType+"&size="+size , GetSize, this);
                    get.execute();
                }
                else
                {
                    GET get = new GET(this, "https://sizeguidev2.pixibo.com/asset/"+clientId+"/"+skuId+"?uid="+uID+"&ht="+ht+"&wt="+wt+"&age="+age+"&ftp="+ftp+"&rg="+rg+"&bs="+bs+"&cu="+cu+"&brand="+brand+"&range="+range+"&sizeType="+sizeType+"&size="+size+"&bu="+bu , GetSize, this);
                    get.execute();
                }


            } else {
                layout_error.setVisibility(View.VISIBLE);
                layout_loading.setVisibility(View.GONE);
                Utils.showToast(this,getResources().getString(R.string.no_internet));
            }

        } catch (Exception e) {
            //Utils.hideLoading();
            Utils.showToast(this,getResources().getString(R.string.something_wrong));
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
        }
    }




    private void updateUserDetails() {

        try {

            JSONObject object = new JSONObject();
            JSONObject femaleObject = new JSONObject();
            JSONObject maleObject = new JSONObject();
            JSONObject whois = new JSONObject();
            JSONObject referenceBrands = new JSONObject();
            JSONArray referenceBrandsArray = new JSONArray();

            object.put("uid",uID);
            object.put("gender",gender);
            object.put("altId",altId);
            object.put("type",category.toLowerCase());


            if (gender.equals("male"))
            {

                maleObject.put("height",Integer.parseInt(localData.getHeight()));
                maleObject.put("weight",Integer.parseInt(localData.getWeight()));
                maleObject.put("age",Integer.parseInt(localData.getAge()));

                maleObject.put("heightPreference","cm");
                maleObject.put("weightPreference","kg");

                if (!localData.getFitPreference().equals(""))
                {
                    maleObject.put("fitPreference",localData.getFitPreference());
                }
                else
                {
                    maleObject.put("fitPreference","2");
                }

                referenceBrands.put("category",category);
                referenceBrands.put("brand",localData.getBrand());
                referenceBrands.put("range",localData.getBrandRange());
                referenceBrands.put("sizeType",localData.getsizeType().toUpperCase());
                referenceBrands.put("size",localData.getBrandSize().toUpperCase());
                referenceBrandsArray.put(0,referenceBrands);

                maleObject.put("referenceBrands",referenceBrandsArray);

                whois.put("male",maleObject);
            }

            else if (gender.equals("female"))
            {

                femaleObject.put("height",Integer.parseInt(localData.getHeight()));
                femaleObject.put("weight",Integer.parseInt(localData.getWeight()));
                femaleObject.put("age",Integer.parseInt(localData.getAge()));

                femaleObject.put("heightPreference","cm");
                femaleObject.put("weightPreference","kg");

                femaleObject.put("region",localData.getRegion().toUpperCase());

                if(!localData.getBand().equals(""))
                {
                    femaleObject.put("band",Integer.parseInt(localData.getBand()));
                }

                femaleObject.put("cup",localData.getCup().toUpperCase());

                if (!localData.getFitPreference().equals(""))
                {
                    femaleObject.put("fitPreference",localData.getFitPreference());
                }
                else
                {
                    femaleObject.put("fitPreference","2");
                }

                if (!localData.getBust().equals(""))
                {
                    femaleObject.put("bust",Integer.parseInt(localData.getBust()));
                }

                referenceBrands.put("category",category);
                referenceBrands.put("brand",localData.getBrand());
                referenceBrands.put("range",localData.getBrandRange());
                referenceBrands.put("sizeType",localData.getsizeType().toUpperCase());
                referenceBrands.put("size",localData.getBrandSize().toUpperCase());

                referenceBrandsArray.put(0,referenceBrands);

                femaleObject.put("referenceBrands",referenceBrandsArray);

                whois.put("female",femaleObject);
            }

            object.put("whois",whois);


            Log.e("Data", String.valueOf(object));


            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {

                POST post = new POST(this, "https://discoverysvc.pixibo.com/uid" ,object, UpdateData, this);
                post.execute();

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
            case BrandSuggestion:
                try
                {

                    if (statusCode == 200)
                    {
                        String[] strArray = result.split(",") ;

                        for (String s : strArray) {

                            BrandModel brandModel = new BrandModel();
                            brandModel.setName(s.replaceAll("[\"^\\[\\]]",""));

                            brandModelArrayList.add(brandModel);
                        }

                        brandAdapter.notifyDataSetChanged();
                    }
                    else if (statusCode == 500)

                    {
                        layout_loading.setVisibility(View.GONE);
                        layout_error.setVisibility(View.VISIBLE);
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;


            case BrandSizes:

                try
                {

                    if (statusCode == 200)
                    {

                        if (!hasSize)
                        {
                            layout_loading.setVisibility(View.GONE);
                            layout_brand_fit.setVisibility(View.VISIBLE);
                        }

                        brandCharts = result ;
                        JSONObject object = new JSONObject(result);


                        if (object.has("regular"))
                        {
                            layout_range_1.setVisibility(View.VISIBLE);
                        }

                        if (object.has("petite"))
                        {
                            layout_range_2.setVisibility(View.VISIBLE);
                        }

                        if (object.has("plus"))
                        {
                            layout_range_3.setVisibility(View.VISIBLE);
                        }

                        if (isReset)
                        {

                            Log.e("BrandSizes","isReset");


                            if (object.has("regular"))
                            {

                                layout_range_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                                tv_range_1.setTextColor(getResources().getColor(R.color.color_text));

                                if (!brandFromResult)
                                {
                                    range = "regular";
                                }

                                setBrandRangeRegular(result,0);
                            }

                            switch (range.toLowerCase())
                            {
                                case "regular":

                                    clearAllRange();

                                    layout_range_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                                    tv_range_1.setTextColor(getResources().getColor(R.color.color_text));
                                    layout_range_1.setVisibility(View.VISIBLE);

                                    break;

                                case "petite":

                                    clearAllRange();

                                    layout_range_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                                    tv_range_2.setTextColor(getResources().getColor(R.color.color_text));
                                    layout_range_2.setVisibility(View.VISIBLE);
                                    break;

                                case "plus":

                                    clearAllRange();

                                    layout_range_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                                    tv_range_3.setTextColor(getResources().getColor(R.color.color_text));
                                    layout_range_3.setVisibility(View.VISIBLE);
                                    break;
                            }
                            setBrandRangeBand(brandCharts,range,band,sizeType,brandSize);
//                            setBrandRangeBand(brandCharts,localData.getBrandRange().toLowerCase(),localData.getBand().toLowerCase(),localData.getsizeType().toLowerCase(),brandSize);
                        }
                        else if (isEditFlow)
                        {

                            Log.e("BrandSizes","isEditFlow");
                            Log.e("BrandSizes range",range);

                            switch (range.toLowerCase())
                            {
                                case "regular":

                                    clearAllRange();

                                    layout_range_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                                    tv_range_1.setTextColor(getResources().getColor(R.color.color_text));
                                    layout_range_1.setVisibility(View.VISIBLE);

                                    break;

                                case "petite":

                                    clearAllRange();

                                    layout_range_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                                    tv_range_2.setTextColor(getResources().getColor(R.color.color_text));
                                    layout_range_2.setVisibility(View.VISIBLE);
                                    break;

                                case "plus":

                                    clearAllRange();

                                    layout_range_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                                    tv_range_3.setTextColor(getResources().getColor(R.color.color_text));
                                    layout_range_3.setVisibility(View.VISIBLE);
                                    break;
                            }

                            setBrandRangeBand(brandCharts,range,band,sizeType,brandSize);

                        }


                    }
                    else if (statusCode == 500)

                    {
                        layout_loading.setVisibility(View.GONE);
                        layout_error.setVisibility(View.VISIBLE);
                    }



                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;

            case ValidateUserUid:

                try
                {
                    if (statusCode == 200)
                    {

                      //  JSONObject userObject = new JSONObject(result);

                            JSONObject userInfoObject = new JSONObject(result);

                            if (!userInfoObject.optString("uid").equals(null))
                            {
                               uID = userInfoObject.optString("uid");
                            }

                            if (userInfoObject.has("whois"))
                            {

                            JSONObject whoIs = new JSONObject(userInfoObject.optString("whois"));

                            if (userInfoObject.optString("whois").contains("female") && gender.equals("female") &&!new JSONObject(userInfoObject.optString("whois")).optString("female").equals(null) && !new JSONObject(userInfoObject.optString("whois")).optString("female").equals("{}"))
                            {
                                JSONObject whoisObject = new JSONObject(userInfoObject.optString("whois"));
                                JSONObject femaleObject = new JSONObject(whoisObject.optString("female"));

                                ht = femaleObject.optString("height");
                                wt = femaleObject.optString("weight");
                                age = femaleObject.optString("age");

                                ftp = femaleObject.optString("fitPreference");
                                rg = femaleObject.optString("region");
                                bs = femaleObject.optString("band");
                                cu = femaleObject.optString("cup");
                                bu = femaleObject.optString("bust");

                                overbustUnit = "cm";


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
                                        brandSize = femaleReferenceBrandsObject.optString("size");

                                        setScreen();
                                    }
                                    else
                                    {
                                        setScreen();
                                    }

                                }
                                else
                                {
                                    setScreen();
                                }

                            }

                            else if (whoIs.has("male") && !new JSONObject(userInfoObject.optString("whois")).optString("male").equals(null) && !new JSONObject(userInfoObject.optString("whois")).optString("male").equals("{}") && gender.equals("male"))
                            {
                                JSONObject whoisObject = new JSONObject(userInfoObject.optString("whois"));

                                JSONObject maleObject = new JSONObject(whoisObject.optString("male"));

                                ht = maleObject.optString("height");
                                wt = maleObject.optString("weight");
                                age = maleObject.optString("age");

                                ftp = maleObject.optString("fitPreference");
                                rg = maleObject.optString("region");
                                bs = maleObject.optString("band");
                                cu = maleObject.optString("cup");

                                brand = "";
                                range = "";
                                sizeType = "";
                                brandSize = "";


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
                                        brandSize = maleReferenceBrandsObject.optString("size");

                                        setScreen();
                                    }
                                    else
                                    {
                                        setScreen();
                                    }

                                }

                                else
                                {
                                    setScreen();
                                }

                            }
                            else
                            {
                                layout_body_profile.setVisibility(View.VISIBLE);
                            }
                        }
                        else
                        {
                            layout_body_profile.setVisibility(View.VISIBLE);
                        }



                    }

                    else if (statusCode == 500)

                    {
                        layout_loading.setVisibility(View.GONE);
                        layout_error.setVisibility(View.VISIBLE);
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;

            case BraSize:
                try
                {

                    if (statusCode == 200)
                    {
                        layout_loading.setVisibility(View.GONE);
                        layout_brand.setVisibility(View.VISIBLE);


                        updateUserDetails();

                        JSONObject object = new JSONObject(result);

                        if (object.has("fys"))
                        {
                            JSONArray fys = new JSONArray(object.getString("fys"));

                            if (fys.length()>0)
                            {
                                JSONObject fysObject = new JSONObject(String.valueOf(fys.get(0)));

                                if (fysObject.getBoolean("recommended"))
                                {

                                    isRecommended = true ;
                                    size = fysObject.getString("size");

                                    if (fysObject.getString("size").contains("INT"))
                                    {
                                        tv_header_text.setText(getResources().getString(R.string.header_text)+" "+
                                                fysObject.getString("size").replace("INT",getResources().getString(R.string.international))+" "+getResources().getString(R.string.header_text_2));
                                    }

                                    else
                                    {
                                        tv_header_text.setText(getResources().getString(R.string.header_text)+" "+
                                                fysObject.getString("size")+ " "+getResources().getString(R.string.header_text_2));
                                    }

                                    tv_header_text.setVisibility(View.VISIBLE);

                                    layout_header.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                                }

                                else
                                {

                                    isRecommended = false  ;

                                    size = fysObject.getString("size");

                                    tv_header_text.setVisibility(View.GONE);

                                    layout_header.setBackground(null);
                                }

                                Log.e("fysObject", String.valueOf(fysObject));
                            }
                        }

                    }
                    else if (statusCode == 500)
                    {
                        layout_loading.setVisibility(View.GONE);
                        layout_error.setVisibility(View.VISIBLE);
                    }



                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;

            case BodyProfile:

                try
                {

                    if (statusCode == 200)
                    {
                        updateUserDetails();

                        JSONObject object = new JSONObject(result);

                        if (object.has("fys"))
                        {
                            JSONArray fys = new JSONArray(object.getString("fys"));

                            if (fys.length()>0)
                            {
                                JSONObject fysObject = new JSONObject(String.valueOf(fys.get(0)));

                                if (fysObject.getBoolean("recommended"))
                                {

                                    isRecommended = true ;
                                    size = fysObject.getString("size");

                                    if (fysObject.getString("size").contains("INT"))
                                    {
                                        tv_header_text.setText(getResources().getString(R.string.header_text)+" "+
                                                fysObject.getString("size").replace("INT",getResources().getString(R.string.international))+" "+getResources().getString(R.string.header_text_2));
                                    }

                                    else
                                    {
                                        tv_header_text.setText(getResources().getString(R.string.header_text)+" "+
                                                fysObject.getString("size")+ " "+getResources().getString(R.string.header_text_2));
                                    }



                                    tv_header_text.setVisibility(View.VISIBLE);

                                    layout_header.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
                                }

                                else
                                {

                                    isRecommended = false  ;

                                    size = fysObject.getString("size");

                                    tv_header_text.setVisibility(View.GONE);

                                    layout_header.setBackground(null);
                                }

                                //  Log.e("fysObject", String.valueOf(fysObject));
                            }
                        }
                    }

                    else if (statusCode == 500)

                    {
                        layout_loading.setVisibility(View.GONE);
                        layout_error.setVisibility(View.VISIBLE);
                    }


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;

            case GetSize:
                try
                {

                    if (statusCode == 200)
                    {

                        layout_body_profile.setVisibility(View.GONE);
                        layout_bra_profile.setVisibility(View.GONE);
                        layout_bust.setVisibility(View.GONE);
                        layout_brand.setVisibility(View.GONE);
                        layout_brand_fit.setVisibility(View.GONE);
                        layout_fit_preference.setVisibility(View.GONE);

                        bust8Bit = "";
                        waist8Bit = "";
                        hip8Bit = "";

                        tv_header_text.setVisibility(View.GONE);

                        layout_header.setBackgroundColor(getResources().getColor(R.color.color_background));

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                layout_loading.setVisibility(View.GONE);

                                layout_result.setVisibility(View.VISIBLE);


                                updateUserDetails();

                                progress(6);

                            }
                        }, 1200);


                        JSONObject jsonObject = new JSONObject(result);

                        if (jsonObject.has("fys"))
                        {
                            JSONArray fys = new JSONArray(jsonObject.getString("fys"));

                            if (fys.length()>0)
                            {
                                JSONObject fysObject = new JSONObject(String.valueOf(fys.get(0)));

                                clearAll();

                                bust8Bit =  fysObject.getString("bust8Bit");
                                waist8Bit =   fysObject.getString("waist8Bit");
                                hip8Bit =  fysObject.getString("hip8Bit");

                                if (gender.equals("male"))
                                {
                                    tv_fit_bust.setText(getResources().getString(R.string.apparel_result_text_check_fit_chest));
                                }

                                if (bust8Bit.equals("0"))
                                {
                                    layout_fit_bust.setVisibility(View.INVISIBLE);
                                }

                                if (waist8Bit.equals("0"))
                                {
                                    layout_fit_waist.setVisibility(View.INVISIBLE);
                                }

                                if (hip8Bit.equals("0"))
                                {
                                    layout_fit_hips.setVisibility(View.INVISIBLE);
                                }

                                setFit(bust8Bit);

                                Log.e("availableSizeList", String.valueOf(availableSizeList));

                                if (availableSizeList.toString().toLowerCase().contains(fysObject.getString("size").toLowerCase()))
                                {
                                    tb_out_of_stock.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    tb_out_of_stock.setVisibility(View.GONE);
                                }

                                if (fysObject.getBoolean("recommended"))
                                {
                                    isRecommended = true ;
                                    size = fysObject.getString("size");

                                    tv_not_fit.setVisibility(View.INVISIBLE);
                                    tv_suits_best.setText(getResources().getText(R.string.apparel_result_text_best));
                                    tv_size.setText(fysObject.getString("size").replace("INT",getResources().getString(R.string.international)));
                                }

                                else
                                {
                                    isRecommended = false ;
                                    size = fysObject.getString("size");

                                    tv_suits_best.setText(getResources().getText(R.string.apparel_result_closest_match));
                                    tv_size.setText(fysObject.getString("size").replace("INT",getResources().getString(R.string.international)));
                                    tv_not_fit.setVisibility(View.VISIBLE);
                                }

                            }
                        }
                    }

                    else if (statusCode == 500)
                    {
                        layout_loading.setVisibility(View.GONE);
                        layout_error.setVisibility(View.VISIBLE);
                    }


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }


    public void setScreen()
    {

        Log.e("ht",ht );
        Log.e("wt",wt );
        Log.e("age",age);
        Log.e("ftp",ftp);
        Log.e("rg",rg );
        Log.e("bs",bs );
        Log.e("cu",cu );
        Log.e("bu",bu );
        Log.e("gender",gender );
        Log.e("category",category );

        Log.e("brand",brand );
        Log.e("range",range );
        Log.e("sizeType",sizeType );
        Log.e("brandSize", brandSize);


        fitPreference = ftp ;
        size = brandSize;
        height_cm = ht ;
        weight = wt;
        bust = bu;
        cup = cu;
        braSizeType = rg;
        band = bs;

        localData.setHeight(ht);
        localData.setWeight(wt);
        localData.setAge(age);
        localData.setFitPreference(ftp);
        localData.setRegion(rg);
        localData.setBand(bs);
        localData.setCup(cu);
        localData.setBust(bu);
        localData.setBrand(brand);
        localData.setBrandRange(range);
        localData.setsizeType(sizeType);
        localData.setBrandSize(brandSize);





        switch (fitPreference)
        {
            case "1":
                tv_fit_size.setText(getResources().getString(R.string.apparel_last_fit_tight));
                break;
            case "2":
                tv_fit_size.setText(getResources().getString(R.string.apparel_last_fit_regular));
                break;
            case "3":
                tv_fit_size.setText(getResources().getString(R.string.apparel_last_fit_loose));
                break;
        }




        if (!brand.equals(""))
        {
            et_what_brand.setText(brand);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    isBrandSelected = true;
                    tv_brand_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                }
            }, 500);

        }


        if (!isNew)
        {

            if (!ftp.equals(""))
            {
                hasSize = true;

                if (altId.equals(""))
                {
                    getFinalSize(uID,ht,wt,age,ftp,rg,bs,cu,
                            brand,range,sizeType,brandSize,bu);
                }
                else
                {
                    getFinalSize(altId,ht,wt,age,ftp,rg,bs,cu,
                            brand,range,sizeType,brandSize,bu);
                }
            }
            else if (!range.equals(""))
            {
                layout_fit_preference.setVisibility(View.VISIBLE);

            }
            else if (!brand.equals(""))
            {


                get_brand_sizes(gender,category,brand);

                if (preferredLanguage.equals("hk") || preferredLanguage.equals("tw"))
                {
                    tv_size_fav_category.setText(getResources().getString(R.string.apparel_brand_fav_size)+" "+brand+" "+
                            Utils.convertApparelType(category,gender, ApparelFlow.this)+
                            getResources().getString(R.string.apparel_brand_fav_size_3));
                }
                else
                {
                    tv_size_fav_category.setText(getResources().getString(R.string.apparel_brand_fav_size)+" "+
                            Utils.convertApparelType(category,gender, ApparelFlow.this)+" "+getResources().getString(R.string.apparel_brand_fav_size_2)+" "+
                            brand+getResources().getString(R.string.apparel_brand_fav_size_3));
                }


                if (!bu.equals(""))
                {
                    layout_brand_fit.setVisibility(View.VISIBLE);
                }
                else if (!rg.equals(""))
                {
                    layout_brand_fit.setVisibility(View.VISIBLE);
                }
            }
            else if (!bu.equals("") && gender.equals("female"))
            {
                layout_brand.setVisibility(View.VISIBLE);

                setDefaultBrands();

                layout_brands.setVisibility(View.VISIBLE);

                tv_brand_error.setVisibility(View.GONE);
                layout_brand_search.setVisibility(View.GONE);

            }
            else if (!rg.equals("")&& gender.equals("female"))
            {
                layout_brand.setVisibility(View.VISIBLE);

                tv_brand_error.setVisibility(View.GONE);
                layout_brand_search.setVisibility(View.GONE);

                setDefaultBrands();

                layout_brands.setVisibility(View.VISIBLE);

            }
            else if (!ht.equals("") && !ht.equals("0"))
            {
                if (gender.equals("male"))
                {
                    layout_brand.setVisibility(View.VISIBLE);

                    tv_brand_error.setVisibility(View.GONE);
                    layout_brand_search.setVisibility(View.GONE);

                    setDefaultBrands();

                    layout_brands.setVisibility(View.VISIBLE);

                }
                else
                {
                    if (bust.equals(""))
                    {
                        layout_bra_profile.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        isBust = true;
                        layout_bust.setVisibility(View.VISIBLE);
                    }

                }
            }
            else
            {
                layout_body_profile.setVisibility(View.VISIBLE);
            }
        }

        else
        {
            layout_body_profile.setVisibility(View.VISIBLE);
        }

        if (!brandSize.equals("") && !brand.equals(""))
        {

            clearRangeButton();

            brandFromResult = true ;
            get_brand_sizes(gender,category,brand);

            isBrandCategorySelected = true;
        }


    }

    public void setDefaultBrands()
    {

        if (gender.equals("female"))
        {
            tv_brand_1.setText(getResources().getString(R.string.female_apparel_brand_1));
            tv_brand_2.setText(getResources().getString(R.string.female_apparel_brand_2));
            tv_brand_3.setText(getResources().getString(R.string.female_apparel_brand_3));
            tv_brand_4.setText(getResources().getString(R.string.female_apparel_brand_4));
            tv_brand_5.setVisibility(View.GONE);
        }

        else if (gender.equals("male"))
        {
            tv_brand_1.setText(getResources().getString(R.string.male_apparel_brand_1));
            tv_brand_2.setText(getResources().getString(R.string.male_apparel_brand_2));
            tv_brand_3.setText(getResources().getString(R.string.male_apparel_brand_3));
            tv_brand_4.setText(getResources().getString(R.string.male_apparel_brand_4));
            tv_brand_5.setVisibility(View.GONE);
        }
    }


    public void clearAll()
    {
        layout_pref_1.setBackgroundColor(getResources().getColor(R.color.grey_2));
        layout_pref_2.setBackgroundColor(getResources().getColor(R.color.grey_2));
        layout_pref_3.setBackgroundColor(getResources().getColor(R.color.grey_2));
        layout_pref_4.setBackgroundColor(getResources().getColor(R.color.grey_2));
        layout_pref_5.setBackgroundColor(getResources().getColor(R.color.grey_2));
        layout_pref_6.setBackgroundColor(getResources().getColor(R.color.grey_2));
        layout_pref_7.setBackgroundColor(getResources().getColor(R.color.grey_2));
        layout_pref_8.setBackgroundColor(getResources().getColor(R.color.grey_2));

    }

    public void checkBrandSelection (String brand)
    {
        clearAllBrands();

        Log.e("Brand Selection",brand);

        if (brand.equals(tv_brand_1.getText().toString()))
        {
            tv_brand_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
            tv_brand_1.setTextColor(getResources().getColor(R.color.color_text));
        }
        else if (brand.equals(tv_brand_2.getText().toString()))
        {
            tv_brand_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
            tv_brand_2.setTextColor(getResources().getColor(R.color.color_text));
        }
        else if (brand.equals(tv_brand_3.getText().toString()))
        {
            tv_brand_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
            tv_brand_3.setTextColor(getResources().getColor(R.color.color_text));
        }
        else if (brand.equals(tv_brand_4.getText().toString()))
        {
            tv_brand_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
            tv_brand_4.setTextColor(getResources().getColor(R.color.color_text));
        }
        else if (brand.equals(tv_brand_5.getText().toString()))
        {
            tv_brand_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
            tv_brand_5.setTextColor(getResources().getColor(R.color.color_text));
        }
    }

    public void setFit(String value)
    {
        switch (value)
        {
            case "1":
                layout_pref_1.setBackgroundColor(getResources().getColor(R.color.color_background_edit_enable));
                break;

            case "2":
                layout_pref_2.setBackgroundColor(getResources().getColor(R.color.color_background_edit_enable));
                break;

            case "3":
                layout_pref_3.setBackgroundColor(getResources().getColor(R.color.color_background_edit_enable));
                break;

            case "4":
                layout_pref_4.setBackgroundColor(getResources().getColor(R.color.color_background_edit_enable));
                break;

            case "5":
                layout_pref_5.setBackgroundColor(getResources().getColor(R.color.color_background_edit_enable));
                break;

            case "6":
                layout_pref_6.setBackgroundColor(getResources().getColor(R.color.color_background_edit_enable));
                break;

            case "7":
                layout_pref_7.setBackgroundColor(getResources().getColor(R.color.color_background_edit_enable));
                break;

            case "8":
                layout_pref_8.setBackgroundColor(getResources().getColor(R.color.color_background_edit_enable));
                break;

        }
    }


    public void resetData()
    {

        clearAllCup();
        clearAll();
        clearAllBand();
        clearAllBrands();
        clearAllBrandSize();
        clearAllRange();
        clearSelectedBand();
        clearRangeButton();

        et_age.setText("");
        et_weight.setText("");
        et_height.setText("");
        et_what_brand.setText("");

        height_cm = "0";
        height_ft = "0";
        height_in = "0";
        weight = "0";
        bust = "0";
        brand = "";


        braSizeType = "";
        sizeType = "";
        band = "";
        cup = "";
        brandSize = "";
        fitPreference = "2";
        range = "";
        brandCharts = "";

        isCM = true;
        isBustCM = true;
        isKG = true;
        isReset = true;
        isBrandSelected = false;
        isBrandCategorySelected = false;
        isAgeFilled = false;

        setCM();
        setKG();

        localData.setHeight("");
        localData.setWeight("");
        localData.setAge("");
        localData.setFitPreference("");
        localData.setRegion("");
        localData.setBand("");
        localData.setCup("");
        localData.setBust("");
        localData.setBrand("");
        localData.setBrandRange("");
        localData.setsizeType("");
        localData.setBrandSize("");
    }


    public void progress(int position)
    {
        switch (position)
        {
            case 1:
                layout_progress_1.setVisibility(View.VISIBLE);
                layout_progress_2.setVisibility(View.INVISIBLE);
                layout_progress_3.setVisibility(View.INVISIBLE);
                layout_progress_4.setVisibility(View.INVISIBLE);
                layout_progress_5.setVisibility(View.INVISIBLE);
                layout_progress_6.setVisibility(View.INVISIBLE);
                break;

            case 2:
                layout_progress_1.setVisibility(View.VISIBLE);
                layout_progress_2.setVisibility(View.VISIBLE);
                layout_progress_3.setVisibility(View.INVISIBLE);
                layout_progress_4.setVisibility(View.INVISIBLE);
                layout_progress_5.setVisibility(View.INVISIBLE);
                layout_progress_6.setVisibility(View.INVISIBLE);
                break;

            case 3:
                layout_progress_1.setVisibility(View.VISIBLE);
                layout_progress_2.setVisibility(View.VISIBLE);
                layout_progress_3.setVisibility(View.VISIBLE);
                layout_progress_4.setVisibility(View.INVISIBLE);
                layout_progress_5.setVisibility(View.INVISIBLE);
                layout_progress_6.setVisibility(View.INVISIBLE);
                break;

            case 4:
                layout_progress_1.setVisibility(View.VISIBLE);
                layout_progress_2.setVisibility(View.VISIBLE);
                layout_progress_3.setVisibility(View.VISIBLE);
                layout_progress_4.setVisibility(View.VISIBLE);
                layout_progress_5.setVisibility(View.INVISIBLE);
                layout_progress_6.setVisibility(View.INVISIBLE);
                break;

            case 5:
                layout_progress_1.setVisibility(View.VISIBLE);
                layout_progress_2.setVisibility(View.VISIBLE);
                layout_progress_3.setVisibility(View.VISIBLE);
                layout_progress_4.setVisibility(View.VISIBLE);
                layout_progress_5.setVisibility(View.VISIBLE);
                layout_progress_6.setVisibility(View.INVISIBLE);
                break;

            case 6:
                layout_progress_1.setVisibility(View.VISIBLE);
                layout_progress_2.setVisibility(View.VISIBLE);
                layout_progress_3.setVisibility(View.VISIBLE);
                layout_progress_4.setVisibility(View.VISIBLE);
                layout_progress_5.setVisibility(View.VISIBLE);
                layout_progress_6.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setCM()
    {
        et_height_in.setVisibility(View.GONE);
        tv_quotes.setVisibility(View.GONE);
        tv_dquotes.setVisibility(View.GONE);

        tv_height_cm.setTypeface(bold_text);
        tv_height_cm.setTextColor(getResources().getColor(R.color.black));

        tv_height_ft.setTypeface(normal_text);
        tv_height_ft.setTextColor(getResources().getColor(R.color.grey));
    }

    public void setFT()
    {
        et_height_in.setVisibility(View.VISIBLE);
        tv_quotes.setVisibility(View.VISIBLE);
        tv_dquotes.setVisibility(View.VISIBLE);

        tv_height_cm.setTypeface(normal_text);
        tv_height_cm.setTextColor(getResources().getColor(R.color.grey));

        tv_height_ft.setTypeface(bold_text);
        tv_height_ft.setTextColor(getResources().getColor(R.color.black));

        et_height.setText(height_ft);
        et_height_in.setText(height_in);
    }

    public void setKG()
    {
        tv_weight_kg.setTypeface(bold_text);
        tv_weight_kg.setTextColor(getResources().getColor(R.color.black));

        tv_weight_lb.setTypeface(normal_text);
        tv_weight_lb.setTextColor(getResources().getColor(R.color.grey));
    }

    public void setLB()
    {
        tv_weight_kg.setTypeface(normal_text);
        tv_weight_kg.setTextColor(getResources().getColor(R.color.grey));

        tv_weight_lb.setTypeface(bold_text);
        tv_weight_lb.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    public void onBackPressed() {


        Intent returnIntent = new Intent();
        returnIntent.putExtra("recommended",isRecommended);
        returnIntent.putExtra("result",size);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

        super.onBackPressed();
    }
}
