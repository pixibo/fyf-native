package com.pixibo.zalora.Adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pixibo.zalora.Apparel.ApparelFlow;
import com.pixibo.zalora.Model.BrandModel;
import com.pixibo.zalora.R;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MyViewHolder> {

    private List<BrandModel> brandModelList;
    private BrandModel brandModel;
    private final onItemClickListener onItemClick;
    private Activity activity;
    Typeface bold_text ;
    Typeface normal_text ;
    int dataPosition ;




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

            bold_text = ResourcesCompat.getFont(activity, R.font.apercu_bold);
            normal_text = ResourcesCompat.getFont(activity, R.font.apercu_regular);


        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            view.setOnClickListener(onClickListener);
        }
    }




    public BrandAdapter(List<BrandModel> brandModels, Activity activity, BrandAdapter.onItemClickListener onItemClickListener) {
        this.brandModelList = brandModels;
        this.onItemClick = onItemClickListener;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_brand_suggession, parent, false);

        return new BrandAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final BrandModel review = brandModelList.get(position);

        holder.tv_brand_name.setText(review.getName());

        //Log.e("tv_brand_name",review.getName());
//
//        if (position != dataPosition)
//        {
//            holder.tv_brand_name.setTypeface(normal_text);
//        }

        holder.tv_brand_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(review);
//                holder.tv_brand_name.setTypeface(bold_text);
//
//                dataPosition = position;
            }
        });

    }

    @Override
    public int getItemCount() {
        return brandModelList.size();
    }
}