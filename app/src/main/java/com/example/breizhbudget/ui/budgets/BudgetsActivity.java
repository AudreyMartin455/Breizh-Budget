package com.example.breizhbudget.ui.budgets;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.breizhbudget.Accueil;
import com.example.breizhbudget.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.Repository.RepositoryBudget;
import com.example.breizhbudget.ui.event.Event;
import com.example.breizhbudget.ui.event.EventActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BudgetsActivity extends AppCompatActivity {

    @BindView(R.id.recycler_budgets)
    RecyclerView recycler_budgets;

    private BudgetsAdapter budgetAdapter;
    private RecyclerView.LayoutManager budgetLayoutManager;
    private RepositoryBudget repository;

    private List<ModelBudgets> listBudgets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgets);
        ButterKnife.bind(this);

        this.repository = RepositoryBudget.getInstance();

        recycler_budgets.setHasFixedSize(true);
        budgetLayoutManager = new LinearLayoutManager(this);
        recycler_budgets.setLayoutManager(budgetLayoutManager);

        repository.getAllBudget(this);
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
                Intent intentBudget = new Intent(BudgetsActivity.this, BudgetsActivity.class);
                startActivity(intentBudget);
                return true;

            case R.id.burgerMenu_Dettes:
                Toast.makeText(getApplicationContext(), "Prochainement Disponible !", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.burgerMenu_Event:
                Intent intentEvent = new Intent(BudgetsActivity.this, EventActivity.class);
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

    /**Mettre à jour l'interface**/
    public void updateBudgetsUI(List<ModelBudgets> budgetsList){
        this.listBudgets = budgetsList;
        for (int i = 0 ; i < budgetsList.size() ; i++){
            budgetAdapter = new BudgetsAdapter(BudgetsActivity.this, budgetsList);
            recycler_budgets.setAdapter(budgetAdapter);
        }
    }

    @OnClick(R.id.addBudget)
    public void viewAddBudget(){
        Intent intent = new Intent(BudgetsActivity.this, AddBudgetActivity.class);
        startActivity(intent);
    }


    public void deleteBudget(int position){
        Log.d(">>>>>", "BudgetActivity" + position);
        ModelBudgets budget = new ModelBudgets(this.listBudgets.get(position).getId());

        Context context  = this;
        new AlertDialog.Builder(this)
                .setTitle("Supprimer un budget")
                .setMessage("Voulez-vous vraiment supprimer ce budget ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        repository.deleteBudget(budget, context);
                    }})
                .setNegativeButton("Non", null).show();
    }

    public void show1Budget(ModelBudgets budget){
        Intent intent = new Intent(BudgetsActivity.this, OneBudgetActivity.class);
        intent.putExtra("BUDGET",budget);
        startActivity(intent);
    }
}
