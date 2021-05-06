package com.babasama;

public class ThreeDayForecast {
    private String date;
    private String description;
    private String maxTempUnit;
    private double maxTempValue;
    private String minTempUnit;
    private double minTempValue;
    private String windSpeedUnit;
    private int windSpeedValue;

    public ThreeDayForecast(String date, String description, String maxTempUnit, double maxTempValue, String minTempUnit, double minTempValue, String windSpeedUnit, int windSpeedValue) {
        this.date = date;
        this.description = description;
        this.maxTempUnit = maxTempUnit;
        this.maxTempValue = maxTempValue;
        this.minTempUnit = minTempUnit;
        this.minTempValue = minTempValue;
        this.windSpeedUnit = windSpeedUnit;
        this.windSpeedValue = windSpeedValue;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getMaxTempUnit() {
        return maxTempUnit;
    }

    public double getMaxTempValue() {
        return maxTempValue;
    }

    public String getMinTempUnit() {
        return minTempUnit;
    }

    public double getMinTempValue() {
        return minTempValue;
    }

    public String getWindSpeedUnit() {
        return windSpeedUnit;
    }

    public int getWindSpeedValue() {
        return windSpeedValue;
    }
}
