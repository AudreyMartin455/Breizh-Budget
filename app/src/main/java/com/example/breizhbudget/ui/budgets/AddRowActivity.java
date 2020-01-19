package com.example.breizhbudget.ui.budgets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddRowActivity extends AppCompatActivity {

    @BindView(R.id.desc)
    EditText description;
    @BindView(R.id.montantTransAdd)
    EditText montant;
    @BindView(R.id.deb)
    RadioButton debit;
    @BindView(R.id.cred)
    RadioButton credit;

    private Repository repository;
    private ModelBudgets budget;
    private boolean sign;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit_credit);
        ButterKnife.bind(this);

        this.repository = Repository.getInstance();
        this.budget = getIntent().getParcelableExtra("BUDGET");


    }

    @OnClick(R.id.confirmRow)
    public void addRow(){
       /* ModelTransaction transaction = new ModelTransaction(
                this.budget.getId(),
                this.description.getText().toString(),
                this.sign,
                Long.parseLong(montant.getText().toString())
        );*/
       ModelTransaction transaction = new ModelTransaction(
               this.budget.getId(),
               "Loyer",
               this.sign,
               400
       );
        repository.addTransaction(this, transaction);
        Intent intent = new Intent(AddRowActivity.this, OneBudgetActivity.class);
        intent.putExtra("BUDGET",this.budget);
        startActivity(intent);
    }


    @OnClick(R.id.cred)
    public void setCredit(){
        this.sign = true;
    }

    @OnClick(R.id.deb)
    public void setDebit(){
        this.sign = false;
    }


}
