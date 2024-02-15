package com.iti.mealmate.fullDetail.view;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.iti.mealmate.R;
import com.iti.mealmate.databinding.FragmentFullDetailsBinding;
import com.iti.mealmate.db.LocalFavMealsDataSource;
import com.iti.mealmate.fullDetail.presenter.FullDetailsPresenter;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.network.RemoteDataSource;
import com.iti.mealmate.repo.meal.MealsRepo;
import com.iti.mealmate.search.view.CategoriesAdapter;
import com.iti.mealmate.search.view.CountriesAdapter;
import com.iti.mealmate.search.view.IngredientAdapter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FullDetailsFragment extends Fragment implements IFullDetails {

    FragmentFullDetailsBinding binding;
    FullDetailsPresenter presenter;

    FulIngradientAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFullDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new FulIngradientAdapter(getContext(), new ArrayList<>());
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.ingrediantRec.setLayoutManager(layoutManager);
        binding.ingrediantRec.setAdapter(adapter);
        String id = FullDetailsFragmentArgs.fromBundle(getArguments()).getMealID();
        presenter = new FullDetailsPresenter(this, MealsRepo.getInstance(RemoteDataSource.getInstance(), LocalFavMealsDataSource.getInstance(getContext())));
        presenter.getFullDetailedMeal(id);
    }

    @Override
    public void onFullDetailedMealSuccess(Meal meal) {
        binding.mealName.setText(meal.getStrMeal());
        binding.instructionsTxt.setText(meal.getStrInstructions());
        Glide.with(getContext())
                .load(meal.getStrMealThumb())
                .apply(new RequestOptions())
                .error(R.drawable.ic_launcher_background)
                .into(binding.mealImg);

        Glide.with(getContext())
                .asBitmap()
                .load(meal.getStrMealThumb())
                .apply(new RequestOptions().override(200, 200))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Log.i("TAG", "onResourceReady: ");
                        meal.setImage(resource);
                        binding.mealImg.setImageBitmap(resource);
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });


        binding.areaName.setText(meal.getStrArea());
        binding.btntnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addToFav(meal);
                Log.i("TAG", "onClick: "+ meal.getImage().getByteCount());
            }
        });
        adapter.setList(meal.combineIngredientsAndMeasures(meal));
        String videoId = extractVideoId(meal.getStrYoutube());
        String embeddedVideoUrl = "https://www.youtube.com/embed/" + videoId;
        String video = "<iframe width=\"100%\" height=\"100%\" src=\"" + embeddedVideoUrl + "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        binding.video.loadData(video, "text/html", "utf-8");
        binding.video.getSettings().setJavaScriptEnabled(true);
        binding.video.setWebChromeClient(new WebChromeClient());
    }

    @Override
    public void onFullDetailedMealFail(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(errorMessage).setTitle("ERROR");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private String extractVideoId(String youtubeUrl) {
        String videoId = null;
        String pattern = "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|/e/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        if (youtubeUrl != null) {
            Matcher matcher = compiledPattern.matcher(youtubeUrl);
            if (matcher.find()) {
                videoId = matcher.group();
            }
        }
        return videoId;

    }
}