package com.example.myrecipe.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myrecipe.R;
import com.example.myrecipe.entities.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    List<Recipe> recipeList;
    Context context;
    com.example.myrecipe.adapters.OnClickListener onClickListener;

    public RecipeAdapter(List<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.MyViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.ratingBar.setRating(recipe.getRating());
//        holder.imageView.setImageURI(Uri.parse(recipe.getImage()));
        Glide.with(context)
                .load(recipe.getImage())
                .into(holder.imageView);
        holder.tName.setText(recipe.getName());
        holder.itemView.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setOnClickListener(com.example.myrecipe.adapters.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tName;
        RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tName = itemView.findViewById(R.id.tName);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            itemView.setOnClickListener(view -> {

                if (onClickListener != null) {
                    onClickListener.onClick(getAdapterPosition());
                }
            });

        }
    }

}
