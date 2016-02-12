package com.joaoretamero.sunshine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment {

    private static final String TAG = MainActivityFragment.class.getSimpleName();
    private WeatherApi weatherApi;

    public MainActivityFragment() {
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

        weatherApi = new WeatherApi();
        try {
            weatherApi.getJson();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return rootView;
    }
}
