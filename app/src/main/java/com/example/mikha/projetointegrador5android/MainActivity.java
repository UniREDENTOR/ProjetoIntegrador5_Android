package com.example.mikha.projetointegrador5android;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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

    int contador;

    Button botaoDeFalar;
    Button botaoDeMudarImagem;

    String oqSeraFalado;
    String resposta;

    TextView resultadoPalavra;

    int queTelaEstamos;

    private FirebaseAuth.AuthStateListener mAuthListener;

    LinearLayout linearLayoutDoEditTextEButton;
    LinearLayout linearLayoutDoTextViewDoFragment;
    LinearLayout linearLayoutDaImagemDoFragment;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseRef;
    

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
        contador = 0;

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
                    if (contador+1 <= classeArrays.checarTamanhoArray()){
                        Log.v("contador: ",""+contador);
                        Log.v("tamanho do array: ",""+classeArrays.checarTamanhoArray());
                        linearLayoutDaImagemDoFragment.setBackgroundResource(classeArrays.getImagens(contador));
                        resposta = classeArrays.getRespostas(contador);
                        editTextPrincipal.getText().clear();
                    }else{
                        alterarTelas();
                        resultadoPalavra.setText("Fim do jogo! :D");
                    }
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
                    contador++;
                    String pontuaçao = String.valueOf(contador);
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    databaseRef = FirebaseDatabase.getInstance().getReference();
                    databaseRef.child(user.getUid()).child("pontuacao").setValue(pontuaçao);
                    botaoDeMudarImagem.setText("Avançar");
                } else {
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
            case R.id.categoria1:
                linearLayoutDaImagemDoFragment.setBackgroundResource(classeArrays.getImagens(contador));
                resposta = classeArrays.getRespostas(contador);
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
                pontuacao.putExtra("pontos", contador);
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
