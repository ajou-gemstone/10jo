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
        arrayList.add("7:00"); arrayList.add("7:30"); arrayList.add("8:00"); arrayList.add("8:30"); arrayList.add("9:00");arrayList.add("9:30");
        arrayList.add("10:00");arrayList.add("10:30");arrayList.add("11:00");arrayList.add("11:30");arrayList.add("12:00");arrayList.add("12:30");
        arrayList.add("13:00");arrayList.add("13:30");arrayList.add("14:00");arrayList.add("14:30");arrayList.add("15:00");arrayList.add("15:30");
        arrayList.add("16:00");arrayList.add("16:30");arrayList.add("17:00");arrayList.add("17:30");arrayList.add("18:00");arrayList.add("18:30");
        arrayList.add("19:00");arrayList.add("19:30");arrayList.add("20:00");arrayList.add("20:30");arrayList.add("21:00");
        return arrayList;
    }

    public static String getTimeByPosition(int position){
        switch (position){
            case 0: return "7:00"; case 1: return "7:30"; case 2: return "8:00"; case 3: return "8:30"; case 4: return "9:00"; case 5: return "9:30";
            case 6: return "10:00"; case 7: return "10:300"; case 8: return "11:00"; case 9: return "11:30"; case 10: return "12:00"; case 11: return "12:30";
            case 12: return "13:00"; case 13: return "13:30"; case 14: return "14:00"; case 15: return "14:30"; case 16: return "15:00"; case 17: return "15:30";
            case 18: return "16:00"; case 19: return "16:30"; case 20: return "17:00"; case 21: return "17:30"; case 22: return "18:00"; case 23: return "18:30";
            case 24: return "19:00"; case 25: return "19:30"; case 26: return "20:00"; case 27: return "20:30"; case 28: return "21:00";
        }
        return "";
    }

}
