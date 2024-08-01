package com.example.shopstock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    private ArrayList<StocksData> stocksArrayList;

    public StockAdapter(ArrayList<StocksData> stocksArrayList) {
        this.stocksArrayList = stocksArrayList;
    }

    class StockViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtQuantity, txtPrice;
        ImageView imgProduct;

        public StockViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtStockTitle);
            txtQuantity = itemView.findViewById(R.id.txtStockQuantity);
            txtPrice = itemView.findViewById(R.id.txtStockPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
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
    }

    @Override
    public int getItemCount() {
        return stocksArrayList.size();
    }
}
