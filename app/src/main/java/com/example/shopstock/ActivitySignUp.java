package com.example.shopstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shopstock.databinding.ActivitySignUpBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ActivitySignUp extends AppCompatActivity {
    ActivitySignUpBinding binding;
    DataBase dataBase;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_LOGGED_IN = "loggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataBase = new DataBase(this);

        binding.btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupConfirm.getText().toString();
                String mobileNumber = binding.signupMobile.getText().toString();

                if(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || mobileNumber.isEmpty()){
                    Toast.makeText(ActivitySignUp.this, "All Fields are Mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    if(mobileNumber.length() != 10){
                        Toast.makeText(ActivitySignUp.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(password.equals(confirmPassword)){
                        Boolean checkUserEmail = dataBase.checkEmail(email);

                        if(!checkUserEmail){
                            binding.progressBarSendingOtp.setVisibility(View.VISIBLE);
                            binding.btnSendOtp.setVisibility(View.INVISIBLE);

                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    "+91" + mobileNumber,
                                    60,
                                    TimeUnit.SECONDS,
                                    ActivitySignUp.this,
                                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                        @Override
                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                            binding.progressBarSendingOtp.setVisibility(View.GONE);
                                            binding.btnSendOtp.setVisibility(View.VISIBLE);

                                            // Insert the user data into the database upon successful OTP verification
                                            Boolean insert = dataBase.insertData(email, password);
                                            if(insert){
                                                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                                                SharedPreferences.Editor editor = prefs.edit();
                                                editor.putBoolean(KEY_LOGGED_IN, true);
                                                editor.apply();
                                                Toast.makeText(ActivitySignUp.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(ActivitySignUp.this, "SignUp Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onVerificationFailed(@NonNull FirebaseException e) {
                                            binding.progressBarSendingOtp.setVisibility(View.GONE);
                                            binding.btnSendOtp.setVisibility(View.VISIBLE);
                                            Toast.makeText(ActivitySignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                            binding.progressBarSendingOtp.setVisibility(View.GONE);
                                            binding.btnSendOtp.setVisibility(View.VISIBLE);
                                            Intent intent = new Intent(ActivitySignUp.this, OtpVerification.class);
                                            intent.putExtra("mobile", mobileNumber);
                                            intent.putExtra("otp", s);
                                            intent.putExtra("email", email);  // Pass email to OtpVerification activity
                                            intent.putExtra("password", password);  // Pass password to OtpVerification activity
                                            startActivity(intent);
                                        }
                                    }
                            );
                        } else {
                            Toast.makeText(ActivitySignUp.this, "User Already Exists!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ActivitySignUp.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitySignUp.this, ActivityLogin.class);
                startActivity(intent);
            }
        });
    }
}
