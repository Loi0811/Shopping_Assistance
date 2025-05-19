package com.example.shoppingassistance;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Product> allProducts = new ArrayList<>();
    private ArrayList<Product> discountedProducts = new ArrayList<>();
    private ArrayList<Product> newProducts = new ArrayList<>();
    private ProductAdapter discountAdapter;
    private ProductAdapter newProductAdapter;
    private  AutoCompleteTextView autoSearch;
    private ProductSuggestionAdapter searchAdapter;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gán danh sách sản phẩm mẫu
        loadSampleProducts();

        // Tách sản phẩm thành 2 nhóm
        for (Product p : allProducts) {
            if (p.getOriginalPrice() > p.getDiscountedPrice()) {
                discountedProducts.add(p);
            }
            if (isWithinOneWeek(p.getImportDate())) {
                newProducts.add(p);
            }
        }

        // Gắn adapter cho 2 ListView
        ListView discountListView = findViewById(R.id.discount_list);
        ListView newProductListView = findViewById(R.id.new_list);

        discountAdapter = new ProductAdapter(this, discountedProducts, 0);
        newProductAdapter = new ProductAdapter(this, newProducts, 1);

        discountListView.setAdapter(discountAdapter);
        newProductListView.setAdapter(newProductAdapter);

        discountListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = (Product) discountAdapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                intent.putExtra("imageResId", selectedProduct.getImageResId());
                intent.putExtra("name", selectedProduct.getName());
                intent.putExtra("originalPrice", selectedProduct.getOriginalPrice());
                intent.putExtra("discountedPrice", selectedProduct.getDiscountedPrice());
                intent.putExtra("importDate", selectedProduct.getImportDate());
                intent.putExtra("location", selectedProduct.getLocation());
                startActivity(intent);
            }
        });

        newProductListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = (Product) newProductAdapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                intent.putExtra("imageResId", selectedProduct.getImageResId());
                intent.putExtra("name", selectedProduct.getName());
                intent.putExtra("originalPrice", selectedProduct.getOriginalPrice());
                intent.putExtra("discountedPrice", selectedProduct.getDiscountedPrice());
                intent.putExtra("importDate", selectedProduct.getImportDate());
                intent.putExtra("location", selectedProduct.getLocation());
                startActivity(intent);
            }
        });

        // Tìm kiếm
        autoSearch = findViewById(R.id.autoCompleteSearch);
        searchAdapter = new ProductSuggestionAdapter(this, allProducts);
        autoSearch.setAdapter(searchAdapter);

        autoSearch.setOnItemClickListener((parent, view, position, id) -> {
            Product selectedProduct = searchAdapter.getItem(position);

            Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
            intent.putExtra("imageResId", selectedProduct.getImageResId());
            intent.putExtra("name", selectedProduct.getName());
            intent.putExtra("originalPrice", selectedProduct.getOriginalPrice());
            intent.putExtra("discountedPrice", selectedProduct.getDiscountedPrice());
            intent.putExtra("importDate", selectedProduct.getImportDate());
            intent.putExtra("location", selectedProduct.getLocation());
            startActivity(intent);
        });

        autoSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                adjustDropdownHeight();
            }
        });
        autoSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adjustDropdownHeight();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


    }

    private void loadSampleProducts() {
        allProducts.add(new Product(R.drawable.indomie_dacbiet_goi, "Mì indomie vị đặc biệt", 6000, 5000, "03/05/2025", "Mi an lien"));
        allProducts.add(new Product(R.drawable.indomie_bocay_goi, "Mì indomie vị bò cay", 6000, 5000, "03/05/2025", "Mi an lien"));
        allProducts.add(new Product(R.drawable.mi_siukay_haisan, "Mì siukay vị hải sản", 12000, 12000, "13/05/2025", "Mi an lien"));
        allProducts.add(new Product(R.drawable.mi_siukay_bo, "Mì siukay vị bò", 12000, 12000, "13/05/2025", "Mi an lien"));
        allProducts.add(new Product(R.drawable.mi_siukay_gaphomai, "Mì siukay vị gà cay phô mai", 12000, 12000, "13/05/2025", "Mi an lien"));
        allProducts.add(new Product(R.drawable.coca_lon, "Lon Coca-Cola 330ml", 9000, 9000, "13/04/2025", "Nuoc ngot"));
        allProducts.add(new Product(R.drawable.coca_loc, "Lóc 4 lon Coca-Cola 330ml", 35000, 30000, "13/04/2025", "Nuoc ngot"));
        allProducts.add(new Product(R.drawable.pepsi_zerocalo_lon, "Lon Pepsi zero calo 330 ml", 10000, 10000, "13/04/2025", "Nuoc ngot"));
        allProducts.add(new Product(R.drawable.pepsi_zerocalo_loc, "Lóc 4 lon Pepsi zero calo 330ml ", 38000, 32000, "13/04/2025", "Nuoc ngot"));
        allProducts.add(new Product(R.drawable.sua_vinamilk_220ml, "Sữa vinamilk ít đường 220ml ", 7000, 7000, "13/05/2025", "Sua"));
        allProducts.add(new Product(R.drawable.sua_vinamilk_itduong_4hop, "Lóc 4 hộp sữa vinamilk ít đường 180ml ", 28000, 28000, "13/05/2025", "Sua"));
        allProducts.add(new Product(R.drawable.sua_vinamilk_itduong_1l, "Sữa vinamilk ít đường 1l ", 30000, 30000, "13/05/2025", "Sua"));
        allProducts.add(new Product(R.drawable.sua_fami_bich_200ml, "Sữa đậu nành fami canxi ít đường bịch 200ml ", 5000, 5000, "01/05/2025", "Sua"));
        allProducts.add(new Product(R.drawable.sua_fami_6hop_200ml, "6 hộp sữa đậu nành fami canxi ít đường 200ml ", 30000, 30000, "01/05/2025", "Sua"));
        allProducts.add(new Product(R.drawable.alpenliebe_muoiot_87g, "alpenliebe muối ớt 87g ", 15000, 13800, "01/05/2025", "Keo"));
        allProducts.add(new Product(R.drawable.kitkat_102g_6thanh, "Kitkat 102g 6 thanh ", 50000, 44500, "01/05/2025", "Keo"));
        allProducts.add(new Product(R.drawable.dynamite_bacha_330g, "Dynamite bạc hà 330g ", 28500, 28500, "01/05/2025", "Keo"));
        allProducts.add(new Product(R.drawable.bdx_rongvang_minhngoc_300g, "Bánh đậu xanh rồng vàng minh ngọc 300g ", 45000, 38000, "01/05/2025", "Keo"));
        allProducts.add(new Product(R.drawable.muoi_say_iot_sosal, "Muối sấy iot Sosal ", 4500, 4500, "01/05/2025", "Gia vi"));
        allProducts.add(new Product(R.drawable.duongmia_bienhoa, "Đường mía biên hòa ", 45000, 40000, "01/05/2025", "Gia vi"));
        allProducts.add(new Product(R.drawable.nuoc_mam_nam_ngu, "Nước mắm nam ngư 900ml ", 61000, 61000, "01/05/2025", "Gia vi"));
        allProducts.add(new Product(R.drawable.tuong_ot_shinsu, "Tương ớt chinsu ", 18500, 18500, "12/05/2025", "Gia vi"));
        allProducts.add(new Product(R.drawable.tao_xanh_my, "1kg táo xanh Mỹ ", 100000, 100000, "13/05/2025", "Trai cay"));
        allProducts.add(new Product(R.drawable.vai, "1kg vải ", 100000, 100000, "13/05/2025", "Trai cay"));
        allProducts.add(new Product(R.drawable.dua_hau, "1kg dưa hấu ", 20000, 15000, "13/05/2025", "Trai cay"));
        allProducts.add(new Product(R.drawable.dau_tay, "1kg dâu tây ", 180000, 165000, "13/05/2025", "Trai cay"));
    }

    private boolean isWithinOneWeek(String importDateStr) {
        try {
            Date importDate = dateFormat.parse(importDateStr);
            Date currentDate = new Date();

            long diff = currentDate.getTime() - importDate.getTime();
            long daysDiff = diff / (1000 * 60 * 60 * 24);

            return daysDiff <= 7;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void filterProducts(String query) {
        ArrayList<Product> filteredDiscount = new ArrayList<>();
        ArrayList<Product> filteredNew = new ArrayList<>();

        for (Product p : allProducts) {
            if (p.getName().toLowerCase().contains(query.toLowerCase())) {
                if (p.getOriginalPrice() > p.getDiscountedPrice()) {
                    filteredDiscount.add(p);
                }
                if (isWithinOneWeek(p.getImportDate())) {
                    filteredNew.add(p);
                }
            }
        }

        discountAdapter.updateList(filteredDiscount);
        newProductAdapter.updateList(filteredNew);
    }

    private void adjustDropdownHeight() {
        int itemCount = searchAdapter.getCount(); // adapter là adapter của AutoCompleteTextView
        int itemHeight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics()); // chiều cao mỗi item
        int maxVisibleItems = 6; // số item tối đa dropdown hiển thị, nếu nhiều hơn sẽ scroll
        int dropdownHeight;

        if (itemCount == 0) {
            dropdownHeight = itemHeight; // hoặc 0
        } else if (itemCount < maxVisibleItems) {
            dropdownHeight = itemHeight * itemCount + 48;
        } else {
            dropdownHeight = itemHeight * maxVisibleItems + 48;
        }

        autoSearch.setDropDownHeight(dropdownHeight);
    }
}