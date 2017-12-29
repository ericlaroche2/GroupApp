package com.example.ericpc.groupapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Progress extends AppCompatActivity {
    private ActivityDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor c;
    private ArrayList<String> activityTypeArray = new ArrayList<String>();
    private ArrayList<Double> minutesArray = new ArrayList<Double>();
    private ArrayList<String> commentsArray = new ArrayList<String>();
    private ArrayList<String> dateArray = new ArrayList<String>();
    private EditText previousMonth;
    private EditText currentMont;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        dbHelper = new ActivityDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        // db.delete(ActivityDatabaseHelper.TABLE_NAME, null,null);
        c = db.rawQuery("SELECT * FROM " + ActivityDatabaseHelper.TABLE_NAME, null);
        int columnIndexActivity = c.getColumnIndex(ActivityDatabaseHelper.ACTIVITY_TYPE);
        int columnIndexMinutues = c.getColumnIndex(ActivityDatabaseHelper.MINUTES);
        int columnIndexComments = c.getColumnIndex(ActivityDatabaseHelper.COMMENTS);
        int columnIndexDate = c.getColumnIndex(ActivityDatabaseHelper.DATE);


        while (c.moveToNext()) {
            String activity_type = c.getString(columnIndexActivity);
            double minutes = c.getDouble(columnIndexMinutues);
            String comment = c.getString(columnIndexComments);
            String date = c.getString(columnIndexDate);

            activityTypeArray.add(activity_type);
            minutesArray.add(minutes);
            commentsArray.add(comment);
            dateArray.add(date);

            Log.i("ActivityTracker ", "SQL MESSAGE: " + activity_type + " " + minutes + " " + comment + " " + date);


        }

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

        String month = date.substring(3,5);

        int currentMonth = Integer.parseInt(month);
        int previousMonthnum = 0;

        if(currentMonth == 1){
            previousMonthnum = 12;

        }else{
            previousMonthnum = currentMonth - 1 ;
        }

        double  lastMonthMinutes = 0;
        double thisMonthMinutes = 0;

        for(int i=0; i < dateArray.size(); i++){
         String dateInArray =   dateArray.get(i);
          int  monthInArray = Integer.parseInt(dateInArray.substring(3,5));

            if(monthInArray == previousMonthnum){
                lastMonthMinutes += minutesArray.get(i);

            }else if(monthInArray == currentMonth ){

                thisMonthMinutes  += minutesArray.get(i);
            }

        }

       progressBar = findViewById(R.id.progressBar);
        previousMonth = findViewById(R.id.previous);
        currentMont = findViewById(R.id.current);

        previousMonth.setText(Double.toString(lastMonthMinutes));
        currentMont.setText(Double.toString(thisMonthMinutes));

      double howMuchFarher;

      if(thisMonthMinutes >= lastMonthMinutes){
          progressBar.setProgress(100);

      }else{
          double percent = (thisMonthMinutes / lastMonthMinutes)*100;
          progressBar.setProgress((int)percent);
        }


        }
    }

