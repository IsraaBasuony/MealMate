package com.iti.mealmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.iti.mealmate.model.UserSharedPref;

public class SplashActivity extends AppCompatActivity {

    private static final String PREF_NAME = "MyAppPrefs";
    private static final String PREF_FIRST_LAUNCH = "first_launch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        SharedPreferences sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean isFirstLaunch = sharedPref.getBoolean(PREF_FIRST_LAUNCH, true);

        UserSharedPref.init(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isFirstLaunch) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean(PREF_FIRST_LAUNCH, false);
                    editor.apply();
                    startActivity(new Intent(SplashActivity.this, WelcomActivity.class));
                    finish();
                }
                else if(UserSharedPref.getUserId().isEmpty()){
                    startActivity(new Intent(SplashActivity.this, AuthActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                }
            }
        }, 3000);
    }
}