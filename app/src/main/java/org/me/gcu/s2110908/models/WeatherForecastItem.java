package org.me.gcu.s2110908.models;

public class WeatherForecastItem {
    private String day;
    private String weatherCondition;
    private String lowTemp;
    private String highTemp;
    private int weatherIcon;

    public WeatherForecastItem(String day, String weatherCondition, String lowTemp, String highTemp, int weatherIcon) {
        this.day = day;
        this.weatherCondition = weatherCondition;
        this.lowTemp = lowTemp;
        this.highTemp = highTemp;
        this.weatherIcon = weatherIcon;
    }

    public WeatherForecastItem() {

    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(String highTemp) {
        this.highTemp = highTemp;
    }

    public int getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(int weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
}
