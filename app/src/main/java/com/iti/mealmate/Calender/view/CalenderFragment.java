package com.iti.mealmate.Calender.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iti.mealmate.Calender.presenter.CalenderPresenter;
import com.iti.mealmate.R;
import com.iti.mealmate.databinding.FragmentCalenderBinding;
import com.iti.mealmate.databinding.FragmentFavouriteBinding;
import com.iti.mealmate.db.favouriteMeal.LocalFavMealsDataSource;
import com.iti.mealmate.db.plannedMeal.LocalPlannedMealsDataSource;
import com.iti.mealmate.favourite.presenter.FavouritePresenter;
import com.iti.mealmate.favourite.view.AllFavMealsAdapter;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.PlannedMeal;
import com.iti.mealmate.network.RemoteDataSource;
import com.iti.mealmate.repo.meal.MealsRepo;
import com.iti.mealmate.repo.plannedMeal.PlannedMealRepo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalenderFragment extends Fragment implements ICalender {

    FragmentCalenderBinding binding;
    CalenderPresenter presenter;
    String date;
    PlannedMeal plannedMeal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalenderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new CalenderPresenter(PlannedMealRepo.getInstance(RemoteDataSource.getInstance(), LocalPlannedMealsDataSource.getInstance(getContext())), this);
        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = "" + i2 + "" + i1 + "" + i;
                presenter.getPlannedMeal(date);
            }
        });
        binding.btnDelPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.deletePlannedMeal(plannedMeal);
            }
        });
    }

    @Override
    public void setPlannedMeal(Flowable<List<PlannedMeal>> mealList) {
        mealList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item ->
                        {
                            plannedMeal = item.get(0);
                            setCard(item.get(0));
                        },
                        e -> Log.i("Planned empty", "setPlannedMeal: ")// no data in this date
                );

    }

    private void setCard(PlannedMeal plannedMeal) {
        Glide.with(getContext())
                .load(plannedMeal.getMeal().getStrMealThumb())
                .apply(new RequestOptions())
                .error(R.drawable.ic_launcher_background)
                .into(binding.plannedImg);
        binding.plannedTxt.setText(plannedMeal.getMeal().getStrMeal());
    }
}