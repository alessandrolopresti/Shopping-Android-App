package com.mobile.shoppingapp;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class AdapterSmartShopping extends ArrayAdapter<SmartShopping> {
    final static String LOG_TAG="AdapterSmartShopping";

    public AdapterSmartShopping(@NonNull Context context, int resource, @NonNull SmartShopping[] objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.smart_item, null);

        TextView market = (TextView)convertView.findViewById(R.id.ang_smart_market);
        TextView price = (TextView)convertView.findViewById(R.id.ang_smart_price);
        ImageView market_image = convertView.findViewById(R.id.ang_market_image);


        SmartShopping c = getItem(position);
        Log.d(LOG_TAG+"IMG",c.getImageUrl()+"");
        Picasso.get().load(c.getImageUrl()).into(market_image);
        Log.d(LOG_TAG,c.getMarketName());
        Log.d(LOG_TAG,c.getTotalCost()+"");


        market.setText(c.getMarketName());
        price.setText(c.getTotalCost()+"");
        return convertView;
    }


}
