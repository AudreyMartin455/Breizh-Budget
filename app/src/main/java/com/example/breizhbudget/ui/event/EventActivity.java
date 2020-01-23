package com.example.breizhbudget.ui.event;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.breizhbudget.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import butterknife.BindView;

public class EventActivity extends AppCompatActivity {



    @BindView(R.id.add_button) Button addbutton;
   // @BindView(R.id.button) Button ViewEvent;

    List<ModelEvent> modelEvents = new ArrayList<>();
    RecyclerView mR;
    RecyclerView.LayoutManager layoutManager;

    // firestore instance
    FirebaseFirestore  db;
    EventAdapter eventAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        // init firestore
        db = FirebaseFirestore.getInstance();

        //initialize view
        mR = findViewById(R.id.recycler_view);
        mR.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mR.setLayoutManager(layoutManager);


        Button fab = (Button) findViewById(R.id.add_button);




        progressDialog = new ProgressDialog(this);
        showDta();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventActivity.this, Event.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.burgermenu, menu);
        return true;
    }

    public  void showDta(){


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
                            ArrayList <Participant> participantList = (ArrayList<Participant>) doc.get("participants");
                            ModelEvent modelEvent =new ModelEvent(doc.getString("id"), doc.getString("title"), participantList);
                            modelEvents.add(modelEvent);

                        }

                        for (int i = 0 ; i < modelEvents.size() ; i++)
                            Log.d("value is" , modelEvents.get(i).toString());
                        eventAdapter = new EventAdapter(EventActivity.this,modelEvents);
                        mR.setAdapter(eventAdapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(EventActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }




}
