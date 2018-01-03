package com.example.ericpc.groupapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AutoDetailsActivity extends Activity {

    private Bundle details;
    private AutoFragment loadedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null){

            details = extras.getBundle("autoDetails");
            loadedFragment = new AutoFragment();
            loadedFragment.setArguments(details);
            getFragmentManager().beginTransaction()
                    .add(R.id.flAutoDetails, loadedFragment).commit();

        }
    }

    public void deleteEntry(Long id, int position){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("id", id);
        resultIntent.putExtra("position", position);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}