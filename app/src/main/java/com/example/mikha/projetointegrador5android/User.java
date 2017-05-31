package com.example.mikha.projetointegrador5android;

import com.firebase.client.Firebase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    private String id;
    private String username;
    private String email;
    private String password;

    private String pontuacaoCor, pontuacaoNumero, pontuacaoObjeto;

    public User() {}

    public String getPontuacaoCor() {
        return pontuacaoCor;
    }

    public void setPontuacaoCor(String pontuacaoCor) {
        this.pontuacaoCor = pontuacaoCor;
    }

    public String getPontuacaoNumero() {
        return pontuacaoNumero;
    }

    public void setPontuacaoNumero(String pontuacaoNumero) {
        this.pontuacaoNumero = pontuacaoNumero;
    }

    public String getPontuacaoObjeto() {
        return pontuacaoObjeto;
    }

    public void setPontuacaoObjeto(String pontuacaoObjeto) {
        this.pontuacaoObjeto = pontuacaoObjeto;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }


    public User(String username, String email, String password, String pontuacaoCor, String pontuacaoNumero, String pontuacaoObjeto) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.pontuacaoCor = pontuacaoCor;
        this.pontuacaoObjeto = pontuacaoObjeto;
        this.pontuacaoNumero = pontuacaoNumero;
    }





}
