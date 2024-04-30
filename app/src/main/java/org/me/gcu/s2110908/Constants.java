package org.me.gcu.s2110908;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {

    private static final Map<String, String> locationToUrl = new HashMap<>();
    private static final String[] LOCATIONS = {"Mauritius", "Glasgow", "London", "New York", "Oman", "Bangladesh"};
    private static final List<String> URLS = new ArrayList<>();

    static {
        // Initialize locationToUrl map
        locationToUrl.put("Mauritius", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154");
        locationToUrl.put("Glasgow", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579");
        locationToUrl.put("London", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743");
        locationToUrl.put("New York", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581");
        locationToUrl.put("Oman", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286");
        locationToUrl.put("Bangladesh", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241");

        // Initialize URLs list
        for (String location : LOCATIONS) {
            URLS.add(locationToUrl.get(location));
        }
    }

    private static final Map<String, String> locationToObservationUrl = new HashMap<>();

    static {
        // Initialize locationToObservationUrl map
        locationToObservationUrl.put("Mauritius", "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/934154");
        locationToObservationUrl.put("Glasgow", "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/2648579");
        locationToObservationUrl.put("London", "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/2643743");
        locationToObservationUrl.put("New York", "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/5128581");
        locationToObservationUrl.put("Oman", "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/287286");
        locationToObservationUrl.put("Bangladesh", "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/1185241");
    }

    // define hashmap for lat and long
    private static final Map<String, String> locationToLatLong = new HashMap<>();

    static {
        locationToLatLong.put("Mauritius", "-20.348404,57.552152");
        locationToLatLong.put("Glasgow", "55.8642,-4.2518");
        locationToLatLong.put("London", "51.5074,-0.1278");
        locationToLatLong.put("New York", "40.7128,-74.0060");
        locationToLatLong.put("Oman", "21.4735,55.9754");
        locationToLatLong.put("Bangladesh", "23.6850,90.3563");
    }



    // Constants getters
    public static Map<String, String> getLocationToUrl() {
        return locationToUrl;
    }

    public static String[] getLocations() {
        return LOCATIONS;
    }

    public static List<String> getUrls() {
        return URLS;
    }

    public static Map<String, String> getObservationUrls() {
        return locationToObservationUrl;
    }

    public static Map<String, String> getLocationToLatLong() {
        return locationToLatLong;
    }
}
