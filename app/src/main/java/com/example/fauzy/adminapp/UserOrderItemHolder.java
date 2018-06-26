package com.example.fauzy.adminapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import mehdi.sakout.fancybuttons.FancyButton;

public class UserOrderItemHolder extends RecyclerView.ViewHolder {
    public TextView name,totalPrice;
    public UserOrderItemHolder(View view){
        super(view);
        name = view.findViewById(R.id.name);
        totalPrice = view.findViewById(R.id.price);
    }
}
