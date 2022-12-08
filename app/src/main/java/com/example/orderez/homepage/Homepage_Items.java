package com.example.orderez.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.orderez.BackKeyHandler;
import com.example.orderez.DatabaseManager;
import com.example.orderez.MainActivity;
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
     Button generate;
    public static String id;
     DatabaseManager theDb;
     Cursor cursor;
     String var0, var1, var2, var3, var4;
    public static String temp = "", recipe="";

    private BackKeyHandler backKeyHandler = new BackKeyHandler(this);

    //Bottom Navigation bar
    BottomNavigationView bottomNavigationView_Month;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_items);

        itemList = findViewById(R.id.itemList);
        layoutManager = new LinearLayoutManager(this);
        itemList.setLayoutManager(layoutManager);

        itemList_adapter = new ItemList_Adapter(this);

        theDb= new DatabaseManager(this);


        generate = (Button) findViewById(R.id.generate);

        Intent intent = getIntent();
        id = intent.getStringExtra("userId");

        cursor = theDb.searchItemId(id);

        if (cursor.getCount()<=0){
            generate.setEnabled(false);
            Toast.makeText(getApplicationContext(), "No Data Yet!!", Toast.LENGTH_LONG).show();
        }
        else if (cursor.moveToFirst() && cursor != null) {
            generate.setEnabled(true);
            temp = "";
            recipe = "";
            do{
                var0 = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                var1 = cursor.getString(cursor.getColumnIndexOrThrow("amount"));
                var2 = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
                var3 = cursor.getString(cursor.getColumnIndexOrThrow("expire_date"));
                var4 = cursor.getString(cursor.getColumnIndexOrThrow("memo"));
                temp += var0 + ", ";

                itemList_adapter.addItem(new ItemList_Manager(var0, var2,  var1, var4));
            } while (cursor.moveToNext());
            recipe = temp.substring(0,temp.length()-2);
            itemList.setAdapter(itemList_adapter);

        } else if (cursor == null){
            generate.setEnabled(false);
            Toast.makeText(getApplicationContext(), "NO DATA!!", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        itemList.setAdapter(itemList_adapter);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent generateIntent = new Intent(getApplicationContext(), MainActivity.class);
//                generateIntent.putExtra("recipe", recipe);
                startActivity(generateIntent);

            }
        });

        //add Button
        addBtn = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoAddPage = new Intent(getApplicationContext(), AddnewItems.class);
                gotoAddPage.putExtra("userId", id);
                startActivity(gotoAddPage);
                finish();
            }
        });
        //Menu Bar
        bottomNavigationView_Month= (BottomNavigationView) findViewById(R.id.item_menubar);
        bottomNavigationView_Month.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.settingIcon_02:
                        Intent intent2 = new Intent(getApplicationContext(), Homepage_SettingCategories.class);
                        intent2.putExtra("userId", id);
                        startActivity(intent2);
                        finish();
                        break;
                    case R.id.itemListIcon_02:
                        Intent intent1 = new Intent(getApplicationContext(), Homepage_Items.class);
                        intent1.putExtra("userId",id);
                        startActivity(intent1);
                        finish();
                        break;
                }
                return false;
            }
        });

    }

    //Overrides BackKeyHandler's onBackPressed method
    //Finish the app if user clicks the back button twice in 2 seconds.
    @Override
    public void onBackPressed() {
        backKeyHandler.onBackPressed();
    }



}