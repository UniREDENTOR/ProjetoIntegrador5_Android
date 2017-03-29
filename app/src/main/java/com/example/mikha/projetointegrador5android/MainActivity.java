package com.example.mikha.projetointegrador5android;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech tts;
    EditText editTextPrincipal;
    Button botaoDeFalar;
    String oqSeraFalado;
//    boolean usuarioDeletouCaracter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPrincipal = (EditText) findViewById(R.id.EditTextEntrada);
        botaoDeFalar = (Button) findViewById(R.id.ButtonTextToSpeech);
//        usuarioDeletouCaracter = false;
        final Locale localeBR = new Locale("pt","BR");

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    tts.setLanguage(localeBR);
                }
            }
        });

//        editTextPrincipal.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                Log.e("key",keyCode+"");
//                Log.e("user",usuarioDeletouCaracter+"");
//                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN){
//                    usuarioDeletouCaracter = true;
//                    return usuarioDeletouCaracter;
//
//                }
//                return usuarioDeletouCaracter;
//
//
//            }
//        });

        editTextPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!usuarioDeletouCaracter){
                    editTextPrincipal.addTextChangedListener(textWatcher);
//                }else{
//                    Toast.makeText(getApplicationContext(), "O usuario apagou", Toast.LENGTH_SHORT).show();
//                }
            }
        });


        botaoDeFalar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oqSeraFalado = editTextPrincipal.getText().toString();
                vamosFalar();
            }
        });
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
            Log.e("count", count + "");
            Log.e("before", before + "");

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void vamosFalar(){
        String ToSpeak = oqSeraFalado;
        //Toast.makeText(getApplicationContext(), ToSpeak, Toast.LENGTH_SHORT).show();
        tts.setPitch(1);
        tts.setSpeechRate(1);
        tts.speak(ToSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }
}
