package com.example.shopstock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopstock.R;
import com.example.shopstock.Stocks;
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
        Button btnDelete;

        public StockViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtStockTitle);
            txtQuantity = itemView.findViewById(R.id.txtStockQuantity);
            txtPrice = itemView.findViewById(R.id.txtStockPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            btnDelete = itemView.findViewById(R.id.btnRemoveProduct);

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
    }

    @Override
    public int getItemCount() {
        return stocksArrayList.size();
    }
}
