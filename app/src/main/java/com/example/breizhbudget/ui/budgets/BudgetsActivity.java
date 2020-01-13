package com.example.breizhbudget.ui.budgets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.breizhbudget.Accueil;
import com.example.breizhbudget.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BudgetsActivity extends AppCompatActivity {

    @BindView(R.id.recycler_budgets)
    RecyclerView recycler_budgets;
    private RecyclerView.Adapter budgetAdapter;
    private RecyclerView.LayoutManager budgetLayoutManager;
    private List<Budget> budgetsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgets);


        // create the data
        budgetsList = new ArrayList<>();
        budgetsList.add(new Budget("Budget Nom 1",500));
        budgetsList.add(new Budget("Budget Nom 2",600));

        // handle the recycler view
      /*  budgetAdapter = new BudgetsAdapter(this, budgetsList, this);
        RecyclerView.LayoutManager budgetLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_budgets.setLayoutManager(budgetLayoutManager);
        recycler_budgets.setAdapter(budgetAdapter);*/

    }

    public void viewAddBudget(){
        Intent intent = new Intent(BudgetsActivity.this, BudgetsActivity.class);
        startActivity(intent);
    }

}
