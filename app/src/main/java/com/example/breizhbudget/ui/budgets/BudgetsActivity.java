package com.example.breizhbudget.ui.budgets;

import android.os.Bundle;
import android.widget.TextView;

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

    @BindView(R.id.text_home)
    TextView home;
    @BindView(R.id.recycler_budgets)
    RecyclerView recycler_budgets;
    private RecyclerView.Adapter budgetAdapter;
    private RecyclerView.LayoutManager budgetLayoutManager;
    private List<Budget> budgetsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgets);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_ajoutParticipant)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


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

}
