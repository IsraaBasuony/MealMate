package com.iti.mealmate.home.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iti.mealmate.R;
import com.iti.mealmate.databinding.FragmentHomeBinding;
import com.iti.mealmate.db.favouriteMeal.LocalFavMealsDataSource;
import com.iti.mealmate.home.presenter.HomePresenter;
import com.iti.mealmate.model.Category;
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
        homePresenter = new HomePresenter(this, MealsRepo.getInstance(RemoteDataSource.getInstance(), LocalFavMealsDataSource.getInstance(getContext())));

    }


    public void onStart() {
        super.onStart();
        NetworkRequest networkRequest = getNetworkRequest();

        Handler mHandler = new Handler(Looper.getMainLooper());
        ConnectivityManager.NetworkCallback networkCallback = getNetworkCallback(mHandler);
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.requestNetwork(networkRequest , networkCallback);
    }

    @NonNull
    private ConnectivityManager.NetworkCallback getNetworkCallback(Handler mHandler) {
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback(){
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("ISraa", "run: " + "here");
                        binding.noInternetLayout.setVisibility(View.GONE);
                        binding.internetHome.setVisibility(View.VISIBLE);
                        homePresenter.getRandomMeal();
                        homePresenter.getAllMeals();
                    }
                });
            }
            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("ISraa", "run: " + "here lost");
                        binding.noInternetLayout.setVisibility(View.VISIBLE);
                        binding.internetHome.setVisibility(View.GONE);
                    }
                });
            }
        };
        return networkCallback;
    }

    private static NetworkRequest getNetworkRequest() {
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();
        return networkRequest;
    }

    @Override
    public void showMealOfTheDay(MealModel mealModel) {
        binding.titleTxt.setText(mealModel.getStrMeal());
        binding.discribtionTxt.setText(mealModel.getStrInstructions());
        binding.inspirationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeFragmentDirections.ActionHomeFragment2ToFullDetailsFragment2 action = HomeFragmentDirections.actionHomeFragment2ToFullDetailsFragment2();
                action.setMealID(mealModel.getIdMeal());
                Navigation.findNavController(view).navigate(action);

            }
        });
        Glide.with(getContext())
                .load(mealModel.getStrMealThumb())
                .apply(new RequestOptions())
                .placeholder(R.drawable.world_pasta_day)
                .error(R.drawable.world_pasta_day)
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
    public void showAllCategories(List<Category> categoryList) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}