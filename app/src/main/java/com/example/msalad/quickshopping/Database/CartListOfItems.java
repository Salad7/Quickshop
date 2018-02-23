package com.example.msalad.quickshopping.Database;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mohammadhia on 9/26/2017.
 */

public class CartListOfItems implements Serializable {
    ArrayList<CartItem> cart = new ArrayList();

    public ArrayList<CartItem> getCart() {
        return cart;
    }

    public void setCart(ArrayList<CartItem> cart) {
        this.cart = cart;
    }

    public void addToCart(CartItem cartItem){ //Add parameter that lets you pick quantity of cartItem to be added

        for(int i = 0; i < cart.size(); i++){
            if( cartItem.getItemKey().equals( cart.get(i).getItemKey() ) ){
                cart.get(i).increaseQuantity( cartItem.getQuantity() ); //Increases quantity of item already in the cart
                return;
            }
        }

        //cartItem.price = cartItem.price * 1;
        cart.add(cartItem);
    }


    public void setFinalQuantity(CartItem cartItem, int finalQuantity){ //For use in the checkout section if the user modifies the quantity of an item there

        for(int i = 0; i < cart.size(); i++){
            if( cartItem.getItemKey().equals( cart.get(i).getItemKey() ) ){
                cart.get(i).setQuantity( finalQuantity ); //Increases quantity of item already in the cart
                return;
            }
        }
    }


    public double getTotal(){
        double total = 0.0;

        for(int i = 0; i < cart.size(); i++){
            total += cart.get(i).getPrice() * cart.get(i).getQuantity();
        }

        return total;
    }


    public String toString(){
        String totalCart = "";
        for(int i = 0; i < cart.size(); i++){
            totalCart += cart.get(i).getName() + "\t" + "$" + cart.get(i).getPrice() + "\t" + "Quantity: " + cart.get(i).getQuantity() + "\n";
        }
        return totalCart;
    }

}
