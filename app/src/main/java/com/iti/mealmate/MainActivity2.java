package com.iti.mealmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.iti.mealmate.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {
    private NavController navController;
    ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment2);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}