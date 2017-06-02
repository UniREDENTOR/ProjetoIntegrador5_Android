package com.example.mikha.projetointegrador5android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ArraysImagensEstrings classeArrays = new ArraysImagensEstrings();

    TextToSpeech tts;

    EditText editTextPrincipal;

    int contadorCor, contadorObjeto, contadorNumero, queTelaEstamos;

    Button botaoDeFalar, botaoDeMudarImagem;

    String oqSeraFalado, resposta, pontuacaoObjeto;

    DrawerLayout drawer;

    TextView resultadoPalavra;

    private FirebaseAuth.AuthStateListener mAuthListener;

    ConstraintLayout linearLayoutDoEditTextEButton, linearLayoutDoTextViewDoFragment;
    LinearLayout linearLayoutDaImagemDoFragment;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseRef;

    FirebaseUser user;

    Toolbar toolbar;

    SharedPreferences sharedPreferences;

    int id, contadorObjeto2;

    private View.OnClickListener onClickListener;

    NavigationView navigationView;

    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPrincipal = (EditText) findViewById(R.id.EditTextEntrada);
        botaoDeFalar = (Button) findViewById(R.id.ButtonTextToSpeech);
        botaoDeMudarImagem = (Button) findViewById(R.id.ButtonTextView);
        resultadoPalavra = (TextView) findViewById(R.id.TextViewTesteFragment);
        linearLayoutDaImagemDoFragment = (LinearLayout) findViewById(R.id.linearlayoutimagem);
        linearLayoutDoEditTextEButton = (ConstraintLayout) findViewById(R.id.linearLayoutDoEditTextEButton);
        linearLayoutDoTextViewDoFragment = (ConstraintLayout) findViewById(R.id.linearLayoutDoTextViewDoFragment);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.bringToFront();

        sharedPreferences = getSharedPreferences("VALUES", MODE_PRIVATE);
        int theme = sharedPreferences.getInt("THEME", 1);

        final Locale localeBR = new Locale("pt","BR");
        firebaseAuth = firebaseAuth.getInstance();
        contadorNumero = 0;
        contadorCor = 0;
        contadorObjeto = 0;
        databaseRef = FirebaseDatabase.getInstance().getReference();
        user = firebaseAuth.getCurrentUser();

        switch (theme){
            case 1: setTheme(R.style.AppTheme);
                break;
            case 2: setTheme(R.style.AppTheme2);
                break;
            case 3: setTheme(R.style.AppTheme3);
                break;
            case 4: setTheme(R.style.AppTheme4);
                break;
            case 5: setTheme(R.style.AppTheme5);
                break;
            case 6: setTheme(R.style.AppTheme6);
                break;
        }

        setSupportActionBar(toolbar);

        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
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

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment fragment = null;
                Intent intent = null;

        switch (id){
            case R.id.nav_home:
                setTitle("Home");
                intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_config:
                setTitle("Configurações");
                fragment = new Cores();
                trocaFragmento(fragment, R.id.container);
                return true;
            case R.id.nav_pontuacao:
                Intent pontuacao = new Intent(MainActivity.this, PontuacaoUserActivity.class);
                startActivity(pontuacao);
                return true;
            case R.id.nav_login:
                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
                return true;
            case R.id.nav_cadastro:
                Intent cadastroActivity = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(cadastroActivity);
                return true;
        }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


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
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void navigationBarStatusBar() {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Fix issues for KitKat setting Status Bar color primary
            if (Build.VERSION.SDK_INT >= 19) {
                TypedValue typedValue19 = new TypedValue();
                MainActivity.this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue19, true);
                final int color = typedValue19.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.toolbar);
                statusBar.setBackgroundColor(color);
            }

            // Fix issues for Lollipop, setting Status Bar color primary dark
            if (Build.VERSION.SDK_INT >= 21) {
                TypedValue typedValue21 = new TypedValue();
                MainActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue21, true);
                final int color = typedValue21.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.toolbar);
                statusBar.setBackgroundColor(color);
            }
        }
        // Fix landscape issues (only Lollipop)
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (Build.VERSION.SDK_INT >= 19) {
                TypedValue typedValue19 = new TypedValue();
                MainActivity.this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue19, true);
                final int color = typedValue19.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.toolbar);
                statusBar.setBackgroundColor(color);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                TypedValue typedValue = new TypedValue();
                MainActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
                final int color = typedValue.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.toolbar);
                statusBar.setBackgroundColor(color);
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
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

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void trocaFragmento(Fragment fragment, int id) {
        getSupportFragmentManager().beginTransaction().replace(id, fragment, getTitle().toString()).commit();
    }
}
