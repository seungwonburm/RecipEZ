package com.example.orderez.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.orderez.R;
import com.example.orderez.calendar.Calendar_Month;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

public class AddnewItems extends AppCompatActivity {

    Button submit_Btn;

    Calendar pickDates = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener fromDataPicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            pickDates.set(Calendar.YEAR,year);
            pickDates.set(Calendar.MONTH,month);
            pickDates.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updateLabel();
        }
    };

    Calendar toDates = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener toDataPicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int to_year, int to_month, int to_dayOfMonth) {
            toDates.set(toDates.YEAR,to_year);
            toDates.set(toDates.MONTH,to_month);
            toDates.set(toDates.DAY_OF_MONTH,to_dayOfMonth);
            updateLabel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_items);


        //Data
        // 마지막에 각 DataPicker.get 해서 data 보내기
        // 테이틀 보내기
        // 메모 보내기
        //
        EditText et_from_Date = (EditText) findViewById(R.id.from_Date);
        et_from_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddnewItems.this, fromDataPicker, pickDates.get(Calendar.YEAR), pickDates.get(Calendar.MONTH), pickDates.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        final EditText et_from_time = (EditText) findViewById(R.id.from_Time);
        et_from_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddnewItems.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            state = "PM";
                        }
                        // EditText에 출력할 형식 지정
                        et_from_time.setText(state + " " + selectedHour + ": " + selectedMinute);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });




        EditText et_to_Date = (EditText) findViewById(R.id.to_Date);
        et_to_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddnewItems.this, toDataPicker, toDates.get(Calendar.YEAR), toDates.get(Calendar.MONTH), toDates.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final EditText et_to_time = (EditText) findViewById(R.id.to_Time);
        et_to_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddnewItems.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            state = "PM";
                        }
                        // EditText에 출력할 형식 지정
                        et_to_time.setText(state + " " + selectedHour + ": " + selectedMinute);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        submit_Btn = (Button) findViewById(R.id.submit_schedule);
        submit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //데이터 옮기기 (연도, 달, 월 ,시간), memo, title ec). toDate.get(Calendar.YEAR)
                Intent goBacktoCalendar = new Intent(getApplicationContext(), Calendar_Month.class);
                startActivity(goBacktoCalendar);
            }
        });
    }

    private void updateLabel(){
        String format_default = "yyyy/MM/dd";
        SimpleDateFormat formatter = new SimpleDateFormat(format_default, Locale.US);

        EditText et_from_date = (EditText) findViewById(R.id.from_Date);
        et_from_date.setText(formatter.format(pickDates.getTime()));

        EditText ed_to_date = (EditText) findViewById(R.id.to_Date);
        ed_to_date.setText(formatter.format(toDates.getTime()));


    }
}