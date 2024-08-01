package com.example.shopstock;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.shopstock.databinding.ActivityAddStockBinding;

public class AddStock extends AppCompatActivity {
    ActivityAddStockBinding binding;
    ProductDB productDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddStockBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productDB = Room.databaseBuilder(this, ProductDB.class, "product_db")
                .addMigrations(ProductDB.MIGRATION_1_2)
                .build();

        binding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.productname.getText().toString().isEmpty() ||
                        binding.productquantity.getText().toString().isEmpty() ||
                        binding.productprice.getText().toString().isEmpty()) {
                    fieldsMandatory();
                } else {
                    showAlertDialog("ShopStock", "Are you sure you want to add?", "Yes", "No", new Runnable() {
                        @Override
                        public void run() {
                            addProductToDatabase();
                        }
                    });
                }
            }
        });
    }

    private void fieldsMandatory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ShopStock")
                .setMessage("All Fields are Mandatory to Add Product")
                .setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void addProductToDatabase() {
        String productName = binding.productname.getText().toString();
        int productQuantity = Integer.parseInt(binding.productquantity.getText().toString());
        double productPrice = Double.parseDouble(binding.productprice.getText().toString());

        Product product = new Product();
        product.setName(productName);
        product.setQuantity(productQuantity);
        product.setPrice(productPrice);

        new InsertProductTask().execute(product);
    }

    private void showAlertDialog(String title, String msg, String btnPosText, String btnNegText, Runnable positiveAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(btnPosText, (dialog, which) -> {
                    dialog.dismiss();
                    positiveAction.run();
                    showAlertDialog("ShopStock", "Product Added Successfully", "Ok", "", null);
                })
                .setNegativeButton(btnNegText, (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private class InsertProductTask extends AsyncTask<Product, Void, Void> {
        @Override
        protected Void doInBackground(Product... products) {
            productDB.productDao().insert(products[0]);
            return null;
        }
    }
}
