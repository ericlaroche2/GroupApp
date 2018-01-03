package com.example.ericpc.groupapp;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

public class AutoStatistics extends Activity {

    private View layout;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_layout);

        AutoDatabaseHelper dbHelper = new AutoDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        TextView avgLiters = findViewById(R.id.avgLiters);
        avgLiters.setText(String.valueOf(dbHelper.getAverageLiters(db) + "L"));

        TextView avgPrice = findViewById(R.id.avgPrice);
        avgPrice.setText(String.valueOf(dbHelper.getAveragePrice(db) + " $/L"));

    }

    public void perform_action(View v){

        title = findViewById(R.id.metadataTitle);

        layout = findViewById(R.id.statsLayout);
        Snackbar.make(layout, "Monthly Averages are based on the last 30 days", Snackbar.LENGTH_LONG).show();

        }
    }
