package com.example.mikha.projetointegrador5android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splashscreen extends AppCompatActivity {

    FirebaseAuth firebaseAuth, auth;
    FirebaseUser user;

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
                    auth = FirebaseAuth.getInstance();
                    user = auth.getCurrentUser();
                    if(user != null){
                        Intent intent = new Intent (Splashscreen.this,
                                HomeActivity.class);
                        intent.setFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity (intent);
                    }else{
                        Intent intent = new Intent (Splashscreen.this,
                                LoginActivity.class);
                        intent.setFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity (intent);
                    }
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

