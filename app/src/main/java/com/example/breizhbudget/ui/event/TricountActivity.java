package com.example.breizhbudget.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository.RepositoryEvent;
import com.example.breizhbudget.ui.budgets.BudgetsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.IntStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TricountActivity  extends AppCompatActivity {

    @BindView(R.id.listeTricount)
    TextView listeTricount;

    private String titleEvent;
    private RepositoryEvent repository;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tricount);
        ButterKnife.bind(this);

        this.repository = RepositoryEvent.getInstance();

        Intent intent = getIntent();
        this.titleEvent = intent.getStringExtra("title");

        this.repository.getAmountPerPerson(this,this.titleEvent,true);

    }

    /**
     * Fonction pour afficher le bouton retour dans l'action bar
     */
    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Fonction du burger menu
     *
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
                Intent intentBudget = new Intent(TricountActivity.this, BudgetsActivity.class);
                startActivity(intentBudget);
                return true;

            case R.id.burgerMenu_Dettes:
                Toast.makeText(getApplicationContext(), "Prochainement Disponible !", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.burgerMenu_Event:
                Intent intentEvent = new Intent(TricountActivity.this, EventActivity.class);
                startActivity(intentEvent);
                return true;

            case R.id.burgerMenu_Parametre:
                Toast.makeText(getApplicationContext(), "Prochainement Disponible !", Toast.LENGTH_SHORT).show();

            case R.id.burgerMenu_Contacts:
                Toast.makeText(getApplicationContext(), "Prochainement Disponible !", Toast.LENGTH_SHORT).show();

            case R.id.home: // Option pour le retour
                Intent intentParticipant = new Intent(TricountActivity.this, ViewEvent.class);
                intentParticipant.putExtra("title", this.titleEvent);
                startActivity(intentParticipant);
                return true;
        }
        return false;
    }

    public void tricount(List<Participant> participantsList){

       if(participantsList.size()>0){

           String result = "";
           List<String> peoples = new ArrayList<>();
           List<Integer> valuesPaid = this.getMontants(participantsList);
           HashMap<String,Integer> peopleValues = this.toHashMap(participantsList);

           int sum = this.sum(valuesPaid);
           int mean = sum / peopleValues.size();

           peopleValues = this.sortHashMapByValues(peopleValues);


           for (Map.Entry mapentry : peopleValues.entrySet()) {
               peoples.add(mapentry.getKey().toString());
               peopleValues.replace(mapentry.getKey().toString(), (Integer) mapentry.getValue()-mean);
           }

           int i = 0;
           int j = peopleValues.size()-1;
           int debt;

           while (i < j){
               int valuei = peopleValues.get(peoples.get(i));
               int valuej = peopleValues.get(peoples.get(j));
               debt = Math.min(-(valuei),valuej);
               valuei += debt;
               valuej -= debt;
               peopleValues.replace(peoples.get(i),valuei);
               peopleValues.replace(peoples.get(j),valuej);

               result += peoples.get(i) + " doit " + debt + " € " + " à " +  peoples.get(j) + "\n";
               Log.d(">>>>>>>>>>>>",peoples.get(i) + " doit " + debt + "EUROS" + " à " +  peoples.get(j));


               if(peopleValues.get(peoples.get(i)) == 0){
                    i++;
               }

               if(peopleValues.get(peoples.get(j)) == 0){
                   j--;
               }
           }

            this.updateUI(result);

       }

    }

    public HashMap<String,Integer> toHashMap(List<Participant> participants){
        HashMap<String,Integer> peopleValues = new HashMap<>();

        for(Participant p: participants){
            peopleValues.put(p.getName(),p.getMontant());
        }

        return  peopleValues;
    }


    public List<Integer> getMontants(List<Participant> participantsList){
        List<Integer> montants = new ArrayList<>();
        for(Participant p : participantsList){
            montants.add(p.getMontant());
        }

        return montants;
    }

    public int sum(List<Integer> values){
        int sum = 0;
        for(Integer value : values){
            sum = sum + value;
        }
        return sum;
    }

    public HashMap<String,Integer> sortHashMapByValues(HashMap<String, Integer> passedMap) {
        TreeMap<String, Integer> sorted = new TreeMap<>(passedMap);
        Set<Map.Entry<String, Integer>> mappings = sorted.entrySet();

        Set<Map.Entry<String, Integer>> entries = passedMap.entrySet();

        Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String,Integer>>() {

            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                Integer v1 = e1.getValue();
                Integer v2 = e2.getValue();
                return v1.compareTo(v2);
            }
        };

        // Sort method needs a List, so let's first convert Set to List in Java
        List<Map.Entry<String, Integer>> listOfEntries = new ArrayList<Map.Entry<String, Integer>>(entries);

        // sorting HashMap by values using comparator
        Collections.sort(listOfEntries, valueComparator);

        LinkedHashMap<String, Integer> sortedByValue = new LinkedHashMap<String, Integer>(listOfEntries.size());

        // copying entries from List to Map
        for(Map.Entry<String, Integer> entry : listOfEntries){
            sortedByValue.put(entry.getKey(), entry.getValue());
        }

        Set<Map.Entry<String, Integer>> entrySetSortedByValue = sortedByValue.entrySet();

        passedMap.clear();
        for(Map.Entry<String, Integer> mapping : entrySetSortedByValue){
            passedMap.put(mapping.getKey(),mapping.getValue());
        }

        return passedMap;
    }

    public void updateUI(String affichageTricount){
        this.listeTricount.setText(affichageTricount);
    }
}
