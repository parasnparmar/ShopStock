package com.example.shopstock;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.shopstock.Product;
import com.example.shopstock.ProductDB;
import com.example.shopstock.databinding.ActivityAddStockBinding;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddStock extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private ImageView imageViewProduct;
    private Button buttonSelectImage;

    ActivityAddStockBinding binding;
    ProductDB productDB;
    ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddStockBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productDB = Room.databaseBuilder(this, ProductDB.class, "product_db")
                .addMigrations(ProductDB.MIGRATION_1_2)
                .build();

        executorService = Executors.newSingleThreadExecutor();

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
        imageViewProduct = findViewById(R.id.imageViewProduct);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);

        productDB = Room.databaseBuilder(this, ProductDB.class, "product_db")
                .addMigrations(ProductDB.MIGRATION_1_2)
                .build();

        executorService = Executors.newSingleThreadExecutor();

        buttonSelectImage.setOnClickListener(v -> openImageChooser());

        binding.btnAddProduct.setOnClickListener(v -> {
            if (binding.productname.getText().toString().isEmpty() ||
                    binding.productquantity.getText().toString().isEmpty() ||
                    binding.productprice.getText().toString().isEmpty()) {
                fieldsMandatory();
            } else {
                showAlertDialog("ShopStock", "Are you sure you want to add?", "Yes", "No", this::addProductToDatabase);
            }
        });
    }
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Glide.with(this)
                    .load(imageUri)
                    .into(imageViewProduct);
        }
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
        String imagePath = imageUri != null ? imageUri.toString() : null;  // Save URI as string

        Product product = new Product();
        product.setName(productName);
        product.setQuantity(productQuantity);
        product.setPrice(productPrice);
        product.setImagePath(imagePath);

        executorService.execute(() -> {
            productDB.productDao().insert(product);
            runOnUiThread(() -> showAlertDialog("ShopStock", "Product Added Successfully", "Ok", "", null));
        });
    }

    private void showAlertDialog(String title, String msg, String btnPosText, String btnNegText, Runnable positiveAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(btnPosText, (dialog, which) -> {
                    dialog.dismiss();
                    if (positiveAction != null) positiveAction.run();
                })
                .setNegativeButton(btnNegText, (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
