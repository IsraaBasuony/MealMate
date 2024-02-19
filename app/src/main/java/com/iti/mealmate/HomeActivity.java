package com.iti.mealmate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.iti.mealmate.databinding.ActivityHomeBinding;
import com.iti.mealmate.model.UserSharedPref;

public class HomeActivity extends AppCompatActivity {
    private NavController navController;
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment2);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.fullDetailsFragment || navDestination.getId() == R.id.allMealsFragment) {
                bottomNavigationView.setVisibility(View.GONE);
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });
        if (UserSharedPref.getUserId() == null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                if (item.getItemId() == R.id.favouriteFragment || item.getItemId() == R.id.profileFragment || item.getItemId() == R.id.calenderFragment)
                    showLoginPopup();
                else {


                    if (item.getItemId() == R.id.searchFragment2)
                        navController.navigate(R.id.searchFragment2);

                    else if (item.getItemId() == R.id.homeFragment2)
                        navController.navigate(R.id.homeFragment2);
                }

                return true;
            });
        }


    }

    private void showLoginPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login Required")
                .setMessage("You need to login to access this feature.")
                .setPositiveButton("Login", (dialog, which) -> {
                    startActivity(new Intent(HomeActivity.this, AuthActivity.class));
                    finish();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    Toast.makeText(HomeActivity.this, "Action canceled", Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}