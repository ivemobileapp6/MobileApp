package com.example.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewsDetailActivity extends AppCompatActivity {
    private TextView newsDetailTitle;
    private TextView newsDetailContent;
    private TextView newsDetailDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        newsDetailTitle = findViewById(R.id.news_detail_title);
        newsDetailContent = findViewById(R.id.news_detail_content);
        newsDetailDate = findViewById(R.id.news_detail_date);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String date = intent.getStringExtra("date");

        newsDetailTitle.setText(title);
        newsDetailContent.setText(content);
        newsDetailDate.setText(date);
    }
}