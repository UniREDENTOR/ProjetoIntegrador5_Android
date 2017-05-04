package com.example.mikha.projetointegrador5android;

import android.app.FragmentTransaction;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.mikha.projetointegrador5android.Fragments.ApurarResultadoFragment;
import com.example.mikha.projetointegrador5android.Fragments.ImagemFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    PegarRespostaDaImagem callback;
    public ApurarResultadoFragment fragmentApurarResultado = new ApurarResultadoFragment();

    TextToSpeech tts;
    EditText editTextPrincipal;

    Button botaoDeFalar;
    String oqSeraFalado;

    LinearLayout linearLayoutDoEditTextEButton;
    LinearLayout linearLayoutDoTextViewDoFragment;
    LinearLayout linearLayoutDaImagemDoFragment;


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
                String resposta = callback.pegarResposta();
                bundle.putString("oqSeraFalado", textoParaFragment);
                bundle.putString("resposta", resposta);
                fragmentApurarResultado.setArguments(bundle);
                oqSeraFalado = textoParaFragment;
                vamosFalar();
                alterarTelas();
                chamarFragmento().replace(R.id.linearLayoutDoTextViewDoFragment, fragmentApurarResultado).addToBackStack("fragment").commit();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.itensdomenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ImagemFragment imagemFrag = new ImagemFragment();
        callback = imagemFrag;
        switch (item.getItemId()) {
            case R.id.categoria1:
                Bundle catID = new Bundle();
                catID.putInt("catID",item.getItemId());
                imagemFrag.setArguments(catID);
                chamarFragmento().replace(R.id.linearlayoutimagem, imagemFrag).addToBackStack("fragment").commit();
                return true;
//            case R.id.help:
//                showHelp();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void setLinearLayoutDaImagemDoFragment(int background) {
        LinearLayout linearimagem = this.linearLayoutDaImagemDoFragment;
        linearimagem.setBackgroundResource(background);
    }


    public android.support.v4.app.FragmentTransaction chamarFragmento(){
        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTrans = fm.beginTransaction();
        fragmentTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        return fragmentTrans;

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

    public interface PegarRespostaDaImagem {
        String pegarResposta();

    }

}
