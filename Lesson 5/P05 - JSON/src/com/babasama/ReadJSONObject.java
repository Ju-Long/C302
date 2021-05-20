package com.babasama;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ReadJSONObject {

    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:3000/person");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getInputStream())));

            JSONObject jsonObject = (JSONObject) obj;
            JSONObject personObj = (JSONObject) jsonObject.get("person");

            String name = (String) personObj.get("name");
            Long age = (Long) personObj.get("age");

            System.out.println(name + " is " + age + " years old.");
        } catch (Exception e) {}
    }
}
