package com.example.breizhbudget.ui.budgets;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddRowActivity extends AppCompatActivity {

    private Repository repository;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit_credit);
        ButterKnife.bind(this);

        this.repository = Repository.getInstance();

    }

    @OnClick(R.id.confirmRow)
    public void addRow(){
        //TODO FAUT AJOUTER LA LIGNE WSH
        Intent intent = new Intent(AddRowActivity.this, OneBudgetActivity.class);
        startActivity(intent);
    }

}
