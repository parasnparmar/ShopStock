package com.example.shopstock

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shopstock.databinding.ActivityOtpVerificationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

class OtpVerification : AppCompatActivity() {

    private lateinit var activityOtpVerificationBinding: ActivityOtpVerificationBinding
    private lateinit var verificationId: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var dataBase: DataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityOtpVerificationBinding = ActivityOtpVerificationBinding.inflate(layoutInflater)
        setContentView(activityOtpVerificationBinding.root)

        dataBase = DataBase(this)
        verificationId = intent.getStringExtra("otp") ?: ""
        email = intent.getStringExtra("email") ?: ""
        password = intent.getStringExtra("password") ?: ""

        activityOtpVerificationBinding.btnSubmitOtp.setOnClickListener {
            val enteredOtp = getEnteredOtp()
            if (enteredOtp.length == 6) {
                verifyOtp(enteredOtp)
            } else {
                Toast.makeText(this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show()
            }
        }

        setupOtpInputFields()
    }

    private fun getEnteredOtp(): String {
        return with(activityOtpVerificationBinding) {
            edtNumber1.text.toString() + edtNumber2.text.toString() +
                    edtNumber3.text.toString() + edtNumber4.text.toString() +
                    edtNumber5.text.toString() + edtNumber6.text.toString()
        }
    }

    private fun verifyOtp(enteredOtp: String) {
        if (verificationId.isNotEmpty()) {
            activityOtpVerificationBinding.progressBarVerfiyOtp.visibility = View.VISIBLE
            activityOtpVerificationBinding.btnSubmitOtp.visibility = View.INVISIBLE

            val credential = PhoneAuthProvider.getCredential(verificationId, enteredOtp)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    activityOtpVerificationBinding.progressBarVerfiyOtp.visibility = View.GONE
                    activityOtpVerificationBinding.btnSubmitOtp.visibility = View.VISIBLE
                    if (task.isSuccessful) {
                        // Insert the user data into the database upon successful OTP verification
                        val insert = dataBase.insertData(email, password)
                        if (insert) {
                            Toast.makeText(this, "SignUp Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, ActivityLogin::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "SignUp Failed", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Invalid OTP", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun setupOtpInputFields() {
        val fields = listOf(
            activityOtpVerificationBinding.edtNumber1,
            activityOtpVerificationBinding.edtNumber2,
            activityOtpVerificationBinding.edtNumber3,
            activityOtpVerificationBinding.edtNumber4,
            activityOtpVerificationBinding.edtNumber5,
            activityOtpVerificationBinding.edtNumber6
        )

        for (i in fields.indices) {
            fields[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.isNotEmpty() == true && i < fields.size - 1) {
                        fields[i + 1].requestFocus()
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }
}
