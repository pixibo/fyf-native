package com.pixibo.zalora.Bra;

import android.content.Intent;
import android.graphics.Typeface;
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
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

import static com.pixibo.zalora.Utils.Utils.TYPE.BrandSizes;
import static com.pixibo.zalora.Utils.Utils.TYPE.BrandSuggestion;
import static com.pixibo.zalora.Utils.Utils.TYPE.GetSize;
import static com.pixibo.zalora.Utils.Utils.TYPE.NewBrand;
import static com.pixibo.zalora.Utils.Utils.TYPE.UpdateData;

public class BraFlow extends AppCompatActivity implements Result, View.OnClickListener {


    private String preferredLanguage = "";
    private String clientId = "";
    private String skuId = "";
    private String altId = "";
    private String uID = "";

    private String gender = "";

    private String category = "";
    private String brand = "";
    private String cup = "";
    private String isWired = "";
    private boolean wired ;
    private String bandFit = "";
    private String cupFit = "";
    private String strapsFit = "";
    private String band = "";
    private String region = "";

    private String braSizeChart = "";
    private String size = "";

    private boolean isRecommended = false ;

    Typeface bold_text ;
    Typeface normal_text ;

    private TextView tv_brand_not_listed,tv_brand_error;

    private ArrayList<BrandModel> brandModelArrayList = new ArrayList<>();
    private ArrayList<BrandModel> braSizeTypeArrayList = new ArrayList<>();
    private BrandAdapter brandAdapter;
    private BraSizeTypeAdapter braSizeTypeAdapter;
    private RecyclerView recycler_brand_suggestion,recycler_bra_sizeType;
    private EditText et_what_brand;

    private RelativeLayout layout_brand_search,layout_add,layout_brands,layout_loading;
    private RelativeLayout layout_brand,layout_bra_profile,layout_result;
    private TextView tv_brand_continue,tv_bra_continue;
    private TextView tv_back_bra;
    private TextView tv_brand_1,tv_brand_2,tv_brand_3,tv_brand_4,tv_brand_5;

    private TextView tv_band_1,tv_band_2,tv_band_3,tv_band_4,tv_band_5,tv_band_6,tv_band_7,tv_band_8,tv_band_9,tv_band_10;
    private RelativeLayout layout_band_1,layout_band_2,layout_band_3,layout_band_4,layout_band_5,layout_band_6,layout_band_7,layout_band_8,layout_band_9,layout_band_10;

    private TextView tv_cup_1,tv_cup_2,tv_cup_3,tv_cup_4,tv_cup_5,tv_cup_6,tv_cup_7,tv_cup_8,tv_cup_9,tv_cup_10,tv_cup_11,tv_cup_12;
    private RelativeLayout layout_cup_1,layout_cup_2,layout_cup_3,layout_cup_4,layout_cup_5,layout_cup_6,layout_cup_7,layout_cup_8,layout_cup_9,layout_cup_10,layout_cup_11,layout_cup_12;

    private TextView tv_type_wired,tv_type_unwired;
    private RelativeLayout layout_type_wired,layout_type_unwired;

    private RelativeLayout layout_cup_back,layout_cup_next,layout_cup_next_2,layout_cup_back_2;
    private RelativeLayout layout_band_next_1,layout_band_back_1;

    private TextView tv_type_size,tb_out_of_stock,tv_not_recommended,tv_add_cart,tv_size_result;

    private LinearLayout layout_size_selection,layout_recommended,layout_start_over;

    private boolean isBrandSelected = false;
    private boolean isEditFlow = false;

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

        scale = getResources().getDisplayMetrics().density;

        bold_text = ResourcesCompat.getFont(BraFlow.this, R.font.apercu_bold);
        normal_text = ResourcesCompat.getFont(BraFlow.this, R.font.apercu_regular);

        tv_brand_not_listed = findViewById(R.id.tv_brand_not_listed);
        tv_brand_error = findViewById(R.id.tv_brand_error);

        tv_type_size = findViewById(R.id.tv_type_size);

        layout_brand_search = findViewById(R.id.layout_brand_search);
        layout_add = findViewById(R.id.layout_add);
        layout_brands = findViewById(R.id.layout_brands);
        layout_loading = findViewById(R.id.layout_loading);
        layout_recommended = findViewById(R.id.layout_recommended);
        layout_start_over = findViewById(R.id.layout_start_over);

        layout_brand = findViewById(R.id.layout_brand);
        layout_bra_profile = findViewById(R.id.layout_bra_profile);
        layout_result = findViewById(R.id.layout_result);
        tb_out_of_stock = findViewById(R.id.tb_out_of_stock);
        tv_not_recommended = findViewById(R.id.tv_not_recommended);
        tv_add_cart = findViewById(R.id.tv_add_cart);
        tv_size_result = findViewById(R.id.tv_size_result);

        layout_cup_back = findViewById(R.id.layout_cup_back);
        layout_cup_next = findViewById(R.id.layout_cup_next);
        layout_cup_next_2 = findViewById(R.id.layout_cup_next_2);
        layout_cup_back_2 = findViewById(R.id.layout_cup_back_2);

        layout_band_next_1 = findViewById(R.id.layout_band_next_1);
        layout_band_back_1 = findViewById(R.id.layout_band_back_1);

        tv_brand_continue = findViewById(R.id.tv_brand_continue);
        tv_bra_continue = findViewById(R.id.tv_bra_continue);

        tv_back_bra = findViewById(R.id.tv_back_bra);

        et_what_brand = findViewById(R.id.et_what_brand);

        layout_size_selection = findViewById(R.id.layout_size_selection);

        recycler_brand_suggestion = findViewById(R.id.recycler_brand_suggestion);
        recycler_bra_sizeType = findViewById(R.id.recycler_bra_sizeType);

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

        tv_type_wired = findViewById(R.id.tv_type_wired);
        tv_type_unwired = findViewById(R.id.tv_type_unwired);

        layout_type_wired = findViewById(R.id.layout_type_wired);
        layout_type_unwired = findViewById(R.id.layout_type_unwired);

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

        SpannableString content;

        content = new SpannableString(getResources().getString(R.string.bra_brand_not_listed));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv_brand_not_listed.setText(content);


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

                setBraSizes(braSizeChart,region);

                clearAllBands();
                clearAllCup();
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

                newBrandRequest(category,et_what_brand.getText().toString());

                et_what_brand.setBackground(getResources().getDrawable(R.drawable.bg_edit_enabled));
                layout_add.setVisibility(View.GONE);
                tv_brand_error.setText(getResources().getString(R.string.brand_thanks));
                layout_brands.setVisibility(View.VISIBLE);
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

        layout_type_wired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wired = true;
                isWired = "true";
                tv_type_wired.setTextColor(getResources().getColor(R.color.color_text));
                layout_type_wired.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));

                tv_type_unwired.setTextColor(getResources().getColor(R.color.grey_3));
                layout_type_unwired.setBackground(getResources().getDrawable(R.drawable.bg_button_unselected));

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

                checkBraSize(band,cup,isWired);
            }
        });

        tv_brand_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isBrandSelected)
                {
                    layout_brand.setVisibility(View.GONE);
                    layout_bra_profile.setVisibility(View.VISIBLE);

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
                    Toast.makeText(BraFlow.this,"Select All",Toast.LENGTH_SHORT).show();
                }

            }
        });


        tv_back_bra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_brand.setVisibility(View.VISIBLE);
                layout_bra_profile.setVisibility(View.GONE);

            }
        });




        tv_brand_1.setOnClickListener(this);
        tv_brand_2.setOnClickListener(this);
        tv_brand_3.setOnClickListener(this);
        tv_brand_4.setOnClickListener(this);
        tv_brand_5.setOnClickListener(this);


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
                referenceBrands.put("brand",brand);
                referenceBrands.put("cup",cup);

                braOptions.put("wired",wired);
                braOptions.put("bandFit",bandFit);
                braOptions.put("cupFit",cupFit);
                braOptions.put("strapsFit",strapsFit);
                braOptions.put("band",band);
                braOptions.put("cup",cup);
                braOptions.put("region",region);

                referenceBrandsArray.put(0,referenceBrands);
                femaleObject.put("referenceBrands",referenceBrandsArray);
                femaleObject.put("braOptions",braOptions);

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

        try {

            if (NetworkUtils.getInstance(this).isConnectedToInternet()) {

//                if (bu.equals(""))
//                {
                    GET get = new GET(this, "https://sizeguidev2.pixibo.com/asset/"+clientId+"/"+skuId+"?uid="+uID+"&brand="+brand+"&band="+band+"&cup="+cup+"&region="+region+"&wiredBra="+wiredBra, GetSize, this);
                    get.execute();
//                }
//                else
//                {
//                    GET get = new GET(this, "https://sizeguidev2.pixibo.com/asset/"+clientId+"/"+skuId+"?uid="+uID+"&brand="+brand+"&band="+band+"&cup="+cup+"&region="+region+"&wiredBra="+wiredBra , GetSize, this);
//                    get.execute();
//                }


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
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {

                                layout_bra_profile.setVisibility(View.GONE);
                                layout_loading.setVisibility(View.GONE);
                                layout_result.setVisibility(View.VISIBLE);


                               // updateUserDetails();

                               // progress(6);

                            }
                        }, 1200);
                    }


                    JSONObject jsonObject = new JSONObject(result);


//                            if (availableSizeList.toString().toLowerCase().contains(jsonObject.getString("cup").toLowerCase()))
//                            {
//                                tb_out_of_stock.setVisibility(View.VISIBLE);
//                            }
//                            else
//                            {
//                                tb_out_of_stock.setVisibility(View.GONE);
//                            }


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
            }
            if (i == 1)
            {
                tv_band_2.setText(list.get(i).toString());
            }
            if (i == 2)
            {
                tv_band_3.setText(list.get(i).toString());
            }
            if (i == 3)
            {
                tv_band_4.setText(list.get(i).toString());
            }
            if (i == 4)
            {
                tv_band_5.setText(list.get(i).toString());
            }
            if (i == 5)
            {
                tv_band_6.setText(list.get(i).toString());
                layout_band_next_1.setVisibility(View.VISIBLE);
                layout_band_back_1.setVisibility(View.VISIBLE);
            }
            if (i == 6)
            {
                tv_band_7.setText(list.get(i).toString());
            }
            if (i == 7)
            {
                tv_band_8.setText(list.get(i).toString());
            }
            if (i == 8)
            {
                tv_band_9.setText(list.get(i).toString());
            }
            if (i == 9)
            {
                tv_band_10.setText(list.get(i).toString());
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
            }
            if (i == 1)
            {
                tv_cup_2.setText(list.get(i).toString());
            }
            if (i == 2)
            {
                tv_cup_3.setText(list.get(i).toString());
            }
            if (i == 3)
            {
                tv_cup_4.setText(list.get(i).toString());
            }
            if (i == 4)
            {
                tv_cup_5.setText(list.get(i).toString());
            }
            if (i == 5)
            {
                tv_cup_6.setText(list.get(i).toString());
                layout_cup_back.setVisibility(View.VISIBLE);
                layout_cup_next.setVisibility(View.VISIBLE);
            }
            if (i == 6)
            {
                tv_cup_7.setText(list.get(i).toString());
            }
            if (i == 7)
            {
                tv_cup_8.setText(list.get(i).toString());
            }
            if (i == 8)
            {
                tv_cup_9.setText(list.get(i).toString());
            }
            if (i == 9)
            {
                tv_cup_10.setText(list.get(i).toString());
            }
            if (i == 10)
            {
                tv_cup_11.setText(list.get(i).toString());
            }
            if (i == 11)
            {
                tv_cup_12.setText(list.get(i).toString());
            }
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

        }
    }


    public void clearAllBands()
    {
        band = "";
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


    public void clearAllCup()
    {

      //  tv_error_cup.setVisibility(View.GONE);

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
}
