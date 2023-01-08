package com.example.mealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout tilFullname;
    private TextInputLayout tilUsername;
    private TextInputLayout tilEmail;
    private TextInputLayout tilPass;
    private TextInputLayout tilConfrimPass;

    private EditText edtFullname;
    private EditText edtUsername;
    private EditText edtEmail;
    private EditText edtPass;
    private EditText edtConfirmPass;

    private Button btnRegister;
    private CheckBox agree;
    private TextView login;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    void init(){
        tilFullname = findViewById(R.id.activity_signup_txtInputLayoutName);
        tilUsername = findViewById(R.id.activity_signup_txtInputLayoutUsername);
        tilEmail = findViewById(R.id.activity_signup_txtInputLayoutEmail);
        tilPass = findViewById(R.id.activity_signup_txtInputLayoutPass);
        tilConfrimPass = findViewById(R.id.activity_signup_txtInputLayoutCPass);

        edtFullname = findViewById(R.id.activity_signup_edtTextName);
        edtUsername = findViewById(R.id.activity_signup_edtTextUsername);
        edtEmail = findViewById(R.id.activity_signup_edtTextEmail);
        edtPass = findViewById(R.id.activity_signup_edtTextPassword);
        edtConfirmPass = findViewById(R.id.activity_signup_edtTextCPassword);

        btnRegister = findViewById(R.id.activity_signup_btnRegister);
        agree = findViewById(R.id.activity_signup_checkbox);
        login = findViewById(R.id.activity_signup_textLogin);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();

        checkboxStatus();

        btnRegister.setOnClickListener(view -> {
            validateField();
        });

        login.setOnClickListener(view -> {
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        });
    }

    private void checkboxStatus(){
        agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    btnRegister.setEnabled(true);
                }else{
                    btnRegister.setEnabled(false);
                }
            }
        });
    }
    private void validateField() {
        String fname = edtFullname.getText().toString().trim();
        String username = edtUsername.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        String cpass = edtConfirmPass.getText().toString().trim();

        if (fname.isEmpty()){
            tilFullname.setError("This field is required!");
            return;
        }else{
            tilFullname.setError(null);
        }
        if (username.isEmpty()){
            tilUsername.setError("This field is required!");
            return;
        }else{
            tilUsername.setError(null);
        }
        if (email.isEmpty()){
            tilEmail.setError("This field is required!");
            return;
        }else{
            tilEmail.setError(null);
        }
        if (pass.isEmpty()){
            tilPass.setError("This field is required!");
            return;
        }else{
            tilPass.setError(null);
        }
        if (cpass.isEmpty()){
            tilConfrimPass.setError("This field is required!");
            return;
        }else{
            tilConfrimPass.setError(null);
        }

        if (!cpass.matches(pass)){
            tilConfrimPass.setError("Password does not match!");
            return;
        }else{
            tilConfrimPass.setError(null);
        }

        Map<String, Object> user = new HashMap<>();
        user.put("fullName", fname);
        user.put("username", username);
        user.put("email", email);
        user.put("pass",pass);


        db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                generateSuccessDialog();
                edtFullname.setText("");
                edtUsername.setText("");
                edtEmail.setText("");
                edtPass.setText("");
                edtConfirmPass.setText("");
            }
        });

    }

    private void generateSuccessDialog(){
        Dialog dialogSuccess = new Dialog(this);
        dialogSuccess.setContentView(R.layout.custom_dialog);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView text = dialogSuccess.findViewById(R.id.custom_dialog_text);

        text.setText("Registration Success!");

        dialogSuccess.show();

        new Handler().postDelayed(() -> {
            dialogSuccess.dismiss();
        },2000);

    }


}