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

public class BudgetsAdapter extends RecyclerView.Adapter<ViewHolder> {



    BudgetsActivity activity;
    List<ModelBudgets> modelBudgetsList;


    public BudgetsAdapter(BudgetsActivity activity, List<ModelBudgets> modelBudgetsList) {
        this.activity = activity;
        this.modelBudgetsList = modelBudgetsList;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_budget, parent , false);
        //handle item clicks here

        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.onClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemclick(View view, int position) {
                //this will be called when user click item
                ModelBudgets budget = modelBudgetsList.get(position);
                activity.show1Budget(budget);
            }


        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //bind view setdata
        holder.nameBudget.setText(modelBudgetsList.get(position).getName());
        holder.montantBudget.setText(modelBudgetsList.get(position).getMontant()+"");


    }

    @Override
    public int getItemCount() {
        return modelBudgetsList.size();
    }
}