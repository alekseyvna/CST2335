package com.example.androidlabs;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        chat = findViewById(R.id.typehere);
        msgs = findViewById(R.id.list);
        messages = new ArrayList<>();
        adapter = new MyListAdapter(messages, ChatRoomActivity.this);


        receive = findViewById(R.id.receive);
        receive.setOnClickListener((View v) -> {
            addMessage("r", chat.getText().toString());
            chat.setText("");
            adapter.notifyDataSetChanged();
        });


        send = findViewById(R.id.sendbutton);
        send.setOnClickListener((View v) -> {
            addMessage("s", chat.getText().toString());
            chat.setText("");
            adapter.notifyDataSetChanged();
        });


        msgs.setAdapter(adapter);




    }


    public void addMessage(String type, String text) {

        ContentValues cv = new ContentValues();
        cv.put(MyOpener.COL_TYPE, type);
        cv.put(MyOpener.COL_TEXT, text);

        long newId = db.insert(MyOpener.TABLE_NAME, null, cv);

        if (type.equals("s")) {
            messages.add(new Message(text, newId, true, false));
        } else {
            messages.add(new Message(text, newId, false, true));


        }
        String[] columns = {MyOpener.COL_ID, MyOpener.COL_TEXT};
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);
        printCursor(results);
        results.moveToFirst();

        while (!results.isAfterLast()) {
            String t = results.getString(results.getColumnIndex("TEXT"));
            long i = results.getLong(results.getColumnIndex("_id"));
            int iS = results.getInt(results.getColumnIndex("_id"));

            if (iS == 1) {
                messages.add(new Message(t, i, true, false));

            } else {
                messages.add(new Message(t, i, false, true));
            }

            results.moveToNext();
        }
    }


    public void printCursor(Cursor c){

        c.moveToFirst();
        int dbVer = db.getVersion();
        int colCount = c.getColumnCount();
        String colNames = "";
        for(int i = 0; i <colCount; i++){
            colNames += c.getColumnName(i);

        }

        int messageIndex = c.getColumnIndex(MyOpener.COL_TEXT);
        int sentIndex = c.getColumnIndex(MyOpener.COL_TYPE);
        int idIndex = c.getColumnIndex(MyOpener.COL_ID);


        String text = dbVer + ", " + colCount + ", " + colNames +  c.getCount() + ", ";
        c.moveToFirst();
        while(!(c.isAfterLast())) {
            text += "" + c.getInt(idIndex)
                    + ", " + c.getString(messageIndex)
            + ", " + c.getString(sentIndex);
            c.moveToNext();
        }

        Log.d("ChatRoomActivity", text);
    }
 }





