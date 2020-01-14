package com.example.breizhbudget.ui.budgets;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
