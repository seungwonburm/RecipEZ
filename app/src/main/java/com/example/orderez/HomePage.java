package com.example.orderez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {

    Homepage_Setting homepageSetting;
    Homepage_ItemList homepageItemList;
    Homepage_Calendar_Month homepageCalendarMonth;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        homepageSetting = new Homepage_Setting();
        homepageItemList = new Homepage_ItemList();
        homepageCalendarMonth = new Homepage_Calendar_Month();
        bottomNavigationView = findViewById(R.id.menus_setting);



        Intent userId = getIntent();
        String idinString = userId.getStringExtra("userId");

        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_container,new Homepage_Calendar_Month()).commit();

        Bundle bundle = new Bundle();
        bundle.putString("userId",idinString);
        homepageItemList.setArguments(bundle);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemListIcon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_container, homepageItemList).commit();
                        break;
                    case R.id.settingIcon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_container, homepageSetting).commit();
                        break;
                    case R.id.calendarIcon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_container, homepageCalendarMonth).commit();
                        break;
                }
                return false;
            }
        });

    }
}