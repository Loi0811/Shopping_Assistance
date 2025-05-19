package com.example.shoppingassistance;

public class Product {
    public int imageResId;
    public String name;
    public double originalPrice;
    public double discountedPrice;
    public String location;
    public String importDate;

    public Product(int imageResId, String name, double originalPrice, double discountedPrice, String importDate, String location) {
        this.imageResId = imageResId;
        this.name = name;
        this.originalPrice = originalPrice;
        this.discountedPrice = discountedPrice;
        this.importDate = importDate;
        this.location = location;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImportDate() {
        return importDate;
    }

    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }
}

