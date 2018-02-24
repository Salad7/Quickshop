package com.example.msalad.quickshopping.Database;

/**
 * Created by cci-loaner on 2/13/18.
 */

public class Store {

    String name;
    String url;
    int drawableID;

    public Store(){

    }
    public Store(String name, String url){
        this.name = name;
        this.url = url;
    }

    public int getDrawableID() {
        return drawableID;
    }

    public Store(int drawableID){
        this.drawableID = drawableID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
