package com.example.breizhbudget.ui.dette;


public class ModelDette {

    private String id;
    private String beneficiaire;
    private String description;
    private Double montant;
    private boolean sign;

    public ModelDette(String id) {
        this.id = id;
    }

    public ModelDette(String id, String beneficiaire, String description, Double montant, Boolean sign) {
        this.id = id;
        this.beneficiaire = beneficiaire;
        this.description = description;
        this.montant = montant;
        this.sign = sign;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBeneficiaire() {
        return beneficiaire;
    }

    public void setBeneficiaire(String beneficiaire) {
        this.beneficiaire = beneficiaire;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }
}
