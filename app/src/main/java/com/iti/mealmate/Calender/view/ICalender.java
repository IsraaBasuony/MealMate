package com.iti.mealmate.Calender.view;
import com.iti.mealmate.model.PlannedMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface ICalender {

    void setPlannedMeal(Flowable<List<PlannedMeal>> mealList);

}
