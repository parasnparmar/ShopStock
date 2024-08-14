package com.example.shopstock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Stocks extends AppCompatActivity implements StockAdapter.OnItemClickListener {
    RecyclerView recyclerViewStocks;
    ArrayList<StocksData> stocks;
    StockAdapter stockAdapter;
    ProductDB productDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);

        productDB = Room.databaseBuilder(this, ProductDB.class, "product_db")
                .build();

        recyclerViewStocks = findViewById(R.id.recyclerViewStocks);
        recyclerViewStocks.setLayoutManager(new LinearLayoutManager(this));

        stocks = new ArrayList<>();
        stockAdapter = new StockAdapter(stocks, this);
        recyclerViewStocks.setAdapter(stockAdapter);

        // Load products from the database
        new LoadProductsTask().execute();
    }

    @Override
    public void onItemDeleteClick(StocksData stocksData) {
        new AlertDialog.Builder(this)
                .setTitle("ShopStock")
                .setMessage("Are you sure you want to delete this product?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DeleteStockTask().execute(stocksData);
                    }
                })
                .setNegativeButton("No", null)
                .show();
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
                stocks.add(new StocksData(product.getId(), product.getName(), product.getQuantity(), product.getPrice(),product.getImagePath()));
            }
            stockAdapter.notifyDataSetChanged();
        }
    }

    private class DeleteStockTask extends AsyncTask<StocksData, Void, Void> {
        @Override
        protected Void doInBackground(StocksData... stocksData) {
            Product product = new Product();
            product.setId(stocksData[0].getId());
            product.setName(stocksData[0].getTitle());
            product.setQuantity(stocksData[0].getQuantity());
            product.setPrice(stocksData[0].getPrice());
            productDB.productDao().delete(product);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            new LoadProductsTask().execute(); // Reload products after deletion
        }
    }
}