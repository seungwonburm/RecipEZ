package com.example.orderez.homepage.settingCategories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.orderez.R;

public class Setting_User_Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_user_profile);

        Intent receive_intent = getIntent();
        String id = receive_intent.getStringExtra("id");



    }
}