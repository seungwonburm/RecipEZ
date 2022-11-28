package com.example.orderez;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.orderez.homepage.CalendarAdapter;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Calendar extends AppCompatActivity {
    TextView monthYearText; //Year and Month Textview
    LocalDate selectedDate;
    RecyclerView recyclerView;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        //Default set
        monthYearText = findViewById(R.id.monthYearText);
        ImageButton preBtn = findViewById(R.id.pre_btn);
        ImageButton nextBtn = findViewById(R.id.next_btn);
        recyclerView = findViewById(R.id.recycler_month);

//        Show present time
        selectedDate = LocalDate.now();
        setMonthView();

//        Click Events (Arrow buttons)
        preBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                selectedDate = selectedDate.minusMonths(1);
                setMonthView();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.plusMonths(1);
                setMonthView();
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
        monthYearText.setText(monthYearFromDate(selectedDate));

        ArrayList<String> dayList = dayInMonthArray(selectedDate);
        CalendarAdapter adapter = new CalendarAdapter(dayList);


        //Layout setting
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),7);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(adapter);


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> dayInMonthArray(LocalDate date){
        ArrayList<String> dayList = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int lastDate = yearMonth.lengthOfMonth();
        LocalDate firstDay = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue();
        for (int i = 1; i < 42; i++){
            if (i <= dayOfWeek || i > lastDate + dayOfWeek){
                dayList.add("");
            }else {
                dayList.add( String.valueOf(i - dayOfWeek) );
            }
        }
        return dayList;
    }
}