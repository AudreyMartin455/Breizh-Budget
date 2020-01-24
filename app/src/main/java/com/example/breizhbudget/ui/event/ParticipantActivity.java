package com.example.breizhbudget.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository.RepositoryEvent;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParticipantActivity extends AppCompatActivity {

    @NotEmpty
    @BindView(R.id.ParticipantName)
    EditText mNameParticipant;
    @NotEmpty
    @BindView(R.id.Montant)
    EditText mMontant;
    @NotEmpty
    @BindView(R.id.Description)
    EditText mDescription;
    @BindView(R.id.Addparticipant)
    Button madd;

    RepositoryEvent repository;
    String newString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);
        madd=findViewById(R.id.Addparticipant);
        ButterKnife.bind(this);

        this.repository = RepositoryEvent.getInstance();

        madd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name= mNameParticipant.getText().toString().trim();
                int Montant =Integer.parseInt(mMontant.getText().toString().trim());
                String Description = mDescription.getText().toString().trim();
                Participant participant = new Participant(Name,Description,Montant);
                Intent intent = getIntent();
                newString=intent.getStringExtra("title");
                repository.addParticipant(ParticipantActivity.this,newString,participant);
            }

        });
    }


    public void returnToViewEvent(){
        Intent intent = new Intent(ParticipantActivity.this, ViewEvent.class);
        intent.putExtra("title", newString);
        startActivity(intent);
    }
}