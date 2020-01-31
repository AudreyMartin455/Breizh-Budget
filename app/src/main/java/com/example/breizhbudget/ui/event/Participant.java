package com.example.breizhbudget.ui.event;

public class Participant {

    String namedoc;


    String Name, Description;
    double Montant;

    public Participant() {
    }

    public Participant(String name, String namedoc) {
        this.Name = name;
        this.namedoc = namedoc;
    }

    public Participant(String name, String description, double montant) {
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

    public void setMontant(double montant) {
        Montant = montant;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public double getMontant() {
        return Montant;
    }

    public String getNamedoc() { return namedoc; }
}
