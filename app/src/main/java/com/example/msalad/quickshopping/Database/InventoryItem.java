package com.example.msalad.quickshopping.Database;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by cci-loaner on 2/13/18.
 */

public class InventoryItem implements Serializable {

    double price;
    String name;
    String image; //Contains path to image on the internet
    String salesTaxGroup;
    String itemKey;

    HashMap<String, Double> taxGroups = new HashMap();


    public InventoryItem(double price, String name, String image, String salesTaxGroup, String key){
        taxGroups.put("Food", 0.03);
        taxGroups.put("Electronics", 0.07);
        taxGroups.put("Cosmetics & Toiletries", 0.05);
        taxGroups.put("Others", 0.06);
        this.price = price; //+ (price * taxGroups.get(salesTaxGroup));
        this.name = name;
        this.image = image;
        itemKey = key;
    }


    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public String getSalesTaxGroup() {
        return salesTaxGroup;
    }

    public String getItemKey() {
        return itemKey;
    }

    public HashMap<String, Double> getTaxGroups() {
        return taxGroups;
    }

    public String getImage() { return image; }



    //Changes price based off of discounts (store wide, coupons, members, etc.)
    //LOOK AT LATER ON
    public void discount(double percentage){
        price = price*(1-(percentage/100));
    }
}
