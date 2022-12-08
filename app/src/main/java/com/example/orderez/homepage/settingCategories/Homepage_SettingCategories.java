package com.example.orderez.homepage.settingCategories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.orderez.R;
import com.example.orderez.homepage.Homepage_Items;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Homepage_SettingCategories extends AppCompatActivity {

    ImageButton account_settings_icon,change_password_icon;
    TextView account_settings_text, change_password_text;
    LinearLayout account_settings_linear, change_password_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_setting_categories);

        //Bottom Navigation bar
        BottomNavigationView bottomNavigationView_Setting;

        Intent intent = getIntent();
        String str = intent.getStringExtra("userId");



        account_settings_linear = (LinearLayout) findViewById(R.id.account_settings_linear);
        change_password_linear = (LinearLayout) findViewById(R.id.change_password_linear);

        account_settings_icon = findViewById(R.id.account_settings_icon);
        account_settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoAccountSettings = new Intent(getApplicationContext(), AccountSettings.class);
                gotoAccountSettings.putExtra("userId", str);
                startActivity(gotoAccountSettings);

            }
        });

        account_settings_text = findViewById(R.id.account_settings_text);
        account_settings_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoAccountSettings = new Intent(getApplicationContext(), AccountSettings.class);
                gotoAccountSettings.putExtra("userId", str);
                startActivity(gotoAccountSettings);
            }
        });

        account_settings_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoAccountSettings = new Intent(getApplicationContext(), AccountSettings.class);
                gotoAccountSettings.putExtra("userId", str);
                startActivity(gotoAccountSettings);
            }
        });

        change_password_icon = findViewById(R.id.change_password_icon);
        change_password_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoChangePassword = new Intent(getApplicationContext(), ChangePassword.class);
                gotoChangePassword.putExtra("userId", str);
                startActivity(gotoChangePassword);
            }
        });

        change_password_text = findViewById(R.id.change_password_text);
        change_password_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoChangePassword = new Intent(getApplicationContext(), ChangePassword.class);
                gotoChangePassword.putExtra("userId", str);
                startActivity(gotoChangePassword);
            }
        });

        change_password_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoChangePassword = new Intent(getApplicationContext(), ChangePassword.class);
                gotoChangePassword.putExtra("userId", str);
                startActivity(gotoChangePassword);
            }
        });

        bottomNavigationView_Setting= (BottomNavigationView) findViewById(R.id.item_menubar);
        bottomNavigationView_Setting.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemListIcon_02:
                        Intent intent = new Intent(getApplicationContext(), Homepage_Items.class);
                        intent.putExtra("userId", str);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.settingIcon_02:
                        Intent intent2 = new Intent(getApplicationContext(), Homepage_SettingCategories.class);
                        intent2.putExtra("userId", str);
                        startActivity(intent2);
                        finish();
                        break;
                }

                return false;
            }
        });
    }
}