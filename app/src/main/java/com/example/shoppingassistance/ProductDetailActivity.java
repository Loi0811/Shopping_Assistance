package com.example.shoppingassistance;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView productImage;
    TextView productName, productOriginalPrice, productDiscountedPrice, productImportDate, productLocation;
    Button btnNavigate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productOriginalPrice = findViewById(R.id.productOriginalPrice);
        productDiscountedPrice = findViewById(R.id.productDiscountedPrice);
        productImportDate = findViewById(R.id.productImportDate);
        productLocation = findViewById(R.id.productLocation);
        btnNavigate = findViewById(R.id.btnNavigate);

        Intent intent = getIntent();

        int imageResId = intent.getIntExtra("imageResId", 0);
        String name = intent.getStringExtra("name");
        double originalPrice = intent.getDoubleExtra("originalPrice", 0);
        double discountedPrice = intent.getDoubleExtra("discountedPrice", 0);
        String importDate = intent.getStringExtra("importDate");
        String location = intent.getStringExtra("location");

        productImage.setImageResource(imageResId);
        productName.setText(name);
        productDiscountedPrice.setText("Price: " + discountedPrice + "đ");

        if (originalPrice > discountedPrice) {
            productOriginalPrice.setText(originalPrice + "đ");
            productOriginalPrice.setPaintFlags(productOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            productOriginalPrice.setVisibility(View.VISIBLE);
        } else {
            productOriginalPrice.setVisibility(View.GONE); // Ẩn nếu không giảm giá
        }
        productImportDate.setText("Import Date: " + importDate);
        productLocation.setText("Location: " + location);

        btnNavigate.setOnClickListener(v -> {
            // ProductDetail.java
            Intent intentNavigation = new Intent(ProductDetailActivity.this, NavigationActivity.class);
            intentNavigation.putExtra("location", location);
            startActivity(intentNavigation);

        });
    }
}
