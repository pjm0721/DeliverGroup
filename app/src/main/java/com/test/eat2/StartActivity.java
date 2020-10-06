package com.test.eat2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import static maes.tech.intentanim.CustomIntent.customType;

public class StartActivity extends AppCompatActivity {
    LoginSharedPreferenceUtil util;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        util = new LoginSharedPreferenceUtil(this);
        final Boolean autoLogin = util.getBooleanData("AutoLogin", false);
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()  {
            public void run() {
                if(autoLogin){
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                    customType(StartActivity.this, "bottom-to-top");
                }
                finish();
            }
        }, 1500);
    }
}