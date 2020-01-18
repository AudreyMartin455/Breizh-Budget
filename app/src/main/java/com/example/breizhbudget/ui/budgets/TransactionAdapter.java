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

public class TransactionAdapter extends RecyclerView.Adapter<ViewHolder> {



    OneBudgetActivity activity;
    List<ModelTransaction> transList;


    public TransactionAdapter(OneBudgetActivity activity, List<ModelTransaction> transList) {
        this.activity = activity;
        this.transList = transList;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent , false);
        //handle item clicks here

        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.onClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemclick(View view, int position) {
                //this will be called when user click item

            }


        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //bind view setdata
       // holder.nameBudget.setText(transList.get(position).getName());
       // holder.montantBudget.setText(transList.get(position).getMontant()+"");


    }

    @Override
    public int getItemCount() {
        return transList.size();
    }
}