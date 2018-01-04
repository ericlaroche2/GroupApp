package com.example.ericpc.groupapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class ThermostatDatabaseHelper extends SQLiteOpenHelper {

    private static final    int VERSION_NUM = 2 ;
    private static final    String DATABASE_NAME = "Thermostat.db" ;
    public static           String TABLE_NAME = "Rule_TABLE";
    public static           String Key_ID = "KEY_ID";
    public static           String KEY_DAY = "KEY_DAY";
    public static           String KEY_HOUR = "KEY_HOUR";
    public static           String KEY_MINUTE = "KEY_MINUTE";
    public static           String KEY_TEMP = "KEY_TEMP";
    private static final    String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static          String CREATE_TABLE = "create table "  + TABLE_NAME  +
            " ( " + Key_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_DAY + " text, "
            + KEY_HOUR + " INTEGER, "
            + KEY_MINUTE + " INTEGER, "
            + KEY_TEMP + " INTEGER "
            + " ); " ;

    public ThermostatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.i("ThermostatDBHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
        Log.i("ThermostatDBHelper", "Calling onUpgrade, oldVersion=" + i + " newVersion=" + i1);
    }
}
