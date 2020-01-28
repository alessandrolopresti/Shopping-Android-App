package com.mobile.shoppingapp;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class SmartShopping {
    private String marketName;
    private String imageUrl;
    private double totalCost;
    private Map<String, String> items = new LinkedHashMap<>();

    public SmartShopping()  {}

    public SmartShopping(String marketName, String imageUrl, double totalCost, Map<String, String> items) {
        this.marketName = marketName;
        this.imageUrl = imageUrl;
        this.totalCost = totalCost;
        this.items = items;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Map<String, String> getItems() {
        return items;
    }

    public void setItems(Map<String, String> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "SmartShopping{" +
                "marketName='" + marketName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", totalCost=" + totalCost +
                ", items=" + items.toString() +
                '}';
    }

}
