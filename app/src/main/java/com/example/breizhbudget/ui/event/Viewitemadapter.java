package com.example.breizhbudget.ui.event;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.R;

import java.util.List;

public class Viewitemadapter extends RecyclerView.Adapter<ViewHolderParticipant> {



    ViewEvent listActivity;
    List<Participant> participant;

    public Viewitemadapter(ViewEvent listActivity, List<Participant> participant) {
        this.listActivity = listActivity;
        this.participant = participant;
    }

    @NonNull
    @Override
    public ViewHolderParticipant onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_event, parent , false);
        ViewHolderParticipant viewHolder = new ViewHolderParticipant(itemView);
        viewHolder.onClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemclick(View view, int position) {


            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderParticipant holder, int position) {

        holder.mName.setText(participant.get(position).getName());
        holder.mMontant.setText(String.valueOf(participant.get(position).getMontant()));
        holder.mName.setTextColor(Color.parseColor("#000000"));
        holder.mMontant.setTextColor(Color.parseColor("#f40b0b"));

    }

    @Override
    public int getItemCount() {
        return participant.size();
    }
}
