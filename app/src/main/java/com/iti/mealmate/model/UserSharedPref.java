package com.iti.mealmate.model;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedPref {

    private static Context appContext;
    private static final String SHARED_PREFERENCES_NAME = "user data";
    private static final String USER_ID = "userId";
    private static final String USER_NAME = "username";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private  static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        appContext = context;
        sharedPreferences = appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }


    public static void setUserName(String userName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, userName).apply();
    }

    public static String getUserName() {
        return sharedPreferences.getString(USER_NAME, "");
    }

    public static void setUserEmail(String userEmail) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_EMAIL, userEmail).apply();
    }


    public static void setUserPassword(String userPassword) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PASSWORD, userPassword).apply();
    }

    public static void setUserId(String userEmail) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, userEmail).apply();
    }

    public static String getUserId() {
        if(sharedPreferences.contains(USER_ID )){
            return sharedPreferences.getString(USER_ID, "");
        }
        return null;
    }

}
