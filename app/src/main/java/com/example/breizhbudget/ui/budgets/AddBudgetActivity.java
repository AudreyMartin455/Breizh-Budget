package com.example.breizhbudget.ui.budgets;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.breizhbudget.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBudgetActivity extends AppCompatActivity {

    private FirebaseFirestore  db;
    @BindView(R.id.intitule)
    EditText name;
    @BindView(R.id.montant)
    EditText montant;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbudget);
        ButterKnife.bind(this);

        db = FirebaseFirestore.getInstance();
    }

    @OnClick(R.id.ajouter)
    public void addBudget() {

        Toast.makeText(this,"TODO: Faire l'ajout de Budget",Toast.LENGTH_SHORT).show();
       /* Map<String, Object> city = new HashMap<>();

        city.put("name", name.getText());
        city.put("montant", Long.parseLong(montant.getText().toString()));


        db.collection("cities").document("LA")
        .set(city)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Success : ", "DocumentSnapshot successfully written!");
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Failure : ", "Error writing document", e);
            }
        });*/

    }
}
