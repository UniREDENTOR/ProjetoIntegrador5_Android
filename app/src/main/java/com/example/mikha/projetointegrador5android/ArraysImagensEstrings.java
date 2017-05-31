package com.example.mikha.projetointegrador5android;

class ArraysImagensEstrings {

    private static final String[] respostas = {
            "Amarelo",
            "Azul",
            "Cinza",
            "Laranja",
            "Roxo",
            "Marrom",
            "Preto",
            "Rosa",
            "Verde",
            "Vermelho",
            //numeros
            "zero",
            "um",
            "dois",
            "três",
            "quatro",
            "cinco",
            "seis",
            "sete",
            "oito",
            "novo",
            //objetos
            "árvore",
            "colher",
            "garfo",
            "faca",
            "escova de dente",
            "carro",
            "motocicleta",
            "rádio",
            "bola",
            "casa"
    };

    private static final int[] imagens = {
            
            R.mipmap.catcor1,
            R.mipmap.catcor2,
            R.mipmap.catcor3,
            R.mipmap.catcor4,
            R.mipmap.catcor5,
            R.mipmap.catcor6,
            R.mipmap.catcor7,
            R.mipmap.catcor8,
            R.mipmap.catcor9,
            R.mipmap.catcor10,
            R.mipmap.catnum1,
            R.mipmap.catnum2,
            R.mipmap.catnum3,
            R.mipmap.catnum4,
            R.mipmap.catnum5,
            R.mipmap.catnum6,
            R.mipmap.catnum7,
            R.mipmap.catnum8,
            R.mipmap.catnum9,
            R.mipmap.catobj1,
            R.mipmap.catobj2,
            R.mipmap.catobj3,
            R.mipmap.catobj4,
            R.mipmap.catobj5,
            R.mipmap.catobj6

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
