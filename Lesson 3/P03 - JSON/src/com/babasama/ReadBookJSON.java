package com.babasama;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class ReadBookJSON {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("book.json"));
            JSONObject jsonObject = (JSONObject) obj;

            String title = (String) jsonObject.get("title");
            System.out.println("title: " + title);

            String author = (String) jsonObject.get("author");
            System.out.println("author: " + author);

            Long year = (Long) jsonObject.get("year");
            System.out.println("year: " + year);

            Double price = (Double) jsonObject.get("price");
            System.out.println("price: " + price);
        } catch (Exception e) {e.printStackTrace();}
    }
}
