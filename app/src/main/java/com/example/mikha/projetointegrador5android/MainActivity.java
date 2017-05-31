package com.example.mikha.projetointegrador5android;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ArraysImagensEstrings classeArrays = new ArraysImagensEstrings();

    TextToSpeech tts;
    EditText editTextPrincipal;

    int contadorCor, contadorObjeto, contadorNumero, queTelaEstamos;

    Button botaoDeFalar;
    Button botaoDeMudarImagem;

    String oqSeraFalado;
    String resposta;

    TextView resultadoPalavra;

    private FirebaseAuth.AuthStateListener mAuthListener;

    LinearLayout linearLayoutDoEditTextEButton;
    LinearLayout linearLayoutDoTextViewDoFragment;
    LinearLayout linearLayoutDaImagemDoFragment;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseRef;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPrincipal = (EditText) findViewById(R.id.EditTextEntrada);
        botaoDeFalar = (Button) findViewById(R.id.ButtonTextToSpeech);
        botaoDeMudarImagem = (Button) findViewById(R.id.ButtonTextView);
        resultadoPalavra = (TextView) findViewById(R.id.TextViewTesteFragment);
        linearLayoutDaImagemDoFragment = (LinearLayout) findViewById(R.id.linearlayoutimagem);
        linearLayoutDoEditTextEButton = (LinearLayout) findViewById(R.id.linearLayoutDoEditTextEButton);
        linearLayoutDoTextViewDoFragment = (LinearLayout) findViewById(R.id.linearLayoutDoTextViewDoFragment);

        final Locale localeBR = new Locale("pt","BR");
        firebaseAuth = firebaseAuth.getInstance();
        contadorNumero = 0;
        contadorCor = 0;
        contadorObjeto = 0;

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("tag", "onAuthStateChanged:signed_in:" + user.getUid());
                    editTextPrincipal.setHint(user.getEmail());
                } else {
                    Log.d("tag", "onAuthStateChanged:signed_out");
                }
            }
        };

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    tts.setLanguage(localeBR);
                }
            }
        });

        editTextPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextPrincipal.addTextChangedListener(textWatcher);
            }
        });

        botaoDeMudarImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (botaoDeMudarImagem.getText().equals("Avançar")) {

                    if (id==1){
                        if (contadorObjeto+1 <= classeArrays.checarTamanhoArray(id)){
                            linearLayoutDaImagemDoFragment.setBackgroundResource(classeArrays.getImagensObjetos(contadorObjeto));
                            resposta = classeArrays.getRespostasObjetos(contadorObjeto);
                        }
                    }else if(id==2){
                        if (contadorCor+1 <= classeArrays.checarTamanhoArray(id)){
                            linearLayoutDaImagemDoFragment.setBackgroundResource(classeArrays.getImagensCor(contadorCor));
                            resposta = classeArrays.getRespostasCor(contadorCor);
                        }
                    }else if(id==3){
                        if (contadorNumero+1 <= classeArrays.checarTamanhoArray(id)){
                            linearLayoutDaImagemDoFragment.setBackgroundResource(classeArrays.getImagensNumero(contadorNumero));
                            resposta = classeArrays.getRespostasNumero(contadorNumero);
                        }
                    }
                    editTextPrincipal.getText().clear();
                }
                alterarTelas();
            }
        });

        botaoDeFalar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oqSeraFalado = editTextPrincipal.getText().toString();
                vamosFalar();
                alterarTelas();
                if (testeResultado()) {
                    resultadoPalavra.setText("Você acertou! Pressione o botão para continuar");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    databaseRef = FirebaseDatabase.getInstance().getReference();
                    if (id==1){
                        contadorObjeto++;
                        String pontuacao = String.valueOf(contadorObjeto);
                        databaseRef.child(user.getUid()).child("pontuacaoObjeto").setValue(pontuacao);
                    }else if (id==2){
                        contadorCor++;
                        String pontuacao = String.valueOf(contadorCor);
                        databaseRef.child(user.getUid()).child("pontuacaoCor").setValue(pontuacao);
                    }else if (id==3){
                        contadorNumero++;
                        String pontuacao = String.valueOf(contadorNumero);
                        databaseRef.child(user.getUid()).child("pontuacaoNumero").setValue(pontuacao);
                    }
                    botaoDeMudarImagem.setText("Avançar");
                }else{
                    resultadoPalavra.setText("Você errou, tente novamente");
                    botaoDeMudarImagem.setText("voltar");
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    public boolean testeResultado() {
        return resposta.equalsIgnoreCase(oqSeraFalado);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.itensdomenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.categoria1Objetos:
                linearLayoutDaImagemDoFragment.setBackgroundResource(classeArrays.getImagensObjetos(contadorObjeto));
                resposta = classeArrays.getRespostasObjetos(contadorObjeto);
                id = 1;
                return true;
            case R.id.categoria2Cores:
                linearLayoutDaImagemDoFragment.setBackgroundResource(classeArrays.getImagensCor(contadorCor));
                resposta = classeArrays.getRespostasCor(contadorCor);
                id = 2;
                return true;
            case R.id.categoria3Numeros:
                linearLayoutDaImagemDoFragment.setBackgroundResource(classeArrays.getImagensNumero(contadorNumero));
                resposta = classeArrays.getRespostasNumero(contadorNumero);
                id = 3;
                return true;
            case R.id.categoria2cadastro:
                Intent cadastroActivity = new Intent(this, CadastroActivity.class);
                startActivity(cadastroActivity);
                return true;
            case R.id.categoria3login:
                Intent loginActivity = new Intent(this, LoginActivity.class);
                startActivity(loginActivity);
                return true;
            case R.id.categoria4pontuacao:
                Intent pontuacao = new Intent(this, PontuacaoUserActivity.class);
                startActivity(pontuacao);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        if (queTelaEstamos == 1) {
            alterarTelas();
        }
        super.onBackPressed();
    }

//    @Override
//    public void onStop(){
//        if(tts != null){
//            tts.stop();
//            tts.shutdown();
//        }
//        super.onStop();
//    }

    @Override
    protected void onDestroy() {
        tts.shutdown();
        super.onDestroy();
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            oqSeraFalado = editTextPrincipal.getText().toString();
            if (oqSeraFalado.length() >= 1){
                if(count >= before){
                    oqSeraFalado = oqSeraFalado.substring(oqSeraFalado.length()-1);
                    vamosFalar();
                }
            }else{
                vamosFalar();
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public void vamosFalar(){
        String toSpeak = oqSeraFalado;
        tts.setPitch(1);
        tts.setSpeechRate(1);
        tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void alterarTelas(){
        if (linearLayoutDoEditTextEButton.getVisibility() == View.VISIBLE){
            linearLayoutDoEditTextEButton.setVisibility(View.INVISIBLE);
            linearLayoutDoTextViewDoFragment.setVisibility(View.VISIBLE);
            queTelaEstamos = 1;
        }else{
            linearLayoutDoEditTextEButton.setVisibility(View.VISIBLE);
            linearLayoutDoTextViewDoFragment.setVisibility(View.INVISIBLE);
            queTelaEstamos = 2;
        }
    }
}
