package com.example.capstonedesignandroid.StaticMethodAndOthers;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.loader.content.CursorLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DefinedMethod {

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static int getYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static int getDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static ArrayList declareEightToTwentyoneTimeArrayList(ArrayList<String> arrayList){
        arrayList.add("8:00");arrayList.add("8:30");arrayList.add("9:00");arrayList.add("9:30");arrayList.add("10:00");arrayList.add("10:30");
        arrayList.add("11:00");arrayList.add("11:30");arrayList.add("12:00");arrayList.add("12:30");arrayList.add("13:00");arrayList.add("13:30");
        arrayList.add("14:00");arrayList.add("14:30");arrayList.add("15:00");arrayList.add("15:30");arrayList.add("16:00");arrayList.add("16:30");
        arrayList.add("17:00");arrayList.add("17:30");arrayList.add("18:00");arrayList.add("18:30");arrayList.add("19:00");arrayList.add("19:30");
        arrayList.add("20:00");arrayList.add("20:30");arrayList.add("21:00");
        return arrayList;
    }

}
