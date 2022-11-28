package com.example.orderez.calendar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.orderez.R;
import com.example.orderez.homepage.AddnewItems;
import com.example.orderez.homepage.HomePage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Calendar_Month extends AppCompatActivity {
    TextView monthYearText; //Year and Month Textview
    RecyclerView recyclerView;

    //Bottom Navigation bar
    BottomNavigationView bottomNavigationView;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_month);


        //Default set
        monthYearText = findViewById(R.id.monthYearText);
        ImageButton preBtn = findViewById(R.id.pre_btn);
        ImageButton nextBtn = findViewById(R.id.next_btn);
        recyclerView = findViewById(R.id.recycler_month);

//        Show present time
        CalendarUtil.selectedDate = LocalDate.now();
        setMonthView();

//        Click Events (Arrow buttons)
        preBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                CalendarUtil.selectedDate = CalendarUtil.selectedDate.minusMonths(1);
                setMonthView();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.selectedDate = CalendarUtil.selectedDate.plusMonths(1);
                setMonthView();
            }
        });

        //Bottom navigation bar


        bottomNavigationView= (BottomNavigationView) findViewById(R.id.menus_calendar_month);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.addIcon_MenuCal:
                        Intent addSchedule = new Intent(getApplicationContext(), AddnewItems.class);
                        startActivity(addSchedule);
                        break;
                    case R.id.calendarIcon_MenuCal:
                        Intent resetView = new Intent(getApplicationContext(), Calendar_Month.class);
                        startActivity(resetView);
                        break;
                    case R.id.settingIcon_MenuCal:
                        Intent setting = new Intent (getApplicationContext(), HomePage.class);
                        startActivity(setting);
                        break;
                }

                return false;
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        return date.format(formatter);
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView(){
        monthYearText.setText(monthYearFromDate(CalendarUtil.selectedDate));

        ArrayList<LocalDate> dayList = dayInMonthArray(CalendarUtil.selectedDate);
        CalendarAdapter adapter = new CalendarAdapter(dayList);


        //Layout setting
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),7);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(adapter);


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<LocalDate> dayInMonthArray(LocalDate date){
        ArrayList<LocalDate> dayList = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int lastDate = yearMonth.lengthOfMonth();
        LocalDate firstDay = CalendarUtil.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue();
        for (int i = 1; i < 42; i++){
            if (i <= dayOfWeek || i > lastDate + dayOfWeek){
                dayList.add(null);
            }else {
                dayList.add(LocalDate.of(CalendarUtil.selectedDate.getYear(), CalendarUtil.selectedDate.getMonth(), i - dayOfWeek));
            }
        }
        return dayList;
    }


}