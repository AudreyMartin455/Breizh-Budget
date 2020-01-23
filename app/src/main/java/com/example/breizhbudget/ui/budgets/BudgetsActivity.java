package com.example.breizhbudget.ui.budgets;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.breizhbudget.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.Repository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BudgetsActivity extends AppCompatActivity {

    @BindView(R.id.recycler_budgets)
    RecyclerView recycler_budgets;

    private BudgetsAdapter budgetAdapter;
    private RecyclerView.LayoutManager budgetLayoutManager;
    private Repository repository;

    private List<ModelBudgets> listBudgets;

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
        this.listBudgets = budgetsList;
        for (int i = 0 ; i < budgetsList.size() ; i++){
            budgetAdapter = new BudgetsAdapter(BudgetsActivity.this, budgetsList);
            recycler_budgets.setAdapter(budgetAdapter);
        }
    }

    @OnClick(R.id.addBudget)
    public void viewAddBudget(){
        Intent intent = new Intent(BudgetsActivity.this, AddBudgetActivity.class);
        startActivity(intent);
    }


    public void deleteBudget(int position){
        Log.d(">>>>>", "BudgetActivity" + position);
        ModelBudgets budget = new ModelBudgets(this.listBudgets.get(position).getId());

        Context context  = this;
        new AlertDialog.Builder(this)
                .setTitle("Supprimer un budget")
                .setMessage("Voulez-vous vraiment supprimer ce budget ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        repository.deleteBudget(budget, context);
                    }})
                .setNegativeButton("Non", null).show();
    }

    public void show1Budget(ModelBudgets budget){
        Intent intent = new Intent(BudgetsActivity.this, OneBudgetActivity.class);
        intent.putExtra("BUDGET",budget);
        startActivity(intent);
    }

}
