package com.example.orderez.homepage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.orderez.R;
import com.example.orderez.homepage.settingCategories.Homepage_SettingCategories;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Homepage_Calendar_Week extends AppCompatActivity {

    private TextView monthYearText; //Year and Month Textview
    private LocalDate selectedDate;
    private Button changeView;
    private RecyclerView itenList_weekly;
    private ItemList_Adapter itemList_adapter;
    private FloatingActionButton addBtn;
    private BottomNavigationView bottomNavigationView;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_week);

        //Default set
        monthYearText = findViewById(R.id.monthYearText);
        ImageButton preBtn = findViewById(R.id.pre_btn);
        ImageButton nextBtn = findViewById(R.id.next_btn);
        itenList_weekly = findViewById(R.id.itemList_day_week);
        changeView = (Button) findViewById(R.id.changeView_monthtoWeek);
        //Show present time
        selectedDate = LocalDate.now();


        //Set screen

        //Click Events (Arrow buttons)
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.minusMonths(1); //When it's clicked it goes to previous month
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.plusMonths(1);
            }
        });


        changeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoMonthlyView = new Intent(getApplicationContext(), Homepage_Calendar_Month.class);
                startActivity(gotoMonthlyView);
            }
        });
        //add Button
        addBtn = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoAddPage = new Intent(getApplicationContext(), AddnewItems.class);
                startActivity(gotoAddPage);
            }
        });

        //Bottom navigation bar
        bottomNavigationView= (BottomNavigationView) findViewById(R.id.menus_calendar_month);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

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

        itenList_weekly = findViewById(R.id.itemList_day);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        itenList_weekly.setLayoutManager(layoutManager);
        itemList_adapter = new ItemList_Adapter(this);
        itemList_adapter.addItem(new ItemList_Manager("This", "is", "just", "Test"));
        itenList_weekly.setAdapter(itemList_adapter);

    }//onCreate

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFormat(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        return date.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView(){
        monthYearText.setText(monthYearFormat(selectedDate));
    }
}