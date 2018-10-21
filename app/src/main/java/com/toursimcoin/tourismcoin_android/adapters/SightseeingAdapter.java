package com.toursimcoin.tourismcoin_android.adapters;

import android.content.Intent;
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
import com.toursimcoin.tourismcoin_android.UI.DetailsActivity;
import com.toursimcoin.tourismcoin_android.UI.MainActivity;
import com.toursimcoin.tourismcoin_android.heplers.Constants;
import com.toursimcoin.tourismcoin_android.model.Sightseeing;

import java.util.List;

public class SightseeingAdapter extends RecyclerView.Adapter<SightseeingAdapter.MyViewHolder> {
    private List<Sightseeing> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View view;

        public TextView title;
        public ImageView image;
        public TextView points;

        public Sightseeing sightseeing;

        public MyViewHolder(View v) {
            super(v);
            view = v;

            title = v.findViewById(R.id.sightseeing_title);
            image = v.findViewById(R.id.sightseeing_image);
            points = v.findViewById(R.id.sightseeing_points);
        }

        public void bind(Sightseeing sightseeing){
            this.sightseeing = sightseeing;
            title.setText(sightseeing.title);
            points.setText(sightseeing.points);
            Glide.with(view.getContext()).load(sightseeing.image_url).apply(new RequestOptions().centerCrop()).into(image);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), DetailsActivity.class);
            intent.putExtra(Constants.IMAGE_URL, sightseeing.image_url);
            intent.putExtra(Constants.TITLE, sightseeing.title);
            intent.putExtra(Constants.DESCRIPTION, sightseeing.description);
            intent.putExtra(Constants.POINTS, sightseeing.points);
            v.getContext().startActivity(intent);
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