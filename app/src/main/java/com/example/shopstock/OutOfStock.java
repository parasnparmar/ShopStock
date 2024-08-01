package com.example.shopstock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.shopstock.databinding.ActivityOutOfStockBinding;

import java.util.ArrayList;

public class OutOfStock extends AppCompatActivity {
    RecyclerView recyclerView;
    ActivityOutOfStockBinding activityOutOfStockBinding;
    StockAdapter stockAdapter;
    ArrayList<StocksData> stocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityOutOfStockBinding = ActivityOutOfStockBinding.inflate(getLayoutInflater());
        setContentView(activityOutOfStockBinding.getRoot());
        initStocks();

        stockAdapter = new StockAdapter(stocks);
        activityOutOfStockBinding.recyclerViewOutOfStocks.setAdapter(stockAdapter);
        activityOutOfStockBinding.recyclerViewOutOfStocks.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        ));

    }

    private void initStocks() {
        stocks = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            stocks.add(new StocksData(i
                    , "Stock " + i,
                    +i * 0,
                    i * 3000));
        }
    }
}
