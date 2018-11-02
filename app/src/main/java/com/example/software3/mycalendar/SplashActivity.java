package com.example.software3.mycalendar;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try{
            Thread.sleep(1300);//1.3 second splash screen
        }catch (Exception e){
            e.printStackTrace();
        }
        Intent intent =new Intent(this,MainActivity.class);
        intent.putExtra("pos","0");
        startActivity(intent);
        finish();

    }
}
