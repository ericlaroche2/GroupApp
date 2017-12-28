package com.example.ericpc.groupapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityView extends Activity {
    private ActivityDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor c;
    private ArrayList<String> activityTypeArray = new ArrayList<String>();
    private ArrayList<Double> minutesArray = new ArrayList<Double>();
    private ArrayList<String> commentsArray = new ArrayList<String>();
    private ArrayList<String> dateArray = new ArrayList<String>();
    private  ActivityAdapter activityAdapter;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        list = findViewById(R.id.listView);


        dbHelper = new ActivityDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
       // db.delete(ActivityDatabaseHelper.TABLE_NAME, null,null);
        c = db.rawQuery("SELECT * FROM " + ActivityDatabaseHelper.TABLE_NAME, null);
        int columnIndexActivity = c.getColumnIndex(ActivityDatabaseHelper.ACTIVITY_TYPE);
        int columnIndexMinutues = c.getColumnIndex(ActivityDatabaseHelper.MINUTES);
        int columnIndexComments = c.getColumnIndex(ActivityDatabaseHelper.COMMENTS);
        int columnIndexDate = c.getColumnIndex(ActivityDatabaseHelper.DATE);


        //Set Listview adapter
        activityAdapter = new ActivityAdapter(this);
        list.setAdapter (activityAdapter);

        while (c.moveToNext()) {
            String activity_type = c.getString(columnIndexActivity);
            double minutes = c.getDouble(columnIndexMinutues);
            String comment = c.getString(columnIndexComments);
            String date = c.getString(columnIndexDate);

            activityTypeArray.add(activity_type);
            minutesArray.add(minutes);
            commentsArray.add(comment);
            dateArray.add(date);

           activityAdapter.notifyDataSetChanged();

            Log.i("ActivityTracker ", "SQL MESSAGE: " + activity_type + " " + minutes + " " + comment + " " + date);


        }


    }


    private class ActivityAdapter extends ArrayAdapter<String> {

        ActivityAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return activityTypeArray.size();
        }

        public String getItem(int position) {
            return activityTypeArray.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ActivityView.this.getLayoutInflater();
            View result = null;
            if (activityTypeArray.get(position).equals("Running"))
                result = inflater.inflate(R.layout.running_activity, null);
            else if (activityTypeArray.get(position).equals("Walking"))
                result = inflater.inflate(R.layout.walking_activity, null);
            else if (activityTypeArray.get(position).equals("Biking"))
                result = inflater.inflate(R.layout.biking_activity, null);
            else if (activityTypeArray.get(position).equals("Swimming"))
                result = inflater.inflate(R.layout.swimming_activity, null);
            else if (activityTypeArray.get(position).equals("Skating"))
                result = inflater.inflate(R.layout.skating_activity, null);


            TextView message = result.findViewById(R.id.textView2);
            message.setText("Time: " + minutesArray.get(position) + ", Comment: " + commentsArray.get(position) + ", Date: " + dateArray.get(position)); // get the string at position
            return result;


        }
    }

    public void onDestroy() {
        super.onDestroy();
        db.close();

    }

}