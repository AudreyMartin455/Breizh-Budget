package com.example.breizhbudget.ui.budgets;

public class ModelTransaction {

    private String idTransaction;
    private String idBudget;
    private String description;
    private boolean sign;
    private long montantTransaction;

    public ModelTransaction(){}

    public ModelTransaction(String idTrans){
        this.idTransaction = idTrans;
    }

    public ModelTransaction(String desc, boolean sign, long montant){
        this.description = desc;
        this.sign = sign;
        this.montantTransaction = montant;
    }

    public ModelTransaction(String id, String idBudget, String desc, boolean sign, long montant){
        this.idTransaction = id;
        this.idBudget = idBudget;
        this.description = desc;
        this.sign = sign;
        this.montantTransaction = montant;
    }

    public ModelTransaction(String idBudget, String desc, boolean sign, long montant){
        this.idBudget = idBudget;
        this.description = desc;
        this.sign = sign;
        this.montantTransaction = montant;
    }

    public String getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(String idBudget) {
        this.idBudget = idBudget;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    public long getMontantTransaction() {
        return montantTransaction;
    }

    public void setMontantTransaction(long montantTransaction) {
        this.montantTransaction = montantTransaction;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

}
