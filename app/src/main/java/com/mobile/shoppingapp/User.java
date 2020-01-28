package com.mobile.shoppingapp;


public class User {
    private String name, email;
    private PastShopping past_shopping;
    private Cart cart;

    public User()   {}

    public User(String name, String email, PastShopping past_shopping, Cart cart) {
        this.name = name;
        this.email = email;
        this.past_shopping = past_shopping;
        this.cart = cart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PastShopping getPast_shopping() {
        return past_shopping;
    }

    public void setPast_shopping(PastShopping past_shopping) {
        this.past_shopping = past_shopping;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
