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
    StocksData stocks;
    ArrayList<StocksData> stocksArrayList;
    TextView txtTitle, txtQuantity, txtPrice;
    ImageView imgProduct;

    public StockAdapter(ArrayList<StocksData> stocksArrayList) {
        this.stocksArrayList = stocksArrayList;
    }
    class StockViewHolder extends RecyclerView.ViewHolder {
        public StockViewHolder(View itemView){
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
        imgProduct = stockView.findViewById(R.id.imgProduct);
        txtTitle = stockView.findViewById(R.id.txtStockTitle);
        txtQuantity = stockView.findViewById(R.id.txtStockQuantity);
        txtPrice = stockView.findViewById(R.id.txtStockPrice);
        return new StockViewHolder(stockView);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        StocksData stocks1 = stocksArrayList.get(position);
        txtTitle = holder.itemView.findViewById(R.id.txtStockTitle);
        txtTitle.setText(stocks1.getTitle());
        txtQuantity = holder.itemView.findViewById(R.id.txtStockQuantity);
        txtQuantity.setText("Quantity: "+stocks1.getQuantity()+"");
        txtPrice = holder.itemView.findViewById(R.id.txtStockPrice);
        txtPrice.setText("Price: "+stocks1.getPrice()+""+ "/-");
        imgProduct = holder.itemView.findViewById(R.id.imgProduct);
        imgProduct.setImageResource(R.drawable.shopstock);

    }

    @Override
    public int getItemCount() {
        return stocksArrayList.size();
    }
}
