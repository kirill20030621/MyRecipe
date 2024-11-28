package com.example.myrecipe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myrecipe.R;
import com.example.myrecipe.entities.Recipe;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    List<String> list;
    Context context;
    OnClickListener onClickListener;
    public int selectedPosition =-1;

    public CategoryAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_category_item, parent, false);
        CategoryAdapter.MyViewHolder holder = new CategoryAdapter.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        String category = list.get(position);

        if (position==selectedPosition){
            holder.tName.setTextColor(context.getResources().getColor(R.color.category_name_selected));
        }
        else
            holder.tName.setTextColor(context.getResources().getColor(R.color.category_name));
        holder.tName.setText(category);
        holder.itemView.setOnClickListener(view->{
            if (onClickListener!=null){
                onClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener= onClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tName;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tName = itemView.findViewById(R.id.tCategory);

            itemView.setOnClickListener(view->{
                if (onClickListener!=null){
                    onClickListener.onClick(getAdapterPosition());
                }
            });
        }
    }

}
