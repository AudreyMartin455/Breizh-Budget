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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.IntStream;

import butterknife.ButterKnife;

public class TricountActivity  extends AppCompatActivity {

    private ModelEvent event;
    private String titleEvent;
    private List<Participant> participants;
    private RepositoryEvent repository;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tricount);
        ButterKnife.bind(this);

        this.repository = RepositoryEvent.getInstance();

        Intent intent = getIntent();
        this.titleEvent = intent.getStringExtra("title");
        Log.d(">>>>>>>>>><<<", this.titleEvent);
        this.repository.getAmountPerPerson(this,this.titleEvent,true);

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


               Log.d(">>>>>>>>>>>>",peoples.get(i) + " owes " + peoples.get(j) + " " + debt + "EUROS");


               if(peopleValues.get(peoples.get(i)) == 0){
                    i++;
               }

               if(peopleValues.get(peoples.get(j)) == 0){
                   j--;
               }
           }



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

    public HashMap<String,Integer> sortHashMapByValues(HashMap<String, Integer> passedMap) {
        TreeMap<String, Integer> sorted = new TreeMap<>(passedMap);
        Set<Map.Entry<String, Integer>> mappings = sorted.entrySet();

        Set<Map.Entry<String, Integer>> entries = passedMap.entrySet();

       /* System.out.println("HashMap after sorting by keys in ascending order ");
        for(Map.Entry<String, Integer> mapping : mappings){
            System.out.println(mapping.getKey() + " ==> " + mapping.getValue());
        }*/

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
            Log.d(">>>>>>>>><",mapping.getKey() + " ==> " + mapping.getValue());
            passedMap.put(mapping.getKey(),mapping.getValue());
        }

        return passedMap;
    }
}
