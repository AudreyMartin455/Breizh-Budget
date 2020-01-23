package com.example.breizhbudget;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewHolderComptage extends RecyclerView.ViewHolder  {

    TextView mName,mComptage;
    List<Participant> mparticipantList;
    View mView;

    public ViewHolderComptage(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        // initialize views with Eventmodel layout

        mName = itemView.findViewById(R.id.textViewNameComptage);
        mComptage = itemView.findViewById(R.id.textViewComptage);
    }
}
