package com.redentor.mikha.Autibook;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.Firebase;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class PontuacaoUserActivity extends AppCompatActivity {

    PieChart graficoUser;

    FirebaseUser user;
    FirebaseAuth auth;
    DatabaseReference databaseRef, userDB;

    ProgressDialog progressDialog;

    ArrayList<PieEntry> entries;

    String pontuacaoObjeto, pontuacaoCor, pontuacaoNumero;

    ArrayList<String> pontos = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_pontuacao_user);

        auth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();
        user = auth.getCurrentUser();
        userDB = databaseRef.child(user.getUid());
        
        progressDialog = new ProgressDialog(this);

        graficoUser = (PieChart) findViewById(R.id.graficoUser);

        progressDialog.setMessage("Buscando Pizza...");
        progressDialog.getProgress();
        progressDialog.show();
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


        userDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();

                if (map != null){
                    pontuacaoObjeto = map.get("pontuacaoObjeto");
                    pontuacaoCor = map.get("pontuacaoCor");
                    pontuacaoNumero = map.get("pontuacaoNumero");
                }else {
                    pontuacaoObjeto = "0";
                    pontuacaoCor = "0";
                    pontuacaoNumero = "0";
                }
                pontos.add(pontuacaoCor);
                pontos.add(pontuacaoNumero);
                pontos.add(pontuacaoObjeto);

                progressDialog.incrementProgressBy(50);
                progressDialog.getProgress();

                entries = new ArrayList<>();
                if (!pontos.get(0).equals("0")){
                    entries.add(new PieEntry(Integer.parseInt(pontos.get(0)), "cor"));
                }
                if (!pontos.get(1).equals("0")){
                    entries.add(new PieEntry(Integer.parseInt(pontos.get(1)), "numero"));
                }
                if (!pontos.get(2).equals("0")){
                    entries.add(new PieEntry(Integer.parseInt(pontos.get(2)), "objeto"));
                }

                PieDataSet dataset = new PieDataSet(entries, "pontuações");

                ArrayList<Integer> colors = new ArrayList<Integer>();

                for (int c : ColorTemplate.VORDIPLOM_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.JOYFUL_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.COLORFUL_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.LIBERTY_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.PASTEL_COLORS)
                    colors.add(c);

                colors.add(ColorTemplate.getHoloBlue());

                dataset.setColors(colors);
                dataset.setValueTextSize(30);

                PieData data = new PieData(dataset);

                PieChart chart = new PieChart(getApplicationContext());
                setContentView(chart);
                chart.setData(data);
                chart.setEntryLabelColor(R.color.colorPrimaryDark);
                chart.setEntryLabelTextSize(15);
                chart.setEntryLabelTypeface(Typeface.SANS_SERIF);
                chart.setContentDescription("pontuações");
                chart.setCenterText("Pontuações");
                chart.setCenterTextSize(15);


                progressDialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
