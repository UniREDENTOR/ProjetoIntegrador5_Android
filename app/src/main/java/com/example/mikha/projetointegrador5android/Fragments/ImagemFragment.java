package com.example.mikha.projetointegrador5android.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mikha.projetointegrador5android.MainActivity;
import com.example.mikha.projetointegrador5android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImagemFragment extends Fragment implements MainActivity.PegarRespostaDaImagem{


    LinearLayout linearlayoutimagemdofragment;
    String respostaDaImagem;
    int catID;

    public ImagemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_imagem, container, false);
        linearlayoutimagemdofragment = (LinearLayout) view.findViewById(R.id.linearlayoutimagemdofragment);
        catID = this.getArguments().getInt("catID");
        escolherCategoria();

        return view;
    }


    public void escolherCategoria(){
        switch (catID){
            case R.id.categoria1:
                linearlayoutimagemdofragment.setBackgroundResource(R.drawable.ic_android_black_24dp);
                setRespostaDaImagem("oi");

        }
    }


    public void setLinearlayoutimagemdofragment(int id){
        linearlayoutimagemdofragment.setBackgroundResource(id);
    }

    public String getRespostaDaImagem(){
        return this.respostaDaImagem;
    }

    public void setRespostaDaImagem(String respostaDaImagem) {
        this.respostaDaImagem = respostaDaImagem;
    }

    @Override
    public String pegarResposta() {
        return this.respostaDaImagem;
    }
}
