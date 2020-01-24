package com.example.breizhbudget.Repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.provider.Telephony;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.breizhbudget.ui.budgets.ModelBudgets;
import com.example.breizhbudget.ui.event.ComptageActivity;
import com.example.breizhbudget.ui.event.ComptageAdapter;
import com.example.breizhbudget.ui.event.Event;
import com.example.breizhbudget.ui.event.EventActivity;
import com.example.breizhbudget.ui.event.EventAdapter;
import com.example.breizhbudget.ui.event.ModelEvent;
import com.example.breizhbudget.ui.event.Participant;
import com.example.breizhbudget.ui.event.ParticipantActivity;
import com.example.breizhbudget.ui.event.ViewEvent;
import com.example.breizhbudget.ui.event.ViewHolder;
import com.example.breizhbudget.ui.event.Viewitemadapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RepositoryEvent {

    private static RepositoryEvent repository;
    private FirebaseFirestore db;
    private final String TAG = ">>>>>";
    private List<ModelEvent> modelEvents;
    private List<Participant> participantList;

    private RepositoryEvent(){
        this.db = FirebaseFirestore.getInstance();
        this.modelEvents = new ArrayList<>();
        this.participantList = new ArrayList<>();
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

    public void uploadData(Context context, Map<String, Object> evnt, String id){
        ProgressDialog pd = new ProgressDialog(context);
        pd.setTitle("adding data");
        pd.show();

        db.collection("Events").document(id).set(evnt)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(context, "uplaoad",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context, e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void getAllParticipant(Context context,String newString){
       ProgressDialog progressDialog = new ProgressDialog(context);
       this.participantList.clear();
       this.modelEvents.clear();

        progressDialog.setTitle(newString);
        progressDialog.show();


        db.collection("Events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressDialog.dismiss();


                        modelEvents = task.getResult().toObjects(ModelEvent.class);



                        for (int i = 0; i < modelEvents.size(); i++){
                            if(modelEvents.get(i).getTitle().equals(newString)){
                                if(modelEvents.get(i).getParticipants().size()>0){
                                    for (int j = 0; j < modelEvents.get(i).getParticipants().size(); j++){
                                        participantList.add(modelEvents.get(i).getParticipants().get(j));}
                                }
                            }

                        }
                       ViewEvent ve = (ViewEvent) context;
                        ve.updateInterface(participantList);


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                //  Toast.makeText(EventActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void getAmountPerPerson(Context context, String title){
        ProgressDialog progressDialog = new ProgressDialog(context);

        this.participantList.clear();
        this.modelEvents.clear();

        progressDialog.setTitle(title);
        progressDialog.show();


        db.collection("Events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressDialog.dismiss();


                        modelEvents = task.getResult().toObjects(ModelEvent.class);

                        Hashtable<String,Integer> tableParticipant = new Hashtable<String,Integer>();
                        int montantParticipant = 0;
                        ArrayList<Participant> participantMontantList = new ArrayList<Participant>();

                        //On récupère l'event
                        for(ModelEvent event : modelEvents){
                            if(event.getTitle().equals(title)){

                                //On compte le montant de chaque participant
                                for(Participant part : event.getParticipants()){
                                    if(tableParticipant.get(part.getName())!=null){
                                        montantParticipant = tableParticipant.get(part.getName());
                                    }
                                    tableParticipant.put(part.getName(),montantParticipant + part.getMontant());
                                    montantParticipant = 0;
                                }


                                Set<String> keys = tableParticipant.keySet();

                                //On met les résultats dans une liste pour afficher
                                for(String namePart : keys){
                                    participantMontantList.add(new Participant(namePart,"",tableParticipant.get(namePart)));
                                }

                            }
                        }
                        

                        ComptageActivity ca = (ComptageActivity) context;
                        ca.updateInterface(participantMontantList);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                //  Toast.makeText(EventActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void addParticipant(Context context,String newString, Participant participant){
        ProgressDialog pd = new ProgressDialog(context);
        ModelEvent modelEvent = new ModelEvent() ;

        pd.setTitle("adding participant");
        pd.show();

        db.collection("Events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        pd.dismiss();

                        modelEvents = task.getResult().toObjects(ModelEvent.class);

                        for (int i = 0; i < modelEvents.size(); i++){

                            if(modelEvents.get(i).getTitle().equals( newString) ){
                                modelEvents.get(i).getParticipants().add(participant);
                                modelEvent.setId(modelEvents.get(i).getId());
                                modelEvent.setTitle(modelEvents.get(i).getTitle());
                                modelEvent.setParticipants(modelEvents.get(i).getParticipants());
                            }
                        }

                        db.collection("Events").document(newString).update("participants",modelEvent.getParticipants());

                        ParticipantActivity pa = (ParticipantActivity) context;
                        pa.returnToViewEvent();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                //Toast.makeText(EventActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
