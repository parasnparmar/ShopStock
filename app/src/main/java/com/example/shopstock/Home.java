package com.example.shopstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private static final int MENU_VIEW_STOCK = 1;
    private static final int MENU_ADD_STOCK = 2;
    private static final int MENU_OUT_OF_STOCK = 3;
    private static final int MENU_STOCK_BILL = 4;

TextView txtUserName;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    PieChart pieChart = findViewById(R.id.pieChart);
    ArrayList<PieEntry> stocks = new ArrayList<>();
    stocks.add(new PieEntry(70F,"Stock1"));
    stocks.add(new PieEntry(40F,"Stock2"));
    stocks.add(new PieEntry(30F,"Stock3"));
    stocks.add(new PieEntry(15F,"Stock4"));
    stocks.add(new PieEntry(02F,"Stock5"));

    PieDataSet pieDataSet = new PieDataSet(stocks,"Stocks");
    pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

    PieData pieData = new PieData(pieDataSet);
    pieChart.setData(pieData);

    pieChart.getDescription().setEnabled(false);
    pieChart.animateY(1000);
    pieChart.invalidate();


        Intent i  = getIntent();
        i.getStringExtra("username");
        txtUserName = findViewById(R.id.textViewUserName);
        txtUserName.setText("Hi "+i.getStringExtra("username"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, MENU_VIEW_STOCK, 1, "View Stock");
        menu.add(1, MENU_ADD_STOCK, 2, "Add Stock");
        menu.add(1, MENU_OUT_OF_STOCK, 3, "Out Of Stock");
        menu.add(1, MENU_STOCK_BILL, 4, "Stock Bill");

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.setGroupVisible(1, true);
        menu.setGroupEnabled(1, true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Intent intent = null;

        switch (itemId) {
            case MENU_VIEW_STOCK:
                intent = new Intent(this, Stocks.class);
                break;
            case MENU_ADD_STOCK:
                intent = new Intent(this, AddStock.class);
                break;
            case MENU_OUT_OF_STOCK:
                intent = new Intent(this, OutOfStock.class);
                break;
            case MENU_STOCK_BILL:
                intent = new Intent(this, Stock_Bill.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}