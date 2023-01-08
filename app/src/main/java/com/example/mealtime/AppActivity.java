package com.example.mealtime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private SimpleDateFormat sdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        bottomNavigationView = findViewById(R.id.activity_app_bottom_nav);
        replaceFragment(new MealsFragment());
        bottomNavigationView.setSelectedItemId(R.id.menu_meals);
        sdf = new SimpleDateFormat("HH:mm");

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment frag = null;
                switch (item.getItemId()){
                    case R.id.menu_meals:
                        replaceFragment(new MealsFragment());
                        break;
                    case R.id.menu_explore:
                        replaceFragment(new ExploreFragment());
                        break;
                    case R.id.menu_profile:
                        replaceFragment(new ProfileFragment());
                        break;
                }
                return true;
            }
        });
    }

    private void generatePopUpAlarm(String date,String name){
        Dialog popUpAlarm = new Dialog(this);
        popUpAlarm.setContentView(R.layout.custom_dialog_pop_up_alarm);
        popUpAlarm.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView time = popUpAlarm.findViewById(R.id.custom_dialog_pop_up_alarm_time);
        TextView title = popUpAlarm.findViewById(R.id.custom_dialog_pop_up_alarm_title);
        Button snooze = popUpAlarm.findViewById(R.id.custom_dialog_pop_up_alarm_snooze);
        Button dismiss = popUpAlarm.findViewById(R.id.custom_dialog_pop_up_alarm_dismiss);
        time.setText(date);
        title.setText(name);
        snooze.setOnClickListener(view -> {
            popUpAlarm.dismiss();
        });
        dismiss.setOnClickListener(view -> {
            popUpAlarm.dismiss();
        });
        popUpAlarm.show();
    }
    private void replaceFragment(Fragment frag){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,frag).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseFirestore.getInstance().collection("meals").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (QueryDocumentSnapshot doc : value) {
                    if (doc.get("date").toString().matches(sdf.format(new Date()))){
                        generatePopUpAlarm(doc.get("date").toString(),doc.get("name").toString());
                        break;
                    }
                }
            }
        });
    }
}