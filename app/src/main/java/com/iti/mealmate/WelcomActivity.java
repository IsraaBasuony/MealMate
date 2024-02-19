package com.iti.mealmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.iti.mealmate.databinding.ActivityWelcomBinding;

public class WelcomActivity extends AppCompatActivity {

    ActivityWelcomBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomActivity.this, AuthActivity.class));
                finish();
            }
        });
    }
}