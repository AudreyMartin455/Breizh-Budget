package com.example.breizhbudget.ui.budgets;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.R;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<ViewHolderTransaction> {



    OneBudgetActivity activity;
    List<ModelTransaction> transList;


    public TransactionAdapter(OneBudgetActivity activity, List<ModelTransaction> transList) {
        this.activity = activity;
        this.transList = transList;

    }



    @NonNull
    @Override
    public ViewHolderTransaction onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent , false);
        //handle item clicks here

        ViewHolderTransaction viewHolder = new ViewHolderTransaction(itemView);
        viewHolder.onClickListener(new ViewHolderTransaction.ClickListener() {
            @Override
            public void onItemclick(View view, int position) {
                //this will be called when user click item

            }


        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTransaction holder, int position) {

        //bind view setdata
        holder.description.setText(transList.get(position).getDescription());
        holder.montant_trans.setText(transList.get(position).getMontantTransaction()+" €");
        if(transList.get(position).isSign()){
            holder.sign.setText(" + ");
        }else{
            holder.sign.setText(" - ");
        }
    }

    @Override
    public int getItemCount() {
        return transList.size();
    }
}