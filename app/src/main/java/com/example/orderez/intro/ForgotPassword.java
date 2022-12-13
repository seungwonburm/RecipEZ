package com.example.orderez.intro;

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

import com.example.orderez.BackKeyHandler;
import com.example.orderez.DatabaseManager;
import com.example.orderez.R;
import com.example.orderez.homepage.Homepage_Items;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class ForgotPassword extends AppCompatActivity {
    private Spinner spinner;
    private Button button1, button2;
    private EditText email_forgot, password_forgot, security_ans_forgot, password_verify_forgot;
    private TextView newPasswordText;
    private DatabaseManager theDb;
    private Cursor cursor;
    private String var0, var1, var2, var3, var4;
    private BackKeyHandler backKeyHandler = new BackKeyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        theDb = new DatabaseManager(this);
        spinner = (Spinner) findViewById(R.id.viewChangeDropdown);
        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.Questions, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);



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
                cursor = theDb.search(email);//Search account associated with email
                String text = spinner.getSelectedItem().toString();

                if (cursor.getCount()<=0){  //If No Account Found
                    Toast.makeText(getApplicationContext(), "Account Not Found!", Toast.LENGTH_LONG).show();
                }
                else if (cursor.moveToFirst() && cursor != null){ //If Account Found

                    var0 = cursor.getString(cursor.getColumnIndexOrThrow("security")); // Stores DB Element values in Strings
                    var1 = cursor.getString(cursor.getColumnIndexOrThrow("security_ans"));
                    var2 = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                    var3 = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                    var4 = cursor.getString(cursor.getColumnIndexOrThrow("password"));

                    if (!var0.equals(text.toLowerCase()) ){ //If Security Question is Incorrect
                        Toast.makeText(getApplicationContext(), "Account Not Found!!", Toast.LENGTH_LONG).show();
                    }else {
                        try {
                            String decrypted = AESCrypt.decrypt(security_ans_forgot.getText().toString(), var1); //Security Answer Decryption, returns email if correct
                            if (decrypted.equals(email)){
                                if (view != null) {
                                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                }

                                //Next Step becomes Visible if Account is found
                                password_forgot.setVisibility(View.VISIBLE);
                                password_verify_forgot.setVisibility(View.VISIBLE);
                                button2.setVisibility(View.VISIBLE);
                                newPasswordText.setVisibility(View.VISIBLE);
                                button1.setVisibility(View.INVISIBLE);

                            }
                        }catch (GeneralSecurityException e){
                            // Error due to incorrect security answers or tampered encryptedMsg
                            Toast.makeText(getApplicationContext(), "Account Not Found!", Toast.LENGTH_LONG).show();
                        }

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

                //Error Handlers
                if (!password_forgot.getText().toString().equals("") && !password_verify_forgot.getText().toString().equals("") ){
                    if (!(4<= password_forgot.getText().length() && password_forgot.getText().length()<=20) || !(4<= password_verify_forgot.getText().length() && password_verify_forgot.getText().length()<=20)){
                        Toast.makeText(getApplicationContext(), "New Password Should be Between 4~20 Characters!", Toast.LENGTH_LONG).show();
                    }
                    else if (!password_forgot.getText().toString().equals(password_verify_forgot.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Passwords Do Not Match!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        added = theDb.updatePW(var2, var3, password_forgot.getText().toString());
                    }


                    if (added == true){
                        Toast.makeText(getApplicationContext(), "Successfully Updated Password", Toast.LENGTH_LONG).show();
                        Intent LogInPage = new Intent(getApplicationContext(), com.example.orderez.intro.LogInPage.class);
                        startActivity(LogInPage);
                        finish();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Failed to Update Password", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter Required Information!", Toast.LENGTH_LONG).show();
                }

            }
        });

        if (savedInstanceState != null) {
            String emailContinue = savedInstanceState.getString("forgot_email");
            email_forgot.setText(emailContinue);
            String passwordContinue = savedInstanceState.getString("forgot_password");
            password_forgot.setText(passwordContinue);
            String passwordVersifyContinue = savedInstanceState.getString("forgot_verifyPassword");
            password_verify_forgot.setText(passwordVersifyContinue);
            String firstnameContinue = savedInstanceState.getString("forgot_securityans");
            security_ans_forgot.setText(firstnameContinue);
            String secondnameContinue = savedInstanceState.getString("forgot_newPassword");
            newPasswordText.setText(secondnameContinue);
        }

    }// onCreate

    @Override
    protected  void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("forgot_email",email_forgot.getText().toString());
        outState.putString("forgot_password",password_forgot.getText().toString());
        outState.putString("forgot_verifyPassword",password_verify_forgot.getText().toString());
        outState.putString("forgot_securityans",security_ans_forgot.getText().toString());
        outState.putString("forgot_newPassword",newPasswordText.getText().toString());
    }

    //Overrides BackKeyHandler's onBackPressed method
    //Finish the app if user clicks the back button twice in 2 seconds.
    @Override
    public void onBackPressed() {
        backKeyHandler.onBackPressed_BacktoMain("You will lose your progress if you click back button again.");
    }
}