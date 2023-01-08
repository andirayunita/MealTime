package com.example.mealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditProfileActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private  SharedPreferences sh;
    private EditText fullName;
    private EditText userName;
    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        backBtn = findViewById(R.id.activity_edit_profile_backBtn);
        fullName = findViewById(R.id.activity_edit_profile_name);
        userName = findViewById(R.id.activity_edit_profile_username);
        email = findViewById(R.id.activity_edit_profile_email);

        email.setText(sh.getString("email",""));
        fullName.setText(sh.getString("fullName",""));
        userName.setText(sh.getString("username",""));

        backBtn.setOnClickListener(view -> {
            finish();
        });
    }
}