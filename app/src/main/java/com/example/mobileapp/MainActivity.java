package com.example.mobileapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button btnLogout;
    private FirebaseAuth mAuth;
    private DrawerLayout drawerLayout;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        mAuth = FirebaseAuth.getInstance();

        btnLogout = findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fetchNewsData();

    }

    private void fetchNewsData() {
        String url = "https://fileprojectwork.ivemobileapp6.repl.co/latestnews";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if ("Success".equals(status)) {
                                JSONArray newsJsonArray = response.getJSONArray("data");
                                List<News> newsList = new ArrayList<>();

                                for (int i = 0; i < newsJsonArray.length(); i++) {
                                    JSONObject newsJson = newsJsonArray.getJSONObject(i);
                                    String title = newsJson.getString("title");
                                    String content = newsJson.optString("content", ""); // Use optString to handle missing content
                                    String date = newsJson.getString("date");
                                    newsList.add(new News(title, content, date));
                                }

                                setupNewsRecyclerView(newsList);
                            } else {
                                Toast.makeText(MainActivity.this, "Error fetching news data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error fetching news data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error fetching news data", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void setupNewsRecyclerView(List<News> newsList) {
        RecyclerView recyclerView = findViewById(R.id.news_recycler_view);
        NewsAdapter newsAdapter = new NewsAdapter(context, newsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(newsAdapter);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.nav_act1) {
            intent = new Intent(MainActivity.this, FareInformationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_act2) {
            intent = new Intent(MainActivity.this, SensorActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_act3) {
            intent = new Intent(MainActivity.this, PhotoActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_act4) {
            intent = new Intent(MainActivity.this, Map.class);
            startActivity(intent);
        }else if (id == R.id.nav_act5) {
            intent = new Intent(MainActivity.this, Payment.class);
            startActivity(intent);
        } else if (id == R.id.nav_act6) {
            intent = new Intent(MainActivity.this, EditProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_act7) {
            intent = new Intent(MainActivity.this, ScheduleActivity.class);
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}