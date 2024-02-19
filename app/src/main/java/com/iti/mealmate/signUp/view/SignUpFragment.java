package com.iti.mealmate.signUp.view;

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
import com.iti.mealmate.databinding.FragmentSignUpBinding;
import com.iti.mealmate.model.UserSharedPref;
import com.iti.mealmate.signUp.presenter.ISignUpPresenter;
import com.iti.mealmate.signUp.presenter.SignUpPresenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment implements IViewSignUp{


   private FragmentSignUpBinding binding;
   private ISignUpPresenter _signUpPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _signUpPresenter = new SignUpPresenter(this);

        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigate(R.id.action_signUpFragment_to_signInFragment);
            }
        });

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               validation();
            }
        });
    }

    private void  validation() {
        String name = binding.UserNameEdit.getText().toString().trim();
        String email = binding.EmailEdit.getText().toString().trim();
        String pass = binding.PasswordEdit.getText().toString().trim();

        if (name.isEmpty()) {
            binding.UserNameLayout.setError("Required");
        } else if (email.isEmpty()) {
            binding.EmailLayout.setError("Required");
        }else if (pass.isEmpty()) {
            binding.passwordLayout.setError("Required");
        }else if (!isValidEmail(email)) {
            binding.EmailLayout.setError("Invalid email");
        } else if(!isValidPassword(pass))
        {
            binding.passwordLayout.setError("Password must have at least 1 numeric character ,1 uppercase character,1 lowercase character,1 special symbol among @#$% and Password length should be(8:20).");
        }
        else {
            _signUpPresenter.signUp(email, pass);
            UserSharedPref.init(getContext().getApplicationContext());
            UserSharedPref.setUserName(name);
            UserSharedPref.setUserPassword(pass);
        }
    }

    public  boolean isValidPassword(String password)
    {
        String regex= "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    public  boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    
    @Override
    public void onRegisterSuccess(String userId) {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();

    }
    @Override
    public void onRegisterFail(String message) {
        Toast.makeText(requireActivity().getBaseContext(), "Failed", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}