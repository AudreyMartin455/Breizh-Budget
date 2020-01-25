package com.example.breizhbudget.ui.event;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.R;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<ViewHolder> {


    Context c;
    EventActivity listActivity;
    List<ModelEvent> modelEventList;
    ArrayList<Participant> participantList;


    public EventAdapter(EventActivity listActivity, List<ModelEvent> modelEventList) {
        this.listActivity = listActivity;
        this.modelEventList = modelEventList;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent , false);
        //handle item clicks here

        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.onClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemclick(View view, int position) {
                String title = modelEventList.get(position).getTitle();
                String id = modelEventList.get(position).getId();
                Intent intent = new Intent(listActivity,ViewEvent.class);
                intent.putExtra("title", title);
                listActivity.startActivity(intent);

            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //bind view setdata
        holder.mTitleEvent.setText(modelEventList.get(position).getTitle());


    }

    @Override
    public int getItemCount() {
        return modelEventList.size();
    }
}
