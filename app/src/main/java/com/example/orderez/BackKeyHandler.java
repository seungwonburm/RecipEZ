package com.example.orderez;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;
import android.os.Bundle;


import com.example.orderez.intro.WelcomePage;

public class BackKeyHandler {
    private Activity activity;
    private long backKeypressedTime = 0;
    private Toast toast;

    public BackKeyHandler(Activity activity) {
        this.activity = activity;
    }
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeypressedTime + 2000){
            backKeypressedTime = System.currentTimeMillis();
            backKeyProtocol();
            return;
        }
        if (System.currentTimeMillis() <= backKeypressedTime + 2000) {
            activity.finish();
        }
    }

    public void onBackPressed_custom(String msgCustom) {
        if (System.currentTimeMillis() > backKeypressedTime + 2000){
            backKeypressedTime = System.currentTimeMillis();
            backKeyProtocol(msgCustom);
            return;
        }
        if (System.currentTimeMillis() <= backKeypressedTime + 2000) {
            activity.finish();
        }
    }

    public void onBackPressed_BacktoMain(String msgCustom) {
        if (System.currentTimeMillis() > backKeypressedTime + 2000){
            backKeypressedTime = System.currentTimeMillis();
            backKeyProtocol(msgCustom);
            return;
        }
        if (System.currentTimeMillis() <= backKeypressedTime + 2000) {
            Intent gotoWelcome = new Intent(activity.getApplicationContext(),WelcomePage.class);
            activity.startActivity(gotoWelcome);
            activity.finish();
        }
    }

    private void backKeyProtocol() {
        toast.makeText(activity,"App will finish if you click back button again.", Toast.LENGTH_SHORT).show();
    }

    private void backKeyProtocol(String customMsg) {
        toast.makeText(activity,customMsg,Toast.LENGTH_SHORT).show();
    }
}
