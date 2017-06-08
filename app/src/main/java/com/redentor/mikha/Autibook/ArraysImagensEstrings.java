package com.redentor.mikha.Autibook;

class ArraysImagensEstrings {

    private static final String[] respostasCor = {
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
    };

    private static final String[] respostasNumero = {
            "zero",
            "um",
            "dois",
            "três",
            "quatro",
            "cinco",
            "seis",
            "sete",
            "oito",
            "novo"

    };

    private static final String[] respostasObjetos = {
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

    private static final int[] imagensCor = {
            
            R.mipmap.catcor1,
            R.mipmap.catcor2,
            R.mipmap.catcor3,
            R.mipmap.catcor4,
            R.mipmap.catcor5,
            R.mipmap.catcor6,
            R.mipmap.catcor7,
            R.mipmap.catcor8,
            R.mipmap.catcor9,
            R.mipmap.catcor10
    };

    private static final int[] imagensNumero = {
            R.mipmap.catnum1,
            R.mipmap.catnum2,
            R.mipmap.catnum3,
            R.mipmap.catnum4,
            R.mipmap.catnum5,
            R.mipmap.catnum6,
            R.mipmap.catnum7,
            R.mipmap.catnum8,
            R.mipmap.catnum9
    };

    private static final int[] imagensObjetos = {
            R.mipmap.catobj1,
            R.mipmap.catobj2,
            R.mipmap.catobj3,
            R.mipmap.catobj4,
            R.mipmap.catobj5,
            R.mipmap.catobj6
    };

    public String getRespostasCor(int count) {
        return respostasCor[count];
    }

    public String getRespostasNumero(int count) {
        return respostasNumero[count];
    }

    public String getRespostasObjetos(int count) {
        return respostasObjetos[count];
    }

    public int getImagensCor(int count) {
        return imagensCor[count];
    }

    public int getImagensNumero(int count) {
        return imagensNumero[count];
    }

    public int getImagensObjetos(int count) {
        return imagensObjetos[count];
    }


    public int checarTamanhoArray(int id){
        int tamanho = 0;
        if (id==1){
            tamanho = imagensObjetos.length;
        }else if (id==2){
            tamanho = imagensCor.length;
        }else if (id==3){
            tamanho = imagensNumero.length;
        }
        return tamanho;
    }
}
