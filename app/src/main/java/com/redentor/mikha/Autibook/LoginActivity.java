package com.redentor.mikha.Autibook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    BootstrapEditText emailLogin;
    BootstrapEditText passwordLogin;
    BootstrapButton botaoConfirmarLogin, botaoPaginaDeCadastro;

    CheckBox check;

    public static final String NOME_PREFERENCE = "INFORMACOES_LOGIN_AUTOMATICO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailLogin = (BootstrapEditText) findViewById(R.id.textEditEmailLogin);
        passwordLogin = (BootstrapEditText) findViewById(R.id.textEditPasswordLogin);
        botaoConfirmarLogin = (BootstrapButton) findViewById(R.id.botaoConfirmarLogin);
        botaoPaginaDeCadastro = (BootstrapButton) findViewById(R.id.botaoPaginaDeCadastro);
        check = (CheckBox) findViewById(R.id.checkBox);
        mAuth = FirebaseAuth.getInstance();

        SharedPreferences prefs = getSharedPreferences(NOME_PREFERENCE, MODE_PRIVATE);

        final String email = prefs.getString("Email", null);
        final String senha = prefs.getString("senha", null);


        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (email != null){
                        emailLogin.setText(email);
                        passwordLogin.setText(senha);
                    }
                }else{
                    emailLogin.setText("");
                    passwordLogin.setText("");
                }
            }
        });

        botaoConfirmarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailLogin.getText().toString().equals("") && passwordLogin.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "é preciso preencher Email e Senha", Toast.LENGTH_LONG).show();
                }else if (passwordLogin.getText() == null || passwordLogin.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Por favor, digite a senha", Toast.LENGTH_LONG).show();
                }else if (emailLogin.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Por favor, digite o email", Toast.LENGTH_LONG).show();
                }else{
                    mAuth.signInWithEmailAndPassword(emailLogin.getText().toString(), passwordLogin.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Email ou senha incorretos", Toast.LENGTH_SHORT).show();
                            }else{
                                SharedPreferences.Editor editor = getSharedPreferences(NOME_PREFERENCE, MODE_PRIVATE).edit();
                                editor.putString("Email", emailLogin.getText().toString());
                                editor.putString("senha", passwordLogin.getText().toString());
                                editor.apply();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }

            }
        });

        botaoPaginaDeCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
