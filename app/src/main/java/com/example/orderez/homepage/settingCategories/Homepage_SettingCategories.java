package com.example.orderez.homepage.settingCategories;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.orderez.R;

public class Homepage_SettingCategories extends AppCompatActivity {

    ImageButton userProfile,accountSetting;
    TextView userProfile_tv, accountSetting_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_setting_categories);


        userProfile = findViewById(R.id.setting_userprofile_icon);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        userProfile_tv = findViewById(R.id.setting_userprofile_tv);
        userProfile_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        accountSetting = findViewById(R.id.setting_account_icon);
        accountSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        accountSetting_tv = findViewById(R.id.setting_account_tv);
        accountSetting_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}