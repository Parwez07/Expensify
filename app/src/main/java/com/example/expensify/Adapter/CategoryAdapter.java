package com.example.expensify.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensify.Model.Category;
import com.example.expensify.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    List<Category> list;
    TextView tvCategory;
    ImageView category;

    onCategoryClickListener clickListener ;
    public interface  onCategoryClickListener{
        void onCategoryClicked(Category c);
    }
    public CategoryAdapter(Context context, List<Category>list, onCategoryClickListener clickListener) {
        this.context = context;
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sample_category_item,parent,false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category c = list.get(position);
        tvCategory.setText(list.get(position).getCategoryName());
        category.setImageResource(list.get(position).getCategoryImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onCategoryClicked(c);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategory = itemView.findViewById(R.id.categoryText);
            category = itemView.findViewById(R.id.categoryIcon);
        }
    }
}
