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

import com.example.orderez.BackKeyHandler;
import com.example.orderez.DatabaseManager;
import com.example.orderez.R;
import com.example.orderez.homepage.Homepage_Items;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class LogInPage extends AppCompatActivity {
    private Cursor cursor;
    private DatabaseManager theDb;
    private Button login,ForgotPasswordBtn,backtoMain;
    private EditText emailLogin, passwordLogin;
    private BackKeyHandler backKeyHandler = new BackKeyHandler(this);
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

                String email = emailLogin.getText().toString();
                cursor =theDb.search(email); // Grabs Account If Exists
                if (cursor.getCount()<=0){ // If Account Does Not Exists
                    Toast.makeText(getApplicationContext(), "Login Failed!!", Toast.LENGTH_LONG).show();
                }
                else if (cursor.moveToFirst() && cursor != null) { // If Account Does Exists
                    String pw = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                    try {
                        String decrypted = AESCrypt.decrypt(passwordLogin.getText().toString(), pw); //Returns Email if Password is Correct

                        if (decrypted.equals(email)){ //Login Successful

                            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));

                            Intent intent = new Intent(getApplicationContext(), Homepage_Items.class); //Sends User_Id to Next Activity
                            intent.putExtra("userId", id);
                            Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();

                        }
                    }catch (GeneralSecurityException e){
                        //Login Failed due to  incorrect password or tampered encryptedMsg
                        Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_LONG).show();
                    }
                } else if (cursor == null){
                    Toast.makeText(getApplicationContext(), "NO DATA!!", Toast.LENGTH_LONG).show();
                }
                cursor.close();
            }});

        ForgotPasswordBtn = (Button) findViewById(R.id.forgotPasswordBtnLogin);
        ForgotPasswordBtn.setOnClickListener(new View.OnClickListener() { //Forgot Password Button
            @Override
            public void onClick(View v) {
                Intent ForgotPasswordPage = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(ForgotPasswordPage);
                finish();
            }
        });

        backtoMain = (Button)  findViewById(R.id.backtomainBtnLogin);
        backtoMain.setOnClickListener(new View.OnClickListener() { // Back to Main Button
            @Override
            public void onClick(View view) {
               Intent backtoMain = new Intent(getApplicationContext(),WelcomePage.class);
               startActivity(backtoMain);
               finish();
            }
        });


        if (savedInstanceState != null) {
            String emailContinue = savedInstanceState.getString("email");
            emailLogin.setText(emailContinue);
            String passwordContinue = savedInstanceState.getString("password");
            passwordLogin.setText(passwordContinue);

        }



    }

    @Override
    protected  void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email", emailLogin.getText().toString());
        outState.putString("password", passwordLogin.getText().toString());

    }

    //Overrides BackKeyHandler's onBackPressed method
    //Finish the app if user clicks the back button twice in 2 seconds.
    @Override
    public void onBackPressed() {
        backKeyHandler.onBackPressed_BacktoMain("If you want to go back to main page press back button again.");
    }
}