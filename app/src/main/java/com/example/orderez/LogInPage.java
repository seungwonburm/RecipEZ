package com.example.orderez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogInPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

//        Button Login = (Button) findViewById(R.id.toLoginPagebtn);
//        Login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Login the user here if correct pass and email
//            }
//        });

        Button RegisterBtn = (Button) findViewById(R.id.registerbtn);
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Registerpage = new Intent(getApplicationContext(), RegisterPage.class);
                startActivity(Registerpage);
            }
        });

        Button ForgotPasswordBtn = (Button) findViewById(R.id.ForgotPasswordBtn);
        ForgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ForgotPasswordPage = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(ForgotPasswordPage);
            }
        });



    }



}