package com.example.mikha.projetointegrador5android;

import android.app.FragmentTransaction;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mikha.projetointegrador5android.Fragments.ApurarResultadoFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech tts;
    EditText editTextPrincipal;
    Button botaoDeFalar;
    String oqSeraFalado;
    LinearLayout linearLayoutDoEditTextEButton;
    LinearLayout linearLayoutDoTextViewDoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextPrincipal = (EditText) findViewById(R.id.EditTextEntrada);
        botaoDeFalar = (Button) findViewById(R.id.ButtonTextToSpeech);
        linearLayoutDoEditTextEButton = (LinearLayout) findViewById(R.id.linearLayoutDoEditTextEButton);
        linearLayoutDoTextViewDoFragment = (LinearLayout) findViewById(R.id.linearLayoutDoTextViewDoFragment);
        final Locale localeBR = new Locale("pt","BR");

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
                Bundle bundle = new Bundle();
                String textoParaFragment = editTextPrincipal.getText().toString();
                bundle.putString("oqSeraFalado", textoParaFragment);
                ApurarResultadoFragment testefragment = new ApurarResultadoFragment();
                testefragment.setArguments(bundle);
                FragmentManager fm = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTrans = fm.beginTransaction();
                fragmentTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                //getSupportFragmentManager().beginTransaction().replace(R.id.LayoutMain, testefragment).commit();
                fragmentTrans.replace(R.id.linearLayoutDoTextViewDoFragment, testefragment);
                fragmentTrans.addToBackStack("fragment");
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
