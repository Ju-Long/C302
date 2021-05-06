package com.babasama;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class CreateModuleJSON {

    public static void main(String[] args) {
	    JSONObject obj = new JSONObject();
	    obj.put("module_code", "C302");
	    obj.put("title", "web Services");
	    obj.put("year", 3);

	    System.out.println(obj.toJSONString());

	    saveJSONFile(obj, "module.json");
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
