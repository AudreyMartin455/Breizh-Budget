package com.example.breizhbudget.ui.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository.RepositoryEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Classe qui liste les participants d'un évènement
 */
public class ViewEvent extends AppCompatActivity {
    @BindView(R.id.recycler_view_participant)
    RecyclerView mR;
    @BindView(R.id.buttonComptage)
    Button buttonComptage;
    @BindView(R.id.add_participant)
    Button buttonAddPart;

    RepositoryEvent repository;

    Viewitemadapter eventAdapter;
    RecyclerView.LayoutManager layoutManager;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        ButterKnife.bind(this);

        //initialize view
        this.mR.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.mR.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        title=intent.getStringExtra("title");

        this.repository = RepositoryEvent.getInstance();
        this.repository.getAllParticipant(this,title);

        buttonComptage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentComptage = new Intent(ViewEvent.this,ComptageActivity.class);
                intentComptage.putExtra("title", title);
                startActivity(intentComptage);
            }
        });

        buttonAddPart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intentParticipant = new Intent(ViewEvent.this,ParticipantActivity.class);
                intentParticipant.putExtra("title", title);
                startActivity(intentParticipant);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.burgermenu, menu);
        return true;
    }

    public void updateInterface(List<Participant> participantList){
        for (int i = 0; i < participantList.size(); i++)
            //Log.d("value is efeffeffefefe", modelEvents.get(i).toString());
            eventAdapter = new Viewitemadapter(ViewEvent.this, participantList);
        mR.setAdapter(eventAdapter);
    }


}
