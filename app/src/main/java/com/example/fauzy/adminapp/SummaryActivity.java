package com.example.fauzy.adminapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class SummaryActivity extends AppCompatActivity {
    List<UserOrder> userOrderList = new ArrayList<>();
    UserOrderController uoc;
    RecyclerView summaryRecycler;
    SummaryAdapter summaryAdapter;
    FancyButton sendToWhatsapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_layout);
        sendToWhatsapp = findViewById(R.id.send_to_whatsapp_btn);
        Intent i = getIntent();
        uoc = (UserOrderController)i.getSerializableExtra("orderList");
        userOrderList = uoc.uOrderList;
        uoc.categorizeDetailOrder();
        uoc.simplifyOrders(uoc.foodSummaryList);
        uoc.simplifyOrders(uoc.drinkSummaryList);
        uoc.writeDataToSummaryList();
        summaryRecycler = findViewById(R.id.summary_recycler);
        summaryAdapter = new SummaryAdapter(uoc.summaryList);
        summaryRecycler.setLayoutManager(new LinearLayoutManager(this));
        summaryRecycler.setAdapter(summaryAdapter);
        sendToWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setPackage("com.whatsapp");
                sendIntent.putExtra(Intent.EXTRA_TEXT, uoc.summaryString);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }
}
