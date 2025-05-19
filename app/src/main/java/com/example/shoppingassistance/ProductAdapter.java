package com.example.shoppingassistance;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    public static final int TYPE_DISCOUNTED = 0;
    public static final int TYPE_JUST_ARRIVED = 1;

    private Context context;
    private List<Product> productList;
    private int type;

    public ProductAdapter(Context context, List<Product> productList, int type) {
        this.context = context;
        this.productList = productList;
        this.type = type;
    }

    @Override public int getCount() { return productList.size(); }
    @Override public Object getItem(int i) { return productList.get(i); }
    @Override public long getItemId(int i) { return i; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = productList.get(position);

        if (type == TYPE_DISCOUNTED) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_discounted, parent, false);
            }

            ImageView img = convertView.findViewById(R.id.imgProduct);
            TextView name = convertView.findViewById(R.id.txtName);
            TextView original = convertView.findViewById(R.id.txtOriginalPrice);
            TextView discounted = convertView.findViewById(R.id.txtDiscountedPrice);
            TextView percent = convertView.findViewById(R.id.txtPercent);

            img.setImageResource(product.imageResId);
            name.setText(product.name);
            original.setText(product.originalPrice + "đ");
            original.setPaintFlags(original.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            discounted.setText(product.discountedPrice + "đ");

            int percentValue = 100 - (int)((product.discountedPrice * 100.0f) / product.originalPrice);
            percent.setText("-" + percentValue + "%");

        } else if (type == TYPE_JUST_ARRIVED) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_just_arrived, parent, false);
            }

            ImageView img = convertView.findViewById(R.id.imgProduct);
            TextView name = convertView.findViewById(R.id.txtName);
            TextView original = convertView.findViewById(R.id.txtOriginalPrice);

            img.setImageResource(product.imageResId);
            name.setText(product.name);
            original.setText(product.originalPrice + "đ");
        }

        return convertView;
    }

    public void updateList(List<Product> newList) {
        this.productList.clear();
        this.productList.addAll(newList);
        notifyDataSetChanged();
    }
}

