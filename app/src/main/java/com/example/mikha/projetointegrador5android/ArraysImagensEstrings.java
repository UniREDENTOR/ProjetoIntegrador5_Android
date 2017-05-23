package com.example.mikha.projetointegrador5android;

import com.example.mikha.projetointegrador5android.R;

class ArraysImagensEstrings {

    private static final String[] respostas = {
            "camera",
            "galeria",
    };

    private static final int[] imagens = {
            
            R.mipmap.catCor1,
            R.mipmap.catCor2,
            R.mipmap.catCor3,
            R.mipmap.catCor4,
            R.mipmap.catCor5,
            R.mipmap.catCor6,
            R.mipmap.catCor7,
            R.mipmap.catCor8,
            R.mipmap.catCor9,
            R.mipmap.catCor10,
            R.mipmap.catNum1,
            R.mipmap.catNum2,
            R.mipmap.catNum3,
            R.mipmap.catNum4,
            R.mipmap.catNum5,
            R.mipmap.catNum6,
            R.mipmap.catNum7,
            R.mipmap.catNum8,
            R.mipmap.catNum9,
            R.mipmap.catObj1,
            R.mipmap.catObj2,
            R.mipmap.catObj3,
            R.mipmap.catObj4,
            R.mipmap.catObj5,
            R.mipmap.catObj6

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
