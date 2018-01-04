package com.example.ericpc.groupapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

public class ThermostatFragment extends Fragment {

    Button btnDelete;
    Button saveButton;
    Button newRuleButton;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TimePicker timePicker;
    NumberPicker numberPicker;

    String day;
    int hour;
    int minute;
    int temp;

    int position;
    long ID;

    private Activity callingActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_thermostat_fragment, container, false);
        Bundle bundle = this.getArguments();

        timePicker = (TimePicker)view.findViewById(R.id.timePicker);
        radioGroup = view.findViewById(R.id.radios);
        numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
        btnDelete = view.findViewById(R.id.btnDelete);
        saveButton = view.findViewById(R.id.saveButton);
        newRuleButton = view.findViewById(R.id.newRuleButton);

        day = bundle.getString("day","New");
        if(day.equals("New")){
            btnDelete.setEnabled(false);
            saveButton.setEnabled(false);
            day = "Monday";
        }

        switch (day) {
            case "Monday":
                radioButton = (RadioButton) view.findViewById(R.id.Monday);
                radioButton.toggle();
                break;
            case "Tuesday":
                radioButton = (RadioButton) view.findViewById(R.id.Tuesday);
                radioButton.toggle();
                break;
            case "Wednesday":
                radioButton = (RadioButton) view.findViewById(R.id.Wednesday);
                radioButton.toggle();
                break;
            case "Thursday":
                radioButton = (RadioButton) view.findViewById(R.id.Thursday);
                radioButton.toggle();
                break;
            case "Friday":
                radioButton = (RadioButton) view.findViewById(R.id.Friday);
                radioButton.toggle();
                break;
            case "Saturday":
                radioButton = (RadioButton) view.findViewById(R.id.Saturday);
                radioButton.toggle();
                break;
            case "Sunday":
                radioButton = (RadioButton) view.findViewById(R.id.Sunday);
                radioButton.toggle();
                break;
        }



        hour = bundle.getInt("hour");
        timePicker.setCurrentHour(hour);
        minute = bundle.getInt("minute");
        timePicker.setCurrentMinute(minute);


        String[] nums = new String[41];
        for(int i=0; i<nums.length; i++)
            nums[i] = Integer.toString(i);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(40);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDisplayedValues(nums);

        temp = bundle.getInt("temp");
        numberPicker.setValue(temp);

        position = bundle.getInt("position");
        ID = bundle.getLong("ID");




        if(callingActivity.getLocalClassName().equals("ThermostatDetails")) {

            btnDelete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {

                    AlertDialog.Builder builder = new AlertDialog.Builder((ThermostatDetails)callingActivity);
                    builder.setTitle(getString(R.string.T_DeleteQuery));
                    builder.setPositiveButton(getString(R.string.T_Delete), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            setValues();
                            ((ThermostatDetails)callingActivity).deleteRule(ID, position);
                        }
                    });
                    builder.setNegativeButton(getString(R.string.T_Cancel), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    setValues();
                    ((ThermostatDetails)callingActivity).saveRule(day, hour, minute, temp, ID, position);
                }
            });

            newRuleButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    setValues();
                    ((ThermostatDetails)callingActivity).saveNewRule(day, hour, minute, temp);
                }
            });
        }

        else{
            btnDelete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder((Thermostat)callingActivity);
                    builder.setTitle(getString(R.string.T_DeleteQuery));
                    builder.setPositiveButton(getString(R.string.T_Delete), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            setValues();
                            ((Thermostat)callingActivity).deleteRule(ID, position);
                        }
                    });
                    builder.setNegativeButton(getString(R.string.T_Cancel), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    setValues();
                    ((Thermostat)callingActivity).saveRule(day, hour, minute, temp, ID, position);
                }
            });

            newRuleButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    setValues();
                    ((Thermostat)callingActivity).saveNewRule(day, hour, minute, temp);
                }
            });
        }

        return view;
    }


    private void setValues(){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton)getView().findViewById(radioId);
        String radioDay = radioButton.getText().toString();

        switch (radioDay) {
            case "Mo":
                day = "Monday";
                break;
            case "Tu":
                day = "Tuesday";
                break;
            case "We":
                day = "Wednesday";
                break;
            case "Th":
                day = "Thursday";
                break;
            case "Fr":
                day = "Friday";
                break;
            case "Sa":
                day = "Saturday";
                break;
            case "Su":
                day = "Sunday";
                break;
        }

        hour = timePicker.getCurrentHour();
        minute = timePicker.getCurrentMinute();
        temp = numberPicker.getValue();
    }


    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.callingActivity = activity;
    }
}
