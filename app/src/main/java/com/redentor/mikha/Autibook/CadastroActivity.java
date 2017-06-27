package com.redentor.mikha.Autibook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity {

    BootstrapEditText nomeDoUsuario;
    BootstrapEditText senhaDoUsuario;
    BootstrapEditText emailDoUsuario;

    BootstrapButton confirmarCadastro;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseRef;

    TextView textviewInfo;

    ProgressDialog progressDialog;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_cadastro);
        progressDialog = new ProgressDialog(getWindow().getContext());

        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();

        initViews();

        textviewInfo.setText("Preencha os campos e aperte no para cadastrar");

        confirmarCadastro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("registrando...");
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                new CadastrarTask().execute();
            }
        });
    }

    private class CadastrarTask extends AsyncTask<Void, Integer, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgress(50);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            CadastroActivity cAcivity = CadastroActivity.this;
            try {
                cAcivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        saveUser();
                    }
                });
            }catch (Exception e){
                Log.e("TAG_ASYNC_TASK", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            textviewInfo.setText(s);
        }
    }

    protected void initViews() {
        nomeDoUsuario = (BootstrapEditText) findViewById(R.id.textEditNomeUsuario);
        senhaDoUsuario = (BootstrapEditText) findViewById(R.id.textEditPassword);
        emailDoUsuario = (BootstrapEditText) findViewById(R.id.textEditEmail);
        confirmarCadastro = (BootstrapButton) findViewById(R.id.botaoConfirmar);
        textviewInfo = (TextView) findViewById(R.id.textviewInfo);
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
        if (TextUtils.isEmpty(senhaDoUsuario.getText().toString()) || senhaDoUsuario.length() < 6){
            //senha is empty
            Toast.makeText(CadastroActivity.this, "A senha deve conter 6 dígitos ou mais", Toast.LENGTH_SHORT).show();
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
                    firebaseUser = mAuth.getCurrentUser();
                    databaseRef.child(firebaseUser.getUid()).setValue(user);
                    Intent intent = new Intent(CadastroActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else{
                    progressDialog.hide();
                }
            }
        });
    }
}
