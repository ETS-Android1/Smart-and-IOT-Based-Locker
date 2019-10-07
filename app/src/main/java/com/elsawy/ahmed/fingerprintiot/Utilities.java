package com.elsawy.ahmed.fingerprintiot;

import java.util.Calendar;

public class Utilities {

    public static String getTime(long timestamp) {

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp * 1000);

        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        String timeForm = (calendar.get(Calendar.AM_PM)) == 0 ? "AM" : "PM";

        return hours +":"+ minutes + " " + timeForm;
    }

    public static String getDate(long timestamp) {

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp * 1000);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        return day +"-"+ month + "-" + year;
    }

}
