package com.example.ericpc.groupapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

public class AutoDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Auto.db";
    private static final int VERSION_NUM = 5;
    public static final String TABLE_NAME = "GASOLINE_TABLE";
    public static final String KEY_ID = "id";
    public static final String KEY_LITERS = "liters";
    public static final String KEY_PRICE = "price";
    public static final String KEY_KILOMETERS = "kilometers";
    public static final String KEY_DATE = "date";
    protected static final String ACTIVITY_NAME = "AutoDatabaseHelper";

    public AutoDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_LITERS + " TEXT," +
                KEY_PRICE + " TEXT," +
                KEY_KILOMETERS + " TEXT," +
                KEY_DATE + " TEXT)"
        );
        Log.i(ACTIVITY_NAME, "Calling onCreate()");
    }

    public void addInfo(SQLiteDatabase db, String liters, String price, String kms, String date){

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_LITERS, liters);
        contentValues.put(KEY_PRICE, price);
        contentValues.put(KEY_KILOMETERS, kms);
        contentValues.put(KEY_DATE, date);
        db.insert(TABLE_NAME,null, contentValues);

        Log.i(ACTIVITY_NAME, "Writing to DB");

    }

    public Cursor getInfo(SQLiteDatabase db){

        String[] projections = {KEY_LITERS, KEY_PRICE, KEY_KILOMETERS, KEY_DATE};

        Cursor cursor = db.query(TABLE_NAME, projections, null, null, null, null, null);
        Log.i(ACTIVITY_NAME, "Retrieving from DB");

        return cursor;
    }

    public void deleteInfo(SQLiteDatabase db, Long id){
        db.delete(TABLE_NAME, KEY_ID+"="+id, null);
    }

    public double getAverageLiters(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("SELECT AVG(liters) FROM GASOLINE_TABLE WHERE date >= CURRENT_TIMESTAMP -30", null);
        cursor.moveToFirst();
        return cursor.getDouble(0);
    }

    public double getAveragePrice(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("SELECT AVG(price) FROM GASOLINE_TABLE WHERE date >= CURRENT_TIMESTAMP -30" ,null);
        cursor.moveToFirst();
        return cursor.getDouble(0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpdate(), oldVersion=" + oldVersion + ", newVersion=" + newVersion);
    }

}