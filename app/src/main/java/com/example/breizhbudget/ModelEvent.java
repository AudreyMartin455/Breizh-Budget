package com.example.breizhbudget;

public class ModelEvent {


    String id,title;

    public ModelEvent(){}



    public  ModelEvent(String id, String title){

        this.id=id;
        this.title=title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
