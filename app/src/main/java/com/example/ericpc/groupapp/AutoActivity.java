package com.example.ericpc.groupapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Date;

public class AutoActivity extends AppCompatActivity {

    EditText enterKM, enterPrice, enterLiters;
    String kiloms, price, liters, date;
    ProgressBar prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        enterKM = (EditText) findViewById(R.id.enterKMET);
        enterPrice = (EditText) findViewById(R.id.enterPriceET);
        enterLiters = (EditText) findViewById(R.id.enterLitersET);

    }

    public void addData(View view){

        kiloms=enterKM.getText().toString();
        price=enterPrice.getText().toString();
        liters=enterLiters.getText().toString();
        Date todaydate = new Date();
        date = todaydate.toString();

        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("add_info", liters, price, kiloms, date);

        prog = (ProgressBar) findViewById(R.id.progressBar);

        enterKM.setText("");
        prog.setProgress(33);
        enterPrice.setText("");
        prog.setProgress(66);
        enterLiters.setText("");
        prog.setProgress(100);

        Toast.makeText(this,"Data has been successfully saved", Toast.LENGTH_LONG).show();

    }


}


