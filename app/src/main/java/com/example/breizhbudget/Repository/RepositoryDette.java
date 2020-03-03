package com.example.breizhbudget.Repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.breizhbudget.ui.dette.DettePretActivity;
import com.example.breizhbudget.ui.dette.ModelDette;
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

public class RepositoryDette {

    private static RepositoryDette repository;
    private FirebaseFirestore db;
    private final String TAG = ">>>>>";

    private RepositoryDette() {this.db = FirebaseFirestore.getInstance();}

    public static synchronized RepositoryDette getInstance(){
        if(repository == null){
            repository = new RepositoryDette();
        }
        return repository;
    }

    public void getAllDette(Context context){

        List<ModelDette> detteList = new ArrayList<>();
        ProgressDialog progressDialog = new ProgressDialog(context);

        progressDialog.setTitle("loading data ....");
        progressDialog.show();

        db.collection("Dettes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressDialog.dismiss();


                        for (DocumentSnapshot doc : task.getResult()) {
                            ModelDette modelDette = new ModelDette(doc.getId(),
                                    doc.getString("beneficiaire"),
                                    doc.getString("description"),
                                    doc.getDouble("montant"),
                                    doc.getBoolean("sign"));
                            detteList.add(modelDette);

                        }

                        DettePretActivity dpa = (DettePretActivity) context;
                        dpa.updateDettesUI(detteList);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(context,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteDette(ModelDette dette,Context context){
        db.collection("Dettes").document(dette.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context,"Dettes deleted",Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        getAllDette(context);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Error, Dettes not deleted",Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

    }

    public void addDette(ModelDette newDette, Context context) {
        db.collection("Dettes")
                .add(newDette)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context,"Dette created",Toast.LENGTH_SHORT).show();
                        Log.d("Success : ", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context,"Error, Dette not created",Toast.LENGTH_SHORT).show();
                        Log.w("Failure : ", "Error writing document", e);
                    }
                });
    }

}
