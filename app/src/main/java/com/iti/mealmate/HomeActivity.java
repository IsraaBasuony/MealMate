package com.iti.mealmate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.iti.mealmate.databinding.ActivityHomeBinding;
import com.iti.mealmate.model.UserSharedPref;

public class HomeActivity extends AppCompatActivity {
    private NavController navController;
    ActivityHomeBinding binding;
    Dialog dialog;

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

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custome_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        Button loginTxt = dialog.findViewById(R.id.login_txt);
        ImageButton close = dialog.findViewById(R.id.btn_close);

        loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AuthActivity.class));
                finish();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(HomeActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}