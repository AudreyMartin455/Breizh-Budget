package com.example.breizhbudget.ui.budgets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OneBudgetActivity extends AppCompatActivity {

    @BindView(R.id.valeur_budget)
    TextView viewMontant;
    @BindView(R.id.titre)
    TextView viewTitle;

    private Repository repository;
    private ModelBudgets budget;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        ButterKnife.bind(this);
        this.repository = Repository.getInstance();


        this.budget = getIntent().getParcelableExtra("BUDGET");
        this.viewMontant.setText(budget.getMontant() + " €");
        this.viewTitle.setText(budget.getName());

        this.repository.getAllDebitCredit(this);

    }

    @OnClick(R.id.addRow)
    public void showAddRowManually(){
        Intent intent = new Intent(OneBudgetActivity.this, AddRowActivity.class);
        startActivity(intent);
    }

    public void addRowScan(){

    }
}
