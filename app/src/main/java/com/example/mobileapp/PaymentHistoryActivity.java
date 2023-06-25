package com.example.mobileapp;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class PaymentHistoryActivity extends AppCompatActivity {

    private RecyclerView paymentHistoryRecyclerView;
    private PaymentHistoryAdapter adapter;
    private static List<PaymentHistoryItem> paymentHistoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        paymentHistoryRecyclerView = findViewById(R.id.payment_history_recycler_view);
        paymentHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<PaymentHistoryItem> paymentHistoryItems = getPaymentReferenceCodesFromSharedPreferences();
        adapter = new PaymentHistoryAdapter(paymentHistoryItems);
        paymentHistoryRecyclerView.setAdapter(adapter);
    }

    private List<PaymentHistoryItem> getPaymentReferenceCodesFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("payment_prefs", MODE_PRIVATE);
        Set<String> referenceCodeSet = sharedPreferences.getStringSet("payment_reference_codes", new HashSet<>());

        List<PaymentHistoryItem> paymentHistoryList = new ArrayList<>();
        for (String paymentInfo : referenceCodeSet) {
            String[] parts = paymentInfo.split("\\|");
            if (parts.length >= 2) {
                String referenceCode = parts[0];
                String itemName = parts[1];
                String paymentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                paymentHistoryList.add(new PaymentHistoryItem(referenceCode, paymentDate, itemName));
            }
        }
        return paymentHistoryList;
    }
}