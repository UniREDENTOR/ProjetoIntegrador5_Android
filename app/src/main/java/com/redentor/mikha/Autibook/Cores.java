package com.redentor.mikha.Autibook;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cuboid.cuboidcirclebutton.CuboidButton;

import static android.content.Context.MODE_PRIVATE;

public class Cores extends Fragment {

    SharedPreferences sharedPreferences;

    CuboidButton buttonLaranja;
    CuboidButton buttonYellow;
    CuboidButton buttonGreen;
    CuboidButton buttonBlue;
    CuboidButton buttonRed;
    CuboidButton buttonPink;
    private int theme;

    public Cores() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedPreferences = getActivity ().getSharedPreferences("VALUES", MODE_PRIVATE);
        int theme = sharedPreferences.getInt("THEME", 1);

        switch (theme){
            case 1: setTheme(R.style.AppTheme);
                break;
            case 2: setTheme(R.style.AppTheme2);
                break;
            case 3: setTheme(R.style.AppTheme3);
                break;
            case 4: setTheme(R.style.AppTheme4);
                break;
            case 5: setTheme(R.style.AppTheme5);
                break;
            case 6: setTheme(R.style.AppTheme6);
                break;

        }
        return inflater.inflate(R.layout.fragment_cores, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated (savedInstanceState);
        navigationBarStatusBar();

        setMyTheme();
    }

    private void setMyTheme() {

        buttonBlue = (CuboidButton) getView ().findViewById(R.id.buttonBlue);
        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putInt("THEME",1).apply();
                getActivity ().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Cores (), getActivity ().getTitle().toString()).commit();
                Intent intent = new Intent(getActivity (), MainActivity.class);
                startActivity(intent);

            }
        });


        buttonLaranja = (CuboidButton) getView ().findViewById(R.id.buttonLaranja);
        buttonLaranja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putInt("THEME",2).apply();
                Intent intent = new Intent(getActivity (), MainActivity.class);
                startActivity(intent);

            }
        });


        buttonYellow = (CuboidButton) getView ().findViewById(R.id.buttonYellow);
        buttonYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putInt("THEME",3).apply();
                Intent intent = new Intent(getActivity (), MainActivity.class);
                startActivity(intent);
            }
        });

        buttonGreen = (CuboidButton) getView ().findViewById(R.id.buttonGreen);
        buttonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putInt("THEME",4).apply();
                Intent intent = new Intent(getActivity (), MainActivity.class);
                startActivity(intent);
            }
        });

        buttonRed = (CuboidButton) getView ().findViewById(R.id.buttonRed);
        buttonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putInt("THEME",5).apply();
                Intent intent = new Intent(getActivity (), MainActivity.class);
                startActivity(intent);
            }
        });


        buttonPink = (CuboidButton) getView ().findViewById(R.id.buttonPink);
        buttonPink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putInt("THEME",6).apply();
                Intent intent = new Intent(getActivity (), MainActivity.class);
                startActivity(intent);
            }
        });

    };



    public void navigationBarStatusBar() {


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Fix issues for KitKat setting Status Bar color primary
            if (Build.VERSION.SDK_INT >= 19) {
                TypedValue typedValue19 = new TypedValue();
                ((AppCompatActivity) getContext()).setTheme(R.style.AppTheme);
                final int color = typedValue19.data;
                Toolbar toolbar = (Toolbar) ((AppCompatActivity) getContext()).findViewById(R.id.toolbar);
                toolbar.setBackgroundColor (color);

            }

            // Fix issues for Lollipop, setting Status Bar color primary dark
            if (Build.VERSION.SDK_INT >= 21) {
                ((AppCompatActivity) getContext()).setTheme(R.style.AppTheme);

                Toolbar toolbar = (Toolbar) ((AppCompatActivity) getContext()).findViewById(R.id.toolbar);

                //toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
                //comentar uma das duas

                /*
                TypedValue typedValue21 = new TypedValue();
                MainActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue21, true);
                final int color = typedValue21.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setBackgroundColor(color);
                */
            }
        }

        // Fix landscape issues (only Lollipop)
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ((AppCompatActivity) getContext()).setTheme(R.style.AppTheme);

            Toolbar toolbar = (Toolbar) ((AppCompatActivity) getContext()).findViewById(R.id.toolbar);
           // if (Build.VERSION.SDK_INT >= 19) {
             //   TypedValue typedValue19 = new TypedValue();
              //  MainActivity.this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue19, true);
              //  final int color = typedValue19.data;
               // FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
               // statusBar.setBackgroundColor(color);
            }
            if (Build.VERSION.SDK_INT >= 21) {


                ((AppCompatActivity) getContext()).setTheme(R.style.AppTheme);

                Toolbar toolbar = (Toolbar) ((AppCompatActivity) getContext()).findViewById(R.id.toolbar);

            }
        }




    public void setTheme(int theme) {
        this.theme = theme;
    }


    public int getTheme() {
        return theme;
    }



}
