package com.babasama;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

public class ReadEmployeesJSON {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("employees.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray array = (JSONArray) jsonObject.get("employees");
            for (int i = 0; i < array.size(); i++) {
                String name = (String) array.get(i);
                System.out.println("name: " + name);
            }
        } catch (Exception e) {e.printStackTrace();}
    }
}
