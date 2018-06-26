package com.example.fauzy.adminapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderItemHolder>{
    List<UserOrder> uOrderList = new ArrayList<>();

    public UserOrderAdapter(List<UserOrder> uOrderList){
        this.uOrderList = uOrderList;
    }

    @Override
    public UserOrderItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_order_item_layout, parent, false);
        return new UserOrderItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserOrderItemHolder holder, int position) {
        UserOrder item = uOrderList.get(position);
        holder.name.setText(item.getNama());
        holder.totalPrice.setText(item.getTotalHarga());
    }

    @Override
    public int getItemCount() {
        return uOrderList.size();
    }
}