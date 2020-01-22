package com.example.breizhbudget.ui.budgets;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository;
import com.example.breizhbudget.camera.OcrCaptureActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OneBudgetActivity extends AppCompatActivity {

    @BindView(R.id.valeur_budget)
    TextView viewMontant;
    @BindView(R.id.titre)
    TextView viewTitle;
    @BindView(R.id.recycler_transaction)
    RecyclerView recycler_row;
    @BindView(R.id.total_budget)
    TextView totalBudget;

    private TransactionAdapter transactionAdapter;
    private RecyclerView.LayoutManager budgetLayoutManager;
    private Repository repository;
    private ModelBudgets budget;

    private List<ModelTransaction> listTransactions;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        ButterKnife.bind(this);
        this.repository = Repository.getInstance();


        this.budget = getIntent().getParcelableExtra("BUDGET");
        this.viewMontant.setText(budget.getMontant() + " €");
        this.viewTitle.setText(budget.getName());

        recycler_row.setHasFixedSize(true);
        budgetLayoutManager = new LinearLayoutManager(this);
        recycler_row.setLayoutManager(budgetLayoutManager);

        this.repository.getAllTransaction(this,this.budget.getId());


    }

    public void updateTransactionUI(List<ModelTransaction> transList){
        this.listTransactions = transList;

        long total = this.budget.getMontant();
        for (int i = 0 ; i < transList.size() ; i++){
            if(transList.get(i).isSign()){
                total = total + transList.get(i).getMontantTransaction();
            }else{
                total = total - transList.get(i).getMontantTransaction();
            }

            transactionAdapter = new TransactionAdapter(OneBudgetActivity.this, transList);
            recycler_row.setAdapter(transactionAdapter);
        }
        this.totalBudget.setText(total + " €");
    }

    @OnClick(R.id.addRow)
    public void showAddRowManually(){
        Intent intent = new Intent(OneBudgetActivity.this, AddRowActivity.class);
        intent.putExtra("BUDGET",this.budget);
        startActivity(intent);
    }

    @OnClick(R.id.scannerButton)
    public void addTransactionScan(){
        Intent intent = new Intent(OneBudgetActivity.this, OcrCaptureActivity.class);
        intent.putExtra("BUDGET",this.budget);
        startActivity(intent);
    }

    @OnClick(R.id.updateBudget)
    public void updateBudget(){
        Intent intent = new Intent(OneBudgetActivity.this, UpdateBudgetActivity.class);
        intent.putExtra("BUDGET",this.budget);
        startActivity(intent);
    }

    public void deleteTransaction(int position){
        ModelTransaction transaction = new ModelTransaction(this.listTransactions.get(position).getIdTransaction());

        Context context  = this;
        new AlertDialog.Builder(this)
                .setTitle("Supprimer une transaction")
                .setMessage("Voulez-vous vraiment supprimer cette transaction ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        repository.deleteTransaction(context,transaction,budget.getId());
                    }})
                .setNegativeButton("Non", null).show();

    }

}
