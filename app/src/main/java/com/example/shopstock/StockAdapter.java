package com.example.shopstock;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.shopstock.R;
import com.example.shopstock.StocksData;

import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {
    private ArrayList<StocksData> stocksArrayList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemDeleteClick(StocksData stock);
    }

    public StockAdapter(ArrayList<StocksData> stocksArrayList, OnItemClickListener onItemClickListener) {
        this.stocksArrayList = stocksArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    class StockViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtQuantity, txtPrice;
        ImageView imgProduct;
        Button btnDelete, btnIncrementQty, btnDecrementQty;

        public StockViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtStockTitle);
            txtQuantity = itemView.findViewById(R.id.txtStockQuantity);
            txtPrice = itemView.findViewById(R.id.txtStockPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            btnDelete = itemView.findViewById(R.id.btnRemoveProduct);
            btnIncrementQty = itemView.findViewById(R.id.btnIncrementQty);
            btnDecrementQty = itemView.findViewById(R.id.btnDecrementQty);

            btnDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemDeleteClick(stocksArrayList.get(position));
                }
            });
        }
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View stockView = inflater.inflate(R.layout.stocks_view, parent, false);
        return new StockViewHolder(stockView);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        StocksData stocks1 = stocksArrayList.get(position);
        holder.txtTitle.setText(stocks1.getTitle());
        holder.txtQuantity.setText("Quantity: " + stocks1.getQuantity());
        holder.txtPrice.setText("Price: " + stocks1.getPrice() + "/-");
        holder.imgProduct.setImageResource(R.drawable.shopstock);
        String imagePath = stocks1.getImagePath();

        if (imagePath != null && !imagePath.isEmpty()) {
            Log.d("Tag", "Image Path: " + imagePath);
            Glide.with(holder.imgProduct.getContext())
                    .asBitmap()
                    .load(imagePath)
                    .placeholder(R.drawable.baseline_hide_image_24)
                    .error(R.drawable.error_image)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            Log.e("GlideError", "Failed to load image: " + imagePath, e);
                            return false; // Let Glide handle the error placeholder
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            Log.d("GlideSuccess", "Successfully loaded image: " + imagePath);
                            return false;
                        }
                    })
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            holder.imgProduct.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            holder.imgProduct.setImageResource(R.drawable.error_image);
                            Toast.makeText(holder.imgProduct.getContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
                        }

                    });

        } else {
            holder.imgProduct.setImageResource(R.drawable.shopstock);
        }
    }

    @Override
    public int getItemCount() {
        return stocksArrayList.size();
    }
}
