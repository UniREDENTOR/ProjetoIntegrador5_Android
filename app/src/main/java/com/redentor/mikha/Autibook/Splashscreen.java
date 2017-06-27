package com.redentor.mikha.Autibook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Splashscreen extends AppCompatActivity {


        Thread splashTread;
        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate (savedInstanceState);
            setContentView (R.layout.activity_splashscreen);
            StartAnimations ();
        }

    private void StartAnimations() {

        splashTread = new Thread () {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep (100);
                        waited += 100;
                    }
                        Intent intent = new Intent (Splashscreen.this,
                                LoginActivity.class);
                        intent.setFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity (intent);
                    Splashscreen.this.finish ();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splashscreen.this.finish ();
                }
            }
        };
        splashTread.start ();

    }
}

