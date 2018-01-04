package com.example.ericpc.groupapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActivityTracker extends AppCompatActivity {
    private  String activityType;
    private EditText enterMinutes;
    private EditText enterComment;
    private ActivityDatabaseHelper dbHelper;
    private SQLiteDatabase db;

   // when the activity is filed out, enter to push data into the database
    private Button enter;
    private Button compare;
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
        Toolbar myToolbar = (Toolbar) findViewById(R.id.activity_tracker_toolbar);
        setSupportActionBar(myToolbar);




        enterMinutes =  findViewById(R.id.enterMinutes);
        enterComment = findViewById(R.id.enterComment);
        enter = findViewById(R.id.enter);

        dbHelper = new ActivityDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

      compare = findViewById(R.id.compare);


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


                contentValue.put(ActivityDatabaseHelper.ACTIVITY_TYPE, activityType);


                contentValue.put(ActivityDatabaseHelper.MINUTES, numberOfMinutes);



                contentValue.put(ActivityDatabaseHelper.COMMENTS, enterComment.getText().toString());


                DateFormat df = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
                String date = df.format(Calendar.getInstance().getTime());



                contentValue.put(ActivityDatabaseHelper.DATE, date);
                db.insert(ActivityDatabaseHelper.TABLE_NAME, "Null Replacement Value", contentValue);


                int duration = Toast.LENGTH_LONG;

                enterMinutes.setText("");
                enterComment.setText("");

                Toast toast = Toast.makeText(ActivityTracker.this , "Successfully saved activity information.", duration);
                toast.show();





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

        compare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityTracker.this, Progress.class);
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


    @Override
    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.activty_tracker_menu, m );

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                Log.d("Toolbar", "Option 3 selected");
                LayoutInflater inflater = getLayoutInflater();
                LinearLayout rootView = (LinearLayout)inflater.inflate(R.layout.activity_tracker_custom_alert, null);


                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTracker.this);
                // Inflate and set the layout for the dialog
                // pass null as the parent view because its going in the dialog layout
                builder.setView(rootView)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                             return;
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;

        }
      return true;
    }
}


