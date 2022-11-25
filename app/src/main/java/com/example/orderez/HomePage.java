package com.example.orderez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {

    Homepage_Setting homepageHomeFragment;
    Homepage_ItemListFragment homepageItemListFragment;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        homepageHomeFragment = new Homepage_Setting();
        homepageItemListFragment = new Homepage_ItemListFragment();
        bottomNavigationView = findViewById(R.id.menus_setting);
//
        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_container,new Homepage_ItemListFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemListIcon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_container,homepageItemListFragment).commit();
                        break;
                    case R.id.settingIcon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_container,homepageHomeFragment).commit();
                        break;
                }
                return false;
            }
        });

    }
}