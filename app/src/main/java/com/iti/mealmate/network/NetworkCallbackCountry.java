package com.iti.mealmate.network;

import com.iti.mealmate.model.Country;
import com.iti.mealmate.model.Country;

import java.util.ArrayList;

public interface NetworkCallbackCountry {
    public void onSuccessCountryResult(ArrayList<Country> countryList);
    public  void onFailureCountryResult(String errorMsg);
}
