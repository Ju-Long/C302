package com.babasama;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class CreateModuleJSON_enhanced {
    public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        obj.put("code", "C302");
        obj.put("title", "web Services");
        obj.put("year", 3);

        JSONArray classes = new JSONArray();
        classes.add("1-W64A");
        classes.add("1-W64B");
        classes.add("1-W64C");

        obj.put("classes", classes);
        saveJSONFile(obj, "module_enhanced.json");
    }

    private static void saveJSONFile(JSONObject obj, String filename) {
        try {
            FileWriter file = new FileWriter(filename);
            file.write(obj.toJSONString());
            file.flush();
            file.close();

            System.out.println(filename + " created successfully...");
        } catch (IOException e) { e.printStackTrace(); }
    }
}
