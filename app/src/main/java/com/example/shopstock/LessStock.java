package com.example.shopstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LessStock extends AppCompatActivity {
    public class CustomAdapter extends ArrayAdapter<String> {
        private List<String> dataList;
        private Context mContext;
        public CustomAdapter(Context context, int resource, List<String> dataList) {
            super(context, resource, dataList);
            this.mContext = context;
            this.dataList = dataList;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            String item = dataList.get(position);
            TextView textView = convertView.findViewById(android.R.id.text1);
            textView.setText(item);

            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_less_stock);
        ListView listView = findViewById(R.id.listViewLessStock);
        List<String> dataList = new ArrayList<>();
        dataList.add("\n\t\t\t                  Mens Collection       \t\t\t\n");
        dataList.add("ProductID 106 \n\n\t Joggers - 05 quantity - 799 mrp\n");
        dataList.add("\n\t\t\t                  Womens Collection       \t\t\t\n");

        dataList.add("ProductID 206 \n\n\t Joggers - 05 quantity - 799 mrp\n");

        CustomAdapter adapter = new LessStock.CustomAdapter(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
    }
}


