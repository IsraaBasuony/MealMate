package com.iti.mealmate.search.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.mealmate.R;
import com.iti.mealmate.model.Category;

import java.util.ArrayList;
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Category> categoryList;
    private OnCategoryClickListener listener;

    public CategoriesAdapter(Context _context, ArrayList<Category> _categoryList) {
        this.context = _context;
        this.categoryList = _categoryList;
    }


//    public CategoriesAdapter(Context _context, OnCategoryClickListener _listener, ArrayList<Category> _categoryList) {
//        this.context = _context;
//        this.categoryList = _categoryList;
//        this.listener = _listener;
//    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryName.setText(categoryList.get(position).getStrCategory());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.searchByCategoryName(category.getStrCategory());
            }
        });
        Glide.with(holder.itemView.getContext()).load(categoryList.get(position).getStrCategoryThumb())
                .into(holder.categoryThumb);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void setList(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;
        ImageView categoryThumb;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_txt);
            categoryThumb = itemView.findViewById(R.id.category_img);
            cardView = itemView.findViewById(R.id.category_card);

        }
    }
}


interface OnCategoryClickListener {
    void searchByCategoryName(String categoryName);
}
