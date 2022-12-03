package com.example.orderez.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.orderez.DatabaseManager;
import com.example.orderez.R;
import com.example.orderez.homepage.settingCategories.Homepage_SettingCategories;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class Homepage_Items extends AppCompatActivity {

    RecyclerView itemList;
    ItemList_Adapter itemList_adapter;
    FloatingActionButton addBtn;
    String id;
    DatabaseManager theDb;
    Cursor cursor;
    String var0, var1, var2, var3;

    //Bottom Navigation bar
    BottomNavigationView bottomNavigationView_Month;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_items);

        itemList = findViewById(R.id.itemList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        itemList.setLayoutManager(layoutManager);

        itemList_adapter = new ItemList_Adapter(getApplicationContext());
        itemList.setAdapter(itemList_adapter);

        theDb= new DatabaseManager(this);


        Intent intent = getIntent();
        id = intent.getStringExtra("userId");


        //다른거 안건들고 이 밑에꺼만 요리하시면 되세용
        //추가하는 방식은 밑에 코드와 동일함
        cursor = theDb.searchItemId(id);

        if (cursor.getCount()<=0){

            Toast.makeText(getApplicationContext(), "No Data Yet!!", Toast.LENGTH_LONG).show();
        }
        else if (cursor.moveToFirst() && cursor != null) {

            do{
                var0 = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                var1 = cursor.getString(cursor.getColumnIndexOrThrow("frequency"));
                var2 = cursor.getString(cursor.getColumnIndexOrThrow("start_date"));
                var3 = cursor.getString(cursor.getColumnIndexOrThrow("end_date"));

                itemList_adapter.addItem(new ItemList_Manager(var0, var1, var2, var3));
            } while (cursor.moveToNext());

            itemList.setAdapter(itemList_adapter);

        } else if (cursor == null){
            Toast.makeText(getApplicationContext(), "NO DATA!!", Toast.LENGTH_LONG).show();
        }
        cursor.close();

        itemList_adapter.addItem(new ItemList_Manager("1", "2", "3", "4"));
        itemList.setAdapter(itemList_adapter);


        //add Button
        addBtn = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoAddPage = new Intent(getApplicationContext(), AddnewItems.class);
                gotoAddPage.putExtra("userId", id);
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
//                    case R.id.itemListIcon_02:
//                        Intent intent = new Intent(getApplicationContext(), Homepage_Items.class);
//                        startActivity(intent);
//                        break;
                    case R.id.settingIcon_02:
                        Intent intent2 = new Intent(getApplicationContext(), Homepage_SettingCategories.class);
                        intent2.putExtra("userId", id);
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