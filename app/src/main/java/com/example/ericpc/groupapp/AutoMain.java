package com.example.ericpc.groupapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by alexi on 2017-12-30.
 */

public class AutoMain extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_auto_buttons);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.autoTools);
        setSupportActionBar(myToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_automobile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AlertDialog.Builder builder = null;

        switch (item.getItemId()) {

            case R.id.help:
                Log.i("Help Selected:", "Automobile Tracker");

                LayoutInflater inflater = getLayoutInflater();
                LinearLayout rootView = (LinearLayout)inflater.inflate(R.layout.custom_alert_dialog, null);

                builder = new AlertDialog.Builder(AutoMain.this);
                builder.setView(rootView)

                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                break;

        }

        if (builder != null){
            AlertDialog alert = builder.create();
            builder.show();

        } return true;
    }



    public void startAdd(View view){

        startActivity(new Intent(this, AutoActivity.class));
    }

    public void startList(View view){

        startActivity(new Intent(this, DisplayList.class));
    }

    public void startMetadata (View view){

        startActivity(new Intent(this, AutoStatistics.class));
    }
}
