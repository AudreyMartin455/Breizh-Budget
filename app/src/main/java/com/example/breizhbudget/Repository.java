package com.example.breizhbudget;


import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.breizhbudget.ui.budgets.BudgetsActivity;
import com.example.breizhbudget.ui.budgets.ModelBudgets;
import com.example.breizhbudget.ui.budgets.ModelTransaction;
import com.example.breizhbudget.ui.budgets.OneBudgetActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe qui utilise Firebase
 */

public class Repository {

    private static Repository repository;
    private FirebaseFirestore db;
    private final String TAG = ">>>>>";

    private Repository(){
        this.db = FirebaseFirestore.getInstance();
    }

    public static synchronized Repository getInstance(){
        if(repository == null){
            repository = new Repository();
        }
        return repository;
    }

    /**************
     *
     *
     *  Budget's methods
     *
     *
     ***************/

    /**
     * Retourne tous les budgets
     * @param context
     * @return liste des budgets trouvés dans la DB
     */
    public List<ModelBudgets> getAllBudget(Context context){
        List<ModelBudgets> budgetsList = new ArrayList<>();
        ProgressDialog progressDialog = new ProgressDialog(context);

        progressDialog.setTitle("loading data ....");
        progressDialog.show();

        db.collection("Budgets")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressDialog.dismiss();


                        for (DocumentSnapshot doc : task.getResult()) {
                            ModelBudgets modelBudgets = new ModelBudgets(doc.getId(),doc.getString("name"), doc.getLong("montant"));
                            budgetsList.add(modelBudgets);

                        }

                        BudgetsActivity ba = (BudgetsActivity) context;
                        ba.updateBudgetsUI(budgetsList);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(context,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
        });

        return budgetsList;
    }



    public void addBudget(ModelBudgets budget,Context context){
        db.collection("Budgets")
                .add(budget)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context,"Budget created",Toast.LENGTH_SHORT).show();
                        Log.d("Success : ", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context,"Error, Budget not created",Toast.LENGTH_SHORT).show();
                        Log.w("Failure : ", "Error writing document", e);
                    }
                });
    }

    public void deleteBudget(ModelBudgets budget, Context context){
        db.collection("Budgets").document(budget.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context,"Budget deleted",Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Error, Budget not deleted",Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

    }

    public void updateBudget(Context context, ModelBudgets budget){
        db.collection("Budgets").document(budget.getId())
                .set(budget)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public void getAllTransaction(Context context, String idBudget){
        List<ModelTransaction> transList = new ArrayList<>();
        ProgressDialog progressDialog = new ProgressDialog(context);

        progressDialog.setTitle("loading data ....");
        progressDialog.show();

        db.collection("Transactions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressDialog.dismiss();


                        for (DocumentSnapshot doc : task.getResult()) {
                            if(doc.getString("idBudget").equals(idBudget)){
                                ModelTransaction modelTrans = new ModelTransaction(
                                        doc.getId(),
                                        doc.getString(doc.getId()),
                                        doc.getString("description"),
                                        doc.getBoolean("sign"),
                                        doc.getLong("montantTransaction")
                                );
                                transList.add(modelTrans);
                            }
                        }

                        OneBudgetActivity ba = (OneBudgetActivity) context;
                        ba.updateTransactionUI(transList);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(context,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addTransaction(Context context, ModelTransaction transaction){
        db.collection("Transactions")
                .add(transaction)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context,"Transaction created",Toast.LENGTH_SHORT).show();
                        Log.d("Success : ", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context,"Error, Budget not created",Toast.LENGTH_SHORT).show();
                        Log.w("Failure : ", "Error writing document", e);
                    }
                });
    }

    public void deleteTransaction(Context context, ModelTransaction transaction, String idBudget){
        db.collection("Transactions").document(transaction.getIdTransaction())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context,"Budget deleted",Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Error, Budget not deleted",Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
        this.getAllTransaction(context,idBudget);
    }

    /**************
     *
     *
     *  Dette's methods
     *
     *
     ***************/



    /**************
     *
     *
     *  Event's methods
     *
     *
     ***************/
}
