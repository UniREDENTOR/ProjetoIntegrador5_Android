package com.example.mikha.projetointegrador5android;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Map;

public class CadastroActivity extends AppCompatActivity {

    BootstrapEditText nomeDoUsuario;
    BootstrapEditText senhaDoUsuario;
    BootstrapEditText emailDoUsuario;
    BootstrapButton confirmarCadastro;

    private FirebaseAuth mAuth;

    private Firebase firebase;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_cadastro);

        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseTest.getFirebase();
        initViews();

        confirmarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUser();
                saveUser();
            }
        });
    }

    protected void initViews() {
        nomeDoUsuario = (BootstrapEditText) findViewById(R.id.textEditNomeUsuario);
        senhaDoUsuario = (BootstrapEditText) findViewById(R.id.textEditPassword);
        emailDoUsuario = (BootstrapEditText) findViewById(R.id.textEditEmail);
        confirmarCadastro = (BootstrapButton) findViewById(R.id.botaoConfirmar);
    }

    protected void initUser(){
        user = new User();
        user.setUsername(nomeDoUsuario.getText().toString() );
        user.setPassword(senhaDoUsuario.getText().toString() );
        user.setEmail(emailDoUsuario.getText().toString() );
    }

    private void saveUser(){
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this, "SUCESSO.", Toast.LENGTH_SHORT).show();
                    user.saveDB();
                    finish();
                }else{
                    Toast.makeText(CadastroActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

}
