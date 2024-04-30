package org.me.gcu.s2110908;

public class Helper {

    public static int getWeatherIcon(String weatherCondition) {
        switch (weatherCondition) {
            case "Sunny":
            case "Clear Sky":
                case "Sunny Intervals":
                return R.drawable.day_clear;
            case "Partly Cloudy":
                case "Light Cloud":
                return R.drawable.day_partial_cloud;
            case "Cloudy":
                return R.drawable.cloudy;
            case "Light Rain":
                case "Light Rain Showers":
                return R.drawable.day_rain;
            case "Heavy Rain":
                return R.drawable.rain_thunder;
            case "Showers":
                return R.drawable.rain;
            case "Thunder":
                return R.drawable.rain_thunder;
            case "Snow":
                return R.drawable.day_snow;
            case "Mist":
                return R.drawable.mist;
            case "Fog":
                return R.drawable.fog;
            case "Drizzle":
                return R.drawable.rain;
            default:
                return R.drawable.day_clear;
        }
    }
}
