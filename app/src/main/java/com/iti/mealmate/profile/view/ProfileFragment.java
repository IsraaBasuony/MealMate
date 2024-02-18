package com.iti.mealmate.profile.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iti.mealmate.MainActivity;
import com.iti.mealmate.MainActivity2;
import com.iti.mealmate.R;
import com.iti.mealmate.databinding.FragmentProfileBinding;
import com.iti.mealmate.db.favouriteMeal.LocalFavMealsDataSource;
import com.iti.mealmate.db.plannedMeal.LocalPlannedMealsDataSource;
import com.iti.mealmate.network.RemoteDataSource;
import com.iti.mealmate.profile.presenter.ProfilePresenter;
import com.iti.mealmate.repo.authentication.FireRepo;
import com.iti.mealmate.repo.meal.MealsRepo;
import com.iti.mealmate.repo.plannedMeal.PlannedMealRepo;

public class ProfileFragment extends Fragment implements IProfile{

    FragmentProfileBinding binding;
    ProfilePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new ProfilePresenter(this, PlannedMealRepo.getInstance(RemoteDataSource.getInstance(), LocalPlannedMealsDataSource.getInstance(getContext())), MealsRepo.getInstance(RemoteDataSource.getInstance(), LocalFavMealsDataSource.getInstance(getContext())));
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogoutSuccess();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onLogoutSuccess() {

        presenter.logOut();
    }

    @Override
    public void onLogoutFail(String errorMsg) {

        Toast.makeText(requireContext(), "Fail: " +errorMsg, Toast.LENGTH_SHORT).show();
    }
}