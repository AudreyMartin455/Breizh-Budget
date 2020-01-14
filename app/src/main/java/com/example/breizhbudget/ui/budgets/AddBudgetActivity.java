package com.example.breizhbudget.ui.budgets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBudgetActivity extends AppCompatActivity {

    @BindView(R.id.intitule)
    EditText name;
    @BindView(R.id.montant)
    EditText montant;

    Repository repository;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbudget);
        ButterKnife.bind(this);

        repository = Repository.getInstance();

    }

    @OnClick(R.id.ajouter)
    public void addBudget() {
        ModelBudgets budget = new ModelBudgets(name.getText().toString(),Long.parseLong(montant.getText().toString()));

        repository.addBudget(budget, this);

        Intent intent = new Intent(AddBudgetActivity.this, BudgetsActivity.class);
        startActivity(intent);
    }
}
