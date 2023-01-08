package com.example.mealtime;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExplorePostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExplorePostsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText sharePost;
    private RecyclerView recyclerView;
    private ViewGroup root;

    public ExplorePostsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExplorePostsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExplorePostsFragment newInstance(String param1, String param2) {
        ExplorePostsFragment fragment = new ExplorePostsFragment();
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
        root = (ViewGroup) inflater.inflate(R.layout.fragment_explore_posts, container, false);
        recyclerView = root.findViewById(R.id.fragment_explore_post_recyclerView);
        sharePost = root.findViewById(R.id.fragment_explore_post_share);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        sharePost.setOnClickListener(view -> {
            generateAddPostDialog();
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        ExplorePostAdapter explorePostAdapter = new ExplorePostAdapter(getContext());
        recyclerView.setAdapter(explorePostAdapter);
    }

    private void generateAddPostDialog(){
        Dialog dialogSuccess = new Dialog(getContext());
        dialogSuccess.setContentView(R.layout.custom_dialog_add_post);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogSuccess.show();
    }
}