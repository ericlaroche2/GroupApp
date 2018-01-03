package com.example.ericpc.groupapp;

/**
 * Created by EricPc on 2017-12-23.
 */



import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.ericpc.groupapp.NutritionDatabaseHelper.DATABASE_NAME;
import static com.example.ericpc.groupapp.NutritionDatabaseHelper.KEY_Calorie;
import static com.example.ericpc.groupapp.NutritionDatabaseHelper.KEY_Carb;
import static com.example.ericpc.groupapp.NutritionDatabaseHelper.KEY_Date;
import static com.example.ericpc.groupapp.NutritionDatabaseHelper.KEY_Fat;
import static com.example.ericpc.groupapp.NutritionDatabaseHelper.KEY_Name;
import static com.example.ericpc.groupapp.NutritionDatabaseHelper.KEY_Protein;
import static com.example.ericpc.groupapp.NutritionDatabaseHelper.KEY_Time;
import static com.example.ericpc.groupapp.NutritionDatabaseHelper.Key_ID;


public class NutritionFragment extends Fragment {
    long id=0;
    int code=0;
    Calendar myCalendar;
    EditText dateText;
    //DatePickerDialog.OnDateSetListener datePicker;
    Button deleteButton;
    Button editButton;

    TextView name ;
    TextView calories ;
    TextView fat ;
    TextView carbs ;
    TextView protein ;
    TextView date ;
    String namerow="" ;
    int calrow=0 ;
    int fatrow=0  ;
    int carbrow=0 ;
    int proteinrow=0 ;
    String daterow="";
    int keyRowId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {//??BUNDLE IS NULL

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_food, container, false);
        Button addFood =(Button) view.findViewById(R.id.addFoodDone);
         name = (TextView) view.findViewById(R.id.addFoodName);
         calories = (TextView) view.findViewById(R.id.editTextCalories);
         fat = (TextView) view.findViewById(R.id.editTextFat);
         carbs = (TextView) view.findViewById(R.id.editTextCarbs);
         protein = (TextView) view.findViewById(R.id.editTextProtein);
         date = (TextView) view.findViewById(R.id.foodDate);

        savedInstanceState  = getArguments();
        if (savedInstanceState!=null) //setTexts(savedInstanceState);
        {
            addFood.setVisibility(View.INVISIBLE);
            Cursor cursor=((NutritionTracker)getActivity()).getDbCursor();
            id=savedInstanceState.getLong("id");
            final FoodObject savedFoodObject = (FoodObject) savedInstanceState.getSerializable("food");
            keyRowId=savedFoodObject.getKey_ID();
            deleteButton =(Button) view.findViewById(R.id.deleteFoodButton);
            deleteButton.setVisibility(View.VISIBLE);
            editButton =(Button) view.findViewById(R.id.editFood);
            editButton.setVisibility(View.VISIBLE);

            /*((NutritionTracker)getActivity()).setReadableDb();
            SQLiteDatabase db  =((NutritionTracker)getActivity()).getDb();

            cursor = db.query(
                    DATABASE_NAME,                     // The table to query
                    ((NutritionTracker)getActivity()).projection,                               // The columns to return
                    null,                                // The columns for the WHERE clause
                    null,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                 // The sort order
            );

            // int i=cursor.getInt(cursor.getColumnIndexOrThrow(Key_ID));

            while(cursor.moveToNext()) {
                int a=cursor.getInt(cursor.getColumnIndexOrThrow(Key_ID));
                if (cursor.getInt(cursor.getColumnIndexOrThrow(Key_ID))==(id+1)){
                    namerow = cursor.getString(cursor.getColumnIndexOrThrow(KEY_Name));
                    calrow = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_Calorie));
                    fatrow = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_Fat));
                    carbrow = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_Carb));
                    proteinrow= cursor.getInt(cursor.getColumnIndexOrThrow(KEY_Protein));
                    daterow= cursor.getString(cursor.getColumnIndexOrThrow(KEY_Date));
                    break;
                }
            }
            name.setText(namerow);
            calories.setText(String.valueOf(calrow));
            fat.setText(String.valueOf(fatrow));
            carbs.setText(String.valueOf(carbrow));
            protein.setText(String.valueOf(proteinrow));
            date.setText(String.valueOf(daterow));*/
            name.setText(savedFoodObject.getKEY_Name());
            calories.setText(savedFoodObject.getKEY_Calorie());
            fat.setText(savedFoodObject.getKEY_Fat());
            carbs.setText(savedFoodObject.getKEY_Carb());
            protein.setText(savedFoodObject.getKEY_Protein());
            date.setText(savedFoodObject.getKEY_Date());

            editButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //mkae food obj place on list
                    // update row
                    ContentValues cValues = new ContentValues();
                    FoodObject food= new FoodObject();
                    if (name.getText().toString().equals("")){
                        cValues.put(KEY_Name, getString(R.string.oops));
                        food.setKEY_Name(getString(R.string.oops));
                    }
                    else{
                        cValues.put(KEY_Name, name.getText().toString());
                        food.setKEY_Name(name.getText().toString());
                    }

                    //Accept blank values as 0
                    if (calories.getText().toString().equals("")){
                        cValues.put(KEY_Calorie, 0 );
                        food.setKEY_Calorie("0");
                    }
                    else {
                        cValues.put(KEY_Calorie, Integer.parseInt(calories.getText().toString()));
                        food.setKEY_Calorie(calories.getText().toString());
                    }
                    if (fat.getText().toString().equals("")){
                        cValues.put(KEY_Fat, 0 );
                        food.setKEY_Fat("0");
                    }
                    else {
                        cValues.put(KEY_Fat, Integer.parseInt(fat.getText().toString()) );
                        food.setKEY_Fat(fat.getText().toString());
                    }
                    if (carbs.getText().toString().equals("")){
                        cValues.put(KEY_Carb, 0 );
                        food.setKEY_Carb("0");
                    }
                    else {
                        cValues.put(KEY_Carb, Integer.parseInt(carbs.getText().toString()) );
                        food.setKEY_Carb(carbs.getText().toString());

                    }
                    if (protein.getText().toString().equals("")){
                        cValues.put(KEY_Protein, 0 );
                        food.setKEY_Protein("0");
                    }
                    else {
                        cValues.put(KEY_Protein, protein.getText().toString() );
                        food.setKEY_Protein(protein.getText().toString());

                    }
                    cValues.put(KEY_Date, (date.getText().toString()) );
                    food.setKEY_Date(date.getText().toString());
                   /* String namerow="" ;
                    int calrow=0 ;
                    int fatrow=0  ;
                    int carbrow=0 ;
                    int proteinrow=0 ;
                    String daterow="";*/
                    //not row, get     text from view
                    String debug=date.getText().toString();
                    ((NutritionTracker)getActivity()).getDb().execSQL("UPDATE "+DATABASE_NAME+" " +
                            "SET "+KEY_Name+" = '"+name.getText().toString()+"', "
                            +KEY_Calorie+" = '"+calories.getText().toString()+"', "
                            +KEY_Fat+" = '"+fat.getText().toString()+"', "
                            +KEY_Carb+" = '"+carbs.getText().toString()+"', "
                            +KEY_Calorie+" = '"+calories.getText().toString()+"', "
                            +KEY_Date+" = '"+date.getText().toString()+"' "
                            +" WHERE "+Key_ID+" = "+(savedFoodObject.getKey_ID())+";");
                    ((NutritionTracker)getActivity()).foodArrayList.remove((int)id);
                    ((NutritionTracker)getActivity()).foodArrayList.add(food);
                    FrameLayout frameLayout=(FrameLayout)((NutritionTracker)getActivity()).findViewById(R.id.nutritionFrame);
                    frameLayout.setVisibility(View.GONE);
                    clearFields();
                    ((NutritionTracker)getActivity()).removeFragment();
                    ((NutritionTracker)getActivity()).updateArray();

                }
        });

        }



        setDate(view);


//Save new food to database, add to list, close fragment
        addFood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContentValues cValues = new ContentValues();
                FoodObject food= new FoodObject();
                if (name.getText().toString().equals("")){
                cValues.put(KEY_Name, getString(R.string.oops));
                    food.setKEY_Name(getString(R.string.oops));
                }
                else{
                    cValues.put(KEY_Name, name.getText().toString());
                    food.setKEY_Name(name.getText().toString());
                }

                                       //Accept blank values as 0
                if (calories.getText().toString().equals("")){
                    cValues.put(KEY_Calorie, 0 );
                    food.setKEY_Calorie("0");
                }
                else {
                    cValues.put(KEY_Calorie, Integer.parseInt(calories.getText().toString()));
                    food.setKEY_Calorie(calories.getText().toString());
                }
                if (fat.getText().toString().equals("")){
                    cValues.put(KEY_Fat, 0 );
                    food.setKEY_Fat("0");
                }
                else {
                        cValues.put(KEY_Fat, Integer.parseInt(fat.getText().toString()) );
                        food.setKEY_Fat(fat.getText().toString());
                }
                if (carbs.getText().toString().equals("")){
                    cValues.put(KEY_Carb, 0 );
                    food.setKEY_Carb("0");
                }
                else {
                        cValues.put(KEY_Carb, Integer.parseInt(carbs.getText().toString()) );
                    food.setKEY_Carb(carbs.getText().toString());

                }
                if (protein.getText().toString().equals("")){
                    cValues.put(KEY_Protein, 0 );
                    food.setKEY_Protein("0");
                }
                else {
                    cValues.put(KEY_Protein, protein.getText().toString() );
                    food.setKEY_Protein(protein.getText().toString());

                }
                cValues.put(KEY_Date, (date.getText().toString()) );
                food.setKEY_Date(date.getText().toString());

                Date currentDateTime = Calendar.getInstance().getTime();
                String time=currentDateTime.toString();
                time = time.substring(11, time.length() - 12);
                cValues.put(KEY_Time, time );
                food.setKEY_Time(time);
                long newRowId=   ((NutritionTracker)getActivity()).getDb().insert(DATABASE_NAME,null, cValues);

                ((NutritionTracker)getActivity()).foodArrayList.add(food);
                ((NutritionTracker)getActivity()).updateArray();
                //((NutritionTracker)getActivity()).updateArray( name.getText().toString() );

                clearFields();

                FrameLayout frameLayout=(FrameLayout)((NutritionTracker)getActivity()).findViewById(R.id.nutritionFrame);


                frameLayout.setVisibility(View.GONE);
                ((NutritionTracker)getActivity()).removeFragment();

                //    ((NutritionTracker)getActivity()).getListAdapter().notifyDataSetChanged();
                /*
                Intent intent = new Intent();
                intent.putExtra("listItemID", id);
                intent.putExtra("text", stringText);
//send code
                if (code != 999) {//WORKS
                    //  getActivity().setResult(888, intent);
                    ((ChatWindow)getActivity()).removeItem(10, intent,stringText);

                } else{
                    ((phoneMsgFragActivity)getActivity()).setResult(888, intent);
                    getActivity().finish();

                }*/
            }
            //removeItem(1, intent,view);
            //  getActivity().finish();
            //  startActivityForResult(intent,1);

        });


        Button deleteFood =(Button) view.findViewById(R.id.deleteFoodButton);
        deleteFood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int b=((NutritionTracker)getActivity()).foodArrayList.size();

                ((NutritionTracker)getActivity()).foodArrayList.remove((int)id);
                int c=((NutritionTracker)getActivity()).foodArrayList.size();


                ((NutritionTracker)getActivity()).getDb().execSQL("DELETE FROM "+DATABASE_NAME+" WHERE "+Key_ID+" = "+keyRowId+";");
                Log.d("output",  ((NutritionTracker)getActivity()).getTableAsString(((NutritionTracker)getActivity()).getDb(),DATABASE_NAME)+"\n array size: ");

                clearFields();
                FrameLayout frameLayout=(FrameLayout)((NutritionTracker)getActivity()).findViewById(R.id.nutritionFrame);
                frameLayout.setVisibility(View.GONE);
                deleteButton.setVisibility(View.INVISIBLE);
                ((NutritionTracker)getActivity()).removeFragment();
                ((NutritionTracker)getActivity()).updateArray();
                //delete row, close frag, delete from list,
            }
        });

        return view;
    }

    private void setDate(View view){
        /* Android_coder and Naveed Ahmad. Datepicker: How to popup datepicker when click on edittext. Stack Overflow
            https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
        */
        myCalendar = Calendar.getInstance();

        dateText= (EditText) view.findViewById(R.id.foodDate);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateText.setText(sdf.format(myCalendar.getTime()));
            }

        };

        dateText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(  ((NutritionTracker)getActivity()), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        Date currentTime = Calendar.getInstance().getTime();

        myCalendar.setTime(currentTime);
        updateLabel();
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (dateText.getText().toString().equals(""))
        dateText.setText(sdf.format(myCalendar.getTime()));
    }

    private void clearFields(){
        name.setText("");
        calories.setText("");
        fat.setText("");
        carbs.setText("");
        protein.setText("");
        date.setText("");
    }
}