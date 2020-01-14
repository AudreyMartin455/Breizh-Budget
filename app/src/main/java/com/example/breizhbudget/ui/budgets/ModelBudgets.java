package com.example.breizhbudget.ui.budgets;

public class ModelBudgets {
    private String id;
    private String name;
    private long montant;

    public ModelBudgets(){}

    public ModelBudgets(String id, String name, long montant) {
        this.id = id;
        this.name = name;
        this.montant = montant;
    }

    public String getName() {
        return name;
    }

    public long getMontant() {
        return montant;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMontant(long montant) {
        this.montant = montant;
    }
}
