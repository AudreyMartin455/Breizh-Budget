package com.example.breizhbudget;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<ViewHolder> {



    EventActivity listActivity;
    List<ModelEvent> modelEventList;


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
                //this will be called when user click item
                String title = modelEventList.get(position).getTitle();
                Toast.makeText(listActivity,title+"\n",Toast.LENGTH_SHORT).show();
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
