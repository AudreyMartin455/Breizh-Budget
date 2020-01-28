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
import java.util.List;
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

           int sum = this.sum(valuesPaid);
           int mean = sum / peoples.size();


       }

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
}
