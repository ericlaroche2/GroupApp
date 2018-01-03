package com.example.ericpc.groupapp;

import java.io.Serializable;

/**
 * Created by EricPc on 2017-12-27.
 */

public class FoodObject implements Serializable {

        int Key_ID ;
        String KEY_Name ;
        String  KEY_Calorie;
        String  KEY_Fat ;
        String  KEY_Carb;
        String  KEY_Protein ;
        String  KEY_Date ;
        String  KEY_Time ;

        public String getKEY_Time() {
            return KEY_Time;
        }

        public void setKEY_Time(String KEY_Time) {
            this.KEY_Time = KEY_Time;
        }

        public int getKey_ID() {
            return Key_ID;
        }

        public void setKey_ID(int key_ID) {
            Key_ID = key_ID;
        }

        public String getKEY_Name() {
            return KEY_Name;
        }

        public void setKEY_Name(String KEY_Name) {
            this.KEY_Name = KEY_Name;
        }

        public String getKEY_Calorie() {
            return KEY_Calorie;
        }

        public void setKEY_Calorie(String KEY_Calorie) {
            this.KEY_Calorie = KEY_Calorie;
        }

        public String getKEY_Fat() {
            return KEY_Fat;
        }

        public void setKEY_Fat(String KEY_Fat) {
            this.KEY_Fat = KEY_Fat;
        }

        public String getKEY_Carb() {
            return KEY_Carb;
        }

        public void setKEY_Carb(String KEY_Carb) {
            this.KEY_Carb = KEY_Carb;
        }

        public String getKEY_Protein() {
            return KEY_Protein;
        }

        public void setKEY_Protein(String KEY_Protein) {
            this.KEY_Protein = KEY_Protein;
        }

        public String getKEY_Date() {
            return KEY_Date;
        }

        public void setKEY_Date(String KEY_Date) {
            this.KEY_Date = KEY_Date;
        }

}
