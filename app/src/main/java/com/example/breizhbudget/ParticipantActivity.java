package com.example.breizhbudget;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.Document;

import java.util.ArrayList;
import java.util.List;

public class ParticipantActivity extends AppCompatActivity {

    EditText mNameParticipant;
    EditText mMontant;
    EditText mDescription;

    Button madd;
    ProgressDialog pd;
    FirebaseFirestore db;
    String newString;

    Document document;
    List<ModelEvent> modelEvents = new ArrayList<ModelEvent>();
    ArrayList<Participant> participantList = new ArrayList<Participant>();
    ModelEvent modelEvent = new ModelEvent() ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);
        madd=findViewById(R.id.Addparticipant);
        mNameParticipant=findViewById(R.id.ParticipantName);
        mMontant=findViewById(R.id.Montant);
        mDescription=findViewById(R.id.Description);

        pd = new ProgressDialog(this);
        db =FirebaseFirestore.getInstance();

        madd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name= mNameParticipant.getText().toString().trim();
                int Montant =Integer.parseInt(mMontant.getText().toString().trim());
                String Description = mDescription.getText().toString().trim();
                Participant participant = new Participant(Name,Description,Montant);
                Intent intent = getIntent();
                newString=intent.getStringExtra("title");
                uploadDta(newString,participant);
            }

        });
    }


    private void uploadDta(String newString, Participant participant) {
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




                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                //Toast.makeText(EventActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        Intent intent = new Intent(ParticipantActivity.this, ViewEvent.class);
        intent.putExtra("title", newString);
        startActivity(intent);
    }
}
