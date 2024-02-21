package com.iti.mealmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.iti.mealmate.databinding.ActivityWelcomBinding;

public class WelcomActivity extends AppCompatActivity {

    ActivityWelcomBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation scaleAnimation = new ScaleAnimation(
                0.3f, 1f,
                0.3f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(3000);

        binding.appName.startAnimation(scaleAnimation);
        binding.caption.startAnimation(scaleAnimation);

        binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomActivity.this, AuthActivity.class));
                finish();
            }
        });
    }
}