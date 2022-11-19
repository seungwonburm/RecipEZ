package com.example.orderez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterPage extends AppCompatActivity {

    EditText email, first, last, password, security, passwordVerify;
    Button register;
    DatabaseManager theDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        theDB = new DatabaseManager(this);

        email = (EditText) findViewById(R.id.email);
        first = (EditText) findViewById(R.id.first);
        last = (EditText) findViewById(R.id.last);
        password = (EditText) findViewById(R.id.password);
        passwordVerify = (EditText) findViewById(R.id.passwordVerify);
        security = (EditText) findViewById(R.id.security);


        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean added = false;
                if (!first.getText().toString().equals("") && !last.getText().toString().equals("") && !email.getText().toString().equals("") && !passwordVerify.getText().toString().equals("") && !security.getText().toString().equals("")){
                    int result =theDB.verify(email.getText().toString());
                    if (result == -1){
                        added = false;
                        Toast.makeText(getApplicationContext(), "Email Already Exists", Toast.LENGTH_LONG).show();

                    } else if (result == -2){
                        Toast.makeText(getApplicationContext(), "Invalid Email Format", Toast.LENGTH_LONG).show();
                    } else if (!password.getText().toString().equals(passwordVerify.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Passwords Do Not Match!", Toast.LENGTH_LONG).show();
                    } else {
                        added = theDB.insert(first.getText().toString(), last.getText().toString(), email.getText().toString(), password.getText().toString(), security.getText().toString());
                    }


                    if (added == true){
                        Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_LONG).show();
                        Intent LogInPage = new Intent(getApplicationContext(), LogInPage.class);
                        startActivity(LogInPage);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Data Not Added", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter Required Information!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}