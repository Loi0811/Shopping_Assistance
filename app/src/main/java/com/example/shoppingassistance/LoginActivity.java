package com.example.shoppingassistance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private RelativeLayout loginLayout, otpLayout;
    private EditText numberPhoneEditText, otpCodeEditText;
    private Button continueButton, submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ các thành phần giao diện
        loginLayout = findViewById(R.id.Login);
        otpLayout = findViewById(R.id.OTP);

        numberPhoneEditText = findViewById(R.id.number_phone);
        otpCodeEditText = findViewById(R.id.otp_code);

        continueButton = findViewById(R.id.btn_continue);
        submitButton = findViewById(R.id.btn_submit);

        // Xử lý nút CONTINUE
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = numberPhoneEditText.getText().toString().trim();
                if (!phone.isEmpty()) {
                    loginLayout.setVisibility(View.INVISIBLE);
                    otpLayout.setVisibility(View.VISIBLE);
                } else {
                    numberPhoneEditText.setError("Please enter phone number");
                }
            }
        });

        // Xử lý nút SUBMIT
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = otpCodeEditText.getText().toString().trim();
                if (!otp.isEmpty()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    otpCodeEditText.setError("Please enter OTP code");
                }
            }
        });
    }
}