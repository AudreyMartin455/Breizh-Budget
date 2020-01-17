package com.example.breizhbudget.ui.budgets;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository;

import butterknife.ButterKnife;

public class OneBudgetActivity extends AppCompatActivity {

    private Repository repository;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO CHANGER LE LAYOUT
        //setContentView(R.layout.activity_budgets);
        ButterKnife.bind(this);

        this.repository = Repository.getInstance();

    }

    public void showAddRowManually(){
        Intent intent = new Intent(OneBudgetActivity.this, AddRowActivity.class);
        startActivity(intent);
    }

    public void addRowScan(){

    }
}
