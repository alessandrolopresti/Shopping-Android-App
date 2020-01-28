package com.mobile.shoppingapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YourShoppingLab {

    private static YourShoppingLab sYourShoppingLab;

    private List<String> mYourShoppings;

    public static YourShoppingLab get(Context context) {
        if (sYourShoppingLab == null) {
            sYourShoppingLab = new YourShoppingLab(context);
        }

        return sYourShoppingLab;
    }

    private YourShoppingLab(Context context) {
        mYourShoppings = new ArrayList<>();
        mYourShoppings.add("Your Profile");
        mYourShoppings.add("Your Cart");
        mYourShoppings.add("Your Smart Shoppings");
        mYourShoppings.add("Your favourite items");
        mYourShoppings.add("Your favourite markets");
    }

    public List<String> getYourShoppings()   {
        return mYourShoppings;
    }
}


