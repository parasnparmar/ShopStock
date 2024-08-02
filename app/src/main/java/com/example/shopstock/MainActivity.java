package com.example.shopstock;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_LOGGED_IN = "loggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean(KEY_LOGGED_IN, false);

        new Handler().postDelayed(() -> {
            Intent intent;
            if (isLoggedIn) {
                intent = new Intent(MainActivity.this, Home.class);
            } else {
                intent = new Intent(MainActivity.this, ActivityLogin.class);
            }
            startActivity(intent);
            finish();
        }, 1000);
    }
}
