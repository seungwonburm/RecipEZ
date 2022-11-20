package com.example.orderez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {

    Homepage_Setting homepageHomeFragment;
    Homepage_ItemListFragment homepageItemListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        homepageHomeFragment = new Homepage_Setting();
        homepageItemListFragment = new Homepage_ItemListFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_container,homepageHomeFragment).commit();
        NavigationBarView navigationBarMenu = findViewById(R.id.menus_setting);
        navigationBarMenu.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemListIcon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_container,homepageItemListFragment).commit();
                        return;
                    case R.id.settingIcon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_container,homepageHomeFragment).commit();
                        return;
                }
                return;
            }
        });


    }
}