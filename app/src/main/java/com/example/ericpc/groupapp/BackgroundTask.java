package com.example.ericpc.groupapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.ericpc.groupapp.AutoDatabaseHelper.KEY_DATE;
import static com.example.ericpc.groupapp.AutoDatabaseHelper.KEY_KILOMETERS;
import static com.example.ericpc.groupapp.AutoDatabaseHelper.KEY_LITERS;
import static com.example.ericpc.groupapp.AutoDatabaseHelper.KEY_PRICE;

/**
 * Created by alexis on 2017-12-29.
 */

public class BackgroundTask extends AsyncTask <String, AutoList, String> {

    Context ctx;
    AutoAdapter adapter;
    Activity activity;
    ListView listview;
    Cursor cursor;

    public Cursor getCursor() {
        return cursor;
    }


    BackgroundTask(Context ctx) {

        this.ctx = ctx;
        activity = (Activity) ctx;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

    }


    @Override
    protected String doInBackground(String... values) {

        String method = values[0];

        AutoDatabaseHelper dbHelper = new AutoDatabaseHelper(ctx);

        if (method.equals("add_info")) {
            String liters = values[1];
            String price = values[2];
            String kiloms = values[3];
            String date = values[4];
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            dbHelper.addInfo(db, liters, price, kiloms, date);

            return "Data has been successfully saved";

        } else if (method.equals("delete_info")) {
            String initialID = values[1];
            Long id = Long.valueOf(initialID);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            dbHelper.deleteInfo(db, id);
            // adapter = new AutoAdapter(ctx, R.layout.data_row);
            //adapter.notifyDataSetChanged();

            return "Data has been successfully removed";

        } else if (method.equals("get_info")) {
            listview = activity.findViewById(R.id.displayListView);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            cursor = dbHelper.getInfo(db);
            adapter = new AutoAdapter(ctx, R.layout.data_row);

            String liters, price, kilometers, date;

            while (cursor.moveToNext()) {

                liters = cursor.getString(cursor.getColumnIndex(KEY_LITERS));
                price = cursor.getString(cursor.getColumnIndex(KEY_PRICE));
                kilometers = cursor.getString(cursor.getColumnIndex(KEY_KILOMETERS));
                date = cursor.getString(cursor.getColumnIndex(KEY_DATE));


                AutoList autoList = new AutoList(liters, price, kilometers, date);
                publishProgress(autoList);
            }


            return "get_info";
        }


        return null;
    }


    @Override
    protected void onProgressUpdate(AutoList... values) {
        adapter.add(values[0]);

    }

    @Override
    protected void onPostExecute(String result) {

        if (result.equals("get_info")) {
            listview.setAdapter(adapter);
        } else if (result.equals("add_info")) {
            listview.setAdapter(adapter);
        } else if (result.equals("delete_info")){
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
    public class AutoAdapter extends ArrayAdapter {

        List list = new ArrayList();
        DataHolder dataHolder;

        public AutoAdapter(Context context, int resource) {
            super(context, resource);
        }

        public void add(AutoList object) {
            list.add(object);
            super.add(object);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            //list.remove(position);
            AutoDatabaseHelper dbHelper = new AutoDatabaseHelper(ctx);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + AutoDatabaseHelper.TABLE_NAME, null);
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndex(AutoDatabaseHelper.KEY_ID));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null) {

                LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.data_row, parent, false);
                dataHolder = new DataHolder();
                dataHolder.textViewDate = row.findViewById(R.id.textViewDate);
                row.setTag(dataHolder);
            } else {
                dataHolder = (DataHolder) row.getTag();
            }

            AutoList autoList = (AutoList) getItem(position);
            dataHolder.textViewDate.setText(autoList.getDate());

            return row;
        }
    }



        static class DataHolder {

            TextView textViewDate;
        }

}