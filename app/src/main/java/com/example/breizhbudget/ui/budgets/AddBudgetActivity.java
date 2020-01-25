package com.example.breizhbudget.ui.budgets;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository.RepositoryBudget;
import com.example.breizhbudget.ui.event.Event;
import com.example.breizhbudget.ui.event.EventActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBudgetActivity extends AppCompatActivity implements Validator.ValidationListener{

    @NotEmpty
    @BindView(R.id.intitule)
    EditText name;

    @NotEmpty
    @BindView(R.id.montantAddBudget)
    EditText montant;

    RepositoryBudget repository;
    Validator validator;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbudget);
        ButterKnife.bind(this);

        this.repository = RepositoryBudget.getInstance();

        this.validator = new Validator(this);
        this.validator.setValidationListener(this);
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
                Intent intentBudget = new Intent(AddBudgetActivity.this, BudgetsActivity.class);
                startActivity(intentBudget);
                return true;

            case R.id.burgerMenu_Dettes:
                Toast.makeText(getApplicationContext(), "Prochainement Disponible !", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.burgerMenu_Event:
                Intent intentEvent = new Intent(AddBudgetActivity.this, EventActivity.class);
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
    public void addBudget() {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        ModelBudgets budget = new ModelBudgets(name.getText().toString(),Long.parseLong(montant.getText().toString()));

        repository.addBudget(budget, this);

        Intent intent = new Intent(AddBudgetActivity.this, BudgetsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
