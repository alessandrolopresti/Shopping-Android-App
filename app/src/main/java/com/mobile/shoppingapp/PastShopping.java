package com.mobile.shoppingapp;

import java.util.List;

public class PastShopping {
    private List<SmartShopping> PastShoppings;

    public PastShopping() {}

    public PastShopping(List<SmartShopping> pastShoppings) {
        PastShoppings = pastShoppings;
    }

    public List<SmartShopping> getPastShoppings() {
        return PastShoppings;
    }

    public void setPastShoppings(List<SmartShopping> pastShoppings) {
        PastShoppings = pastShoppings;
    }
}
