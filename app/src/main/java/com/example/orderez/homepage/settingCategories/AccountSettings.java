package com.example.orderez.homepage.settingCategories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderez.DatabaseManager;
import com.example.orderez.R;

public class AccountSettings extends AppCompatActivity {

    TextView welcome, email;
    DatabaseManager theDb;
    Cursor cursor;
    String id, var1, var2, var3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        welcome = (TextView) findViewById(R.id.welcome);
        email = (TextView) findViewById(R.id.email);

        theDb = new DatabaseManager(this);


        Intent intent = getIntent();
        id = intent.getStringExtra("userId");

        cursor =theDb.searchId(id);
        if (cursor.getCount()<=0){

            Toast.makeText(getApplicationContext(), "Failed To Load Data!", Toast.LENGTH_LONG).show();
        }
        else if (cursor.moveToFirst() && cursor != null) {

            var1 = cursor.getString(cursor.getColumnIndexOrThrow("first"));
            var2 = cursor.getString(cursor.getColumnIndexOrThrow("last"));
            var3 = cursor.getString(cursor.getColumnIndexOrThrow("email"));

            welcome.setText("Welcome " + var1 + " " + var2 + "!");
            email.setText(var3+"");

        }






    }
}