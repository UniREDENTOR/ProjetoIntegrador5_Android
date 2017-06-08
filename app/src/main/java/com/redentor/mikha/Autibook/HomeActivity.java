package com.redentor.mikha.Autibook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    Button vamosComecar;

    FirebaseAuth auth;
    DatabaseReference databaseRef, userDB;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;

    int contadorCor, contadorObjeto, contadorNumero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        vamosComecar = (Button) findViewById(R.id.buttonVamosComecar);

        auth = auth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();
        user = auth.getCurrentUser();
        userDB = databaseRef.child(user.getUid());

        vamosComecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(HomeActivity.this, MainActivity.class);
                main.putExtra("pontuacaoObjeto", contadorObjeto);
                main.putExtra("pontuacaoCor", contadorCor);
                main.putExtra("pontuacaoNumero", contadorNumero);
                startActivity(main);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user != null) {
                    Log.d("tag", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d("tag", "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();

                Log.v("mapString", map+"");

                if (map != null) {
                    String username = map.get("username");
                    String password = map.get("password");
                    String email = map.get("email");
                    String pontuacaoObjeto = map.get("pontuacaoObjeto");
                    String pontuacaoCor = map.get("pontuacaoCor");
                    String pontuacaoNumero = map.get("pontuacaoNumero");

                    contadorObjeto = Integer.parseInt(pontuacaoObjeto);
                    contadorCor = Integer.parseInt(pontuacaoCor);
                    contadorNumero = Integer.parseInt(pontuacaoNumero);
                }else{
                    contadorCor = 0;
                    contadorNumero = 0;
                    contadorObjeto = 0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }





}
