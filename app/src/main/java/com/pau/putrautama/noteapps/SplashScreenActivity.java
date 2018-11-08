package com.pau.putrautama.noteapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    Thread splashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        startAnimation();
    }

    private void startAnimation() {
        splashThread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    int waited = 0;
                    while (waited < 3500){
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    SplashScreenActivity.this.finish();
                }
            }
        };
        splashThread.start();
    }
}
