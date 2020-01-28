package com.mobile.shoppingapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

    public class SearchLab {
        private static SearchLab sSearchLab;
        private Map<String, String> mSearches;

        public static SearchLab get(Context context)    {
            if (sSearchLab == null) {
                sSearchLab = new SearchLab(context);
            }

            return sSearchLab;
        }

        private SearchLab(Context context) {
            mSearches = new LinkedHashMap<>(); //LinkedHashMap preserves position as an array
            mSearches.put("Recognize what you want", "Add items into your cart taking a photo");
            mSearches.put("Traditional search", "Digit your item");
        }

        public Map<String, String> getSearches() {
            return mSearches;
        }


    }

