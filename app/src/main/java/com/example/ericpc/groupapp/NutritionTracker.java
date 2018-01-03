package com.example.ericpc.groupapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import static android.content.ContentValues.TAG;
import static com.example.ericpc.groupapp.NutritionDatabaseHelper.DATABASE_NAME;
import static com.example.ericpc.groupapp.NutritionDatabaseHelper.KEY_Calorie;
import static com.example.ericpc.groupapp.NutritionDatabaseHelper.KEY_Carb;
import static com.example.ericpc.groupapp.NutritionDatabaseHelper.KEY_Date;
import static com.example.ericpc.groupapp.NutritionDatabaseHelper.KEY_Fat;
import static com.example.ericpc.groupapp.NutritionDatabaseHelper.KEY_Name;
import static com.example.ericpc.groupapp.NutritionDatabaseHelper.KEY_Protein;
import static com.example.ericpc.groupapp.NutritionDatabaseHelper.KEY_Time;
import static com.example.ericpc.groupapp.NutritionDatabaseHelper.Key_ID;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

/**
 * Created by EricPc on 2017-12-23.
 */

public class NutritionTracker extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;
    NutritionFragment fragment;
    ArrayList<FoodObject> foodArrayList = new ArrayList<>();
    ListView itemsListView;
    NutritionDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    String[] projection = {Key_ID, KEY_Name, KEY_Calorie, KEY_Fat, KEY_Carb, KEY_Protein, KEY_Date, KEY_Time};
    Button addButton;
    FoodListAdapter listAdapter;
    ProgressBar progressBar;
    TextView totalFat;
    TextView totalProtein;
    TextView totalCarbs;
    TextView yesterdayAvgTxt ;
    TextView textViewCalAvg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrition_tracker_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new NutritionDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        //db.execSQL("delete from "+ DATABASE_NAME);
         totalFat=(TextView)   findViewById(R.id.textViewTotalFat);
         totalProtein=(TextView)   findViewById(R.id.textViewTotalProtein);
         totalCarbs=(TextView)   findViewById(R.id.textViewTotalCarbs);
         yesterdayAvgTxt = (TextView) findViewById(R.id.textViewYesterdayCal);
         textViewCalAvg = (TextView) findViewById(R.id.textViewCalAvg);
        cursor = db.query(DATABASE_NAME, projection, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int rowId = cursor.getInt(cursor.getColumnIndexOrThrow(Key_ID));
            String itemName = cursor.getString(cursor.getColumnIndexOrThrow(KEY_Name));
            int cal = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_Calorie));
            int fat = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_Fat));
            int carb = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_Carb));
            int protein = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_Protein));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(KEY_Date));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(KEY_Time));
            FoodObject food = new FoodObject();
            food.setKey_ID(rowId);
            food.setKEY_Name(itemName);
            food.setKEY_Calorie(String.valueOf(cal));
            food.setKEY_Carb(String.valueOf(carb));
            food.setKEY_Fat(String.valueOf(fat));
            food.setKEY_Protein(String.valueOf(protein));
            food.setKEY_Date(date);
            food.setKEY_Time(time);
            foodArrayList.add(food);
        }
        cursor.close();
        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        new StatisticsQuery().execute();
        itemsListView = (ListView) findViewById(R.id.nutriontionListView);
        listAdapter = new FoodListAdapter(this);
        itemsListView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        Log.d("output", getTableAsString(db, DATABASE_NAME) + "\n array size: " + foodArrayList.size());
        addButtonListeners();
    }

    private class FoodListAdapter extends ArrayAdapter<FoodObject> {

        public FoodListAdapter(Context context) {
            super(context, 0);
        }

        public int getCount() {
            return foodArrayList.size();
        }

        public FoodObject getItem(int position) {
            return foodArrayList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {


            FoodObject food = getItem(position);
            LayoutInflater inflater = NutritionTracker.this.getLayoutInflater();
            View result = null;
            result = inflater.inflate(R.layout.food_row, null);

            //set text at row
            TextView textname = (TextView) result.findViewById(R.id.textViewFoodName);
            TextView textcalories = (TextView) result.findViewById(R.id.textViewCalories);
            TextView textfat = (TextView) result.findViewById(R.id.textViewfat);
            TextView textcarb = (TextView) result.findViewById(R.id.textViewcarb);
            TextView textprotein = (TextView) result.findViewById(R.id.textViewprotein);
            TextView textdate = (TextView) result.findViewById(R.id.textViewdate);
            TextView texttime = (TextView) result.findViewById(R.id.textviewFoodTime);

            textname.setText(food.getKEY_Name()); // get the string at position
            textcalories.setText(getResources().getString(R.string.calories) + food.getKEY_Calorie());
            textfat.setText(getResources().getString(R.string.fat) + food.getKEY_Fat() + "g");
            textcarb.setText(getResources().getString(R.string.carbohydrates )+ food.getKEY_Carb() + "g");
            textprotein.setText(getResources().getString(R.string.protein) + food.getKEY_Protein() + "g");
            textdate.setText(food.getKEY_Date());
            texttime.setText(food.getKEY_Time());

            return result;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (resultCode == 888) {
            // foodArrayList.add("testing testing");
            listAdapter.notifyDataSetChanged();
        }
    }


    public void updateArray() {
        listAdapter.notifyDataSetChanged();
        Log.d("output", getTableAsString(db, DATABASE_NAME) + "\n array size: " + foodArrayList.size());
    }


    public Cursor getDbCursor() {
        return cursor;
    }

/*
 *  Debugger helper that displays database into a string
* Source:https://stackoverflow.com/questions/27003486/printing-all-rows-of-a-sqlite-database-in-android
* By: A.Wan
 */
    public String getTableAsString(SQLiteDatabase db, String tableName) {
        Log.d(TAG, "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst()) {
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name : columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }

        return tableString;
    }

    public void removeFragment() {
        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment).commit();
        Toast toast = Toast.makeText(NutritionTracker.this, R.string.changesSaved, Toast.LENGTH_LONG);
        toast.show();
        new StatisticsQuery().execute();


    }



    private void addButtonListeners(){
        addButton = (Button) findViewById(R.id.addFood);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FrameLayout frameLayout = (FrameLayout) findViewById(R.id.nutritionFrame);
                frameLayout.setVisibility(View.VISIBLE);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // You can then add a fragment using the add() method, specifying the fragment to add and the view in which to insert it. For example:
                fragment = new NutritionFragment();
                fragmentTransaction.add(R.id.nutritionFrame, fragment);
                fragmentTransaction.commit();

            }
        });
        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                db = dbHelper.getWritableDatabase();//to allow delete
                FrameLayout frameLayout = (FrameLayout) findViewById(R.id.nutritionFrame);
                frameLayout.setVisibility(View.VISIBLE);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // You can then add a fragment using the add() method, specifying the fragment to add and the view in which to insert it. For example:
                fragment = new NutritionFragment();
                Bundle args = new Bundle();
                //  args.putString("message", messageAdapter.getItem(i));
                args.putSerializable("food", listAdapter.getItem(i));
                args.putSerializable("id", listAdapter.getItemId(i));

                fragment.setArguments(args);
                fragmentTransaction.add(R.id.nutritionFrame, fragment);
                fragmentTransaction.commit();

            }

        });

    }
    public SQLiteDatabase getDb() {
        return db;
    }

    public class StatisticsQuery extends AsyncTask<String, Integer, String> {
        int fatTotal=0;
        int carbTotal=0;
        int proteinTotal=0;
        double totalCals = 0;
        double globalCals = 0;


        @Override
        protected String doInBackground(String... strings) {
            try {
                return updateStatistics();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);        }

        protected void onPostExecute(String result) {
           // showDialog("Downloaded " + result + " bytes");



            onProgressUpdate(100);
            progressBar.setVisibility(View.INVISIBLE);
            Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), R.string.statsUpdated, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null);
            snackbar.show();
            totalFat.setText(String.valueOf(fatTotal));
            totalProtein.setText(String.valueOf(proteinTotal));
            totalCarbs.setText(String.valueOf(carbTotal));
            yesterdayAvgTxt.setText(getString(R.string.recentday) + String.valueOf(totalCals)+ "cal");
            textViewCalAvg.setText(getString(R.string.avg) + (int)globalCals+ getString(R.string.calperday));


        }
        private String updateStatistics() throws ParseException {
            if (foodArrayList.size()>0) {


                Calendar myCalendar = Calendar.getInstance();
                // Date currentTime = Calendar.getInstance().getTime();


                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                String recentDay ="";
                String today=(sdf.format(myCalendar.getTime()));
                Date date1 = new Date();
                Date date2 = new Date();
                onProgressUpdate(25);


                    date1 = new SimpleDateFormat("MM/dd/yy").parse(foodArrayList.get(0).getKEY_Date());

                for (int i = 0; i < foodArrayList.size(); i++) {
                        date2 = new SimpleDateFormat("MM/dd/yy").parse(foodArrayList.get(i).getKEY_Date());


                    if (date2.after(date1)) {
                        recentDay = sdf.format(date2);
                    } else recentDay = sdf.format(date1);
                    //Get daily macro counts
                    if (today.equals(foodArrayList.get(i).getKEY_Date())){
                        fatTotal+=Integer.parseInt(foodArrayList.get(i).getKEY_Fat());
                        carbTotal+=Integer.parseInt(foodArrayList.get(i).getKEY_Carb());
                        proteinTotal+=Integer.parseInt(foodArrayList.get(i).getKEY_Protein());

                    }
                }

                onProgressUpdate(50);
                //Add up cals for most recent date
                for (int i = 0; i < foodArrayList.size(); i++) {
                    String what = foodArrayList.get(i).getKEY_Date();
                    if (recentDay.equals(foodArrayList.get(i).getKEY_Date())) {
                        totalCals += Integer.parseInt(foodArrayList.get(i).getKEY_Calorie());
                    }
                }

                onProgressUpdate(75);
                //get unique dates to calculate daily calorie average
                HashSet<String> uniqueDates = new HashSet<>();
                for (int i = 0; i < foodArrayList.size(); i++) {
                    uniqueDates.add(foodArrayList.get(i).getKEY_Date());
                    globalCals += Integer.parseInt(foodArrayList.get(i).getKEY_Calorie());
                }
                globalCals = globalCals / foodArrayList.size();


            }



            return "Updated";

        }
    }
    @Override
    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.toolbar_nutrition, m );

        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem mi){
        switch (mi.getItemId()) {

            case R.id.action_about_nutrition_tracker:
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(this);
                AlertDialog dialog;
                LayoutInflater inflater = this.getLayoutInflater();

                builder.setView(inflater.inflate(R.layout.dialog_about_nutrition_tracker, null))
                        // Add action buttons

                        .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                dialog = builder.create();
                dialog.show();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(mi);
        }
    }

     private void updateUI(){

     }
}
