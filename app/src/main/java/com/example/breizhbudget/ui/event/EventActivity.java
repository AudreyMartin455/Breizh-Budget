package com.example.breizhbudget.ui.event;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.breizhbudget.Accueil;
import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository.RepositoryEvent;
import com.example.breizhbudget.ui.budgets.BudgetsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Classe qui liste tous les évènements
 */
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
        ButterKnife.bind(this);


        //On récupère le repo pour les requêtes vers firebase
        this.repository = RepositoryEvent.getInstance();

        //On initialise la vue
        this.recyclerListEvent.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerListEvent.setLayoutManager(layoutManager);

        //On demande au repo de prendre tous les events
        repository.getAllEvent(this);
    }

    /**
     * Fonction pour afficher le bouton retour dans l'action bar
     */
    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Fonction du burger menu
     * @param menu
     * @return
     */
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


        switch (id) {
            case R.id.burgerMenu_Budget:
                Intent intentBudget = new Intent(EventActivity.this, BudgetsActivity.class);
                startActivity(intentBudget);
                return true;

            case R.id.burgerMenu_Dettes:
                Toast.makeText(getApplicationContext(), "Prochainement Disponible !", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.burgerMenu_Event:
                Intent intentEvent = new Intent(EventActivity.this, EventActivity.class);
                startActivity(intentEvent);
                return true;

            case R.id.burgerMenu_Parametre:
                Toast.makeText(getApplicationContext(), "Prochainement Disponible !", Toast.LENGTH_SHORT).show();

            case R.id.burgerMenu_Contacts:
                Toast.makeText(getApplicationContext(), "Prochainement Disponible !", Toast.LENGTH_SHORT).show();

            case R.id.home: // Option pour le retour
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return false;
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

    /**
     * Envoie sur l'activity Event lorsque le bouton est cliqué
     */
    @OnClick(R.id.add_button)
    public void addEventActivity(){
        Intent intent = new Intent(EventActivity.this, Event.class);
        startActivity(intent);
    }

}
