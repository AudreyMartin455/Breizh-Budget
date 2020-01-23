package com.example.breizhbudget.Repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.breizhbudget.ui.budgets.ModelBudgets;
import com.example.breizhbudget.ui.event.EventActivity;
import com.example.breizhbudget.ui.event.EventAdapter;
import com.example.breizhbudget.ui.event.ModelEvent;
import com.example.breizhbudget.ui.event.Participant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RepositoryEvent {

    private static RepositoryEvent repository;
    private FirebaseFirestore db;
    private final String TAG = ">>>>>";

    private RepositoryEvent(){
        this.db = FirebaseFirestore.getInstance();
    }

    public static synchronized RepositoryEvent getInstance(){
        if(repository == null){
            repository = new RepositoryEvent();
        }
        return repository;
    }

    public void getAllEvent(Context context){
        List<ModelEvent> modelEvents = new ArrayList<>();
        ProgressDialog progressDialog = new ProgressDialog(context);

        progressDialog.setTitle("loading data ....");
        progressDialog.show();

        db.collection("Events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressDialog.dismiss();
                        for( DocumentSnapshot doc:task.getResult()){
                            String id =doc.getString("id");
                            String title=doc.getString("title");
                            ArrayList<Participant> participantList = (ArrayList<Participant>) doc.get("participants");
                            ModelEvent modelEvent =new ModelEvent(doc.getString("id"), doc.getString("title"), participantList);
                            modelEvents.add(modelEvent);

                        }

                        EventActivity ea = (EventActivity) context;
                        ea.showData(modelEvents);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(context,e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
