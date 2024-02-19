package com.iti.mealmate.allMeals.view;

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
import com.iti.mealmate.R;
import com.iti.mealmate.home.view.HomeFragmentDirections;
import com.iti.mealmate.model.MealModel;

import java.util.ArrayList;

public class AllMealsFromSearchAdapter extends RecyclerView.Adapter<AllMealsFromSearchAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<MealModel> mealModelArrayList;
    private OnMealClickListener listener;

    public AllMealsFromSearchAdapter(Context _context , ArrayList<MealModel> _mealModelList) {
       this.context = _context;
       this.mealModelArrayList = _mealModelList;
  }

//    public CategoriesAdapter(Context _context, OnCategoryClickListener _listener, ArrayList<Category> _categoryList) {
//        this.context = _context;
//        this.categoryList = _categoryList;
//        this.listener = _listener;
//    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MealModel mealModel = mealModelArrayList.get(position);
        holder.mealName.setText(mealModelArrayList.get(position).getStrMeal());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listener.searchByMealId(mealModelArrayList.get(position).getStrMeal());
                AllMealsFragmentDirections.ActionAllMealsFragmentToFullDetailsFragment action = AllMealsFragmentDirections.actionAllMealsFragmentToFullDetailsFragment();
                action.setMealID(mealModelArrayList.get(position).getIdMeal());
                Navigation.findNavController(v).navigate(action);
            }
        });
        Glide.with(holder.itemView.getContext()).load(mealModelArrayList.get(position).getStrMealThumb())
                .into(holder.mealThumb);
    }

    @Override
    public int getItemCount() {
        return mealModelArrayList.size();
    }

    public void setList(ArrayList<MealModel> mealModelArrayList) {
        this.mealModelArrayList = mealModelArrayList;
        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mealName;
        ImageView mealThumb;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.title_meal_txt);
            mealThumb = itemView.findViewById(R.id.meal_img);
            cardView = itemView.findViewById(R.id.meal_card);

        }


        }
    }


interface OnMealClickListener {
    void searchByMealId(String mealId);
}
