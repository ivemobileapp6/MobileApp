package com.example.mobileapp;//package com.example.mobileapp;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//
//public class ShowActivity extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private ArrayList<String> imageUrls;
//    private ImageAdapter imageAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show);
//
//        Toast.makeText(this, "ShowActivity launched", Toast.LENGTH_SHORT).show();
//
//        recyclerView = findViewById(R.id.recyclerview);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        imageUrls = new ArrayList<>();
//        imageAdapter = new ImageAdapter(this, imageUrls);
//        recyclerView.setAdapter(imageAdapter);
//
//        loadImages();
//    }
//
//    private void loadImages() {
//        imageUrls.add("https://firebasestorage.googleapis.com/v0/b/mobileapp-7b6c9.appspot.com/o/1687378838174.jpg?alt=media&token=2cda058c-7cfc-4b8f-bcb5-875e3cb05e44");
//        imageUrls.add("https://firebasestorage.googleapis.com/v0/b/mobileapp-7b6c9.appspot.com/o/1687379011037.jpg?alt=media&token=ac2eadcc-db84-4b0d-beb7-dc82a5834657");
//        imageUrls.add("https://firebasestorage.googleapis.com/v0/b/mobileapp-7b6c9.appspot.com/o/1687379787913.jpg?alt=media&token=5b7946d8-a4b2-4eea-ba7c-28437ece39d1");
//
//        imageAdapter.notifyDataSetChanged();
//    }
//}

import android.os.Bundle;
import android.support.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Model> list;
    private MyAdapter adapter;
    private DatabaseReference root = FirebaseDatabase.getInstance("https://mobileapp-7b6c9-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Image");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new MyAdapter(this, list);
        recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}