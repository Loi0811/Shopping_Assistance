package com.example.shoppingassistance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ProductSuggestionAdapter extends ArrayAdapter<Product> {
    private Context context;
    private List<Product> productList; // danh sách gốc
    private List<Product> filteredProducts; // danh sách sau lọc
    private ProductFilter filter;

    public ProductSuggestionAdapter(@NonNull Context context, @NonNull List<Product> products) {
        super(context, 0, products);
        this.context = context;
        this.productList = new ArrayList<>(products);
        this.filteredProducts = new ArrayList<>(products);
    }

    @Override
    public int getCount() {
        return filteredProducts.size();
    }

    @Nullable
    @Override
    public Product getItem(int position) {
        return filteredProducts.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.product_suggestion_item, parent, false);
        }

        Product product = filteredProducts.get(position);

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);

        imageView.setImageResource(product.getImageResId());
        textView.setText(product.getName());

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new ProductFilter();
        }
        return filter;
    }

    private class ProductFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Product> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                // Nếu không có input thì không hiển thị gợi ý (hoặc có thể hiển thị toàn bộ tùy bạn)
                // suggestions.addAll(productList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Product product : productList) {
                    if (product.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(product);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredProducts.clear();
            if (results != null && results.count > 0) {
                //noinspection unchecked
                filteredProducts.addAll((List<Product>) results.values);
            }
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Product) resultValue).getName();
        }
    }


}
