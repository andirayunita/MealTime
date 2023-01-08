package com.example.mealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilUsername;
    private TextInputLayout tilPassword;

    private EditText edtUsername;
    private EditText edtPass;

    private Button btnLogin;
    private TextView signup;
    private FirebaseFirestore db;
    private boolean isUserExist= false;
    private SharedPreferences sharedPreferences;
    void init(){
        db = FirebaseFirestore.getInstance();
        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        tilUsername = findViewById(R.id.activity_login_txtInputLayoutName);
        tilPassword = findViewById(R.id.activity_login_txtInputLayoutPass);
        edtUsername = findViewById(R.id.activity_login_edtTextName);
        edtPass = findViewById(R.id.activity_login_edtTextPassword);
        btnLogin = findViewById(R.id.activity_login_btnLogin);
        signup = findViewById(R.id.activity_login_signup);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        signup.setOnClickListener(view -> {
            finish();
            startActivity(new Intent(this,SignUpActivity.class));
        });
        btnLogin.setOnClickListener(view -> {
            validateField();
        });
    }

    private void validateField() {
        isUserExist= false;
        String username = edtUsername.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();

        if (username.isEmpty()){
            tilUsername.setError("This field is required!");
            return;
        }else{
            tilUsername.setError(null);
        }

        if (pass.isEmpty()){
            tilPassword.setError("This field is required!");
            return;
        }else{
            tilPassword.setError(null);
        }

        db.collection("users").get()
                .continueWithTask(new Continuation<QuerySnapshot, Task<List<QuerySnapshot>>>() {
                    @Override
                    public Task<List<QuerySnapshot>> then(@NonNull Task<QuerySnapshot> task) {
                        List<Task<QuerySnapshot>> tasks = new ArrayList<Task<QuerySnapshot>>();
                        for (DocumentSnapshot ds : task.getResult()) {
                            if (username.matches(ds.get("username").toString())
                                    && pass.matches(ds.get("pass").toString())){
                                Log.e("mas",ds.getId());
                                isUserExist = true;
                                edtPass.setText("");
                                edtUsername.setText("");
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putBoolean("login", true);
                                myEdit.putString("fullName", ds.get("fullName").toString());
                                myEdit.putString("password", ds.get("pass").toString());
                                myEdit.putString("email", ds.get("email").toString());
                                myEdit.putString("username", ds.get("username").toString());
                                myEdit.putString("docId",ds.getId());
                                myEdit.commit();
                                break;
                            }
                        }

                        return Tasks.whenAllSuccess(tasks);
                    }
                }).addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
            @Override
            public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
                if (isUserExist){
                    tilUsername.setError(null);
                    tilPassword.setError(null);
                    startActivity(new Intent(getApplicationContext(),AppActivity.class));
                }else{
                    tilUsername.setError("Username invalid");
                    tilPassword.setError("Password invalid");
                    return;
                }
            }
        });



    }
}