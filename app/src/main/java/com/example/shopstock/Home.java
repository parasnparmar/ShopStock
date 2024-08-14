package com.example.shopstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    TextView txtUserName;
    PieChart pieChart;
    ProductDB productDB;
    RecyclerView recyclerViewOutOfStock;
    OutOfStockAdapter outOfStockAdapter;
    List<Product> outOfStockProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        productDB = Room.databaseBuilder(getApplicationContext(),
                        ProductDB.class, "product_db")
                .fallbackToDestructiveMigration()
                .build();

        pieChart = findViewById(R.id.pieChart);
        txtUserName = findViewById(R.id.textViewUserName);
        recyclerViewOutOfStock = findViewById(R.id.recyclerViewOutOfStock);

        recyclerViewOutOfStock.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        txtUserName.setText("Hi " + intent.getStringExtra("username"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Fetch data and update PieChart and RecyclerView whenever the activity is resumed
        new LoadStockDataTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.functionality_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.setGroupVisible(R.id.group_stock_management, true);
        menu.setGroupEnabled(R.id.group_stock_management, true);

        menu.setGroupVisible(R.id.group_logout, true);
        menu.setGroupEnabled(R.id.group_logout, true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;

        if (item.getItemId() == R.id.view_stock) {
            intent = new Intent(this, Stocks.class);
        } else if (item.getItemId() == R.id.add_stock) {
            intent = new Intent(this, AddStock.class);
        } else if (item.getItemId() == R.id.out_of_stock) {
            intent = new Intent(this, OutOfStock.class);
        } else if (item.getItemId() == R.id.stock_bill) {
            intent = new Intent(this, Stock_Bill.class);
        } else if (item.getItemId() == R.id.logout) {
            Log.d("Home", "Logout menu item selected");

            SharedPreferences.Editor editor = getSharedPreferences("LoginPrefs", MODE_PRIVATE).edit();
            editor.putBoolean("keepLoggedIn", false);
            editor.putString("userEmail", null);
            editor.apply();

            intent = new Intent(this, ActivityLogin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        if (intent != null) {
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class LoadStockDataTask extends AsyncTask<Void, Void, List<Product>> {
        @Override
        protected List<Product> doInBackground(Void... voids) {
            return productDB.productDao().getAllProducts();
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            ArrayList<PieEntry> entries = new ArrayList<>();
            outOfStockProducts = new ArrayList<>();

            for (Product product : products) {
                if (product.getQuantity() > 0) {
                    entries.add(new PieEntry(product.getQuantity(), product.getName()));
                } else {
                    outOfStockProducts.add(product);
                }
            }

            // Update PieChart with available stock
            PieDataSet pieDataSet = new PieDataSet(entries, "Stocks");
            pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
            PieData pieData = new PieData(pieDataSet);
            pieChart.setData(pieData);
            pieChart.getDescription().setEnabled(false);
            pieChart.animateY(1000);
            pieChart.invalidate();

            // Update RecyclerView with out-of-stock products
            outOfStockAdapter = new OutOfStockAdapter(outOfStockProducts);
            recyclerViewOutOfStock.setAdapter(outOfStockAdapter);
        }
    }
}
