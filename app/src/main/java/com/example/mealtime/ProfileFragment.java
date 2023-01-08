package com.example.mealtime;

import static android.content.Context.MODE_APPEND;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    private ViewGroup root;
    private LinearLayout editProfile;
    private LinearLayout changePassword;
    private LinearLayout helpInfo;
    private LinearLayout postLiked;
    private TextView name;
    private TextView email;
    private SharedPreferences sh;
    private LinearLayout logout;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);
        editProfile = root.findViewById(R.id.fragment_profile_edt_profile);
        changePassword = root.findViewById(R.id.fragment_profile_change_password);
        helpInfo = root.findViewById(R.id.fragment_profile_help_info);
        postLiked = root.findViewById(R.id.fragment_profile_post_liked);
        email = root.findViewById(R.id.fragment_profile_email);
        name = root.findViewById(R.id.fragment_profile_name);
        logout = root.findViewById(R.id.fragment_profile_logout);
        sh = getActivity().getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        name.setText(sh.getString("fullName", ""));
        email.setText(sh.getString("email",""));
        editProfile.setOnClickListener(view -> {
            getActivity().startActivity(new Intent(getContext(),EditProfileActivity.class));
        });
        changePassword.setOnClickListener(view -> {
            getActivity().startActivity(new Intent(getContext(),ChangePasswordActivity.class));
        });
        helpInfo.setOnClickListener(view -> {
            getActivity().startActivity(new Intent(getContext(),HelpInfoActivity.class));
        });
        postLiked.setOnClickListener(view -> {
            getActivity().startActivity(new Intent(getContext(),PostLikedActivity.class));
        });
        logout.setOnClickListener(view -> {
            generateLogoutDialog();
        });
        return root;
    }

    private void generateLogoutDialog(){
        Dialog dialogLogut = new Dialog(getContext());
        dialogLogut.setContentView(R.layout.custom_dialog_logout);
        dialogLogut.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnLogout = dialogLogut.findViewById(R.id.custom_dialog_logout_btnLogout);
        Button btnCancel = dialogLogut.findViewById(R.id.custom_dialog_logout_btnCancel);

        btnCancel.setOnClickListener(view -> dialogLogut.dismiss());
        btnLogout.setOnClickListener(view -> {
            sh.edit().clear().apply();
            getActivity().finish();
        });
        dialogLogut.show();
    }
}