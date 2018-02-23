package com.example.msalad.quickshopping.Database;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cci-loaner on 2/22/18.
 */

public class HistoryItem {
    String date;
    String price;
    ArrayList<InventoryItem> items;

    public HistoryItem() {
        items = new ArrayList<>();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<InventoryItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<InventoryItem> items) {
        this.items = items;
    }
}
