package com.joaoretamero.sunshine.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.joaoretamero.sunshine.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utility {

    public static String getPreferredLocation(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_location_key),
                context.getString(R.string.pref_location_default_value));
    }

    public static boolean isMetric(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_unit_key),
                context.getString(R.string.pref_unit_metric_key))
                .equals(context.getString(R.string.pref_unit_metric_key));
    }

    public static String formatTemperature(Context context, double temperature, boolean isMetric) {
        double temp;
        if (!isMetric) {
            temp = 9 * temperature / 5 + 32;
        } else {
            temp = temperature;
        }
        return String.format(context.getString(R.string.format_temperature), temp);
    }

    public static String formatDate(long dateInMillis) {
        Date date = new Date(dateInMillis);
        return DateFormat.getDateInstance().format(date);
    }

    public static String getFriendlyDayString(Context context, long dateTimeInMillis) {
        Calendar calendar = Calendar.getInstance();
        int currentJulianDay = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTimeInMillis(dateTimeInMillis);
        int julianDay = calendar.get(Calendar.DAY_OF_YEAR);

        if (currentJulianDay == julianDay) {
            SimpleDateFormat monthDayFormat = new SimpleDateFormat("MMM dd");
            String today = context.getString(R.string.today);
            String stringToFormat = context.getString(R.string.format_full_friendly_date);
            return String.format(stringToFormat, today, monthDayFormat.format(dateTimeInMillis));
        } else if (julianDay == currentJulianDay + 1) {
            return context.getString(R.string.tomorrow);
        } else if (julianDay < currentJulianDay + 7) {
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
            return dayFormat.format(dateTimeInMillis);
        } else {
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MM dd");
            return shortenedDateFormat.format(dateTimeInMillis);
        }
    }
}
