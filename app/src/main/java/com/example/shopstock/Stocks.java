package com.example.shopstock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Stocks extends AppCompatActivity {
    RecyclerView recyclerViewStocks;
    ArrayList<StocksData> stocks;
    StockAdapter stockAdapter;
    ProductDB productDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);

        productDB = Room.databaseBuilder(this, ProductDB.class, "product_db")
                .addMigrations(ProductDB.MIGRATION_1_2)
                .build();

        recyclerViewStocks = findViewById(R.id.recyclerViewStocks);
        recyclerViewStocks.setLayoutManager(new LinearLayoutManager(this));

        stocks = new ArrayList<>();
        stockAdapter = new StockAdapter(stocks);
        recyclerViewStocks.setAdapter(stockAdapter);

        // Load products from the database
        new LoadProductsTask().execute();
    }

    private class LoadProductsTask extends AsyncTask<Void, Void, List<Product>> {
        @Override
        protected List<Product> doInBackground(Void... voids) {
            return productDB.productDao().getAllProducts();
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            stocks.clear(); // Clear the list before adding new items
            for (Product product : products) {
                stocks.add(new StocksData(stocks.size() + 1, product.getName(), product.getQuantity(), product.getPrice()));
            }
            stockAdapter.notifyDataSetChanged();
        }
    }
}
