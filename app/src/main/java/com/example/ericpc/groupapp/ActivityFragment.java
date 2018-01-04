package com.example.ericpc.groupapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ElieMassaad on 2017-12-28.
 */

public class ActivityFragment extends Fragment {
    private Activity activityToCall;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        final String activityType = getArguments().getString("activity");
        final double minutes = getArguments().getDouble("minutes");
        final String comments = getArguments().getString("comments");
        final String date = getArguments().getString("date");
        final long id = getArguments().getLong("id");
        final int position = getArguments().getInt("position");



        View view = inflater.inflate(R.layout.activityfragment, container, false);

        TextView msgActivity =  view.findViewById(R.id.msgactivityType);
        TextView msgMinutes = view.findViewById(R.id.msgMinutes);
        TextView msgDate = view.findViewById(R.id.msgDate);
        TextView msgComment = view.findViewById(R.id.msgComment);
        TextView msgID = view.findViewById(R.id.msgID);



        msgActivity.setText("Activity: " + activityType);
       msgMinutes.setText("Minutes Completed: " + Double.toString(minutes));
        msgComment.setText("Comment: " + comments);
        msgDate.setText("Date and Time Completed: " + date);
        msgID.setText("ID: " + Long.toString(id));



        Button btnDeleteMessage = view.findViewById(R.id.btnDeleteMsg);

        switch(activityToCall.getLocalClassName()){
            case "ActivityView":
                btnDeleteMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ActivityView)activityToCall).deleteMessage(id, position);
                        activityToCall.getFragmentManager().beginTransaction().
                                remove(ActivityFragment.this).commit();
                    }
                });
                break;

            case "ActivityInformation":
                btnDeleteMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ActivityInformation) activityToCall).deleteMessage(id, position);

                    }
                });
                break;
        }
        return view;
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }


    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activityToCall = activity;
    }

}







