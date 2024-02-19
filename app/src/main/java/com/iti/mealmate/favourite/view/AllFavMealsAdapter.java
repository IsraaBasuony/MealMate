package com.iti.mealmate.favourite.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.mealmate.R;
import com.iti.mealmate.allMeals.view.AllMealsFragmentDirections;
import com.iti.mealmate.home.view.HomeFragmentDirections;
import com.iti.mealmate.model.Meal;

import java.util.ArrayList;

public class AllFavMealsAdapter extends RecyclerView.Adapter<AllFavMealsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Meal> mealModelArrayList;
    private OnDeleteClickListener listener;


    public AllFavMealsAdapter(Context _context, OnDeleteClickListener _listener, ArrayList<Meal> _mealArrayList) {
        this.context = _context;
        this.mealModelArrayList = _mealArrayList;
        this.listener = _listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Meal meal = mealModelArrayList.get(position);
        holder.mealName.setText(mealModelArrayList.get(position).getStrMeal());
        Glide.with(holder.itemView.getContext()).load(mealModelArrayList.get(position).getStrMealThumb())
                .placeholder(R.drawable.world_pasta_day)
                .error(R.drawable.world_pasta_day)
                .into(holder.mealThumb);
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDelClick(meal);
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavouriteFragmentDirections.ActionFavouriteFragmentToFullDetailsFragment action = FavouriteFragmentDirections.actionFavouriteFragmentToFullDetailsFragment();
                action.setMealID(meal.getIdMeal());
                action.setId(1);
                Navigation.findNavController(view).navigate(action);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mealModelArrayList.size();
    }

    public void setList(ArrayList<Meal> mealModelArrayList) {
        this.mealModelArrayList = mealModelArrayList;
        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mealName;
        ImageView mealThumb;
        CardView cardView;
        ImageButton btnDel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.fav_txt);
            mealThumb = itemView.findViewById(R.id.fav_img);
            cardView = itemView.findViewById(R.id.fav_card);
            btnDel = itemView.findViewById(R.id.btn_del);

        }

    }
}

interface OnDeleteClickListener {
    void onDelClick(Meal meal);
}


