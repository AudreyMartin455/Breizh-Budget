package com.example.breizhbudget.ui.budgets;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository.RepositoryBudget;
import com.example.breizhbudget.ui.event.Event;
import com.example.breizhbudget.ui.event.EventActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddRowActivity extends AppCompatActivity implements Validator.ValidationListener{

    @NotEmpty
    @BindView(R.id.desc)
    EditText description;
    @NotEmpty
    @BindView(R.id.montantTransAdd)
    EditText montant;
    @Checked
    @BindView(R.id.sign)
    RadioGroup signTrans;
    @BindView(R.id.deb)
    RadioButton debit;
    @BindView(R.id.cred)
    RadioButton credit;

    private RepositoryBudget repository;
    Validator validator;

    private ModelBudgets budget;
    private boolean sign;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit_credit);
        ButterKnife.bind(this);

        this.repository = RepositoryBudget.getInstance();
        this.budget = getIntent().getParcelableExtra("BUDGET");

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
                Intent intentBudget = new Intent(AddRowActivity.this, BudgetsActivity.class);
                startActivity(intentBudget);
                return true;

            case R.id.burgerMenu_Dettes:
                Toast.makeText(getApplicationContext(), "Prochainement Disponible !", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.burgerMenu_Event:
                Intent intentEvent = new Intent(AddRowActivity.this, EventActivity.class);
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

    @OnClick(R.id.confirmRow)
    public void addRow(){
        validator.validate();
    }


    @OnClick(R.id.cred)
    public void setCredit(){
        this.sign = true;
    }

    @OnClick(R.id.deb)
    public void setDebit(){
        this.sign = false;
    }


    @Override
    public void onValidationSucceeded() {
        ModelTransaction transaction = new ModelTransaction(
                this.budget.getId(),
                this.description.getText().toString(),
                this.sign,
                Long.parseLong(montant.getText().toString())
        );

        repository.addTransaction(this, transaction);
        Intent intent = new Intent(AddRowActivity.this, OneBudgetActivity.class);
        intent.putExtra("BUDGET",this.budget);
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
