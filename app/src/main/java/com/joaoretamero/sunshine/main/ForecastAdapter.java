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

    private final int VIEW_TYPE_TODAY = 0;
    private final int VIEW_TYPE_FUTURE_DAY = 1;

    private boolean mUseTodayLayout = true;

    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public void setUseTodayLayout(boolean pUseTodayLayout) {
        mUseTodayLayout = pUseTodayLayout;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && mUseTodayLayout) ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int viewType = getItemViewType(cursor.getPosition());
        View inflatedView;
        if (viewType == VIEW_TYPE_TODAY) {
            inflatedView = LayoutInflater.from(context).inflate(R.layout.list_item_forecast_today, parent, false);
        } else {
            inflatedView = LayoutInflater.from(context).inflate(R.layout.list_item_forecast, parent, false);
        }
        inflatedView.setTag(new ViewHolder(inflatedView));
        return inflatedView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        int weatherId = cursor.getInt(WeatherContract.Forecast.COL_WEATHER_CONDITION_ID);
        int viewType = getItemViewType(cursor.getPosition());
        if (viewType == VIEW_TYPE_TODAY) {
            viewHolder.icon.setImageResource(Utility.getArtResourceForWeatherCondition(weatherId));
        } else if (viewType == VIEW_TYPE_FUTURE_DAY) {
            viewHolder.icon.setImageResource(Utility.getIconResourceForWeatherCondition(weatherId));
        }

        long dateTimeInMillis = cursor.getLong(WeatherContract.Forecast.COL_WEATHER_DATE);
        viewHolder.textDate.setText(Utility.getFriendlyDayString(context, dateTimeInMillis));

        String description = cursor.getString(WeatherContract.Forecast.COL_WEATHER_DESC);
        viewHolder.textForecast.setText(description);
        viewHolder.icon.setContentDescription(description);

        boolean isMetric = Utility.isMetric(context);

        double high = cursor.getDouble(WeatherContract.Forecast.COL_WEATHER_MAX_TEMP);
        viewHolder.textHigh.setText(Utility.formatTemperature(context, high, isMetric));

        double low = cursor.getDouble(WeatherContract.Forecast.COL_WEATHER_MIN_TEMP);
        viewHolder.textLow.setText(Utility.formatTemperature(context, low, isMetric));
    }

    private class ViewHolder {
        public final ImageView icon;
        public final TextView textDate;
        public final TextView textForecast;
        public final TextView textHigh;
        public final TextView textLow;

        public ViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.list_item_icon);
            textDate = (TextView) view.findViewById(R.id.list_item_date_textview);
            textForecast = (TextView) view.findViewById(R.id.list_item_forecast_textview);
            textHigh = (TextView) view.findViewById(R.id.list_item_high_textview);
            textLow = (TextView) view.findViewById(R.id.list_item_low_textview);
        }
    }
}
