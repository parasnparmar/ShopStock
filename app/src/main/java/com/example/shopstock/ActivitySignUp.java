package com.example.shopstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shopstock.databinding.ActivitySignUpBinding;

public class ActivitySignUp extends AppCompatActivity {
    ActivitySignUpBinding binding;
    DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataBase = new DataBase(this);
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupConfirm.getText().toString();

                if(email.equals("")||password.equals("")||confirmPassword.equals("")){
                    Toast.makeText(ActivitySignUp.this,"All Feilds are Mandatory",Toast.LENGTH_SHORT).show();

                }else{
                    if(password.equals(confirmPassword)){
                        Boolean checkUserEmail = dataBase.checkEmail(email);

                        if(checkUserEmail == false){
                            Boolean insert = dataBase.insertData(email, password);
                            if(insert ==true){
                                Toast.makeText(ActivitySignUp.this,"SignUp Successful",Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(ActivitySignUp.this,"SignUp Faild",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(ActivitySignUp.this,"User Already Exists!",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ActivitySignUp.this,"Invaild Password",Toast.LENGTH_SHORT).show();
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