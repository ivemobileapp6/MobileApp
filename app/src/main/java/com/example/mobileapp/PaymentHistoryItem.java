package com.example.mobileapp;


public class PaymentHistoryItem {

    private String referenceCode;
    private String paymentDate;
    private String itemName;

    public PaymentHistoryItem(String referenceCode, String paymentDate, String itemName) {
        this.referenceCode = referenceCode;
        this.paymentDate = paymentDate;
        this.itemName = itemName;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getItemName() {
        return itemName;
    }
}