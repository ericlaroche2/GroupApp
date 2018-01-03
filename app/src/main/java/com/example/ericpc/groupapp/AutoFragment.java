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


public class AutoFragment extends Fragment {

    private Activity callingActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        final String liters = getArguments().getString("liters");
        final String price = getArguments().getString("price");
        final String kiloms = getArguments().getString("kiloms");
        final Long id = getArguments().getLong("id");
        final int position = getArguments().getInt("position");

        View view = inflater.inflate(R.layout.list_details_fragment, container, false);

        TextView tvLiters = view.findViewById(R.id.textViewLiters);
        TextView tvPrice = view.findViewById(R.id.textViewPrice);
        TextView tvKiloms = view.findViewById(R.id.textViewKiloms);

        tvLiters.setText("Liters: " + liters);
        tvPrice.setText("Price: " + price);
        tvKiloms.setText("Kilometers: " + kiloms);

        Button btnDeleteMessage = view.findViewById(R.id.btnDeleteEntry);

        switch(callingActivity.getLocalClassName()){
            case "DisplayList":
                btnDeleteMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((DisplayList)callingActivity).deleteEntry(id.toString(), position);
                        callingActivity.getFragmentManager().beginTransaction().
                                remove(AutoFragment.this).commit();
                    }
                });
                break;

            case "AutoDetailsActivity":
                btnDeleteMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((AutoDetailsActivity) callingActivity).deleteEntry(id, position);
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
        this.callingActivity = activity;
    }
}