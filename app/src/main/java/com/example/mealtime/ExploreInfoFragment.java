package com.example.mealtime;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private ViewGroup root;
    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;
    public ExploreInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploreInfoFragment newInstance(String param1, String param2) {
        ExploreInfoFragment fragment = new ExploreInfoFragment();
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
        root = (ViewGroup) inflater.inflate(R.layout.fragment_explore_info, container, false);
        db = FirebaseFirestore.getInstance();
        recyclerView = root.findViewById(R.id.fragment_explore_info_recyclerView);
        getInfoList();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
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

    private void getInfoList(){
        Query query = db.collection("explore_post");

        FirestoreRecyclerOptions<Info> options = new FirestoreRecyclerOptions.Builder<Info>()
                .setQuery(query, Info.class)
                .build();


        adapter = new FirestoreRecyclerAdapter<Info, InfoViewHolder>(options) {

            @NonNull
            @Override
            public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_recycler_view_info, parent, false);
                return new InfoViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull InfoViewHolder holder, int position, @NonNull Info model) {
                holder.setInfo(model.getImg(),model.getTitle());

                holder.view.setOnClickListener(view -> {
                    Intent intent = new Intent(getContext(),DetailExplorePostActivity.class);
                    intent.putExtra("img",model.getImg());
                    intent.putExtra("title",model.getTitle());
                    intent.putExtra("desc",model.getDesc());
                    startActivity(intent);
                });
            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private class InfoViewHolder extends RecyclerView.ViewHolder{

        private View view;
        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }
        void setInfo(String imgUrl,String titleStr) {
            ImageView img = view.findViewById(R.id.template_recycler_view_info_image);
            TextView title = view.findViewById(R.id.template_recycler_view_info_title);
            Picasso.get().load(imgUrl).into(img);
            title.setText(titleStr);
        }
    }
   
}