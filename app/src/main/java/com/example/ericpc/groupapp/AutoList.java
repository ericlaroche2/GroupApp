package com.example.ericpc.groupapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexi on 2017-12-29.
 */

public class AutoList {

    private String liters, price, kiloms,  date;

    public AutoList(String liters, String price, String kiloms, String date){

        this.setLiters(liters);
        this.setPrice(price);
        this.setKiloms(kiloms);
        this.setDate(date);

    }

    public String getLiters() {
        return liters;
    }

    public void setLiters(String liters) {
        this.liters = liters;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getKiloms() {
        return kiloms;
    }

    public void setKiloms(String kiloms) {
        this.kiloms = kiloms;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }
}
