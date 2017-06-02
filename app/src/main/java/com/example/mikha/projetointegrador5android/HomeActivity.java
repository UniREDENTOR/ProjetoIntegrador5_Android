package com.example.mikha.projetointegrador5android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button vamosComecar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        vamosComecar = (Button) findViewById(R.id.buttonVamosComecar);

        vamosComecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(main);
            }
        });
    }





}
