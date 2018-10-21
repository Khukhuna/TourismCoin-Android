package com.toursimcoin.tourismcoin_android.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.toursimcoin.tourismcoin_android.R;
import com.toursimcoin.tourismcoin_android.model.Sale;

import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.MyViewHolder> {
    private List<Sale> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public TextView title;
        public ImageView image;
        public Button buy;

        public Sale sale;

        public MyViewHolder(View v) {
            super(v);
            view = v;

            title = v.findViewById(R.id.sales_title);
            image = v.findViewById(R.id.sales_image);
            buy = v.findViewById(R.id.sales_buy);
        }

        public void bind(Sale sale){
            this.sale = sale;
            title.setText(sale.getTitle());
            Glide.with(view).load(sale.getImage_url()).apply(new RequestOptions().centerCrop()).into(image);
            buy.setText(String.valueOf(sale.getPrice()));
        }
    }

    public SalesAdapter(List<Sale> myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public SalesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales_cell, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bind(mDataset.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}