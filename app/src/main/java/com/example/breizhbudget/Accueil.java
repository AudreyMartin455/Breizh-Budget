package com.example.breizhbudget;

import android.content.Intent;
import android.os.Bundle;

import com.example.breizhbudget.ui.budgets.BudgetsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Accueil extends AppCompatActivity {


    @BindView(R.id.addRow) Button buttonEvent;
    @BindView(R.id.button) Button buttonBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//Ligne pour le bouton Evenements
   /*     buttonEvent.setOnClickListener(new View.OnClickListener(){

            public void onClick(View V){
                Intent intent = new Intent(Accueil.this, EventActivity.class);
                startActivity(intent);
            }
        });*/

        buttonBudget.setOnClickListener(new View.OnClickListener(){

            public void onClick(View V){
                Intent intent = new Intent(Accueil.this, BudgetsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.burgermenu, menu);
        return true;
    }

}
