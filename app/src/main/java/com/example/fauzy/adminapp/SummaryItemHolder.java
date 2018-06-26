package com.example.fauzy.adminapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class SummaryItemHolder extends RecyclerView.ViewHolder {
    TextView concatedMenu;
    public SummaryItemHolder(View view){
        super(view);
        concatedMenu = view.findViewById(R.id.concated_menu);
    }
}
