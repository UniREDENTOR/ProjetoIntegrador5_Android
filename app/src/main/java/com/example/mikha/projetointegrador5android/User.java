package com.example.mikha.projetointegrador5android;

import com.firebase.client.Firebase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    private String id;
    private String username;
    private String email;
    private String password;
    private String pontuacao;

    public User() {}

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

    public String getPontuacao() {
        return this.pontuacao;
    }

    public void setPontuacao(String pontuacao) {
        this.pontuacao = pontuacao;
    }

    public User(String username, String email, String password, String pontuacao) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.pontuacao = pontuacao;
    }





}
