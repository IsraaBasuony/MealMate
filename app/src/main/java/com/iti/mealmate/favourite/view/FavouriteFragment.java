package com.iti.mealmate.favourite.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.mealmate.AuthActivity;
import com.iti.mealmate.HomeActivity;
import com.iti.mealmate.R;
import com.iti.mealmate.databinding.FragmentFavouriteBinding;
import com.iti.mealmate.db.favouriteMeal.LocalFavMealsDataSource;
import com.iti.mealmate.favourite.presenter.FavouritePresenter;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.network.RemoteDataSource;
import com.iti.mealmate.repo.meal.MealsRepo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FavouriteFragment extends Fragment implements IFavourite, OnDeleteClickListener {

    FragmentFavouriteBinding binding;
    AllFavMealsAdapter adapter;
    FavouritePresenter presenter;
    LinearLayoutManager layoutManager;
    private Disposable disposable;
    Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new AllFavMealsAdapter(getContext(), this, new ArrayList<>());
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        presenter = new FavouritePresenter(MealsRepo.getInstance(RemoteDataSource.getInstance(), LocalFavMealsDataSource.getInstance(getContext())), this);
        binding.recFav.setLayoutManager(layoutManager);
        binding.recFav.setAdapter(adapter);
        presenter.getAllMeal();
    }

    @Override
    public void onDelClick(Meal meal) {
        showDeletePopup(meal);
    }
    private void showDeletePopup(Meal meal) {

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custome_delete_layout);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txt = dialog.findViewById(R.id.delete_txt);
        txt.setText("Are you sure to delete\n your Favourite Meal?");
        Button delete = dialog.findViewById(R.id.delete_btn);
        ImageButton close = dialog.findViewById(R.id.btn_close_delet);

       delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                presenter.deleteFavMeal(meal);
                Toast.makeText(getActivity(), "Successfully deleted", Toast.LENGTH_SHORT).show();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }


    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        binding = null;
    }

    @Override
    public void setFavMeals(Flowable<List<Meal>> mealList) {
        disposable = mealList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                    if (item != null && !(item.isEmpty())) {
                        binding.noInternetImage.setVisibility(View.GONE);
                        binding.recFav.setVisibility(View.VISIBLE);
                        adapter.setList((ArrayList<Meal>) item);
                        adapter.notifyDataSetChanged();
                    } else {
                        binding.recFav.setVisibility(View.GONE);
                        binding.noInternetImage.setVisibility(View.VISIBLE);

                    }
                });
    }


}