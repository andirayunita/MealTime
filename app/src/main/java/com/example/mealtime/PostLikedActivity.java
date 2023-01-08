package com.example.mealtime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

public class PostLikedActivity extends AppCompatActivity {
    private ImageButton backButton;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_liked);
        recyclerView = findViewById(R.id.activity_post_liked_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        backButton = findViewById(R.id.activity_post_liked_backBtn);
        backButton.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ExplorePostAdapter explorePostAdapter = new ExplorePostAdapter(this);
        recyclerView.setAdapter(explorePostAdapter);
    }
}