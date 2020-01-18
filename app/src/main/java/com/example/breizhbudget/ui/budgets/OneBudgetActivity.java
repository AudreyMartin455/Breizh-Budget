package com.example.breizhbudget.ui.budgets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OneBudgetActivity extends AppCompatActivity {

    @BindView(R.id.valeur_budget)
    TextView viewMontant;
    @BindView(R.id.titre)
    TextView viewTitle;
    @BindView(R.id.recycler_transaction)
    RecyclerView recycler_row;

    private TransactionAdapter transactionAdapter;
    private RecyclerView.LayoutManager budgetLayoutManager;
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

        recycler_row.setHasFixedSize(true);
        budgetLayoutManager = new LinearLayoutManager(this);
        recycler_row.setLayoutManager(budgetLayoutManager);

        this.repository.getAllTransaction(this,budget.getId());

    }

    public void updateTransactionUI(List<ModelTransaction> transList){
        for (int i = 0 ; i < transList.size() ; i++){
            transactionAdapter = new TransactionAdapter(OneBudgetActivity.this, transList);
            recycler_row.setAdapter(transactionAdapter);
        }
    }

    @OnClick(R.id.addRow)
    public void showAddRowManually(){
        Intent intent = new Intent(OneBudgetActivity.this, AddRowActivity.class);
        startActivity(intent);
    }

    public void addRowScan(){

    }
}
