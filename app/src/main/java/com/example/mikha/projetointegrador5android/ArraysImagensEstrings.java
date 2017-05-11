package com.example.mikha.projetointegrador5android;

import com.example.mikha.projetointegrador5android.R;

class ArraysImagensEstrings {

    private static final String[] respostas = {
            "camera",
            "galeria",
    };

    private static final int[] imagens = {
            R.drawable.ic_menu_camera,
            R.drawable.ic_menu_gallery
    };

    public int getImagens(int count) {
        return imagens[count];
    }

    public String getRespostas(int count) {
        return respostas[count];
    }

    public int checarTamanhoArray(){
        return imagens.length;
    }
}
