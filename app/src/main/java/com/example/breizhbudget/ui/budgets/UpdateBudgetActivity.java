package com.example.breizhbudget.ui.budgets;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository.RepositoryBudget;
import com.example.breizhbudget.ui.event.Event;
import com.example.breizhbudget.ui.event.EventActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateBudgetActivity extends AppCompatActivity {

    @BindView(R.id.intitule)
    EditText name;
    @BindView(R.id.montantAddBudget)
    EditText montant;
    @BindView(R.id.ajouter)
    Button addButton;

    private RepositoryBudget repository;
    private ModelBudgets budget;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbudget);
        ButterKnife.bind(this);
        this.repository = RepositoryBudget.getInstance();

        this.budget = getIntent().getParcelableExtra("BUDGET");
        this.name.setText(this.budget.getName());
        this.montant.setText(this.budget.getMontant()+"");
        this.addButton.setText("Valider");

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
                Intent intentBudget = new Intent(UpdateBudgetActivity.this, BudgetsActivity.class);
                startActivity(intentBudget);
                return true;

            case R.id.burgerMenu_Dettes:
                Toast.makeText(getApplicationContext(), "Prochainement Disponible !", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.burgerMenu_Event:
                Intent intentEvent = new Intent(UpdateBudgetActivity.this, EventActivity.class);
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

    @OnClick(R.id.ajouter)
    public void updateBudget(){
        this.budget.setMontant(Double.parseDouble(montant.getText().toString()));
        this.budget.setName(name.getText().toString());

        this.repository.updateBudget(this, this.budget);

        Intent intent = new Intent(UpdateBudgetActivity.this, OneBudgetActivity.class);
        intent.putExtra("BUDGET",this.budget);
        startActivity(intent);
    }
}