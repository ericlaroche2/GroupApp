package com.example.ericpc.groupapp;



import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Thermostat extends AppCompatActivity {

    ListView listView1;
    Button newButton;
    View parentLayout;
    ProgressBar progressBar;

    ArrayList<String> arrayS;
    ThermostatAdapter thermostatAdapter;
    SQLiteDatabase db;

    public static String ACTIVITY_NAME = "Thermostat";
    boolean isTablet = false;
    Cursor cursor;
    ThermostatFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermostat);
        parentLayout = findViewById(R.id.thermostat);
        if(findViewById(R.id.thermostatFrame)!=null){isTablet=true;progressBar = findViewById(R.id.progressBar2);}

        listView1 = findViewById(R.id.ThermostatListView);
        newButton = findViewById(R.id.ThermostatNewButton);
        arrayS = new ArrayList();
        thermostatAdapter =new ThermostatAdapter( this );
        listView1.setAdapter (thermostatAdapter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.thermostat_toolbar);
        setSupportActionBar(myToolbar);

        DatabaseLoader dbl = new DatabaseLoader();
        dbl.execute();

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            long ID;
                if(isTablet ==true){
                    fragment = new ThermostatFragment();
                    fragment.setArguments(new Bundle());
                    getFragmentManager().beginTransaction().replace(R.id.thermostatFrame, fragment).commit();
                }


                if(isTablet == false){
                    Intent intent = new Intent(Thermostat.this, ThermostatDetails.class);
                    startActivityForResult(intent, 11);}
            }
        });


        listView1.setClickable(true);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                String day = thermostatAdapter.getItemDay(position);
                int hour = thermostatAdapter.getItemHour(position);
                int minute = thermostatAdapter.getItemMinute(position);
                int temp = thermostatAdapter.getItemTemp(position);
                long ID = thermostatAdapter.getItemId(position);


                if(isTablet ==true){
                    fragment = new ThermostatFragment();
                    Bundle args = new Bundle();
                    args.putString("day", day);
                    args.putInt("hour", hour);
                    args.putInt("minute", minute);
                    args.putLong("temp", temp);
                    args.putLong("ID", ID);
                    args.putInt("position", position);
                    fragment.setArguments(args);
                    getFragmentManager().beginTransaction().replace(R.id.thermostatFrame, fragment).commit();
                }


                if(isTablet == false){
                    Intent intent = new Intent(Thermostat.this, ThermostatDetails.class);
                    intent.putExtra("day", day);
                    intent.putExtra("hour", hour);
                    intent.putExtra("minute", minute);
                    intent.putExtra("temp", temp);
                    intent.putExtra("ID", ID);
                    intent.putExtra("position", position);
                    startActivityForResult(intent, 11);}
            }
        });
    }

    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.thermostat_menu, m );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){
        int id = mi.getItemId();
        switch(id){
            case R.id.action_one:

                LayoutInflater inflater = Thermostat.this.getLayoutInflater();
                View result = inflater.inflate(R.layout.thermostat_customdialogue, null);
               TextView text = result.findViewById(R.id.ThermoDialogue);
               text.setText(getString(R.string.T_Author)+"\n\n"+getString(R.string.T_Version)+"\n\n"+getString(R.string.T_Instructions));
                AlertDialog.Builder alert = new AlertDialog.Builder(Thermostat.this);
                alert.setView(result);
                alert.setTitle(getString(R.string.T_About));
                alert.setPositiveButton("OK",null);
                alert.show();

        }
        return true;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
    }

    public void deleteRule(Long ID, int position){
        db.execSQL("DELETE FROM " + ThermostatDatabaseHelper.TABLE_NAME + " WHERE " + ThermostatDatabaseHelper.Key_ID + "= '" + ID + "'");
        int y = ID.intValue();
        arrayS.remove(position);
        cursor = db.query(false, ThermostatDatabaseHelper.TABLE_NAME,new String[] {ThermostatDatabaseHelper.Key_ID, ThermostatDatabaseHelper.KEY_DAY, ThermostatDatabaseHelper.KEY_HOUR, ThermostatDatabaseHelper.KEY_MINUTE, ThermostatDatabaseHelper.KEY_TEMP}, null, null, null,null, null,  null, null);
        thermostatAdapter.notifyDataSetChanged();

        if(isTablet){getFragmentManager().beginTransaction().remove(fragment).commit();}

        Snackbar.make(parentLayout, getString(R.string.T_Deleted), Snackbar.LENGTH_LONG).show();
    }

    public void saveRule(String day, int hour, int minute, int temp, Long ID, int position){
        db.execSQL("Update " + ThermostatDatabaseHelper.TABLE_NAME + " SET " + ThermostatDatabaseHelper.KEY_DAY + " = '" + day + "' , " + ThermostatDatabaseHelper.KEY_HOUR + " = '" + hour + "' , "+ ThermostatDatabaseHelper.KEY_MINUTE + " = '" + minute + "' , " + ThermostatDatabaseHelper.KEY_TEMP + " = '" + temp + "' WHERE " + ThermostatDatabaseHelper.Key_ID + "= '" + ID + "'");

        String hp="";
        String mp="";
        if(hour<10){hp = "0";}
        if(minute<10){mp ="0";}

        arrayS.set(position, day + ", " + hp + hour + ":" + mp + minute + " Temp -> " + temp + "\u2103");
        cursor = db.query(false, ThermostatDatabaseHelper.TABLE_NAME,new String[] {ThermostatDatabaseHelper.Key_ID, ThermostatDatabaseHelper.KEY_DAY, ThermostatDatabaseHelper.KEY_HOUR, ThermostatDatabaseHelper.KEY_MINUTE, ThermostatDatabaseHelper.KEY_TEMP}, null, null, null,null, null,  null, null);
        thermostatAdapter.notifyDataSetChanged();


        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(Thermostat.this, getString(R.string.T_Updated), duration);
        toast.show();
    }

    public void saveNewRule(String day, int hour, int minute, int temp){

            String hp="";
            String mp="";
            if(hour<10){hp = "0";}
            if(minute<10){mp ="0";}

            arrayS.add(day + ", " + hp + hour + ":" + mp + minute + " Temp -> " + temp + "\u2103");

            ContentValues values = new ContentValues();
            values.put(ThermostatDatabaseHelper.KEY_DAY, day);
            values.put(ThermostatDatabaseHelper.KEY_HOUR, hour);
            values.put(ThermostatDatabaseHelper.KEY_MINUTE, minute);
            values.put(ThermostatDatabaseHelper.KEY_TEMP, temp);
            db.insert(ThermostatDatabaseHelper.TABLE_NAME, null, values);

        cursor = db.query(false, ThermostatDatabaseHelper.TABLE_NAME,new String[] {ThermostatDatabaseHelper.Key_ID, ThermostatDatabaseHelper.KEY_DAY, ThermostatDatabaseHelper.KEY_HOUR, ThermostatDatabaseHelper.KEY_MINUTE, ThermostatDatabaseHelper.KEY_TEMP}, null, null, null,null, null,  null, null);
        thermostatAdapter.notifyDataSetChanged();


        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(Thermostat.this, getString(R.string.T_Added), duration);
        toast.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11) {

            if(resultCode==11) {
                Long ID = data.getLongExtra("ID", 0);
                int position = data.getIntExtra("position", 0);
                deleteRule(ID,position);
            }

            if(resultCode == 12){}

            if(resultCode == 13){
                String day = data.getStringExtra("day");
                int hour = data.getIntExtra("hour", 0);
                int minute = data.getIntExtra("minute", 0);
                int temp = data.getIntExtra("temp", 0);
                long ID = data.getLongExtra("ID", 0);
                int position = data.getIntExtra("position", 0);
                saveRule(day, hour, minute, temp, ID, position);
            }

            if(resultCode == 14){
                String day = data.getStringExtra("day");
                int hour = data.getIntExtra("hour", 0);
                int minute = data.getIntExtra("minute", 0);
                int temp = data.getIntExtra("temp", 0);
                saveNewRule(day, hour, minute, temp);
            }
        }
    }

    public class ThermostatAdapter extends ArrayAdapter<String>{

        private ThermostatAdapter(Context ctx){
            super(ctx,0);
        }
        public int getCount(){
            return arrayS.size();
        }
        public String getItem(int position){
            return arrayS.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = Thermostat.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.thermostat_rule, null);

            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }

        public long getItemId(int position){
            cursor.moveToPosition(position);
            long place = cursor.getLong( cursor.getColumnIndex( ThermostatDatabaseHelper.Key_ID));
            return place;
        }

        public String getItemDay(int position){
            cursor.moveToPosition(position);
            String day = cursor.getString(cursor.getColumnIndex( ThermostatDatabaseHelper.KEY_DAY));
            return day;
        }

        public int getItemHour(int position){
            cursor.moveToPosition(position);
            int hour = cursor.getInt(cursor.getColumnIndex( ThermostatDatabaseHelper.KEY_HOUR));
            return hour;
        }

        public int getItemMinute(int position){
            cursor.moveToPosition(position);
            int minute = cursor.getInt(cursor.getColumnIndex( ThermostatDatabaseHelper.KEY_MINUTE));
            return minute;
        }

        public int getItemTemp(int position){
            cursor.moveToPosition(position);
            int temp = cursor.getInt( cursor.getColumnIndex( ThermostatDatabaseHelper.KEY_TEMP));
            return temp;
        }
    }

    public class DatabaseLoader extends AsyncTask<String, Integer, String> {

        @Override
        public String doInBackground(String ...args) {

            ThermostatDatabaseHelper dbHelper = new ThermostatDatabaseHelper(Thermostat.this);
            db = dbHelper.getWritableDatabase();
            cursor = db.query(false, ThermostatDatabaseHelper.TABLE_NAME,new String[] {ThermostatDatabaseHelper.Key_ID, ThermostatDatabaseHelper.KEY_DAY, ThermostatDatabaseHelper.KEY_HOUR, ThermostatDatabaseHelper.KEY_MINUTE, ThermostatDatabaseHelper.KEY_TEMP}, null, null, null,null, null,  null, null);

            publishProgress(25);

            int colIndex = cursor.getColumnIndex( ThermostatDatabaseHelper.KEY_DAY );
            cursor.moveToNext();
            for(int i = 0; i < cursor.getCount(); i++){
                String day = cursor.getString( cursor.getColumnIndex( ThermostatDatabaseHelper.KEY_DAY ));
                int hour = cursor.getInt(cursor.getColumnIndex( ThermostatDatabaseHelper.KEY_HOUR));
                int minute = cursor.getInt(cursor.getColumnIndex( ThermostatDatabaseHelper.KEY_HOUR));
                int temp = cursor.getInt(cursor.getColumnIndex( ThermostatDatabaseHelper.KEY_TEMP));

                String hp="";
                String mp="";
                if(hour<10){hp = "0";}
                if(minute<10){mp ="0";}
                publishProgress(25+(75*(i/cursor.getCount())));
                arrayS.add(day+", "+ hp+ hour+":"+mp+minute +" Temp -> "+temp+"\u2103");
                thermostatAdapter.notifyDataSetChanged();
                cursor.moveToNext();
            }

            return "Go to OnPostExecute";
        }

        public void onProgressUpdate(Integer ...i )
        {
            Log.i(ACTIVITY_NAME,"In onProgressUpdate: " + i[0] +"%");
           if(isTablet){progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(i[0]);}
        }


        public void onPostExecute(String s )
        {
            Log.i(ACTIVITY_NAME,"In onPostExcecute");
           if(isTablet){progressBar.setVisibility(View.INVISIBLE);}
        }


    }


}


