package com.example.ericpc.groupapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ThermostatDetails extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermostat_details);

        Intent intent = getIntent();

        String day = intent.getStringExtra("day");
        int hour = intent.getIntExtra("hour", 0);
        int minute = intent.getIntExtra("minute", 0);
        int temp = intent.getIntExtra("temp", 0);
        long ID = intent.getLongExtra("ID", 0);
        int position = intent.getIntExtra("position", 0);

        Bundle args = new Bundle();

        args.putString("day", day);
        args.putInt("hour", hour);
        args.putInt("minute", minute);
        args.putInt("temp", temp);
        args.putLong("ID", ID);
        args.putInt("position", position);

        ThermostatFragment fragment = new ThermostatFragment();
        fragment.setArguments(args);
        getFragmentManager().beginTransaction().add(R.id.frameLayout, fragment).commit();
    }

    public void deleteRule(long ID, int position){
        Intent intent = new Intent();
        intent.putExtra("ID", ID);
        intent.putExtra("position", position);
        setResult(11, intent);
        finish();
    }

    public void saveRule(String day, int hour, int minute, int temp, long ID, int position){
        Intent intent = new Intent();
        intent.putExtra("day", day);
        intent.putExtra("hour", hour);
        intent.putExtra("minute", minute);
        intent.putExtra("temp", temp);
        intent.putExtra("ID", ID);
        intent.putExtra("position", position);
        setResult(13, intent);
        finish();
    }

    public void saveNewRule(String day, int hour, int minute, int temp){
        Intent intent = new Intent();
        intent.putExtra("day", day);
        intent.putExtra("hour", hour);
        intent.putExtra("minute", minute);
        intent.putExtra("temp", temp);
        setResult(14, intent);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setResult(12);
        finish();
    }

}

