package com.example.orderez.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.orderez.R;
import com.example.orderez.homepage.settingCategories.Homepage_SettingCategories;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {

    Homepage_ItemList homepageItemList;
    Homepage_Calendar_Month homepageMonthCalendar;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        homepageItemList = new Homepage_ItemList();
        homepageMonthCalendar = new Homepage_Calendar_Month();

        bottomNavigationView = findViewById(R.id.menus_setting);



        Intent userId = getIntent();
        String idinString = userId.getStringExtra("userId");
        Bundle bundle = new Bundle();
        bundle.putString("userId",idinString);
        homepageItemList.setArguments(bundle);


        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_container, homepageItemList).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemListIcon_02:
                        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_container, homepageItemList).commit();
                        break;
                    case R.id.settingIcon_02:
                        Intent intent = new Intent(getApplicationContext(), Homepage_SettingCategories.class);
                        startActivity(intent);
                        break;
                    case R.id.calendarIcon_02:
                        Intent calendarIntent = new Intent(getApplicationContext(),Homepage_Calendar_Month.class);
                        startActivity(calendarIntent);
                        break;
                }
                return false;
            }
        });

    }
}