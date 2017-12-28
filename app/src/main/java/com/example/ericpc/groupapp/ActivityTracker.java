package com.example.ericpc.groupapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.KeyEventCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ActivityTracker extends Activity {
    private  String activityType;
    private EditText enterMinutes;
    private EditText enterComment;
    private ActivityDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor c;
    private  ArrayList<String> activityTypeArray = new ArrayList<String>();
    private ArrayList<Double> minutesArray = new ArrayList<Double>();
    private ArrayList<String> commentsArray = new ArrayList<String>();
    private  ArrayList<String> dateArray = new ArrayList<String>();
   // when the activity is filed out, enter to push data into the database
    private Button enter;
    private ContentValues contentValue;
    //this will be set from the enterMinutes editText to add into the database
    private double numberOfMinutes;

    protected static int ARRAYS_INDEX_COUNTER = 0;

    private Button seeActivities;
    private Boolean aRadioButtonIsChecked;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        enterMinutes =  findViewById(R.id.enterMinutes);
        enterComment = findViewById(R.id.enterComment);
        enter = findViewById(R.id.enter);

        dbHelper = new ActivityDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();


        c = db.rawQuery("SELECT * FROM " + ActivityDatabaseHelper.TABLE_NAME , null);
        int columnIndexActivity = c.getColumnIndex(ActivityDatabaseHelper.ACTIVITY_TYPE);
        int columnIndexMinutues = c.getColumnIndex(ActivityDatabaseHelper.MINUTES);
        int columnIndexComments = c.getColumnIndex(ActivityDatabaseHelper.COMMENTS);
        int columnIndexDate= c.getColumnIndex(ActivityDatabaseHelper.DATE);


        while(c.moveToNext() ) {
            String activity_type= c.getString(columnIndexActivity);
            double minutes = c.getDouble(columnIndexMinutues);
            String comment = c.getString(columnIndexComments );
            String date = c.getString(columnIndexDate);

             activityTypeArray.add(activity_type);
             minutesArray.add(minutes);
             commentsArray.add(comment);
             dateArray.add(date);

            Log.i("ActivityTracker ", "SQL MESSAGE: " + activity_type + " " + minutes + " " + comment + " " + date);
        }

// checks if user set a number
        enterMinutes.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String text = enterMinutes.getText().toString();
                    if(text.equals("") || text.equals("")){
                        enterMinutes.setText("");

                    }else {

                        try {
                            numberOfMinutes = Double.parseDouble(text);
                            Log.i("ActivityTracker", numberOfMinutes + " is a number");
                        } catch (NumberFormatException e) {
                            Log.i("ActivityTracker", text + " is not a number");
                            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTracker.this);
                            builder.setTitle("Alert");
                            builder.setMessage("Please Enter a Number Only!");
                            builder.setCancelable(true);
                            builder.setNeutralButton("OK!",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            enterMinutes.setText("");
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert = builder.create();
                            alert.show();

                        }
                    }
                }

            }
        });


        enter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                contentValue = new ContentValues();

                activityTypeArray.add(activityType);
                contentValue.put(ActivityDatabaseHelper.ACTIVITY_TYPE, activityType);




                minutesArray.add(numberOfMinutes);
                contentValue.put(ActivityDatabaseHelper.MINUTES, numberOfMinutes);


                commentsArray.add(enterComment.getText().toString());
                contentValue.put(ActivityDatabaseHelper.COMMENTS, enterComment.getText().toString());


                DateFormat df = new SimpleDateFormat("dd MM yyyy, HH:mm");
                String date = df.format(Calendar.getInstance().getTime());


                dateArray.add(date);
                contentValue.put(ActivityDatabaseHelper.DATE, date);
                db.insert(ActivityDatabaseHelper.TABLE_NAME, "Null Replacement Value", contentValue);


                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(ActivityTracker.this , "Successfully saved activity information.", duration);
                toast.show();

               // ARRAYS_INDEX_COUNTER++;
               // Log.i("ActivityTracker ", "SQL MESSAGE: " + activityTypeArray.get(ARRAYS_INDEX_COUNTER) + " " + minutesArray.get(ARRAYS_INDEX_COUNTER) + " " + commentsArray.get(ARRAYS_INDEX_COUNTER) + " " + dateArray.get(ARRAYS_INDEX_COUNTER));





            }
        });

        seeActivities = findViewById(R.id.seeActivities);

        seeActivities.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityTracker.this, ActivityView.class);
                startActivity(intent);




            }
        });



    }







    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();


        RadioButton setButton;

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioRunning:
                if (checked){
                    Log.i(" ActivityTracker", "Clicked on Running");
                  setButton = findViewById(R.id.radioRunning);
                   setActivityType((String) setButton.getText());
                    aRadioButtonIsChecked= true;

                }
                    break;
            case R.id.radioWalking:
                if (checked){
                    Log.i(" ActivityTracker", "Clicked on Walking");
                    setButton = findViewById(R.id.radioWalking);
                    setActivityType((String) setButton.getText());
                    aRadioButtonIsChecked = true;

                }
                    break;
            case R.id.radioBiking:
                if (checked){
                    Log.i(" ActivityTracker", "Clicked on Biking");
                    setButton = findViewById(R.id.radioBiking);
                    setActivityType((String) setButton.getText());
                    aRadioButtonIsChecked = true;

                }
                    break;
            case R.id.radioSwimming:
                if (checked){
                    Log.i(" ActivityTracker", "Clicked on Swimming");
                    setButton =  findViewById(R.id.radioSwimming);
                    setActivityType((String) setButton.getText());
                    aRadioButtonIsChecked = true;

                }
                    break;
            case R.id.radioSkating:
                if (checked){
                    Log.i(" ActivityTracker", "Clicked on Skating");
                    setButton =  findViewById(R.id.radioSkating);
                    setActivityType((String) setButton.getText());
                    aRadioButtonIsChecked = true;

                }
                    break;
        }
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }


    public void onDestroy(){
        super.onDestroy();
        db.close();

    }




}
