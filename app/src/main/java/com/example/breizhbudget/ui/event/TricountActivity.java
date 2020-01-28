package com.example.breizhbudget.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository.RepositoryEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import butterknife.ButterKnife;

public class TricountActivity  extends AppCompatActivity {

    private ModelEvent event;
    private List<Participant> participants;
    private RepositoryEvent repository;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tricount);
        ButterKnife.bind(this);

        this.repository = RepositoryEvent.getInstance();

        Intent intent = getIntent();
        ModelEvent event = new ModelEvent(intent.getStringExtra("title"));

        this.repository.getAmountPerPerson(this,event.getTitle(),true);

    }

    public void tricount(List<Participant> participantsList){
       if(participantsList.size()>0){
           List<String> peoples = this.getAllNames(participantsList);
           List<Integer> valuesPaid = this.getMontants(participantsList);
           HashMap<String,Integer> peopleValues = this.toHashMap(participantsList);

           int sum = this.sum(valuesPaid);
           int mean = sum / peopleValues.size();

           peopleValues = this.sortHashMapByValues(peopleValues);

           for (Map.Entry mapentry : peopleValues.entrySet()) {
               Log.d(">>>>>>","clé: "+mapentry.getKey() + " | valeur: " + mapentry.getValue());
           }
           // pour tous les noms dans la Hashmap, on leur enlève mean.

       }

    }

    public HashMap<String,Integer> toHashMap(List<Participant> participants){
        HashMap<String,Integer> peopleValues = new HashMap<>();

        for(Participant p: participants){
            peopleValues.put(p.getName(),p.getMontant());
        }

        return  peopleValues;
    }


    public List<String> getAllNames(List<Participant> participantsList){
        List<String> names = new ArrayList<>();
        for(Participant p : participantsList){
            names.add(p.getName());
        }

        return names;
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

    public HashMap<String, Integer> sortHashMapByValues(HashMap<String, Integer> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        HashMap<String, Integer> sortedMap =
                new HashMap<>();

        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Integer val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Integer comp1 = passedMap.get(key);
                Integer comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
}
