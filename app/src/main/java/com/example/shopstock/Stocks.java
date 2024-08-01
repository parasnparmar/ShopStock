package com.example.shopstock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Stocks extends AppCompatActivity {
    RecyclerView recyclerViewStocks;
    ArrayList<StocksData> stocks;
    StockAdapter stockAdapter;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);

        initStocks();
        initView();
        }
        private void initStocks(){
            stocks = new ArrayList<>();
            for(int i=0;i<50;i++){
                stocks.add(new StocksData(i
                        ,"Stock "+i,
                        i*5,
                        i*10000));
            }
        }
        private void initView(){
            recyclerViewStocks = findViewById(R.id.recyclerViewStocks);
            stockAdapter = new StockAdapter(stocks);
            recyclerViewStocks.setAdapter(stockAdapter);
            recyclerViewStocks.setLayoutManager(new LinearLayoutManager(
                    this,
                    LinearLayoutManager.VERTICAL,
                    false
            ));
        }

    }


