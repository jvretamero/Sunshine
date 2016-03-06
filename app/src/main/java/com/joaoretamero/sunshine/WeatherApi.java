package com.joaoretamero.sunshine;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApi {
    public static final String UNIDADE_CELCIUS = "metric";
    public static final String UNIDADE_FAHRENHEIT = "imperial";
    public static final String TIPO_JSON = "json";
    public static final String TIPO_XML = "xml";
    public static final String ID_PIRACICABA = "3453643";
    private static final String OPEN_WEATHER_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    private String idCidade;
    private String tipo;
    private String dias;
    private String unidade;

    public WeatherApi() {
        tipo = TIPO_JSON;
        dias = "7";
        unidade = UNIDADE_CELCIUS;
        idCidade = ID_PIRACICABA;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(String idCidade) {
        this.idCidade = idCidade;
    }

    public String getJson() throws IOException {
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;

        try {
            connection = getUrlConnection();

            InputStream inputStream = connection.getInputStream();
            if (inputStream == null)
                return null;

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }

            if (stringBuffer.length() == 0)
                return null;

            return stringBuffer.toString();
        } finally {
            if (connection != null)
                connection.disconnect();

            if (bufferedReader != null)
                bufferedReader.close();
        }
    }

    private HttpURLConnection getUrlConnection() throws IOException {
        URL url = new URL(getUrl());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        return connection;
    }

    private String getUrl() {
        Uri.Builder builder = Uri.parse(OPEN_WEATHER_URL).buildUpon();

        builder.appendQueryParameter("id", String.valueOf(idCidade));
        builder.appendQueryParameter("mode", tipo);
        builder.appendQueryParameter("units", unidade);
        builder.appendQueryParameter("cnt", String.valueOf(dias));
        builder.appendQueryParameter("appid", BuildConfig.OPEN_WEATHER_API_KEY);

        return builder.toString();
    }
}
