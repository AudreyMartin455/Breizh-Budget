package com.example.breizhbudget.Repository;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.breizhbudget.ui.dette.ModelDette;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    /**
     * Retourne toutes les dette et tous les préts
     * @param context
     * @return Liste des dettes et prêts de la BD
     */
    public List<ModelDette> getAllDette(Context context) {
        List<ModelDette> dettesListe = new ArrayList<ModelDette>();
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
                            ModelDette modelDette = new ModelDette(doc.getId(), doc.getString("beneficiaire"), doc.getString("desctiption"), doc.getDouble("montant"));
                            dettesListe.add(modelDette);
                        }
                    }
                })
    }
}
