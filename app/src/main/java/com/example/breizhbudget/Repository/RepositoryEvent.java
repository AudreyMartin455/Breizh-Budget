package com.example.breizhbudget.Repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.breizhbudget.ui.event.ComptageActivity;
import com.example.breizhbudget.ui.event.EventActivity;
import com.example.breizhbudget.ui.event.ModelEvent;
import com.example.breizhbudget.ui.event.Participant;
import com.example.breizhbudget.ui.event.ParticipantActivity;
import com.example.breizhbudget.ui.event.TricountActivity;
import com.example.breizhbudget.ui.event.ViewEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
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
    private List<Participant> newparticipantList;

    private RepositoryEvent() {
        this.db = FirebaseFirestore.getInstance();
        this.modelEvents = new ArrayList<>();
        this.participantList = new ArrayList<>();
        this.newparticipantList = new ArrayList<>();
    }

    public static synchronized RepositoryEvent getInstance() {
        if (repository == null) {
            repository = new RepositoryEvent();
        }
        return repository;
    }

    public void getAllEvent(Context context) {
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
                        for (DocumentSnapshot doc : task.getResult()) {
                            ArrayList<Participant> participantList = (ArrayList<Participant>) doc.get("participants");
                            ModelEvent modelEvent = new ModelEvent(doc.getString("id"), doc.getString("title"), participantList);
                            modelEvents.add(modelEvent);

                        }

                        EventActivity ea = (EventActivity) context;
                        ea.showData(modelEvents);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void uploadData(Context context, Map<String, Object> evnt, String id) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setTitle("adding data");
        pd.show();

        db.collection("Events").document(id).set(evnt)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(context, "uplaoad", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void getAllParticipant(Context context, String titleEvent) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        this.participantList.clear();
        this.modelEvents.clear();

        progressDialog.setTitle(titleEvent);
        progressDialog.show();


        db.collection("Events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressDialog.dismiss();


                        modelEvents = task.getResult().toObjects(ModelEvent.class);


                        for (ModelEvent event : modelEvents) {
                            if (event.getTitle().equals(titleEvent)) {
                                if (event.getParticipants().size() > 0) {
                                    for (Participant participant : event.getParticipants()) {
                                        participantList.add(participant);
                                    }
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

    public void getAmountPerPerson(Context context, String title, boolean tricount) {
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

                        Hashtable<String, Double> tableParticipant = new Hashtable<String, Double>();
                        double montantParticipant = 0.0;
                        ArrayList<Participant> participantMontantList = new ArrayList<Participant>();

                        //On récupère l'event
                        for (ModelEvent event : modelEvents) {
                            if (event.getTitle().equals(title)) {

                                //On compte le montant de chaque participant
                                for (Participant part : event.getParticipants()) {
                                    if (tableParticipant.get(part.getName()) != null) {
                                        montantParticipant = tableParticipant.get(part.getName());
                                    }
                                    tableParticipant.put(part.getName(), montantParticipant + part.getMontant());
                                    montantParticipant = 0.0;
                                }


                                Set<String> keys = tableParticipant.keySet();

                                //On met les résultats dans une liste pour afficher
                                for (String namePart : keys) {
                                    participantMontantList.add(new Participant(namePart, "", tableParticipant.get(namePart)));
                                }

                            }
                        }

                        if (!tricount) {
                            ComptageActivity ca = (ComptageActivity) context;
                            ca.updateInterface(participantMontantList);
                        } else {
                            TricountActivity ta = (TricountActivity) context;
                            ta.tricount(participantMontantList);
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                //  Toast.makeText(EventActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void addParticipant(Context context, String newString, Participant participant) {
        ProgressDialog pd = new ProgressDialog(context);
        ModelEvent modelEvent = new ModelEvent();

        pd.setTitle("adding participant");
        pd.show();

        db.collection("Events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        pd.dismiss();

                        modelEvents = task.getResult().toObjects(ModelEvent.class);

                        for (int i = 0; i < modelEvents.size(); i++) {

                            if (modelEvents.get(i).getTitle().equals(newString)) {
                                modelEvents.get(i).getParticipants().add(participant);
                                modelEvent.setId(modelEvents.get(i).getId());
                                modelEvent.setTitle(modelEvents.get(i).getTitle());
                                modelEvent.setParticipants(modelEvents.get(i).getParticipants());
                            }
                        }

                        db.collection("Events").document(newString).update("participants", modelEvent.getParticipants());

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

    public void deleteEvent(Context context, ModelEvent event) {
        db.collection("Events").document(event.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Event deleted", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        getAllEvent(context);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error, Event not deleted", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

    }

    public void deleteParticipantEvent(Context context, Participant participant) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        this.participantList.clear();
        this.modelEvents.clear();

        progressDialog.setTitle(participant.getName());
        progressDialog.show();


        db.collection("Events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressDialog.dismiss();

                        ModelEvent e = new ModelEvent();
                        modelEvents = task.getResult().toObjects(ModelEvent.class);


                        for (int i = 0; i < modelEvents.size(); i++) {
                            if (modelEvents.get(i).getTitle().equals(participant.getNamedoc())) {
                                e=modelEvents.get(i);
                                if (modelEvents.get(i).getParticipants().size() > 0) {
                                    for (int j = 0; j < modelEvents.get(i).getParticipants().size(); j++) {

                                        participantList.add(modelEvents.get(i).getParticipants().get(j));
                                    }
                                }
                            }

                        }
                        for (Participant c : participantList) {
                            if (c.getName().equals(participant.getName())) {
                            } else {
                                newparticipantList.add(c);
                            }
                        }


                        db.collection("Events").document(participant.getName()).update("participants", newparticipantList);

                        ViewEvent ve = (ViewEvent) context;
                        ve.updateInterface(newparticipantList);


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                //  Toast.makeText(EventActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
