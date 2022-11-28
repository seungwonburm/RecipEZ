package com.example.orderez.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.example.orderez.DatabaseManager;
import com.example.orderez.R;
import com.example.orderez.intro.LogInPage;
import com.example.orderez.intro.RegisterPage;

public class WelcomePage extends AppCompatActivity {
    DatabaseManager theDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theDb = new DatabaseManager(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        Button loginbtn = (Button) findViewById(R.id.toLoginPagebtnMain);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotologinpage = new Intent(getApplicationContext(), LogInPage.class);
                startActivity(gotologinpage);
            }
        });

        Button registerbtn = (Button) findViewById(R.id.toRegPagebtnMain);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregisterPage = new Intent(getApplicationContext(), RegisterPage.class);
                startActivity(gotoregisterPage);
            }
        });
    }









}