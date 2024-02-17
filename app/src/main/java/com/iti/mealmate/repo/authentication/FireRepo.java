package com.iti.mealmate.repo.authentication;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.iti.mealmate.model.UserSharedPref;
import com.iti.mealmate.network.FirebaseCallback;
public class FireRepo implements IFireRepo {
    private final FirebaseAuth auth;
    private final FirebaseCallback firebaseCallback;

    public FireRepo(FirebaseCallback firebaseCallback) {
        this.firebaseCallback = firebaseCallback;
        this.auth = FirebaseAuth.getInstance();
    }

    @Override
    public void signUp(String email, String pass) {
        auth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                        .setDisplayName(UserSharedPref.getUserName()).build();
                authResult.getUser().updateProfile(userProfileChangeRequest);
                UserSharedPref.setUserEmail(authResult.getUser().getEmail());
                firebaseCallback.onSuccess(authResult.getUser().getUid());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                firebaseCallback.onFail(e.getMessage());
            }
        });

    }

    @Override
    public void signIn(String email, String pass) {

        auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        UserSharedPref.setUserName(authResult.getUser().getDisplayName());
                        UserSharedPref.setUserEmail(authResult.getUser().getEmail());
                        firebaseCallback.onSuccess(authResult.getUser().getUid());

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        firebaseCallback.onFail(e.getMessage());
                    }
                });
    }

    @Override
    public void logout() {

        auth.signOut();
    }
}
