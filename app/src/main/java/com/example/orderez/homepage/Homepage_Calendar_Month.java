package com.example.orderez.homepage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Homepage_Calendar_Month extends AppCompatActivity {
    private TextView monthYearText; //Year and Month Textview
    private RecyclerView recyclerView, itemList_Calendar_Month;
    private ItemList_Adapter itemList_adapter;
    private FloatingActionButton addBtn;
    private Button changeView;
    private String id;

    //Bottom Navigation bar
    private BottomNavigationView bottomNavigationView;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_month);

        Intent intent = getIntent();
        id = intent.getStringExtra("userId");




        //Default set
        monthYearText = findViewById(R.id.monthYearText);
        ImageButton preBtn = findViewById(R.id.pre_btn);
        ImageButton nextBtn = findViewById(R.id.next_btn);
        recyclerView = findViewById(R.id.recycler_month);
        changeView = (Button) findViewById(R.id.changeView_monthtoWeek);

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

//        changeView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent gotoweeklyView = new Intent(getApplicationContext(), Homepage_Calendar_Week.class);
//                startActivity(gotoweeklyView);
//            }
//        });


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
//                        Intent intent = new Intent(getApplicationContext(), Homepage_Items.class);
//                        intent.putExtra("userId", id);
//                        startActivity(intent);
                        finish();
                        break;
                    case R.id.settingIcon_02:
                        Intent intent2 = new Intent(getApplicationContext(),Homepage_SettingCategories.class);
                        intent2.putExtra("userId", id);
                        startActivity(intent2);
                        finish();
                        break;
//                    case R.id.calendarIcon_02:
//                        Intent refresh = new Intent(getApplicationContext(),Homepage_Calendar_Month.class);
//                        startActivity(refresh);
//                        break;
                }

                return false;
            }
        });


        //우선 아이템 리스트는 가져오긴 하는데, 날짜 별로 분류해서 데이터 출력해놓으면 될듯
        itemList_Calendar_Month = findViewById(R.id.itemList_day);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        itemList_Calendar_Month.setLayoutManager(layoutManager);
        itemList_adapter = new ItemList_Adapter(this);
        itemList_adapter.addItem(new ItemList_Manager("This", "is", "just", "Test"));
        itemList_Calendar_Month.setAdapter(itemList_adapter);



        //이 밑에서 부터 데이터 꺼내주면 됌



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