package com.example.mealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.time.Duration;
import java.util.ArrayList;

public class EditReminderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageButton backButton;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirestoreRecyclerAdapter adapter;
    private ArrayList<Meal> mealUpdate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);

        recyclerView = findViewById(R.id.activity_edit_reminder_recyclerView);
        backButton = findViewById(R.id.activity_edit_reminder_backBtn);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backButton.setOnClickListener(view -> {
            finish();
        });

        getMealList();
    }

    private void getMealList(){
        Query query = db.collection("meals");


        FirestoreRecyclerOptions<Meal> options =
                new FirestoreRecyclerOptions.Builder<Meal>()
                .setQuery(query, new SnapshotParser<Meal>() {
                    @NonNull
                    @Override
                    public Meal parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        Meal meal = snapshot.toObject(Meal.class);
                        meal.setDocumentId(snapshot.getId());
                        return meal;
                    }
                })
                .setLifecycleOwner(this)
                .build();

        adapter = new FirestoreRecyclerAdapter<Meal, EditMealsViewHolder>(options) {
            @NonNull
            @Override
            public EditMealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_recycler_view_edit_reminder, parent, false);
                return new EditMealsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull EditMealsViewHolder holder, int position, @NonNull Meal model) {
                holder.itemView.setTag(model.getDocumentId());
                holder.setProductName(model.getName(),model.getDate());
            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    private class EditMealsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View view;
        EditText nameMeals;
        EditText time;
        ImageButton deleteBtn;
        ImageButton updateBtn;
        EditMealsViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            nameMeals = view.findViewById(R.id.template_recycler_view_edit_name);
            time = view.findViewById(R.id.template_recycler_view_edit_time);
            deleteBtn = view.findViewById(R.id.template_recycler_view_edit_delete);
            updateBtn = view.findViewById(R.id.template_recycler_view_edit_update);
        }

        void setProductName(String name,String date) {
            nameMeals.setText(name);
            time.setText(date);
            deleteBtn.setOnClickListener(this);
            updateBtn.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            String documentId = (String) itemView.getTag() ;
            Log.e("cobba",documentId);
            switch (view.getId()){
                case R.id.template_recycler_view_edit_delete:
                    db.collection("meals").document(documentId).delete();
                    break;
                case R.id.template_recycler_view_edit_update:
                    db.collection("meals").document(documentId).update("name",nameMeals.getText().toString());
                    db.collection("meals").document(documentId).update("date",time.getText().toString());
                    break;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}