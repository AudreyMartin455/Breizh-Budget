package com.example.breizhbudget.ui.budgets;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelBudgets implements Parcelable {
    private String id;
    private String name;
    private long montant;

    public ModelBudgets(){}

    public ModelBudgets(String id){
        this.id = id;
    }

    public ModelBudgets(String name, long montant){
        this.name = name;
        this.montant = montant;
    }

    public ModelBudgets(String id, String name, long montant) {
        this.name = name;
        this.montant = montant;
        this.id = id;
    }

    protected ModelBudgets(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.montant = in.readLong();
    }


    public String getId() { return id; }

    public String getName() {
        return name;
    }

    public long getMontant() {
        return montant;
    }

    public void setId(String id) { this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setMontant(long montant) {
        this.montant = montant;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeLong(montant);
    }

    public static final Creator<ModelBudgets> CREATOR = new Creator<ModelBudgets>() {
        @Override
        public ModelBudgets createFromParcel(Parcel in) {
            return new ModelBudgets(in);
        }

        @Override
        public ModelBudgets[] newArray(int size) {
            return new ModelBudgets[size];
        }
    };

    public String toString(){
        return this.id + " " + this.name + " " + this.montant;
    }
}
