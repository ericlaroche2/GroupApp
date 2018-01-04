package com.example.ericpc.groupapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ElieMassaad on 2017-12-26.
 */

public class ActivityDatabaseHelper extends SQLiteOpenHelper {
    protected static final int VERSION_NUM = 1;
    protected static final String TABLE_NAME = "TrackingActivities";
    protected static final String KEY_ID = "_id";
    protected static  final  String DATABASE_NAME = "ActivitiesData.db";
    protected static  final  String ACTIVITY_TYPE = "ActivityType";
    protected static  final  String MINUTES= "Minutes";
    protected static  final  String COMMENTS= "COMMENTS";
    protected static  final  String DATE = "Date";


    public ActivityDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  ACTIVITY_TYPE  + " text, " + MINUTES + " Real, " +  COMMENTS + " text, " + DATE + " text);");
        Log.i(" ActivityDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ActivityDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion= " + newVersion);

    }
}
