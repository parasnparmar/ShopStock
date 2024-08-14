package com.example.shopstock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OutOfStockAdapter extends RecyclerView.Adapter<OutOfStockAdapter.OutOfStockViewHolder> {
    private List<Product> outOfStockProducts;

    public OutOfStockAdapter(List<Product> outOfStockProducts) {
        this.outOfStockProducts = outOfStockProducts;
    }

    @NonNull
    @Override
    public OutOfStockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_out_of_stock, parent, false);
        return new OutOfStockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutOfStockViewHolder holder, int position) {
        Product product = outOfStockProducts.get(position);
        holder.txtProductName.setText(product.getName());
    }

    @Override
    public int getItemCount() {
        return outOfStockProducts.size();
    }

    public static class OutOfStockViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName;

        public OutOfStockViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.textViewProductName);
        }
    }
}
