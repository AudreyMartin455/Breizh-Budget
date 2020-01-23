package com.example.breizhbudget;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.ui.budgets.BudgetsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ComptageActivity extends AppCompatActivity {

    List<ModelEvent> modelEvents = new ArrayList<ModelEvent>();
    ArrayList<Participant> participantList = new ArrayList<Participant>();
    RecyclerView mR;
    RecyclerView.LayoutManager layoutManager;

    // firestore instance
    FirebaseFirestore db;
    ComptageAdapter comptageAdapter;
    ProgressDialog progressDialog;
    String newString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_comptage);

        // init firestore
        db = FirebaseFirestore.getInstance();

        //initialize view
        mR = findViewById(R.id.recycler_view_comptage);
        mR.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mR.setLayoutManager(layoutManager);

        progressDialog = new ProgressDialog(this);
        Intent intent = getIntent();
        newString=intent.getStringExtra("title");
        showDta(newString);
    }

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


        switch (id){
            case R.id.burgerMenu_Budget:
                Intent intentBudget = new Intent(ComptageActivity.this, BudgetsActivity.class);
                startActivity(intentBudget);

            case R.id.burgerMenu_Dettes:
                Toast.makeText(getApplicationContext(),"Prochainement Disponible !", Toast.LENGTH_SHORT).show();

            case R.id.burgerMenu_Event:
                Intent intentEvent = new Intent(ComptageActivity.this, EventActivity.class);
                startActivity(intentEvent);

            case R.id.burgerMenu_Parametre:
                Toast.makeText(getApplicationContext(),"Prochainement Disponible !", Toast.LENGTH_SHORT).show();

            case R.id.burgerMenu_Contacts:
                Toast.makeText(getApplicationContext(),"Prochainement Disponible !", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public  void showDta(String newString1) {


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
                        // Participant p= new Participant("zz555555555555","dd",14);
                        //participantList.add(modelEvents.get(0).getParticipants().get(0));


                        Hashtable tableParticipant = new Hashtable();
                        String nomParticipant;
                        int montantParticipant;
                        ArrayList<String> listNomParticipant = new ArrayList<String>();
                        for (int i = 0; i < participantList.size(); i++) {
                            nomParticipant = participantList.get(i).getName();
                            montantParticipant = participantList.get(i).getMontant();
                            if (tableParticipant.containsKey(nomParticipant)) {
                                montantParticipant +=  (int) tableParticipant.get(nomParticipant);
                                tableParticipant.remove(nomParticipant);
                                tableParticipant.put(nomParticipant, montantParticipant);
                            } else {
                                tableParticipant.put(nomParticipant, montantParticipant);
                                listNomParticipant.add(nomParticipant);
                            }
                        }

                        ArrayList<Participant> participantMontantList = new ArrayList<Participant>();
                        for (int i = 0; i < listNomParticipant.size(); i++) {
                            String nomActuel = listNomParticipant.get(i);
                            int montantTotal = (int) tableParticipant.get(listNomParticipant.get(i));
                            participantMontantList.add(new Participant(nomActuel, "",montantTotal));
                        }

                        for (int i = 0; i < participantMontantList.size(); i++){
                            //Log.d("value is efeffeffefefe", modelEvents.get(i).toString());
                            comptageAdapter = new ComptageAdapter(ComptageActivity.this, participantMontantList);
                            mR.setAdapter(comptageAdapter);
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


}
