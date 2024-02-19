package com.iti.mealmate.search.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iti.mealmate.R;
import com.iti.mealmate.model.Ingredient;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Ingredient> ingredientList;
    private OnIngredientClickListener listener;

    public IngredientAdapter(Context _context ,ArrayList<Ingredient> _ingredientList) {
        this.context = _context;
        this.ingredientList = _ingredientList;
    }



//    public CategoriesAdapter(Context _context, OnCategoryClickListener _listener, ArrayList<Category> _categoryList) {
//        this.context = _context;
//        this.categoryList = _categoryList;
//        this.listener = _listener;
//    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ingrediant_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.ingredientName.setText(ingredientList.get(position).getStrIngredient());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragmentDirections.ActionSearchFragment2ToAllMealsFragment action = SearchFragmentDirections.actionSearchFragment2ToAllMealsFragment();
                action.setCategoryName(ingredientList.get(position).getStrIngredient());
                action.setId(2);
                Navigation.findNavController(v).navigate(action);            }
        });
        String image=String.format("https://www.themealdb.com/images/ingredients/%s.png", ingredient.getStrIngredient());
        Glide.with(context).load(image).apply(new RequestOptions()
                        .override(80,80))
                .placeholder(R.drawable.world_pasta_day)
                .error(R.drawable.world_pasta_day)
                .into(holder.ingredientThumb);

    }

    @Override
    public int getItemCount() {
        return ingredientList != null ? ingredientList.size() : 0;
    }

    public void setList(ArrayList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName ;
        ImageView ingredientThumb;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingrediant_txt);
            ingredientThumb = itemView.findViewById(R.id.ingrediant_img);
            cardView = itemView.findViewById(R.id.ingrediant_card);
        }
    }
}


interface OnIngredientClickListener {
    void searchByIngredientName(String categoryName);
}
