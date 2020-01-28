package com.mobile.shoppingapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CartItemViewHolder extends RecyclerView.ViewHolder {
    public TextView itemView;

    public CartItemViewHolder(View itemView) {
        super(itemView);

        itemView = (TextView) itemView.findViewById(R.id.your_shopping_title);
    }




}
