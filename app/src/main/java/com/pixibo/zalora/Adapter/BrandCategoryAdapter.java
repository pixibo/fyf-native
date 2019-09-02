package com.pixibo.zalora.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pixibo.zalora.Model.BrandModel;
import com.pixibo.zalora.R;

import java.util.List;

public class BrandCategoryAdapter extends RecyclerView.Adapter<BrandCategoryAdapter.MyViewHolder> {

    private List<BrandModel> brandModelList;
    private BrandModel brandModel;
    private final BrandCategoryAdapter.onItemClickListener onItemClick;
    private Activity activity;




    public interface onItemClickListener {
        void onItemClick(BrandModel suggestions);
    }



    public void updateList(List<BrandModel> list){
        brandModelList = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_brand_name;
        View view;



        public MyViewHolder(View view) {
            super(view);
            tv_brand_name = (TextView) view.findViewById(R.id.tv_brand_name);


        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            view.setOnClickListener(onClickListener);
        }
    }




    public BrandCategoryAdapter(List<BrandModel> brandModels, Activity activity, BrandCategoryAdapter.onItemClickListener onItemClickListener) {
        this.brandModelList = brandModels;
        this.onItemClick = onItemClickListener;
        this.activity = activity;
    }

    @Override
    public BrandCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_brand_suggession, parent, false);

        return new BrandCategoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BrandCategoryAdapter.MyViewHolder holder, int position) {
        final BrandModel review = brandModelList.get(position);

        holder.tv_brand_name.setText(review.getName());

        //Log.e("tv_brand_name",review.getName());

        holder.tv_brand_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(review);
            }
        });

    }

    @Override
    public int getItemCount() {
        return brandModelList.size();
    }
}