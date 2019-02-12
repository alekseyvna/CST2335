package com.example.androidlabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyListAdapter extends BaseAdapter {

    ArrayList<Message> messages;
    private Context context;



    public MyListAdapter(ArrayList<Message> m, Context c) {
        this.messages = m;
        this.context = c;

    }

    @Override
    public int getCount(){
        return messages.size();
    }

    @Override
    public Message getItem(int position){

        return messages.get(position);
    }

    @Override
    public long getItemId(int position){

        return (long)position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View newView;
        TextView textview;
        textview = null;

        if( messages.get(position).getType() == 'r') {
            newView = inflater.inflate(R.layout.receive_layout, parent, false);
            textview = newView.findViewById(R.id.receivetext);
            textview.setText(getItem(position).getText());

        }



        else {
        newView = inflater.inflate(R.layout.send_layout, parent, false);
        textview = newView.findViewById(R.id.sendtext);
        textview.setText(getItem(position).getText());
    }

        return newView;
}

    }
