package com.toursimcoin.tourismcoin_android.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.toursimcoin.tourismcoin_android.R;
import com.toursimcoin.tourismcoin_android.model.Sightseeing;

import java.util.List;

public class SightseeingAdapter extends RecyclerView.Adapter<SightseeingAdapter.MyViewHolder> {
    private List<Sightseeing> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public TextView title;
        public ImageView image;

        public MyViewHolder(View v) {
            super(v);
            view = v;

            title = v.findViewById(R.id.sightseeing_title);
            image = v.findViewById(R.id.sightseeing_image);
        }

        public void bind(Sightseeing sightseeing){
            title.setText(sightseeing.title);
            Glide.with(view.getContext()).load(sightseeing.image_url).apply(new RequestOptions().centerCrop()).into(image);
        }
    }

    public SightseeingAdapter(List<Sightseeing> myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public SightseeingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sightseeing_cell, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(mDataset.get(position));

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addItems(List<Sightseeing> data){
        mDataset.addAll(data);
        notifyDataSetChanged();
    }
}