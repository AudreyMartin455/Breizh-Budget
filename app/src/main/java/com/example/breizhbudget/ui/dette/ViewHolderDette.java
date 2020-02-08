package com.example.breizhbudget.ui.dette;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.R;

public class ViewHolderDette extends RecyclerView.ViewHolder {

    TextView beneficiaire;
    TextView sign;
    TextView montant;
    Button deleteDette;
    View mView;

    public ViewHolderDette(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mclicklistener.onItemclick(v, getAdapterPosition());

            }
        });

        // initialize views with Eventmodel layout

        this.beneficiaire = itemView.findViewById(R.id.beneficiaire);
        this.sign = itemView.findViewById(R.id.signDette);
        this.montant = itemView.findViewById(R.id.montantDette);
        this.deleteDette = itemView.findViewById(R.id.deleteDette);
    }

    // interface for click listener
    private ViewHolderDette.ClickListener mclicklistener;
    public interface ClickListener{
        void  onItemclick(View view, int position);

    }

    public  void onClickListener(ViewHolderDette.ClickListener clickListener){
        mclicklistener=clickListener;
    }
}
