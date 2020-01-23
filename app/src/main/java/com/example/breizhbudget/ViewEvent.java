package com.example.breizhbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ViewEvent extends AppCompatActivity {


    List<ModelEvent> modelEvents = new ArrayList<ModelEvent>();
    ArrayList<Participant> participantList = new ArrayList<Participant>();
    RecyclerView mR;
    RecyclerView.LayoutManager layoutManager;

    // firestore instance
    FirebaseFirestore  db;
    Viewitemadapter eventAdapter;
    ProgressDialog progressDialog;
    String newString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_participant);
        Button buttonComptage = (Button) findViewById(R.id.buttonComptage);


        // init firestore
        db = FirebaseFirestore.getInstance();

        //initialize view
        mR = findViewById(R.id.recycler_view_participant);
        mR.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mR.setLayoutManager(layoutManager);


        progressDialog = new ProgressDialog(this);
        Intent intent = getIntent();
        newString=intent.getStringExtra("title");
        showDta(newString);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewEvent.this,ParticipantActivity.class);
                intent.putExtra("title", newString);
                startActivity(intent);
            }
        });

        buttonComptage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentComptage = new Intent(ViewEvent.this,ComptageActivity.class);
                intentComptage.putExtra("title", newString);
                startActivity(intentComptage);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.burgermenu, menu);
        return true;
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

                        for (int i = 0; i < participantList.size(); i++)
                            //Log.d("value is efeffeffefefe", modelEvents.get(i).toString());
                        eventAdapter = new Viewitemadapter(ViewEvent.this, participantList);
                        mR.setAdapter(eventAdapter);
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
