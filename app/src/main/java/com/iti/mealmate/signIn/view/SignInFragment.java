package com.iti.mealmate.signIn.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iti.mealmate.HomeActivity;
import com.iti.mealmate.R;
import com.iti.mealmate.databinding.FragmentSignInBinding;
import com.iti.mealmate.db.favouriteMeal.LocalFavMealsDataSource;
import com.iti.mealmate.model.UserSharedPref;
import com.iti.mealmate.network.RemoteDataSource;
import com.iti.mealmate.repo.meal.MealsRepo;
import com.iti.mealmate.signIn.presenter.ISignInPresenter;
import com.iti.mealmate.signIn.presenter.SignInPresenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignInFragment extends Fragment implements IViewSignIn {

    FragmentSignInBinding binding;
    ISignInPresenter _signInPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _signInPresenter = new SignInPresenter(this, MealsRepo.getInstance(RemoteDataSource.getInstance(), LocalFavMealsDataSource.getInstance(getContext())));
        binding.signUpRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();

            }
        });
        binding.guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
                getActivity().finish();
            }
        });


    }

    private void validation() {
        String email = binding.UserNameEdit.getText().toString().trim();
        String pass = binding.PasswordEdit.getText().toString().trim();

        if (email.isEmpty()) {
            binding.UserNameEdit.setError("Required");
        } else if (pass.isEmpty()) {
            binding.passwordLayout.setError("Required");
        } else if (!isValidEmail(email)) {
            binding.UserNameEdit.setError("Invalid email");
        } else if (!isValidPassword(pass)) {
            binding.passwordLayout.setError("Password must have at least 1 numeric character ,1 uppercase character,1 lowercase character,1 special symbol among @#$% and Password length should be(8:20).");
        } else {
            UserSharedPref.init(getContext().getApplicationContext());
            _signInPresenter.signin(email, pass);
        }
    }

    public boolean isValidPassword(String password) {
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    @Override
    public void onLoginSuccess(String userId) {
        _signInPresenter.getFavMeals();
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onLoginFail(String message) {
        Toast.makeText(requireActivity().getBaseContext(), "Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}