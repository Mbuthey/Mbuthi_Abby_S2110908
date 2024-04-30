package org.me.gcu.s2110908.services;

import android.util.Log;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WeatherFetchService extends AsyncTask<String, Void, String> {

    private WeatherFetchListener listener;

    public WeatherFetchService(WeatherFetchListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... urls) {
        String url = urls[0];
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            URL aurl = new URL(url);
            URLConnection yc = aurl.openConnection();
            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
            }
        } catch (IOException e) {
            Log.e("WeatherFetchService", "Error fetching weather data", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                Log.e("WeatherFetchService", "Error closing BufferedReader", e);
            }
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (listener != null) {
            listener.onWeatherFetchComplete(result);
        }
    }

    public interface WeatherFetchListener {
        void onWeatherFetchComplete(String weatherData);
    }
}