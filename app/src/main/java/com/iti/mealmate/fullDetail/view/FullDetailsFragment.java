package com.iti.mealmate.fullDetail.view;
import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.widget.DatePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.iti.mealmate.R;
import com.iti.mealmate.databinding.FragmentFullDetailsBinding;
import com.iti.mealmate.db.favouriteMeal.LocalFavMealsDataSource;
import com.iti.mealmate.db.plannedMeal.LocalPlannedMealsDataSource;
import com.iti.mealmate.fullDetail.presenter.FullDetailsPresenter;
import com.iti.mealmate.model.FlagSource;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.PlannedMeal;
import com.iti.mealmate.model.UserSharedPref;
import com.iti.mealmate.network.RemoteDataSource;
import com.iti.mealmate.repo.meal.MealsRepo;
import com.iti.mealmate.repo.plannedMeal.PlannedMealRepo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FullDetailsFragment extends Fragment implements IFullDetails {
    FragmentFullDetailsBinding binding;
    FullDetailsPresenter presenter;
    FulIngradientAdapter adapter;
    LinearLayoutManager layoutManager;
    boolean isFavorite = false;
    private FlagSource flagSource = new FlagSource();


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
        String mealID = FullDetailsFragmentArgs.fromBundle(getArguments()).getMealID();
        int id = FullDetailsFragmentArgs.fromBundle(getArguments()).getId();
        int plannedID = FullDetailsFragmentArgs.fromBundle(getArguments()).getPlannedMealID();

        presenter = new FullDetailsPresenter(this, MealsRepo.getInstance(RemoteDataSource.getInstance(), LocalFavMealsDataSource.getInstance(getContext())), PlannedMealRepo.getInstance(RemoteDataSource.getInstance(), LocalPlannedMealsDataSource.getInstance(getContext())));

        if(UserSharedPref.getUserId()== null){
            binding.btntnFav.setVisibility(View.GONE);
            binding.btnCalenar.setVisibility(View.GONE);
        }
        if (id == 1) {
            presenter.getFullFavLocalMeal(mealID);
            binding.btntnFav.setVisibility(View.GONE);
        } else if(id == 2){
            presenter.getFullPlannedLocalMeal(plannedID);
            binding.btnCalenar.setVisibility(View.GONE);

        }else {
            presenter.getFullDetailedMeal(mealID);
        }
    }

    @Override
    public void onFullDetailedMealSuccess(Meal meal) {
        binding.mealName.setText(meal.getStrMeal());
        String[] instructions = meal.getStrInstructions().split("[.]");
        binding.instructionsTxt.setText(instructions[0]);
        Log.i("TAG", "showMealData: " + instructions[0]);
        for (int i = 1; i < instructions.length; i++) {
            binding.instructionsTxt.setText(binding.instructionsTxt.getText() + "\n" + instructions[i]);
            binding.instructionsTxt.setText(binding.instructionsTxt.getText() + "\n");
        }
        Glide.with(getContext())
                .load(meal.getStrMealThumb())
                .apply(new RequestOptions())
                .placeholder(R.drawable.world_pasta_day)
                .error(R.drawable.world_pasta_day)
                .into(binding.mealImg);


        binding.areaName.setText(meal.getStrArea());
        binding.flag.setImageResource(flagSource.getFlagIdByName(meal.getStrArea()));
        binding.categoryName.setText(meal.getStrCategory());
        binding.btntnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isFavorite = !isFavorite;
                if (isFavorite) {
                   binding.btntnFav.setImageResource(R.drawable.favorite_fill);
                } else {
                    binding.btntnFav.setImageResource(R.drawable.ic_fav_unfill);
                }

                presenter.addToFav(meal);
                Toast.makeText(getContext(), "added to Favourite", Toast.LENGTH_SHORT).show();
            }
        });


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        binding.btnCalenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String date = ""+dayOfMonth+""+month+""+year;
                                PlannedMeal plannedMeal = new PlannedMeal(meal, date);
                                presenter.addToPlan(plannedMeal);
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 100);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}