package com.example.breizhbudget.ui.budgets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository.RepositoryBudget;
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
