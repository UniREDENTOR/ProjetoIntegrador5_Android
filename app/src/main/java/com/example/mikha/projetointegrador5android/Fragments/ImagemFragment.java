package com.example.mikha.projetointegrador5android.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mikha.projetointegrador5android.MainActivity;
import com.example.mikha.projetointegrador5android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImagemFragment extends Fragment implements MainActivity.PegarRespostaDaImagem{

    RespostaCorreta callbackResposta;

    LinearLayout linearlayoutimagemdofragment;
    String respostaDaImagem;
    int catID;
    MainActivity mainActivity = new MainActivity();

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

    public static final String[] respostas = {
            "camera",
    };

    public static final int[] imagens = {
            R.drawable.ic_menu_camera,
    };

    public boolean verificaçao() {
        if (callbackResposta.verificarAresposta()) {
            Toast.makeText(getContext(), "resposta correta", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(getContext(), "resposta falsa", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public void escolherCategoria(){
        callbackResposta = mainActivity.fragmentApurarResultado;
        int i;
        switch (catID){
            case R.id.categoria1:
                for(i = 0; i < respostas.length; i++){
                    setLinearlayoutimagemdofragment(imagens[i]);
                    setRespostaDaImagem(respostas[i]);

                    if (mainActivity.isoBotaoFoiApertado()){
                        if (!verificaçao()){
                            Toast.makeText(getContext(), "resposta errada!!", Toast.LENGTH_SHORT).show();
                        }
                    }



                    //Esperar o retorno da variável "aRespostaEstaCorreta" contida no fragmento "ApurarResultadoFragment"
                    //o callback para pegar esta varíavel está funcionando, o problema é que preciso de um gatilho que pegará
                    //a variável desejada apenas quando a verificação da resposta (que é feita dentro do fragmento "ApurarResultadoFragment")
                    //estiver concluída. Daí eu poderia jogar esta verificação dentro de uma verificação (já que ela é boolean) para continuar
                    //o "for".
                }
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

    public interface RespostaCorreta {
        boolean verificarAresposta();
    }
}
