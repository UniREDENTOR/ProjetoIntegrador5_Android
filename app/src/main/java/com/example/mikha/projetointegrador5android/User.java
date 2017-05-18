package com.example.mikha.projetointegrador5android;
import com.firebase.client.Firebase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    private String id;
    private String username;
    private String email;
    private String password;

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
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void saveDB(){
        Firebase firebase = FirebaseTest.getFirebase();
        firebase = firebase.child("users").child(getId());

        setPassword(null);
        setid(null);
        firebase.setValue(this);
    }


}
