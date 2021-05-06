package com.babasama;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class ThreeDayForecastApp_JSON {
    public static void main(String[] args) {
        //create
        ArrayList<ThreeDayForecast> values = new ArrayList<>();
        values.add(new ThreeDayForecast("2018-04-01", "Sunny", "C", 32.0, "C", 27.0, "kph", 3));
        values.add(new ThreeDayForecast("2018-04-02", "Rainy", "C", 28.0, "C", 23.0, "kph", 7));
        values.add(new ThreeDayForecast("2018-04-03", "Cloudy", "C", 30.0, "C", 25.0, "kph", 5));

        JSONArray array = new JSONArray();
        for (ThreeDayForecast i : values) {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("date", i.getDate());
            data.put("description", i.getDescription());
            JSONArray jsonArray = new JSONArray();

            JSONObject temp = new JSONObject();
            JSONObject maxTemp = new JSONObject();
            maxTemp.put("unit", i.getMaxTempUnit());
            maxTemp.put("value", i.getMaxTempValue());
            temp.put("maxTemp", maxTemp);

            JSONObject minTemp = new JSONObject();
            minTemp.put("unit", i.getMinTempUnit());
            minTemp.put("value", i.getMinTempValue());
            temp.put("minTemp", minTemp);

            jsonArray.add(temp);
            data.put("temperatures",jsonArray);

            JSONObject windSpeed = new JSONObject();
            windSpeed.put("unit", i.getWindSpeedUnit());
            windSpeed.put("value", i.getWindSpeedValue());
            data.put("windSpeed", windSpeed);

            obj.put("dailyForecast", data);
            array.add(obj);
        }
        saveJSONFile(array, "ThreeDayForecast.json");

        //read

        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(new FileReader("ThreeDayForecast.json"));
            JSONArray jsonArray = (JSONArray) object;
            for (Object i: jsonArray) {
                JSONObject n = (JSONObject) i;
                JSONObject dailyForecast = (JSONObject) n.get("dailyForecast");

                String date = (String) dailyForecast.get("date");
                System.out.println("Date: " + date);

                String description = (String) dailyForecast.get("description");
                System.out.println("Description: " + description);

                JSONObject windSpeed = (JSONObject) dailyForecast.get("windSpeed");
                String windSpeedUnit = (String) windSpeed.get("unit");
                Long windSpeedValue = (Long) windSpeed.get("value");
                System.out.println("Wind Speed: " + windSpeedValue + windSpeedUnit);

                JSONArray temperature = (JSONArray) dailyForecast.get("temperatures");
                JSONObject maxTemp = (JSONObject) ((JSONObject) temperature.get(0)).get("maxTemp");
                String maxTempUnit = (String) maxTemp.get("unit");
                double maxTempValue = (Double) maxTemp.get("value");
                System.out.println("Maximum Temperature: " + maxTempValue + maxTempUnit);

                JSONObject minTemp = (JSONObject) ((JSONObject) temperature.get(0)).get("minTemp");
                String minTempUnit = (String) minTemp.get("unit");
                double minTempValue = (Double) minTemp.get("value");
                System.out.println("Minimum Temperature: " + minTempValue + maxTempUnit);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static void saveJSONFile(JSONArray obj, String filename) {

        try {
            FileWriter file = new FileWriter(filename);
            file.write(obj.toJSONString());
            file.flush();
            file.close();

            System.out.println(filename + " created successfully...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
