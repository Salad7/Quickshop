package com.example.msalad.quickshopping.Database;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Mohammadhia on 9/11/2017.
 */

public class CartItem extends InventoryItem implements Serializable{
    int quantity;


    public CartItem(double price, String name, String image, String salesTaxGroup, String key, int quantity){
        super(price, name, image, salesTaxGroup, key);
        this.quantity = quantity; //Will change as more items are added
    }

    public void increaseQuantity(int number){
        quantity += number;
    }


    public  void setQuantity(int number){ //For use in the checkout section if the user modifies the quantity of an item there
        quantity = number;
    }

    public int getQuantity(){
        return quantity;
    }
}
