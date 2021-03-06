package com.redentor.mikha.Autibook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.redentor.mikha.Autibook.fragments.HelpFragment;
import com.redentor.mikha.Autibook.fragments.HelpFragment.OnFragmentInteractionListener;

import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    Cores cores = new Cores ();

    ArraysImagensEstrings classeArrays = new ArraysImagensEstrings();

    TextToSpeech tts;

    EditText editTextPrincipal;

    int contadorCor, contadorObjeto, contadorNumero, queTelaEstamos, id;

    Button botaoDeFalar, botaoDeMudarImagem;

    String oqSeraFalado, resposta, userName;

    DrawerLayout drawer;

    TextView resultadoPalavra, textheaderEmail;

    private FirebaseAuth.AuthStateListener mAuthListener;

    ConstraintLayout linearLayoutDoEditTextEButton, linearLayoutDoTextViewDoFragment, container;
    LinearLayout linearLayoutDaImagemDoFragment, linear_container;

    FirebaseAuth auth;
    DatabaseReference databaseRef, userDB;

    FirebaseUser user;

    Toolbar toolbar;

    SharedPreferences sharedPreferences;

    NavigationView navigationView;

    ActionBarDrawerToggle toggle;

    FloatingActionButton botaoAjuda;

    View header;

    ProgressDialog prog;

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
        botaoAjuda = (FloatingActionButton) findViewById(R.id.helpButton);
        container = (ConstraintLayout) findViewById(R.id.container);
        linear_container = (LinearLayout) findViewById(R.id.linear_container);
        prog = new ProgressDialog(getWindow().getContext());

        navigationView.bringToFront();

        header = navigationView.getHeaderView(0);

        textheaderEmail = (TextView)header.findViewById(R.id.textViewEmailHeader);

        sharedPreferences = getSharedPreferences("VALUES", MODE_PRIVATE);
        final int theme = sharedPreferences.getInt("THEME", 1);

        final Locale localeBR = new Locale("pt","BR");
        auth = auth.getInstance();
        contadorNumero = getIntent().getIntExtra("pontuacaoNumero", 0);
        contadorCor = getIntent().getIntExtra("pontuacaoCor", 0);
        contadorObjeto = getIntent().getIntExtra("pontuacaoObjeto", 0);
        databaseRef = FirebaseDatabase.getInstance().getReference();
        user = auth.getCurrentUser();
        userDB = databaseRef.child(user.getUid());


        textheaderEmail.setText(getIntent().getStringExtra("userEmail"));

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


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
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

        botaoAjuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.tutorialanim);
                final Fragment fragment = new HelpFragment();
                linear_container.startAnimation(animation);
                trocaFragmento(fragment, R.id.container);


                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        linearLayoutDoTextViewDoFragment.setVisibility(LinearLayout.INVISIBLE);
                        linearLayoutDoEditTextEButton.setVisibility(LinearLayout.INVISIBLE);
//                        linear_container.setVisibility(LinearLayout.VISIBLE);
                        botaoAjuda.hide();
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        alterarTelas();
                        getSupportFragmentManager().popBackStackImmediate();
                        linear_container.clearAnimation();
                        botaoAjuda.show();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

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
                        }else{
                            editTextPrincipal.setText("fim");
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
                }else if (botaoDeMudarImagem.getText().equals("Voltar")){
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                alterarTelas();
            }
        });

        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                    String pontuacaoObjeto = map.get("pontuacaoObjeto");
                    String pontuacaoCor = map.get("pontuacaoCor");
                    String pontuacaoNumero = map.get("pontuacaoNumero");

                    contadorObjeto = Integer.parseInt(pontuacaoObjeto);
                    contadorCor = Integer.parseInt(pontuacaoCor);
                    contadorNumero = Integer.parseInt(pontuacaoNumero);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
                        if (contadorObjeto != classeArrays.checarTamanhoArray(id)){
                            contadorObjeto++;
                            String pontuacao = String.valueOf(contadorObjeto);
                            databaseRef.child(user.getUid()).child("pontuacaoObjeto").setValue(pontuacao);
                        }
                    }else if (id==2){
                        if (contadorCor != classeArrays.checarTamanhoArray(id)){
                            contadorCor++;
                            String pontuacao = String.valueOf(contadorCor);
                            databaseRef.child(user.getUid()).child("pontuacaoCor").setValue(pontuacao);
                        }
                    }else if (id==3){
                        if (contadorNumero != classeArrays.checarTamanhoArray(id)){
                            contadorNumero++;
                            String pontuacao = String.valueOf(contadorNumero);
                            databaseRef.child(user.getUid()).child("pontuacaoNumero").setValue(pontuacao);
                        }
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
                intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_reset_fases:
                drawer.closeDrawers();
                mensagemResetFase();
                databaseRef.child(user.getUid()).child("pontuacaoObjeto").setValue("0");
                databaseRef.child(user.getUid()).child("pontuacaoCor").setValue("0");
                databaseRef.child(user.getUid()).child("pontuacaoNumero").setValue("0");
                prog.hide();
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

    private void mensagemResetFase() {
        prog.setMessage("Resetando a fase...");
        prog.setIndeterminate(true);
        prog.show();
        Toast.makeText(getApplicationContext(), "Fases resetadas", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager().popBackStackImmediate();
        linear_container.setVisibility(LinearLayout.INVISIBLE);
        auth.addAuthStateListener(mAuthListener);
        atualizarBD();
    }

    public void atualizarBD(){
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                    String pontuacaoObjeto = map.get("pontuacaoObjeto");
                    String pontuacaoCor = map.get("pontuacaoCor");
                    String pontuacaoNumero = map.get("pontuacaoNumero");
                    userName = map.get("username");

                    contadorObjeto = Integer.parseInt(pontuacaoObjeto);
                    contadorCor = Integer.parseInt(pontuacaoCor);
                    contadorNumero = Integer.parseInt(pontuacaoNumero);

                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public boolean testeResultado() {
        try{
            return resposta.equalsIgnoreCase(oqSeraFalado);
        }catch (Throwable e){
            e.printStackTrace();
        }
        return false;
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
                id = 1;
                if (contadorObjeto != classeArrays.checarTamanhoArray(id)){
                    linearLayoutDaImagemDoFragment.setBackgroundResource(classeArrays.getImagensObjetos(contadorObjeto));
                    resposta = classeArrays.getRespostasObjetos(contadorObjeto);
                    return true;
                }else{
                    editTextPrincipal.setVisibility(EditText.INVISIBLE);
                    botaoDeFalar.setVisibility(Button.INVISIBLE);
                    botaoDeMudarImagem.setText("Voltar");
                    linearLayoutDaImagemDoFragment.setVisibility(LinearLayout.VISIBLE);
                    linearLayoutDaImagemDoFragment.setBackgroundResource(R.mipmap.parabens);
                    linearLayoutDoTextViewDoFragment.setVisibility(LinearLayout.VISIBLE);
                    resultadoPalavra.setText("Você chegou no fim desta fase. PARABÉNS!");
                    return true;
                }
            case R.id.categoria2Cores:
                id = 2;
                if (contadorCor != classeArrays.checarTamanhoArray(id)){
                    linearLayoutDaImagemDoFragment.setBackgroundResource(classeArrays.getImagensCor(contadorCor));
                    resposta = classeArrays.getRespostasCor(contadorCor);
                    return true;
                }else{
                    editTextPrincipal.setVisibility(EditText.INVISIBLE);
                    botaoDeFalar.setVisibility(Button.INVISIBLE);
                    botaoDeMudarImagem.setText("Voltar");
                    linearLayoutDaImagemDoFragment.setVisibility(LinearLayout.VISIBLE);
                    linearLayoutDaImagemDoFragment.setBackgroundResource(R.mipmap.parabens);
                    linearLayoutDoTextViewDoFragment.setVisibility(LinearLayout.VISIBLE);
                    resultadoPalavra.setText("Você chegou no fim desta fase. PARABÉNS!");
                    return true;
                }
            case R.id.categoria3Numeros:
                id = 3;
                if (contadorNumero != classeArrays.checarTamanhoArray(id)){
                    linearLayoutDaImagemDoFragment.setBackgroundResource(classeArrays.getImagensNumero(contadorNumero));
                    resposta = classeArrays.getRespostasNumero(contadorNumero);
                    return true;
                }else{
                    editTextPrincipal.setVisibility(EditText.INVISIBLE);
                    botaoDeFalar.setVisibility(Button.INVISIBLE);
                    botaoDeMudarImagem.setText("Voltar");
                    linearLayoutDaImagemDoFragment.setVisibility(LinearLayout.VISIBLE);
                    linearLayoutDaImagemDoFragment.setBackgroundResource(R.mipmap.parabens);
                    linearLayoutDoTextViewDoFragment.setVisibility(LinearLayout.VISIBLE);
                    resultadoPalavra.setText("Você chegou no fim desta fase. PARABÉNS!");
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    public void onBackPressed(){
        try {
            linear_container.getAnimation().cancel();
        }catch (Throwable e){
            e.printStackTrace();
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (queTelaEstamos == 1) {
            alterarTelas();
        }
        super.onBackPressed();
    }

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

    private void trocaFragmento(Fragment fragment, int id) {
        getSupportFragmentManager().beginTransaction().replace(id, fragment, getTitle().toString()).addToBackStack("back").commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
