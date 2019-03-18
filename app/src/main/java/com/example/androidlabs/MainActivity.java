package com.example.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        loginB.setOnClickListener(clicked ->
                {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra("ReserveName", emailEdit.getText().toString());
                    startActivity(intent);
                }
        );
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = prefs.edit();


        String userIn = emailEdit.getText().toString();
        editor.putString("ReserveName", userIn);


        editor.commit();

    }
}
