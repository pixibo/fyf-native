package com.pixibo.zalora.Footwear;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pixibo.zalora.Adapter.BrandAdapter;
import com.pixibo.zalora.Adapter.BrandCategoryAdapter;
import com.pixibo.zalora.Adapter.BrandCategoryModelAdapter;
import com.pixibo.zalora.Apparel.ApparelFlow;
import com.pixibo.zalora.Model.BrandModel;
import com.pixibo.zalora.R;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.pixibo.zalora.Utils.Utils.TYPE.BrandCategorySuggestion;
import static com.pixibo.zalora.Utils.Utils.TYPE.BrandSizeSuggestion;
import static com.pixibo.zalora.Utils.Utils.TYPE.BrandSuggestion;
import static com.pixibo.zalora.Utils.Utils.TYPE.GetSize;
import static com.pixibo.zalora.Utils.Utils.TYPE.Track;
import static com.pixibo.zalora.Utils.Utils.TYPE.UpdateData;
import static com.pixibo.zalora.Utils.Utils.TYPE.ValidateUserUid;

public class FootwearFlow extends AppCompatActivity implements Result, View.OnClickListener {




    private ArrayList<BrandModel> brandModelArrayList = new ArrayList<>();
    private ArrayList<BrandModel> brandCategoryModelArrayList = new ArrayList<>();
    private ArrayList<BrandModel> brandCategoryArrayList = new ArrayList<>();
    private BrandAdapter brandAdapter;
    private BrandCategoryAdapter brandCategoryAdapter;
    private BrandCategoryModelAdapter brandCategoryModelAdapter;
    private RecyclerView recycler_brand_suggestion,recycler_brand_category_model,recycler_brand_category_selection;

    private EditText et_what_brand;

    private boolean isBrandSelected = false;

    private RelativeLayout layout_brand_search,layout_add,layout_brands,layout_loading;
    private RelativeLayout layout_brand,layout_brand_category,layout_brand_category_model,layout_brand_size,layout_result;
    private LinearLayout layout_brand_category_selection,layout_brand_category_model_selection,layout_size_type,layout_width_type,
            layout_size_row1,layout_size_row2,layout_size_row3,layout_size_row4,layout_size_row5,layout_size_row6,layout_recommended,layout_start_over;


    private String category = "";
    private String gender = "";

    private String clientId = "";
    private String skuId = "";
    private String altId = "";
    private String uID = "";
    private String brand = "";
    private String categoryType = "";
    private String categoryModel = "";
    private String brandCategorySuggestion = "";
    private String brandSizeSuggestion = "";
    private String sizeType = "";
    private String size = "";
    private String width = "regular";
    private boolean isBrandModelPresent = false ;
    private boolean isSizeSelected = false ;
    private boolean isRecommended = false ;
    private String preferredLanguage = "";


    private TextView tv_category,tv_brand_category,tv_category_model,tv_brand_category_model,tv_brand_fit,tv_type_size,tv_width;

    private TextView tv_brand_1,tv_brand_2,tv_brand_3,tv_brand_4,tv_brand_5,tv_brand_6;
    private TextView tv_size_1,tv_size_2,tv_size_3,tv_size_4,tv_size_5,tv_size_6,tv_size_7,tv_size_8,tv_size_9,tv_size_10,tv_size_11,tv_size_12,tv_size_13,tv_size_14,
            tv_size_15,tv_size_16,tv_size_17,tv_size_18,tv_size_19,tv_size_20,tv_size_21,tv_size_22,tv_size_23,tv_size_24,tv_size_25;
    private TextView tv_type_1,tv_type_2,tv_type_3,tv_type_4,tv_type_5,tv_type_6,tv_type_7,tv_type_8;
    private TextView tv_width_1,tv_width_2,tv_width_3,tv_width_4;
    private RelativeLayout layout_next_1,layout_back_1,layout_next_2,layout_back_2,layout_next_3,layout_back_3,layout_next_4,layout_back_4,layout_next_5,layout_back_5,layout_next_6;
    private TextView tv_brand_error,tv_error_category,tv_error_category_model,tv_erroe_size,tv_size_result,tv_not_recommended;
    private TextView tv_brand_continue,tv_brand_category_continue,tv_brand_category_model_continue,tv_brand_size_continue;
    private TextView tv_brand_category_skip,tv_brand_model_skip;
    private TextView tv_back_brand_category,tv_back_brand_model_category,tv_back_brand_size;
    private RelativeLayout layout_progress_1,layout_progress_2,layout_progress_3,layout_progress_4,layout_progress_5,layout_progress_6;
    private RelativeLayout layout_size_1,layout_size_2,layout_size_3,layout_size_4,layout_size_5,layout_size_6,layout_size_7,layout_size_8,layout_size_9,layout_size_10,layout_size_11,
            layout_size_12,layout_size_13,layout_size_14,layout_size_15,layout_size_16,layout_size_17,layout_size_18,layout_size_19,layout_size_20,layout_size_21,layout_size_22,layout_size_23,layout_size_24,layout_size_25;

    private RelativeLayout layout_close,layout_privacy,layout_popup;

    private TextView tv_privacy_policy,tv_description,tv_add_cart;

    private ImageView iv_shoe_category;

    String[] footwear_array = new String[]{"Shoes"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footwear_flow);


        Intent intent = getIntent();

        category = intent.getStringExtra("dataType");
        gender = intent.getStringExtra("gender");

        brand = intent.getStringExtra("brand");

        clientId = intent.getStringExtra("clientId");
        skuId = intent.getStringExtra("skuId");
        altId = intent.getStringExtra("altId");
        uID = intent.getStringExtra("uID");
        preferredLanguage = intent.getStringExtra("preferredLanguage");


        recycler_brand_suggestion = findViewById(R.id.recycler_brand_suggestion);
        recycler_brand_category_model = findViewById(R.id.recycler_brand_category_model);
        recycler_brand_category_selection = findViewById(R.id.recycler_brand_category_selection);

        iv_shoe_category = findViewById(R.id.iv_shoe_category);

        tv_category = findViewById(R.id.tv_category);
        tv_brand_category = findViewById(R.id.tv_brand_category);
        tv_brand_category_model = findViewById(R.id.tv_brand_category_model);
        tv_category_model = findViewById(R.id.tv_category_model);
        tv_brand_fit = findViewById(R.id.tv_brand_fit);
        tv_type_size = findViewById(R.id.tv_type_size);
        tv_width = findViewById(R.id.tv_width);

        tv_brand_error = findViewById(R.id.tv_brand_error);
        tv_error_category = findViewById(R.id.tv_error_category);
        tv_error_category_model = findViewById(R.id.tv_error_category_model);
        tv_erroe_size = findViewById(R.id.tv_erroe_size);
        tv_size_result = findViewById(R.id.tv_size_result);
        tv_not_recommended = findViewById(R.id.tv_not_recommended);

        tv_brand_1 = findViewById(R.id.tv_brand_1);
        tv_brand_2 = findViewById(R.id.tv_brand_2);
        tv_brand_3 = findViewById(R.id.tv_brand_3);
        tv_brand_4 = findViewById(R.id.tv_brand_4);
        tv_brand_5 = findViewById(R.id.tv_brand_5);
        tv_brand_6 = findViewById(R.id.tv_brand_6);

        tv_type_1 = findViewById(R.id.tv_type_1);
        tv_type_2 = findViewById(R.id.tv_type_2);
        tv_type_3 = findViewById(R.id.tv_type_3);
        tv_type_4 = findViewById(R.id.tv_type_4);
        tv_type_5 = findViewById(R.id.tv_type_5);
        tv_type_6 = findViewById(R.id.tv_type_6);
        tv_type_7 = findViewById(R.id.tv_type_7);
        tv_type_8 = findViewById(R.id.tv_type_8);


        tv_width_1 = findViewById(R.id.tv_width_1);
        tv_width_2 = findViewById(R.id.tv_width_2);
        tv_width_3 = findViewById(R.id.tv_width_3);
        tv_width_4 = findViewById(R.id.tv_width_4);


        tv_size_1 = findViewById(R.id.tv_size_1);
        tv_size_2 = findViewById(R.id.tv_size_2);
        tv_size_3 = findViewById(R.id.tv_size_3);
        tv_size_4 = findViewById(R.id.tv_size_4);
        tv_size_5 = findViewById(R.id.tv_size_5);
        tv_size_6 = findViewById(R.id.tv_size_6);
        tv_size_7 = findViewById(R.id.tv_size_7);
        tv_size_8 = findViewById(R.id.tv_size_8);
        tv_size_9 = findViewById(R.id.tv_size_9);
        tv_size_10 = findViewById(R.id.tv_size_10);
        tv_size_11 = findViewById(R.id.tv_size_11);
        tv_size_12 = findViewById(R.id.tv_size_12);
        tv_size_13 = findViewById(R.id.tv_size_13);
        tv_size_14 = findViewById(R.id.tv_size_14);
        tv_size_15 = findViewById(R.id.tv_size_15);
        tv_size_16 = findViewById(R.id.tv_size_16);
        tv_size_17 = findViewById(R.id.tv_size_17);
        tv_size_18 = findViewById(R.id.tv_size_18);
        tv_size_19 = findViewById(R.id.tv_size_19);
        tv_size_20 = findViewById(R.id.tv_size_20);
        tv_size_21 = findViewById(R.id.tv_size_21);
        tv_size_22 = findViewById(R.id.tv_size_22);
        tv_size_23 = findViewById(R.id.tv_size_23);
        tv_size_24 = findViewById(R.id.tv_size_24);
        tv_size_25 = findViewById(R.id.tv_size_25);


        layout_size_1 = findViewById(R.id.layout_size_1);
        layout_size_2 = findViewById(R.id.layout_size_2);
        layout_size_3 = findViewById(R.id.layout_size_3);
        layout_size_4 = findViewById(R.id.layout_size_4);
        layout_size_5 = findViewById(R.id.layout_size_5);
        layout_size_6 = findViewById(R.id.layout_size_6);
        layout_size_7 = findViewById(R.id.layout_size_7);
        layout_size_8 = findViewById(R.id.layout_size_8);
        layout_size_9 = findViewById(R.id.layout_size_9);
        layout_size_10 = findViewById(R.id.layout_size_10);
        layout_size_11 = findViewById(R.id.layout_size_11);
        layout_size_12 = findViewById(R.id.layout_size_12);
        layout_size_13 = findViewById(R.id.layout_size_13);
        layout_size_14 = findViewById(R.id.layout_size_14);
        layout_size_15 = findViewById(R.id.layout_size_15);
        layout_size_16 = findViewById(R.id.layout_size_16);
        layout_size_17 = findViewById(R.id.layout_size_17);
        layout_size_18 = findViewById(R.id.layout_size_18);
        layout_size_19 = findViewById(R.id.layout_size_19);
        layout_size_20 = findViewById(R.id.layout_size_20);
        layout_size_21 = findViewById(R.id.layout_size_21);
        layout_size_22 = findViewById(R.id.layout_size_22);
        layout_size_23 = findViewById(R.id.layout_size_23);
        layout_size_24 = findViewById(R.id.layout_size_24);
        layout_size_25 = findViewById(R.id.layout_size_25);

        layout_close = findViewById(R.id.layout_close);
        layout_privacy = findViewById(R.id.layout_privacy);
        layout_popup = findViewById(R.id.layout_popup);


        tv_privacy_policy = findViewById(R.id.tv_privacy_policy);
        tv_description = findViewById(R.id.tv_description);
        tv_add_cart = findViewById(R.id.tv_add_cart);

        layout_progress_1 = findViewById(R.id.layout_progress_1);
        layout_progress_2 = findViewById(R.id.layout_progress_2);
        layout_progress_3 = findViewById(R.id.layout_progress_3);
        layout_progress_4 = findViewById(R.id.layout_progress_4);
        layout_progress_5 = findViewById(R.id.layout_progress_5);
        layout_progress_6 = findViewById(R.id.layout_progress_6);


        layout_next_1 = findViewById(R.id.layout_next_1);
        layout_back_1 = findViewById(R.id.layout_back_1);
        layout_next_2 = findViewById(R.id.layout_next_2);
        layout_back_2 = findViewById(R.id.layout_back_2);
        layout_next_3 = findViewById(R.id.layout_next_3);
        layout_back_3 = findViewById(R.id.layout_back_3);
        layout_next_4 = findViewById(R.id.layout_next_4);
        layout_back_4 = findViewById(R.id.layout_back_4);
        layout_next_5 = findViewById(R.id.layout_next_5);
        layout_back_5 = findViewById(R.id.layout_back_5);
        layout_next_6 = findViewById(R.id.layout_next_6);


        tv_brand_continue = findViewById(R.id.tv_brand_continue);
        tv_brand_category_continue = findViewById(R.id.tv_brand_category_continue);
        tv_brand_category_model_continue = findViewById(R.id.tv_brand_category_model_continue);
        tv_brand_size_continue = findViewById(R.id.tv_brand_size_continue);

        tv_brand_category_skip = findViewById(R.id.tv_brand_category_skip);
        tv_brand_model_skip = findViewById(R.id.tv_brand_model_skip);

        tv_back_brand_category = findViewById(R.id.tv_back_brand_category);
        tv_back_brand_model_category = findViewById(R.id.tv_back_brand_model_category);
        tv_back_brand_size = findViewById(R.id.tv_back_brand_size);

        et_what_brand = findViewById(R.id.et_what_brand);

        layout_brand_search = findViewById(R.id.layout_brand_search);
        layout_add = findViewById(R.id.layout_add);
        layout_brands = findViewById(R.id.layout_brands);
        layout_loading = findViewById(R.id.layout_loading);

        layout_brand_category_selection = findViewById(R.id.layout_brand_category_selection);
        layout_brand_category_model_selection = findViewById(R.id.layout_brand_category_model_selection);
        layout_size_type = findViewById(R.id.layout_size_type);
        layout_width_type = findViewById(R.id.layout_width_type);

        layout_size_row1 = findViewById(R.id.layout_size_row1);
        layout_size_row2 = findViewById(R.id.layout_size_row2);
        layout_size_row3 = findViewById(R.id.layout_size_row3);
        layout_size_row4 = findViewById(R.id.layout_size_row4);
        layout_size_row5 = findViewById(R.id.layout_size_row5);
        layout_size_row6 = findViewById(R.id.layout_size_row6);

        layout_recommended = findViewById(R.id.layout_recommended);
        layout_start_over = findViewById(R.id.layout_start_over);


        layout_brand = findViewById(R.id.layout_brand);
        layout_brand_category = findViewById(R.id.layout_brand_category);
        layout_brand_category_model = findViewById(R.id.layout_brand_category_model);
        layout_brand_size = findViewById(R.id.layout_brand_size);
        layout_result = findViewById(R.id.layout_result);



        if (preferredLanguage.equals("hk") || preferredLanguage.equals("tw"))
        {
            tv_description.setVisibility(View.GONE);
        }



        brandAdapter = new BrandAdapter(brandModelArrayList, FootwearFlow.this, new BrandAdapter.onItemClickListener() {
            @Override
            public void onItemClick(BrandModel suggestions) {

                isBrandSelected = true;
                brand = suggestions.getName();
                et_what_brand.setText(suggestions.getName());
                et_what_brand.setTextColor(getResources().getColor(R.color.black));
                et_what_brand.setSelection(et_what_brand.getText().length());
                layout_brand_search.setVisibility(View.GONE);
                tv_brand_continue.setBackgroundColor(getResources().getColor(R.color.color_background_button_enable));

                checkBrandSelection(brand);

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recycler_brand_suggestion.setItemAnimator(new DefaultItemAnimator());
        recycler_brand_suggestion.setLayoutManager(mLayoutManager);
        recycler_brand_suggestion.setAdapter(brandAdapter);




        brandCategoryModelAdapter = new BrandCategoryModelAdapter(brandCategoryModelArrayList, FootwearFlow.this, new BrandCategoryModelAdapter.onItemClickListener() {
            @Override
            public void onItemClick(BrandModel suggestions) {

                tv_error_category_model.setVisibility(View.GONE);

                categoryModel = suggestions.getName();

                tv_brand_category_model.setText(categoryModel);
                tv_brand_category_model.setBackground(getDrawable(R.drawable.bg_edit_enabled));

                layout_brand_category_model_selection.setVisibility(View.GONE);

                tv_brand_category_model_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

            }
        });

        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recycler_brand_category_model.setItemAnimator(new DefaultItemAnimator());
        recycler_brand_category_model.setLayoutManager(mLayoutManager2);
        recycler_brand_category_model.setAdapter(brandCategoryModelAdapter);



        brandCategoryAdapter = new BrandCategoryAdapter(brandCategoryArrayList, FootwearFlow.this, new BrandCategoryAdapter.onItemClickListener() {
            @Override
            public void onItemClick(BrandModel suggestions) {

                tv_error_category.setVisibility(View.GONE);

                categoryType = suggestions.getId();

                tv_brand_category.setText(suggestions.getName());

                setCategoryImage(categoryType);
                setBrandCategoryModel(categoryType);

                layout_brand_category_selection.setVisibility(View.GONE);

                tv_brand_category_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

            }
        });

        RecyclerView.LayoutManager mLayoutManager3 = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recycler_brand_category_selection.setItemAnimator(new DefaultItemAnimator());
        recycler_brand_category_selection.setLayoutManager(mLayoutManager3);
        recycler_brand_category_selection.setAdapter(brandCategoryAdapter);


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


        layout_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showToast(FootwearFlow.this,"Added");

                et_what_brand.setText("");
                et_what_brand.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                layout_add.setVisibility(View.GONE);
                tv_brand_error.setText(getResources().getString(R.string.brand_thanks));
                layout_brands.setVisibility(View.VISIBLE);
            }
        });


        layout_brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_brand_search.setVisibility(View.GONE);
            }
        });


        tv_brand_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.hideSoftKeyboard(FootwearFlow.this);

                if (isBrandSelected) {

                    get_brand_category(gender,category,brand);

                    final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
                    final SpannableStringBuilder sb = new SpannableStringBuilder(getResources().getString(R.string.footwear_pick_category) + " " + brand + " " + getResources().getString(R.string.footwear_pick_category_2) + getResources().getString(R.string.footwear_pick_category_optional));
                    sb.setSpan(bss, sb.length() - getResources().getString(R.string.footwear_pick_category_optional).length(), sb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    tv_category.setText(sb);

                    categoryType = "";

                    tv_brand_category_continue.setBackground(getResources().getDrawable(R.color.color_background_button_disabled));

                    progress(2);

                    layout_brand.setVisibility(View.GONE);
                    layout_brand_category.setVisibility(View.VISIBLE);

                    iv_shoe_category.setVisibility(View.GONE);

                    tv_brand_category.setText(getResources().getString(R.string.brand_category_select));
                    tv_brand_category.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
                }

            }
        });





        tv_brand_category_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.hideSoftKeyboard(FootwearFlow.this);


                if (!categoryType.equals(""))
                {

                    progress(3);


                    final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
                    final SpannableStringBuilder sb = new SpannableStringBuilder(getResources().getString(R.string.footwear_pick_category_model) + "" + brand + " "+ categoryType + " " + getResources().getString(R.string.footwear_pick_category__model2)+ getResources().getString(R.string.footwear_pick_category_optional));
                    sb.setSpan(bss, sb.length() - getResources().getString(R.string.footwear_pick_category_optional).length(), sb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    tv_category_model.setText(sb);

                    layout_brand_category.setVisibility(View.GONE);
                    layout_brand_category_selection.setVisibility(View.GONE);
                    tv_error_category_model.setVisibility(View.GONE);

                    if (isBrandModelPresent)
                    {
                        layout_brand_category_model.setVisibility(View.VISIBLE);

                        categoryModel = "";

                        tv_brand_category_model.setText(getResources().getString(R.string.brand_category_select));
                        tv_brand_category_model.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

                        tv_brand_category_model_continue.setBackground(getResources().getDrawable(R.color.color_background_button_disabled));

                        layout_brand_category_model_selection.setVisibility(View.GONE);

                        // brand category model will be set to default when going back then pressing continue
                    }
                    else
                    {
                        progress(5);

                        get_brand_sizes(gender,category,brand);

                        clearAllSize();
                        layout_brand_size.setVisibility(View.VISIBLE);


                        if (categoryModel.equals(""))
                        {
                            tv_brand_fit.setText(getResources().getString(R.string.footwear_what_size) +" "+ brand +" "+categoryType+" "+getResources().getString(R.string.footwear_what_size_2));
                        }
                        else
                        {
                            tv_brand_fit.setText(getResources().getString(R.string.footwear_what_size) +" "+ brand +" "+categoryModel+" "+getResources().getString(R.string.footwear_what_size_2));
                        }
                    }


                }

                else
                {
                    tv_error_category.setVisibility(View.VISIBLE);
                }


            }
        });

        tv_brand_category_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.hideSoftKeyboard(FootwearFlow.this);


                clearAllSize();
                layout_brand_category.setVisibility(View.GONE);
                layout_brand_size.setVisibility(View.VISIBLE);

                categoryType = "";
                categoryModel = "";
                width = "";

                get_brand_sizes(gender,category,brand);

                if (categoryModel.equals(""))
                {
                    tv_brand_fit.setText(getResources().getString(R.string.footwear_what_size) +" "+ brand +" "+categoryType+" "+getResources().getString(R.string.footwear_what_size_2));
                }
                else
                {
                    tv_brand_fit.setText(getResources().getString(R.string.footwear_what_size) +" "+ brand +" "+categoryModel+" "+getResources().getString(R.string.footwear_what_size_2));
                }

            }
        });

        tv_back_brand_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.hideSoftKeyboard(FootwearFlow.this);

                layout_brand_category.setVisibility(View.GONE);
                layout_brand.setVisibility(View.VISIBLE);

            }
        });




        tv_brand_category_model_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.hideSoftKeyboard(FootwearFlow.this);

                if (!categoryModel.equals(""))
                {
                    clearAllSize();
                    layout_brand_category_model.setVisibility(View.GONE);
                    layout_brand_size.setVisibility(View.VISIBLE);

                    progress(5);

                    get_brand_sizes(gender,category,brand);

                    if (categoryModel.equals(""))
                    {
                        tv_brand_fit.setText(getResources().getString(R.string.footwear_what_size) +" "+ brand +" "+categoryType+" "+getResources().getString(R.string.footwear_what_size_2));
                    }
                    else
                    {
                        tv_brand_fit.setText(getResources().getString(R.string.footwear_what_size) +" "+ brand +" "+categoryModel+" "+getResources().getString(R.string.footwear_what_size_2));
                    }
                }
                else
                {
                    tv_error_category_model.setVisibility(View.VISIBLE);
                }


            }
        });


        tv_brand_model_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.hideSoftKeyboard(FootwearFlow.this);

                clearAllSize();

                categoryModel = "";

                layout_brand_category_model.setVisibility(View.GONE);
                layout_brand_size.setVisibility(View.VISIBLE);

                get_brand_sizes(gender,category,brand);

                if (categoryModel.equals(""))
                {
                    tv_brand_fit.setText(getResources().getString(R.string.footwear_what_size) +" "+ brand +" "+categoryType+" "+getResources().getString(R.string.footwear_what_size_2));
                }
                else
                {
                    tv_brand_fit.setText(getResources().getString(R.string.footwear_what_size) +" "+ brand +" "+categoryModel+" "+getResources().getString(R.string.footwear_what_size_2));
                }
            }
        });



        tv_back_brand_model_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.hideSoftKeyboard(FootwearFlow.this);

                progress(2);

                layout_brand_category_model.setVisibility(View.GONE);
                layout_brand_category.setVisibility(View.VISIBLE);
            }
        });


        tv_brand_size_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.hideSoftKeyboard(FootwearFlow.this);

                if (!width.equals(""))
                {
                    width = "regular";

                    if (isSizeSelected)
                    {

                        if (altId.equals(""))
                        {
                            getFinalSize(uID,brand,sizeType,size,width,categoryType,category);
                        }
                        else
                        {
                            getFinalSize(altId,brand,sizeType,size,width,categoryType,category);
                        }

                        updateUserDetails();

                    }
                    else
                    {
                        tv_erroe_size.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

        tv_back_brand_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.hideSoftKeyboard(FootwearFlow.this);

                layout_brand_category_selection.setVisibility(View.GONE);


                if (!categoryModel.equals(""))
                {
                    progress(3);
                    layout_brand_category_model.setVisibility(View.VISIBLE);
                    layout_brand_size.setVisibility(View.GONE);
                }
                else if (!categoryType.equals(""))
                {
                    progress(2);
                    layout_brand_category.setVisibility(View.VISIBLE);
                    layout_brand_size.setVisibility(View.GONE);
                }
                else
                {
                    progress(1);
                    layout_brand.setVisibility(View.VISIBLE);
                    layout_brand_size.setVisibility(View.GONE);
                }



            }
        });





        tv_brand_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (layout_brand_category_selection.getVisibility() == View.VISIBLE)
                {
                    layout_brand_category_selection.setVisibility(View.GONE);
                    tv_brand_category.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
                }
                else
                {
                    layout_brand_category_selection.setVisibility(View.VISIBLE);
                    tv_brand_category.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                }
            }
        });


        tv_brand_category_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (layout_brand_category_model_selection.getVisibility() == View.VISIBLE)
                {
                    layout_brand_category_model_selection.setVisibility(View.GONE);
                    tv_brand_category_model.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
                }
                else
                {
                    layout_brand_category_model_selection.setVisibility(View.VISIBLE);
                    tv_brand_category_model.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                }
            }
        });



        tv_type_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (layout_size_type.getVisibility() == View.VISIBLE)
                {
                    layout_size_type.setVisibility(View.GONE);
                    tv_type_size.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
                }
                else
                {
                    layout_size_type.setVisibility(View.VISIBLE);
                    tv_type_size.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                }
            }
        });





        tv_width.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (layout_width_type.getVisibility() == View.VISIBLE)
                {
                    layout_width_type.setVisibility(View.GONE);
                    tv_width.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
                }
                else
                {
                    layout_width_type.setVisibility(View.VISIBLE);
                    tv_width.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                }
            }
        });


        layout_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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


        layout_next_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_size_row1.setVisibility(View.GONE);
                layout_size_row2.setVisibility(View.VISIBLE);
            }
        });

        layout_back_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_size_row1.setVisibility(View.VISIBLE);
                layout_size_row2.setVisibility(View.GONE);
            }
        });

        layout_next_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_size_row2.setVisibility(View.GONE);
                layout_size_row3.setVisibility(View.VISIBLE);
            }
        });

        layout_back_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_size_row2.setVisibility(View.VISIBLE);
                layout_size_row3.setVisibility(View.GONE);
            }
        });

        layout_next_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_size_row3.setVisibility(View.GONE);
                layout_size_row4.setVisibility(View.VISIBLE);
            }
        });

        layout_back_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_size_row3.setVisibility(View.VISIBLE);
                layout_size_row4.setVisibility(View.GONE);
            }
        });

        layout_next_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_size_row4.setVisibility(View.GONE);
                layout_size_row5.setVisibility(View.VISIBLE);
            }
        });

        layout_back_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_size_row5.setVisibility(View.GONE);
                layout_size_row4.setVisibility(View.VISIBLE);
            }
        });

        layout_next_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_size_row5.setVisibility(View.GONE);
                layout_size_row6.setVisibility(View.VISIBLE);
            }
        });

        layout_back_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_size_row6.setVisibility(View.GONE);
                layout_size_row5.setVisibility(View.VISIBLE);
            }
        });


        tv_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent returnIntent = new Intent();
                returnIntent.putExtra("recommended",isRecommended);
                returnIntent.putExtra("result",size);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        });


        layout_start_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //resetData();

                progress(1);

                layout_brand.setVisibility(View.VISIBLE);
                layout_result.setVisibility(View.GONE);

            }
        });


        tv_brand_1.setOnClickListener(this);
        tv_brand_2.setOnClickListener(this);
        tv_brand_3.setOnClickListener(this);
        tv_brand_4.setOnClickListener(this);
        tv_brand_5.setOnClickListener(this);
        tv_brand_6.setOnClickListener(this);


        tv_type_1.setOnClickListener(this);
        tv_type_2.setOnClickListener(this);
        tv_type_3.setOnClickListener(this);
        tv_type_4.setOnClickListener(this);
        tv_type_5.setOnClickListener(this);
        tv_type_6.setOnClickListener(this);
        tv_type_7.setOnClickListener(this);
        tv_type_8.setOnClickListener(this);


        tv_width_1.setOnClickListener(this);
        tv_width_2.setOnClickListener(this);
        tv_width_3.setOnClickListener(this);
        tv_width_4.setOnClickListener(this);


        layout_size_1.setOnClickListener(this);
        layout_size_2.setOnClickListener(this);
        layout_size_3.setOnClickListener(this);
        layout_size_4.setOnClickListener(this);
        layout_size_5.setOnClickListener(this);
        layout_size_6.setOnClickListener(this);
        layout_size_7.setOnClickListener(this);
        layout_size_8.setOnClickListener(this);
        layout_size_9.setOnClickListener(this);
        layout_size_10.setOnClickListener(this);
        layout_size_11.setOnClickListener(this);
        layout_size_12.setOnClickListener(this);
        layout_size_13.setOnClickListener(this);
        layout_size_14.setOnClickListener(this);
        layout_size_15.setOnClickListener(this);
        layout_size_16.setOnClickListener(this);
        layout_size_17.setOnClickListener(this);
        layout_size_18.setOnClickListener(this);
        layout_size_19.setOnClickListener(this);
        layout_size_20.setOnClickListener(this);
        layout_size_21.setOnClickListener(this);
        layout_size_22.setOnClickListener(this);
        layout_size_23.setOnClickListener(this);
        layout_size_24.setOnClickListener(this);
        layout_size_25.setOnClickListener(this);


        validate_user(clientId,skuId);

        setDefaultBrands();
        get_brands(gender,category);
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



    public void setDefaultBrands()
    {

        switch (clientId)
        {
            case "1ies8njemojfo":

                if (gender.equals("female"))
                {
                    tv_brand_1.setText(getResources().getString(R.string.footwear_brand_adidas));
                    tv_brand_2.setText(getResources().getString(R.string.footwear_brand_converse));
                    tv_brand_3.setText(getResources().getString(R.string.footwear_brand_nike));
                    tv_brand_4.setText(getResources().getString(R.string.footwear_brand_vans));
                    tv_brand_5.setText(getResources().getString(R.string.footwear_brand_puma));
                    tv_brand_6.setText(brand);
                }

                else if (gender.equals("male"))
                {
                    tv_brand_1.setText(getResources().getString(R.string.footwear_brand_adidas));
                    tv_brand_2.setText(getResources().getString(R.string.footwear_brand_converse));
                    tv_brand_3.setText(getResources().getString(R.string.footwear_brand_nike));
                    tv_brand_4.setText(getResources().getString(R.string.footwear_brand_vans));
                    tv_brand_5.setText(getResources().getString(R.string.footwear_brand_puma));
                    tv_brand_6.setText(brand);
                }
                break;

            case "ubu8zhxrh8hg":

                if (gender.equals("female"))
                {
                    tv_brand_1.setText(getResources().getString(R.string.footwear_brand_adidas));
                    tv_brand_2.setText(getResources().getString(R.string.footwear_brand_nike));
                    tv_brand_3.setText(getResources().getString(R.string.footwear_brand_onitsuka_tiger));
                    tv_brand_4.setText(getResources().getString(R.string.footwear_brand_vans));
                    tv_brand_5.setText(getResources().getString(R.string.footwear_brand_puma));
                    tv_brand_6.setText(brand);
                }

                else if (gender.equals("male"))
                {
                    tv_brand_1.setText(getResources().getString(R.string.footwear_brand_adidas));
                    tv_brand_2.setText(getResources().getString(R.string.footwear_brand_nike));
                    tv_brand_3.setText(getResources().getString(R.string.footwear_brand_onitsuka_tiger));
                    tv_brand_4.setText(getResources().getString(R.string.footwear_brand_vans));
                    tv_brand_5.setText(getResources().getString(R.string.footwear_brand_puma));
                    tv_brand_6.setText(brand);
                }
                break;

            case "sl8zvzsjelpg":

                if (gender.equals("female"))
                {
                    tv_brand_1.setText(getResources().getString(R.string.footwear_brand_adidas));
                    tv_brand_2.setText(getResources().getString(R.string.footwear_brand_nike));
                    tv_brand_3.setText(getResources().getString(R.string.footwear_brand_vans));
                    tv_brand_4.setText(getResources().getString(R.string.footwear_brand_melissa));
                    tv_brand_5.setText(getResources().getString(R.string.footwear_brand_puma));
                    tv_brand_6.setText(brand);
                }

                else if (gender.equals("male"))
                {
                    tv_brand_1.setText(getResources().getString(R.string.footwear_brand_adidas));
                    tv_brand_2.setText(getResources().getString(R.string.footwear_brand_nike));
                    tv_brand_3.setText(getResources().getString(R.string.footwear_brand_vans));
                    tv_brand_4.setText(getResources().getString(R.string.footwear_brand_converse));
                    tv_brand_5.setText(getResources().getString(R.string.footwear_brand_puma));
                    tv_brand_6.setText(brand);
                }
                break;

            case "qe3uhcp1kh11":

                if (gender.equals("female"))
                {
                    tv_brand_1.setText(getResources().getString(R.string.footwear_brand_adidas));
                    tv_brand_2.setText(getResources().getString(R.string.footwear_brand_nike));
                    tv_brand_3.setText(getResources().getString(R.string.footwear_brand_vans));
                    tv_brand_4.setText(getResources().getString(R.string.footwear_brand_melissa));
                    tv_brand_5.setText(getResources().getString(R.string.footwear_brand_puma));
                    tv_brand_6.setText(brand);
                }

                else if (gender.equals("male"))
                {
                    tv_brand_1.setText(getResources().getString(R.string.footwear_brand_adidas));
                    tv_brand_2.setText(getResources().getString(R.string.footwear_brand_converse));
                    tv_brand_3.setText(getResources().getString(R.string.footwear_brand_nike));
                    tv_brand_4.setText(getResources().getString(R.string.footwear_brand_vans));
                    tv_brand_5.setText(getResources().getString(R.string.footwear_brand_puma));
                    tv_brand_6.setText(brand);
                }
                break;

            case "1ib8r3kfef5fb":

                if (gender.equals("female"))
                {
                    tv_brand_1.setText(getResources().getString(R.string.footwear_brand_adidas));
                    tv_brand_2.setText(getResources().getString(R.string.footwear_brand_reebok));
                    tv_brand_3.setText(getResources().getString(R.string.footwear_brand_birkenstock));
                    tv_brand_4.setText(getResources().getString(R.string.footwear_brand_aldo));
                    tv_brand_5.setText(getResources().getString(R.string.footwear_brand_puma));
                    tv_brand_6.setText(brand);
                }

                else if (gender.equals("male"))
                {
                    tv_brand_1.setText(getResources().getString(R.string.footwear_brand_adidas));
                    tv_brand_2.setText(getResources().getString(R.string.footwear_brand_reebok));
                    tv_brand_3.setText(getResources().getString(R.string.footwear_brand_birkenstock));
                    tv_brand_4.setText(getResources().getString(R.string.footwear_brand_aldo));
                    tv_brand_5.setText(getResources().getString(R.string.footwear_brand_puma));
                    tv_brand_6.setText(brand);
                }
                break;
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {


            case R.id.tv_brand_1:

                clearAllBrands();

                brand = tv_brand_1.getText().toString();

                isBrandSelected = true;
                et_what_brand.setText(tv_brand_1.getText().toString());
                et_what_brand.setTextColor(getResources().getColor(R.color.black));
                layout_brand_search.setVisibility(View.GONE);
                tv_brand_continue.setBackgroundColor(getResources().getColor(R.color.color_background_button_enable));

                et_what_brand.setSelection(et_what_brand.getText().length());

                tv_brand_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_1.setTextColor(getResources().getColor(R.color.color_text));


                break;


            case R.id.tv_brand_2:

                clearAllBrands();

                brand = tv_brand_2.getText().toString();

                isBrandSelected = true;
                et_what_brand.setText(tv_brand_2.getText().toString());
                et_what_brand.setTextColor(getResources().getColor(R.color.black));
                layout_brand_search.setVisibility(View.GONE);
                tv_brand_continue.setBackgroundColor(getResources().getColor(R.color.color_background_button_enable));
                et_what_brand.setSelection(et_what_brand.getText().length());

                tv_brand_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_2.setTextColor(getResources().getColor(R.color.color_text));

                break;


            case R.id.tv_brand_3:

                clearAllBrands();

                brand = tv_brand_3.getText().toString();

                isBrandSelected = true;
                et_what_brand.setText(tv_brand_3.getText().toString());
                et_what_brand.setTextColor(getResources().getColor(R.color.black));
                layout_brand_search.setVisibility(View.GONE);
                tv_brand_continue.setBackgroundColor(getResources().getColor(R.color.color_background_button_enable));
                et_what_brand.setSelection(et_what_brand.getText().length());

                tv_brand_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_3.setTextColor(getResources().getColor(R.color.color_text));

                break;


            case R.id.tv_brand_4:

                clearAllBrands();

                brand = tv_brand_4.getText().toString();

                isBrandSelected = true;
                et_what_brand.setText(tv_brand_4.getText().toString());
                et_what_brand.setTextColor(getResources().getColor(R.color.black));
                layout_brand_search.setVisibility(View.GONE);
                tv_brand_continue.setBackgroundColor(getResources().getColor(R.color.color_background_button_enable));
                et_what_brand.setSelection(et_what_brand.getText().length());

                tv_brand_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_4.setTextColor(getResources().getColor(R.color.color_text));

                break;


            case R.id.tv_brand_5:

                clearAllBrands();

                brand = tv_brand_5.getText().toString();

                isBrandSelected = true;
                et_what_brand.setText(tv_brand_5.getText().toString());
                et_what_brand.setTextColor(getResources().getColor(R.color.black));
                layout_brand_search.setVisibility(View.GONE);
                tv_brand_continue.setBackgroundColor(getResources().getColor(R.color.color_background_button_enable));
                et_what_brand.setSelection(et_what_brand.getText().length());

                tv_brand_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_5.setTextColor(getResources().getColor(R.color.color_text));

                break;


            case R.id.tv_brand_6:

                clearAllBrands();

                brand = tv_brand_6.getText().toString();

                isBrandSelected = true;
                et_what_brand.setText(tv_brand_6.getText().toString());
                et_what_brand.setTextColor(getResources().getColor(R.color.black));
                layout_brand_search.setVisibility(View.GONE);
                tv_brand_continue.setBackgroundColor(getResources().getColor(R.color.color_background_button_enable));
                et_what_brand.setSelection(et_what_brand.getText().length());

                tv_brand_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_6.setTextColor(getResources().getColor(R.color.color_text));

                break;



            case R.id.tv_type_1:

                clearAllSize();

                sizeType = tv_type_1.getText().toString();

                tv_type_size.setText(sizeType);
                setBrandSize(brandSizeSuggestion,sizeType);
                layout_size_type.setVisibility(View.GONE);

                break;


            case R.id.tv_type_2:

                clearAllSize();
                sizeType = tv_type_2.getText().toString();

                tv_type_size.setText(sizeType);
                setBrandSize(brandSizeSuggestion,sizeType);
                layout_size_type.setVisibility(View.GONE);

                break;


            case R.id.tv_type_3:
                clearAllSize();
                sizeType = tv_type_3.getText().toString();

                tv_type_size.setText(sizeType);
                setBrandSize(brandSizeSuggestion,sizeType);
                layout_size_type.setVisibility(View.GONE);

                break;


            case R.id.tv_type_4:
                clearAllSize();
                sizeType = tv_type_4.getText().toString();

                tv_type_size.setText(sizeType);
                setBrandSize(brandSizeSuggestion,sizeType);
                layout_size_type.setVisibility(View.GONE);

                break;


            case R.id.tv_type_5:
                clearAllSize();
                sizeType = tv_type_5.getText().toString();

                tv_type_size.setText(sizeType);
                setBrandSize(brandSizeSuggestion,sizeType);
                layout_size_type.setVisibility(View.GONE);

                break;


            case R.id.tv_type_6:
                clearAllSize();
                sizeType = tv_type_6.getText().toString();

                tv_type_size.setText(sizeType);
                setBrandSize(brandSizeSuggestion,sizeType);
                layout_size_type.setVisibility(View.GONE);

                break;


            case R.id.tv_type_7:
                clearAllSize();
                sizeType = tv_type_7.getText().toString();

                tv_type_size.setText(sizeType);
                setBrandSize(brandSizeSuggestion,sizeType);
                layout_size_type.setVisibility(View.GONE);

                break;


            case R.id.tv_type_8:

                clearAllSize();
                sizeType = tv_type_8.getText().toString();

                tv_type_size.setText(sizeType);
                setBrandSize(brandSizeSuggestion,sizeType);
                layout_size_type.setVisibility(View.GONE);

                break;


            case R.id.layout_size_1:

                clearSelectedSizes();


                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                layout_size_1.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_1.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_1.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_2:

                clearSelectedSizes();

                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                layout_size_2.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_2.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_2.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_3:

                clearSelectedSizes();

                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                layout_size_3.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_3.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_3.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_4:

                clearSelectedSizes();

                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                layout_size_4.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_4.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_4.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_5:

                clearSelectedSizes();

                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                layout_size_5.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_5.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_5.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_6:

                clearSelectedSizes();

                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                layout_size_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_6.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_6.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_7:

                clearSelectedSizes();

                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));

                layout_size_7.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_7.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_7.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_8:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_8.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_8.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_8.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_9:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_9.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_9.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_9.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_10:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_10.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_10.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_10.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_11:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_11.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_11.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_11.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_12:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_12.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_12.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_12.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_13:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_13.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_13.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_13.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_14:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_14.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_14.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_14.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_15:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_15.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_15.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_15.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_16:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_16.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_16.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_16.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_17:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_17.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_17.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_17.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_18:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_18.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_18.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_18.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_19:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_19.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_19.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_19.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_20:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_20.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_20.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_20.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_21:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_21.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_21.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_21.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_22:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_22.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_22.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_22.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_23:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_23.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_23.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_23.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_24:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_24.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_24.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_24.getText().toString().toLowerCase();

                break;


            case R.id.layout_size_25:

                clearSelectedSizes();
                isSizeSelected = true;
                tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
                layout_size_25.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_size_25.setTextColor(getResources().getColor(R.color.color_text));

                size = tv_size_25.getText().toString().toLowerCase();

                break;


            case R.id.tv_width_1:

                width = tv_width_1.getText().toString();

                tv_width.setText(width);

                layout_width_type.setVisibility(View.GONE);

                break;


            case R.id.tv_width_2:

                width = tv_width_2.getText().toString();

                tv_width.setText(width);

                layout_width_type.setVisibility(View.GONE);

                break;


            case R.id.tv_width_3:

                width = tv_width_3.getText().toString();

                tv_width.setText(width);

                layout_width_type.setVisibility(View.GONE);

                break;


            case R.id.tv_width_4:

                width = tv_width_4.getText().toString();

                tv_width.setText(width);

                layout_width_type.setVisibility(View.GONE);

                break;

        }
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

        tv_brand_6.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_brand_6.setTextColor(getResources().getColor(R.color.color_text_grey));
    }


    public void clearAllSize()
    {

        layout_size_1.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_1.setVisibility(View.INVISIBLE);
        tv_size_1.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_2.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_2.setVisibility(View.INVISIBLE);
        tv_size_2.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_3.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_3.setVisibility(View.INVISIBLE);
        tv_size_3.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_4.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_4.setVisibility(View.INVISIBLE);
        tv_size_4.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_5.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_5.setVisibility(View.INVISIBLE);
        tv_size_5.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_6.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_6.setVisibility(View.INVISIBLE);
        tv_size_6.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_7.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_7.setVisibility(View.INVISIBLE);
        tv_size_7.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_8.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_8.setVisibility(View.INVISIBLE);
        tv_size_8.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_9.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_9.setVisibility(View.INVISIBLE);
        tv_size_9.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_10.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_10.setVisibility(View.INVISIBLE);
        tv_size_10.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_11.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_11.setVisibility(View.INVISIBLE);
        tv_size_11.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_12.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_12.setVisibility(View.INVISIBLE);
        tv_size_12.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_13.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_13.setVisibility(View.INVISIBLE);
        tv_size_13.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_14.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_14.setVisibility(View.INVISIBLE);
        tv_size_14.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_15.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_15.setVisibility(View.INVISIBLE);
        tv_size_15.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_16.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_16.setVisibility(View.INVISIBLE);
        tv_size_16.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_17.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_17.setVisibility(View.INVISIBLE);
        tv_size_17.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_18.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_18.setVisibility(View.INVISIBLE);
        tv_size_18.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_19.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_19.setVisibility(View.INVISIBLE);
        tv_size_19.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_20.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_20.setVisibility(View.INVISIBLE);
        tv_size_20.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_21.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_21.setVisibility(View.INVISIBLE);
        tv_size_21.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_22.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_22.setVisibility(View.INVISIBLE);
        tv_size_22.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_23.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_23.setVisibility(View.INVISIBLE);
        tv_size_23.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_24.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_24.setVisibility(View.INVISIBLE);
        tv_size_24.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_25.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        layout_size_25.setVisibility(View.INVISIBLE);
        tv_size_25.setTextColor(getResources().getColor(R.color.color_text_grey));


        layout_next_1.setVisibility(View.INVISIBLE);
        layout_back_1.setVisibility(View.INVISIBLE);
        layout_next_2.setVisibility(View.INVISIBLE);
        layout_back_2.setVisibility(View.INVISIBLE);
        layout_next_3.setVisibility(View.INVISIBLE);
        layout_back_3.setVisibility(View.INVISIBLE);
        layout_next_4.setVisibility(View.INVISIBLE);
        layout_back_4.setVisibility(View.INVISIBLE);
        layout_next_5.setVisibility(View.INVISIBLE);
        layout_back_5.setVisibility(View.INVISIBLE);
        layout_next_6.setVisibility(View.INVISIBLE);


        layout_size_row1.setVisibility(View.VISIBLE);
        layout_size_row2.setVisibility(View.GONE);
        layout_size_row3.setVisibility(View.GONE);
        layout_size_row4.setVisibility(View.GONE);
        layout_size_row5.setVisibility(View.GONE);
        layout_size_row6.setVisibility(View.GONE);

        isSizeSelected = false ;

        tv_brand_size_continue.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));

    }


    public void clearSelectedSizes()
    {
        tv_erroe_size.setVisibility(View.GONE);

        layout_size_1.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_1.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_2.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_2.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_3.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_3.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_4.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_4.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_5.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_5.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_6.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_6.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_7.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_7.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_8.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_8.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_9.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_9.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_10.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_10.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_11.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_11.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_12.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_12.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_13.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_13.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_14.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_14.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_15.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_15.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_16.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_16.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_17.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_17.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_18.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_18.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_19.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_19.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_20.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_20.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_21.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_21.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_22.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_22.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_23.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_23.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_24.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_24.setTextColor(getResources().getColor(R.color.color_text_grey));

        layout_size_25.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));
        tv_size_25.setTextColor(getResources().getColor(R.color.color_text_grey));
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
                    GET get = new GET(this, URI.validate +clientId+"/"+skuId+"?uid="+altId , ValidateUserUid, this);
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

    private void get_brand_category(String gender ,String category ,String brand) {


        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {
                GET get = new GET(this, URI.base2 +"brands/list?gender="+gender.toLowerCase()+"&category="+category.toLowerCase()+"&brand="+brand , BrandCategorySuggestion, this);
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

    private void get_brand_sizes(String gender  ,String category ,String brand) {


        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {
                GET get = new GET(this, URI.base2 +"brands/charts/"+gender.toLowerCase()+"/"+category.toLowerCase()+"/"+brand , BrandSizeSuggestion, this);
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


    private void getFinalSize(String uID, String brand, String sizeType , String size , String footwearWidthType , String model , String categoryType) {

        layout_loading.setVisibility(View.VISIBLE);

        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {




                if (sizeType.equals(""))
                {
                    GET get = new GET(this, "https://sizeguidev2.pixibo.com/asset/"+clientId+"/"+skuId+"?uid="+uID+"&brand="+brand+"&size="+size+"&footwearWidthType="+footwearWidthType.toLowerCase()+"&model="+model+"&categoryType="+categoryType , GetSize, this);
                    get.execute();
                }
                else
                {
                    GET get = new GET(this, "https://sizeguidev2.pixibo.com/asset/"+clientId+"/"+skuId+"?uid="+uID+"&brand="+brand+"&sizeType="+sizeType+"&size="+size+"&footwearWidthType="+footwearWidthType.toLowerCase()+"&model="+model+"&categoryType="+categoryType , GetSize, this);
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

    private void trackEvent(String clientID ,String SKUID,String eventType,String page,String event,String uid ) {


        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {
                GET get = new GET(this, "https://sizeguidev2.pixibo.com/event/"+clientID+"/"+SKUID+"?eventType="+eventType+"&page="+page+"&event="+event+"&uid="+uid+"&source=app" , Track, this);
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


    private void updateUserDetails() {

        try {

            JSONObject object = new JSONObject();
            JSONObject femaleObject = new JSONObject();
            JSONObject maleObject = new JSONObject();
            JSONObject heelToToe = new JSONObject();
            JSONObject whois = new JSONObject();
            JSONObject referenceBrands = new JSONObject();
            JSONArray referenceBrandsArray = new JSONArray();

            object.put("uid",uID);
            object.put("gender",gender);
            object.put("altId",altId);
            object.put("type",category.toLowerCase());


            Log.e("brand",brand);
            Log.e("sizeType",sizeType);
            Log.e("size",size);
            Log.e("footwearWidthType",width);
            Log.e("categoryType",categoryType);
            Log.e("model",categoryModel);
            Log.e("category",category);

            if (gender.equals("male"))
            {

                heelToToe.put("htt",0);

                referenceBrands.put("brand",brand);
                referenceBrands.put("category",category);
                referenceBrands.put("categoryType",categoryType);
                referenceBrands.put("footwearWidthType",width);

                if (!categoryModel.equals(""))
                {
                    referenceBrands.put("model",categoryModel);
                }

                referenceBrands.put("size",size);
                referenceBrands.put("sizeType",sizeType);


                referenceBrandsArray.put(0,referenceBrands);

                maleObject.put("referenceBrands",referenceBrandsArray);
                maleObject.put("heelToToe",heelToToe);

                whois.put("male",maleObject);
            }

            else if (gender.equals("female"))
            {

//                femaleObject.put("height",Integer.parseInt(localData.getHeight()));

                heelToToe.put("htt",0);

                referenceBrands.put("brand",brand);
                referenceBrands.put("category",category);
                referenceBrands.put("categoryType",categoryType);
                referenceBrands.put("footwearWidthType",width);

                if (!categoryModel.equals(""))
                {
                    referenceBrands.put("model",categoryModel);
                }


                referenceBrands.put("size",size);
                referenceBrands.put("sizeType",sizeType);

                referenceBrandsArray.put(0,referenceBrands);

                femaleObject.put("referenceBrands",referenceBrandsArray);
                femaleObject.put("heelToToe",heelToToe);
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

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;

            case BrandCategorySuggestion:
                try
                {
                    if (statusCode == 200)
                    {
                        JSONArray jsonArray = new JSONArray(result);

                        if (jsonArray.length() > 0)
                        {
                            brandCategorySuggestion = result ;
                            setBrandCategory(result);

                            tv_brand_category.setText(getResources().getString(R.string.brand_category_select));
                            tv_brand_category.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
                            layout_brand_category_selection.setVisibility(View.GONE);
                        }
                        else
                        {

                        }
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;


            case BrandSizeSuggestion:
                try
                {
                    if (statusCode == 200)
                    {

                        JSONObject jsonObject = new JSONObject(result);

                        JSONArray jsonArray = new JSONArray(jsonObject.getString("regular"));

                        if (jsonArray.length() > 0)
                        {
                            brandSizeSuggestion = jsonObject.getString("regular") ;

                            setBrandSize(brandSizeSuggestion,jsonArray.getJSONObject(0).getString("region"));


                        }
                        else
                        {

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
                    if (statusCode == 200)
                    {

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                layout_brand_size.setVisibility(View.GONE);
                                layout_loading.setVisibility(View.GONE);
                                layout_result.setVisibility(View.VISIBLE);

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

                                if (fysObject.getBoolean("recommended"))
                                {
                                    isRecommended = true ;

                                    size = fysObject.getString("size");

                                    layout_recommended.setVisibility(View.VISIBLE);
                                    tv_not_recommended.setVisibility(View.GONE);

                                    tv_add_cart.setText(getResources().getString(R.string.apparel_result_add_to_cart));

                                    tv_size_result.setText(size);
                                }

                                else
                                {
                                    isRecommended = false ;

                                    layout_recommended.setVisibility(View.GONE);

                                    tv_not_recommended.setVisibility(View.VISIBLE);

                                    tv_add_cart.setText(getResources().getString(R.string.footwear_result_continue));
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

            case ValidateUserUid:
                try
                {
                    if (statusCode == 200)
                    {

                        JSONObject userObject = new JSONObject(result);

                        if(userObject.has("userInfo"))
                        {
                            JSONObject userInfoObject = new JSONObject(userObject.optString("userInfo"));

                            if (!userInfoObject.optString("uid").equals(null))
                            {
                                uID = userInfoObject.optString("uid");
                            }

                            if (userInfoObject.has("whois"))
                            {

                                JSONObject whoIs = new JSONObject(userInfoObject.optString("whois"));

                                Log.e("whoIs male", String.valueOf(whoIs.has("male")));
                                Log.e("whoIs female", String.valueOf(whoIs.has("female")));

                                if (userInfoObject.optString("whois").contains("female") && userObject.optString("gender").equals("female") &&!new JSONObject(userInfoObject.optString("whois")).optString("female").equals(null) && !new JSONObject(userInfoObject.optString("whois")).optString("female").equals("{}"))
                                {
                                    JSONObject whoisObject = new JSONObject(userInfoObject.optString("whois"));
                                    JSONObject femaleObject = new JSONObject(whoisObject.optString("female"));

                                    Log.e("femaleObject", String.valueOf(femaleObject));


                                    JSONArray femaleReferenceBrandsArray = new JSONArray(femaleObject.optString("referenceBrands"));

                                    if(femaleReferenceBrandsArray.length() > 0){
                                        JSONObject femaleReferenceBrandsObject = null;
                                        for (int i = 0; i<femaleReferenceBrandsArray.length(); i++){
                                            JSONObject referenceObject = femaleReferenceBrandsArray.getJSONObject(i);
                                            String category= referenceObject.optString("category");
                                            if(Arrays.asList(footwear_array).contains(category)){

                                                femaleReferenceBrandsObject = referenceObject;

                                                if(femaleReferenceBrandsObject != null){

                                                    brand = femaleReferenceBrandsObject.optString("brand");
                                                    categoryModel = femaleReferenceBrandsObject.optString("model");
                                                    categoryType = femaleReferenceBrandsObject.optString("categoryType");
                                                    width = femaleReferenceBrandsObject.optString("footwearWidthType");
                                                    sizeType = femaleReferenceBrandsObject.optString("sizeType");
                                                    size = femaleReferenceBrandsObject.optString("size");

                                                    setScreen();
                                                }
                                                else
                                                {
                                                    setScreen();
                                                }
                                            }
                                        }

                                    }
                                    else
                                    {
                                        setScreen();
                                    }


                                }

                                else if (whoIs.has("male") && !new JSONObject(userInfoObject.optString("whois")).optString("male").equals(null) && !new JSONObject(userInfoObject.optString("whois")).optString("male").equals("{}") && userObject.optString("gender").equals("male"))
                                {
                                    JSONObject whoisObject = new JSONObject(userInfoObject.optString("whois"));

                                    JSONObject maleObject = new JSONObject(whoisObject.optString("male"));

                                    Log.e("maleObject", String.valueOf(maleObject));

                                    JSONArray maleReferenceBrandsArray = new JSONArray(maleObject.optString("referenceBrands"));
                                    if(maleReferenceBrandsArray.length() > 0){
                                        JSONObject maleReferenceBrandsObject = null;
                                        for (int i = 0; i<maleReferenceBrandsArray.length(); i++){
                                            JSONObject referenceObject = maleReferenceBrandsArray.getJSONObject(i);
                                            String category= referenceObject.optString("category");
                                            if(Arrays.asList(footwear_array).contains(category)){
                                                maleReferenceBrandsObject = referenceObject;

                                                if(maleReferenceBrandsObject != null){
                                                    brand = maleReferenceBrandsObject.optString("brand");
                                                    categoryModel = maleReferenceBrandsObject.optString("model");
                                                    categoryType = maleReferenceBrandsObject.optString("categoryType");
                                                    width = maleReferenceBrandsObject.optString("footwearWidthType");
                                                    sizeType = maleReferenceBrandsObject.optString("sizeType");
                                                    size = maleReferenceBrandsObject.optString("size");
                                                }
                                            }
                                            else
                                            {
                                                setScreen();
                                            }
                                        }
                                    }

                                    else
                                    {
                                        setScreen();
                                    }

                                }
                                else
                                {
                                    layout_brand.setVisibility(View.VISIBLE);
                                }
                            }

                        }
                        else
                        {
                            layout_brand.setVisibility(View.VISIBLE);
                        }


                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
        }
    }


    public void setBrandCategory(String result)
    {
        try {

            JSONArray range = new JSONArray(result);

            brandCategoryArrayList.clear();



            if (range.length() < 4)
            {
                final float scale = getResources().getDisplayMetrics().density;
                int pixels = (int) (200 * scale + 0.4f);
                recycler_brand_category_selection.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, pixels));
            }


            for (int i = 0; i < range.length(); i++)
            {

                JSONObject obj = range.getJSONObject(i);

                Log.e("category",obj.getString("category"));

                final float scale = getResources().getDisplayMetrics().density;
                int pixels = (int) (200 * scale + 0.5f);

                recycler_brand_category_selection.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, pixels));

                BrandModel brandModel = new BrandModel();


                switch (obj.getString("category"))
                {
                    case "Flip-flops & Sandals":
                        brandModel.setName(getResources().getString(R.string.shoe_flipflop));
                        brandModel.setId(obj.getString("category"));
                        break;

                    case "Boots":

                        if (gender.equals("male"))
                        {
                            brandModel.setName(getResources().getString(R.string.shoe_boots_male));
                            brandModel.setId(obj.getString("category"));
                        }
                        else if (gender.equals("female"))
                        {
                            brandModel.setName(getResources().getString(R.string.shoe_boots_female));
                            brandModel.setId(obj.getString("category"));
                        }
                        break;

                    case "Sneakers":
                        brandModel.setName(getResources().getString(R.string.shoe_sneakers));
                        brandModel.setId(obj.getString("category"));
                        break;

                    case "Hiking & Trail":
                        brandModel.setName(getResources().getString(R.string.shoe_hiking));
                        brandModel.setId(obj.getString("category"));
                        break;

                    case "Platforms & Wedges":
                        brandModel.setName(getResources().getString(R.string.shoe_wedges));
                        brandModel.setId(obj.getString("category"));
                        break;

                    case "Running":
                        brandModel.setName(getResources().getString(R.string.shoe_running));
                        brandModel.setId(obj.getString("category"));
                        break;

                    case "Training & Gym":
                        brandModel.setName(getResources().getString(R.string.shoe_gym));
                        brandModel.setId(obj.getString("category"));
                        break;

                    case "Flats":
                        brandModel.setName(getResources().getString(R.string.shoe_flats));
                        brandModel.setId(obj.getString("category"));
                        break;

                    case "Heels & Pumps":
                        brandModel.setName(getResources().getString(R.string.shoe_heels));
                        brandModel.setId(obj.getString("category"));
                        break;

                    case "Mules":
                        brandModel.setName(getResources().getString(R.string.shoe_mules));
                        brandModel.setId(obj.getString("category"));
                        break;

                    case "Loafers":
                        brandModel.setName(getResources().getString(R.string.shoe_loafers));
                        brandModel.setId(obj.getString("category"));
                        break;

                    case "Oxfords":
                        if (gender.equals("male"))
                        {
                            brandModel.setName(getResources().getString(R.string.shoe_oxfords_male));
                            brandModel.setId(obj.getString("category"));
                        }
                        else if (gender.equals("female"))
                        {
                            brandModel.setName(getResources().getString(R.string.shoe_oxfords_female));
                            brandModel.setId(obj.getString("category"));
                        }
                        break;
                }

                brandCategoryArrayList.add(brandModel);

            }

            Collections.sort(brandCategoryArrayList, new Comparator<BrandModel>() {

                @Override
                public int compare(BrandModel brandModel, BrandModel t1) {
                    return brandModel.getName().compareToIgnoreCase(t1.getName());
                }
            });


            brandCategoryAdapter.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void setSize(String result)
    {
        try {

            JSONArray charts = new JSONArray(result);


            for (int i = 0; i < charts.length(); i++)
            {

                JSONObject chartsObj = charts.getJSONObject(i);

                Log.e("size",chartsObj.getString("size"));

                if ( i == 0)
                {

                    tv_size_1.setText(chartsObj.getString("size"));
                    layout_size_1.setVisibility(View.VISIBLE);
                }

                if (i == 1)
                {
                    tv_size_2.setText(chartsObj.getString("size"));
                    layout_size_2.setVisibility(View.VISIBLE);
                }

                if (i == 2)
                {
                    tv_size_3.setText(chartsObj.getString("size"));
                    layout_size_3.setVisibility(View.VISIBLE);
                }

                if (i == 3)
                {
                    tv_size_4.setText(chartsObj.getString("size"));
                    layout_size_4.setVisibility(View.VISIBLE);
                }

                if (i == 4)
                {
                    tv_size_5.setText(chartsObj.getString("size"));
                    layout_size_5.setVisibility(View.VISIBLE);
                }

                if (i == 5)
                {
                    tv_size_6.setText(chartsObj.getString("size"));
                    layout_size_6.setVisibility(View.VISIBLE);
                    layout_next_1.setVisibility(View.VISIBLE);
                    layout_back_1.setVisibility(View.VISIBLE);
                }

                if (i == 6)
                {
                    tv_size_7.setText(chartsObj.getString("size"));
                    layout_size_7.setVisibility(View.VISIBLE);
                }

                if (i == 7)
                {
                    tv_size_8.setText(chartsObj.getString("size"));
                    layout_size_8.setVisibility(View.VISIBLE);
                }

                if (i == 8)
                {
                    tv_size_9.setText(chartsObj.getString("size"));
                    layout_size_9.setVisibility(View.VISIBLE);
                }

                if (i == 9)
                {
                    tv_size_10.setText(chartsObj.getString("size"));
                    layout_size_10.setVisibility(View.VISIBLE);
                    layout_next_2.setVisibility(View.VISIBLE);
                    layout_back_2.setVisibility(View.VISIBLE);
                }

                if (i == 10)
                {
                    tv_size_11.setText(chartsObj.getString("size"));
                    layout_size_11.setVisibility(View.VISIBLE);
                }

                if (i == 11)
                {
                    tv_size_12.setText(chartsObj.getString("size"));
                    layout_size_12.setVisibility(View.VISIBLE);
                }

                if (i == 12)
                {
                    tv_size_13.setText(chartsObj.getString("size"));
                    layout_size_13.setVisibility(View.VISIBLE);
                }

                if (i == 13)
                {
                    tv_size_14.setText(chartsObj.getString("size"));
                    layout_size_14.setVisibility(View.VISIBLE);
                    layout_next_3.setVisibility(View.VISIBLE);
                    layout_back_3.setVisibility(View.VISIBLE);
                }

                if (i == 14)
                {
                    tv_size_15.setText(chartsObj.getString("size"));
                    layout_size_15.setVisibility(View.VISIBLE);
                }

                if (i == 15)
                {
                    tv_size_16.setText(chartsObj.getString("size"));
                    layout_size_16.setVisibility(View.VISIBLE);
                }

                if (i == 16)
                {
                    tv_size_17.setText(chartsObj.getString("size"));
                    layout_size_17.setVisibility(View.VISIBLE);
                }

                if (i == 17)
                {
                    tv_size_18.setText(chartsObj.getString("size"));
                    layout_size_18.setVisibility(View.VISIBLE);
                    layout_next_4.setVisibility(View.VISIBLE);
                    layout_back_4.setVisibility(View.VISIBLE);
                }

                if (i == 18)
                {
                    tv_size_19.setText(chartsObj.getString("size"));
                    layout_size_19.setVisibility(View.VISIBLE);
                }

                if (i == 19)
                {
                    tv_size_20.setText(chartsObj.getString("size"));
                    layout_size_20.setVisibility(View.VISIBLE);
                }

                if (i == 20)
                {
                    tv_size_21.setText(chartsObj.getString("size"));
                    layout_size_21.setVisibility(View.VISIBLE);
                }

                if (i == 21)
                {
                    tv_size_22.setText(chartsObj.getString("size"));
                    layout_size_22.setVisibility(View.VISIBLE);
                    layout_next_5.setVisibility(View.VISIBLE);
                    layout_back_5.setVisibility(View.VISIBLE);
                }

                if (i == 22)
                {
                    tv_size_23.setText(chartsObj.getString("size"));
                    layout_size_23.setVisibility(View.VISIBLE);
                }

                if (i == 23)
                {
                    tv_size_24.setText(chartsObj.getString("size"));
                    layout_size_24.setVisibility(View.VISIBLE);
                }

                if (i == 24)
                {
                    tv_size_25.setText(chartsObj.getString("size"));
                    layout_size_25.setVisibility(View.VISIBLE);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void setBrandSize(String result , String region)
    {
        try {

            Log.e("region",region);

            JSONArray regular = new JSONArray(result);

            for (int i = 0; i < regular.length(); i++)
            {

                JSONObject obj = regular.getJSONObject(i);


                if (obj.getString("region").equals(region))
                {
                    setSize(region,obj);
                }

                if ( i == 0)
                {

                    tv_type_1.setText(obj.getString("region"));
                    tv_type_1.setVisibility(View.VISIBLE);

                }
                if ( i == 1)
                {
                    tv_type_2.setText(obj.getString("region"));
                    tv_type_2.setVisibility(View.VISIBLE);
                }
                if ( i == 2)
                {
                    tv_type_3.setText(obj.getString("region"));
                    tv_type_3.setVisibility(View.VISIBLE);
                }
                if ( i == 3)
                {
                    tv_type_4.setText(obj.getString("region"));
                    tv_type_4.setVisibility(View.VISIBLE);
                }
                if ( i == 4)
                {
                    tv_type_5.setText(obj.getString("region"));
                    tv_type_5.setVisibility(View.VISIBLE);
                }
                if ( i == 5)
                {
                    tv_type_6.setText(obj.getString("region"));
                    tv_type_6.setVisibility(View.VISIBLE);
                }
                if ( i == 6)
                {
                    tv_type_7.setText(obj.getString("region"));
                    tv_type_7.setVisibility(View.VISIBLE);
                }
                if ( i == 7)
                {
                    tv_type_8.setText(obj.getString("region"));
                    tv_type_8.setVisibility(View.VISIBLE);
                }




                //loop is repeating here in switch condition


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void setSize(String region ,JSONObject obj)
    {
        try
        {

            sizeType = region;
            size = "";

            clearSelectedSizes();



            switch (region)
        {
            case "EU":
                tv_type_size.setText(obj.getString("region"));
                setSize(obj.getString("charts"));
                break;

            case "US":
                tv_type_size.setText(obj.getString("region"));
                setSize(obj.getString("charts"));
                break;

            case "UK":
                tv_type_size.setText(obj.getString("region"));
                setSize(obj.getString("charts"));
                break;

            case "DE":
                tv_type_size.setText(obj.getString("region"));
                setSize(obj.getString("charts"));
                break;

            case "FR":
                tv_type_size.setText(obj.getString("region"));
                setSize(obj.getString("charts"));
                break;

            case "IT":
                tv_type_size.setText(obj.getString("region"));
                setSize(obj.getString("charts"));
                break;

            case "ES":
                tv_type_size.setText(obj.getString("region"));
                setSize(obj.getString("charts"));
                break;

            case "JP":
                tv_type_size.setText(obj.getString("region"));
                setSize(obj.getString("charts"));
                break;

            case "MX":
                tv_type_size.setText(obj.getString("region"));
                setSize(obj.getString("charts"));
                break;
        }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }




    public void setBrandCategoryModel(String categoryType)
    {
        try {

            brandCategoryModelArrayList.clear();

            tv_brand_category_model.setText(getResources().getString(R.string.brand_category_select));
            tv_brand_category_model.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));

            Log.e("BrandCategory",categoryType);

            JSONArray category = new JSONArray(brandCategorySuggestion);

            for (int i = 0; i < category.length(); i++)
            {

                JSONObject obj = category.getJSONObject(i);


                if (obj.getString("category").equals(categoryType))
                {

                    String[] strArray = null;

                    if (!obj.optString("models").equals("[]"))
                    {
                        strArray = obj.optString("models").split(",") ;
                    }


                    if (strArray == null)
                    {
                        Log.e("Length","0");
                        isBrandModelPresent = false ;
                    }


                    else
                    {

                        if (strArray.length < 4)
                        {
                            final float scale = getResources().getDisplayMetrics().density;
                            int pixels = (int) (200 * scale + 0.4f);
                            recycler_brand_category_model.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, pixels));
                        }

                        for (int j = 0; j < strArray.length; j++)
                        {

                            final float scale = getResources().getDisplayMetrics().density;
                            int pixels = (int) (200 * scale + 0.5f);

                            recycler_brand_category_model.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, pixels));

                            BrandModel brandModel = new BrandModel();
                            brandModel.setName(strArray[j].replaceAll("[\"^\\[\\]]",""));

                            brandCategoryModelArrayList.add(brandModel);


                            Log.e("models",strArray[j].replaceAll("[\"^\\[\\]]",""));

                            isBrandModelPresent = true;

                        }



                        Collections.sort(brandCategoryModelArrayList, new Comparator<BrandModel>() {

                            @Override
                            public int compare(BrandModel brandModel, BrandModel t1) {
                                return brandModel.getName().compareToIgnoreCase(t1.getName());
                            }
                        });

                        brandCategoryModelAdapter.notifyDataSetChanged();
                    }



                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void checkBrandSelection (String brand)
    {
        clearAllBrands();

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


    public void setCategoryImage(String category)
    {

        tv_brand_category_continue.setBackground(getResources().getDrawable(R.color.color_background_button_enable));

        iv_shoe_category.setVisibility(View.VISIBLE);

        switch (category)
        {
            case "Flip-flops & Sandals":
                iv_shoe_category.setImageDrawable(getResources().getDrawable(R.drawable.ic_flipflops));
                break;

            case "Boots":

                if (gender.equals("male"))
                {
                    iv_shoe_category.setImageDrawable(getResources().getDrawable(R.drawable.ic_boots_male));
                }
                else if (gender.equals("female"))
                {
                    iv_shoe_category.setImageDrawable(getResources().getDrawable(R.drawable.ic_boots_female));
                }
                break;

            case "Sneakers":
                iv_shoe_category.setImageDrawable(getResources().getDrawable(R.drawable.ic_sneaker));
                break;

            case "Hiking & Trail":
                iv_shoe_category.setImageDrawable(getResources().getDrawable(R.drawable.ic_hiking));
                break;

            case "Platforms & Wedges":
                iv_shoe_category.setImageDrawable(getResources().getDrawable(R.drawable.ic_wedges_female));
                break;

            case "Running":
                iv_shoe_category.setImageDrawable(getResources().getDrawable(R.drawable.ic_running));
                break;

            case "Training & Gym":
                iv_shoe_category.setImageDrawable(getResources().getDrawable(R.drawable.ic_training));
                break;

            case "Flats":
              //  iv_shoe_category.setImageDrawable(getResources().getDrawable(R.drawable.ic_running));
                break;

            case "Heels & Pumps":
                iv_shoe_category.setImageDrawable(getResources().getDrawable(R.drawable.ic_heels_female));
                break;

            case "Mules":
                iv_shoe_category.setImageDrawable(getResources().getDrawable(R.drawable.ic_mules_female));
                break;

            case "Loafers":
                iv_shoe_category.setImageDrawable(getResources().getDrawable(R.drawable.ic_loafers_male));
                break;

            case "Oxfords":
                if (gender.equals("male"))
                {
                    iv_shoe_category.setImageDrawable(getResources().getDrawable(R.drawable.ic_oxfords_male));
                }
                else if (gender.equals("female"))
                {
                    iv_shoe_category.setImageDrawable(getResources().getDrawable(R.drawable.ic_oxford_female));
                }
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


    public void setScreen()
    {

        Log.e("uid",uID);
        Log.e("gender",gender);
        Log.e("altId",altId);
        Log.e("type",category.toLowerCase());


        Log.e("brand",brand);
        Log.e("sizeType",sizeType);
        Log.e("size",size);
        Log.e("footwearWidthType",width);
        Log.e("categoryType",categoryType);
        Log.e("model",categoryModel);
        Log.e("category",category);
//
//
//        localData.setHeight(ht);
//        localData.setWeight(wt);
//        localData.setAge(age);
//        localData.setFitPreference(ftp);
//        localData.setRegion(rg);
//        localData.setBand(bs);
//        localData.setCup(cu);
//        localData.setBust(bu);
//        localData.setBrand(brand);
//        localData.setBrandRange(range);
//        localData.setsizeType(sizeType);
//        localData.setBrandSize(brandSize);
//

        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        final SpannableStringBuilder sb = new SpannableStringBuilder(getResources().getString(R.string.footwear_pick_category_model) + "" + brand + " "+ categoryType + " " + getResources().getString(R.string.footwear_pick_category__model2)+ getResources().getString(R.string.footwear_pick_category_optional));
        sb.setSpan(bss, sb.length() - getResources().getString(R.string.footwear_pick_category_optional).length(), sb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tv_category_model.setText(sb);

        final StyleSpan bsss = new StyleSpan(android.graphics.Typeface.BOLD);
        final SpannableStringBuilder sbb = new SpannableStringBuilder(getResources().getString(R.string.footwear_pick_category) + " " + brand + " " + getResources().getString(R.string.footwear_pick_category_2) + getResources().getString(R.string.footwear_pick_category_optional));
        sbb.setSpan(bsss, sbb.length() - getResources().getString(R.string.footwear_pick_category_optional).length(), sbb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tv_category.setText(sbb);



        if(!width.equals(""))
        {
            progress(6);
            if (altId.equals(""))
            {
                getFinalSize(uID,brand,sizeType,size,width,categoryModel,categoryType);
            }
            else
            {
                getFinalSize(altId,brand,sizeType,size,width,categoryModel,categoryType);
            }
        }
        else if(!categoryModel.equals(""))
        {
//            clearAllSize();
//            layout_brand_category_model.setVisibility(View.GONE);
//            layout_brand_size.setVisibility(View.VISIBLE);
//
//            progress(5);
//
//            get_brand_sizes(gender,category,brand);
//
//            if (categoryModel.equals(""))
//            {
//                tv_brand_fit.setText(getResources().getString(R.string.footwear_what_size) +" "+ brand +" "+categoryType+" "+getResources().getString(R.string.footwear_what_size_2));
//            }
//            else
//            {
//                tv_brand_fit.setText(getResources().getString(R.string.footwear_what_size) +" "+ brand +" "+categoryModel+" "+getResources().getString(R.string.footwear_what_size_2));
//            }
            layout_brand.setVisibility(View.VISIBLE);
        }
        else if(!categoryType.equals(""))
        {
//            get_brand_category(gender,category,brand);
//
//            layout_brand_category_model.setVisibility(View.VISIBLE);
//
//            progress(4);
//
//            categoryModel = "";
//
//            tv_brand_category_model.setText(getResources().getString(R.string.brand_category_select));
//            tv_brand_category_model.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
//
//            tv_brand_category_model_continue.setBackground(getResources().getDrawable(R.color.color_background_button_disabled));
//
//            layout_brand_category_model_selection.setVisibility(View.GONE);
            layout_brand.setVisibility(View.VISIBLE);
        }
        else if(!brand.equals(""))
        {
//            get_brand_category(gender,category,brand);
//
//            categoryType = "";
//
//            tv_brand_category_continue.setBackground(getResources().getDrawable(R.color.color_background_button_disabled));
//
//            progress(2);
//
//            layout_brand.setVisibility(View.GONE);
//            layout_brand_category.setVisibility(View.VISIBLE);
//
//            iv_shoe_category.setVisibility(View.GONE);
//
//            tv_brand_category.setText(getResources().getString(R.string.brand_category_select));
//            tv_brand_category.setBackground(getResources().getDrawable(R.drawable.bg_dropdown));
            layout_brand.setVisibility(View.VISIBLE);
        }
        else
        {
          progress(1);
          layout_brand.setVisibility(View.VISIBLE);
        }




    }


}
