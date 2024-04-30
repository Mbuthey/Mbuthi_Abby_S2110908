package org.me.gcu.s2110908.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.me.gcu.s2110908.Constants;
import org.me.gcu.s2110908.Helper;
import org.me.gcu.s2110908.MapsActivity;
import org.me.gcu.s2110908.R;
import org.me.gcu.s2110908.adapter.WeatherForecastAdapter;
import org.me.gcu.s2110908.models.Item;
import org.me.gcu.s2110908.models.WeatherForecastItem;
import org.me.gcu.s2110908.services.WeatherFetchService;
import org.me.gcu.s2110908.services.PullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class WeatherForecastFragment extends Fragment implements OnMapReadyCallback {

    private static final String ARG_LOCATION = "location";

    public String location;

    private TextView locationTextView;
    private TextView temperatureTextView;
    private TextView weatherConditionTextView;
    private TextView highLowTempTextView;
    private TextView txtWind;
    private TextView txtPressure;
    private TextView txtHumidity;
    private TextView threeDayForecastTextView;
    private ListView weatherForecastListView;
    private ProgressBar progressBar;
    private ImageView weatherIcon;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private LinearLayout weatherForecastLayout;
    private ScrollView scrollView;
    private FrameLayout frameLayout;
    private FloatingActionButton fab;
    private WeatherForecastAdapter weatherForecastAdapter;
    ArrayList<WeatherForecastItem> items;

    public static WeatherForecastFragment newInstance(String location) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LOCATION, location);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            location = getArguments().getString(ARG_LOCATION);
        }

        ArrayList<WeatherForecastItem> weatherForecastItems = new ArrayList<>();
        weatherForecastItems.add(new WeatherForecastItem("Monday", "Sunny", "20°C", "25°C", R.drawable.day_clear));
        weatherForecastItems.add(new WeatherForecastItem("Tuesday", "Cloudy", "18°C", "23°C", R.drawable.cloudy));
        weatherForecastItems.add(new WeatherForecastItem("Wednesday", "Rainy", "15°C", "20°C", R.drawable.rain));
        weatherForecastAdapter = new WeatherForecastAdapter(weatherForecastItems, this.getContext());
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);

        locationTextView = view.findViewById(R.id.txtCity);
        temperatureTextView = view.findViewById(R.id.txtTemperature);
        weatherConditionTextView = view.findViewById(R.id.txtWeatherCondition);
        highLowTempTextView = view.findViewById(R.id.txtHighLowTemperature);
        weatherForecastListView = view.findViewById(R.id.lvForecast);
        progressBar = view.findViewById(R.id.progressBar);
        threeDayForecastTextView = view.findViewById(R.id.txtForecastTitle);
        fab = view.findViewById(R.id.fabBackArrow);
        weatherForecastLayout = view.findViewById(R.id.lnlWeatherInfo);
        txtWind = view.findViewById(R.id.txtWind);
        txtPressure = view.findViewById(R.id.txtPressure);
        txtHumidity = view.findViewById(R.id.txtHumidity);
        frameLayout = view.findViewById(R.id.flLoading);
        scrollView = view.findViewById(R.id.scrollView);
        weatherIcon = view.findViewById(R.id.imgWeatherCondition);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        weatherForecastListView.setAdapter(weatherForecastAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        // Populate the views with the weather data
        if (location == "Mauritius") {
            locationTextView.setText(location + " ->>");
        } else if (location == "Bangladesh") {
            locationTextView.setText("<<- " + location);
        } else {
            locationTextView.setText("<<- " +location + " ->>");
        }
        temperatureTextView.setText("20°C");
        weatherConditionTextView.setText("Sunny");
        highLowTempTextView.setText("High: 25°C, Low: 15°C");

        return view;
    }

    public void onWeatherDataFetched(String weatherData, Context context) {
        // Implement method to display the fetched weather data
        System.out.println("Weather data fetched: " + weatherData);

        // Parse the weather data and update the views
        items = (ArrayList<WeatherForecastItem>) PullParser.parseThreeDaysWeatherData(weatherData);

        if (items != null && items.size() > 0) {
            WeatherForecastItem today = items.get(0);

            weatherForecastAdapter.clear();
            weatherForecastAdapter.addAll(items);
            weatherForecastAdapter.notifyDataSetChanged();
        }

    }

    public void onObservationDataFetched(String observationData, Context context) {
        try {
            Item today = PullParser.parseItem(observationData);
            if (items != null && items.size() > 0) {
                WeatherForecastItem todayWeather = items.get(0);
                today.setWeatherCondition(todayWeather.getWeatherCondition());
                today.setLowTemp(todayWeather.getLowTemp());
                today.setHighTemp(todayWeather.getHighTemp());
                weatherForecastAdapter.notifyDataSetChanged();
            }
            if (today != null) {
                temperatureTextView.setText(today.getTemperature() + "°C");
                weatherConditionTextView.setText(today.getWeatherCondition());
                txtWind.setText("Wind: " + today.getWindSpeed() + " mph");
                txtPressure.setText("Pressure: " + today.getPressure() + " mb");
                txtHumidity.setText("Humidity: " + today.getHumidity() + "%");
                weatherIcon.setImageResource(Helper.getWeatherIcon(today.getWeatherCondition()));
                highLowTempTextView.setText("High: " + today.getHighTemp() + ", Low: " + today.getLowTemp());
            }

            frameLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            weatherForecastLayout.setVisibility(View.VISIBLE);
        } catch (XmlPullParserException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Implement method to display the map
        this.googleMap = googleMap;

        // Add a marker for the specified location
        String latlong = Constants.getLocationToLatLong().get(location);
        String campusLocation = location;
        String[] latlongArray = latlong.split(",");
        double latitude = Double.parseDouble(latlongArray[0]);
        double longitude = Double.parseDouble(latlongArray[1]);
        LatLng location = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(location)
                .title("GCU " + campusLocation + " Campus");
        googleMap.addMarker(markerOptions);

        // Move the camera to the marker location
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));

        // start new activity on map click
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("latLng", latlong);
                startActivity(intent);
            }
        });

    }

}
