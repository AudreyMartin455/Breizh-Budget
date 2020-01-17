package com.example.breizhbudget;


import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.example.breizhbudget.ui.budgets.ModelBudgets;
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
     * @param budgetsList liste vide de budgets
     * @param context
     * @return liste des budgets trouvés dans la DB
     */
    public List<ModelBudgets> getAllBudget(List<ModelBudgets> budgetsList, Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);;

        progressDialog.setTitle("loading data ....");
        progressDialog.show();

        db.collection("Budgets")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressDialog.dismiss();


                        for (DocumentSnapshot doc : task.getResult()) {
                            ModelBudgets modelBudgets = new ModelBudgets(doc.getString("name"), doc.getLong("montant"));
                            budgetsList.add(modelBudgets);

                        }

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
