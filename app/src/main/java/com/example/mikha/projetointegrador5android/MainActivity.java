package com.example.mikha.projetointegrador5android;

import android.app.FragmentTransaction;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mikha.projetointegrador5android.Fragments.TesteFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech tts;
    EditText editTextPrincipal;
    Button botaoDeFalar;
    String oqSeraFalado;
    LinearLayout linearlayoutfragment;
    LinearLayout linearlayoutfragmenttextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextPrincipal = (EditText) findViewById(R.id.EditTextEntrada);
        botaoDeFalar = (Button) findViewById(R.id.ButtonTextToSpeech);
        linearlayoutfragment = (LinearLayout) findViewById(R.id.linearlayoutfragment);
        linearlayoutfragmenttextview = (LinearLayout) findViewById(R.id.linearlayoutfragmenttextview);
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
                TesteFragment testefragment = new TesteFragment();
                testefragment.setArguments(bundle);
                FragmentManager fm = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTrans = fm.beginTransaction();
                fragmentTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                //getSupportFragmentManager().beginTransaction().replace(R.id.LayoutMain, testefragment).commit();
                fragmentTrans.replace(R.id.linearlayoutfragmenttextview, testefragment);
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


            Log.e("count", count + "");
            Log.e("before", before + "");

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
        if (linearlayoutfragment.getVisibility() == View.VISIBLE){
            linearlayoutfragment.setVisibility(View.INVISIBLE);
            linearlayoutfragmenttextview.setVisibility(View.VISIBLE);
        }else{
            linearlayoutfragment.setVisibility(View.VISIBLE);
            linearlayoutfragmenttextview.setVisibility(View.INVISIBLE);
        }
    }
}
