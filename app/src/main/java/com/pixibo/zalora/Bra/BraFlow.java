package com.pixibo.zalora.Bra;

import android.content.Intent;
import android.graphics.Typeface;
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
    private String wired = "";
    private String bandFit = "";
    private String cupFit = "";
    private String strapsFit = "";
    private String band = "";
    private String region = "";

    private String braSizeChart = "";

    Typeface bold_text ;
    Typeface normal_text ;

    private TextView tv_brand_not_listed,tv_brand_error;

    private ArrayList<BrandModel> brandModelArrayList = new ArrayList<>();
    private ArrayList<BrandModel> braSizeTypeArrayList = new ArrayList<>();
    private BrandAdapter brandAdapter;
    private BraSizeTypeAdapter braSizeTypeAdapter;
    private RecyclerView recycler_brand_suggestion,recycler_bra_sizeType;
    private EditText et_what_brand;

    private RelativeLayout layout_brand_search,layout_add,layout_brands;
    private RelativeLayout layout_brand,layout_bra_profile;
    private TextView tv_brand_continue,tv_bra_continue;
    private TextView tv_back_bra;
    private TextView tv_brand_1,tv_brand_2,tv_brand_3,tv_brand_4,tv_brand_5;

    private TextView tv_band_1,tv_band_2,tv_band_3,tv_band_4,tv_band_5;
    private TextView tv_cup_1,tv_cup_2,tv_cup_3,tv_cup_4,tv_cup_5;

    private TextView tv_type_size;

    private LinearLayout layout_size_selection;

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

        layout_brand = findViewById(R.id.layout_brand);
        layout_bra_profile = findViewById(R.id.layout_bra_profile);


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

        tv_cup_1 = findViewById(R.id.tv_cup_1);
        tv_cup_2 = findViewById(R.id.tv_cup_2);
        tv_cup_3 = findViewById(R.id.tv_cup_3);
        tv_cup_4 = findViewById(R.id.tv_cup_4);
        tv_cup_5 = findViewById(R.id.tv_cup_5);

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
//                layout_brand.setVisibility(View.GONE);
//                layout_bra_profile.setVisibility(View.GONE);
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
        }
    }
}
