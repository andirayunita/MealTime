package com.example.mealtime;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MealsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private ViewGroup root;
    private ImageButton btnEdit;
    private FloatingActionButton floatButton;
    private int hour,minutes;
    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;
    private SimpleDateFormat sdf;
    public MealsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MealsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MealsFragment newInstance(String param1, String param2) {
        MealsFragment fragment = new MealsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    void init(){
        db = FirebaseFirestore.getInstance();
        recyclerView = root.findViewById(R.id.fragment_meals_recyclerView);
        btnEdit = root.findViewById(R.id.fragment_meals_btn_edit);
        floatButton = root.findViewById(R.id.fragment_meals_floating_button);
        sdf = new SimpleDateFormat("HH:mm");

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_meals, container, false);
        init();
        getMealList();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btnEdit.setOnClickListener(view -> {
            getActivity().startActivity(new Intent(getContext(),EditReminderActivity.class));
        });
        floatButton.setOnClickListener(view -> {
            generateDialog();
        });
        return root;
    }

    private void getMealList(){
        Query query = db.collection("meals");

        FirestoreRecyclerOptions<Meal> options = new FirestoreRecyclerOptions.Builder<Meal>()
                .setQuery(query, Meal.class)
                .build();


        adapter = new FirestoreRecyclerAdapter<Meal, MealsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MealsViewHolder holder, int position, @NonNull Meal model) {
                holder.setProductName(model.getName(),model.getDate());
            }

            @NonNull
            @Override
            public MealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.templare_recycler_view_meals, parent, false);
                return new MealsViewHolder(view);
            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    private class MealsViewHolder extends RecyclerView.ViewHolder {
        private View view;

        MealsViewHolder(View itemView) {
            super(itemView);
            view = itemView;

        }

        void setProductName(String name,String date) {
            TextView nameMeals = view.findViewById(R.id.template_recycler_view_meals_name);
            TextView time = view.findViewById(R.id.template_recycler_view_meals_time);
            nameMeals.setText(name);
            time.setText(date);
//            if (date == sdf.format(new Date())){
//                generatePopUpAlarm(date,name);
//            }
        }
    }

    private void generateSuccessDialog(){
        Dialog dialogSuccess = new Dialog(getContext());
        dialogSuccess.setContentView(R.layout.custom_dialog);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView text = dialogSuccess.findViewById(R.id.custom_dialog_text);

        text.setText("Your reminder has been saved!");

        dialogSuccess.show();

        new Handler().postDelayed(() -> {
            dialogSuccess.dismiss();
        },2000);

    }
    private void generateDialog(){
        Dialog dialogAddMeals = new Dialog(getContext());

        dialogAddMeals.setContentView(R.layout.custom_add_reminder_dialog);
        dialogAddMeals.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnSave = dialogAddMeals.findViewById(R.id.custom_add_reminder_dialog_btnSave);
        Button btnCancel = dialogAddMeals.findViewById(R.id.custom_add_reminder_dialog_btnCancel);
        RadioButton everyday = dialogAddMeals.findViewById(R.id.custom_add_reminder_dialog_radio_everyday);
        TimePicker timePicker = dialogAddMeals.findViewById(R.id.custom_add_reminder_dialog_timepicker);
        TextInputLayout tilNameMeal = dialogAddMeals.findViewById(R.id.custom_add_reminder_dialog_til);
        EditText editText = dialogAddMeals.findViewById(R.id.custom_add_reminder_dialog_edtText);

        hour = timePicker.getHour();
        minutes = timePicker.getMinute();
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int h, int m) {
                hour = h;
                minutes = m;
            }
        });

        btnCancel.setOnClickListener(view -> dialogAddMeals.dismiss());
        btnSave.setOnClickListener(view -> {
            if (editText.getText().toString().trim().isEmpty()){
                tilNameMeal.setError("This field is required!");
                return;
            }
            String repeatStr = everyday.isChecked() ? "Every day" : "Off";

            String hourStr="";
            String minutesStr="";
            if (hour<10){
                hourStr +="0";
            }if (minutes<10){
                minutesStr+="0";
            }
            String date = hourStr+ hour + ":" +minutesStr+minutes;

            Map<String, Object> meal = new HashMap<>();
            meal.put("name", editText.getText().toString().trim());
            meal.put("date", date);
            meal.put("statusRepeat", repeatStr);


            db.collection("meals").add(meal).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    dialogAddMeals.dismiss();
                    generateSuccessDialog();
                }
            });
        });
        dialogAddMeals.show();
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