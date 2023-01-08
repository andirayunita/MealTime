package com.example.mealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        boolean status = sharedPreferences.getBoolean("login",false);
        new Handler().postDelayed(() -> {
            if (status){
                startActivity(new Intent(getApplicationContext(),AppActivity.class));
            }else{
                startActivity(new Intent(getApplicationContext(),LaunchScreen.class));
            }

            finish();
        },2000);
    }
}