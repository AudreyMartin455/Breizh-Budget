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

public class UpdateBudgetActivity extends AppCompatActivity {

    @BindView(R.id.intitule)
    EditText name;
    @BindView(R.id.montantTransAdd)
    EditText montant;

    private Repository repository;
    private ModelBudgets budget;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbudget);
        ButterKnife.bind(this);
        this.repository = Repository.getInstance();

        this.budget = getIntent().getParcelableExtra("BUDGET");


    }

    @OnClick(R.id.ajouter)
    public void updateBudget(){
        this.budget.setMontant(Long.parseLong(montant.getText().toString()));
        this.budget.setName(name.getText().toString());

        this.repository.updateBudget(this, this.budget);

        Intent intent = new Intent(UpdateBudgetActivity.this, OneBudgetActivity.class);
        startActivity(intent);
    }
}