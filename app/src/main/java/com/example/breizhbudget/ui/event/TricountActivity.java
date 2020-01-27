package com.example.breizhbudget.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository.RepositoryEvent;

import java.util.List;

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

        this.repository.getAllParticipant(this,event.getTitle(),true);

    }

    public void tricount(List<Participant> participantsList){
        this.participants = participantsList;
        for(Participant participant : participantsList){
            Log.d(">>>>>",participant.getName() + " " + participant.getMontant());
        }

    }
}
