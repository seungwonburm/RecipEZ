package com.example.orderez.homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.orderez.DatabaseManager;
import com.example.orderez.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddnewItems extends AppCompatActivity{
    Button submit_Btn;
    EditText title, amount,memo, oneTimeConsume, rateTxT;
    Spinner totalAmountConsumeUnit, oneTimeConsumeUnit, rateUnit;
    String id;
    DatabaseManager theDb;



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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_items);




        theDb = new DatabaseManager(this);
        title = (EditText) findViewById(R.id.addItem_title);
        amount = (EditText) findViewById(R.id.addItem_enterTotal);
        memo = (EditText) findViewById(R.id.addItem_memo);
        oneTimeConsume = (EditText) findViewById(R.id.addItem_oneTimeConsume);
        rateTxT = (EditText) findViewById(R.id.addItem_rateAmount);

//        Intent intent = getIntent();
//        id = intent.getStringExtra("userId");
        id="1";

//        Homepage_ItemList fragment = new Homepage_ItemList();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.frameLayout, fragment);
//        fragmentTransaction.commit();


        EditText et_from_Date = (EditText) findViewById(R.id.from_Date);
        et_from_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddnewItems.this, fromDataPicker, pickDates.get(Calendar.YEAR), pickDates.get(Calendar.MONTH), pickDates.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        totalAmountConsumeUnit = (Spinner) findViewById(R.id.addItem_unitList);
        ArrayAdapter totalunitAdapter = ArrayAdapter.createFromResource(this,R.array.UnitTypes,android.R.layout.simple_spinner_item);
        totalunitAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        totalAmountConsumeUnit.setAdapter(totalunitAdapter);
        String unitTotal = totalAmountConsumeUnit.getSelectedItem().toString();

        oneTimeConsumeUnit = (Spinner) findViewById(R.id.addItem_unitPer01);
        ArrayAdapter unitAdapter = ArrayAdapter.createFromResource(this,R.array.UnitTypes,android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        oneTimeConsumeUnit.setAdapter(unitAdapter);
        String unitOne = oneTimeConsumeUnit.getSelectedItem().toString();

        rateUnit = (Spinner) findViewById(R.id.addItem_unitRate);
        ArrayAdapter perAdapter = ArrayAdapter.createFromResource(this,R.array.dayOrweek,android.R.layout.simple_spinner_item);
        perAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        rateUnit.setAdapter(perAdapter);
        String selectedRate = rateUnit.getSelectedItem().toString();


        submit_Btn = (Button) findViewById(R.id.submit_schedule);
        submit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_title = title.getText().toString();
                String str_amountTotal = amount.getText().toString();
                String str_amountOne = oneTimeConsume.getText().toString();
                String unitOne = oneTimeConsumeUnit.getSelectedItem().toString();
                String unitTotal = totalAmountConsumeUnit.getSelectedItem().toString();

                String str_rateNum = rateTxT.getText().toString();
                String selectedRate = rateUnit.getSelectedItem().toString();

                String mem= memo.toString();
                boolean goodOrNot = errorChecker(unitTotal, unitOne,str_title,str_amountTotal,str_amountOne, str_rateNum,selectedRate);
                if (goodOrNot){
                    calculateRate(str_title, str_amountTotal,str_amountOne,unitOne, unitTotal, str_rateNum, selectedRate, pickDates, mem);
                    Intent goBacktoHomepage = new Intent(getApplicationContext(), HomePage.class);
                    startActivity(goBacktoHomepage);
//                    Toast.makeText(getApplicationContext(),"Data Added",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }//onCreate

    private void updateLabel(){
        String format_default = "yyyy/MM/dd";
        SimpleDateFormat formatter = new SimpleDateFormat(format_default, Locale.US);

        EditText et_from_date = (EditText) findViewById(R.id.from_Date);
        et_from_date.setText(formatter.format(pickDates.getTime()));
    }

    private boolean errorChecker(String totalAmountUnit, String oneTimeAmountUnit, String title,String amountTotal, String amountOne, String rateNum, String rate){
        boolean errorCheck = true;
        float totalAmount = 0;
        float oneTimeAmount = 0;




        if (title.equals("")  ||amountTotal.equals("") || amountOne.equals("") || rateNum.equals("") || totalAmountUnit.equals("Select One")|| oneTimeAmountUnit.equals("Select One")|| rate.equals("Select One")){
            Toast.makeText(getApplicationContext(),"Please fill in the blank and select units and cycle",Toast.LENGTH_SHORT).show();
            errorCheck =false;
        }

        if (!(amountTotal.equals(""))&& !(amountOne.equals(""))){
            totalAmount = Float.parseFloat(amountTotal);
            oneTimeAmount = Float.parseFloat(amountOne);
        }


        if (totalAmountUnit.equals("mL")){
            errorCheck = false;
            Toast.makeText(getApplicationContext(),"Please change the unit ",Toast.LENGTH_SHORT).show();
        }else if (totalAmountUnit.equals("g")){
            errorCheck = false;
            Toast.makeText(getApplicationContext(),"Please change the unit ",Toast.LENGTH_SHORT).show();
        }else if ((totalAmountUnit.equals("lbs"))){
            errorCheck = false;
            Toast.makeText(getApplicationContext(),"Please change the unit ",Toast.LENGTH_SHORT).show();
        }else if (totalAmountUnit.equals("kg") && !(oneTimeAmountUnit.equals("g") || oneTimeAmountUnit.equals("kg"))){
            errorCheck = false;
            Toast.makeText(getApplicationContext(),"Please change the unit ",Toast.LENGTH_SHORT).show();
        }else if (totalAmountUnit.equals("oz") && !(oneTimeAmountUnit.equals("oz") || oneTimeAmountUnit.equals("lbs"))){
            errorCheck = false;
            Toast.makeText(getApplicationContext(),"Please change the unit ",Toast.LENGTH_SHORT).show();
        }else if (totalAmountUnit.equals("L") && !(oneTimeAmountUnit.equals("L") || oneTimeAmountUnit.equals("mL"))){
            errorCheck = false;
            Toast.makeText(getApplicationContext(),"Please change the unit ",Toast.LENGTH_SHORT).show();
        }else if (totalAmountUnit.equals("Count") && !(oneTimeAmountUnit.equals("Count"))){
            errorCheck = false;
            Toast.makeText(getApplicationContext(),"Please change the unit ",Toast.LENGTH_SHORT).show();
        }



        if (totalAmountUnit.equals(oneTimeAmountUnit)){
            if (totalAmount < oneTimeAmount){
                errorCheck = false;
                Toast.makeText(getApplicationContext(),"Total amount is less than once!",Toast.LENGTH_SHORT).show();
            }
        }else {
            if (totalAmountUnit.equals("L") && oneTimeAmountUnit.equals("mL")){
                float temp = oneTimeAmount * 1000;
                if (temp < oneTimeAmount){
                    errorCheck = false;
                    Toast.makeText(getApplicationContext(),"Total amount is less than once!",Toast.LENGTH_SHORT).show();
                }
            }else if (totalAmountUnit.equals("kg") && oneTimeAmountUnit.equals("g")){
                float temp = oneTimeAmount * 1000;
                if (temp < oneTimeAmount){
                    errorCheck = false;
                    Toast.makeText(getApplicationContext(),"Total amount is less than once!",Toast.LENGTH_SHORT).show();
                }
            }else if (totalAmountUnit.equals("oz") && oneTimeAmountUnit.equals("lbs")){
                float temp = oneTimeAmount * 16;
                if (temp < oneTimeAmount){
                    errorCheck = false;
                    Toast.makeText(getApplicationContext(),"Total amount is less than once!",Toast.LENGTH_SHORT).show();
                }
            }
        }
        int rateNumVal = 0;
        if (!(rateNum.equals(""))){
            rateNumVal = Integer.parseInt(rateNum);
            if (rateNumVal > 6){
                errorCheck = false;
                Toast.makeText(getApplicationContext(),"Please set your number less than 7 if we want to set week cycle",Toast.LENGTH_SHORT).show();
            }
        }


        if (totalAmountUnit.equals(oneTimeAmountUnit) && amountTotal.equals(amountOne)){
            if (!(rateNum.equals(""))){
                rateNumVal = Integer.parseInt(rateNum);
                if (rateNumVal > 1){
                    errorCheck = false;
                    Toast.makeText(getApplicationContext(),"Please set your number of cycle again.",Toast.LENGTH_SHORT).show();

                }
            }
        }


        return errorCheck;
    }

    //str_title, str_amountTotal,str_amountOne,unitOne, unitTotal, str_rateNum, selectedRate, pickDates, mem
    private void calculateRate(String title , String amountOne, String amountTotal, String unitOne, String unitTotal,  String rateNum, String selectedRate, Calendar startingDate, String memo ) {
        float totalAmount = Float.parseFloat(amountTotal); //Total amount 저장
        float oneTimeAmount = Float.parseFloat(amountOne); //한번 먹을양 저장
        int frequency = 0;
        int rateVal = Integer.parseInt(rateNum); //먹는 횟수 저장
        String endDate, frequencyStr;
        String startDate  = startingDate.getTime().toString();
        Boolean added = false;

        //Total amount unit, one time unit 저장
        if (selectedRate.equals("day(s)")){
            if (unitTotal.equals("L") && unitOne.equals("mL")){
                float temp = totalAmount * 1000;
                frequency = Math.round(temp/oneTimeAmount); //섭취 횟수 저장
                frequencyStr = String.valueOf(frequency);
                startingDate.add(Calendar.DATE,frequency/rateVal);
                endDate = startingDate.getTime().toString(); // 끝나는 날짜 저장
                //insertItem(String item_name, String start_date, String end_date, String days, String frequency, String unit_one, String unit_entire, String amount_one, String amount_entire, String memo, String user_id) {
                added = theDb.insertItem(title, startDate, endDate, rateNum, frequencyStr, unitOne, unitTotal, amountOne, amountTotal, memo, id);

            }else if (unitTotal.equals("kg") && unitOne.equals("g")){
                float temp = totalAmount * 1000;
                frequency = Math.round(temp/oneTimeAmount);
                frequencyStr = String.valueOf(frequency);

                startingDate.add(Calendar.DATE,frequency/rateVal);
                endDate = startingDate.getTime().toString(); // 끝나는 날짜 저장
                added = theDb.insertItem(title, startDate, endDate, rateNum, frequencyStr, unitOne, unitTotal, amountOne, amountTotal, memo, id);
            }else if (unitTotal.equals("oz") && unitOne.equals("lbs")){
                float temp = totalAmount * 16;
                frequency = Math.round(temp/oneTimeAmount);
                frequencyStr = String.valueOf(frequency);
                startingDate.add(Calendar.DATE,frequency/rateVal);
                endDate = startingDate.getTime().toString(); // 끝나는 날짜 저장
                added = theDb.insertItem(title, startDate, endDate, rateNum, frequencyStr, unitOne, unitTotal, amountOne, amountTotal, memo, id);
            }else if (unitTotal.equals("Count") && unitOne.equals("Count")){
                frequency = Math.round(totalAmount/oneTimeAmount);
                frequencyStr = String.valueOf(frequency);

                startingDate.add(Calendar.DATE,frequency/rateVal);
                endDate = startingDate.getTime().toString(); // 끝나는 날짜 저장
                added = theDb.insertItem(title, startDate, endDate, rateNum, frequencyStr, unitOne, unitTotal, amountOne, amountTotal, memo, id);
            }

            if (added == true){
                Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_LONG).show();
                Intent LogInPage = new Intent(getApplicationContext(), com.example.orderez.intro.LogInPage.class);
                startActivity(LogInPage);
            }
            else
                Toast.makeText(getApplicationContext(), "Data Not Added", Toast.LENGTH_LONG).show();
        }else {
            //매주 무슨 요일에 몇주동안 먹는지 계산해야함 (이건 오늘 하겠음)
        }


    }


//    @Override
//    public void sendMessage(String s) {
//        id=s;
//    }
}
//Class