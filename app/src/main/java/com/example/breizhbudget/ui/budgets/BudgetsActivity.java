package com.example.breizhbudget.ui.budgets;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.breizhbudget.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.Repository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BudgetsActivity extends AppCompatActivity {

    @BindView(R.id.recycler_budgets)
    RecyclerView recycler_budgets;

    private BudgetsAdapter budgetAdapter;
    private RecyclerView.LayoutManager budgetLayoutManager;

    private List<ModelBudgets> budgetsList = new ArrayList<>();

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgets);
        ButterKnife.bind(this);

        this.repository = Repository.getInstance();

        //recycler_budgets = findViewById(R.id.recycler_budgets);
        recycler_budgets.setHasFixedSize(true);
        budgetLayoutManager = new LinearLayoutManager(this);
        recycler_budgets.setLayoutManager(budgetLayoutManager);


        budgetsList = repository.getAllBudget(budgetsList,this);


        for (int i = 0 ; i < budgetsList.size() ; i++){
            Log.d("value is" , budgetsList.get(i).toString());
            budgetAdapter = new BudgetsAdapter(BudgetsActivity.this, budgetsList);
            recycler_budgets.setAdapter(budgetAdapter);
        }


    }

    public void viewAddBudget(){
        Intent intent = new Intent(BudgetsActivity.this, AddBudgetActivity.class);
        startActivity(intent);
    }

}
