package com.example.mikha.projetointegrador5android;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.BootstrapProgressBar;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    BootstrapEditText nomeDoUsuario;
    BootstrapEditText senhaDoUsuario;
    BootstrapEditText emailDoUsuario;

    BootstrapButton confirmarCadastro;
    BootstrapButton botaoAutoFill;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseRef;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_cadastro);

        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();
        initViews();

        confirmarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });

        botaoAutoFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeDoUsuario.setText("mika");
                senhaDoUsuario.setText("0803890");
                emailDoUsuario.setText("mikhael.pc@hotmail.com");
            }
        });
    }

    protected void initViews() {
        nomeDoUsuario = (BootstrapEditText) findViewById(R.id.textEditNomeUsuario);
        senhaDoUsuario = (BootstrapEditText) findViewById(R.id.textEditPassword);
        emailDoUsuario = (BootstrapEditText) findViewById(R.id.textEditEmail);
        confirmarCadastro = (BootstrapButton) findViewById(R.id.botaoConfirmar);
        botaoAutoFill = (BootstrapButton) findViewById(R.id.botaoAutoFill);
        progressDialog = new ProgressDialog(this);
    }

    protected User initUser(){
        user = new User();
        user.setUsername(nomeDoUsuario.getText().toString() );
        user.setPassword(senhaDoUsuario.getText().toString() );
        user.setEmail(emailDoUsuario.getText().toString() );
        user.setPontuacaoCor("0");
        user.setPontuacaoNumero("0");
        user.setPontuacaoObjeto("0");
        if (TextUtils.isEmpty(nomeDoUsuario.getText().toString())){
            //Nome is empty
            Toast.makeText(CadastroActivity.this, "Nome vazio", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(senhaDoUsuario.getText().toString())){
            //senha is empty
            Toast.makeText(CadastroActivity.this, "Senha vazio", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(emailDoUsuario.getText().toString())){
            //email is empty
            Toast.makeText(CadastroActivity.this, "Email vazio", Toast.LENGTH_SHORT).show();
        }
        return user;
    }

    private void saveUser(){
        initUser();
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.setMessage("Registrando...");
                    progressDialog.show();
                    firebaseUser = mAuth.getCurrentUser();
                    databaseRef.child(firebaseUser.getUid()).setValue(user);
                    finish();
                }else{
                    Toast.makeText(CadastroActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
