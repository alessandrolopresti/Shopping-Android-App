package com.mobile.shoppingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Cart  {
    private ArrayList<String> items = new ArrayList<>();

    public Cart()   {}

    public Cart(ArrayList<String> cart) {
        items = cart;
    }



    public Cart[] newArray(int size) {
        return new Cart[size];
    }


    public ArrayList<String> getCart() {
        return items;
    }

    public void setCart(ArrayList<String> cart) {
        items = cart;
    }




}
