package org.me.gcu.s2110908.models;

public class WelcomeItemModel {
    private String location;
    private String time;
    private String weather;
    private String temperature;
    private String highLow;
    public WelcomeItemModel(String location, String weather, String temperature, String highLow) {
        this.location = location;
        this.time = time;
        this.weather = weather;
        this.temperature = temperature;
        this.highLow = highLow;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHighLow() {
        return highLow;
    }

    public void setHighLow(String highLow) {
        this.highLow = highLow;
    }

}
