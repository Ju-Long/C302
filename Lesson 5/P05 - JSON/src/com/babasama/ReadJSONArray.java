package com.babasama;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ReadJSONArray {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:3000/modules");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new BufferedReader(
                    new InputStreamReader(conn.getInputStream())));

            JSONArray moduleArray = (JSONArray) obj;

            for (int i = 0; i < moduleArray.size(); i++) {

                JSONObject moduleObj = (JSONObject) moduleArray.get(i);

                String moduleCode = (String) moduleObj.get("module_code");
                String moduleName = (String) moduleObj.get("module_name");

                System.out.println((i + 1) + ": " + moduleName + " has a module code of  " + moduleCode);

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
