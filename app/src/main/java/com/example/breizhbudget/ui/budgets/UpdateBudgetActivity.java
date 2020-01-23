package com.example.breizhbudget.ui.budgets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.breizhbudget.R;
import com.example.breizhbudget.RepositoryBudget;

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

    @OnClick(R.id.ajouter)
    public void updateBudget(){
        this.budget.setMontant(Long.parseLong(montant.getText().toString()));
        this.budget.setName(name.getText().toString());

        this.repository.updateBudget(this, this.budget);

        Intent intent = new Intent(UpdateBudgetActivity.this, OneBudgetActivity.class);
        intent.putExtra("BUDGET",this.budget);
        startActivity(intent);
    }
}