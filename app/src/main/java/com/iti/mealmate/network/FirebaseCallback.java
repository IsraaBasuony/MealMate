package com.iti.mealmate.network;

public interface FirebaseCallback {
    void onSuccess(String userId);

    void onFail(String msg);
}
