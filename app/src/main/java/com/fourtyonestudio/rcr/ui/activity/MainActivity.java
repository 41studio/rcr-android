package com.fourtyonestudio.rcr.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.preferences.DataPreferences;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginSession loginSession = new DataPreferences(MainActivity.this).getLoginSession();
        if (loginSession != null) {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        } else {
            showSplash();
        }
    }

    private void showSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,
                        LoginActivity.class));
                finish();
            }
        }, 3000);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
