package com.example.mikha.projetointegrador5android.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mikha.projetointegrador5android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImagemFragment extends Fragment {

    LinearLayout linearlayoutimagemdofragment;
    private String respostaDaImagem;


    public ImagemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_imagem, container, false);
        linearlayoutimagemdofragment = (LinearLayout) view.findViewById(R.id.linearlayoutimagemdofragment);
        linearlayoutimagemdofragment.setBackgroundResource(R.drawable.ic_android_black_24dp);
        respostaDaImagem = "oi";


        return view;

    }

    public void setLinearlayoutimagemdofragment(int id){
        linearlayoutimagemdofragment.setBackgroundResource(id);
    }

    public String getRespostaDaImagem(){
        return this.respostaDaImagem;
    }

}
