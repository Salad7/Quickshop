package com.example.msalad.quickshopping.Database;

import java.util.ArrayList;

/**
 * Created by cci-loaner on 2/22/18.
 */

public class User {

    public String uuid;
    public ArrayList<CartListOfItems> cartItems;

    public User() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ArrayList<CartListOfItems> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartListOfItems> cartItems) {
        this.cartItems = cartItems;
    }
}
