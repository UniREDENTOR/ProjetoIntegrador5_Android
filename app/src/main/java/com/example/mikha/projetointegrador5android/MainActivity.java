package com.example.mikha.projetointegrador5android;

import android.app.FragmentTransaction;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mikha.projetointegrador5android.Fragments.ApurarResultadoFragment;
import com.example.mikha.projetointegrador5android.Fragments.ImagemFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech tts;
    EditText editTextPrincipal;
    Button botaoDeFalar;
    String oqSeraFalado;
    String respDaImagem;

    LinearLayout linearLayoutDoEditTextEButton;
    LinearLayout linearLayoutDoTextViewDoFragment;
    LinearLayout linearLayoutDaImagemDoFragment;

    FragmentManager fm = getSupportFragmentManager();
    android.support.v4.app.FragmentTransaction fragmentTrans = fm.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextPrincipal = (EditText) findViewById(R.id.EditTextEntrada);
        botaoDeFalar = (Button) findViewById(R.id.ButtonTextToSpeech);
        linearLayoutDaImagemDoFragment = (LinearLayout) findViewById(R.id.linearlayoutimagem);
        linearLayoutDoEditTextEButton = (LinearLayout) findViewById(R.id.linearLayoutDoEditTextEButton);
        linearLayoutDoTextViewDoFragment = (LinearLayout) findViewById(R.id.linearLayoutDoTextViewDoFragment);
        final Locale localeBR = new Locale("pt","BR");

        fragmentTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        final ImagemFragment imagemFrag = new ImagemFragment();
        fragmentTrans.replace(R.id.linearlayoutimagem, imagemFrag).addToBackStack("fragment").commit();


        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
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

        botaoDeFalar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle txtfragment = new Bundle();
                Bundle resposta = new Bundle();
                Bundle bundle = new Bundle();
                String textoParaFragment = editTextPrincipal.getText().toString();

                imagemFrag.respostaDaImagem = respDaImagem;
                Log.v("resposta", respDaImagem);

                txtfragment.putString("oqSeraFalado", textoParaFragment);
                resposta.putString("resposta", respDaImagem);

                bundle.putBundle("bundle_oqSeraFalado", txtfragment);
                bundle.putBundle("bundle_resposta", resposta);

                ApurarResultadoFragment fragmentApurarResultado = new ApurarResultadoFragment();
                fragmentApurarResultado.setArguments(bundle);
                //getSupportFragmentManager().beginTransaction().replace(R.id.LayoutMain, testefragment).commit();
                fragmentTrans.replace(R.id.linearLayoutDoTextViewDoFragment, fragmentApurarResultado);
                fragmentTrans.addToBackStack("frag");
                alterarTelas();
                oqSeraFalado = textoParaFragment;
                vamosFalar();
                fragmentTrans.commit();

            }
        });
    }

    @Override
    public void onBackPressed(){
        if(getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }
        alterarTelas();
    }

    @Override
    public void onPause(){
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            oqSeraFalado = editTextPrincipal.getText().toString();
            if (oqSeraFalado.length() >= 1){
                if(count < before){
                    Toast.makeText(getApplicationContext(), "Letra apagada", Toast.LENGTH_SHORT).show();
                }else{
                    oqSeraFalado = oqSeraFalado.substring(oqSeraFalado.length()-1);
                    vamosFalar();
                }

            }else{
                vamosFalar();
            }
//            Log.e("count", count + "");
//            Log.e("before", before + "");
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
        }else{
            linearLayoutDoEditTextEButton.setVisibility(View.VISIBLE);
            linearLayoutDoTextViewDoFragment.setVisibility(View.INVISIBLE);
        }
    }
}
