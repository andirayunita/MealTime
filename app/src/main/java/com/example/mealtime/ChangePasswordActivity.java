package com.example.mealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangePasswordActivity extends AppCompatActivity {
    private ImageButton backButton;

    private TextInputLayout tilCurrPass;
    private TextInputLayout tilNewPass;
    private TextInputLayout tilRNewPass;

    private EditText edtCurrPass;
    private EditText edtNewPass;
    private EditText edtRNewPass;

    private Button btnChangePass;
    private FirebaseFirestore db;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        db = FirebaseFirestore.getInstance();
        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        backButton = findViewById(R.id.activity_change_password_backBtn);

        tilCurrPass = findViewById(R.id.activity_change_password_txtInputLayoutCurPass);
        tilNewPass = findViewById(R.id.activity_change_password_txtInputLayoutNewPass);
        tilRNewPass = findViewById(R.id.activity_change_password_txtInputLayoutRNewPass);

        edtCurrPass = findViewById(R.id.activity_change_password_edtTextCurrPass);
        edtNewPass = findViewById(R.id.activity_change_password_edtTextNewPass);
        edtRNewPass = findViewById(R.id.activity_change_password_edtTextRNewPass);

        btnChangePass = findViewById(R.id.activity_change_password_btnChange);

        backButton.setOnClickListener(view -> {
            finish();
        });

        btnChangePass.setOnClickListener(view -> {
            String currPass = edtCurrPass.getText().toString().trim();
            String newPass = edtNewPass.getText().toString().trim();
            String RNewPass = edtRNewPass.getText().toString().trim();

            if (currPass.isEmpty()){
                tilCurrPass.setError("This field is required!");
                return;
            }else{
                tilCurrPass.setError(null);
            }
            if (newPass.isEmpty()){
                tilNewPass.setError("This field is required!");
                return;
            }
            else{
                tilNewPass.setError(null);
            }
            if (RNewPass.isEmpty()){
                tilRNewPass.setError("This field is required!");
                return;
            }else{
                tilRNewPass.setError(null);
            }
            if (!currPass.matches(sharedPreferences.getString("password",""))){
                tilCurrPass.setError("Current password not match!");
                return;
            }else{
                tilCurrPass.setError(null);
            }

            if (!newPass.matches(RNewPass)){
                tilNewPass.setError("The password confirmation does not match!");
                tilRNewPass.setError("");
                return;
            }else{
                tilNewPass.setError(null);
                tilRNewPass.setError(null);
            }

            db.collection("users").document(sharedPreferences.getString("docId","")).update("pass",newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    generateSuccessDialog();
                    finish();
                }
            });
        });
    }

    private void generateSuccessDialog(){
        Dialog dialogSuccess = new Dialog(this);
        dialogSuccess.setContentView(R.layout.custom_dialog);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView text = dialogSuccess.findViewById(R.id.custom_dialog_text);

        text.setText("Your new password has been saved!");

        dialogSuccess.show();

        new Handler().postDelayed(() -> {
            dialogSuccess.dismiss();
        },2000);

    }
}