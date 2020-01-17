package com.example.breizhbudget.ui.budgets;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.breizhbudget.Repository;

import butterknife.ButterKnife;

public class AddRowActivity extends AppCompatActivity {

    private Repository repository;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO CHANGER LE LAYOUT
        //setContentView(R.layout.activity_budgets);
        ButterKnife.bind(this);

        this.repository = Repository.getInstance();

    }

    public void addRow(){

    }

}
