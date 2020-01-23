package com.example.breizhbudget;

public class Participant {

    String Name, Description;
    int Montant;

    public Participant(){}

    public Participant(String name, String description, int montant) {
        Name = name;
        Description = description;
        Montant = montant;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setMontant(int montant) {
        Montant = montant;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public int getMontant() {
        return Montant;
    }
}
