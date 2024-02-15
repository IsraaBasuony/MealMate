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

import com.iti.mealmate.R;
import com.iti.mealmate.model.Country;

import java.util.ArrayList;


public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Country> countryList;
    private OnCountryClickListener listener;
    public CountriesAdapter(Context _context, ArrayList<Country> _countryList) {
        this.context = _context;
        this.countryList = _countryList;
    }


//    public CategoriesAdapter(Context _context, OnCategoryClickListener _listener, ArrayList<Category> _categoryList) {
//        this.context = _context;
//        this.categoryList = _categoryList;
//        this.listener = _listener;
//    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Country country = countryList.get(position);
        holder.countryName.setText(countryList.get(position).getStrArea());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchFragmentDirections.ActionSearchFragment2ToAllMealsFragment action = SearchFragmentDirections.actionSearchFragment2ToAllMealsFragment();
                action.setCategoryName(countryList.get(position).getStrArea());
                action.setId(3);
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public void setList(ArrayList<Country> countryList) {
        this.countryList = countryList;
        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView countryName;
        ImageView countryThumb;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.country_txt);
            countryThumb = itemView.findViewById(R.id.category_img);
            cardView = itemView.findViewById(R.id.country_card);

        }


    }
}


interface OnCountryClickListener {
    void onCountryClicked(String categoryName);
}
