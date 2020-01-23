package com.example.breizhbudget.ui.event;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository.RepositoryEvent;
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



    @BindView(R.id.add_button)
    Button addbutton;
    @BindView(R.id.recyclerListEvent)
    RecyclerView recyclerListEvent;

    RecyclerView.LayoutManager layoutManager;
    EventAdapter eventAdapter;

    ProgressDialog progressDialog;

    RepositoryEvent repository;
    List<ModelEvent> modelEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        //On récupère le repo pour les requêtes vers firebase
        this.repository = RepositoryEvent.getInstance();

        //On initialise la vue
        this.recyclerListEvent.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerListEvent.setLayoutManager(layoutManager);

        //On demande au repo de prendre tous les events
        repository.getAllEvent(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.burgermenu, menu);
        return true;
    }

    /**
     * Appelée pour mettre à jour l'interface.
     * @param modelEvents liste des evenements mise à jour par le repo
     */
    public  void showData(List<ModelEvent> modelEvents){
        this.modelEvents = modelEvents;
        for (int i = 0 ; i < modelEvents.size() ; i++)
            Log.d("value is" , modelEvents.get(i).toString());
        eventAdapter = new EventAdapter(EventActivity.this,modelEvents);
        recyclerListEvent.setAdapter(eventAdapter);
    }

}
