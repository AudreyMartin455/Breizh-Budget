package com.example.breizhbudget.ui.dette;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.R;
import com.example.breizhbudget.ui.budgets.ModelBudgets;
import com.example.breizhbudget.ui.budgets.ViewHolderBudget;

import java.util.List;

public class DetteAdapter extends RecyclerView.Adapter<ViewHolderDette> {

    DettePretActivity activity;
    List<ModelDette> modelDetteList;

    public DetteAdapter(DettePretActivity activity, List<ModelDette> modelDetteList) {
        this.activity = activity;
        this.modelDetteList = modelDetteList;
    }

    @NonNull
    @Override
    public ViewHolderDette onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dette, parent , false);

        ViewHolderDette viewHolder = new ViewHolderDette(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDette holder, int position) {

        //bind view setdata
        holder.beneficiaire.setText(modelDetteList.get(position).getBeneficiaire());
        holder.montant.setText(modelDetteList.get(position).getMontant()+" €");
        if(modelDetteList.get(position).isSign()){
            holder.sign.setText(" + ");
        }else{
            holder.sign.setText(" - ");
        }


    }

    @Override
    public int getItemCount() {
        return modelDetteList.size();
    }
}
