package com.example.orderez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {
    Spinner spinner;
    Button button1, button2;
    EditText email_forgot, password_forgot, security_ans_forgot, password_verify_forgot;
    TextView newPasswordText;
    DatabaseManager theDb;
    Cursor cursor;
    String var0, var1, var2, var3, var4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        theDb = new DatabaseManager(this);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.Questions, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        String text = spinner.getSelectedItem().toString();


        button1 = (Button) findViewById(R.id.submit_forgot_1);
        button2 = (Button) findViewById(R.id.submit_forgot_2);
        email_forgot = (EditText) findViewById(R.id.email_forgot);
        password_forgot = (EditText) findViewById(R.id.password_forgot);
        password_verify_forgot = (EditText) findViewById(R.id.password_verify_forgot);
        security_ans_forgot = (EditText) findViewById(R.id.security_ans_forgot);
        newPasswordText = (TextView) findViewById(R.id.newPasswordText);

        password_forgot.setVisibility(View.INVISIBLE);
        password_verify_forgot.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        newPasswordText.setVisibility(View.INVISIBLE);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_forgot.getText().toString();
                cursor = theDb.search(email);
                if (cursor.getCount()<=0){
                    Toast.makeText(getApplicationContext(), "Account Not Found!", Toast.LENGTH_LONG).show();
                }
                else if (cursor.moveToFirst() && cursor != null){
                    do{
                        var0 = cursor.getString(cursor.getColumnIndexOrThrow("security"));
                        var1 = cursor.getString(cursor.getColumnIndexOrThrow("security_ans"));
                        var2 = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                        var3 = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                        var4 = cursor.getString(cursor.getColumnIndexOrThrow("password"));


                    } while (cursor.moveToNext());

                    if (!var0.equals(text.toLowerCase()) || !var1.equals(security_ans_forgot.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Account Not Found!!", Toast.LENGTH_LONG).show();
                    }else {

                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        password_forgot.setVisibility(View.VISIBLE);
                        password_verify_forgot.setVisibility(View.VISIBLE);
                        button2.setVisibility(View.VISIBLE);
                        newPasswordText.setVisibility(View.VISIBLE);
                        button1.setVisibility(View.INVISIBLE);
                    }
                }else if (cursor == null){
                    Toast.makeText(getApplicationContext(), "NO DATA!!", Toast.LENGTH_LONG).show();
                }
                cursor.close();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean added = false;
                if (!password_forgot.getText().toString().equals("") && !password_verify_forgot.getText().toString().equals("") ){
                  if (!password_forgot.getText().toString().equals(password_verify_forgot.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Passwords Do Not Match!", Toast.LENGTH_LONG).show();
                    }
                  else {
                        added = theDb.updatePW(var2, var3, password_forgot.getText().toString());
                    }


                    if (added == true){
                        Toast.makeText(getApplicationContext(), "Successfully Updated Password", Toast.LENGTH_LONG).show();
                        Intent LogInPage = new Intent(getApplicationContext(), LogInPage.class);
                        startActivity(LogInPage);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Failed to Update Password", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter Required Information!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}