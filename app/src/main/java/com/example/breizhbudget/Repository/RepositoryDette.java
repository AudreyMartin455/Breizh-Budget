package com.example.breizhbudget.Repository;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.breizhbudget.ui.dette.ModelDette;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RepositoryDette {

    private static RepositoryDette repository;
    private FirebaseFirestore db;
    private final String TAG = ">>>>>";

    private RepositoryDette() {this.db = FirebaseFirestore.getInstance();}

    public static synchronized RepositoryDette getInstance(){
        if(repository == null){
            repository = new RepositoryDette();
        }
        return repository;
    }
    
}
