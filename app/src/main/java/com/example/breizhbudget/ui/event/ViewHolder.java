package com.example.breizhbudget.ui.event;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.R;

import java.util.List;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView mTitleEvent;
    List<Participant> mparticipantList;
    ImageButton deleteButton;
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

        mTitleEvent= itemView.findViewById(R.id.textView);
        deleteButton = itemView.findViewById(R.id.layout_list_delete);

        deleteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                EventActivity ba = (EventActivity) v.getContext();
                ba.deleteEvent(getAdapterPosition());

            }
        });

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
