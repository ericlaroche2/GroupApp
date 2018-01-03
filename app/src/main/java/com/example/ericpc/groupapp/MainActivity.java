package com.example.ericpc.groupapp;

import android.content.Intent;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private View dashboardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        dashboardLayout = findViewById(R.id.mainActivity_content);

        final FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.info);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(dashboardLayout, "Created By Alexis Small,  Elie Massaad, Eric Laroche, " +
                        "and Alex Mastropietro", Snackbar.LENGTH_LONG)
                        .show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m );

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_one:

                Intent intent = new Intent(this, NutritionTracker.class);
                startActivity(intent);

                return true;

            case R.id.action_two:

                return true;
            case R.id.action_three:

                return true;

            case R.id.action_four:
                Log.i("Activity Selected:", "Automobile Tracker");
                Intent autoActivity = new Intent(this, AutoMain.class);
                startActivity(autoActivity);

                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
