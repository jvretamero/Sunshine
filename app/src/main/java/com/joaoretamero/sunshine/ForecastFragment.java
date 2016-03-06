package com.joaoretamero.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ForecastFragment extends Fragment {

    private static final String TAG = ForecastFragment.class.getSimpleName();

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        List<String> listaClimaSemana = new ArrayList<String>();
        listaClimaSemana.add("Hoje - Sol - 35/25");
        listaClimaSemana.add("Amanhã - Chuva - 20/10");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                listaClimaSemana);

        ListView listForecast = (ListView) rootView.findViewById(R.id.listview_forecast);
        listForecast.setAdapter(arrayAdapter);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                FetchWeatherTask weatherTask = new FetchWeatherTask();
                weatherTask.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class FetchWeatherTask extends AsyncTask<Void, Void, Void> {

        private final String TAG = FetchWeatherTask.class.getSimpleName();

        @Override
        protected Void doInBackground(Void... params) {
            WeatherApi weatherApi = new WeatherApi();
            try {
                String json = weatherApi.getJson();
                Log.d(TAG, json);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }

            return null;
        }
    }
}
