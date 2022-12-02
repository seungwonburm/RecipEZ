package com.example.orderez.intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orderez.DatabaseManager;
import com.example.orderez.R;
import com.example.orderez.homepage.HomePage;
import com.example.orderez.homepage.Homepage_ItemList;
import com.example.orderez.homepage.settingCategories.AccountSettings;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class LogInPage extends AppCompatActivity {
    Cursor cursor;
    DatabaseManager theDb;
    Button login,ForgotPasswordBtn,backtoMain;
    EditText emailLogin, passwordLogin;
    Homepage_ItemList homepage;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        theDb = new DatabaseManager(this);
        login = (Button) findViewById(R.id.loginbtLogin);
        emailLogin = (EditText) findViewById(R.id.emailLogin);
        passwordLogin = (EditText) findViewById(R.id.passwordLogin);


        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final  Homepage_ItemList myObj = new Homepage_ItemList();

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

                            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));



                            Bundle bundle = new Bundle();
                            bundle.putString("userId", id);

                            myObj.setArguments(bundle);
                            fragmentTransaction.add(R.id.frameLayout, myObj).commit();

                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                            intent.putExtra("userId", id);

                            Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_LONG).show();
                            startActivity(intent);

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


        ForgotPasswordBtn = (Button) findViewById(R.id.forgotPasswordBtnLogin);
        ForgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ForgotPasswordPage = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(ForgotPasswordPage);
            }
        });

        backtoMain = (Button)  findViewById(R.id.backtomainBtnLogin);
        backtoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gobacktoMainPage = new Intent(getApplicationContext(), WelcomePage.class);
                startActivity(gobacktoMainPage);
            }
        });
    }



}