package com.example.orderez.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.orderez.R;
import com.example.orderez.homepage.settingCategories.Homepage_SettingCategories;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Homepage_Items extends AppCompatActivity {

    RecyclerView itemList;
    ItemList_Adapter itemList_adapter;
    FloatingActionButton addBtn;

    //Bottom Navigation bar
    BottomNavigationView bottomNavigationView_Month;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_items);

        itemList = findViewById(R.id.itemList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        itemList.setLayoutManager(layoutManager);

        itemList_adapter = new ItemList_Adapter(getApplicationContext());
        itemList.setAdapter(itemList_adapter);


        //다른거 안건들고 이 밑에꺼만 요리하시면 되세용
        //추가하는 방식은 밑에 코드와 동일함
        itemList_adapter.addItem(new ItemList_Manager("아이템 이름", "남은 횟수", "시작 날짜", "소진 날짜"));




        //add Button
        addBtn = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoAddPage = new Intent(getApplicationContext(), AddnewItems.class);
                startActivity(gotoAddPage);
            }
        });
        //Menu Bar
        bottomNavigationView_Month= (BottomNavigationView) findViewById(R.id.item_menubar);
        bottomNavigationView_Month.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemListIcon_02:
                        Intent intent = new Intent(getApplicationContext(), Homepage_Items.class);
                        startActivity(intent);
                        break;
                    case R.id.settingIcon_02:
                        Intent intent2 = new Intent(getApplicationContext(), Homepage_SettingCategories.class);
                        startActivity(intent2);
                        break;
                    case R.id.calendarIcon_02:
                        Intent refresh = new Intent(getApplicationContext(),Homepage_Calendar_Month.class);
                        startActivity(refresh);
                        break;
                }
                return false;
            }
        });

    }



}