package com.babasama;

public class DailyForecast {

    String date;
    String description;
    int maxTemp;
    int minTemp;
    int windSpeed;

    public DailyForecast(String date, String description, int maxTemp, int minTemp, int windSpeed) {
        this.date = date;
        this.description = description;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.windSpeed = windSpeed;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getMaxTemp() {
        return String.valueOf(maxTemp);
    }

    public String getMinTemp() {
        return String.valueOf(minTemp);
    }

    public String getWindSpeed() {
        return String.valueOf(windSpeed);
    }
}

