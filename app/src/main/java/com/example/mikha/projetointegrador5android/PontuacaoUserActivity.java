package com.example.mikha.projetointegrador5android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.Firebase;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
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
    int pontos;

    FirebaseUser user;
    FirebaseAuth auth;
    DatabaseReference databaseRef, userDB;

    ArrayList<PieEntry> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_pontuacao_user);

        auth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();
        user = auth.getCurrentUser();
        userDB = databaseRef.child(user.getUid());

        graficoUser = (PieChart) findViewById(R.id.graficoUser);

        userDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();

                Log.v("map", map+"");

                String username = map.get("username");
                String password = map.get("password");
                String email = map.get("email");
                String pontuacao = map.get("pontuacao");

                pontos = Integer.valueOf(pontuacao);

                Log.v("pontos: ",pontos+"");
                Log.v("nome: ",email+"");

                entries = new ArrayList<>();
                entries.add(new PieEntry(pontos, 0));

                PieDataSet dataset = new PieDataSet(entries, "pontuação");

                PieData data = new PieData(dataset);
                PieChart chart = new PieChart(getApplicationContext());
                setContentView(chart);
                chart.setData(data);
                chart.setContentDescription("pontução");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }

}
