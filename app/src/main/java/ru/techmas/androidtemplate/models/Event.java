package ru.techmas.androidtemplate.models;

public class Event {

    private int id;
    private Object data;
    private Object secondData;

    public Event(int type){
        this.id =type;
    }


    public Event(int type, Object data){
        this.id =type;
        this.data=data;
    }

    public Event(int type, Object data, Object secondData) {
        this.id = type;
        this.data = data;
        this.secondData = secondData;
    }

    public int getId() {
        return id;
    }

    public Object getData() {
        return data;
    }

    public Object getSecondData() {
        return secondData;
    }
}
