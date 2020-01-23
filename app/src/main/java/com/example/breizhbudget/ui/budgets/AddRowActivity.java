package com.example.breizhbudget.ui.budgets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddRowActivity extends AppCompatActivity implements Validator.ValidationListener{

    @NotEmpty
    @BindView(R.id.desc)
    EditText description;
    @NotEmpty
    @BindView(R.id.montantTransAdd)
    EditText montant;
    @Checked
    @BindView(R.id.sign)
    RadioGroup signTrans;
    @BindView(R.id.deb)
    RadioButton debit;
    @BindView(R.id.cred)
    RadioButton credit;

    private Repository repository;
    Validator validator;

    private ModelBudgets budget;
    private boolean sign;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit_credit);
        ButterKnife.bind(this);

        this.repository = Repository.getInstance();
        this.budget = getIntent().getParcelableExtra("BUDGET");

        this.validator = new Validator(this);
        this.validator.setValidationListener(this);
    }

    @OnClick(R.id.confirmRow)
    public void addRow(){
        validator.validate();
    }


    @OnClick(R.id.cred)
    public void setCredit(){
        this.sign = true;
    }

    @OnClick(R.id.deb)
    public void setDebit(){
        this.sign = false;
    }


    @Override
    public void onValidationSucceeded() {
        ModelTransaction transaction = new ModelTransaction(
                this.budget.getId(),
                this.description.getText().toString(),
                this.sign,
                Long.parseLong(montant.getText().toString())
        );

        repository.addTransaction(this, transaction);
        Intent intent = new Intent(AddRowActivity.this, OneBudgetActivity.class);
        intent.putExtra("BUDGET",this.budget);
        startActivity(intent);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
