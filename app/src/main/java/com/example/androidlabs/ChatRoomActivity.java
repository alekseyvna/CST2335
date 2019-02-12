package com.example.androidlabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    Button send, receive;
    ListView msgs;
    EditText chat;
    ArrayList<Message> messages;
    MyListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        chat = findViewById(R.id.typehere);
        msgs = findViewById(R.id.list);
        messages = new ArrayList<>();
        adapter = new MyListAdapter(messages, ChatRoomActivity.this);


        receive = findViewById(R.id.receive);
        receive.setOnClickListener((View v) -> {
            addMessage('r', chat.getText().toString());
            chat.setText("");
            adapter.notifyDataSetChanged();
        });


        send = findViewById(R.id.sendbutton);
        send.setOnClickListener((View v) -> {
            addMessage('s', chat.getText().toString());
            chat.setText("");
            adapter.notifyDataSetChanged();
        });


        msgs.setAdapter(adapter);

    }


    public void addMessage(char type, String text){
      Message m = new Message(type, text);
        messages.add(m);

        }

    }
