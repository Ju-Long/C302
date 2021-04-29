package com.babasama;

public class ForecastReport {

    private String date;
    private String desc;
    private double windSpeed;
    private String windUnit;
    private double maxTemp;
    private String maxTempUnit;
    private double minTemp;
    private String minTempUnit;

    public ForecastReport() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindUnit() {
        return windUnit;
    }

    public void setWindUnit(String windUnit) {
        this.windUnit = windUnit;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMaxTempUnit() {
        return maxTempUnit;
    }

    public void setMaxTempUnit(String maxTempUnit) {
        this.maxTempUnit = maxTempUnit;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public String getMinTempUnit() {
        return minTempUnit;
    }

    public void setMinTempUnit(String minTempUnit) {
        this.minTempUnit = minTempUnit;
    }
}
