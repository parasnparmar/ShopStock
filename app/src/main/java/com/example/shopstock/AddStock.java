package com.example.shopstock;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopstock.databinding.ActivityAddStockBinding;

public class AddStock extends AppCompatActivity {
    ActivityAddStockBinding binding;
    DataBase dataBase;
    ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        binding = ActivityAddStockBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.productname.equals("") || binding.productquantity.equals("") || binding.productprice.equals("")) {
                    feildsMandotory();

                } else {
                    int id = binding.productId.getText().toString().length();
                    String title = binding.productname.getText().toString();
                    String quantity = binding.productquantity.getText().toString();
                    String price = binding.productprice.getText().toString();

                    Product product = new Product(id,title,quantity,price);
                    productViewModel.insertProduct(product);
                    showAlertDialog();
                }


            }

        });
        productViewModel.getAllProducts();

    }
    private void feildsMandotory () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ShopStock").setMessage("All Feilds Mandotory to Add Product")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
        private void showAlertDialog () {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("ShopStock").setMessage("Product added")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }


    }

