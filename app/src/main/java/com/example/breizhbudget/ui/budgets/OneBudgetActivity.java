package com.example.breizhbudget.ui.budgets;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository.RepositoryBudget;
import com.example.breizhbudget.camera.OcrCaptureActivity;
import com.example.breizhbudget.ui.event.EventActivity;

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
    private RepositoryBudget repository;
    private ModelBudgets budget;

    private List<ModelTransaction> listTransactions;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        ButterKnife.bind(this);
        this.repository = RepositoryBudget.getInstance();


        this.budget = getIntent().getParcelableExtra("BUDGET");
        this.viewMontant.setText(this.budget.getMontant() + " €");
        this.viewTitle.setText(this.budget.getName());

        recycler_row.setHasFixedSize(true);
        budgetLayoutManager = new LinearLayoutManager(this);
        recycler_row.setLayoutManager(budgetLayoutManager);

        this.repository.getAllTransaction(this,this.budget.getId());


    }

    /**
     * Fonction pour afficher le bouton retour dans l'action bar
     */
    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Fonction du burger menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.burgermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {
            case R.id.burgerMenu_Budget:
                Intent intentBudget = new Intent(OneBudgetActivity.this, BudgetsActivity.class);
                startActivity(intentBudget);
                return true;

            case R.id.burgerMenu_Dettes:
                Toast.makeText(getApplicationContext(), "Prochainement Disponible !", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.burgerMenu_Event:
                Intent intentEvent = new Intent(OneBudgetActivity.this, EventActivity.class);
                startActivity(intentEvent);
                return true;

            case R.id.burgerMenu_Parametre:
                Toast.makeText(getApplicationContext(), "Prochainement Disponible !", Toast.LENGTH_SHORT).show();

            case R.id.burgerMenu_Contacts:
                Toast.makeText(getApplicationContext(), "Prochainement Disponible !", Toast.LENGTH_SHORT).show();

            case R.id.home: // Option pour le retour
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return false;
    }

    public void updateTransactionUI(List<ModelTransaction> transList){
        this.listTransactions = transList;

        double total = this.budget.getMontant();
        for (ModelTransaction trans: transList){
            if(trans.isSign()){
                total = total + trans.getMontantTransaction();
            }else{
                total = total - trans.getMontantTransaction();
            }
        }
        transactionAdapter = new TransactionAdapter(OneBudgetActivity.this, transList);
        recycler_row.setAdapter(transactionAdapter);
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
        intent.putExtra("OPTION","budget");
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
