package com.example.mealtime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ExplorePostAdapter extends RecyclerView.Adapter<ExplorePostAdapter.ViewHolder> {

    Context context;

    public ExplorePostAdapter(Context context){

        this.context = context;
    }

    @NonNull
    @Override
    public ExplorePostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_recycler_view_post,
                parent, false);
        return new ExplorePostAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExplorePostAdapter.ViewHolder holder, int position) {
//        Picasso.get().load(favFoods.get(position).getImage()).into(holder.imageView);
//        holder.nama.setText(favFoods.get(position).getName());
//        String str = "";
//        if (favFoods.get(position).getAbout().length()>50){
//            str = favFoods.get(position).getAbout().substring(0,50)+"...";
//        }
//        else{
//            str = favFoods.get(position).getAbout();
//        }
//        holder.about.setText(str);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
//        private ImageView imageView;
//        private TextView nama;
//        private TextView about;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            imageView = itemView.findViewById(R.id.template_recycler_view_most_viewed_img);
//            nama = itemView.findViewById(R.id.template_recycler_view_most_viewed_foodName);
//            about = itemView.findViewById(R.id.template_recycler_view_most_viewed_foodAbout);
        }
    }
}
