package com.example.breizhbudget.ui.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository.RepositoryEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Classe Activity qui crée un Event
 */
public class Event extends AppCompatActivity {

    @NonNull
    @BindView(R.id.editText2222)
    EditText mtitle;
    @BindView(R.id.button4)
    Button madd;

    private RepositoryEvent repository;
    private List<Participant> participantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event2);
        ButterKnife.bind(this);


        this.repository = RepositoryEvent.getInstance();

        this.participantList = new ArrayList<>();

        //On set le listener du bouton
        madd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title= mtitle.getText().toString().trim();
                uploadDta(title,participantList);
            }

        });


    }

    /**
     * Demande au repo d'upload un nouvel event à Firebase
     * @param title titre de l'event
     * @param participantList liste des participants
     */
    private void uploadDta(String title, List<Participant> participantList) {

        String id = title;
        Map<String, Object> evnt= new HashMap<>();
        evnt.put("id",title);
        evnt.put("title",title);
        evnt.put("participants",participantList);

        this.repository.uploadData(this,evnt,id);

        Intent intent = new Intent(Event.this, EventActivity.class);
        startActivity(intent);
    }


}
