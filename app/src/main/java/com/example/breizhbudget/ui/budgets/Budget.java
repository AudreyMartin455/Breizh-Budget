package com.example.breizhbudget.ui.budgets;

class Budget {

    private String nom;
    private int montant;

    public Budget(String s,int montant) {
        this.nom = nom;
        this.montant = montant;
    }

    public String getName(){
        return this.nom;
    }

    public int getMontant(){
        return montant;
    }
}
