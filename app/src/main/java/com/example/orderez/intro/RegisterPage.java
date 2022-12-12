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
                    } else if (!(4<= password.getText().length() && password.getText().length()<=20) || !(4<= passwordVerify.getText().length() && passwordVerify.getText().length()<=20)){
                        Toast.makeText(getApplicationContext(), "Password Should be Between 4~20 Characters!", Toast.LENGTH_LONG).show();
                    }else if (!password.getText().toString().equals(passwordVerify.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Passwords Do Not Match!", Toast.LENGTH_LONG).show();
                    } else if (first.getText().toString().length()>100 || last.getText().toString().length()>100 || email.getText().toString().length()>100 || security_ans.getText().toString().length()>100 ) {
                        Toast.makeText(getApplicationContext(), "Too Much Information!", Toast.LENGTH_LONG).show();
                    } else {
                        String text = spinner.getSelectedItem().toString();
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


        if (savedInstanceState != null) {
            String emailContinue = savedInstanceState.getString("email");
            email.setText(emailContinue);
            String passwordContinue = savedInstanceState.getString("password");
            password.setText(passwordContinue);
            String passwordVersifyContinue = savedInstanceState.getString("passwordVerify");
            passwordVerify.setText(passwordVersifyContinue);
            String firstnameContinue = savedInstanceState.getString("firstname");
            first.setText(firstnameContinue);
            String secondnameContinue = savedInstanceState.getString("lastname");
            last.setText(secondnameContinue);
            String securityAnsContinue = savedInstanceState.getString("securityAns");
            security_ans.setText(securityAnsContinue);
        }


    }// onCreate

    @Override
    protected  void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email", email.getText().toString());
        outState.putString("password", password.getText().toString());
        outState.putString("passwordVerify", passwordVerify.getText().toString());
        outState.putString("firstname", first.getText().toString());
        outState.putString("lastname", last.getText().toString());
        outState.putString("securityAns", security_ans.getText().toString());
    }


    //Overrides BackKeyHandler's onBackPressed method
    //Finish the app if user clicks the back button twice in 2 seconds.
    @Override
    public void onBackPressed() {
        backKeyHandler.onBackPressed_BacktoMain("You will lose your progress if you click back button again.");
    }
}