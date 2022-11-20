package com.example.orderez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class LogInPage extends AppCompatActivity {
    Cursor cursor;
    DatabaseManager theDb;
    Button login;
    EditText emailLogin, passwordLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        theDb = new DatabaseManager(this);
        login = (Button) findViewById(R.id.loginbtLogin);
        emailLogin = (EditText) findViewById(R.id.emailLogin);
        passwordLogin = (EditText) findViewById(R.id.passwordLogin);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Login the user here if correct pass and email

                String email = emailLogin.getText().toString();
                cursor =theDb.search(email);
                if (cursor.getCount()<=0){

                    Toast.makeText(getApplicationContext(), "Login Failed!!", Toast.LENGTH_LONG).show();
                }
                else if (cursor.moveToFirst() && cursor != null) {

                    String pw = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                    try {
                        String decrypted = AESCrypt.decrypt(passwordLogin.getText().toString(), pw);
                        System.out.println("");
                        if (decrypted.equals(email)){
                            Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_LONG).show();
                            Intent gotohomepage = new Intent(getApplicationContext(),HomePage.class);
                            startActivity(gotohomepage);

                        }
                    }catch (GeneralSecurityException e){
                        //handle error - could be due to incorrect password or tampered encryptedMsg
                        Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_LONG).show();

                    }


                } else if (cursor == null){
                    Toast.makeText(getApplicationContext(), "NO DATA!!", Toast.LENGTH_LONG).show();
                }
                cursor.close();

            }


                                 }
        );



        Button ForgotPasswordBtn = (Button) findViewById(R.id.forgotPasswordBtnLogin);
        ForgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ForgotPasswordPage = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(ForgotPasswordPage);
            }
        });



    }



}