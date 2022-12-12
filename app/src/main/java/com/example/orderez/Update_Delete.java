package com.example.orderez;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.orderez.homepage.Homepage_Items;
import com.example.orderez.homepage.ItemList_Manager;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Update_Delete extends AppCompatActivity {

    EditText itemName, itemAmount, itemexpirationDate, itemMemo;
    Spinner itemUnit;
    Button submit, delete;
    String id, item, var0, var1, var2, var3, var4, var5;
    DatabaseManager theDb;
    Cursor cursor;
    private BackKeyHandler backKeyHandler = new BackKeyHandler(this);

    Calendar editDates = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener datePick = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            editDates.set(Calendar.YEAR,year);
            editDates.set(Calendar.MONTH,month);
            editDates.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updateLabel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        Intent intent = getIntent();
        id = intent.getStringExtra("userId");
        item = intent.getStringExtra("ItemName");
        theDb = new DatabaseManager(this);
        cursor = theDb.searchItemNameId(id, item);

        itemName = (EditText) findViewById(R.id.editItemName);
        itemAmount = (EditText) findViewById(R.id.editItemAmount);
        itemexpirationDate = (EditText) findViewById(R.id.editExpirationDate);
        itemMemo = (EditText) findViewById(R.id.editMemo);
        itemUnit = (Spinner) findViewById(R.id.editItemUnit);

        if (cursor.getCount()<=0){
            Toast.makeText(getApplicationContext(), "No Data found!!", Toast.LENGTH_LONG).show();
        }
        else if (cursor.moveToFirst() && cursor != null) {
            var0 = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            var1 = cursor.getString(cursor.getColumnIndexOrThrow("amount"));
            var3 = cursor.getString(cursor.getColumnIndexOrThrow("expire_date"));
            var4 = cursor.getString(cursor.getColumnIndexOrThrow("memo"));
            var5 = cursor.getString(cursor.getColumnIndexOrThrow("id"));

        } else if (cursor == null){
            Toast.makeText(getApplicationContext(), "NO DATA!!", Toast.LENGTH_LONG).show();
        }
        cursor.close();



        itemName.setText(var0);
        itemAmount.setText(var1);
        itemexpirationDate.setText(var3);
        itemMemo.setText(var4);


        itemexpirationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Update_Delete.this, datePick, editDates.get(Calendar.YEAR), editDates.get(Calendar.MONTH), editDates.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ArrayAdapter unitAdapter = ArrayAdapter.createFromResource(this,R.array.UnitTypes,android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        itemUnit.setAdapter(unitAdapter);

        submit = (Button) findViewById(R.id.editSubmitBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Update Process
                var0 = itemName.getText().toString();
                var3 = itemexpirationDate.getText().toString();
                var1 = itemAmount.getText().toString();
                var2 = itemUnit.getSelectedItem().toString();
                var4 = itemMemo.getText().toString();

                if (var2.equals("Select one")){
                    Toast.makeText(getApplicationContext(),"Please select unit",Toast.LENGTH_SHORT).show();
                }else if (var0.length() >100 ||  var3.length() >100 || var4.length() >100){
                    Toast.makeText(getApplicationContext(), "Please enter no more than 100 words", Toast.LENGTH_LONG).show();
                }else if (var1.length() >10 ){
                    Toast.makeText(getApplicationContext(), "Please enter amount no more than 10 digits", Toast.LENGTH_LONG).show();
                }else if (!var0.equals("") && !var1.equals("") && !var2.equals("")&& !var3.equals("")){
                    //public void updateItem(String user_id, String item, String date, String amount, String unit, String memo)
                    theDb.updateItem(var5, var0, var3, var1, var2, var4);
                    //Back to Item List
                    Intent backtoItem = new Intent(getApplicationContext(), Homepage_Items.class);
                    backtoItem.putExtra("userId", Homepage_Items.id);
                    startActivity(backtoItem);
                    finish();
                } else{
                    Toast.makeText(getApplicationContext(),"Please Provide Required Information!",Toast.LENGTH_SHORT).show();
                }

            }
        });

        delete = (Button) findViewById(R.id.deleteBtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Delete Process

                theDb.delete(var5);

                cursor = theDb.searchItemId(id);
                String temp = "";
                String recipe = "";
                if (cursor.getCount()<=0){
                    Homepage_Items.recipe = "";

                } else if (cursor.moveToFirst() && cursor != null) {



                   do {
                       var0 = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                       var1 = cursor.getString(cursor.getColumnIndexOrThrow("amount"));
                       var2 = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
                       var3 = cursor.getString(cursor.getColumnIndexOrThrow("expire_date"));
                       var4 = cursor.getString(cursor.getColumnIndexOrThrow("memo"));
                       temp += var0 + ", ";

                   } while (cursor.moveToNext());


                    recipe = temp.substring(0, temp.length() - 2);
               }


                Homepage_Items.recipe = recipe;

                cursor.close();



                //Back to Item List
                Intent backtoItem = new Intent(getApplicationContext(), Homepage_Items.class);
                backtoItem.putExtra("userId", Homepage_Items.id);
                startActivity(backtoItem);
                finish();
            }
        });

        if (savedInstanceState != null) {
            String emailContinue = savedInstanceState.getString("title");
            itemName.setText(emailContinue);
            String amountContinue = savedInstanceState.getString("amount");
            itemAmount.setText(amountContinue);
            String dateContinue = savedInstanceState.getString("date");
            itemexpirationDate.setText(dateContinue);
            String memoCountinue = savedInstanceState.getString("memo");
            itemMemo.setText(memoCountinue);
        }

    }// onCreate

    @Override
    protected  void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title",itemName.getText().toString());
        outState.putString("memo",itemMemo.getText().toString());
        outState.putString("amount",itemAmount.getText().toString());
        outState.putString("date",itemexpirationDate.getText().toString());
    }


    private void updateLabel(){
        String format_default = "MM/dd/yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(format_default, Locale.US);

        EditText et_from_date = (EditText) findViewById(R.id.editExpirationDate);
        et_from_date.setText(formatter.format(editDates.getTime()));
    }

    //Overrides BackKeyHandler's onBackPressed method
    //Finish the app if user clicks the back button twice in 2 seconds.
    @Override
    public void onBackPressed() {
        backKeyHandler.onBackPressed_BacktoItem("You will lose your progress if you click back button again.");
    }
}