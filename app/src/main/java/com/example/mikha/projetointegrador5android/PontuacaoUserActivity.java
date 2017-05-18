package com.example.mikha.projetointegrador5android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

import java.util.ArrayList;

public class PontuacaoUserActivity extends AppCompatActivity {

    PieChart graficoUser;
    int pontos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontuacao_user);
        graficoUser = (PieChart) findViewById(R.id.graficoUser);
        Intent intent = getIntent();
        pontos = intent.getIntExtra("pontos", 0);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(pontos, 0));

        PieDataSet dataset = new PieDataSet(entries, "pontuação");

        PieData data = new PieData(dataset);
        PieChart chart = new PieChart(this);
        setContentView(chart);
        chart.setData(data);
        chart.setContentDescription("pontução");

    }

}
