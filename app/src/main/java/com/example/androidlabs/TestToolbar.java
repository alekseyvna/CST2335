package com.example.androidlabs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class TestToolbar extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar tBar = findViewById(R.id.toolbarbutton);
        setSupportActionBar(tBar);

    }


    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;
    }
    String s = "You clicked on the overflow menu";
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {

            case R.id.choice1:
                Toast.makeText(this, "This is the initial message", Toast.LENGTH_LONG).show();
                return true;
            case R.id.choice2:
                alertExample();
                return true;
            case R.id.choice3:
                Toolbar tBar = (Toolbar)findViewById(R.id.toolbarbutton);
                setSupportActionBar(tBar);
                Snackbar sb = Snackbar.make(tBar, "Clicked item 3", Snackbar.LENGTH_LONG)
                        .setAction("Go Back?", e -> finish());
                sb.show();
                return true;

            case R.id.action_settings:
                Toast.makeText(this,s, Toast.LENGTH_LONG).show();
                return true;
        }
        return true;
    }

    public void alertExample()
    {
        View middle = getLayoutInflater().inflate(R.layout.pop_up, null);
        EditText et = (EditText)middle.findViewById(R.id.view_edit_text);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Change the overflow Toast ")
                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        s = et.getText().toString();
                    }
                })
                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).setView(middle);

        builder.create().show();
    }
}






