package com.example.ericpc.groupapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private boolean frameLayoutExists;
    private final int DELETE_REQUEST = 10;
    private int columnIndexActivity;
    private int  columnIndexMinutues;
    private int  columnIndexComments;
    private int columnIndexDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        list = findViewById(R.id.listView);
       OpenDatabase open = new OpenDatabase();
       open.execute();

        frameLayoutExists = findViewById(R.id.message_detail_container) != null;


list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String activityType = c.getString(c.getColumnIndex(ActivityDatabaseHelper.ACTIVITY_TYPE));
                double minutes = c.getDouble(c.getColumnIndex(ActivityDatabaseHelper.MINUTES));
                String comments = c.getString(c.getColumnIndex(ActivityDatabaseHelper.COMMENTS));
                String date = c.getString(c.getColumnIndex(ActivityDatabaseHelper.DATE));
                Bundle bundle = new Bundle();
                bundle.putLong("id", id);

                bundle.putString("activity", activityType);
                bundle.putDouble("minutes", minutes);
                bundle.putString("comments", comments);
                bundle.putString("date", date);

                bundle.putInt("position", position);


                if (!frameLayoutExists){
                    Intent intent = new Intent(ActivityView.this, ActivityInformation.class);
                    intent.putExtra("msgActivity", bundle);
                    startActivityForResult(intent, DELETE_REQUEST);
                }
                else {
                    ActivityFragment fragment = new  ActivityFragment();
                   fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.message_detail_container, fragment).commit();
                }

            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == DELETE_REQUEST){
            if (resultCode == Activity.RESULT_OK){

                Bundle extras = data.getExtras();
                long id = extras.getLong("id");
                int position = extras.getInt("position");
                deleteMessage(id, position);
            }
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        startActivity(new Intent(this, ActivityView.class));
    }

    protected void deleteMessage(long id, int position){
       activityTypeArray.remove(position);
       minutesArray.remove(position);
       commentsArray.remove(position);
       dateArray.remove(position);

        db.delete(ActivityDatabaseHelper.TABLE_NAME, ActivityDatabaseHelper.KEY_ID + "=" + id, null);
        activityAdapter.notifyDataSetChanged();
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

        @Override
        public long getItemId(int position){
            if (c == null)
                throw new NullPointerException(" cursor is null");

            // refresh the cursors
            c = db.rawQuery("SELECT * FROM " + ActivityDatabaseHelper.TABLE_NAME, null);
            c.moveToPosition(position);
            return c.getLong(c.getColumnIndex(ActivityDatabaseHelper.KEY_ID));
        }
    }



    public void onDestroy() {
        super.onDestroy();
        db.close();

    }


    private class OpenDatabase extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {
            dbHelper = new ActivityDatabaseHelper(getApplicationContext());
            db = dbHelper.getWritableDatabase();
            // db.delete(ActivityDatabaseHelper.TABLE_NAME, null,null);
            c = db.rawQuery("SELECT * FROM " + ActivityDatabaseHelper.TABLE_NAME, null);
            columnIndexActivity = c.getColumnIndex(ActivityDatabaseHelper.ACTIVITY_TYPE);
             columnIndexMinutues = c.getColumnIndex(ActivityDatabaseHelper.MINUTES);
            columnIndexComments = c.getColumnIndex(ActivityDatabaseHelper.COMMENTS);
            columnIndexDate = c.getColumnIndex(ActivityDatabaseHelper.DATE);


            return null;
        }



        @Override
        protected void onPostExecute(String result) {
            activityAdapter = new ActivityAdapter(getApplicationContext());
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

}}