package com.example.orderez.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.orderez.R;
import com.example.orderez.calendar.Calendar_Month;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {

    Homepage_Setting homepageSetting;
    Homepage_ItemList homepageItemList;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        homepageSetting = new Homepage_Setting();
        homepageItemList = new Homepage_ItemList();

        bottomNavigationView = findViewById(R.id.menus_setting);



        Intent userId = getIntent();
        String idinString = userId.getStringExtra("userId");
        Bundle bundle = new Bundle();
        bundle.putString("userId",idinString);
        homepageItemList.setArguments(bundle);


        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_container, homepageSetting).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemListIcon_02:
                        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_container, homepageItemList).commit();
                        break;
                    case R.id.settingIcon_02:
                        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_container, homepageSetting).commit();
                        break;
                    case R.id.calendarIcon_02:
                        Intent intent = new Intent(getApplicationContext(), Calendar_Month.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

    }
}