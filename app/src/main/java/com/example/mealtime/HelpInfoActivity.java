package com.example.mealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HelpInfoActivity extends AppCompatActivity {

    private TextView send;

    private TextInputLayout tilProb;
    private TextInputLayout tilEmail;

    private EditText edtTxtProb;
    private EditText edtTxtEmail;

    private FirebaseFirestore db ;
    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_info);

        db = FirebaseFirestore.getInstance();
        backBtn = findViewById(R.id.activity_help_info_backBtn);

        tilProb = findViewById(R.id.activity_help_info_TIL_prob);
        tilEmail = findViewById(R.id.activity_help_info_TIL_email);

        edtTxtProb = findViewById(R.id.activity_help_info_edtTxt_prob);
        edtTxtEmail = findViewById(R.id.activity_help_info_edtTxt_email);

        send = findViewById(R.id.activity_help_info_send);

        send.setOnClickListener(view -> {
            validateField();
        });

        backBtn.setOnClickListener(view -> {
            finish();
        });


    }

    private void validateField() {
        String prob = edtTxtProb.getText().toString().trim();
        String email = edtTxtEmail.getText().toString().trim();

        if (prob.isEmpty()){
            tilProb.setError("This field is required!");
            return;
        }else{
            tilProb.setError(null);
        }
        if (email.isEmpty()){
            tilEmail.setError("This field is required!");
            return;
        }else{
            tilEmail.setError(null);
        }
        Map<String, Object> help = new HashMap<>();
        help.put("problem", prob);
        help.put("email", email);

        db.collection("help_and_info").add(help).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                generateSuccessDialog();
                finish();
            }
        });

    }

    private void generateSuccessDialog(){
        Dialog dialogSuccess = new Dialog(this);
        dialogSuccess.setContentView(R.layout.custom_dialog);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView text = dialogSuccess.findViewById(R.id.custom_dialog_text);

        text.setText("Your problem has been sent!");

        dialogSuccess.show();

        new Handler().postDelayed(() -> {
            dialogSuccess.dismiss();
        },2000);

    }
}