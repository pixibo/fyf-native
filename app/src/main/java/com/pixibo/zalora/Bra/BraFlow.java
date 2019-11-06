package com.pixibo.zalora.Bra;

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
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pixibo.zalora.Adapter.BraSizeTypeAdapter;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.pixibo.zalora.Utils.Utils.TYPE.BrandSizes;
import static com.pixibo.zalora.Utils.Utils.TYPE.BrandSuggestion;
import static com.pixibo.zalora.Utils.Utils.TYPE.GetSize;
import static com.pixibo.zalora.Utils.Utils.TYPE.NewBrand;
import static com.pixibo.zalora.Utils.Utils.TYPE.UpdateData;
import static com.pixibo.zalora.Utils.Utils.TYPE.ValidateUserUid;

public class BraFlow extends AppCompatActivity implements Result, View.OnClickListener {


    private String preferredLanguage = "";
    private String clientId = "";
    private String skuId = "";
    private String altId = "";
    private String uID = "";

    private String gender = "";
    private String category = "";
    private String brand = "";

    //Bra Size
    private String region = "";
    private String region_noBrand = "";
    private String cup = "";
    private String cup_noBrand = "";
    private String band = "" ;
    private String band_noBrand = "";
    private String isWired = "";
    private String isWired_noBrand = "";
    private boolean wired = false ;
    private boolean wired_noBrand =false ;

    private String bandFit = "0";
    private String cupFit = "0";
    private String strapsFit = "0";

    private String braAge = "0";
    private String braStyle = "0";
    private String gaps = "0";
    private boolean sameBra = false ;
    private boolean tightHook = false;
    private boolean looseHook = false;
    private boolean tightStrap = false;

    private String sideWireFit = "0";
    private String frontWireFit = "0";

    private String braSizeChart = "";
    private String size = "";

    private boolean isRecommended = false ;
    private boolean brandNotListed = false ;

    private boolean isBrandSelected = false;
    private boolean isEditFlow = false;
    private boolean tellMore = false;
    private boolean moreAccurate = false;
    private boolean startOver = false;


    Typeface bold_text ;
    Typeface normal_text ;

    private TextView tv_brand_not_listed,tv_brand_error,tv_error_band,tv_error_cup,tv_error_wire,tv_error_band_noBrand,tv_error_cup_noBrand,tv_error_wire_noBrand;

    private ArrayList<BrandModel> brandModelArrayList = new ArrayList<>();
    private ArrayList<BrandModel> braSizeTypeArrayList = new ArrayList<>();
    private BrandAdapter brandAdapter;
    private BraSizeTypeAdapter braSizeTypeAdapter;
    private RecyclerView recycler_brand_suggestion,recycler_bra_sizeType;
    private EditText et_what_brand;

    private RelativeLayout layout_brand_search,layout_add,layout_brands,layout_loading;
    private RelativeLayout layout_brand,layout_bra_profile,layout_bra_profile_noBrand,layout_result,layout_band_feel,layout_bra_old,layout_cup_fit,layout_gaps,layout_strap_fit,layout_underwire_front,layout_underwire_side,layout_bra_style;
    private TextView tv_brand_continue,tv_bra_continue,tv_bra_continue_noBrand;
    private TextView tv_back_bra,tv_back_bra_noBrand,tv_back_band_feel,tv_back_cup_fit,tv_back_strap_fit,tv_back_gap,tv_back_bra_style,tv_back_old_bra,tv_back_layout_underwire_front,tv_back_layout_underwire_side;
    private TextView tv_brand_1,tv_brand_2,tv_brand_3,tv_brand_4,tv_brand_5;

    private TextView tv_band_1,tv_band_2,tv_band_3,tv_band_4,tv_band_5,tv_band_6,tv_band_7,tv_band_8,tv_band_9,tv_band_10;
    private RelativeLayout layout_band_1,layout_band_2,layout_band_3,layout_band_4,layout_band_5,layout_band_6,layout_band_7,layout_band_8,layout_band_9,layout_band_10;

    private TextView tv_band_1_noBrand,tv_band_2_noBrand,tv_band_3_noBrand,tv_band_4_noBrand,tv_band_5_noBrand,tv_band_6_noBrand,tv_band_7_noBrand,tv_band_8_noBrand,tv_band_9_noBrand,tv_band_10_noBrand;
    private RelativeLayout layout_band_1_noBrand,layout_band_2_noBrand,layout_band_3_noBrand,layout_band_4_noBrand,layout_band_5_noBrand,layout_band_6_noBrand,layout_band_7_noBrand,layout_band_8_noBrand,layout_band_9_noBrand,layout_band_10_noBrand;

    private TextView tv_cup_1,tv_cup_2,tv_cup_3,tv_cup_4,tv_cup_5,tv_cup_6,tv_cup_7,tv_cup_8,tv_cup_9,tv_cup_10,tv_cup_11,tv_cup_12;
    private RelativeLayout layout_cup_1,layout_cup_2,layout_cup_3,layout_cup_4,layout_cup_5,layout_cup_6,layout_cup_7,layout_cup_8,layout_cup_9,layout_cup_10,layout_cup_11,layout_cup_12;

    private TextView tv_cup_1_noBrand,tv_cup_2_noBrand,tv_cup_3_noBrand,tv_cup_4_noBrand,tv_cup_5_noBrand,tv_cup_6_noBrand,tv_cup_7_noBrand,tv_cup_8_noBrand,tv_cup_9_noBrand,tv_cup_10_noBrand,tv_cup_11_noBrand,tv_cup_12_noBrand;
    private RelativeLayout layout_cup_1_noBrand,layout_cup_2_noBrand,layout_cup_3_noBrand,layout_cup_4_noBrand,layout_cup_5_noBrand,layout_cup_6_noBrand,layout_cup_7_noBrand,layout_cup_8_noBrand,layout_cup_9_noBrand,layout_cup_10_noBrand,layout_cup_11_noBrand,layout_cup_12_noBrand;

    private TextView tv_type_wired,tv_type_unwired;
    private RelativeLayout layout_type_wired,layout_type_unwired;


    private TextView tv_type_wired_noBrand,tv_type_unwired_noBrand;
    private RelativeLayout layout_type_wired_noBrand,layout_type_unwired_noBrand;

    private RelativeLayout layout_fit_how,layout_recommended;
    private TextView tv_how_fit,tv_taken_account;

    private RelativeLayout layout_cup_back,layout_cup_next,layout_cup_next_2,layout_cup_back_2;
    private RelativeLayout layout_band_next_1,layout_band_back_1;

    private RelativeLayout layout_cup_back_noBrand,layout_cup_next_noBrand,layout_cup_next_2_noBrand,layout_cup_back_2_noBrand;
    private RelativeLayout layout_band_next_1_noBrand,layout_band_back_1_noBrand;

    private TextView tv_type_size,tb_out_of_stock,tv_not_recommended,tv_add_cart,tv_size_result,tv_type_size_noBrand,loader_text;

    private LinearLayout layout_size_selection,layout_start_over,layout_cup_row1,layout_cup_row2,layout_cup_row3,layout_band_row1,layout_band_row2;

    private LinearLayout layout_sizeType_selection,layout_cup_row1_noBrand,layout_cup_row2_noBrand,layout_cup_row3_noBrand,layout_band_row1_noBrand,layout_band_row2_noBrand;

    private RelativeLayout layout_close,layout_privacy,layout_popup;

    private TextView tv_privacy_policy,tv_description;
    private TextView tv_au,tv_eu,tv_fr,tv_uk,tv_us;

    private RelativeLayout layout_digging_in,layout_straight,layout_riding_up;
    private TextView tv_digging_in,tv_straight,tv_riding_up;
    private TextView tv_digging_in_description,tv_straight_description,tv_riding_up_description;
    private TextView tv_bra_sizes_text, tv_isOld_no, tv_isOld_yes,tv_bra_style_text,tv_gap_no,tv_gap_yes, tv_bra_looser_hook_no, tv_bra_looser_hook_yes,tv_bra_tight_hook_no,tv_bra_tight_hook_yes,tv_old_bra_text, tv_old_bra_page_no, tv_old_bra_page_yes,tv_style_options_text;
    private ImageView ic_digging_in,ic_straight,ic_riding_up;
    private LinearLayout layout_bra_loose_hook_button, layout_bra_isOld_button,layout_bra_tight_hook_button,layout_gap_button,layout_old_bra_button;


    private RelativeLayout layout_just_right,layout_slipping,layout_gap;
    private TextView tv_just_right,tv_slipping,tv_gap;
    private TextView tv_just_right_description,tv_slipping_description,tv_gap_description;
    private ImageView ic_just_right,ic_slipping,ic_gap;

    private RelativeLayout layout_strap_digging_in,layout_strap_fit_fine,layout_strap_not_up;
    private TextView tv_strap_digging_in,tv_strap_fit_fine,tv_strap_not_up;
    private TextView tv_strap_digging_in_description,tv_strap_fit_fine_description,tv_strap_not_up_description;
    private ImageView ic_strap_digging_in,ic_strap_fit_fine,ic_strap_not_up;

    private RelativeLayout layout_full_coverage,layout_half_cup,layout_pushup,layout_strapless;
    private TextView tv_full_coverage,tv_half_cup,tv_pushup,tv_strapless;
    private TextView tv_full_coverage_description,tv_half_cup_description,tv_pushup_description,tv_strapless_description;
    private ImageView ic_full_coverage,ic_half_cup,ic_pushup,ic_strapless;

    private RelativeLayout layout_lift_away,layout_sit_comfortable,layout_digging_into_chest;
    private TextView tv_lift_away,tv_sit_comfortable,tv_digging_into_chest;
    private ImageView ic_lift_away,ic_sit_comfortable,ic_digging_into_chest;

    private RelativeLayout layout_digging_arms,layout_around_bust,layout_digging_chest;
    private TextView tv_digging_arms,tv_around_bust,tv_digging_chest;
    private ImageView ic_digging_arms,ic_around_bust,ic_digging_chest;

    private RelativeLayout layout_at_top,layout_all_over,layout_at_bottom;
    private TextView tv_at_top,tv_all_over,tv_at_bottom;
    private ImageView ic_at_top,ic_all_over,ic_at_bottom;

    private RelativeLayout layout_bought_recently,layout_6months,layout_12months,layout_1year;
    private TextView tv_bought_recently,tv_6months,tv_12months,tv_1year;

    private RelativeLayout layout_accurate_fit, layout_edit,layout_tell_more;

    private RelativeLayout layout_progress_1,layout_progress_2,layout_progress_3,layout_progress_4,layout_progress_5,layout_progress_6,layout_progress_7,layout_progress_8,layout_progress_9,layout_progress_10,layout_progress_11;

    private String [] availableSizeList ;

    private LocalData localData;

    float scale;
    int pixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bra_flow);

        localData = new LocalData(this);

        Intent intent = getIntent();

        category = intent.getStringExtra("dataType");
        gender = intent.getStringExtra("gender");
        brand = intent.getStringExtra("brand");

        clientId = intent.getStringExtra("clientId");
        skuId = intent.getStringExtra("skuId");
        altId = intent.getStringExtra("altId");
        uID = intent.getStringExtra("uID");
        preferredLanguage = intent.getStringExtra("preferredLanguage");
        availableSizeList = intent.getStringArrayExtra("availableSizeList");


        Log.e("altId",altId);
        Log.e("uID",uID);

        scale = getResources().getDisplayMetrics().density;

        bold_text = ResourcesCompat.getFont(BraFlow.this, R.font.apercu_bold);
        normal_text = ResourcesCompat.getFont(BraFlow.this, R.font.apercu_regular);

        tv_brand_not_listed = findViewById(R.id.tv_brand_not_listed);
        tv_brand_error = findViewById(R.id.tv_brand_error);

        tv_error_band = findViewById(R.id.tv_error_band);
        tv_error_cup = findViewById(R.id.tv_error_cup);
        tv_error_wire = findViewById(R.id.tv_error_wire);


        tv_error_band_noBrand = findViewById(R.id.tv_error_band_noBrand);
        tv_error_cup_noBrand = findViewById(R.id.tv_error_cup_noBrand);
        tv_error_wire_noBrand = findViewById(R.id.tv_error_wire_noBrand);


        tv_type_size = findViewById(R.id.tv_type_size);

        layout_brand_search = findViewById(R.id.layout_brand_search);
        layout_add = findViewById(R.id.layout_add);
        layout_brands = findViewById(R.id.layout_brands);
        layout_loading = findViewById(R.id.layout_loading);
        layout_recommended = findViewById(R.id.layout_recommended);
        layout_start_over = findViewById(R.id.layout_start_over);


        layout_brand = findViewById(R.id.layout_brand);
        layout_bra_profile = findViewById(R.id.layout_bra_profile);
        layout_bra_profile_noBrand = findViewById(R.id.layout_bra_profile_noBrand);
        layout_result = findViewById(R.id.layout_result);
        layout_band_feel = findViewById(R.id.layout_band_feel);
        layout_cup_fit = findViewById(R.id.layout_cup_fit);
        layout_bra_old = findViewById(R.id.layout_bra_old);
        layout_gaps = findViewById(R.id.layout_gaps);
        layout_strap_fit = findViewById(R.id.layout_strap_fit);
        layout_underwire_front = findViewById(R.id.layout_underwire_front);
        layout_underwire_side = findViewById(R.id.layout_underwire_side);
        layout_bra_style = findViewById(R.id.layout_bra_style);


        tb_out_of_stock = findViewById(R.id.tb_out_of_stock);
        tv_not_recommended = findViewById(R.id.tv_not_recommended);
        tv_add_cart = findViewById(R.id.tv_add_cart);
        tv_size_result = findViewById(R.id.tv_size_result);
        tv_privacy_policy = findViewById(R.id.tv_privacy_policy);
        tv_description = findViewById(R.id.tv_description);
        tv_type_size_noBrand = findViewById(R.id.tv_type_size_noBrand);
        layout_sizeType_selection = findViewById(R.id.layout_sizeType_selection);
        loader_text = findViewById(R.id.loader_text);

        layout_close = findViewById(R.id.layout_close);
        layout_privacy = findViewById(R.id.layout_privacy);
        layout_popup = findViewById(R.id.layout_popup);

        layout_cup_back = findViewById(R.id.layout_cup_back);
        layout_cup_next = findViewById(R.id.layout_cup_next);

        layout_cup_next_2 = findViewById(R.id.layout_cup_next_2);
        layout_cup_back_2 = findViewById(R.id.layout_cup_back_2);

        layout_cup_row1 = findViewById(R.id.layout_cup_row1);
        layout_cup_row2 = findViewById(R.id.layout_cup_row2);
        layout_cup_row3 = findViewById(R.id.layout_cup_row3);

        layout_band_row1= findViewById(R.id.layout_band_row1);
        layout_band_row2 = findViewById(R.id.layout_band_row2);

        layout_band_next_1 = findViewById(R.id.layout_band_next_1);
        layout_band_back_1 = findViewById(R.id.layout_band_back_1);

        layout_cup_back_noBrand = findViewById(R.id.layout_cup_back_noBrand);
        layout_cup_next_noBrand = findViewById(R.id.layout_cup_next_noBrand);

        layout_cup_next_2_noBrand = findViewById(R.id.layout_cup_next_2_noBrand);
        layout_cup_back_2_noBrand = findViewById(R.id.layout_cup_back_2_noBrand);

        layout_cup_row1_noBrand = findViewById(R.id.layout_cup_row1_noBrand);
        layout_cup_row2_noBrand = findViewById(R.id.layout_cup_row2_noBrand);
        layout_cup_row3_noBrand = findViewById(R.id.layout_cup_row3_noBrand);

        layout_band_row1_noBrand = findViewById(R.id.layout_band_row1_noBrand);
        layout_band_row2_noBrand = findViewById(R.id.layout_band_row2_noBrand);

        layout_band_next_1_noBrand = findViewById(R.id.layout_band_next_1_noBrand);
        layout_band_back_1_noBrand = findViewById(R.id.layout_band_back_1_noBrand);

        layout_fit_how = findViewById(R.id.layout_fit_how);

        tv_how_fit = findViewById(R.id.tv_how_fit);
        tv_taken_account = findViewById(R.id.tv_taken_account);

        tv_au = findViewById(R.id.tv_au);
        tv_eu = findViewById(R.id.tv_eu);
        tv_fr = findViewById(R.id.tv_fr);
        tv_uk = findViewById(R.id.tv_uk);
        tv_us = findViewById(R.id.tv_us);

        tv_brand_continue = findViewById(R.id.tv_brand_continue);
        tv_bra_continue = findViewById(R.id.tv_bra_continue);
        tv_bra_continue_noBrand = findViewById(R.id.tv_bra_continue_noBrand);

        tv_back_bra = findViewById(R.id.tv_back_bra);
        tv_back_bra_noBrand = findViewById(R.id.tv_back_bra_noBrand);
        tv_back_band_feel = findViewById(R.id.tv_back_band_feel);
        tv_back_cup_fit = findViewById(R.id.tv_back_cup_fit);
        tv_back_strap_fit = findViewById(R.id.tv_back_strap_fit);
        tv_back_gap = findViewById(R.id.tv_back_gap);
        tv_back_bra_style = findViewById(R.id.tv_back_bra_style);
        tv_back_old_bra = findViewById(R.id.tv_back_old_bra);
        tv_back_layout_underwire_front = findViewById(R.id.tv_back_layout_underwire_front);
        tv_back_layout_underwire_side = findViewById(R.id.tv_back_layout_underwire_side);

        et_what_brand = findViewById(R.id.et_what_brand);

        layout_size_selection = findViewById(R.id.layout_size_selection);

        recycler_brand_suggestion = findViewById(R.id.recycler_brand_suggestion);
        recycler_bra_sizeType = findViewById(R.id.recycler_bra_sizeType);

        layout_digging_in = findViewById(R.id.layout_digging_in);
        layout_straight = findViewById(R.id.layout_straight);
        layout_riding_up = findViewById(R.id.layout_riding_up);


        tv_digging_in = findViewById(R.id.tv_digging_in);
        tv_straight = findViewById(R.id.tv_straight);
        tv_riding_up = findViewById(R.id.tv_riding_up);

        tv_digging_in_description = findViewById(R.id.tv_digging_in_description);
        tv_straight_description = findViewById(R.id.tv_straight_description);
        tv_riding_up_description = findViewById(R.id.tv_riding_up_description);

        ic_digging_in = findViewById(R.id.ic_digging_in);
        ic_straight = findViewById(R.id.ic_straight);
        ic_riding_up = findViewById(R.id.ic_riding_up);

        tv_bra_sizes_text = findViewById(R.id.tv_bra_sizes_text);
        tv_isOld_no = findViewById(R.id.tv_isOld_no);
        tv_isOld_yes = findViewById(R.id.tv_isOld_yes);
        tv_bra_style_text = findViewById(R.id.tv_bra_style_text);
        tv_old_bra_text = findViewById(R.id.tv_old_bra_text);
        tv_style_options_text = findViewById(R.id.tv_style_options_text);

        tv_gap_no = findViewById(R.id.tv_gap_no);
        tv_gap_yes = findViewById(R.id.tv_gap_yes);

        tv_bra_looser_hook_no = findViewById(R.id.tv_bra_looser_hook_no);
        tv_bra_looser_hook_yes = findViewById(R.id.tv_bra_looser_hook_yes);

        tv_bra_tight_hook_no = findViewById(R.id.tv_bra_tight_hook_no);
        tv_bra_tight_hook_yes = findViewById(R.id.tv_bra_tight_hook_yes);

        tv_old_bra_page_no = findViewById(R.id.tv_old_bra_page_no);
        tv_old_bra_page_yes = findViewById(R.id.tv_old_bra_page_yes);

        layout_just_right = findViewById(R.id.layout_just_right);
        layout_slipping = findViewById(R.id.layout_slipping);
        layout_gap = findViewById(R.id.layout_gap);


        tv_just_right = findViewById(R.id.tv_just_right);
        tv_slipping = findViewById(R.id.tv_slipping);
        tv_gap = findViewById(R.id.tv_gap);

        tv_just_right_description = findViewById(R.id.tv_just_right_description);
        tv_slipping_description = findViewById(R.id.tv_slipping_description);
        tv_gap_description = findViewById(R.id.tv_gap_description);

        ic_just_right = findViewById(R.id.ic_just_right);
        ic_slipping = findViewById(R.id.ic_slipping);
        ic_gap = findViewById(R.id.ic_gap);

        layout_strap_digging_in = findViewById(R.id.layout_strap_digging_in);
        layout_strap_fit_fine = findViewById(R.id.layout_strap_fit_fine);
        layout_strap_not_up = findViewById(R.id.layout_strap_not_up);

        tv_strap_digging_in = findViewById(R.id.tv_strap_digging_in);
        tv_strap_fit_fine = findViewById(R.id.tv_strap_fit_fine);
        tv_strap_not_up = findViewById(R.id.tv_strap_not_up);

        tv_strap_digging_in_description = findViewById(R.id.tv_strap_digging_in_description);
        tv_strap_fit_fine_description = findViewById(R.id.tv_strap_fit_fine_description);
        tv_strap_not_up_description = findViewById(R.id.tv_strap_not_up_description);

        ic_strap_digging_in = findViewById(R.id.ic_strap_digging_in);
        ic_strap_fit_fine = findViewById(R.id.ic_strap_fit_fine);
        ic_strap_not_up = findViewById(R.id.ic_strap_not_up);

        layout_full_coverage = findViewById(R.id.layout_full_coverage);
        layout_half_cup = findViewById(R.id.layout_half_cup);
        layout_pushup = findViewById(R.id.layout_pushup);
        layout_strapless = findViewById(R.id.layout_strapless);

        tv_full_coverage = findViewById(R.id.tv_full_coverage);
        tv_half_cup = findViewById(R.id.tv_half_cup);
        tv_pushup = findViewById(R.id.tv_pushup);
        tv_strapless = findViewById(R.id.tv_strapless);

        tv_full_coverage_description = findViewById(R.id.tv_full_coverage_description);
        tv_half_cup_description = findViewById(R.id.tv_half_cup_description);
        tv_pushup_description = findViewById(R.id.tv_pushup_description);
        tv_strapless_description = findViewById(R.id.tv_strapless_description);

        ic_full_coverage = findViewById(R.id.ic_full_coverage);
        ic_half_cup = findViewById(R.id.ic_half_cup);
        ic_strapless = findViewById(R.id.ic_strapless);
        ic_pushup = findViewById(R.id.ic_pushup);

        layout_lift_away = findViewById(R.id.layout_lift_away);
        layout_sit_comfortable = findViewById(R.id.layout_sit_comfortable);
        layout_digging_into_chest = findViewById(R.id.layout_digging_into_chest);

        tv_lift_away = findViewById(R.id.tv_lift_away);
        tv_sit_comfortable = findViewById(R.id.tv_sit_comfortable);
        tv_digging_into_chest = findViewById(R.id.tv_digging_into_chest);

        ic_lift_away = findViewById(R.id.ic_lift_away);
        ic_sit_comfortable = findViewById(R.id.ic_sit_comfortable);
        ic_digging_into_chest = findViewById(R.id.ic_digging_into_chest);

        layout_digging_arms = findViewById(R.id.layout_digging_arms);
        layout_around_bust = findViewById(R.id.layout_around_bust);
        layout_digging_chest = findViewById(R.id.layout_digging_chest);

        tv_digging_arms = findViewById(R.id.tv_digging_arms);
        tv_around_bust = findViewById(R.id.tv_around_bust);
        tv_digging_chest = findViewById(R.id.tv_digging_chest);

        ic_digging_arms = findViewById(R.id.ic_digging_arms);
        ic_around_bust = findViewById(R.id.ic_around_bust);
        ic_digging_chest = findViewById(R.id.ic_digging_chest);

        layout_at_top = findViewById(R.id.layout_at_top);
        layout_all_over = findViewById(R.id.layout_all_over);
        layout_at_bottom = findViewById(R.id.layout_at_bottom);

        tv_at_top = findViewById(R.id.tv_at_top);
        tv_all_over = findViewById(R.id.tv_all_over);
        tv_at_bottom = findViewById(R.id.tv_at_bottom);

        ic_at_top = findViewById(R.id.ic_at_top);
        ic_all_over = findViewById(R.id.ic_all_over);
        ic_at_bottom = findViewById(R.id.ic_at_bottom);

        layout_bought_recently = findViewById(R.id.layout_bought_recently);
        layout_6months = findViewById(R.id.layout_6months);
        layout_12months = findViewById(R.id.layout_12months);
        layout_1year = findViewById(R.id.layout_1year);

        tv_bought_recently = findViewById(R.id.tv_bought_recently);
        tv_6months = findViewById(R.id.tv_6months);
        tv_12months = findViewById(R.id.tv_12months);
        tv_1year = findViewById(R.id.tv_1year);

        layout_bra_isOld_button = findViewById(R.id.layout_isOld_button);
        layout_bra_loose_hook_button = findViewById(R.id.layout_bra_looser_hook_button);
        layout_bra_tight_hook_button = findViewById(R.id.layout_bra_tight_hook_button);
        layout_gap_button = findViewById(R.id.layout_gap_button);
        layout_old_bra_button = findViewById(R.id.layout_old_bra_page_button);

        tv_brand_1 = findViewById(R.id.tv_brand_1);
        tv_brand_2 = findViewById(R.id.tv_brand_2);
        tv_brand_3 = findViewById(R.id.tv_brand_3);
        tv_brand_4 = findViewById(R.id.tv_brand_4);
        tv_brand_5 = findViewById(R.id.tv_brand_5);

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


        layout_band_1 = findViewById(R.id.layout_band_1);
        layout_band_2 = findViewById(R.id.layout_band_2);
        layout_band_3 = findViewById(R.id.layout_band_3);
        layout_band_4 = findViewById(R.id.layout_band_4);
        layout_band_5 = findViewById(R.id.layout_band_5);
        layout_band_6 = findViewById(R.id.layout_band_6);
        layout_band_7 = findViewById(R.id.layout_band_7);
        layout_band_8 = findViewById(R.id.layout_band_8);
        layout_band_9 = findViewById(R.id.layout_band_9);
        layout_band_10 = findViewById(R.id.layout_band_10);

        tv_band_1_noBrand = findViewById(R.id.tv_band_1_noBrand);
        tv_band_2_noBrand = findViewById(R.id.tv_band_2_noBrand);
        tv_band_3_noBrand = findViewById(R.id.tv_band_3_noBrand);
        tv_band_4_noBrand = findViewById(R.id.tv_band_4_noBrand);
        tv_band_5_noBrand = findViewById(R.id.tv_band_5_noBrand);
        tv_band_6_noBrand = findViewById(R.id.tv_band_6_noBrand);
        tv_band_7_noBrand = findViewById(R.id.tv_band_7_noBrand);
        tv_band_8_noBrand = findViewById(R.id.tv_band_8_noBrand);
        tv_band_9_noBrand = findViewById(R.id.tv_band_9_noBrand);
        tv_band_10_noBrand = findViewById(R.id.tv_band_10_noBrand);


        layout_band_1_noBrand = findViewById(R.id.layout_band_1_noBrand);
        layout_band_2_noBrand = findViewById(R.id.layout_band_2_noBrand);
        layout_band_3_noBrand = findViewById(R.id.layout_band_3_noBrand);
        layout_band_4_noBrand = findViewById(R.id.layout_band_4_noBrand);
        layout_band_5_noBrand = findViewById(R.id.layout_band_5_noBrand);
        layout_band_6_noBrand = findViewById(R.id.layout_band_6_noBrand);
        layout_band_7_noBrand = findViewById(R.id.layout_band_7_noBrand);
        layout_band_8_noBrand = findViewById(R.id.layout_band_8_noBrand);
        layout_band_9_noBrand = findViewById(R.id.layout_band_9_noBrand);
        layout_band_10_noBrand = findViewById(R.id.layout_band_10_noBrand);

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

        layout_cup_1 = findViewById(R.id.layout_cup_1);
        layout_cup_2 = findViewById(R.id.layout_cup_2);
        layout_cup_3 = findViewById(R.id.layout_cup_3);
        layout_cup_4 = findViewById(R.id.layout_cup_4);
        layout_cup_5 = findViewById(R.id.layout_cup_5);
        layout_cup_6 = findViewById(R.id.layout_cup_6);
        layout_cup_7 = findViewById(R.id.layout_cup_7);
        layout_cup_8 = findViewById(R.id.layout_cup_8);
        layout_cup_9 = findViewById(R.id.layout_cup_9);
        layout_cup_10 = findViewById(R.id.layout_cup_10);
        layout_cup_11 = findViewById(R.id.layout_cup_11);
        layout_cup_12 = findViewById(R.id.layout_cup_12);

        tv_cup_1_noBrand = findViewById(R.id.tv_cup_1_noBrand);
        tv_cup_2_noBrand = findViewById(R.id.tv_cup_2_noBrand);
        tv_cup_3_noBrand = findViewById(R.id.tv_cup_3_noBrand);
        tv_cup_4_noBrand = findViewById(R.id.tv_cup_4_noBrand);
        tv_cup_5_noBrand = findViewById(R.id.tv_cup_5_noBrand);
        tv_cup_6_noBrand = findViewById(R.id.tv_cup_6_noBrand);
        tv_cup_7_noBrand = findViewById(R.id.tv_cup_7_noBrand);
        tv_cup_8_noBrand = findViewById(R.id.tv_cup_8_noBrand);
        tv_cup_9_noBrand = findViewById(R.id.tv_cup_9_noBrand);
        tv_cup_10_noBrand = findViewById(R.id.tv_cup_10_noBrand);
        tv_cup_11_noBrand = findViewById(R.id.tv_cup_11_noBrand);
        tv_cup_12_noBrand = findViewById(R.id.tv_cup_12_noBrand);

        layout_cup_1_noBrand = findViewById(R.id.layout_cup_1_noBrand);
        layout_cup_2_noBrand = findViewById(R.id.layout_cup_2_noBrand);
        layout_cup_3_noBrand = findViewById(R.id.layout_cup_3_noBrand);
        layout_cup_4_noBrand = findViewById(R.id.layout_cup_4_noBrand);
        layout_cup_5_noBrand = findViewById(R.id.layout_cup_5_noBrand);
        layout_cup_6_noBrand = findViewById(R.id.layout_cup_6_noBrand);
        layout_cup_7_noBrand = findViewById(R.id.layout_cup_7_noBrand);
        layout_cup_8_noBrand = findViewById(R.id.layout_cup_8_noBrand);
        layout_cup_9_noBrand = findViewById(R.id.layout_cup_9_noBrand);
        layout_cup_10_noBrand = findViewById(R.id.layout_cup_10_noBrand);
        layout_cup_11_noBrand = findViewById(R.id.layout_cup_11_noBrand);
        layout_cup_12_noBrand = findViewById(R.id.layout_cup_12_noBrand);

        tv_type_wired = findViewById(R.id.tv_type_wired);
        tv_type_unwired = findViewById(R.id.tv_type_unwired);

        layout_type_wired = findViewById(R.id.layout_type_wired);
        layout_type_unwired = findViewById(R.id.layout_type_unwired);

        tv_type_wired_noBrand = findViewById(R.id.tv_type_wired_noBrand);
        tv_type_unwired_noBrand = findViewById(R.id.tv_type_unwired_noBrand);

        layout_type_wired_noBrand = findViewById(R.id.layout_type_wired_noBrand);
        layout_type_unwired_noBrand = findViewById(R.id.layout_type_unwired_noBrand);


        layout_accurate_fit = findViewById(R.id.layout_accurate_fit);
        layout_edit = findViewById(R.id.layout_taken_account);
        layout_tell_more = findViewById(R.id.layout_tell_more);


        layout_progress_1 = findViewById(R.id.layout_progress_1);
        layout_progress_2 = findViewById(R.id.layout_progress_2);
        layout_progress_3 = findViewById(R.id.layout_progress_3);
        layout_progress_4 = findViewById(R.id.layout_progress_4);
        layout_progress_5 = findViewById(R.id.layout_progress_5);
        layout_progress_6 = findViewById(R.id.layout_progress_6);
        layout_progress_7 = findViewById(R.id.layout_progress_7);
        layout_progress_8 = findViewById(R.id.layout_progress_8);
        layout_progress_9 = findViewById(R.id.layout_progress_9);
        layout_progress_10 = findViewById(R.id.layout_progress_10);
        layout_progress_11 = findViewById(R.id.layout_progress_11);


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

        layout_band_1_noBrand.setOnClickListener(this);
        layout_band_2_noBrand.setOnClickListener(this);
        layout_band_3_noBrand.setOnClickListener(this);
        layout_band_4_noBrand.setOnClickListener(this);
        layout_band_5_noBrand.setOnClickListener(this);
        layout_band_6_noBrand.setOnClickListener(this);
        layout_band_7_noBrand.setOnClickListener(this);
        layout_band_8_noBrand.setOnClickListener(this);
        layout_band_9_noBrand.setOnClickListener(this);
        layout_band_10_noBrand.setOnClickListener(this);


        layout_cup_1_noBrand.setOnClickListener(this);
        layout_cup_2_noBrand.setOnClickListener(this);
        layout_cup_3_noBrand.setOnClickListener(this);
        layout_cup_4_noBrand.setOnClickListener(this);
        layout_cup_5_noBrand.setOnClickListener(this);
        layout_cup_6_noBrand.setOnClickListener(this);
        layout_cup_7_noBrand.setOnClickListener(this);
        layout_cup_8_noBrand.setOnClickListener(this);
        layout_cup_9_noBrand.setOnClickListener(this);
        layout_cup_10_noBrand.setOnClickListener(this);
        layout_cup_11_noBrand.setOnClickListener(this);
        layout_cup_12_noBrand.setOnClickListener(this);


        tv_au.setOnClickListener(this);
        tv_eu.setOnClickListener(this);
        tv_fr.setOnClickListener(this);
        tv_uk.setOnClickListener(this);
        tv_us.setOnClickListener(this);


        SpannableString content, content2, content3;

        content = new SpannableString(getResources().getString(R.string.bra_brand_not_listed));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv_brand_not_listed.setText(content);

        content2 = new SpannableString(getResources().getString(R.string.bra_result_how_fit));
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
        tv_how_fit.setText(content2);

        content3 = new SpannableString(getResources().getString(R.string.bra_result_taken_account)+" "+getResources().getString(R.string.bar_edit));
        content3.setSpan(new UnderlineSpan(), content3.length()- getString(R.string.bar_edit).length(), content3.length(), 0);
        content3.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_text)), content3.length()- getString(R.string.bar_edit).length(), content3.length(), 0);
        tv_taken_account.setText(content3);


        if (preferredLanguage.equals("hk") || preferredLanguage.equals("tw"))
        {
            tv_description.setVisibility(View.GONE);
        }


        brandAdapter = new BrandAdapter(brandModelArrayList, BraFlow.this, new BrandAdapter.onItemClickListener() {
            @Override
            public void onItemClick(BrandModel suggestions) {

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

        braSizeTypeAdapter = new BraSizeTypeAdapter(braSizeTypeArrayList, BraFlow.this, new BraSizeTypeAdapter.onItemClickListener() {
            @Override
            public void onItemClick(BrandModel suggestions) {

                region = suggestions.getName();

                tv_type_size.setText(region);

                layout_size_selection.setVisibility(View.GONE);

                hideAllBands();
                hideAllCup();

                clearAllBands();
                clearAllCup();

                layout_cup_row1.setVisibility(View.VISIBLE);
                layout_cup_row2.setVisibility(View.GONE);

                layout_band_row1.setVisibility(View.VISIBLE);
                layout_band_row2.setVisibility(View.GONE);

                setBraSizes(braSizeChart,region);

                checkBraSize(band,cup,isWired);

                braSizeTypeAdapter.notifyDataSetChanged();

            }
        });

        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recycler_bra_sizeType.setItemAnimator(new DefaultItemAnimator());
        recycler_bra_sizeType.setLayoutManager(mLayoutManager2);
        recycler_bra_sizeType.setAdapter(braSizeTypeAdapter);


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
                else
                {
                    layout_brands.setVisibility(View.VISIBLE);
                    layout_brand_search.setVisibility(View.GONE);

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


        layout_cup_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_cup_row1.setVisibility(View.GONE);
                layout_cup_row2.setVisibility(View.VISIBLE);
            }
        });


        layout_cup_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_cup_row1.setVisibility(View.VISIBLE);
                layout_cup_row2.setVisibility(View.GONE);

            }
        });


        layout_cup_next_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_cup_row2.setVisibility(View.GONE);
                layout_cup_row3.setVisibility(View.VISIBLE);
            }
        });


        layout_cup_back_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_cup_row2.setVisibility(View.VISIBLE);
                layout_cup_row3.setVisibility(View.GONE);

            }
        });


        layout_band_next_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_band_row1.setVisibility(View.GONE);
                layout_band_row2.setVisibility(View.VISIBLE);
            }
        });


        layout_band_back_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_band_row1.setVisibility(View.VISIBLE);
                layout_band_row2.setVisibility(View.GONE);

            }
        });




        layout_cup_next_noBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_cup_row1_noBrand.setVisibility(View.GONE);
                layout_cup_row2_noBrand.setVisibility(View.VISIBLE);
            }
        });


        layout_cup_back_noBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_cup_row1_noBrand.setVisibility(View.VISIBLE);
                layout_cup_row2_noBrand.setVisibility(View.GONE);

            }
        });


        layout_cup_next_2_noBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_cup_row2_noBrand.setVisibility(View.GONE);
                layout_cup_row3_noBrand.setVisibility(View.VISIBLE);
            }
        });


        layout_cup_back_2_noBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_cup_row2_noBrand.setVisibility(View.VISIBLE);
                layout_cup_row3_noBrand.setVisibility(View.GONE);

            }
        });


        layout_band_next_1_noBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_band_row1_noBrand.setVisibility(View.GONE);
                layout_band_row2_noBrand.setVisibility(View.VISIBLE);
            }
        });


        layout_band_back_1_noBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_band_row1_noBrand.setVisibility(View.VISIBLE);
                layout_band_row2_noBrand.setVisibility(View.GONE);

            }
        });


        layout_tell_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_band_feel.setVisibility(View.VISIBLE);
                layout_result.setVisibility(View.GONE);
                progress(3);

                tellMore = true;

            }
        });


        layout_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_band_feel.setVisibility(View.VISIBLE);
                layout_result.setVisibility(View.GONE);
                progress(3);


                if (bandFit.equals("1"))
                {
                    tv_bra_sizes_text.setText(getResources().getString(R.string.bra_feel_digging));

                    layout_bra_loose_hook_button.setVisibility(View.VISIBLE);
                    layout_bra_isOld_button.setVisibility(View.GONE);
                    layout_bra_tight_hook_button.setVisibility(View.GONE);
                }
                else if (bandFit.equals("2"))
                {
                    tv_bra_sizes_text.setText("");

                    layout_bra_loose_hook_button.setVisibility(View.GONE);
                    layout_bra_isOld_button.setVisibility(View.GONE);
                    layout_bra_tight_hook_button.setVisibility(View.GONE);
                }
                else if (bandFit.equals("3"))
                {
                    tv_bra_sizes_text.setText(getResources().getString(R.string.bra_isOld_bra));

                    layout_bra_loose_hook_button.setVisibility(View.GONE);
                    layout_bra_isOld_button.setVisibility(View.VISIBLE);
                    layout_bra_tight_hook_button.setVisibility(View.GONE);
                }

                isEditFlow = true;
               // tellMore = true;

//                bandFit = "0";
//                cupFit = "0";
//                strapsFit = "0";
//                braAge = "0";
//                braStyle = "0";
//                gaps = "0";

//                sameBra = false ;
//                tightHook = false;
//                looseHook = false;
//                tightStrap = false;

//                sideWireFit = "0";
//                frontWireFit = "0";

            }
        });


        layout_accurate_fit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moreAccurate = true;

                layout_result.setVisibility(View.GONE);

                if (!bandFit.equals("0"))
                {
                    setBandFeel(bandFit);

                    layout_band_feel.setVisibility(View.GONE);


                    if (bandFit.equals("3")  && braAge.equals("0") && sameBra)
                    {
                        layout_bra_old.setVisibility(View.VISIBLE);
                        progress(4);
                    }
                    else
                    {
                        setBraAge(braAge);

                        layout_bra_old.setVisibility(View.GONE);

                        if (!cupFit.equals("0"))
                        {
                            setCupFit(cupFit);

                            layout_cup_fit.setVisibility(View.GONE);

                            if(cupFit.equals("3") && gaps.equals("0"))
                            {
                                layout_gaps.setVisibility(View.VISIBLE);

                                progress(6);
                            }
                            else
                            {
                                setGaps(gaps);

                                layout_gaps.setVisibility(View.GONE);

                                if (gaps.equals("3") && braStyle.equals("0"))
                                {
                                    layout_bra_style.setVisibility(View.VISIBLE);
                                    progress(7);
                                }
                                else
                                {
                                    setBraStyle(braStyle);

                                    layout_bra_style.setVisibility(View.GONE);

                                    if (!strapsFit.equals("0"))
                                    {
                                        setStraps(strapsFit);

                                        layout_strap_fit.setVisibility(View.GONE);

                                        if (wired || wired_noBrand)
                                        {
                                            if (!frontWireFit.equals("0"))
                                            {
                                                setFrontWire(frontWireFit);

                                                layout_underwire_front.setVisibility(View.GONE);

                                                if (sideWireFit.equals("0"))
                                                {
                                                    layout_underwire_side.setVisibility(View.VISIBLE);
                                                    progress(10);
                                                }

                                            }
                                            else
                                            {
                                                layout_underwire_front.setVisibility(View.VISIBLE);
                                                progress(9);
                                            }

                                        }
                                    }
                                    else
                                    {
                                        layout_strap_fit.setVisibility(View.VISIBLE);
                                        progress(8);
                                    }
                                }

                            }
                        }
                        else
                        {
                            layout_cup_fit.setVisibility(View.VISIBLE);
                            progress(5);
                        }
                    }


                }
                else
                {
                    layout_band_feel.setVisibility(View.VISIBLE);
                    progress(3);
                }

            }
        });






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


        tv_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("recommended",isRecommended);
                returnIntent.putExtra("result",size);
                returnIntent.putExtra("addToBag",true);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });


        tv_how_fit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (layout_fit_how.getVisibility() == View.VISIBLE)
                {
                    layout_fit_how.setVisibility(View.GONE);
                }
                else
                {
                    layout_fit_how.setVisibility(View.VISIBLE);
                }
            }
        });

        layout_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (layout_fit_how.getVisibility() == View.VISIBLE)
                {
                    layout_fit_how.setVisibility(View.GONE);
                }
            }
        });


        tv_brand_not_listed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_brand.setVisibility(View.GONE);
                layout_bra_profile_noBrand.setVisibility(View.VISIBLE);
                layout_sizeType_selection.setVisibility(View.GONE);

                progress(2);

                brandNotListed = true;


                clearBraType();


                if (region_noBrand.equals(""))
                {
                    region_noBrand = "EU";

                    setEU("","");

                    tv_type_size_noBrand.setText(getResources().getString(R.string.eu));
                    layout_sizeType_selection.setVisibility(View.GONE);

                    clearAllBandNoBrand();
                    clearAllCupNoBrand();
                    checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);
                }

            }
        });


        tv_type_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        tv_type_size_noBrand.setOnClickListener(new View.OnClickListener() {
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


                if (layout_sizeType_selection.getVisibility() == View.VISIBLE)
                {
                    layout_sizeType_selection.setVisibility(View.GONE);
                    tv_type_size_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
                }
                else
                {
                    layout_sizeType_selection.setVisibility(View.VISIBLE);
                    tv_type_size_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                }
            }
        });

        layout_type_wired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wired = true;
                isWired = "true";
                tv_type_wired.setTextColor(getResources().getColor(R.color.color_text));
                layout_type_wired.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));

                tv_type_unwired.setTextColor(getResources().getColor(R.color.grey_3));
                layout_type_unwired.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));

                tv_error_wire.setVisibility(View.GONE);

                checkBraSize(band,cup,isWired);
            }
        });

        layout_type_unwired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wired = false;
                isWired = "false";
                tv_type_unwired.setTextColor(getResources().getColor(R.color.color_text));
                layout_type_unwired.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));

                tv_type_wired.setTextColor(getResources().getColor(R.color.grey_3));
                layout_type_wired.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));

                tv_error_wire.setVisibility(View.GONE);

                checkBraSize(band,cup,isWired);
            }
        });



        layout_type_wired_noBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wired_noBrand = true;
                isWired_noBrand = "true";
                tv_type_wired_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_type_wired_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));

                tv_type_unwired_noBrand.setTextColor(getResources().getColor(R.color.grey_3));
                layout_type_unwired_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));

                tv_error_wire_noBrand.setVisibility(View.GONE);

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);
            }
        });

        layout_type_unwired_noBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wired_noBrand = false;
                isWired_noBrand = "false";
                tv_type_unwired_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_type_unwired_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));

                tv_type_wired_noBrand.setTextColor(getResources().getColor(R.color.grey_3));
                layout_type_wired_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));

                tv_error_wire_noBrand.setVisibility(View.GONE);

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);
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




        layout_start_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetData();

               // progress(1);

                braSizeTypeAdapter.updatePosition(0);

                braSizeTypeAdapter.notifyDataSetChanged();

                progress(1);

                layout_brand.setVisibility(View.VISIBLE);
                layout_result.setVisibility(View.GONE);

            }
        });


        tv_brand_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isBrandSelected)
                {
                    brandNotListed = false;
                    layout_brand.setVisibility(View.GONE);

                    //clearAllBrandSize();
                    Log.e("Brand",brand);
                    get_brand_sizes(gender,category,brand);

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



        tv_bra_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!band.equals("") && !cup.equals("") && !isWired.equals(""))
                {

                    updateUserDetails();

                    if (altId.equals(""))
                    {

                        getFinalSize(uID,brand,band,cup,region,wired);
                    }
                    else
                    {
                        getFinalSize(altId,brand,band,cup,region,wired);
                    }

                    layout_brand.setVisibility(View.GONE);
                    layout_bra_profile.setVisibility(View.GONE);
                }
                else
                {

                    if (band.equals(""))
                    {
                        tv_error_band.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        tv_error_band.setVisibility(View.GONE);
                    }
                    if (cup.equals(""))
                    {
                        tv_error_cup.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        tv_error_cup.setVisibility(View.GONE);
                    }
                    if (isWired.equals(""))
                    {
                        tv_error_wire.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        tv_error_wire.setVisibility(View.GONE);
                    }
//                    Toast.makeText(BraFlow.this,"Select All",Toast.LENGTH_SHORT).show();
                }

            }
        });



        tv_bra_continue_noBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!band_noBrand.equals("") && !cup_noBrand.equals("") && !isWired_noBrand.equals(""))
                {
                    //TODO Update data to server


                    Log.e("region_noBrand",region_noBrand);
                    Log.e("band_noBrand",band_noBrand);
                    Log.e("cup_noBrand",cup_noBrand);
                    Log.e("isWired_noBrand",isWired_noBrand);

                    layout_brand.setVisibility(View.GONE);
                    layout_bra_profile_noBrand.setVisibility(View.GONE);
                    layout_band_feel.setVisibility(View.VISIBLE);
                    progress(3);
                    updateUserDetails();

                }
                else
                {
                    if (band_noBrand.equals(""))
                    {
                        tv_error_band_noBrand.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        tv_error_band_noBrand.setVisibility(View.GONE);
                    }
                    if (cup_noBrand.equals(""))
                    {
                        tv_error_cup_noBrand.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        tv_error_cup_noBrand.setVisibility(View.GONE);
                    }
                    if (isWired_noBrand.equals(""))
                    {
                        tv_error_wire_noBrand.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        tv_error_wire_noBrand.setVisibility(View.GONE);
                    }

//                    Toast.makeText(BraFlow.this,"Select All",Toast.LENGTH_SHORT).show();
                }

            }
        });


        tv_back_bra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_brand.setVisibility(View.VISIBLE);
                layout_bra_profile.setVisibility(View.GONE);

                progress(1);

            }
        });


        tv_back_bra_noBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress(1);
                layout_brand.setVisibility(View.VISIBLE);
                layout_bra_profile_noBrand.setVisibility(View.GONE);

            }
        });



        tv_back_band_feel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isEditFlow || moreAccurate || tellMore)
                {
                    layout_band_feel.setVisibility(View.GONE);
                    layout_result.setVisibility(View.VISIBLE);

                    layout_edit.setVisibility(View.GONE);
                    layout_accurate_fit.setVisibility(View.VISIBLE);
                    layout_tell_more.setVisibility(View.GONE);

                }
                else
                {
                    layout_band_feel.setVisibility(View.GONE);
                    layout_bra_profile_noBrand.setVisibility(View.VISIBLE);
                    progress(2);
                }
            }
        });


        tv_back_cup_fit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sameBra)
                {
                    layout_bra_old.setVisibility(View.VISIBLE);
                    layout_cup_fit.setVisibility(View.GONE);
                    progress(4);
                }
                else
                {
                    layout_cup_fit.setVisibility(View.GONE);
                    layout_bra_profile_noBrand.setVisibility(View.GONE);
                    layout_band_feel.setVisibility(View.VISIBLE);
                    progress(3);
                }

            }
        });



        tv_back_strap_fit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_cup_fit.setVisibility(View.VISIBLE);
                layout_bra_profile_noBrand.setVisibility(View.GONE);
                layout_band_feel.setVisibility(View.GONE);
                layout_strap_fit.setVisibility(View.GONE);
                progress(5);
            }
        });

        tv_back_gap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_gaps.setVisibility(View.GONE);
                layout_cup_fit.setVisibility(View.VISIBLE);
                progress(5);
            }
        });

        tv_back_bra_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_gaps.setVisibility(View.VISIBLE);
                layout_bra_style.setVisibility(View.GONE);
                progress(6);
            }
        });

        tv_back_old_bra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_band_feel.setVisibility(View.VISIBLE);
                layout_bra_old.setVisibility(View.GONE);
                progress(3);
            }
        });

        tv_back_layout_underwire_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_strap_fit.setVisibility(View.VISIBLE);
                layout_underwire_front.setVisibility(View.GONE);
                progress(8);
            }
        });

        tv_back_layout_underwire_side.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_underwire_side.setVisibility(View.GONE);
                layout_underwire_front.setVisibility(View.VISIBLE);
                progress(9);
            }
        });

        tv_gap_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_gaps.setVisibility(View.GONE);
                layout_strap_fit.setVisibility(View.VISIBLE);
                tv_gap_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                tv_gap_no.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
                progress(8);
                tightStrap = true ;
                Log.e("tightStrap", String.valueOf(tightStrap));

                updateUserDetails();

            }
        });

        tv_gap_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_gaps.setVisibility(View.GONE);
                layout_bra_style.setVisibility(View.VISIBLE);
                tv_gap_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
                tv_gap_no.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                progress(7);
                tightStrap = false ;
                Log.e("tightStrap", String.valueOf(tightStrap));

                updateUserDetails();
            }
        });

        tv_isOld_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_band_feel.setVisibility(View.GONE);
                layout_bra_old.setVisibility(View.VISIBLE);
                progress(4);
                tv_isOld_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                tv_isOld_no.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));


                sameBra = true;
                bandFit="3";
                Log.e("isOldBra", String.valueOf(sameBra));
                Log.e("tightHook", String.valueOf(tightHook));
                Log.e("bandFit", String.valueOf(bandFit));
            }
        });

        tv_isOld_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_bra_sizes_text.setText(getResources().getString(R.string.bra_tighter_hook));

                layout_bra_tight_hook_button.setVisibility(View.VISIBLE);

                tv_isOld_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
                tv_isOld_no.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));


                sameBra = false;
                bandFit="3";

                Log.e("isOldBra", String.valueOf(sameBra));
                Log.e("bandFit", String.valueOf(bandFit));
            }
        });


        tv_bra_looser_hook_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_band_feel.setVisibility(View.GONE);
                layout_cup_fit.setVisibility(View.VISIBLE);
                progress(5);
                updateUserDetails();

                tv_bra_looser_hook_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                tv_bra_looser_hook_no.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));


                tv_isOld_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
                tv_isOld_no.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));


                looseHook = true;
                bandFit="1";

                Log.e("looseHook", String.valueOf(looseHook));
                Log.e("bandFit", String.valueOf(bandFit));
            }
        });

        tv_bra_looser_hook_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_bra_isOld_button.setVisibility(View.GONE);
                layout_band_feel.setVisibility(View.GONE);
                layout_cup_fit.setVisibility(View.VISIBLE);
                progress(5);
                updateUserDetails();

                tv_bra_looser_hook_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
                tv_bra_looser_hook_no.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                bandFit="1";
                looseHook = false;

                Log.e("looseHook", String.valueOf(looseHook));
                Log.e("bandFit", String.valueOf(bandFit));
            }
        });


        tv_bra_tight_hook_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_band_feel.setVisibility(View.GONE);
                layout_cup_fit.setVisibility(View.VISIBLE);
                progress(5);
                updateUserDetails();

                tv_bra_tight_hook_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                tv_bra_tight_hook_no.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));


                tightHook = true;
                sameBra = false;
                bandFit="3";
                Log.e("tightHook", String.valueOf(tightHook));
                Log.e("bandFit", String.valueOf(bandFit));

            }
        });

        tv_bra_tight_hook_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_band_feel.setVisibility(View.GONE);
                layout_cup_fit.setVisibility(View.VISIBLE);

                updateUserDetails();

                tv_bra_tight_hook_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
                tv_bra_tight_hook_no.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                tightHook = false;
                sameBra = false;
                bandFit="3";
                Log.e("tightHook", String.valueOf(tightHook));
                Log.e("bandFit", String.valueOf(bandFit));

            }
        });

        tv_old_bra_page_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_bra_old.setVisibility(View.GONE);
                layout_cup_fit.setVisibility(View.VISIBLE);
                progress(5);
                updateUserDetails();

                tv_old_bra_page_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                tv_old_bra_page_no.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));


                bandFit="3";
                tightHook = true;
                Log.e("tightHook", String.valueOf(tightHook));
                Log.e("bandFit", String.valueOf(bandFit));

            }
        });

        tv_old_bra_page_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_bra_old.setVisibility(View.GONE);
                layout_cup_fit.setVisibility(View.VISIBLE);
                progress(5);
                updateUserDetails();

                tv_old_bra_page_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
                tv_old_bra_page_no.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                bandFit="3";
                tightHook = false;
                Log.e("tightHook", String.valueOf(tightHook));
                Log.e("bandFit", String.valueOf(bandFit));

            }
        });


        layout_digging_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearBandFit();

                bandFit = "1";
                layout_digging_in.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_digging_in.setTextColor(getResources().getColor(R.color.color_text));
                tv_digging_in_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_digging_in.setImageResource(R.drawable.ic_bra_digging_in_selected);

                tv_bra_sizes_text.setText(getResources().getString(R.string.bra_feel_digging));

                layout_bra_isOld_button.setVisibility(View.GONE);
                layout_bra_loose_hook_button.setVisibility(View.VISIBLE);
                layout_bra_tight_hook_button.setVisibility(View.GONE);

                Log.e("bandFit",bandFit);

            }
        });

        layout_straight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearBandFit();

                bandFit = "2";
                layout_straight.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_straight.setTextColor(getResources().getColor(R.color.color_text));
                tv_straight_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_straight.setImageResource(R.drawable.ic_bra_straight_selected);

                tv_bra_sizes_text.setText("");

                layout_bra_loose_hook_button.setVisibility(View.GONE);
                layout_bra_isOld_button.setVisibility(View.GONE);
                layout_bra_tight_hook_button.setVisibility(View.GONE);

                layout_cup_fit.setVisibility(View.VISIBLE);
                layout_band_feel.setVisibility(View.GONE);
                progress(5);
                updateUserDetails();

                Log.e("bandFit",bandFit);


            }
        });

        layout_riding_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearBandFit();

                bandFit = "3";
                layout_riding_up.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_riding_up.setTextColor(getResources().getColor(R.color.color_text));
                tv_riding_up_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_riding_up.setImageResource(R.drawable.ic_bra_riding_up_selected);

                tv_bra_sizes_text.setText(getResources().getString(R.string.bra_isOld_bra));

                layout_bra_loose_hook_button.setVisibility(View.GONE);
                layout_bra_isOld_button.setVisibility(View.VISIBLE);
                layout_bra_tight_hook_button.setVisibility(View.GONE);

                Log.e("bandFit",bandFit);

            }
        });


        layout_just_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearCupFit();

                cupFit = "1";
                layout_just_right.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_just_right.setTextColor(getResources().getColor(R.color.color_text));
                tv_just_right_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_just_right.setImageResource(R.drawable.ic_cup_right_selected);


                layout_cup_fit.setVisibility(View.GONE);
                layout_strap_fit.setVisibility(View.VISIBLE);
                updateUserDetails();
                progress(8);
                Log.e("cupFit",cupFit);

            }
        });

        layout_slipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearCupFit();

                cupFit = "2";
                layout_slipping.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_slipping.setTextColor(getResources().getColor(R.color.color_text));
                tv_slipping_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_slipping.setImageResource(R.drawable.ic_cup_slipping_selected);

                layout_cup_fit.setVisibility(View.GONE);
                layout_strap_fit.setVisibility(View.VISIBLE);
                progress(8);
                updateUserDetails();

                Log.e("cupFit",cupFit);
            }
        });

        layout_gap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearCupFit();

                cupFit = "3";

                layout_gap.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_gap.setTextColor(getResources().getColor(R.color.color_text));
                tv_gap_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_gap.setImageResource(R.drawable.ic_gaps_selected);

                layout_cup_fit.setVisibility(View.GONE);
                layout_gaps.setVisibility(View.VISIBLE);

                progress(6);
                updateUserDetails();

                Log.e("cupFit",cupFit);

            }
        });


        layout_strap_digging_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearStrapFit();

                strapsFit="1";
                layout_strap_digging_in.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_strap_digging_in.setTextColor(getResources().getColor(R.color.color_text));
                tv_strap_digging_in_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_strap_digging_in.setImageResource(R.drawable.ic_strap_digging_in_selected);

                layout_strap_fit.setVisibility(View.GONE);

                updateUserDetails();

                if (wired_noBrand || wired)
                {
                    layout_underwire_front.setVisibility(View.VISIBLE);
                    progress(9);
                }
                else
                {
                    moreAccurate =false ;

                    if (altId.equals(""))
                    {
                        if (tellMore)
                        {
                            updateUserDetails();
                            getFinalSize(uID,brand,band,cup,region,wired);
                        }
                        else
                        {
                            updateUserDetails();
                            getFinalSize(uID,brand,band_noBrand,cup_noBrand,region_noBrand,wired_noBrand);
                        }
                    }
                    else
                    {
                        if (tellMore)
                        {
                            updateUserDetails();
                            getFinalSize(altId,brand,band,cup,region,wired);
                        }
                        else
                        {
                            updateUserDetails();
                            getFinalSize(altId,brand,band_noBrand,cup_noBrand,region_noBrand,wired_noBrand);
                        }
                    }
                }


                Log.e("strapsFit",strapsFit);

            }
        });

        layout_strap_fit_fine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearStrapFit();

                strapsFit="2";

                updateUserDetails();

                layout_strap_fit_fine.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_strap_fit_fine.setTextColor(getResources().getColor(R.color.color_text));
                tv_strap_fit_fine_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_strap_fit_fine.setImageResource(R.drawable.ic_strap_fit_fine_selected);

                layout_strap_fit.setVisibility(View.GONE);

                if (wired_noBrand || wired)
                {
                    layout_underwire_front.setVisibility(View.VISIBLE);
                    progress(9);
                }
                else
                {
                    moreAccurate =false ;

                    if (altId.equals(""))
                    {
                        if (tellMore)
                        {
                            updateUserDetails();
                            getFinalSize(uID,brand,band,cup,region,wired);
                        }
                        else
                        {
                            updateUserDetails();
                            getFinalSize(uID,brand,band_noBrand,cup_noBrand,region_noBrand,wired_noBrand);
                        }
                    }
                    else
                    {
                        if (tellMore)
                        {
                            updateUserDetails();
                            getFinalSize(altId,brand,band,cup,region,wired);
                        }
                        else
                        {
                            updateUserDetails();
                            getFinalSize(altId,brand,band_noBrand,cup_noBrand,region_noBrand,wired_noBrand);
                        }
                    }
                }
                Log.e("strapsFit",strapsFit);

            }
        });

        layout_strap_not_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearStrapFit();
                strapsFit="3";

                updateUserDetails();

                layout_strap_not_up.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_strap_not_up.setTextColor(getResources().getColor(R.color.color_text));
                tv_strap_not_up_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_strap_not_up.setImageResource(R.drawable.ic_strap_not_staying_selected);


                layout_strap_fit.setVisibility(View.GONE);

                if (wired_noBrand || wired)
                {
                    layout_underwire_front.setVisibility(View.VISIBLE);
                    progress(9);
                }
                else
                {
                    moreAccurate = false ;

                    if (altId.equals(""))
                    {
                        if (tellMore)
                        {
                            updateUserDetails();
                            getFinalSize(uID,brand,band,cup,region,wired);
                        }
                        else
                        {
                            updateUserDetails();
                            getFinalSize(uID,brand,band_noBrand,cup_noBrand,region_noBrand,wired_noBrand);
                        }
                    }
                    else
                    {
                        if (tellMore)
                        {
                            updateUserDetails();
                            getFinalSize(altId,brand,band,cup,region,wired);
                        }
                        else
                        {
                            updateUserDetails();
                            getFinalSize(altId,brand,band_noBrand,cup_noBrand,region_noBrand,wired_noBrand);
                        }
                    }
                }
                Log.e("strapsFit",strapsFit);

            }
        });



        layout_lift_away.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearUnderwireFront();

                frontWireFit = "1";
                layout_lift_away.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_lift_away.setTextColor(getResources().getColor(R.color.color_text));
                ic_lift_away.setImageResource(R.drawable.ic_underwire_front_lift_away_selected);


                layout_underwire_side.setVisibility(View.VISIBLE);
                layout_underwire_front.setVisibility(View.GONE);
                progress(10);
                updateUserDetails();

                Log.e("frontWireFit",frontWireFit);

            }
        });

        layout_sit_comfortable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearUnderwireFront();

                frontWireFit = "2";
                layout_sit_comfortable.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_sit_comfortable.setTextColor(getResources().getColor(R.color.color_text));
                ic_sit_comfortable.setImageResource(R.drawable.ic_underwire_front_sit_comfortable_selected);

                layout_underwire_side.setVisibility(View.VISIBLE);
                layout_underwire_front.setVisibility(View.GONE);
                progress(10);
                updateUserDetails();

                Log.e("frontWireFit",frontWireFit);

            }
        });

        layout_digging_into_chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearUnderwireFront();

                frontWireFit = "3";
                layout_digging_into_chest.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_digging_into_chest.setTextColor(getResources().getColor(R.color.color_text));
                ic_digging_into_chest.setImageResource(R.drawable.ic_underwire_front_digging_into_chest_selcted);

                layout_underwire_side.setVisibility(View.VISIBLE);
                layout_underwire_front.setVisibility(View.GONE);
                progress(10);
                updateUserDetails();

                Log.e("frontWireFit",frontWireFit);


            }
        });


        layout_digging_arms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearUnderwireSide();

                sideWireFit = "1";
                layout_digging_arms.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_digging_arms.setTextColor(getResources().getColor(R.color.color_text));
                ic_digging_arms.setImageResource(R.drawable.ic_underwire_side_digging_arms_selected);


                layout_underwire_side.setVisibility(View.VISIBLE);
                layout_underwire_front.setVisibility(View.GONE);

                updateUserDetails();

                Log.e("sideWireFit",sideWireFit);


                moreAccurate =false ;

                if (altId.equals(""))
                {
                    if (tellMore)
                    {
                        getFinalSize(uID,brand,band,cup,region,wired);
                    }
                    else
                    {
                        getFinalSize(uID,brand,band_noBrand,cup_noBrand,region_noBrand,wired_noBrand);
                    }
                }
                else
                {
                    if (tellMore)
                    {
                        getFinalSize(altId,brand,band,cup,region,wired);
                    }
                    else
                    {
                        getFinalSize(altId,brand,band_noBrand,cup_noBrand,region_noBrand,wired_noBrand);
                    }
                }


            }
        });

        layout_around_bust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearUnderwireSide();

                sideWireFit = "2";
                layout_around_bust.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_around_bust.setTextColor(getResources().getColor(R.color.color_text));
                ic_around_bust.setImageResource(R.drawable.ic_underwire_side_sit_nicely_selected);

                layout_underwire_side.setVisibility(View.VISIBLE);
                layout_underwire_front.setVisibility(View.GONE);
                progress(10);
                updateUserDetails();

                Log.e("sideWireFit",sideWireFit);

                moreAccurate =false ;

                if (altId.equals(""))
                {
                    if (tellMore)
                    {
                        getFinalSize(uID,brand,band,cup,region,wired);
                    }
                    else
                    {
                        getFinalSize(uID,brand,band_noBrand,cup_noBrand,region_noBrand,wired_noBrand);
                    }
                }
                else
                {
                    if (tellMore)
                    {
                        getFinalSize(altId,brand,band,cup,region,wired);
                    }
                    else
                    {
                        getFinalSize(altId,brand,band_noBrand,cup_noBrand,region_noBrand,wired_noBrand);
                    }
                }


            }
        });

        layout_digging_chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearUnderwireSide();

                sideWireFit = "3";
                layout_digging_chest.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_digging_chest.setTextColor(getResources().getColor(R.color.color_text));
                ic_digging_chest.setImageResource(R.drawable.ic_underwire_side_digging_in_selected);

                layout_underwire_side.setVisibility(View.VISIBLE);
                layout_underwire_front.setVisibility(View.GONE);
                progress(10);
                updateUserDetails();

                Log.e("sideWireFit",sideWireFit);

                moreAccurate =false ;

                if (altId.equals(""))
                {
                    if (tellMore)
                    {
                        getFinalSize(uID,brand,band,cup,region,wired);
                    }
                    else
                    {
                        getFinalSize(uID,brand,band_noBrand,cup_noBrand,region_noBrand,wired_noBrand);
                    }
                }
                else
                {
                    if (tellMore)
                    {
                        getFinalSize(altId,brand,band,cup,region,wired);
                    }
                    else
                    {
                        getFinalSize(altId,brand,band_noBrand,cup_noBrand,region_noBrand,wired_noBrand);
                    }
                }

            }
        });



        layout_at_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearGap();

                gaps = "1";
                layout_at_top.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_at_top.setTextColor(getResources().getColor(R.color.color_text));
                ic_at_top.setImageResource(R.drawable.ic_gap_at_top_selected);

                tv_bra_style_text.setText(getResources().getString(R.string.bra_stap_gap));
                layout_gap_button.setVisibility(View.VISIBLE);

                Log.e("gaps",gaps);

//                layout_gaps.setVisibility(View.GONE);
//                layout_underwire_front.setVisibility(View.GONE);



            }
        });

        layout_all_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearGap();

                gaps = "2";
                layout_all_over.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_all_over.setTextColor(getResources().getColor(R.color.color_text));
                ic_all_over.setImageResource(R.drawable.ic_gap_all_over_selected);

                tv_bra_style_text.setText("");
                layout_gap_button.setVisibility(View.GONE);

                layout_gaps.setVisibility(View.GONE);
                layout_strap_fit.setVisibility(View.VISIBLE);
                progress(8);
                updateUserDetails();


                Log.e("gaps",gaps);

            }
        });

        layout_at_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearGap();

                gaps = "3";
                layout_at_bottom.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_at_bottom.setTextColor(getResources().getColor(R.color.color_text));
                ic_at_bottom.setImageResource(R.drawable.ic_gap_at_bottom_selected);

                tv_bra_style_text.setText("");
                layout_gap_button.setVisibility(View.GONE);

                layout_gaps.setVisibility(View.GONE);
                layout_bra_style.setVisibility(View.VISIBLE);
                progress(7);
                updateUserDetails();


                Log.e("gaps",gaps);

            }
        });



        layout_full_coverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearStyle();

                braStyle = "1";
                layout_full_coverage.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_full_coverage.setTextColor(getResources().getColor(R.color.color_text));
                tv_full_coverage_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_full_coverage.setImageResource(R.drawable.ic_bra_style_full_coverage_selected);


               // layout_gap_button.setVisibility(View.VISIBLE);

                layout_strap_fit.setVisibility(View.VISIBLE);
                layout_bra_style.setVisibility(View.GONE);
                progress(8);
                updateUserDetails();


                Log.e("braStyle",braStyle);



            }
        });

        layout_half_cup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearStyle();

                braStyle = "2";
                layout_half_cup.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_half_cup.setTextColor(getResources().getColor(R.color.color_text));
                tv_half_cup_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_half_cup.setImageResource(R.drawable.ic_bra_style_half_cup_selected);

                layout_strap_fit.setVisibility(View.VISIBLE);
                layout_bra_style.setVisibility(View.GONE);
                progress(8);
                updateUserDetails();


                Log.e("braStyle",braStyle);

            }
        });

        layout_pushup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearStyle();

                braStyle = "3";
                layout_pushup.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_pushup.setTextColor(getResources().getColor(R.color.color_text));
                tv_pushup_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_pushup.setImageResource(R.drawable.ic_bra_style_pushup_selected);

                layout_strap_fit.setVisibility(View.VISIBLE);
                layout_bra_style.setVisibility(View.GONE);
                progress(8);
                updateUserDetails();


                Log.e("braStyle",braStyle);

            }
        });

        layout_strapless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearStyle();

                braStyle = "4";
                layout_strapless.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_strapless.setTextColor(getResources().getColor(R.color.color_text));
                tv_strapless_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_strapless.setImageResource(R.drawable.ic_bra_style_strapless_selected);

                layout_strap_fit.setVisibility(View.VISIBLE);
                layout_bra_style.setVisibility(View.GONE);
                progress(8);
                updateUserDetails();


                Log.e("braStyle",braStyle);

            }
        });

        tv_style_options_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearStyle();

                braStyle = "1";

                layout_strap_fit.setVisibility(View.VISIBLE);
                layout_bra_style.setVisibility(View.GONE);
                progress(8);
                updateUserDetails();


                Log.e("braStyle",braStyle);
            }
        });

        layout_bought_recently.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearOldBra();
                layout_bought_recently.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_bought_recently.setTextColor(getResources().getColor(R.color.color_text));

                tv_old_bra_text.setText(getResources().getString(R.string.bra_tighter_hook));
                layout_old_bra_button.setVisibility(View.VISIBLE);

                braAge = "1";
                Log.e("braAge",braAge);
            }
        });

        layout_6months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearOldBra();
                layout_6months.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_6months.setTextColor(getResources().getColor(R.color.color_text));

                tv_old_bra_text.setText(getResources().getString(R.string.bra_tighter_hook));
                layout_old_bra_button.setVisibility(View.VISIBLE);

                braAge = "2";
                Log.e("braAge",braAge);

            }
        });

        layout_12months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearOldBra();
                layout_12months.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_12months.setTextColor(getResources().getColor(R.color.color_text));

                layout_bra_old.setVisibility(View.GONE);
                layout_cup_fit.setVisibility(View.VISIBLE);
                progress(5);
                updateUserDetails();


                braAge = "3";
                Log.e("braAge",braAge);

            }
        });

        layout_1year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearOldBra();
                layout_1year.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_1year.setTextColor(getResources().getColor(R.color.color_text));

                layout_bra_old.setVisibility(View.GONE);
                layout_cup_fit.setVisibility(View.VISIBLE);
                progress(5);
                updateUserDetails();


                braAge = "4";
                Log.e("braAge",braAge);

            }
        });


        tv_brand_1.setOnClickListener(this);
        tv_brand_2.setOnClickListener(this);
        tv_brand_3.setOnClickListener(this);
        tv_brand_4.setOnClickListener(this);
        tv_brand_5.setOnClickListener(this);

        getData();
        get_brands(gender,category);
        setDefaultBrands();


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




    private void updateUserDetails() {

        try {

            if (tellMore && bandFit.equals(""))
            {

                moreAccurate = true;

                Log.e("moreAccurate", String.valueOf(moreAccurate));

                layout_edit.setVisibility(View.GONE);
                layout_accurate_fit.setVisibility(View.VISIBLE);
                layout_tell_more.setVisibility(View.GONE);

            }

            JSONObject object = new JSONObject();
            JSONObject femaleObject = new JSONObject();
            JSONObject whois = new JSONObject();
            JSONObject braOptions = new JSONObject();
            JSONObject referenceBrands = new JSONObject();
            JSONArray referenceBrandsArray = new JSONArray();

            object.put("uid",uID);
            object.put("gender",gender);
            object.put("altId",altId);
            object.put("type",category.toLowerCase());


            if (gender.equals("female"))
            {
                referenceBrands.put("category",category);

                if (brandNotListed)
                {
                    braOptions.put("wired",String.valueOf(wired_noBrand));
                    braOptions.put("region",region_noBrand);
                    braOptions.put("band",Integer.parseInt(band_noBrand));
                    braOptions.put("cup",cup_noBrand);
                    braOptions.put("bandFit",bandFit);
                    braOptions.put("cupFit",cupFit);
                    braOptions.put("strapsFit",strapsFit);
                    braOptions.put("braAge",braAge);
                    braOptions.put("sameBra",String.valueOf(sameBra));
                    braOptions.put("tightHook",String.valueOf(tightHook));
                    braOptions.put("looseHook",String.valueOf(looseHook));
                    braOptions.put("tightStrap",String.valueOf(tightStrap));
                    braOptions.put("braStyle",braStyle);
                    braOptions.put("frontWireFit",frontWireFit);
                    braOptions.put("sideWireFit",sideWireFit);
                    braOptions.put("gaps",gaps);
                }

                else if(tellMore)
                {
                    referenceBrands.put("brand",brand);
                    referenceBrands.put("band",Integer.parseInt(band));
                    referenceBrands.put("cup",cup);
                    referenceBrands.put("region",region);
                    referenceBrands.put("wired",wired);

                    braOptions.put("bandFit",bandFit);
                    braOptions.put("cupFit",cupFit);
                    braOptions.put("strapsFit",strapsFit);
                    braOptions.put("braAge",braAge);
                    braOptions.put("sameBra",String.valueOf(sameBra));
                    braOptions.put("tightHook",String.valueOf(tightHook));
                    braOptions.put("looseHook",String.valueOf(looseHook));
                    braOptions.put("tightStrap",String.valueOf(tightStrap));
                    braOptions.put("braStyle",braStyle);
                    braOptions.put("frontWireFit",frontWireFit);
                    braOptions.put("sideWireFit",sideWireFit);
                    braOptions.put("gaps",gaps);
                }
                else
                {
                    referenceBrands.put("brand",brand);
                    referenceBrands.put("band",Integer.parseInt(band));
                    referenceBrands.put("cup",cup);
                    referenceBrands.put("region",region);
                    referenceBrands.put("wired",wired);
                }



                referenceBrandsArray.put(0,referenceBrands);
                femaleObject.put("referenceBrands",referenceBrandsArray);
                femaleObject.put("braOptions",braOptions);

                whois.put("female",femaleObject);
            }

            object.put("whois",whois);


            Log.e("Data", String.valueOf(object));


            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {

                if (!startOver && !isEditFlow)
                {
                    POST post = new POST(this, "https://discoverysvc.pixibo.com/uid" ,object, UpdateData, this);
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
                Utils.showToast(this,getResources().getString(R.string.no_internet));
            }

        } catch (Exception e) {
            //Utils.hideLoading();
            Utils.showToast(this,getResources().getString(R.string.something_wrong));
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
        }
    }


    private void get_brand_sizes(String gender ,String category,String brand) {

        layout_loading.setVisibility(View.VISIBLE);
        loader_text.setVisibility(View.INVISIBLE);

        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {
                GET get = new GET(this, URI.base2 +"brands/charts/"+gender.toLowerCase()+"/"+category.toLowerCase()+"/"+brand , BrandSizes, this);
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



    private void getFinalSize(String uID, String brand, String band , String cup , String region , boolean wiredBra) {

        layout_loading.setVisibility(View.VISIBLE);
        loader_text.setVisibility(View.VISIBLE);
        layout_underwire_side.setVisibility(View.GONE);
        layout_bra_profile.setVisibility(View.GONE);

        startOver = false;
        isEditFlow = false;
        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {

                if (!brandNotListed)
                {
                    GET get = new GET(this, "https://sizeguidev2.pixibo.com/asset/"+clientId+"/"+skuId+"?uid="+uID+"&brand="+brand+"&band="+band+"&cup="+cup+"&region="+region+"&wiredBra="+wiredBra, GetSize, this);
                    get.execute();
                }
                else if (tellMore)
                {
                    GET get = new GET(this, "https://sizeguidev2.pixibo.com/asset/"+clientId+"/"+skuId+"?uid="+uID+"&brand="+brand+"&band="+band+"&cup="+cup+"&region="+region+"&wiredBra="+wiredBra+"&bandPref="+bandFit+"&sameBra="+sameBra+"&looseHook="+looseHook+"&tightHook="+tightHook+"&cupPref="+cupFit+"&wiresFront="+frontWireFit+"&wiresSide="+sideWireFit+"&straps="+strapsFit+"&gaps="+gaps+"&tightStraps="+tightStrap+"&braStyle="+braStyle+"&braAge="+braAge , GetSize, this);
                    get.execute();
                }
                else
                {
                    GET get = new GET(this, "https://sizeguidev2.pixibo.com/asset/"+clientId+"/"+skuId+"?uid="+uID+"&band="+band+"&cup="+cup+"&region="+region+"&wiredBra="+wiredBra+"&bandPref="+bandFit+"&sameBra="+sameBra+"&looseHook="+looseHook+"&tightHook="+tightHook+"&cupPref="+cupFit+"&wiresFront="+frontWireFit+"&wiresSide="+sideWireFit+"&straps="+strapsFit+"&gaps="+gaps+"&tightStraps="+tightStrap+"&braStyle="+braStyle+"&braAge="+braAge , GetSize, this);
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
            case UpdateData:
                try
                {

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;

            case BrandSuggestion:
                try
                {
                    String[] strArray = result.split(",") ;

                    for (String s : strArray) {

                        BrandModel brandModel = new BrandModel();
                        brandModel.setName(s.replaceAll("[\"^\\[\\]]",""));

                        brandModelArrayList.add(brandModel);
                    }

                    brandAdapter.notifyDataSetChanged();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;

            case BrandSizes:

                try
                {
                    layout_loading.setVisibility(View.GONE);
                    layout_bra_profile.setVisibility(View.VISIBLE);
                    progress(2);

                    JSONObject object = new JSONObject(result);

                    if (object.has("regular"))
                    {

                        braSizeTypeArrayList.clear();

                        braSizeChart = object.getString("regular") ;

                        JSONArray range = new JSONArray(object.getString("regular"));

                        for (int i = 0; i < range.length(); i++)
                        {

                            JSONObject obj = range.getJSONObject(i);
                            BrandModel item = new BrandModel();

                            if (i == 0)
                            {

                                region = obj.getString("region");

                                tv_type_size.setText(obj.getString("region"));

                                layout_size_selection.setVisibility(View.GONE);

                                tv_type_size.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

                                hideAllBands();
                                hideAllCup();

                                clearAllBands();
                                clearAllCup();

                                isWired = "";
                                wired = false;

                                tv_type_wired.setTextColor(getResources().getColor(R.color.grey_3));
                                layout_type_wired.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));

                                tv_type_unwired.setTextColor(getResources().getColor(R.color.grey_3));
                                layout_type_unwired.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));


                                setBraSizes(braSizeChart,obj.getString("region"));
                            }

                            item.setName(obj.getString("region"));

                            braSizeTypeArrayList.add(item);

                        }

                        braSizeTypeAdapter.notifyDataSetChanged();


                        switch (braSizeTypeArrayList.size())
                        {

                            case 1:
                                pixels= (int) (50 * scale + 0.5f);
                                recycler_bra_sizeType.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, pixels));
                                Log.e("Case","1");
                                break;

                            case 2:
                                pixels = (int) (100 * scale + 0.5f);
                                recycler_bra_sizeType.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, pixels));
                                Log.e("Case","2");
                                break;

                            case 3:
                                pixels = (int) (150 * scale + 0.5f);
                                recycler_bra_sizeType.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, pixels));
                                Log.e("Case","3");
                                break;

                        }

                        if (braSizeTypeArrayList.size() >= 4)
                        {
                            scale = getResources().getDisplayMetrics().density;
                            pixels = (int) (200 * scale + 0.5f);
                            recycler_bra_sizeType.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, pixels));
                        }
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
                    if(statusCode ==200)
                    {


                        updateUserDetails();

                        progress(11);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {

                                layout_bra_profile.setVisibility(View.GONE);
                                layout_loading.setVisibility(View.GONE);
                                layout_result.setVisibility(View.VISIBLE);


                            }
                        }, 1000);


                        JSONObject jsonObject = new JSONObject(result);


                        if (jsonObject.getBoolean("recommended"))
                        {
                            isRecommended = true ;

                            size = jsonObject.getString("region") +" "+jsonObject.getString("cup");

                            layout_recommended.setVisibility(View.VISIBLE);
                            tv_not_recommended.setVisibility(View.GONE);

                            tv_add_cart.setText(getResources().getString(R.string.footwear_result_add_to_cart));

                            tv_size_result.setText(size);
                        }

                        else
                        {
                            isRecommended = false ;

                            layout_recommended.setVisibility(View.GONE);

                            tv_not_recommended.setVisibility(View.VISIBLE);

                            tv_add_cart.setText(getResources().getString(R.string.footwear_result_continue));
                        }

                        Log.e("brandNotListed", String.valueOf(brandNotListed));
                        Log.e("tellMore", String.valueOf(tellMore));

                        if (brandNotListed)
                        {
                            layout_edit.setVisibility(View.VISIBLE);
                            layout_accurate_fit.setVisibility(View.GONE);
                            layout_tell_more.setVisibility(View.GONE);

                            tellMore = false;
                        }
                        else if (tellMore)
                        {
                            if (wired && sideWireFit.equals("0")  || !wired && strapsFit.equals("0"))
                            {
                                layout_edit.setVisibility(View.GONE);
                                layout_accurate_fit.setVisibility(View.VISIBLE);
                                layout_tell_more.setVisibility(View.GONE);
                            }
                            else
                            {
                                layout_edit.setVisibility(View.VISIBLE);
                                layout_accurate_fit.setVisibility(View.GONE);
                                layout_tell_more.setVisibility(View.GONE);
                            }
                        }
                        else
                        {
                            tellMore = false;
                            layout_edit.setVisibility(View.GONE);
                            layout_accurate_fit.setVisibility(View.GONE);
                            layout_tell_more.setVisibility(View.VISIBLE);
                        }
                    }

                    else if (statusCode == 500)
                    {
                        layout_bra_profile.setVisibility(View.GONE);
                        layout_loading.setVisibility(View.GONE);
                        layout_brand.setVisibility(View.VISIBLE);
                        progress(1);
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
                    if(statusCode ==200)
                    {
                        JSONObject userInfoObject = new JSONObject(result);

                        if (userInfoObject.has("whois"))
                        {
                            Log.e("userInfoObject", String.valueOf(userInfoObject));

                            uID = userInfoObject.optString("uid");

                            if (userInfoObject.optString("whois").contains("female") && gender.equals("female") &&!new JSONObject(userInfoObject.optString("whois")).optString("female").equals(null) && !new JSONObject(userInfoObject.optString("whois")).optString("female").equals("{}"))
                            {
                                JSONObject whoisObject = new JSONObject(userInfoObject.optString("whois"));

                                String[] lingerie_array = new String[]{"Bra"};

                                JSONObject femaleObject = new JSONObject(whoisObject.optString("female"));

                                    String brand_rb = "";
                                    String cup_rb = "";
                                    String wired_rb = "";
                                    String region_rb = "";
                                    String band_rb = "";

                                    JSONArray femaleReferenceBrandsArray = new JSONArray(femaleObject.optString("referenceBrands"));

                                    if(femaleReferenceBrandsArray.length() > 0){
                                        JSONObject braOptions = null;
                                        JSONObject femaleReferenceBrandsObject = null;
                                        for (int i = 0; i<femaleReferenceBrandsArray.length(); i++){

                                            JSONObject referenceObject = femaleReferenceBrandsArray.getJSONObject(i);

                                            String category = referenceObject.optString("category");

                                            if(Arrays.asList(lingerie_array).contains(category))
                                            {
                                                femaleReferenceBrandsObject = referenceObject;
                                            }

                                            if(femaleReferenceBrandsObject != null){

                                                brand_rb = femaleReferenceBrandsObject.optString("brand");
                                                band_rb = femaleReferenceBrandsObject.optString("band");
                                                cup_rb = femaleReferenceBrandsObject.optString("cup");
                                                region_rb = femaleReferenceBrandsObject.optString("region");
                                                wired_rb = femaleReferenceBrandsObject.optString("wired");

                                            }


                                            brand = brand_rb;
                                            band = band_rb;
                                            cup = cup_rb;
                                            region = region_rb;
                                            wired = Boolean.parseBoolean(wired_rb);

                                            Log.e("brand_rb",brand);
                                            Log.e("band_rb",band);
                                            Log.e("cup_rb",cup);
                                            Log.e("region_rb",region);
                                            Log.e("wired_rb", String.valueOf(wired));


                                            if (femaleObject.has("braOptions"))
                                            {
                                                braOptions = new JSONObject(femaleObject.optString("braOptions"));

                                                    if (referenceObject.has("wired"))
                                                    {

                                                        bandFit = braOptions.optString("bandFit");
                                                        cupFit = braOptions.optString("cupFit");
                                                        strapsFit = braOptions.optString("strapsFit");
                                                        braAge = braOptions.optString("braAge");
                                                        sameBra = Boolean.parseBoolean(braOptions.optString("sameBra"));
                                                        tightHook = Boolean.parseBoolean(braOptions.optString("tightHook"));
                                                        looseHook = Boolean.parseBoolean(braOptions.optString("looseHook"));
                                                        braStyle = braOptions.optString("braStyle");
                                                        frontWireFit = braOptions.optString("frontWireFit");
                                                        sideWireFit = braOptions.optString("sideWireFit");
                                                        gaps = braOptions.optString("gaps");
                                                        tightStrap = Boolean.parseBoolean(braOptions.optString("tightStrap"));
                                                    }
                                                    else
                                                    {
                                                        wired = Boolean.parseBoolean(braOptions.optString("wired"));
                                                        cup = braOptions.optString("cup");
                                                        region = braOptions.optString("region");

                                                        bandFit = braOptions.optString("bandFit");
                                                        cupFit = braOptions.optString("cupFit");
                                                        strapsFit = braOptions.optString("strapsFit");
                                                        band = braOptions.optString("band");
                                                        braAge = braOptions.optString("braAge");
                                                        sameBra = Boolean.parseBoolean(braOptions.optString("sameBra"));
                                                        tightHook = Boolean.parseBoolean(braOptions.optString("tightHook"));
                                                        looseHook = Boolean.parseBoolean(braOptions.optString("looseHook"));
                                                        braStyle = braOptions.optString("braStyle");
                                                        frontWireFit = braOptions.optString("frontWireFit");
                                                        sideWireFit = braOptions.optString("sideWireFit");
                                                        gaps = braOptions.optString("gaps");
                                                        tightStrap = Boolean.parseBoolean(braOptions.optString("tightStrap"));
                                                    }

                                                Log.e("bandFit",bandFit);
                                                Log.e("strapsFit",strapsFit);
                                                Log.e("band",band);
                                                Log.e("region",region);
                                                Log.e("braAge",braAge);
                                                Log.e("sameBra", String.valueOf(sameBra));
                                                Log.e("tightHook", String.valueOf(tightHook));
                                                Log.e("looseHook", String.valueOf(looseHook));
                                                Log.e("braStyle",braStyle);
                                                Log.e("frontWireFit",frontWireFit);
                                                Log.e("sideWireFit",sideWireFit);
                                                Log.e("gaps",gaps);
                                                Log.e("tightStrap", String.valueOf(tightStrap));


                                            }

                                        }

                                        layout_brand.setVisibility(View.GONE);


                                        if (!wired_rb.equals("") && !band_rb.equals("") && !femaleObject.has("braOptions"))
                                        {

                                            Log.e("Result","Brand Flow without extra data");

                                            if (altId.equals(""))
                                            {
                                                getFinalSize(uID,brand,band,cup,region, wired);
                                            }
                                            else
                                            {
                                                getFinalSize(altId,brand,band,cup,region, wired);
                                            }
                                        }

                                        else if (!wired_rb.equals("") && !band_rb.equals("") && !cup_rb.equals(""))
                                        {

                                            Log.e("Result","Brand Flow with extra data");

                                            brandNotListed = false;
                                            tellMore = true;

                                            setBandFeel(bandFit);
                                            setBraAge(braAge);
                                            setCupFit(cupFit);
                                            setGaps(gaps);
                                            setBraStyle(braStyle);
                                            setStraps(strapsFit);
                                            setFrontWire(frontWireFit);
                                            setSideWire(sideWireFit);


                                            if (bandFit.equals("1"))
                                            {
                                                if (looseHook)
                                                {
                                                    tv_bra_looser_hook_yes.setTextColor(getResources().getColor(R.color.white));
                                                    tv_bra_looser_hook_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                                                }
                                                else
                                                {
                                                    tv_bra_looser_hook_no.setTextColor(getResources().getColor(R.color.white));
                                                    tv_bra_looser_hook_no.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                                                }
                                            }


                                            if (bandFit.equals("3"))
                                            {
                                                if (sameBra)
                                                {
                                                    tv_isOld_yes.setTextColor(getResources().getColor(R.color.white));
                                                    tv_isOld_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                                                }
                                                else if (tightHook)
                                                {
                                                    tv_isOld_no.setTextColor(getResources().getColor(R.color.white));
                                                    tv_isOld_no.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                                                    tv_bra_tight_hook_yes.setTextColor(getResources().getColor(R.color.white));
                                                    tv_bra_tight_hook_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                                                    tv_bra_sizes_text.setText(getResources().getString(R.string.bra_tighter_hook));

                                                    layout_bra_tight_hook_button.setVisibility(View.VISIBLE);
                                                    layout_bra_loose_hook_button.setVisibility(View.GONE);
                                                    layout_bra_isOld_button.setVisibility(View.GONE);
                                                }
                                                else
                                                {
                                                    tv_bra_tight_hook_no.setTextColor(getResources().getColor(R.color.white));
                                                    tv_bra_tight_hook_no.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                                                }
                                            }


                                            if (braAge.equals("1") || braAge.equals("2"))
                                            {
                                                if (tightHook)
                                                {
                                                    tv_old_bra_page_yes.setTextColor(getResources().getColor(R.color.white));
                                                    tv_old_bra_page_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                                                }
                                                else
                                                {
                                                    tv_old_bra_page_no.setTextColor(getResources().getColor(R.color.white));
                                                    tv_old_bra_page_no.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                                                }
                                            }




                                            if (gaps.equals("1"))
                                            {
                                                Log.e("gaps",gaps);
                                                Log.e("tightStrap", String.valueOf(tightStrap));

                                                if (tightStrap)
                                                {
                                                    Log.e("INside tightStrap", String.valueOf(tightStrap));
                                                    tv_gap_yes.setTextColor(getResources().getColor(R.color.white));
                                                    tv_gap_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                                                }
                                                else
                                                {
                                                    Log.e("INside tightStrap", String.valueOf(tightStrap));
                                                    tv_gap_no.setTextColor(getResources().getColor(R.color.white));
                                                    tv_gap_no.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                                                }
                                            }


                                            if (altId.equals(""))
                                            {
                                                getFinalSize(uID,brand,band,cup,region, wired);
                                            }
                                            else
                                            {
                                                getFinalSize(altId,brand,band,cup,region, wired);
                                            }
                                        }
                                        else if (!bandFit.equals("0") && !bandFit.equals(""))
                                        {

                                            Log.e("Flow resume","Resume");

                                            tellMore = true;

                                            setBandFeel(bandFit);

                                            layout_band_feel.setVisibility(View.GONE);


                                            if (bandFit.equals("1"))
                                            {
                                                if (looseHook)
                                                {
                                                    tv_bra_looser_hook_yes.setTextColor(getResources().getColor(R.color.white));
                                                    tv_bra_looser_hook_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                                                }
                                                else
                                                {
                                                    tv_bra_looser_hook_no.setTextColor(getResources().getColor(R.color.white));
                                                    tv_bra_looser_hook_no.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                                                }
                                            }


                                            if (bandFit.equals("3"))
                                            {
                                                if (sameBra)
                                                {
                                                    tv_isOld_yes.setTextColor(getResources().getColor(R.color.white));
                                                    tv_isOld_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                                                }
                                                else if (tightHook)
                                                {
                                                    tv_isOld_no.setTextColor(getResources().getColor(R.color.white));
                                                    tv_isOld_no.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                                                    tv_bra_tight_hook_yes.setTextColor(getResources().getColor(R.color.white));
                                                    tv_bra_tight_hook_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                                                    tv_bra_sizes_text.setText(getResources().getString(R.string.bra_tighter_hook));

                                                    layout_bra_tight_hook_button.setVisibility(View.VISIBLE);
                                                    layout_bra_loose_hook_button.setVisibility(View.GONE);
                                                    layout_bra_isOld_button.setVisibility(View.GONE);
                                                }
                                                else
                                                {
                                                    tv_bra_tight_hook_no.setTextColor(getResources().getColor(R.color.white));
                                                    tv_bra_tight_hook_no.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                                                }
                                            }


                                            if (bandFit.equals("3")  && braAge.equals("0") && sameBra)
                                            {
                                                layout_bra_old.setVisibility(View.VISIBLE);
                                                progress(4);
                                            }
                                            else
                                            {
                                                setBraAge(braAge);

                                                if (braAge.equals("1") || braAge.equals("2"))
                                                {
                                                    if (tightHook)
                                                    {
                                                        tv_old_bra_page_yes.setTextColor(getResources().getColor(R.color.white));
                                                        tv_old_bra_page_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                                                    }
                                                    else
                                                    {
                                                        tv_old_bra_page_no.setTextColor(getResources().getColor(R.color.white));
                                                        tv_old_bra_page_no.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                                                    }
                                                }

                                                layout_bra_old.setVisibility(View.GONE);

                                                if (!cupFit.equals("0"))
                                                {
                                                    setCupFit(cupFit);

                                                    layout_cup_fit.setVisibility(View.GONE);

                                                    if(cupFit.equals("3") && gaps.equals("0"))
                                                    {
                                                        layout_gaps.setVisibility(View.VISIBLE);

                                                        progress(6);
                                                    }
                                                    else
                                                    {
                                                        setGaps(gaps);

                                                        if (gaps.equals("1"))
                                                        {
                                                            if (tightStrap)
                                                            {
                                                                tv_gap_yes.setTextColor(getResources().getColor(R.color.white));
                                                                tv_gap_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                                                            }
                                                            else
                                                            {
                                                                tv_gap_no.setTextColor(getResources().getColor(R.color.white));
                                                                tv_gap_no.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                                                            }
                                                        }

                                                        layout_gaps.setVisibility(View.GONE);

                                                        if (gaps.equals("3") && braStyle.equals("0"))
                                                        {
                                                            layout_bra_style.setVisibility(View.VISIBLE);
                                                            progress(7);
                                                        }
                                                        else
                                                        {
                                                            setBraStyle(braStyle);

                                                            layout_bra_style.setVisibility(View.GONE);

                                                            if (!strapsFit.equals("0"))
                                                            {
                                                                setStraps(strapsFit);

                                                                layout_strap_fit.setVisibility(View.GONE);

                                                                if (wired)
                                                                {


                                                                    if (!frontWireFit.equals("0"))
                                                                    {
                                                                        setFrontWire(frontWireFit);

                                                                        layout_underwire_front.setVisibility(View.GONE);

                                                                        if (sideWireFit.equals("0"))
                                                                        {
                                                                            layout_underwire_side.setVisibility(View.VISIBLE);
                                                                            progress(10);
                                                                        }
                                                                        else
                                                                        {
                                                                            setSideWire(sideWireFit);

                                                                            layout_underwire_side.setVisibility(View.GONE);

                                                                            Log.e("Flow resume result","Unwired");

                                                                            if (altId.equals(""))
                                                                            {
                                                                                getFinalSize(uID,brand,band,cup,region,wired);
                                                                            }
                                                                            else
                                                                            {
                                                                                getFinalSize(altId,brand,band,cup,region,wired);
                                                                            }
                                                                        }

                                                                    }
                                                                    else
                                                                    {
                                                                        layout_underwire_front.setVisibility(View.VISIBLE);
                                                                        progress(9);
                                                                    }

                                                                }
                                                                else
                                                                {

                                                                    Log.e("Flow resume result","Unwired");

                                                                    if (altId.equals(""))
                                                                    {
                                                                        getFinalSize(uID,brand,band,cup,region,wired);
                                                                    }
                                                                    else
                                                                    {
                                                                        getFinalSize(altId,brand,band,cup,region,wired);
                                                                    }
                                                                }
                                                            }
                                                            else
                                                            {
                                                                layout_strap_fit.setVisibility(View.VISIBLE);
                                                                progress(8);
                                                            }
                                                        }

                                                    }
                                                }
                                                else
                                                {
                                                    layout_cup_fit.setVisibility(View.VISIBLE);
                                                    progress(5);
                                                }
                                            }
                                        }
                                        else
                                        {
                                            layout_brand.setVisibility(View.VISIBLE);
                                            progress(1);
                                        }

                                    }
                            }
                            else
                            {
                                layout_brand.setVisibility(View.VISIBLE);
                                progress(1);
                            }
                        }

                    }
                    else if (statusCode == 500)
                    {
                        layout_brand.setVisibility(View.VISIBLE);
                        progress(1);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
        }
    }


    public void checkBraSize(String band, String cup, String wired)
    {
        if (!band.equals("") && !cup.equals("") && !wired.equals(""))
        {
            tv_bra_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
        }
        else
        {
            tv_bra_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));

        }
    }



    public void checkBraSizeNoBrand(String band, String cup, String wired)
    {
        if (!band.equals("") && !cup.equals("") && !wired.equals(""))
        {
            tv_bra_continue_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
        }
        else
        {
            tv_bra_continue_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));

        }
    }


    public void setDefaultBrands()
    {

        if (gender.equals("female"))
        {
            tv_brand_1.setText(getResources().getString(R.string.bra_brand_cotton_on_body));
            tv_brand_2.setText(getResources().getString(R.string.bra_brand_gap));
            tv_brand_3.setText(getResources().getString(R.string.bra_brand_ck));
            tv_brand_4.setVisibility(View.GONE);
            tv_brand_5.setVisibility(View.GONE);
        }
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

    public void setBraSizes(String result , String region)
    {

         Log.e("Result",result);
         Log.e("region",region);

         ArrayList<String> bandArrayList = new ArrayList<>();
         ArrayList<String> cupArrayList = new ArrayList<>();



        try {

                JSONArray regular = new JSONArray(result);

                for (int i = 0; i < regular.length(); i++)
                {
                    JSONObject obj = regular.getJSONObject(i);

                    if (obj.getString("region").equals(region))
                    {
                        JSONArray charts = new JSONArray(obj.getString("charts"));

                        for (int j = 0; j < charts.length(); j++)
                        {
                            JSONObject chartsobj = charts.getJSONObject(j);

                            if (!bandArrayList.contains(chartsobj.getString("band")))
                            {
                                bandArrayList.add(chartsobj.getString("band"));
                            }
                        }

                        for (int k = 0; k < charts.length(); k++)
                        {
                            JSONObject chartsobj = charts.getJSONObject(k);

                            if (!cupArrayList.contains(chartsobj.getString("cup")))
                            {
                                cupArrayList.add(chartsobj.getString("cup"));
                            }
                        }
                    }
                }

                setBraBand(bandArrayList);
                setBraCup(cupArrayList);


                Log.e("cupArrayList", String.valueOf(cupArrayList));
                Log.e("bandArrayList", String.valueOf(bandArrayList));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void setBraBand(ArrayList list)
    {
        for (int i = 0; i < list.size(); i++)
        {
            if (i == 0)
            {
                tv_band_1.setText(list.get(i).toString());
                layout_band_1.setVisibility(View.VISIBLE);
            }
            if (i == 1)
            {
                tv_band_2.setText(list.get(i).toString());
                layout_band_2.setVisibility(View.VISIBLE);
            }
            if (i == 2)
            {
                tv_band_3.setText(list.get(i).toString());
                layout_band_3.setVisibility(View.VISIBLE);
            }
            if (i == 3)
            {
                tv_band_4.setText(list.get(i).toString());
                layout_band_4.setVisibility(View.VISIBLE);
            }
            if (i == 4)
            {
                tv_band_5.setText(list.get(i).toString());
                layout_band_5.setVisibility(View.VISIBLE);
            }
            if (i == 5)
            {
                tv_band_6.setText(list.get(i).toString());
                layout_band_6.setVisibility(View.VISIBLE);

                layout_band_next_1.setVisibility(View.VISIBLE);
                layout_band_back_1.setVisibility(View.VISIBLE);
            }
            if (i == 6)
            {
                tv_band_7.setText(list.get(i).toString());
                layout_band_7.setVisibility(View.VISIBLE);
            }
            if (i == 7)
            {
                tv_band_8.setText(list.get(i).toString());
                layout_band_8.setVisibility(View.VISIBLE);
            }
            if (i == 8)
            {
                tv_band_9.setText(list.get(i).toString());
                layout_band_9.setVisibility(View.VISIBLE);
            }
            if (i == 9)
            {
                tv_band_10.setText(list.get(i).toString());
                layout_band_10.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setBraCup(ArrayList list)
    {
        for (int i = 0; i < list.size(); i++)
        {
            if (i == 0)
            {
                tv_cup_1.setText(list.get(i).toString());
                layout_cup_1.setVisibility(View.VISIBLE);
            }
            if (i == 1)
            {
                tv_cup_2.setText(list.get(i).toString());
                layout_cup_2.setVisibility(View.VISIBLE);
            }
            if (i == 2)
            {
                tv_cup_3.setText(list.get(i).toString());
                layout_cup_3.setVisibility(View.VISIBLE);
            }
            if (i == 3)
            {
                tv_cup_4.setText(list.get(i).toString());
                layout_cup_4.setVisibility(View.VISIBLE);
            }
            if (i == 4)
            {
                tv_cup_5.setText(list.get(i).toString());
                layout_cup_5.setVisibility(View.VISIBLE);
            }
            if (i == 5)
            {
                tv_cup_6.setText(list.get(i).toString());
                layout_cup_6.setVisibility(View.VISIBLE);

                layout_cup_back.setVisibility(View.VISIBLE);
                layout_cup_next.setVisibility(View.VISIBLE);
            }
            if (i == 6)
            {
                tv_cup_7.setText(list.get(i).toString());
                layout_cup_7.setVisibility(View.VISIBLE);
            }
            if (i == 7)
            {
                tv_cup_8.setText(list.get(i).toString());
                layout_cup_8.setVisibility(View.VISIBLE);
            }
            if (i == 8)
            {
                tv_cup_9.setText(list.get(i).toString());
                layout_cup_9.setVisibility(View.VISIBLE);
            }
            if (i == 9)
            {
                tv_cup_10.setText(list.get(i).toString());
                layout_cup_10.setVisibility(View.VISIBLE);
            }
            if (i == 10)
            {
                tv_cup_11.setText(list.get(i).toString());
                layout_cup_11.setVisibility(View.VISIBLE);
            }
            if (i == 11)
            {
                tv_cup_12.setText(list.get(i).toString());
                layout_cup_12.setVisibility(View.VISIBLE);
            }
        }
    }


    public void setAU(String braBand , String braCup)
    {


        clearAllBandNoBrand();
        clearAllCupNoBrand();


        switch (braBand)
        {
            case "8":
                tv_band_1_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_1_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "10":
                tv_band_2_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_2_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "12":
                tv_band_3_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_3_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "14":
                tv_band_4_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_4_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "16":
                tv_band_5_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_5_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "18":
                tv_band_6_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_6_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "20":
                tv_band_7_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_7_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "22":
                tv_band_8_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_8_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }



        switch (braCup)
        {
            case "aa":
                tv_cup_1_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_1_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "a":
                tv_cup_2_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_2_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "b":
                tv_cup_3_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_3_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "c":
                tv_cup_4_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_4_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "d":
                tv_cup_5_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_5_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "dd":
                tv_cup_6_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_6_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "e":
                tv_cup_7_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_7_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "f":
                tv_cup_8_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_8_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "g":
                tv_cup_9_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_9_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "h":
                tv_cup_10_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_10_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "i":
                tv_cup_11_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_11_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }


        tv_band_1_noBrand.setText(getResources().getString(R.string.band_au_8));
        tv_band_2_noBrand.setText(getResources().getString(R.string.band_au_10));
        tv_band_3_noBrand.setText(getResources().getString(R.string.band_au_12));
        tv_band_4_noBrand.setText(getResources().getString(R.string.band_au_14));
        tv_band_5_noBrand.setText(getResources().getString(R.string.band_au_16));
        tv_band_6_noBrand.setText(getResources().getString(R.string.band_au_18));
        tv_band_7_noBrand.setText(getResources().getString(R.string.band_au_20));
        tv_band_8_noBrand.setText(getResources().getString(R.string.band_au_22));
        layout_band_9_noBrand.setVisibility(View.INVISIBLE);
        layout_band_10_noBrand.setVisibility(View.INVISIBLE);
//        layout_band_11.setVisibility(View.INVISIBLE);
//        layout_band_12.setVisibility(View.INVISIBLE);


        layout_band_1_noBrand.setVisibility(View.VISIBLE);
        layout_band_2_noBrand.setVisibility(View.VISIBLE);
        layout_band_3_noBrand.setVisibility(View.VISIBLE);
        layout_band_4_noBrand.setVisibility(View.VISIBLE);
        layout_band_5_noBrand.setVisibility(View.VISIBLE);
        layout_band_6_noBrand.setVisibility(View.VISIBLE);
        layout_band_7_noBrand.setVisibility(View.VISIBLE);
        layout_band_8_noBrand.setVisibility(View.VISIBLE);


        tv_cup_1_noBrand.setText(getResources().getString(R.string.cup_au_1));
        tv_cup_2_noBrand.setText(getResources().getString(R.string.cup_au_2));
        tv_cup_3_noBrand.setText(getResources().getString(R.string.cup_au_3));
        tv_cup_4_noBrand.setText(getResources().getString(R.string.cup_au_4));
        tv_cup_5_noBrand.setText(getResources().getString(R.string.cup_au_5));
        tv_cup_6_noBrand.setText(getResources().getString(R.string.cup_au_6));
        tv_cup_7_noBrand.setText(getResources().getString(R.string.cup_au_7));
        tv_cup_8_noBrand.setText(getResources().getString(R.string.cup_au_8));
        tv_cup_9_noBrand.setText(getResources().getString(R.string.cup_au_9));
        tv_cup_10_noBrand.setText(getResources().getString(R.string.cup_au_10));
        tv_cup_11_noBrand.setText(getResources().getString(R.string.cup_au_11));
        layout_cup_12_noBrand.setVisibility(View.INVISIBLE);
//        layout_cup_13.setVisibility(View.INVISIBLE);


        tv_au.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
        tv_eu.setBackgroundColor(getResources().getColor(R.color.white));
        tv_fr.setBackgroundColor(getResources().getColor(R.color.white));
        tv_uk.setBackgroundColor(getResources().getColor(R.color.white));
        tv_us.setBackgroundColor(getResources().getColor(R.color.white));

        tv_type_size_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

    }



    public void setEU(String braBand , String braCup)
    {
        tv_band_1_noBrand.setText(getResources().getString(R.string.band_eu_8));
        tv_band_2_noBrand.setText(getResources().getString(R.string.band_eu_10));
        tv_band_3_noBrand.setText(getResources().getString(R.string.band_eu_12));
        tv_band_4_noBrand.setText(getResources().getString(R.string.band_eu_14));
        tv_band_5_noBrand.setText(getResources().getString(R.string.band_eu_16));
        tv_band_6_noBrand.setText(getResources().getString(R.string.band_eu_18));
        tv_band_7_noBrand.setText(getResources().getString(R.string.band_eu_20));
        tv_band_8_noBrand.setText(getResources().getString(R.string.band_eu_22));
        layout_band_9_noBrand.setVisibility(View.INVISIBLE);
        layout_band_10_noBrand.setVisibility(View.INVISIBLE);
//        layout_band_11.setVisibility(View.INVISIBLE);
//        layout_band_12.setVisibility(View.INVISIBLE);


        layout_band_1_noBrand.setVisibility(View.VISIBLE);
        layout_band_2_noBrand.setVisibility(View.VISIBLE);
        layout_band_3_noBrand.setVisibility(View.VISIBLE);
        layout_band_4_noBrand.setVisibility(View.VISIBLE);
        layout_band_5_noBrand.setVisibility(View.VISIBLE);
        layout_band_6_noBrand.setVisibility(View.VISIBLE);
        layout_band_7_noBrand.setVisibility(View.VISIBLE);
        layout_band_8_noBrand.setVisibility(View.VISIBLE);

        tv_cup_1_noBrand.setText(getResources().getString(R.string.cup_eu_1));
        tv_cup_2_noBrand.setText(getResources().getString(R.string.cup_eu_2));
        tv_cup_3_noBrand.setText(getResources().getString(R.string.cup_eu_3));
        tv_cup_4_noBrand.setText(getResources().getString(R.string.cup_eu_4));
        tv_cup_5_noBrand.setText(getResources().getString(R.string.cup_eu_5));
        tv_cup_6_noBrand.setText(getResources().getString(R.string.cup_eu_6));
        tv_cup_7_noBrand.setText(getResources().getString(R.string.cup_eu_7));
        tv_cup_8_noBrand.setText(getResources().getString(R.string.cup_eu_8));
        tv_cup_9_noBrand.setText(getResources().getString(R.string.cup_eu_9));
        tv_cup_10_noBrand.setText(getResources().getString(R.string.cup_eu_10));
        tv_cup_11_noBrand.setText(getResources().getString(R.string.cup_eu_11));
        layout_cup_12_noBrand.setVisibility(View.INVISIBLE);
//        layout_cup_13.setVisibility(View.INVISIBLE);




        switch (braBand)
        {
            case "65":
                tv_band_1_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_1_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "70":
                tv_band_2_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_2_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "75":
                tv_band_3_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_3_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "80":
                tv_band_4_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_4_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "85":
                tv_band_5_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_5_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "90":
                tv_band_6_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_6_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "95":
                tv_band_7_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_7_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "100":
                tv_band_8_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_8_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }



        switch (braCup)
        {
            case "a":
                tv_cup_1_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_1_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "b":
                tv_cup_2_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_2_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "c":
                tv_cup_3_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_3_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "d":
                tv_cup_4_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_4_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "e":
                tv_cup_5_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_5_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "f":
                tv_cup_6_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_6_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "g":
                tv_cup_7_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_7_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "h":
                tv_cup_8_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_8_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "i":
                tv_cup_9_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_9_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "j":
                tv_cup_10_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_10_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "k":
                tv_cup_11_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_11_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }


        tv_au.setBackgroundColor(getResources().getColor(R.color.white));
        tv_eu.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
        tv_fr.setBackgroundColor(getResources().getColor(R.color.white));
        tv_uk.setBackgroundColor(getResources().getColor(R.color.white));
        tv_us.setBackgroundColor(getResources().getColor(R.color.white));

        tv_type_size_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

    }


    public void setFR(String braBand , String braCup)
    {
        tv_band_1_noBrand.setText(getResources().getString(R.string.band_fr_8));
        tv_band_2_noBrand.setText(getResources().getString(R.string.band_fr_10));
        tv_band_3_noBrand.setText(getResources().getString(R.string.band_fr_12));
        tv_band_4_noBrand.setText(getResources().getString(R.string.band_fr_14));
        tv_band_5_noBrand.setText(getResources().getString(R.string.band_fr_16));
        tv_band_6_noBrand.setText(getResources().getString(R.string.band_fr_18));
        tv_band_7_noBrand.setText(getResources().getString(R.string.band_fr_20));
        tv_band_8_noBrand.setText(getResources().getString(R.string.band_fr_22));
        layout_band_9_noBrand.setVisibility(View.INVISIBLE);
        layout_band_10_noBrand.setVisibility(View.INVISIBLE);
//        layout_band_11.setVisibility(View.INVISIBLE);
//        layout_band_12.setVisibility(View.INVISIBLE);

        layout_band_1_noBrand.setVisibility(View.VISIBLE);
        layout_band_2_noBrand.setVisibility(View.VISIBLE);
        layout_band_3_noBrand.setVisibility(View.VISIBLE);
        layout_band_4_noBrand.setVisibility(View.VISIBLE);
        layout_band_5_noBrand.setVisibility(View.VISIBLE);
        layout_band_6_noBrand.setVisibility(View.VISIBLE);
        layout_band_7_noBrand.setVisibility(View.VISIBLE);
        layout_band_8_noBrand.setVisibility(View.VISIBLE);

        tv_cup_1_noBrand.setText(getResources().getString(R.string.cup_fr_1));
        tv_cup_2_noBrand.setText(getResources().getString(R.string.cup_fr_2));
        tv_cup_3_noBrand.setText(getResources().getString(R.string.cup_fr_3));
        tv_cup_4_noBrand.setText(getResources().getString(R.string.cup_fr_4));
        tv_cup_5_noBrand.setText(getResources().getString(R.string.cup_fr_5));
        tv_cup_6_noBrand.setText(getResources().getString(R.string.cup_fr_6));
        tv_cup_7_noBrand.setText(getResources().getString(R.string.cup_fr_7));
        tv_cup_8_noBrand.setText(getResources().getString(R.string.cup_fr_8));
        tv_cup_9_noBrand.setText(getResources().getString(R.string.cup_fr_9));
        tv_cup_10_noBrand.setText(getResources().getString(R.string.cup_fr_10));
        tv_cup_11_noBrand.setText(getResources().getString(R.string.cup_fr_11));
        layout_cup_12_noBrand.setVisibility(View.INVISIBLE);
//        layout_cup_13.setVisibility(View.INVISIBLE);




        switch (braBand)
        {
            case "80":
                tv_band_1_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_1_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "85":
                tv_band_2_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_2_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "90":
                tv_band_3_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_3_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "95":
                tv_band_4_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_4_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "100":
                tv_band_5_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_5_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "105":
                tv_band_6_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_6_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "110":
                tv_band_7_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_7_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "115":
                tv_band_8_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_8_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }



        switch (braCup)
        {
            case "a":
                tv_cup_1_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_1_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "b":
                tv_cup_2_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_2_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "c":
                tv_cup_3_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_3_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "d":
                tv_cup_4_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_4_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "e":
                tv_cup_5_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_5_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "f":
                tv_cup_6_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_6_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "g":
                tv_cup_7_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_7_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "h":
                tv_cup_8_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_8_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "i":
                tv_cup_9_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_9_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "j":
                tv_cup_10_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_10_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "k":
                tv_cup_11_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_11_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }



        tv_au.setBackgroundColor(getResources().getColor(R.color.white));
        tv_eu.setBackgroundColor(getResources().getColor(R.color.white));
        tv_fr.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
        tv_uk.setBackgroundColor(getResources().getColor(R.color.white));
        tv_us.setBackgroundColor(getResources().getColor(R.color.white));

        tv_type_size_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

    }



    public void setUK(String braBand , String braCup)
    {
        tv_band_1_noBrand.setText(getResources().getString(R.string.band_uk_8));
        tv_band_2_noBrand.setText(getResources().getString(R.string.band_uk_10));
        tv_band_3_noBrand.setText(getResources().getString(R.string.band_uk_12));
        tv_band_4_noBrand.setText(getResources().getString(R.string.band_uk_14));
        tv_band_5_noBrand.setText(getResources().getString(R.string.band_uk_16));
        tv_band_6_noBrand.setText(getResources().getString(R.string.band_uk_18));
        tv_band_7_noBrand.setText(getResources().getString(R.string.band_uk_20));
        tv_band_8_noBrand.setText(getResources().getString(R.string.band_uk_22));
        layout_band_9_noBrand.setVisibility(View.INVISIBLE);
        layout_band_10_noBrand.setVisibility(View.INVISIBLE);
//        layout_band_11.setVisibility(View.INVISIBLE);
//        layout_band_12.setVisibility(View.INVISIBLE);

        layout_band_1_noBrand.setVisibility(View.VISIBLE);
        layout_band_2_noBrand.setVisibility(View.VISIBLE);
        layout_band_3_noBrand.setVisibility(View.VISIBLE);
        layout_band_4_noBrand.setVisibility(View.VISIBLE);
        layout_band_5_noBrand.setVisibility(View.VISIBLE);
        layout_band_6_noBrand.setVisibility(View.VISIBLE);
        layout_band_7_noBrand.setVisibility(View.VISIBLE);
        layout_band_8_noBrand.setVisibility(View.VISIBLE);


        tv_cup_1_noBrand.setText(getResources().getString(R.string.cup_uk_1));
        tv_cup_2_noBrand.setText(getResources().getString(R.string.cup_uk_2));
        tv_cup_3_noBrand.setText(getResources().getString(R.string.cup_uk_3));
        tv_cup_4_noBrand.setText(getResources().getString(R.string.cup_uk_4));
        tv_cup_5_noBrand.setText(getResources().getString(R.string.cup_uk_5));
        tv_cup_6_noBrand.setText(getResources().getString(R.string.cup_uk_6));
        tv_cup_7_noBrand.setText(getResources().getString(R.string.cup_uk_7));
        tv_cup_8_noBrand.setText(getResources().getString(R.string.cup_uk_8));
        tv_cup_9_noBrand.setText(getResources().getString(R.string.cup_uk_9));
        tv_cup_10_noBrand.setText(getResources().getString(R.string.cup_uk_10));
        tv_cup_11_noBrand.setText(getResources().getString(R.string.cup_uk_11));
        layout_cup_12_noBrand.setVisibility(View.INVISIBLE);
//        layout_cup_13.setVisibility(View.INVISIBLE);




        switch (braBand)
        {
            case "30":
                tv_band_1_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_1_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));

                break;

            case "32":
                tv_band_2_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_2_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));

                break;

            case "34":
                tv_band_3_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_3_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "36":
                tv_band_4_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_4_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "38":
                tv_band_5_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_5_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "40":
                tv_band_6_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_6_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "42":
                tv_band_7_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_7_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "44":
                tv_band_8_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_8_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }



        switch (braCup)
        {
            case "a":
                tv_cup_1_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_1_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "b":
                tv_cup_2_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_2_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "c":
                tv_cup_3_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_3_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "d":
                tv_cup_4_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_4_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "dd":
                tv_cup_5_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_5_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "e":
                tv_cup_6_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_6_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "f":
                tv_cup_7_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_7_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "g":
                tv_cup_8_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_8_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "h":
                tv_cup_9_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_9_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "i":
                tv_cup_10_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_10_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "j":
                tv_cup_11_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_11_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }

        tv_au.setBackgroundColor(getResources().getColor(R.color.white));
        tv_eu.setBackgroundColor(getResources().getColor(R.color.white));
        tv_fr.setBackgroundColor(getResources().getColor(R.color.white));
        tv_uk.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));
        tv_us.setBackgroundColor(getResources().getColor(R.color.white));

        tv_type_size_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
    }



    public void setUS(String braBand , String braCup)
    {
        tv_band_1_noBrand.setText(getResources().getString(R.string.band_us_8));
        tv_band_2_noBrand.setText(getResources().getString(R.string.band_us_10));
        tv_band_3_noBrand.setText(getResources().getString(R.string.band_us_12));
        tv_band_4_noBrand.setText(getResources().getString(R.string.band_us_14));
        tv_band_5_noBrand.setText(getResources().getString(R.string.band_us_16));
        tv_band_6_noBrand.setText(getResources().getString(R.string.band_us_18));
        tv_band_7_noBrand.setText(getResources().getString(R.string.band_us_20));
        tv_band_8_noBrand.setText(getResources().getString(R.string.band_us_22));
        layout_band_9_noBrand.setVisibility(View.INVISIBLE);
        layout_band_10_noBrand.setVisibility(View.INVISIBLE);
//        layout_band_11.setVisibility(View.INVISIBLE);
//        layout_band_12.setVisibility(View.INVISIBLE);


        tv_cup_1_noBrand.setText(getResources().getString(R.string.cup_us_1));
        tv_cup_2_noBrand.setText(getResources().getString(R.string.cup_us_2));
        tv_cup_3_noBrand.setText(getResources().getString(R.string.cup_us_3));
        tv_cup_4_noBrand.setText(getResources().getString(R.string.cup_us_4));
        tv_cup_5_noBrand.setText(getResources().getString(R.string.cup_us_5));
        tv_cup_6_noBrand.setText(getResources().getString(R.string.cup_us_6));
        tv_cup_7_noBrand.setText(getResources().getString(R.string.cup_us_7));
        tv_cup_8_noBrand.setText(getResources().getString(R.string.cup_us_8));
        tv_cup_9_noBrand.setText(getResources().getString(R.string.cup_us_9));
        tv_cup_10_noBrand.setText(getResources().getString(R.string.cup_us_10));
        tv_cup_11_noBrand.setText(getResources().getString(R.string.cup_us_11));
        layout_cup_12_noBrand.setVisibility(View.INVISIBLE);
//        layout_cup_13.setVisibility(View.INVISIBLE);




        switch (braBand)
        {
            case "30":
                tv_band_1_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_1_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                layout_band_1_noBrand.setVisibility(View.VISIBLE);
                break;

            case "32":
                tv_band_2_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_2_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                layout_band_2_noBrand.setVisibility(View.VISIBLE);
                break;

            case "34":
                tv_band_3_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_3_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                layout_band_3_noBrand.setVisibility(View.VISIBLE);
                break;

            case "36":
                tv_band_4_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_4_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                layout_band_4_noBrand.setVisibility(View.VISIBLE);
                break;

            case "38":
                tv_band_5_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_5_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                layout_band_5_noBrand.setVisibility(View.VISIBLE);
                break;

            case "40":
                tv_band_6_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_6_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                layout_band_6_noBrand.setVisibility(View.VISIBLE);
                break;

            case "42":
                tv_band_7_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_7_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                layout_band_7_noBrand.setVisibility(View.VISIBLE);
                break;

            case "44":
                tv_band_8_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_band_8_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                layout_band_8_noBrand.setVisibility(View.VISIBLE);
                break;
        }



        switch (braCup)
        {
            case "a":
                tv_cup_1_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_1_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "b":
                tv_cup_2_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_2_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "c":
                tv_cup_3_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_3_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "d":
                tv_cup_4_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_4_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "dd":
                tv_cup_5_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_5_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "ddd":
                tv_cup_6_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_6_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "e":
                tv_cup_7_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_7_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "f":
                tv_cup_8_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_8_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "g":
                tv_cup_9_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_9_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "h":
                tv_cup_10_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_10_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;

            case "i":
                tv_cup_11_noBrand.setTextColor(getResources().getColor(R.color.color_text));
                layout_cup_11_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                break;
        }

        tv_au.setBackgroundColor(getResources().getColor(R.color.white));
        tv_eu.setBackgroundColor(getResources().getColor(R.color.white));
        tv_fr.setBackgroundColor(getResources().getColor(R.color.white));
        tv_uk.setBackgroundColor(getResources().getColor(R.color.white));
        tv_us.setBackgroundColor(getResources().getColor(R.color.color_button_selected_bg));

        tv_type_size_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

    }



    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
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


            case R.id.layout_band_1:

                clearAllBands();

                band  = tv_band_1.getText().toString();

                layout_band_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_1.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;


            case R.id.layout_band_2:

                clearAllBands();

                band  = tv_band_2.getText().toString();

                layout_band_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_2.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;


            case R.id.layout_band_3:

                clearAllBands();

                band  = tv_band_3.getText().toString();

                layout_band_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_3.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;


            case R.id.layout_band_4:

                clearAllBands();

                band  = tv_band_4.getText().toString();

                layout_band_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_4.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;


            case R.id.layout_band_5:

                clearAllBands();

                band  = tv_band_5.getText().toString();

                layout_band_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_5.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;


            case R.id.layout_band_6:

                clearAllBands();

                band  = tv_band_6.getText().toString();

                layout_band_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_6.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;


            case R.id.layout_band_7:

                clearAllBands();

                band  = tv_band_7.getText().toString();

                layout_band_7.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_7.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;


            case R.id.layout_band_8:

                clearAllBands();

                band  = tv_band_8.getText().toString();

                layout_band_8.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_8.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;


            case R.id.layout_band_9:

                clearAllBands();

                band  = tv_band_9.getText().toString();

                layout_band_9.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_9.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;


            case R.id.layout_band_10:

                clearAllBands();

                band  = tv_band_10.getText().toString();

                layout_band_10.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_10.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;

            case R.id.layout_cup_1:

                clearAllCup();

                cup  = tv_cup_1.getText().toString();

                layout_cup_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_1.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;

            case R.id.layout_cup_2:

                clearAllCup();

                cup  = tv_cup_2.getText().toString();

                layout_cup_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_2.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;

            case R.id.layout_cup_3:

                clearAllCup();

                cup  = tv_cup_3.getText().toString();

                layout_cup_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_3.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;

            case R.id.layout_cup_4:

                clearAllCup();

                cup  = tv_cup_4.getText().toString();

                layout_cup_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_4.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;

            case R.id.layout_cup_5:

                clearAllCup();

                cup  = tv_cup_5.getText().toString();

                layout_cup_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_5.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;

            case R.id.layout_cup_6:

                clearAllCup();

                cup  = tv_cup_6.getText().toString();

                layout_cup_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_6.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;

            case R.id.layout_cup_7:

                clearAllCup();

                cup  = tv_cup_7.getText().toString();

                layout_cup_7.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_7.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;

            case R.id.layout_cup_8:

                clearAllCup();

                cup  = tv_cup_8.getText().toString();

                layout_cup_8.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_8.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;

            case R.id.layout_cup_9:

                clearAllCup();

                cup  = tv_cup_9.getText().toString();

                layout_cup_9.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_9.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;

            case R.id.layout_cup_10:

                clearAllCup();

                cup  = tv_cup_10.getText().toString();

                layout_cup_10.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_10.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;

            case R.id.layout_cup_11:

                clearAllCup();

                cup  = tv_cup_11.getText().toString();

                layout_cup_11.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_11.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;

            case R.id.layout_cup_12:

                clearAllCup();

                cup  = tv_cup_12.getText().toString();

                layout_cup_12.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_12.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSize(band,cup,isWired);

                break;

            case R.id.tv_au:

                clearAllBandNoBrand();
                clearAllCupNoBrand();
                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                layout_cup_row1_noBrand.setVisibility(View.VISIBLE);
                layout_cup_row2_noBrand.setVisibility(View.GONE);
                layout_cup_row3_noBrand.setVisibility(View.GONE);

                region_noBrand = "AU";

                setAU("","");
                tv_type_size_noBrand.setText(getResources().getString(R.string.au));
                layout_sizeType_selection.setVisibility(View.GONE);


                break;



            case R.id.tv_eu:

                clearAllBandNoBrand();
                clearAllCupNoBrand();
                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                layout_cup_row1_noBrand.setVisibility(View.VISIBLE);
                layout_cup_row2_noBrand.setVisibility(View.GONE);
                layout_cup_row3_noBrand.setVisibility(View.GONE);

                region_noBrand = "EU";

                setEU("","");

                tv_type_size_noBrand.setText(getResources().getString(R.string.eu));
                layout_sizeType_selection.setVisibility(View.GONE);

                break;



            case R.id.tv_fr:

                clearAllBandNoBrand();
                clearAllCupNoBrand();
                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                layout_cup_row1_noBrand.setVisibility(View.VISIBLE);
                layout_cup_row2_noBrand.setVisibility(View.GONE);
                layout_cup_row3_noBrand.setVisibility(View.GONE);

                region_noBrand = "FR";

                setFR("","");
                tv_type_size_noBrand.setText(getResources().getString(R.string.fr));
                layout_sizeType_selection.setVisibility(View.GONE);

                break;

            case R.id.tv_uk:

                clearAllBandNoBrand();
                clearAllCupNoBrand();
                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                layout_cup_row1_noBrand.setVisibility(View.VISIBLE);
                layout_cup_row2_noBrand.setVisibility(View.GONE);
                layout_cup_row3_noBrand.setVisibility(View.GONE);

                region_noBrand = "UK";

                setUK("","");
                tv_type_size_noBrand.setText(getResources().getString(R.string.uk));
                layout_sizeType_selection.setVisibility(View.GONE);

                break;

            case R.id.tv_us:

                clearAllBandNoBrand();
                clearAllCupNoBrand();
                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                layout_cup_row1_noBrand.setVisibility(View.VISIBLE);
                layout_cup_row2_noBrand.setVisibility(View.GONE);
                layout_cup_row3_noBrand.setVisibility(View.GONE);

                region_noBrand = "US";

                setUS("","");
                tv_type_size_noBrand.setText(getResources().getString(R.string.us));
                layout_sizeType_selection.setVisibility(View.GONE);

                break;


            case R.id.layout_band_1_noBrand:

                clearAllBandNoBrand();

                band_noBrand  = tv_band_1_noBrand.getText().toString();

                layout_band_1_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_1_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;


            case R.id.layout_band_2_noBrand:

                clearAllBandNoBrand();

                band_noBrand  = tv_band_2_noBrand.getText().toString();

                layout_band_2_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_2_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;


            case R.id.layout_band_3_noBrand:

                clearAllBandNoBrand();

                band_noBrand  = tv_band_3_noBrand.getText().toString();

                layout_band_3_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_3_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;


            case R.id.layout_band_4_noBrand:

                clearAllBandNoBrand();

                band_noBrand  = tv_band_4_noBrand.getText().toString();

                layout_band_4_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_4_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;


            case R.id.layout_band_5_noBrand:

                clearAllBandNoBrand();

                band_noBrand  = tv_band_5_noBrand.getText().toString();

                layout_band_5_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_5_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;


            case R.id.layout_band_6_noBrand:

                clearAllBandNoBrand();

                band_noBrand  = tv_band_6_noBrand.getText().toString();

                layout_band_6_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_6_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;


            case R.id.layout_band_7_noBrand:

                clearAllBandNoBrand();

                band_noBrand  = tv_band_7_noBrand.getText().toString();

                layout_band_7_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_7_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;


            case R.id.layout_band_8_noBrand:

                clearAllBandNoBrand();

                band_noBrand  = tv_band_8_noBrand.getText().toString();

                layout_band_8_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_8_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;


            case R.id.layout_band_9_noBrand:

                clearAllBandNoBrand();

                band_noBrand  = tv_band_9_noBrand.getText().toString();

                layout_band_9_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_9_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;


            case R.id.layout_band_10_noBrand:

                clearAllBandNoBrand();

                band_noBrand  = tv_band_10_noBrand.getText().toString();

                layout_band_10_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_band_10_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;

            case R.id.layout_cup_1_noBrand:

                clearAllCupNoBrand();

                cup_noBrand  = tv_cup_1_noBrand.getText().toString();

                layout_cup_1_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_1_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;

            case R.id.layout_cup_2_noBrand:

                clearAllCupNoBrand();

                cup_noBrand  = tv_cup_2_noBrand.getText().toString();

                layout_cup_2_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_2_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;

            case R.id.layout_cup_3_noBrand:

                clearAllCupNoBrand();

                cup_noBrand  = tv_cup_3_noBrand.getText().toString();

                layout_cup_3_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_3_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;

            case R.id.layout_cup_4_noBrand:

                clearAllCupNoBrand();

                cup_noBrand  = tv_cup_4_noBrand.getText().toString();

                layout_cup_4_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_4_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;

            case R.id.layout_cup_5_noBrand:

                clearAllCupNoBrand();

                cup_noBrand  = tv_cup_5_noBrand.getText().toString();

                layout_cup_5_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_5_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;

            case R.id.layout_cup_6_noBrand:

                clearAllCupNoBrand();

                cup_noBrand  = tv_cup_6_noBrand.getText().toString();

                layout_cup_6_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_6_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;

            case R.id.layout_cup_7_noBrand:

                clearAllCupNoBrand();

                cup_noBrand  = tv_cup_7_noBrand.getText().toString();

                layout_cup_7_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_7_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;

            case R.id.layout_cup_8_noBrand:

                clearAllCupNoBrand();

                cup_noBrand  = tv_cup_8_noBrand.getText().toString();

                layout_cup_8_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_8_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;

            case R.id.layout_cup_9_noBrand:

                clearAllCupNoBrand();

                cup_noBrand  = tv_cup_9_noBrand.getText().toString();

                layout_cup_9_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_9_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;

            case R.id.layout_cup_10_noBrand:

                clearAllCupNoBrand();

                cup_noBrand  = tv_cup_10_noBrand.getText().toString();

                layout_cup_10_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_10_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;

            case R.id.layout_cup_11_noBrand:

                clearAllCupNoBrand();

                cup_noBrand  = tv_cup_11_noBrand.getText().toString();

                layout_cup_11_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_11_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;

            case R.id.layout_cup_12_noBrand:

                clearAllCupNoBrand();

                cup_noBrand  = tv_cup_12_noBrand.getText().toString();

                layout_cup_12_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_cup_12_noBrand.setTextColor(getResources().getColor(R.color.color_text));

                checkBraSizeNoBrand(band_noBrand,cup_noBrand,isWired_noBrand);

                break;

        }
    }


    public void clearAllBands()
    {
        band = "";

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

    }

    public void clearAllBandNoBrand()
    {

//        isEditFlow = false;
//
        tv_error_band_noBrand.setVisibility(View.GONE);

        band_noBrand = "";

        layout_band_1_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_1_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_2_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_2_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_3_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_3_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_4_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_4_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_5_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_5_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_6_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_6_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_7_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_7_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_8_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_8_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_9_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_9_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_band_10_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_band_10_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

//        layout_band_11_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
//        tv_band_11_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));
//
//        layout_band_12_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
//        tv_band_12_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

    }

    public void hideAllBands()
    {

        layout_band_1.setVisibility(View.INVISIBLE);

        layout_band_2.setVisibility(View.INVISIBLE);

        layout_band_3.setVisibility(View.INVISIBLE);

        layout_band_4.setVisibility(View.INVISIBLE);

        layout_band_5.setVisibility(View.INVISIBLE);

        layout_band_6.setVisibility(View.INVISIBLE);

        layout_band_7.setVisibility(View.INVISIBLE);

        layout_band_8.setVisibility(View.INVISIBLE);

        layout_band_9.setVisibility(View.INVISIBLE);

        layout_band_10.setVisibility(View.INVISIBLE);

        layout_band_next_1.setVisibility(View.INVISIBLE);
        layout_band_back_1.setVisibility(View.INVISIBLE);
    }


    public void clearAllCup()
    {

        tv_error_cup.setVisibility(View.GONE);

        cup = "";

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


    public void clearAllCupNoBrand()
    {

//        isEditFlow = false;
        tv_error_cup_noBrand.setVisibility(View.GONE);


        cup_noBrand = "";

        layout_cup_1_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_1_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_2_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_2_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_3_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_3_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_4_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_4_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_5_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_5_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_6_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_6_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_7_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_7_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_8_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_8_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_9_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_9_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_10_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_10_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_11_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_11_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_cup_12_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_cup_12_noBrand.setTextColor(getResources().getColor(R.color.color_text_grey));

    }




    public void hideAllCup()
    {

        layout_cup_1.setVisibility(View.INVISIBLE);

        layout_cup_2.setVisibility(View.INVISIBLE);

        layout_cup_3.setVisibility(View.INVISIBLE);

        layout_cup_4.setVisibility(View.INVISIBLE);

        layout_cup_5.setVisibility(View.INVISIBLE);

        layout_cup_6.setVisibility(View.INVISIBLE);

        layout_cup_7.setVisibility(View.INVISIBLE);

        layout_cup_8.setVisibility(View.INVISIBLE);

        layout_cup_9.setVisibility(View.INVISIBLE);

        layout_cup_10.setVisibility(View.INVISIBLE);

        layout_cup_11.setVisibility(View.INVISIBLE);

        layout_cup_12.setVisibility(View.INVISIBLE);

        layout_cup_back.setVisibility(View.INVISIBLE);

        layout_cup_next.setVisibility(View.INVISIBLE);


    }

    public void clearBandFit()
    {
        layout_digging_in.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_digging_in.setTextColor(getResources().getColor(R.color.grey_4));
        tv_digging_in_description.setTextColor(getResources().getColor(R.color.grey_4));
        ic_digging_in.setImageResource(R.drawable.ic_bra_digging_in_unselected);

        layout_straight.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_straight.setTextColor(getResources().getColor(R.color.grey_4));
        tv_straight_description.setTextColor(getResources().getColor(R.color.grey_4));
        ic_straight.setImageResource(R.drawable.ic_bra_straight_unselected);

        layout_riding_up.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_riding_up.setTextColor(getResources().getColor(R.color.grey_4));
        tv_riding_up_description.setTextColor(getResources().getColor(R.color.grey_4));
        ic_riding_up.setImageResource(R.drawable.ic_bra_riding_up_unselected);
    }


    public void clearCupFit()
    {
        layout_just_right.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_just_right.setTextColor(getResources().getColor(R.color.grey_4));
        tv_just_right_description.setTextColor(getResources().getColor(R.color.grey_4));
        ic_just_right.setImageResource(R.drawable.ic_cup_right_unselected);

        layout_slipping.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_slipping.setTextColor(getResources().getColor(R.color.grey_4));
        tv_slipping_description.setTextColor(getResources().getColor(R.color.grey_4));
        ic_slipping.setImageResource(R.drawable.ic_cup_slipping_unselected);

        layout_gap.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_gap.setTextColor(getResources().getColor(R.color.grey_4));
        tv_gap_description.setTextColor(getResources().getColor(R.color.grey_4));
        ic_gap.setImageResource(R.drawable.ic_gaps_unselected);
    }


    public void clearStrapFit()
    {
        layout_strap_digging_in.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_strap_digging_in.setTextColor(getResources().getColor(R.color.grey_4));
        tv_strap_digging_in_description.setTextColor(getResources().getColor(R.color.grey_4));
        ic_strap_digging_in.setImageResource(R.drawable.ic_strap_digging_in_unselected);

        layout_strap_fit_fine.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_strap_fit_fine.setTextColor(getResources().getColor(R.color.grey_4));
        tv_strap_fit_fine_description.setTextColor(getResources().getColor(R.color.grey_4));
        ic_strap_fit_fine.setImageResource(R.drawable.ic_strap_fit_fine_unselected);

        layout_strap_not_up.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_strap_not_up.setTextColor(getResources().getColor(R.color.grey_4));
        tv_strap_not_up_description.setTextColor(getResources().getColor(R.color.grey_4));
        ic_strap_not_up.setImageResource(R.drawable.ic_strap_not_staying_unselected);
    }



    public void clearUnderwireFront()
    {
        layout_lift_away.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_lift_away.setTextColor(getResources().getColor(R.color.grey_4));
        ic_lift_away.setImageResource(R.drawable.ic_underwire_front_lift_away_unselected);


        layout_sit_comfortable.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_sit_comfortable.setTextColor(getResources().getColor(R.color.grey_4));
        ic_sit_comfortable.setImageResource(R.drawable.ic_underwire_front_sit_comfortable_unselected);

        layout_digging_into_chest.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_digging_into_chest.setTextColor(getResources().getColor(R.color.grey_4));
        ic_digging_into_chest.setImageResource(R.drawable.ic_underwire_front_digging_into_chest_unselcted);

    }

    public void clearUnderwireSide()
    {
        layout_digging_arms.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_digging_arms.setTextColor(getResources().getColor(R.color.grey_4));
        ic_digging_arms.setImageResource(R.drawable.ic_underwire_side_digging_arms_unselected);


        layout_around_bust.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_around_bust.setTextColor(getResources().getColor(R.color.grey_4));
        ic_around_bust.setImageResource(R.drawable.ic_underwire_side_sit_nicely_unselected);

        layout_digging_chest.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_digging_chest.setTextColor(getResources().getColor(R.color.grey_4));
        ic_digging_chest.setImageResource(R.drawable.ic_underwire_side_digging_in_unselected);

    }

    public void clearGap()
    {
        layout_at_top.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_at_top.setTextColor(getResources().getColor(R.color.grey_4));
        ic_at_top.setImageResource(R.drawable.ic_gap_at_top_unselected);


        layout_all_over.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_all_over.setTextColor(getResources().getColor(R.color.grey_4));
        ic_all_over.setImageResource(R.drawable.ic_gap_all_over_unselected);

        layout_at_bottom.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_at_bottom.setTextColor(getResources().getColor(R.color.grey_4));
        ic_at_bottom.setImageResource(R.drawable.ic_gap_at_bottom_unselected);

    }

    public void clearStyle()
    {
        layout_full_coverage.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_full_coverage.setTextColor(getResources().getColor(R.color.grey_4));
        tv_full_coverage_description.setTextColor(getResources().getColor(R.color.grey_4));
        ic_full_coverage.setImageResource(R.drawable.ic_bra_style_full_coverage_unselected);


        layout_half_cup.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_half_cup.setTextColor(getResources().getColor(R.color.grey_4));
        tv_half_cup_description.setTextColor(getResources().getColor(R.color.grey_4));
        ic_half_cup.setImageResource(R.drawable.ic_bra_style_half_cup_unselected);


        layout_pushup.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_pushup.setTextColor(getResources().getColor(R.color.grey_4));
        tv_pushup_description.setTextColor(getResources().getColor(R.color.grey_4));
        ic_pushup.setImageResource(R.drawable.ic_bra_style_pushup_unselected);

        layout_strapless.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_strapless.setTextColor(getResources().getColor(R.color.grey_4));
        tv_strapless_description.setTextColor(getResources().getColor(R.color.grey_4));
        ic_strapless.setImageResource(R.drawable.ic_bra_style_strapless_unselected);

    }


    public void clearOldBra()
    {

        layout_bought_recently.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_bought_recently.setTextColor(getResources().getColor(R.color.grey_4));

        layout_6months.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_6months.setTextColor(getResources().getColor(R.color.grey_4));

        layout_12months.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_12months.setTextColor(getResources().getColor(R.color.grey_4));

        layout_1year.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
        tv_1year.setTextColor(getResources().getColor(R.color.grey_4));

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


    public void clearBraType()
    {
        layout_type_wired_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_type_wired_noBrand.setTextColor(getResources().getColor(R.color.grey_3));


        layout_type_unwired_noBrand.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_type_unwired_noBrand.setTextColor(getResources().getColor(R.color.grey_3));
    }

    public void resetData()
    {
        et_what_brand.setText(null);

        brand = "";
        cup = "";
        isWired = "";
        bandFit = "";
        cupFit = "";
        strapsFit = "";
        band = "";
        region = "";
        braSizeChart = "";
        isWired = "";

        region = "";
        region_noBrand = "";
        cup = "";
        cup_noBrand = "";
        band_noBrand = "";
        isWired = "";
        isWired_noBrand = "";
        wired = false ;
        wired_noBrand =false ;
        bandFit = "0";
        cupFit = "0";
        strapsFit = "0";
        braAge = "0";
        braStyle = "0";
        gaps = "0";
        sameBra = false ;
        tightHook = false;
        looseHook = false;
        tightStrap = false;
        sideWireFit = "0";
        frontWireFit = "0";

        isEditFlow = false;
        brandNotListed = false ;
        isBrandSelected = false;
        tellMore = false;
        moreAccurate = false;


        startOver = true;

        tv_type_unwired.setTextColor(getResources().getColor(R.color.grey_3));
        layout_type_unwired.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

        tv_type_wired.setTextColor(getResources().getColor(R.color.grey_3));
        layout_type_wired.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));


        clearAllCupNoBrand();
        clearAllCup();
        clearAllBandNoBrand();
        clearAllBands();
        clearBandFit();
        clearGap();
        clearCupFit();
        clearStrapFit();
        clearUnderwireFront();
        clearUnderwireSide();
        clearBraType();
        clearOldBra();
        clearStyle();


        tv_bra_sizes_text.setText("");
        tv_bra_style_text.setText("");
        layout_bra_loose_hook_button.setVisibility(View.GONE);
        layout_bra_isOld_button.setVisibility(View.GONE);
        layout_bra_tight_hook_button.setVisibility(View.GONE);
        layout_gap_button.setVisibility(View.GONE);

        tv_bra_looser_hook_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
        tv_bra_looser_hook_no.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));

        tv_bra_looser_hook_no.setTextColor(getResources().getColor(R.color.white));
        tv_bra_looser_hook_yes.setTextColor(getResources().getColor(R.color.white));

        tv_bra_tight_hook_no.setBackground(getResources().getDrawable(R.color.color_background_button_disabled));
        tv_bra_tight_hook_yes.setBackground(getResources().getDrawable(R.color.color_background_button_disabled));

        tv_bra_tight_hook_no.setTextColor(getResources().getColor(R.color.white));
        tv_bra_tight_hook_yes.setTextColor(getResources().getColor(R.color.white));

        tv_old_bra_page_no.setBackground(getResources().getDrawable(R.color.color_background_button_disabled));
        tv_old_bra_page_yes.setBackground(getResources().getDrawable(R.color.color_background_button_disabled));

        tv_old_bra_page_no.setTextColor(getResources().getColor(R.color.white));
        tv_old_bra_page_yes.setTextColor(getResources().getColor(R.color.white));

        tv_isOld_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
        tv_isOld_no.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));

        tv_isOld_yes.setTextColor(getResources().getColor(R.color.white));
        tv_isOld_no.setTextColor(getResources().getColor(R.color.white));

        tv_gap_no.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
        tv_gap_yes.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));

        tv_gap_no.setTextColor(getResources().getColor(R.color.white));
        tv_gap_yes.setTextColor(getResources().getColor(R.color.white));



    }


    public void setBandFeel(String band_feel)
    {

        Log.e("band_feel_value", band_feel);

        clearBandFit();

        switch (band_feel)
        {
            case "1":

                layout_digging_in.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_digging_in.setTextColor(getResources().getColor(R.color.color_text));
                tv_digging_in_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_digging_in.setImageResource(R.drawable.ic_bra_digging_in_selected);

                tv_bra_sizes_text.setText(getResources().getString(R.string.bra_feel_digging));

                layout_bra_isOld_button.setVisibility(View.GONE);
                layout_bra_loose_hook_button.setVisibility(View.VISIBLE);
                layout_bra_tight_hook_button.setVisibility(View.GONE);

                break;

            case "2":

                layout_straight.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_straight.setTextColor(getResources().getColor(R.color.color_text));
                tv_straight_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_straight.setImageResource(R.drawable.ic_bra_straight_selected);

                tv_bra_sizes_text.setText("");

                layout_bra_loose_hook_button.setVisibility(View.GONE);
                layout_bra_isOld_button.setVisibility(View.GONE);
                layout_bra_tight_hook_button.setVisibility(View.GONE);

                break;

            case "3":

                layout_riding_up.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_riding_up.setTextColor(getResources().getColor(R.color.color_text));
                tv_riding_up_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_riding_up.setImageResource(R.drawable.ic_bra_riding_up_selected);

                tv_bra_sizes_text.setText(getResources().getString(R.string.bra_isOld_bra));

                layout_bra_loose_hook_button.setVisibility(View.GONE);
                layout_bra_isOld_button.setVisibility(View.VISIBLE);
                layout_bra_tight_hook_button.setVisibility(View.GONE);

                break;
        }
    }


    public void setBraAge(String bra_age)
    {
        Log.e("bra_age_value", bra_age);

        clearOldBra();

        switch (bra_age)
        {
            case "1":

                layout_bought_recently.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_bought_recently.setTextColor(getResources().getColor(R.color.color_text));

                tv_old_bra_text.setText(getResources().getString(R.string.bra_tighter_hook));
                layout_old_bra_button.setVisibility(View.VISIBLE);

                break;

            case "2":

                layout_6months.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_6months.setTextColor(getResources().getColor(R.color.color_text));

                tv_old_bra_text.setText(getResources().getString(R.string.bra_tighter_hook));
                layout_old_bra_button.setVisibility(View.VISIBLE);

                break;

            case "3":

                layout_12months.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_12months.setTextColor(getResources().getColor(R.color.color_text));



                break;

            case "4":

                layout_1year.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_1year.setTextColor(getResources().getColor(R.color.color_text));

                 break;
        }
    }


    public void setCupFit(String cup_fit)
    {
        Log.e("cup_fit_value", cup_fit);

        clearCupFit();

        switch (cup_fit)
        {
            case "1":
                layout_just_right.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_just_right.setTextColor(getResources().getColor(R.color.color_text));
                tv_just_right_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_just_right.setImageResource(R.drawable.ic_cup_right_selected);

                break;

            case "2":
                layout_slipping.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_slipping.setTextColor(getResources().getColor(R.color.color_text));
                tv_slipping_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_slipping.setImageResource(R.drawable.ic_cup_slipping_selected);

                break;

            case "3":
                layout_gap.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_gap.setTextColor(getResources().getColor(R.color.color_text));
                tv_gap_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_gap.setImageResource(R.drawable.ic_gaps_selected);
                break;
        }
    }


    public void setGaps(String gaps_value)
    {

        Log.e("gaps_value", gaps_value);

        clearGap();

        switch (gaps_value)
        {
            case "1":

                layout_at_top.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_at_top.setTextColor(getResources().getColor(R.color.color_text));
                ic_at_top.setImageResource(R.drawable.ic_gap_at_top_selected);

                tv_bra_style_text.setText(getResources().getString(R.string.bra_stap_gap));
                layout_gap_button.setVisibility(View.VISIBLE);

                break;

            case "2":

                layout_all_over.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_all_over.setTextColor(getResources().getColor(R.color.color_text));
                ic_all_over.setImageResource(R.drawable.ic_gap_all_over_selected);

                tv_bra_style_text.setText("");
                layout_gap_button.setVisibility(View.GONE);

                break;

            case "3":

                layout_at_bottom.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_at_bottom.setTextColor(getResources().getColor(R.color.color_text));
                ic_at_bottom.setImageResource(R.drawable.ic_gap_at_bottom_selected);

                tv_bra_style_text.setText("");
                layout_gap_button.setVisibility(View.GONE);

                break;
        }
    }



    public void setBraStyle(String braStyle_value)
    {

        Log.e("braStyle_value", braStyle_value);

        clearStyle();

        switch (braStyle_value)
        {
            case "1":

                layout_full_coverage.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_full_coverage.setTextColor(getResources().getColor(R.color.color_text));
                tv_full_coverage_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_full_coverage.setImageResource(R.drawable.ic_bra_style_full_coverage_selected);

                break;

            case "2":

                layout_half_cup.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_half_cup.setTextColor(getResources().getColor(R.color.color_text));
                tv_half_cup_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_half_cup.setImageResource(R.drawable.ic_bra_style_half_cup_selected);

                break;

            case "3":

                layout_pushup.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_pushup.setTextColor(getResources().getColor(R.color.color_text));
                tv_pushup_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_pushup.setImageResource(R.drawable.ic_bra_style_pushup_selected);

                break;

            case "4":

                layout_strapless.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_strapless.setTextColor(getResources().getColor(R.color.color_text));
                tv_strapless_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_strapless.setImageResource(R.drawable.ic_bra_style_strapless_selected);

                break;
        }
    }


    public void setStraps(String straps_value)
    {

        Log.e("straps_value", straps_value);

        clearStrapFit();

        switch (straps_value)
        {
            case "1":

                layout_strap_digging_in.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_strap_digging_in.setTextColor(getResources().getColor(R.color.color_text));
                tv_strap_digging_in_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_strap_digging_in.setImageResource(R.drawable.ic_strap_digging_in_selected);

                break;

            case "2":

                layout_strap_fit_fine.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_strap_fit_fine.setTextColor(getResources().getColor(R.color.color_text));
                tv_strap_fit_fine_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_strap_fit_fine.setImageResource(R.drawable.ic_strap_fit_fine_selected);

                break;

            case "3":

                layout_strap_not_up.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_strap_not_up.setTextColor(getResources().getColor(R.color.color_text));
                tv_strap_not_up_description.setTextColor(getResources().getColor(R.color.color_text));
                ic_strap_not_up.setImageResource(R.drawable.ic_strap_not_staying_selected);

                break;
        }
    }


    public void setFrontWire(String wire_front_value)
    {

        Log.e("wire_front_value", wire_front_value);

        clearUnderwireFront();

        switch (wire_front_value)
        {
            case "1":

                layout_lift_away.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_lift_away.setTextColor(getResources().getColor(R.color.color_text));
                ic_lift_away.setImageResource(R.drawable.ic_underwire_front_lift_away_selected);

                break;

            case "2":

                layout_sit_comfortable.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_sit_comfortable.setTextColor(getResources().getColor(R.color.color_text));
                ic_sit_comfortable.setImageResource(R.drawable.ic_underwire_front_sit_comfortable_selected);

                break;

            case "3":

                layout_digging_into_chest.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_digging_into_chest.setTextColor(getResources().getColor(R.color.color_text));
                ic_digging_into_chest.setImageResource(R.drawable.ic_underwire_front_digging_into_chest_selcted);

                break;
        }
    }


    public void setSideWire(String wire_side_value)
    {

        Log.e("wire_side_value", wire_side_value);

        clearUnderwireSide();

        switch (wire_side_value)
        {
            case "1":

                layout_digging_arms.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_digging_arms.setTextColor(getResources().getColor(R.color.color_text));
                ic_digging_arms.setImageResource(R.drawable.ic_underwire_side_digging_arms_selected);

                break;

            case "2":

                layout_around_bust.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_around_bust.setTextColor(getResources().getColor(R.color.color_text));
                ic_around_bust.setImageResource(R.drawable.ic_underwire_side_sit_nicely_selected);

                break;

            case "3":

                layout_digging_chest.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_digging_chest.setTextColor(getResources().getColor(R.color.color_text));
                ic_digging_chest.setImageResource(R.drawable.ic_underwire_side_digging_in_selected);

                break;
        }
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
                layout_progress_7.setVisibility(View.INVISIBLE);
                layout_progress_8.setVisibility(View.INVISIBLE);
                layout_progress_9.setVisibility(View.INVISIBLE);
                layout_progress_10.setVisibility(View.INVISIBLE);
                layout_progress_11.setVisibility(View.INVISIBLE);

                break;

            case 2:
                layout_progress_1.setVisibility(View.VISIBLE);
                layout_progress_2.setVisibility(View.VISIBLE);
                layout_progress_3.setVisibility(View.INVISIBLE);
                layout_progress_4.setVisibility(View.INVISIBLE);
                layout_progress_5.setVisibility(View.INVISIBLE);
                layout_progress_6.setVisibility(View.INVISIBLE);
                layout_progress_7.setVisibility(View.INVISIBLE);
                layout_progress_8.setVisibility(View.INVISIBLE);
                layout_progress_9.setVisibility(View.INVISIBLE);
                layout_progress_10.setVisibility(View.INVISIBLE);
                layout_progress_11.setVisibility(View.INVISIBLE);
                break;

            case 3:
                layout_progress_1.setVisibility(View.VISIBLE);
                layout_progress_2.setVisibility(View.VISIBLE);
                layout_progress_3.setVisibility(View.VISIBLE);
                layout_progress_4.setVisibility(View.INVISIBLE);
                layout_progress_5.setVisibility(View.INVISIBLE);
                layout_progress_6.setVisibility(View.INVISIBLE);
                layout_progress_7.setVisibility(View.INVISIBLE);
                layout_progress_8.setVisibility(View.INVISIBLE);
                layout_progress_9.setVisibility(View.INVISIBLE);
                layout_progress_10.setVisibility(View.INVISIBLE);
                layout_progress_11.setVisibility(View.INVISIBLE);
                break;

            case 4:
                layout_progress_1.setVisibility(View.VISIBLE);
                layout_progress_2.setVisibility(View.VISIBLE);
                layout_progress_3.setVisibility(View.VISIBLE);
                layout_progress_4.setVisibility(View.VISIBLE);
                layout_progress_5.setVisibility(View.INVISIBLE);
                layout_progress_6.setVisibility(View.INVISIBLE);
                layout_progress_7.setVisibility(View.INVISIBLE);
                layout_progress_8.setVisibility(View.INVISIBLE);
                layout_progress_9.setVisibility(View.INVISIBLE);
                layout_progress_10.setVisibility(View.INVISIBLE);
                layout_progress_11.setVisibility(View.INVISIBLE);
                break;

            case 5:
                layout_progress_1.setVisibility(View.VISIBLE);
                layout_progress_2.setVisibility(View.VISIBLE);
                layout_progress_3.setVisibility(View.VISIBLE);
                layout_progress_4.setVisibility(View.VISIBLE);
                layout_progress_5.setVisibility(View.VISIBLE);
                layout_progress_6.setVisibility(View.INVISIBLE);
                layout_progress_7.setVisibility(View.INVISIBLE);
                layout_progress_8.setVisibility(View.INVISIBLE);
                layout_progress_9.setVisibility(View.INVISIBLE);
                layout_progress_10.setVisibility(View.INVISIBLE);
                layout_progress_11.setVisibility(View.INVISIBLE);
                break;

            case 6:
                layout_progress_1.setVisibility(View.VISIBLE);
                layout_progress_2.setVisibility(View.VISIBLE);
                layout_progress_3.setVisibility(View.VISIBLE);
                layout_progress_4.setVisibility(View.VISIBLE);
                layout_progress_5.setVisibility(View.VISIBLE);
                layout_progress_6.setVisibility(View.VISIBLE);
                layout_progress_7.setVisibility(View.INVISIBLE);
                layout_progress_8.setVisibility(View.INVISIBLE);
                layout_progress_9.setVisibility(View.INVISIBLE);
                layout_progress_10.setVisibility(View.INVISIBLE);
                layout_progress_11.setVisibility(View.INVISIBLE);
                break;

            case 7:
                layout_progress_1.setVisibility(View.VISIBLE);
                layout_progress_2.setVisibility(View.VISIBLE);
                layout_progress_3.setVisibility(View.VISIBLE);
                layout_progress_4.setVisibility(View.VISIBLE);
                layout_progress_5.setVisibility(View.VISIBLE);
                layout_progress_6.setVisibility(View.VISIBLE);
                layout_progress_7.setVisibility(View.VISIBLE);
                layout_progress_8.setVisibility(View.INVISIBLE);
                layout_progress_9.setVisibility(View.INVISIBLE);
                layout_progress_10.setVisibility(View.INVISIBLE);
                layout_progress_11.setVisibility(View.INVISIBLE);
                break;


            case 8:
                layout_progress_1.setVisibility(View.VISIBLE);
                layout_progress_2.setVisibility(View.VISIBLE);
                layout_progress_3.setVisibility(View.VISIBLE);
                layout_progress_4.setVisibility(View.VISIBLE);
                layout_progress_5.setVisibility(View.VISIBLE);
                layout_progress_6.setVisibility(View.VISIBLE);
                layout_progress_7.setVisibility(View.VISIBLE);
                layout_progress_8.setVisibility(View.VISIBLE);
                layout_progress_9.setVisibility(View.INVISIBLE);
                layout_progress_10.setVisibility(View.INVISIBLE);
                layout_progress_11.setVisibility(View.INVISIBLE);
                break;

            case 9:
                layout_progress_1.setVisibility(View.VISIBLE);
                layout_progress_2.setVisibility(View.VISIBLE);
                layout_progress_3.setVisibility(View.VISIBLE);
                layout_progress_4.setVisibility(View.VISIBLE);
                layout_progress_5.setVisibility(View.VISIBLE);
                layout_progress_6.setVisibility(View.VISIBLE);
                layout_progress_7.setVisibility(View.VISIBLE);
                layout_progress_8.setVisibility(View.VISIBLE);
                layout_progress_9.setVisibility(View.VISIBLE);
                layout_progress_10.setVisibility(View.INVISIBLE);
                layout_progress_11.setVisibility(View.INVISIBLE);
                break;

            case 10:
                layout_progress_1.setVisibility(View.VISIBLE);
                layout_progress_2.setVisibility(View.VISIBLE);
                layout_progress_3.setVisibility(View.VISIBLE);
                layout_progress_4.setVisibility(View.VISIBLE);
                layout_progress_5.setVisibility(View.VISIBLE);
                layout_progress_6.setVisibility(View.VISIBLE);
                layout_progress_7.setVisibility(View.VISIBLE);
                layout_progress_8.setVisibility(View.VISIBLE);
                layout_progress_9.setVisibility(View.VISIBLE);
                layout_progress_10.setVisibility(View.VISIBLE);
                layout_progress_11.setVisibility(View.INVISIBLE);
                break;

            case 11:
                layout_progress_1.setVisibility(View.VISIBLE);
                layout_progress_2.setVisibility(View.VISIBLE);
                layout_progress_3.setVisibility(View.VISIBLE);
                layout_progress_4.setVisibility(View.VISIBLE);
                layout_progress_5.setVisibility(View.VISIBLE);
                layout_progress_6.setVisibility(View.VISIBLE);
                layout_progress_7.setVisibility(View.VISIBLE);
                layout_progress_8.setVisibility(View.VISIBLE);
                layout_progress_9.setVisibility(View.VISIBLE);
                layout_progress_10.setVisibility(View.VISIBLE);
                layout_progress_11.setVisibility(View.VISIBLE);
                break;
        }
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
