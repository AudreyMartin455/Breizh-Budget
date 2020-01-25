package com.example.breizhbudget.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.breizhbudget.R;
import com.example.breizhbudget.Repository.RepositoryEvent;
import com.example.breizhbudget.ui.budgets.BudgetsActivity;
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


    /**
     * Fonction pour afficher le bouton retour dans l'action bar
     */
    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
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
                Intent intentBudget = new Intent(ParticipantActivity.this, BudgetsActivity.class);
                startActivity(intentBudget);

            case R.id.burgerMenu_Dettes:
                Toast.makeText(getApplicationContext(),"Prochainement Disponible !", Toast.LENGTH_SHORT).show();

            case R.id.burgerMenu_Event:
                Intent intentEvent = new Intent(ParticipantActivity.this, EventActivity.class);
                startActivity(intentEvent);

            case R.id.burgerMenu_Parametre:
                Toast.makeText(getApplicationContext(),"Prochainement Disponible !", Toast.LENGTH_SHORT).show();

            case R.id.burgerMenu_Contacts:
                Toast.makeText(getApplicationContext(),"Prochainement Disponible !", Toast.LENGTH_SHORT).show();

            case R.id.home: // Option pour le retour
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void returnToViewEvent(){
        Intent intent = new Intent(ParticipantActivity.this, ViewEvent.class);
        intent.putExtra("title", newString);
        startActivity(intent);
    }
}
