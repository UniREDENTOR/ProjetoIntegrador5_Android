package com.example.mikha.projetointegrador5android;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech tts;
    EditText editTextPrincipal;
    Button botaoDeFalar;
    String oqSeraFalado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPrincipal = (EditText) findViewById(R.id.EditTextEntrada);
        botaoDeFalar = (Button) findViewById(R.id.ButtonTextToSpeech);
        final Locale localeBR = new Locale("pt","BR");

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    tts.setLanguage(localeBR);
                }
            }
        });

        editTextPrincipal.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                keyCode = event.getKeyCode();
                if (keyCode == KeyEvent.KEYCODE_DEL){
                    return true;
                }else{
                    return false;
                }
            }
        });

        editTextPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onKey == false){
                    editTextPrincipal.addTextChangedListener(textWatcher);
                }

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
            if (oqSeraFalado.length() > 1){
                oqSeraFalado = oqSeraFalado.substring(oqSeraFalado.length()-1);
                vamosFalar();
            }else{
                vamosFalar();
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void vamosFalar(){
        String ToSpeak = oqSeraFalado;
        Toast.makeText(getApplicationContext(), ToSpeak, Toast.LENGTH_SHORT).show();
        tts.setPitch(1);
        tts.setSpeechRate(1);
        tts.speak(ToSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }
}
