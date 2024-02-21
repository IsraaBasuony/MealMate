package com.iti.mealmate.home.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iti.mealmate.R;
import com.iti.mealmate.model.MealModel;


import java.util.ArrayList;

public class AllMealsAdapter extends RecyclerView.Adapter<AllMealsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<MealModel> mealModelArrayList;
    private OnMealClickListener listener;

    public AllMealsAdapter(Context _context , ArrayList<MealModel> _mealModelList) {
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
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lazy_meal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MealModel mealModel = mealModelArrayList.get(position);
        holder.mealName.setText(mealModelArrayList.get(position).getStrMeal());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listener.searchByMealId(mealModelArrayList.get(position).getStrMeal());
                HomeFragmentDirections.ActionHomeFragment2ToFullDetailsFragment2 action = HomeFragmentDirections.actionHomeFragment2ToFullDetailsFragment2();
                action.setMealID(mealModelArrayList.get(position).getIdMeal());
                Navigation.findNavController(v).navigate(action);
            }
        });
        Glide.with(holder.itemView.getContext()).load(mealModelArrayList.get(position).getStrMealThumb())
                .apply(new RequestOptions())
                .placeholder(R.drawable.world_pasta_day)
                .error(R.drawable.world_pasta_day)
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
            mealName = itemView.findViewById(R.id.title_txt);
            mealThumb = itemView.findViewById(R.id.lazy_meal_img);
            cardView = itemView.findViewById(R.id.lazy_meal_card);

        }


        }
    }


interface OnMealClickListener {
    void searchByMealId(String mealId);
}
