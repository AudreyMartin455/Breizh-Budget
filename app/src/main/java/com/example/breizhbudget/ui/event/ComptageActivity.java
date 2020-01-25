package com.example.breizhbudget.ui.event;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository.RepositoryEvent;
import com.example.breizhbudget.ui.budgets.BudgetsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComptageActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_comptage)
    RecyclerView mR;

    RecyclerView.LayoutManager layoutManager;
    ComptageAdapter comptageAdapter;

    RepositoryEvent repository;
    String newString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_comptage);
        ButterKnife.bind(this);

        mR.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mR.setLayoutManager(layoutManager);

        this.repository = RepositoryEvent.getInstance();

        Intent intent = getIntent();
        newString=intent.getStringExtra("title");
        this.repository.getAmountPerPerson(this, newString);
    }

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


        switch (id){
            case R.id.burgerMenu_Budget:
                Intent intentBudget = new Intent(ComptageActivity.this, BudgetsActivity.class);
                startActivity(intentBudget);

            case R.id.burgerMenu_Dettes:
                Toast.makeText(getApplicationContext(),"Prochainement Disponible !", Toast.LENGTH_SHORT).show();

            case R.id.burgerMenu_Event:
                Intent intentEvent = new Intent(ComptageActivity.this, EventActivity.class);
                startActivity(intentEvent);

            case R.id.burgerMenu_Parametre:
                Toast.makeText(getApplicationContext(),"Prochainement Disponible !", Toast.LENGTH_SHORT).show();

            case R.id.burgerMenu_Contacts:
                Toast.makeText(getApplicationContext(),"Prochainement Disponible !", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateInterface(ArrayList<Participant> participantMontantList){
        for (int i = 0; i < participantMontantList.size(); i++){
            comptageAdapter = new ComptageAdapter(ComptageActivity.this, participantMontantList);
            mR.setAdapter(comptageAdapter);
        }
    }


}
