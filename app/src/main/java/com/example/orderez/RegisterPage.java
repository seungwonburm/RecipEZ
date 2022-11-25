package com.example.orderez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterPage extends AppCompatActivity {

    EditText email, first, last, password, passwordVerify;
    Button register, back;
    DatabaseManager theDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        theDB = new DatabaseManager(this);

        email = (EditText) findViewById(R.id.emailReg);
        first = (EditText) findViewById(R.id.firstnameReg);
        last = (EditText) findViewById(R.id.lastnameReg);
        password = (EditText) findViewById(R.id.passwordReg);
        passwordVerify = (EditText) findViewById(R.id.passwordVerifyReg);



        register = (Button) findViewById(R.id.registerReg);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean added = false;
                if (!first.getText().toString().equals("") && !last.getText().toString().equals("") && !email.getText().toString().equals("") && !passwordVerify.getText().toString().equals("") ){
                    int result =theDB.verify(email.getText().toString());
                    if (result == -1){
                        added = false;
                        Toast.makeText(getApplicationContext(), "Email Already Exists", Toast.LENGTH_LONG).show();

                    } else if (result == -2){
                        Toast.makeText(getApplicationContext(), "Invalid Email Format", Toast.LENGTH_LONG).show();
                    } else if (!password.getText().toString().equals(passwordVerify.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Passwords Do Not Match!", Toast.LENGTH_LONG).show();
                    } else {
                        added = theDB.insert(first.getText().toString(), last.getText().toString(), email.getText().toString(), password.getText().toString());
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

        back = (Button) findViewById(R.id.backtomainBtnReg);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtoLoginPage = new Intent(getApplicationContext(), WelcomePage.class);
                startActivity(backtoLoginPage);
            }
        });



    }


}