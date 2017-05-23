package com.example.mikha.projetointegrador5android;

import com.firebase.client.Firebase;

public class FirebaseTest {
    private static Firebase firebase;

    public static Firebase getFirebase(){
        if( firebase == null ){
            firebase = new Firebase("https://audiobook-pi5.firebaseio.com/");
        }
        return( firebase );
    }
}
