package com.example.breizhbudget;

import android.content.Intent;
import android.os.Bundle;

import com.example.breizhbudget.ui.budgets.BudgetsActivity;
import com.example.breizhbudget.ui.dette.DettePretActivity;
import com.example.breizhbudget.ui.event.EventActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Accueil extends AppCompatActivity {


    @BindView(R.id.buttonEvent) Button buttonEvent;
    @BindView(R.id.buttonBudget) Button buttonBudget;
    @BindView(R.id.buttonDette) Button buttonDette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//Ligne pour le bouton Evenements
        buttonEvent.setOnClickListener(new View.OnClickListener(){

            public void onClick(View V){
                Intent intent = new Intent(Accueil.this, EventActivity.class);
                startActivity(intent);
            }
        });

        buttonBudget.setOnClickListener(new View.OnClickListener(){

            public void onClick(View V){
                Intent intent = new Intent(Accueil.this, BudgetsActivity.class);
                startActivity(intent);
            }
        });

        buttonDette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil.this, DettePretActivity.class);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id){
            case R.id.burgerMenu_Budget:
                Intent intentBudget = new Intent(Accueil.this, BudgetsActivity.class);
                startActivity(intentBudget);
                return true;

            case R.id.burgerMenu_Dettes:
                Toast.makeText(getApplicationContext(),"Prochainement Disponible !", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.burgerMenu_Event:
                Intent intentEvent = new Intent(Accueil.this, EventActivity.class);
                startActivity(intentEvent);
                return true;

            case R.id.burgerMenu_Parametre:
                Toast.makeText(getApplicationContext(),"Prochainement Disponible !", Toast.LENGTH_SHORT).show();

            case R.id.burgerMenu_Contacts:
                Toast.makeText(getApplicationContext(),"Prochainement Disponible !", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

}
