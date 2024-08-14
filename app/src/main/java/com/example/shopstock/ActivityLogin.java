package com.example.shopstock;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.shopstock.databinding.ActivityLoginBinding;

public class ActivityLogin extends AppCompatActivity {

    ActivityLoginBinding binding;
    DataBase database; // Assuming this class handles your database operations
    private static final String PREFS_NAME = "LoginPrefs";
    private static final String KEY_KEEP_LOGGED_IN = "keepLoggedIn";
    private static final String KEY_USER_EMAIL = "userEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Initialize your database instance
        database = new DataBase(this);

        // Check if user is already logged in
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean keepLoggedIn = sharedPreferences.getBoolean(KEY_KEEP_LOGGED_IN, false);

        // If keepLoggedIn is true, directly navigate to Home activity
        if (keepLoggedIn) {
            String email = sharedPreferences.getString(KEY_USER_EMAIL, null);
            if (email != null) {
                Intent intent = new Intent(ActivityLogin.this, Home.class);
                intent.putExtra("email", email); // Pass email as extra
                intent.putExtra("username", email); // Pass email as username for display
                startActivity(intent);
                finish();
                return;
            }
        }

        // Set up login button click listener
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.loginEmail.getText().toString().trim();
                String password = binding.loginPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(ActivityLogin.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    // Check user credentials using the single database
                    boolean checkCredentials = database.checkEmailPassword(email, password);
                    if (checkCredentials) {
                        Toast.makeText(ActivityLogin.this, "Login Successfully!", Toast.LENGTH_SHORT).show();

                        // Save user session details
                        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putBoolean(KEY_KEEP_LOGGED_IN, binding.keepMeLoggedIn.isChecked());
                        editor.putString(KEY_USER_EMAIL, email);
                        editor.apply();

                        // Navigate to Home activity
                        Intent intent = new Intent(ActivityLogin.this, Home.class);
                        intent.putExtra("email", email); // Pass email as extra
                        intent.putExtra("username", email); // Pass email as username for display
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ActivityLogin.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Redirect to SignUp activity
        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, ActivitySignUp.class);
                startActivity(intent);
            }
        });
    }
}
