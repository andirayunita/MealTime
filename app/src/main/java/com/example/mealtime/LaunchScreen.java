package com.example.mealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LaunchScreen extends AppCompatActivity {

    private Button btnLogin;
    private Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
        btnLogin = findViewById(R.id.activity_launch_screen_btn_login);
        btnSignUp = findViewById(R.id.activity_launch_screen_btn_signup);

        btnLogin.setOnClickListener(view -> {
            startActivity(new Intent(this,LoginActivity.class));
        });

        btnSignUp.setOnClickListener(view -> {
            startActivity(new Intent(this,SignUpActivity.class));
        });
    }
}