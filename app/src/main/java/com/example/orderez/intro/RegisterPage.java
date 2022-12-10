package com.example.orderez.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.orderez.BackKeyHandler;
import com.example.orderez.DatabaseManager;
import com.example.orderez.R;

public class RegisterPage extends AppCompatActivity {

    //Make Variable private. prevent use in other activity.
    private EditText email, first, last, password, passwordVerify, security_ans;
    private Button register, backtoMain;
    private DatabaseManager theDB;
    private Spinner spinner;

    private BackKeyHandler backKeyHandler = new BackKeyHandler(this);


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
        security_ans = (EditText) findViewById(R.id.security_ans);


        spinner = (Spinner) findViewById(R.id.viewChangeDropdown);
        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.Questions, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        String text = spinner.getSelectedItem().toString();


        backtoMain = (Button) findViewById(R.id.backtomainBtnReg);
        backtoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gobackToMain = new Intent(getApplicationContext(),WelcomePage.class);
                startActivity(gobackToMain);
                finish();
            }
        });



        register = (Button) findViewById(R.id.registerReg);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean added = false;
                if (!first.getText().toString().equals("") && !last.getText().toString().equals("") && !email.getText().toString().equals("") && !passwordVerify.getText().toString().equals("") && !security_ans.getText().toString().equals("") && !password.getText().toString().equals("") ){
                    int result =theDB.verify(email.getText().toString());
                    if (result == -1){
                        added = false;
                        Toast.makeText(getApplicationContext(), "Email Already Exists", Toast.LENGTH_LONG).show();

                    } else if (result == -2){
                        Toast.makeText(getApplicationContext(), "Invalid Email Format", Toast.LENGTH_LONG).show();
                    } else if (!password.getText().toString().equals(passwordVerify.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Passwords Do Not Match!", Toast.LENGTH_LONG).show();
                    } else {
                        added = theDB.insert(first.getText().toString(), last.getText().toString(), email.getText().toString(), password.getText().toString(), text, security_ans.getText().toString());
                    }


                    if (added == true){
                        Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_LONG).show();
                        Intent LogInPage = new Intent(getApplicationContext(), com.example.orderez.intro.LogInPage.class);
                        startActivity(LogInPage);
                        finish();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Data Not Added", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter Required Information!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }// onCreate


    //Overrides BackKeyHandler's onBackPressed method
    //Finish the app if user clicks the back button twice in 2 seconds.
    @Override
    public void onBackPressed() {
        backKeyHandler.onBackPressed_BacktoMain("You will lose your progress if you click back button again.");
    }
}