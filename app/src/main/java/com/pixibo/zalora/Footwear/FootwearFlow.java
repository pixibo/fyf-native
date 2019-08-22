package com.pixibo.zalora.Footwear;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pixibo.zalora.Adapter.BrandAdapter;
import com.pixibo.zalora.Apparel.ApparelFlow;
import com.pixibo.zalora.Model.BrandModel;
import com.pixibo.zalora.R;
import com.pixibo.zalora.Utils.Utils;
import com.pixibo.zalora.Utils.WebResources.GET;
import com.pixibo.zalora.Utils.WebResources.NetworkUtils;
import com.pixibo.zalora.Utils.WebResources.Result;
import com.pixibo.zalora.Utils.WebResources.URI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.pixibo.zalora.Utils.Utils.TYPE.BrandCategorySuggestion;
import static com.pixibo.zalora.Utils.Utils.TYPE.BrandSuggestion;

public class FootwearFlow extends AppCompatActivity implements Result, View.OnClickListener {




    private ArrayList<BrandModel> brandModelArrayList = new ArrayList<>();
    private BrandAdapter brandAdapter;
    private RecyclerView recycler_brand_suggestion;

    private EditText et_what_brand;

    private boolean isBrandSelected = false;

    private RelativeLayout layout_brand_search,layout_add,layout_brands;
    private RelativeLayout layout_brand,layout_brand_category,layout_brand_category_model;
    private LinearLayout layout_brand_category_selection,layout_brand_category_model_selection;


    private String category = "";
    private String gender = "";

    private String clientId = "";
    private String skuId = "";
    private String altId = "";
    private String uID = "";
    private String brand = "";
    private String categoryType = "";
    private String categoryModel = "";


    private TextView tv_category,tv_brand_category,tv_category_model,tv_brand_category_model;

    private TextView tv_brand_1,tv_brand_2,tv_brand_3,tv_brand_4,tv_brand_5,tv_brand_6;
    private TextView tv_brand_category_1,tv_brand_category_2,tv_brand_category_3,tv_brand_category_4,tv_brand_category_5,tv_brand_category_6,tv_brand_category_7,tv_brand_category_8;
    private TextView tv_brand_category_model_1,tv_brand_category_model_2,tv_brand_category_model_3,tv_brand_category_model_4,tv_brand_category_model_5,tv_brand_category_model_6,tv_brand_category_model_7,tv_brand_category_model_8;
    private TextView tv_brand_error;
    private TextView tv_brand_continue,tv_brand_category_continue,tv_brand_category_model_continue;
    private TextView tv_back_brand_category,tv_back_brand_model_category;
    private RelativeLayout layout_progress_1,layout_progress_2,layout_progress_3,layout_progress_4,layout_progress_5,layout_progress_6;


    private ImageView iv_shoe_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footwear_flow);


        Intent intent = getIntent();

        category = intent.getStringExtra("dataType");
        gender = intent.getStringExtra("gender");

        brand = intent.getStringExtra("brand").toUpperCase();

        clientId = intent.getStringExtra("clientId");
        skuId = intent.getStringExtra("skuId");
        altId = intent.getStringExtra("altId");
        uID = intent.getStringExtra("uID");


        recycler_brand_suggestion = findViewById(R.id.recycler_brand_suggestion);

        iv_shoe_category = findViewById(R.id.iv_shoe_category);

        tv_category = findViewById(R.id.tv_category);
        tv_brand_category = findViewById(R.id.tv_brand_category);
        tv_brand_category_model = findViewById(R.id.tv_brand_category_model);
        tv_category_model = findViewById(R.id.tv_category_model);

        tv_brand_error = findViewById(R.id.tv_brand_error);

        tv_brand_1 = findViewById(R.id.tv_brand_1);
        tv_brand_2 = findViewById(R.id.tv_brand_2);
        tv_brand_3 = findViewById(R.id.tv_brand_3);
        tv_brand_4 = findViewById(R.id.tv_brand_4);
        tv_brand_5 = findViewById(R.id.tv_brand_5);
        tv_brand_6 = findViewById(R.id.tv_brand_6);

        tv_brand_category_1 = findViewById(R.id.tv_brand_category_1);
        tv_brand_category_2 = findViewById(R.id.tv_brand_category_2);
        tv_brand_category_3 = findViewById(R.id.tv_brand_category_3);
        tv_brand_category_4 = findViewById(R.id.tv_brand_category_4);
        tv_brand_category_5 = findViewById(R.id.tv_brand_category_5);
        tv_brand_category_6 = findViewById(R.id.tv_brand_category_6);
        tv_brand_category_7 = findViewById(R.id.tv_brand_category_7);
        tv_brand_category_8 = findViewById(R.id.tv_brand_category_8);


        tv_brand_category_model_1 = findViewById(R.id.tv_brand_category_model_1);
        tv_brand_category_model_2 = findViewById(R.id.tv_brand_category_model_2);
        tv_brand_category_model_3 = findViewById(R.id.tv_brand_category_model_3);
        tv_brand_category_model_4 = findViewById(R.id.tv_brand_category_model_4);
//        tv_brand_category_model_5 = findViewById(R.id.tv_brand_category_model_5);
//        tv_brand_category_model_6 = findViewById(R.id.tv_brand_category_model_6);
//        tv_brand_category_model_7 = findViewById(R.id.tv_brand_category_model_7);
//        tv_brand_category_model_8 = findViewById(R.id.tv_brand_category_model_8);


        layout_progress_1 = findViewById(R.id.layout_progress_1);
        layout_progress_2 = findViewById(R.id.layout_progress_2);
        layout_progress_3 = findViewById(R.id.layout_progress_3);
        layout_progress_4 = findViewById(R.id.layout_progress_4);
        layout_progress_5 = findViewById(R.id.layout_progress_5);
        layout_progress_6 = findViewById(R.id.layout_progress_6);

        tv_brand_continue = findViewById(R.id.tv_brand_continue);
        tv_brand_category_continue = findViewById(R.id.tv_brand_category_continue);
        tv_brand_category_model_continue = findViewById(R.id.tv_brand_category_model_continue);

        tv_back_brand_category = findViewById(R.id.tv_back_brand_category);
        tv_back_brand_model_category = findViewById(R.id.tv_back_brand_model_category);

        et_what_brand = findViewById(R.id.et_what_brand);

        layout_brand_search = findViewById(R.id.layout_brand_search);
        layout_add = findViewById(R.id.layout_add);
        layout_brands = findViewById(R.id.layout_brands);

        layout_brand_category_selection = findViewById(R.id.layout_brand_category_selection);
        layout_brand_category_model_selection = findViewById(R.id.layout_brand_category_model_selection);


        layout_brand = findViewById(R.id.layout_brand);
        layout_brand_category = findViewById(R.id.layout_brand_category);
        layout_brand_category_model = findViewById(R.id.layout_brand_category_model);


        brandAdapter = new BrandAdapter(brandModelArrayList, FootwearFlow.this, new BrandAdapter.onItemClickListener() {
            @Override
            public void onItemClick(BrandModel suggestions) {

                isBrandSelected = true;
                brand = suggestions.getName();
                et_what_brand.setText(suggestions.getName());
                et_what_brand.setTextColor(getResources().getColor(R.color.black));
                layout_brand_search.setVisibility(View.GONE);
                tv_brand_continue.setBackgroundColor(getResources().getColor(R.color.color_background_button_enable));
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recycler_brand_suggestion.setItemAnimator(new DefaultItemAnimator());
        recycler_brand_suggestion.setLayoutManager(mLayoutManager);
        recycler_brand_suggestion.setAdapter(brandAdapter);


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


                if (charSequence.toString().trim().length()>=1)
                {
                    brand_filter(charSequence.toString());

                    clearAllBrands();
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

                if (isBrandSelected) {

                    get_brand_category(gender,category,brand);

                    tv_category.setText(getResources().getString(R.string.footwear_pick_category) + " " + brand + " " + getResources().getString(R.string.footwear_pick_category_2));

                    categoryType = "";

                    tv_brand_category_continue.setBackground(getResources().getDrawable(R.color.color_background_button_disabled));

                    progress(2);

                    layout_brand.setVisibility(View.GONE);
                    layout_brand_category.setVisibility(View.VISIBLE);
                }

            }
        });


        tv_back_brand_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_brand_category.setVisibility(View.GONE);
                layout_brand.setVisibility(View.VISIBLE);
            }
        });


        tv_brand_category_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!categoryType.equals(""))
                {

                    progress(3);
                    tv_category_model.setText(getResources().getString(R.string.footwear_pick_category_model) + " " + brand + " "+ categoryType + " " + getResources().getString(R.string.footwear_pick_category__model2));

                    layout_brand_category.setVisibility(View.GONE);
                    layout_brand_category_model.setVisibility(View.VISIBLE);
                }


            }
        });

//        tv_brand_category_skip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        tv_back_brand_model_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progress(4);
                layout_brand_category_model.setVisibility(View.GONE);
                layout_brand_category.setVisibility(View.VISIBLE);
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




//        tv_category_model



        tv_brand_1.setOnClickListener(this);
        tv_brand_2.setOnClickListener(this);
        tv_brand_3.setOnClickListener(this);
        tv_brand_4.setOnClickListener(this);
        tv_brand_5.setOnClickListener(this);
        tv_brand_6.setOnClickListener(this);


        tv_brand_category_1.setOnClickListener(this);
        tv_brand_category_2.setOnClickListener(this);
        tv_brand_category_3.setOnClickListener(this);
        tv_brand_category_4.setOnClickListener(this);
        tv_brand_category_5.setOnClickListener(this);
        tv_brand_category_6.setOnClickListener(this);
        tv_brand_category_7.setOnClickListener(this);
        tv_brand_category_8.setOnClickListener(this);


        tv_brand_category_model_1.setOnClickListener(this);
        tv_brand_category_model_2.setOnClickListener(this);
        tv_brand_category_model_3.setOnClickListener(this);
        tv_brand_category_model_4.setOnClickListener(this);
//        tv_brand_category_model_5.setOnClickListener(this);
//        tv_brand_category_model_6.setOnClickListener(this);
//        tv_brand_category_model_7.setOnClickListener(this);
//        tv_brand_category_model_8.setOnClickListener(this);


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

                tv_brand_6.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                tv_brand_6.setTextColor(getResources().getColor(R.color.color_text));

                break;


            case R.id.tv_brand_category_1:

                categoryType = tv_brand_category_1.getText().toString();

                tv_brand_category.setText(categoryType);
                setCategoryImage(categoryType);



                layout_brand_category_selection.setVisibility(View.GONE);

                break;


            case R.id.tv_brand_category_2:

                categoryType = tv_brand_category_2.getText().toString();

                tv_brand_category.setText(categoryType);
                setCategoryImage(categoryType);
                layout_brand_category_selection.setVisibility(View.GONE);

                break;


            case R.id.tv_brand_category_3:

                categoryType = tv_brand_category_3.getText().toString();

                tv_brand_category.setText(categoryType);
                setCategoryImage(categoryType);
                layout_brand_category_selection.setVisibility(View.GONE);

                break;


            case R.id.tv_brand_category_4:

                categoryType = tv_brand_category_4.getText().toString();

                tv_brand_category.setText(categoryType);
                setCategoryImage(categoryType);
                layout_brand_category_selection.setVisibility(View.GONE);

                break;


            case R.id.tv_brand_category_5:

                categoryType = tv_brand_category_5.getText().toString();

                tv_brand_category.setText(categoryType);
                setCategoryImage(categoryType);
                layout_brand_category_selection.setVisibility(View.GONE);

                break;


            case R.id.tv_brand_category_6:

                categoryType = tv_brand_category_6.getText().toString();

                tv_brand_category.setText(categoryType);
                setCategoryImage(categoryType);
                layout_brand_category_selection.setVisibility(View.GONE);

                break;


            case R.id.tv_brand_category_7:

                categoryType = tv_brand_category_7.getText().toString();

                tv_brand_category.setText(categoryType);
                setCategoryImage(categoryType);
                layout_brand_category_selection.setVisibility(View.GONE);

                break;


            case R.id.tv_brand_category_8:

                categoryType = tv_brand_category_8.getText().toString();

                tv_brand_category.setText(categoryType);
                setCategoryImage(categoryType);
                layout_brand_category_selection.setVisibility(View.GONE);

                break;


            case R.id.tv_brand_category_model_1:

                categoryModel = tv_brand_category_model_1.getText().toString();

                tv_brand_category_model.setText(categoryModel);

                layout_brand_category_model_selection.setVisibility(View.GONE);

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
        }
    }


    public void setBrandCategory(String result)
    {
        try {

            JSONArray range = new JSONArray(result);


            for (int i = 0; i < range.length(); i++)
            {

                JSONObject obj = range.getJSONObject(i);

                Log.e("category",obj.getString("category"));

                if ( i == 0)
                {
                    tv_brand_category_1.setText(obj.getString("category"));
                    tv_brand_category_1.setVisibility(View.VISIBLE);
                }

                if (i == 1)
                {
                    tv_brand_category_2.setText(obj.getString("category"));
                    tv_brand_category_2.setVisibility(View.VISIBLE);
                }

                if (i == 2)
                {
                    tv_brand_category_3.setText(obj.getString("category"));
                    tv_brand_category_3.setVisibility(View.VISIBLE);
                }

                if (i == 3)
                {
                    tv_brand_category_4.setText(obj.getString("category"));
                    tv_brand_category_4.setVisibility(View.VISIBLE);
                }

                if (i == 4)
                {
                    tv_brand_category_5.setText(obj.getString("category"));
                    tv_brand_category_5.setVisibility(View.VISIBLE);
                }

                if (i == 5)
                {
                    tv_brand_category_6.setText(obj.getString("category"));
                    tv_brand_category_6.setVisibility(View.VISIBLE);
                }

                if (i == 6)
                {
                    tv_brand_category_7.setText(obj.getString("category"));
                    tv_brand_category_7.setVisibility(View.VISIBLE);
                }

                if (i == 7)
                {
                    tv_brand_category_8.setText(obj.getString("category"));
                    tv_brand_category_8.setVisibility(View.VISIBLE);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void setBrandCategoryModel(String result)
    {
        try {

            JSONArray range = new JSONArray(result);


            for (int i = 0; i < range.length(); i++)
            {

                JSONObject obj = range.getJSONObject(i);

                Log.e("category",obj.getString("category"));

                if ( i == 0)
                {
                    tv_brand_category_model_1.setText(obj.getString("category"));
                    tv_brand_category_model_1.setVisibility(View.VISIBLE);
                }

                if (i == 1)
                {
                    tv_brand_category_model_2.setText(obj.getString("category"));
                    tv_brand_category_model_2.setVisibility(View.VISIBLE);
                }

                if (i == 2)
                {
                    tv_brand_category_model_3.setText(obj.getString("category"));
                    tv_brand_category_model_3.setVisibility(View.VISIBLE);
                }

                if (i == 3)
                {
                    tv_brand_category_model_4.setText(obj.getString("category"));
                    tv_brand_category_model_4.setVisibility(View.VISIBLE);
                }

//                if (i == 4)
//                {
//                    tv_brand_category_model_5.setText(obj.getString("category"));
//                    tv_brand_category_model_5.setVisibility(View.VISIBLE);
//                }
//
//                if (i == 5)
//                {
//                    tv_brand_category_model_6.setText(obj.getString("category"));
//                    tv_brand_category_model_6.setVisibility(View.VISIBLE);
//                }
//
//                if (i == 6)
//                {
//                    tv_brand_category_model_7.setText(obj.getString("category"));
//                    tv_brand_category_model_7.setVisibility(View.VISIBLE);
//                }
//
//                if (i == 7)
//                {
//                    tv_brand_category_model_8.setText(obj.getString("category"));
//                    tv_brand_category_model_8.setVisibility(View.VISIBLE);
//                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void setCategoryImage(String category)
    {

        tv_brand_category_continue.setBackground(getResources().getDrawable(R.color.color_background_button_enable));

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
                iv_shoe_category.setImageDrawable(getResources().getDrawable(R.drawable.ic_sneakers));
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


}
