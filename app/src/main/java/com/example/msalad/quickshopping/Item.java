package com.example.msalad.quickshopping;

/**
 * Created by msalad on 1/16/2018.
 */

public class Item {
    String title;
    double price;
    String category;
    String img_src;


    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
