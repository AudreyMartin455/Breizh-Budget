package com.example.breizhbudget.ui.budgets;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.R;

import java.util.List;

public class BudgetsAdapter extends RecyclerView.Adapter<ViewHolder> {



    BudgetsActivity listActivity;
    List<ModelBudgets> modelBudgetsList;


    public BudgetsAdapter(BudgetsActivity listActivity, List<ModelBudgets> modelBudgetsList) {
        this.listActivity = listActivity;
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
                String title = modelBudgetsList.get(position).getName();
                long montant = modelBudgetsList.get(position).getMontant();
                Toast.makeText(listActivity,title+"\n"+montant,Toast.LENGTH_SHORT).show();
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