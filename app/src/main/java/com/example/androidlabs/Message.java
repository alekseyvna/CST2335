package com.example.androidlabs;

public class Message {
    char type;
    String text;
    boolean isSent;
    boolean isReceived;
    long id;


    public Message(String te, long id, boolean iS, boolean iR) {

        this.text = te;
        this.isSent = iS;
        this.isReceived = iR;
        this.id = id;
    }

    public String getText() {

        return text;
    }

    public char getType() {

        return type;
    }


    public long getId() {
        return id;
    }

    public boolean getIsSent(){
        return isSent;
    }

    public boolean getIsReceived(){
        return isReceived;
    }


}
