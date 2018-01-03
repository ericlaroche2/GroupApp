package com.example.ericpc.groupapp;

/**
 * Created by EricPc on 2017-12-23.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by EricPc on 2017-12-15.
 */

public class NutritionDatabaseHelper  extends SQLiteOpenHelper {

    static String DATABASE_NAME = "Foods";
    static int VERSION_NUM = 53;

    public static final String Key_ID = "Key_id";
    public static final String KEY_Name = "Name";
    public static final String KEY_Calorie = "Calorie";
    public static final String KEY_Fat = "Fat";
    public static final String KEY_Carb = "Carb";
    public static final String KEY_Protein = "Protein";
    public static final String KEY_Date = "Date";
    public static final String KEY_Time = "Time";


    public static final String[] studentColumns = new String[]{
            Key_ID,
            KEY_Name,
            KEY_Calorie,
            KEY_Fat,
            KEY_Carb,
            KEY_Protein,
            KEY_Date,
            KEY_Time
    };

    private static final String CREATE_TABLE = "CREATE TABLE " + DATABASE_NAME +
            " (" + Key_ID + " INTEGER PRIMARY KEY, " +
            KEY_Name + " TEXT, " +
            KEY_Calorie + " INTEGER, " +
            KEY_Fat + " INTEGER, " +
            KEY_Carb + " INTEGER, " +
            KEY_Protein + " INTEGER, " +
            KEY_Date + " TEXT,  " +
            KEY_Time + " TEXT  " +
            ")";

    public NutritionDatabaseHelper(Context context) {
        // super(context, name, factory, version);
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        Log.i("database", " table Created--------------------");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + i + " newVersion=" + i1);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }

    public void addMessage(String message) {

        // db.execSQL("INSERT INTO "+DATABASE_NAME+ " "+ KEY_MESSAGE + " VALUES "+message+";");
    }
}