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
            String today = context.getString(R.string.today);
            String stringToFormat = context.getString(R.string.format_full_friendly_date);
            return String.format(stringToFormat, today, getFormattedMonthDay(context, dateTimeInMillis));
        } else if (julianDay < currentJulianDay + 7) {
            return getDayName(context, dateTimeInMillis);
        } else {
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MM dd");
            return shortenedDateFormat.format(dateTimeInMillis);
        }
    }

    public static String getDayName(Context context, long dateTimeInMillis) {
        Calendar calendar = Calendar.getInstance();
        int currentJulianDay = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTimeInMillis(dateTimeInMillis);
        int julianDay = calendar.get(Calendar.DAY_OF_YEAR);

        if (currentJulianDay == julianDay) {
            return context.getString(R.string.today);
        } else if (julianDay == currentJulianDay + 1) {
            return context.getString(R.string.tomorrow);
        } else {
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
            return dayFormat.format(dateTimeInMillis);
        }
    }

    public static String getFormattedMonthDay(Context context, long dateInMillis) {
        SimpleDateFormat monthDayFormat = new SimpleDateFormat("MMMM dd");
        return monthDayFormat.format(dateInMillis);
    }

    public static String getFormattedWind(Context context, float windSpeed, float degrees) {
        int windFormat;

        if (isMetric(context)) {
            windFormat = R.string.format_wind_kmh;
        } else {
            windFormat = R.string.format_wind_mph;
            windSpeed = .621371192237334f * windSpeed;
        }

        String direction = "Unknown";
        if (degrees >= 337.5 || degrees < 22.5) {
            direction = "N";
        } else if (degrees >= 22.5 && degrees < 67.5) {
            direction = "NE";
        } else if (degrees >= 67.5 && degrees < 112.5) {
            direction = "E";
        } else if (degrees >= 112.5 && degrees < 157.5) {
            direction = "SE";
        } else if (degrees >= 157.5 && degrees < 202.5) {
            direction = "S";
        } else if (degrees >= 202.5 && degrees < 247.5) {
            direction = "SW";
        } else if (degrees >= 247.5 && degrees < 292.5) {
            direction = "W";
        } else if (degrees >= 292.5 || degrees < 22.5) {
            direction = "NW";
        }

        return String.format(context.getString(windFormat), windSpeed, direction);
    }
}
