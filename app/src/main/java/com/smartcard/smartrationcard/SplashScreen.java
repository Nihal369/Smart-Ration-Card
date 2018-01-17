package com.smartcard.smartrationcard;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Edit this for setting splash screen duration
        int timeLeft=3000;

        //Intent used to shift from splash screen activity to RFID_READ activity
        final Intent intent=new Intent(this,RFID_Read.class);

        //Start the countdown of splash screen
        new CountDownTimer(timeLeft, 1000) {

            @Override
            public void onTick(long l) {

            }

            //Launch RFID_READ after finishing timeLeft
            public void onFinish() {
                startActivity(intent);
                finish();
            }

        }.start();//Start the countdown timer

    }
}
