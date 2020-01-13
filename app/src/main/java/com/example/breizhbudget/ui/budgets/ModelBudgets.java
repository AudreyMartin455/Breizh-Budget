package com.example.breizhbudget.ui.budgets;

public class ModelBudgets {
    private String name;
    private int montant;

    public ModelBudgets(String name, int montant) {
        this.name = name;
        this.montant = montant;
    }

    public String getName() {
        return name;
    }

    public int getMontant() {
        return montant;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }
}
