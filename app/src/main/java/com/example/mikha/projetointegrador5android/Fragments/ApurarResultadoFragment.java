package com.example.mikha.projetointegrador5android.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mikha.projetointegrador5android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApurarResultadoFragment extends Fragment {

    private TextView resultadoPalavra;
    String palavraDigitada;


    public ApurarResultadoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apurar_resultado, container, false);
        resultadoPalavra = (TextView) view.findViewById(R.id.TextViewTesteFragment);
        palavraDigitada = this.getArguments().getString("oqSeraFalado");
        resultadoPalavra.setText(palavraDigitada);
        return view;
    }


}
