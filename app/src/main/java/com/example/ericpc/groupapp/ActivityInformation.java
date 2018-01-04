package com.example.ericpc.groupapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityInformation extends AppCompatActivity {
 private Bundle activityType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Bundle extras = getIntent().getExtras();
        if (extras != null){

            activityType = extras.getBundle("msgActivity");
            ActivityFragment fragment = new ActivityFragment();
            fragment.setArguments(activityType);
            getFragmentManager().beginTransaction()
                    .add(R.id.activity_information, fragment).commit();
        }
    }


    public void deleteMessage(long id, int position){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("id", id);
        resultIntent.putExtra("position", position);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }


}