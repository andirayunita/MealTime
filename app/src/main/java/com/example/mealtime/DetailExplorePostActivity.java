package com.example.mealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailExplorePostActivity extends AppCompatActivity {
    private ImageButton backButton;
    private ImageView img;
    private TextView title;
    private TextView desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_explore_post);
        backButton = findViewById(R.id.activity_detail_explore_post_backBtn);
        img = findViewById(R.id.activity_detail_explore_post_image);
        title = findViewById(R.id.activity_detail_explore_post_title);
        desc = findViewById(R.id.activity_detail_explore_post_desc);

        Picasso.get().load(getIntent().getStringExtra("img")).into(img);
        title.setText(getIntent().getStringExtra("title"));
        desc.setText(getIntent().getStringExtra("desc"));
        backButton.setOnClickListener(view -> {
            finish();
        });
    }
}