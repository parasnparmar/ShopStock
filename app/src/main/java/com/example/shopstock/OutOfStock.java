package com.example.shopstock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.shopstock.databinding.ActivityOutOfStockBinding;

import java.util.ArrayList;
import java.util.List;

public class OutOfStock extends AppCompatActivity implements StockAdapter.OnItemClickListener {
    ActivityOutOfStockBinding activityOutOfStockBinding;
    StockAdapter stockAdapter;
    ArrayList<StocksData> stocks;
    ProductDB productDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityOutOfStockBinding = ActivityOutOfStockBinding.inflate(getLayoutInflater());
        setContentView(activityOutOfStockBinding.getRoot());

        productDB = Room.databaseBuilder(this, ProductDB.class, "product_db")
                        .addMigrations(productDB.MIGRATION_1_2)
                                .build();

        stocks = new ArrayList<>();
        stockAdapter = new StockAdapter(stocks,this);
        activityOutOfStockBinding.recyclerViewOutOfStocks.setAdapter(stockAdapter);
        activityOutOfStockBinding.recyclerViewOutOfStocks.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false));

        new LoadOutOfStockTask().execute();

    }

    private class LoadOutOfStockTask extends AsyncTask<Void, Void, List<Product>> {
        @Override
        protected List<Product> doInBackground(Void... voids) {
            return productDB.productDao().getAllProducts();
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            stocks.clear();
            for (Product product : products) {
                if (product.getQuantity() == 0) {
                    stocks.add(new StocksData(product.getId(), product.getName(), product.getQuantity(), product.getPrice()));
                }
            }
            stockAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemDeleteClick(StocksData stock) {

    }
}
