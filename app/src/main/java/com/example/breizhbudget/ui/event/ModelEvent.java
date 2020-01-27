package com.example.breizhbudget.ui.event;

import java.util.ArrayList;

public class ModelEvent {


    String id,title;
    ArrayList<Participant> participants;

    public ModelEvent() {
    }

    public ModelEvent(String id){
        this.id = id;
    }

    public ModelEvent(String id, String title, ArrayList<Participant> participants) {
        this.id = id;
        this.title = title;
        this.participants = participants;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }
}
