package com.example.breizhbudget;


import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Classe qui utilise Firebase
 */

public class Repository {

    private static Repository repository;
    private FirebaseFirestore db;

    private Repository(){
        this.db = FirebaseFirestore.getInstance();
    }

    public static synchronized Repository getInstance(){
        if(repository == null){
            repository = new Repository();
        }
        return repository;
    }

    /**************
     *
     *
     *  Budget's methods
     *
     *
     ***************/


    /**
     * TODO changer Void en doc ?
     */
    public void getAllBudget(){

    }

    /**
     * Add a new Budget
     */
    public void addBudget(){

    }

    /**************
     *
     *
     *  Dette's methods
     *
     *
     ***************/



    /**************
     *
     *
     *  Event's methods
     *
     *
     ***************/
}
