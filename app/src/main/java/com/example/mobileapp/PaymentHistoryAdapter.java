package com.example.mobileapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder> {

    private List<PaymentHistoryItem> paymentHistoryList;

    public PaymentHistoryAdapter(List<PaymentHistoryItem> paymentHistoryList) {
        this.paymentHistoryList = paymentHistoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PaymentHistoryItem paymentHistoryItem = paymentHistoryList.get(position);
        holder.referenceCodeTextView.setText("Reference Code: " + paymentHistoryItem.getReferenceCode());
        holder.itemNameTextView.setText("Item Name: " + paymentHistoryItem.getItemName());
        holder.paymentDateTextView.setText("Payment Date: " + paymentHistoryItem.getPaymentDate());
    }

    @Override
    public int getItemCount() {
        return paymentHistoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView referenceCodeTextView;
        TextView itemNameTextView;
        TextView paymentDateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            referenceCodeTextView = itemView.findViewById(R.id.referenceCodeTextView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            paymentDateTextView = itemView.findViewById(R.id.paymentDateTextView);
        }
    }
}