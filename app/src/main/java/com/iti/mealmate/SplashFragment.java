package com.iti.mealmate;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.mealmate.model.UserSharedPref;

public class SplashFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserSharedPref.init(getActivity());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(UserSharedPref.getUserId()==null){
                    Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_welcomeFragment);
                }else{
                    Intent intent = new Intent(getActivity(), MainActivity2.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        }, 3000);
    }
}