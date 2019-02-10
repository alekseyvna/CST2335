package com.example.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs;
    Button loginB;
    EditText emailEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);

        prefs = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        emailEdit = findViewById(R.id.emailaddress);
        String emails = prefs.getString("ReserveName", "");
        emailEdit.setText(emails);


        loginB = findViewById(R.id.login);
        loginB.setOnClickListener((View v) ->
                {
                    Intent intent = new Intent(this, ProfileActivity.class);
                    intent.putExtra("ReserveName", emailEdit.getText().toString());
                    startActivity(intent);
                }
        );
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = prefs.edit();

        //save what was typed under the name "ReserveName"
        String userIn = emailEdit.getText().toString();
        editor.putString("ReserveName", userIn);

        //write it to disk:
        editor.commit();

    }
}
