package com.example.orderez.settingCategories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderez.DatabaseManager;
import com.example.orderez.R;

public class Setting_Account extends AppCompatActivity {

    DatabaseManager theDb;
    TextView welcomeUser, welcomeEmail;
    Cursor cursor;
    String var0, var1, var2, var3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_account);

//        Intent receive_intent = getIntent();
//        String id = receive_intent.getStringExtra("id");
//        theDb = new DatabaseManager(this);
//
//        welcomeUser = (TextView) findViewById(R.id.welcome_user);
//        welcomeEmail = (TextView) findViewById(R.id.welcome_email);
//
//        cursor = theDb.searchId(id);

//        if (cursor.getCount()<=0){
//            Toast.makeText(getApplicationContext(), "Account Not Found!", Toast.LENGTH_LONG).show();
//        }
//        else if (cursor.moveToFirst() && cursor != null){
//            do{
//                var0 = cursor.getString(cursor.getColumnIndexOrThrow("id"));
//                var1 = cursor.getString(cursor.getColumnIndexOrThrow("first"));
//                var2 = cursor.getString(cursor.getColumnIndexOrThrow("last"));
//                var3 = cursor.getString(cursor.getColumnIndexOrThrow("email"));
//
//                String text = "Welcome " + var1 + " " + var2;
//                welcomeUser.setText(text + "");
//                welcomeEmail.setText(var3 + "");
//
//            } while (cursor.moveToNext());
//
//        }



    }

//    public void loggedIn(String id){
//
//    }
}