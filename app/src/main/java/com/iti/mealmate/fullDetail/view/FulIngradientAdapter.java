package com.iti.mealmate.fullDetail.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iti.mealmate.R;
import com.iti.mealmate.model.Meal;

import java.util.ArrayList;

public class FulIngradientAdapter extends RecyclerView.Adapter<FulIngradientAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Meal.IngredientMeasures> ingredientList;

    public FulIngradientAdapter(Context _context, ArrayList<Meal.IngredientMeasures> _ingredientList) {
        this.context = _context;
        this.ingredientList = _ingredientList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ingrediant_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Meal.IngredientMeasures ingredient = ingredientList.get(position);
        holder.ingredientName.setText(ingredientList.get(position).getMeasure());

        String image = String.format("https://www.themealdb.com/images/ingredients/%s.png", ingredient.getIngredient());
        Glide.with(context).load(image).apply(new RequestOptions()
                        .override(80, 80))
                .placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_background).into(holder.ingredientThumb);


    }

    @Override
    public int getItemCount() {
        return ingredientList != null ? ingredientList.size() : 0;
    }

    public void setList(ArrayList<Meal.IngredientMeasures> ingredientList) {
        this.ingredientList = ingredientList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName;
        ImageView ingredientThumb;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingrediant_txt);
            ingredientThumb = itemView.findViewById(R.id.ingrediant_img);
        }
    }
}

