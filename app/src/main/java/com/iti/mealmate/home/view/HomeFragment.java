package com.iti.mealmate.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iti.mealmate.R;
import com.iti.mealmate.databinding.FragmentHomeBinding;
import com.iti.mealmate.home.presenter.HomePresenter;
import com.iti.mealmate.model.MealModel;
import com.iti.mealmate.network.RemoteDataSource;
import com.iti.mealmate.repo.meal.MealsRepo;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements IViewHome {

    FragmentHomeBinding binding;
    HomePresenter homePresenter;
    AllMealsAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       adapter = new AllMealsAdapter(getContext(), new ArrayList<>());
       layoutManager = new LinearLayoutManager(getContext());
       binding.lazyMealsRec.setHasFixedSize(true);
       layoutManager.setOrientation(RecyclerView.HORIZONTAL);
       binding.lazyMealsRec.setLayoutManager(layoutManager);
       binding.lazyMealsRec.setAdapter(adapter);
        homePresenter = new HomePresenter(this, MealsRepo.getInstance(RemoteDataSource.getInstance()));
        homePresenter.getRandomMeal();
        homePresenter.getAllMeals();
    }

    @Override
    public void showMealOfTheDay(MealModel mealModel) {

        binding.titleTxt.setText(mealModel.getStrMeal());
        binding.discribtionTxt.setText(mealModel.getStrInstructions());

        Glide.with(requireContext())
                .load(mealModel.getStrMealThumb())
                .apply(new RequestOptions())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(binding.inspirationImg);
    }

    @Override
    public void showAllMeals(List<MealModel> meals) {

        adapter.setList((ArrayList<MealModel>) meals);
    }

    @Override
    public void showErrMsg(String errorMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(errorMsg).setTitle("ERROR");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}