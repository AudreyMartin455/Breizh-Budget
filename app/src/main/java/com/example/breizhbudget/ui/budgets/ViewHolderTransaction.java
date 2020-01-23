package com.example.breizhbudget.ui.budgets;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.R;

public class ViewHolderTransaction extends RecyclerView.ViewHolder {

    TextView description;
    TextView sign;
    TextView montant_trans;
    Button deleteTransaction;
    View mView;

    public ViewHolderTransaction(@NonNull View itemView) {
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

        this.description = itemView.findViewById(R.id.description);
        this.sign = itemView.findViewById(R.id.sign);
        this.montant_trans = itemView.findViewById(R.id.montant_trans);
        this.deleteTransaction = itemView.findViewById(R.id.deleteTransaction);

        deleteTransaction.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                OneBudgetActivity ba = (OneBudgetActivity) v.getContext();
                ba.deleteTransaction(getAdapterPosition());
            }
        });

    }


    // interface for click listener
    private ViewHolderTransaction.ClickListener mclicklistener;
    public interface ClickListener{
        void  onItemclick(View view, int position);

    }


    public  void onClickListener(ViewHolderTransaction.ClickListener clickListener){
        mclicklistener=clickListener;
    }
}