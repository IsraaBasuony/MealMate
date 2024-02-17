package com.iti.mealmate.Calender.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.mealmate.R;
import com.iti.mealmate.favourite.view.FavouriteFragmentDirections;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.PlannedMeal;

import java.util.ArrayList;

public class AllPlannedMealsAdapter extends RecyclerView.Adapter<AllPlannedMealsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<PlannedMeal> plannedMealArrayList;
    private OnDeleteClickListener listener;


    public AllPlannedMealsAdapter(Context _context, OnDeleteClickListener _listener, ArrayList<PlannedMeal> _plannedMealArrayList) {
        this.context = _context;
        this.plannedMealArrayList = _plannedMealArrayList;
        this.listener = _listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.planned_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PlannedMeal plannedMeal = plannedMealArrayList.get(position);
        holder.mealName.setText(plannedMealArrayList.get(position).getMeal().getStrMeal());
         holder.btnDel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 listener.onDelClick(plannedMeal);
             }
         });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalenderFragmentDirections.ActionCalenderFragmentToFullDetailsFragment action = CalenderFragmentDirections.actionCalenderFragmentToFullDetailsFragment();
                action.setPlannedMealID(plannedMeal.id);
                action.setId(2);
                Navigation.findNavController(view).navigate(action);
            }
        });

    }

    @Override
    public int getItemCount() {
        return plannedMealArrayList.size();
    }

    public void setList(ArrayList<PlannedMeal> mealModelArrayList) {
        this.plannedMealArrayList = mealModelArrayList;
        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mealName;
        Button btnDel;
        CardView cardView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.planned_txt);
            btnDel = itemView.findViewById(R.id.btn_del_plan);
            cardView = itemView.findViewById(R.id.planned_card);


        }

    }
}

interface OnDeleteClickListener {
    void onDelClick(PlannedMeal meal);
}


