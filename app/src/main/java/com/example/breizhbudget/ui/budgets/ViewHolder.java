package com.example.breizhbudget.ui.budgets;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView nameBudget;
    TextView montantBudget;
    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        //item click

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mclicklistener.onItemclick(v, getAdapterPosition());

            }
        });

        // initialize views with Eventmodel layout

        nameBudget = itemView.findViewById(R.id.nom_budget);
        montantBudget = itemView.findViewById(R.id.montant_budget);

    }


    // interface for click listener
    private ViewHolder.ClickListener mclicklistener;
    public interface ClickListener{
        void  onItemclick(View view, int position);

    }


    public  void onClickListener(ViewHolder.ClickListener clickListener){
        mclicklistener=clickListener;
    }
}