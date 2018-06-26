package com.example.fauzy.adminapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity {
    List<UserOrder> uOrderList = new ArrayList<>();
    DatabaseReference mRef;
    public static final String URL_NOTIFYOPENORDER = "http://laniary-accountabil.000webhostapp.com/android/notifyopenorder.php?topics=news&type=topics";
    RecyclerView recyclerView;
    LinearLayoutManager llm;
    ImageView logo;
    FancyButton closeOrderBtn;
    TextView mainText, supportingText;
    UserOrderAdapter uOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#47D4AE"));
        if (!getSharedPreferences("isOpenOrder", MODE_PRIVATE).getBoolean("isEnabled", false)) {
            setContentView(R.layout.close_state_layout);
            FancyButton openOrderBtn = findViewById(R.id.open_order_btn);
            openOrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = getSharedPreferences("isOpenOrder", MODE_PRIVATE).edit();
                    editor.putBoolean("isEnabled", true);
                    editor.apply();

                    CallWebPageTask task = new CallWebPageTask();
                    task.execute(new String[]{URL_NOTIFYOPENORDER});

                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
                }
            });
        } else {
            setContentView(R.layout.open_state_layout);
            closeOrderBtn = findViewById(R.id.close_order_btn);
            closeOrderBtn.setVisibility(View.VISIBLE);
            closeOrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = getSharedPreferences("isOpenOrder", MODE_PRIVATE).edit();
                    editor.putBoolean("isEnabled", false);
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this,SummaryActivity.class);
                    UserOrderController uoc = new UserOrderController(uOrderList);
                    intent.putExtra("orderList",uoc);
                    finish();
                    startActivity(intent);
                }
            });
            mRef = FirebaseDatabase.getInstance().getReference();
            llm = new LinearLayoutManager(this);
            uOrderAdapter = new UserOrderAdapter(uOrderList);
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //dataSnapshot.getChildren() isinya objek nama
                    uOrderList.clear();
                    DataSnapshot orderList = dataSnapshot.child("orderlist");
                    for (DataSnapshot ds : orderList.getChildren()) {
                        UserOrder uo = new UserOrder();
                        uo.setNama(ds.getKey());
                        uo.setTotalHarga(ds.child("totalharga").getValue().toString());
                        Iterable<DataSnapshot> iterableDetailOrder = ds.child("detailpesanan").getChildren();
                        List<DetailOrder> mListTemp = new ArrayList<>();
                        for (DataSnapshot dataSnapshot1 : iterableDetailOrder) {
                            String[] words = dataSnapshot1.getValue().toString().split(Pattern.quote("."));
//                            mListTemp.add(dataSnapshot1.getValue().toString());
                            DetailOrder detailOrder = new DetailOrder();
                            detailOrder.setJenis(words[1]);
                            detailOrder.setNamaMenu(words[0]);
                            mListTemp.add(detailOrder);
                        }
                        uo.setDetailOrder(mListTemp);
                        uOrderList.add(uo);
                    }
                    recyclerView = findViewById(R.id.user_order_recycler);
                    logo = findViewById(R.id.logo);
                    mainText = findViewById(R.id.main_text);
                    supportingText = findViewById(R.id.supporting_text);
                    if (uOrderList.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        logo.setVisibility(View.VISIBLE);
                        mainText.setVisibility(View.VISIBLE);
                        supportingText.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        logo.setVisibility(View.INVISIBLE);
                        mainText.setVisibility(View.INVISIBLE);
                        supportingText.setVisibility(View.INVISIBLE);
                        recyclerView.setLayoutManager(llm);
                        recyclerView.setAdapter(uOrderAdapter);
                        uOrderAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mRef.addValueEventListener(valueEventListener);

        }
    }

    //Method untuk Mengirimkan data keserver
    public String getRequest(String Url) {
        String sret;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(Url);
        try {
            HttpResponse response = client.execute(request);
            sret = request(response);
        } catch (Exception ex) {
            sret = "Failed Connect to server!";
        }
        return sret;
    }

    //Method untuk Menerima data dari server
    public static String request(HttpResponse response) {
        String result = "";
        try {
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();
        } catch (Exception ex) {
            result = "Error";
        }
        return result;
    }

    private class CallWebPageTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            response = getRequest(urls[0]);
            mRef = FirebaseDatabase.getInstance().getReference();
            mRef.child("statusorder").setValue("open");
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(MainActivity.this, "Messages sent successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
