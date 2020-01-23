package com.example.breizhbudget;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ComptageAdapter extends RecyclerView.Adapter<ViewHolderComptage> {

    ComptageActivity listActivity;
    List<Participant> participant;

    public ComptageAdapter(ComptageActivity listActivity, List<Participant> participant) {
        this.listActivity = listActivity;
        this.participant = participant;
    }

    @NonNull
    @Override
    public ViewHolderComptage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_comptage, parent , false);
        ViewHolderComptage viewHolder = new ViewHolderComptage(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderComptage holder, int position) {

        holder.mName.setText(participant.get(position).getName());
        holder.mComptage.setText(String.valueOf(participant.get(position).getMontant()));
        holder.mName.setTextColor(Color.parseColor("#000000"));
        holder.mComptage.setTextColor(Color.parseColor("#f40b0b"));

    }

    @Override
    public int getItemCount() {
        return participant.size();
    }
}
