package com.joaoretamero.sunshine.main;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joaoretamero.sunshine.R;
import com.joaoretamero.sunshine.data.WeatherContract;
import com.joaoretamero.sunshine.util.Utility;

public class ForecastAdapter extends CursorAdapter {
    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    private String formatHighLows(double high, double low) {
        boolean isMetric = Utility.isMetric(mContext);
        String highLowStr = Utility.formatTemperature(high, isMetric) + "/" + Utility.formatTemperature(low, isMetric);
        return highLowStr;
    }

    private String convertCursorRowToUXFormat(Cursor cursor) {
        String highAndLow = formatHighLows(cursor.getDouble(WeatherContract.Forecast.COL_WEATHER_MAX_TEMP), cursor.getDouble(WeatherContract.Forecast.COL_WEATHER_MIN_TEMP));

        return Utility.formatDate(cursor.getLong(WeatherContract.Forecast.COL_WEATHER_DATE)) +
                " - " + cursor.getString(WeatherContract.Forecast.COL_WEATHER_DESC) +
                " - " + highAndLow;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_forecast, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView icon = (ImageView) view.findViewById(R.id.list_item_icon);
        icon.setImageResource(R.mipmap.ic_launcher);

        long dateTimeInMillis = cursor.getLong(WeatherContract.Forecast.COL_WEATHER_DATE);
        TextView textDate = (TextView) view.findViewById(R.id.list_item_date_textview);
        textDate.setText(Utility.getFriendlyDayString(context, dateTimeInMillis));

        TextView textForecast = (TextView) view.findViewById(R.id.list_item_forecast_textview);
        textForecast.setText(cursor.getString(WeatherContract.Forecast.COL_WEATHER_DESC));

        boolean isMetric = Utility.isMetric(context);

        double high = cursor.getDouble(WeatherContract.Forecast.COL_WEATHER_MAX_TEMP);
        TextView textHigh = (TextView) view.findViewById(R.id.list_item_high_textview);
        textHigh.setText(Utility.formatTemperature(high, isMetric));

        double low = cursor.getDouble(WeatherContract.Forecast.COL_WEATHER_MIN_TEMP);
        TextView textLow = (TextView) view.findViewById(R.id.list_item_low_textview);
        textLow.setText(Utility.formatTemperature(low, isMetric));
    }
}
