package com.example.breizhbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Event extends AppCompatActivity {

    EditText mtitle;
    Button madd;
    ProgressDialog pd;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event2);

        madd=findViewById(R.id.button4);
        mtitle=findViewById(R.id.editText2222);
        List<Participant> participantList = new ArrayList<>();

        pd = new ProgressDialog(this);
        db =FirebaseFirestore.getInstance();

        madd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title= mtitle.getText().toString().trim();
                uploadDta(title,participantList);
            }

        });


    }

    private void uploadDta(String title, List<Participant> participantList) {
        pd.setTitle("adding data");
        pd.show();
        String id = title;
        Map<String, Object> evnt= new HashMap<>();
        evnt.put("id",title);
        evnt.put("title",title);
        evnt.put("participants",participantList);
        db.collection("Events").document(id).set(evnt)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(Event.this, "uplaoad",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Event.this, e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        Intent intent = new Intent(Event.this, EventActivity.class);
        startActivity(intent);
    }


}
