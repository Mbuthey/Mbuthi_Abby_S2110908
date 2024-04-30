package org.me.gcu.s2110908.models;

public class Item {
    private String temperature;
    private String windSpeed;
    private String windDirection;
    private String weatherCondition;
    private String weatherIcon;
    private String date;
    private String humidity;
    private String pressure;
    private String highTemp;
    private String lowTemp;

    public Item(String temperature, String windSpeed, String windDirection, String weatherCondition, String weatherIcon, String date, String humidity, String pressure, String highTemp, String lowTemp) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.weatherCondition = weatherCondition;
        this.weatherIcon = weatherIcon;
        this.date = date;
        this.humidity = humidity;
        this.pressure = pressure;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
    }

    public Item() {

    }

    public String getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(String highTemp) {
        this.highTemp = highTemp;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }
}
