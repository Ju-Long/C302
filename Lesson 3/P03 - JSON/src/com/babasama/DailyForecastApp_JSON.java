package com.babasama;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DailyForecastApp_JSON {

	public static void main(String[] args) {
		String[] menu = { "Create Weather Forecast JSON file", "Display Weather Forecast" };

		int choice = Helper.getUserOption("Weather Forecast", menu);
		while (choice != 0) {
			switch (choice) {
			case 1:
				createWeatherForecast_JSON();
				break;

			case 2:
				readWeatherForecast_JSON();
				break;

			default:
				System.out.println("Invalid choice. Please try again!");
			}

			System.out.println();
			choice = Helper.getUserOption("Weather Forecast", menu);

		}
	}

	private static void createWeatherForecast_JSON() {

		// TODO: Case 1a - Create JSON file
		JSONObject obj = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("date", "2018-04-01");
		data.put("description", "Sunny");
		JSONArray jsonArray = new JSONArray();

		JSONObject temp = new JSONObject();
		JSONObject maxTemp = new JSONObject();
		maxTemp.put("unit", "C");
		maxTemp.put("value", 32.0);
		temp.put("maxTemp", maxTemp);

		JSONObject minTemp = new JSONObject();
		minTemp.put("unit", "C");
		minTemp.put("value", 27.0);
		temp.put("minTemp", minTemp);

		jsonArray.add(temp);
		data.put("temperatures",jsonArray);

		JSONObject windSpeed = new JSONObject();
		windSpeed.put("unit", "kph");
		windSpeed.put("value", 3);
		data.put("windSpeed", windSpeed);

		obj.put("dailyForecast", data);
		saveJSONFile(obj, "daily_forecast_results.json");
		
	}
	
	private static void saveJSONFile(JSONObject obj, String filename) {
		
		try { 		
			// TODO: Case 1b - Save JSON object to file
			FileWriter file = new FileWriter(filename);
			file.write(obj.toJSONString());
			file.flush();
			file.close();
			
			System.out.println(filename + " created successfully...");

		} catch (Exception e) {		
			e.printStackTrace();
		}
	}

	private static void readWeatherForecast_JSON() {

		// TODO: Case 2 - Read JSON file

		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("daily_forecast_results.json"));
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject dailyForecast = (JSONObject) jsonObject.get("dailyForecast");

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
		} catch (Exception e) { e.printStackTrace(); }
	}
}