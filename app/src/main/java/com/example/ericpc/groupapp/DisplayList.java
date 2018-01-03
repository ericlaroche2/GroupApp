package com.example.ericpc.groupapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class DisplayList extends AppCompatActivity {
    private final int DELETE_REQUEST = 10;
    Boolean frameLayoutExists;
    private ListView window;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_list_layout);
        final BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("get_info");

        frameLayoutExists = findViewById(R.id.flAutoDetails) != null;

        window = (ListView)findViewById(R.id.displayListView);
        window.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                backgroundTask.getCursor().moveToPosition(position);
                String liters = backgroundTask.getCursor().getString(backgroundTask.getCursor().getColumnIndex(AutoDatabaseHelper.KEY_LITERS));
                String price = backgroundTask.getCursor().getString(backgroundTask.getCursor().getColumnIndex(AutoDatabaseHelper.KEY_PRICE));
                String kiloms = backgroundTask.getCursor().getString(backgroundTask.getCursor().getColumnIndex(AutoDatabaseHelper.KEY_KILOMETERS));
                Bundle bundle = new Bundle();
                bundle.putLong("id", id);
                bundle.putString("liters", liters);
                bundle.putString("price", price);
                bundle.putString("kiloms", kiloms);
                bundle.putInt("position", position);

                // FrameLayout was not loaded
                // Device width is less than 600px wide (i.e. layout-sq600dp)
                if (!frameLayoutExists){
                    Intent intent = new Intent(DisplayList.this, AutoDetailsActivity.class);
                    intent.putExtra("autoDetails", bundle);
                    startActivityForResult(intent, DELETE_REQUEST);
                }
                // Fragment loaded. Device width is more than 600px wide
                else {
                    AutoFragment autoFragment = new AutoFragment();
                    autoFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.flAutoDetails, autoFragment).commit();
                }

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == DELETE_REQUEST){
            if (resultCode == Activity.RESULT_OK){

                Bundle extras = data.getExtras();
                Long intialID = extras.getLong("id");
                String id = Long.toString(intialID);
                int position = extras.getInt("position");
                deleteEntry(id, position);
                finish();
                startActivity(getIntent());
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        startActivity(new Intent(this, DisplayList.class));
    }

    protected void deleteEntry(String id, int position){
        BackgroundTask bt = new BackgroundTask(this);
        bt.execute("delete_info",id);
    }
}
