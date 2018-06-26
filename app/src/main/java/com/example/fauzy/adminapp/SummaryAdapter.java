package com.example.fauzy.adminapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryItemHolder>{
    List<String> summaryList = new ArrayList<>();

    public SummaryAdapter(List<String> summaryList){
        this.summaryList = summaryList;
    }

    @Override
    public SummaryItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_item_layout, parent, false);
        return new SummaryItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SummaryItemHolder holder, int position) {
        String item = summaryList.get(position);
        holder.concatedMenu.setText(item);
    }

    @Override
    public int getItemCount() {
        return summaryList.size();
    }
}