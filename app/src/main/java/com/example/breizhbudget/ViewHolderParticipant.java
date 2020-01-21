package com.example.breizhbudget;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewHolderParticipant extends RecyclerView.ViewHolder {

    TextView mName,mMontant;
    List<Participant> mparticipantList;
    View mView;

    public ViewHolderParticipant(@NonNull View itemView) {
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

        mName= itemView.findViewById(R.id.textViewName);
        mMontant = itemView.findViewById(R.id.textViewMonatnt);


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