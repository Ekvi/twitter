package com.ekvilan.onixtwitter.utils;


import android.content.Context;

import com.ekvilan.onixtwitter.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateUtils {
    private static final String DATE_FORMAT = "d MMM";
    private static final String PREV_YEARS_FORMAT = "d MMM yyyy";

    public static String formatDate(Context context, Date date)  {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar today = Calendar.getInstance();

        String formattedDate;

        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
            if(calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                if(calendar.get(Calendar.HOUR) == today.get(Calendar.HOUR)) {
                    int delta = today.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE);
                    String minute = context.getResources().getString(R.string.minute);
                    formattedDate = delta != 0 ? delta + " " + minute : 1 + " " + minute;
                } else {
                    int delta = today.get(Calendar.HOUR) - calendar.get(Calendar.HOUR);
                    formattedDate = delta + " " + context.getResources().getString(R.string.hour);
                }
            } else {
                formattedDate = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(date);
            }
        } else {
            formattedDate = new SimpleDateFormat(PREV_YEARS_FORMAT, Locale.getDefault()).format(date);
        }

        return formattedDate;
    }
}
