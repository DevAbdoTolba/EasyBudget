package com.benoitletondor.easybudgetapp.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.benoitletondor.easybudgetapp.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

/**
 * A simple activity to test Firebase Authentication
 * NOTE: This is only for testing purposes - remove this class after verifying authentication works
 */
public class FirebaseAuthTestActivity extends AppCompatActivity {

    private static final String TAG = "FirebaseAuthTest";
    private TextView statusTextView;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Successfully signed in
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    updateUI(user);
                    Toast.makeText(this, "Sign in successful!", Toast.LENGTH_SHORT).show();
                } else {
                    // Sign in failed
                    updateUI(null);
                    Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_auth_test);

        statusTextView = findViewById(R.id.text_auth_status);
        Button signInButton = findViewById(R.id.button_sign_in);
        Button signOutButton = findViewById(R.id.button_sign_out);

        signInButton.setOnClickListener(v -> signIn());
        signOutButton.setOnClickListener(v -> signOut());

        // Check if user is signed in
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(currentUser);
    }

    private void signIn() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        
        signInLauncher.launch(signInIntent);
    }

    private void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(task -> {
                    updateUI(null);
                    Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            statusTextView.setText(getString(R.string.signed_in_fmt, user.getDisplayName(), user.getEmail()));
            Log.d(TAG, "User ID: " + user.getUid());
        } else {
            statusTextView.setText(R.string.signed_out);
        }
    }
}
