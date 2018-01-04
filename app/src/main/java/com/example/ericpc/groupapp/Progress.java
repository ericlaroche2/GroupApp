package com.example.ericpc.groupapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import  android.support.design.widget.Snackbar;
import android.widget.TextView;

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
    private TextView previousText;
    private TextView currentText;

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
        currentText = findViewById(R.id.currenText);
        previousText = findViewById(R.id.previousText);
        String month = date.substring(3, 5);

        int currentMonth = Integer.parseInt(month);
        int previousMonthnum = 0;

        if (currentMonth == 1) {
            previousMonthnum = 12;
            currentText.setText("January:");
            previousText.setText("December:");
        } else if(currentMonth == 2) {
            previousMonthnum = currentMonth - 1;
            currentText.setText("February:");
            previousText.setText("January:");
        }else if(currentMonth == 3) {
            previousMonthnum = currentMonth - 1;
            currentText.setText("March:");
            previousText.setText("February:");
        }else if(currentMonth == 4) {
            previousMonthnum = currentMonth - 1;
            currentText.setText("April:");
            previousText.setText("March:");
        }else if(currentMonth == 5) {
            previousMonthnum = currentMonth - 1;
            currentText.setText("May:");
            previousText.setText("April:");
        }else if(currentMonth == 6) {
            previousMonthnum = currentMonth - 1;
            currentText.setText("June:");
            previousText.setText("May:");
        }else if(currentMonth == 7) {
            previousMonthnum = currentMonth - 1;
            currentText.setText("July:");
            previousText.setText("June:");
        }else if(currentMonth == 8) {
            previousMonthnum = currentMonth - 1;
            currentText.setText("August:");
            previousText.setText("July:");
        }else if(currentMonth == 9) {
            previousMonthnum = currentMonth - 1;
            currentText.setText("September");
            previousText.setText("August");
        }else if(currentMonth == 10) {
            previousMonthnum = currentMonth - 1;
            currentText.setText("October:");
            previousText.setText("September:");
        }else if(currentMonth == 11) {
            previousMonthnum = currentMonth - 1;
            currentText.setText("November:");
            previousText.setText("October:");
        }else if(currentMonth == 12) {
            previousMonthnum = currentMonth - 1;
            currentText.setText("December:");
            previousText.setText("November:");
        }

        double lastMonthMinutes = 0;
        double thisMonthMinutes = 0;

        for (int i = 0; i < dateArray.size(); i++) {
            String dateInArray = dateArray.get(i);
            int monthInArray = Integer.parseInt(dateInArray.substring(3, 5));

            if (monthInArray == previousMonthnum) {
                lastMonthMinutes += minutesArray.get(i);

            } else if (monthInArray == currentMonth) {

                thisMonthMinutes += minutesArray.get(i);
            }

        }

        progressBar = findViewById(R.id.progressBar);
        previousMonth = findViewById(R.id.previous);
        currentMont = findViewById(R.id.current);

        previousMonth.setEnabled(false);
        currentMont.setEnabled(false);
        previousMonth.setText(Double.toString(lastMonthMinutes));
        currentMont.setText(Double.toString(thisMonthMinutes));

        double howMuchFarher;
        double percent = 0;
        View view = findViewById(R.id.toolbar);

        if (thisMonthMinutes >= lastMonthMinutes) {
            progressBar.setProgress(100);
            Snackbar.make(view, "Great Job! You've worked out more this month!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            percent = (thisMonthMinutes / lastMonthMinutes) * 100;
            progressBar.setProgress((int) percent);
        }


        if (percent > 75 && percent < 100) {
            Snackbar.make(view, "You are almost there!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else if (percent > 50 && percent < 75) {
            Snackbar.make(view, "Don;t stop now!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        } else if (percent == 50) {
            Snackbar.make(view, "You are half way there!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        } else if (percent < 50) {
            Snackbar.make(view, "You will get there!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }


    }


}