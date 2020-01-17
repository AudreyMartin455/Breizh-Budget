package com.example.breizhbudget.ui.budgets;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.breizhbudget.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.Repository;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BudgetsActivity extends AppCompatActivity {

    @BindView(R.id.recycler_budgets)
    RecyclerView recycler_budgets;

    private BudgetsAdapter budgetAdapter;
    private RecyclerView.LayoutManager budgetLayoutManager;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgets);
        ButterKnife.bind(this);

        this.repository = Repository.getInstance();

        recycler_budgets.setHasFixedSize(true);
        budgetLayoutManager = new LinearLayoutManager(this);
        recycler_budgets.setLayoutManager(budgetLayoutManager);


        repository.getAllBudget(this);


    }

    public void updateBudgetsUI(List<ModelBudgets> budgetsList){
        for (int i = 0 ; i < budgetsList.size() ; i++){
            budgetAdapter = new BudgetsAdapter(BudgetsActivity.this, budgetsList);
            recycler_budgets.setAdapter(budgetAdapter);
        }
    }


    public void viewAddBudget(){
        Intent intent = new Intent(BudgetsActivity.this, AddBudgetActivity.class);
        startActivity(intent);
    }

}
