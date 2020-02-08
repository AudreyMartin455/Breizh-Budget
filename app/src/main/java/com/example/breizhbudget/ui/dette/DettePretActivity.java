package com.example.breizhbudget.ui.dette;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository.RepositoryDette;
import com.example.breizhbudget.ui.budgets.BudgetsActivity;
import com.example.breizhbudget.ui.budgets.ModelBudgets;
import com.example.breizhbudget.ui.event.EventActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DettePretActivity extends AppCompatActivity {

    @BindView(R.id.button_add_dette) Button buttonAddDette;
    @BindView(R.id.recyclerViewDette) RecyclerView recyclerDette;

    private DetteAdapter detteAdapter;
    private RecyclerView.LayoutManager detteLayoutManager;
    private RepositoryDette repository;

    private List<ModelDette> listDette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dette_pret);
        ButterKnife.bind(this);

        buttonAddDette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DettePretActivity.this, AddDetteActivity.class);
                startActivity(intent);
            }
        });

        this.repository = RepositoryDette.getInstance();

        recyclerDette.setHasFixedSize(true);
        detteLayoutManager = new LinearLayoutManager(this);
        recyclerDette.setLayoutManager(detteLayoutManager);

        repository.getAllDette(this);
    }

    /**
     * Fonction pour afficher le bouton retour dans l'action bar
     */
    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Fonction du burger menu
     * @param menu
     * @return
     */
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


        switch (id) {
            case R.id.burgerMenu_Budget:
                Intent intentBudget = new Intent(DettePretActivity.this, BudgetsActivity.class);
                startActivity(intentBudget);
                return true;

            case R.id.burgerMenu_Dettes:
                Toast.makeText(getApplicationContext(), "Prochainement Disponible !", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.burgerMenu_Event:
                Intent intentEvent = new Intent(DettePretActivity.this, EventActivity.class);
                startActivity(intentEvent);
                return true;

            case R.id.burgerMenu_Parametre:
                Toast.makeText(getApplicationContext(), "Prochainement Disponible !", Toast.LENGTH_SHORT).show();

            case R.id.burgerMenu_Contacts:
                Toast.makeText(getApplicationContext(), "Prochainement Disponible !", Toast.LENGTH_SHORT).show();

            case R.id.home: // Option pour le retour
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return false;
    }

    public void updateDettesUI(List<ModelDette> detteList) {
        this.listDette = detteList;
        this.detteAdapter = new DetteAdapter(DettePretActivity.this, detteList);
        this.recyclerDette.setAdapter(detteAdapter);
    }

    public void deleteBudget(int position){
        Log.d(">>>>>", "DettePretActivity" + position);
        ModelDette dette = new ModelDette(this.listDette.get(position).getId());

        Context context  = this;
        new AlertDialog.Builder(this)
                .setTitle("Supprimer une dette ou un prêts")
                .setMessage("Voulez-vous vraiment supprimer cet item ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        repository.deleteDette(dette, context);
                    }})
                .setNegativeButton("Non", null).show();
    }
}
