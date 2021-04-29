package com.babasama;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ForecastReportingApp_Enhancement {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File xmlFile = new File("three_days_forecast.xml");
            Document document = builder.parse(xmlFile);

            Element rootElement = document.getDocumentElement();

            NodeList forecastNodeList = rootElement.getElementsByTagName("dailyForecast");
            for (int i = 0; i < forecastNodeList.getLength(); i++) {
                Element forecastElement = (Element) forecastNodeList.item(i);

                Element dateElement = (Element) forecastElement.getElementsByTagName("date").item(0);
                System.out.println("date: " + dateElement.getTextContent());

                Element descElement = (Element) forecastElement.getElementsByTagName("description").item(0);
                System.out.println("description: " + descElement.getTextContent());

                Element windSpeedElement = (Element) forecastElement.getElementsByTagName("windSpeed").item(0);
                System.out.println("windSpeed: " + windSpeedElement.getTextContent());

                Element tempElement = (Element) forecastElement.getElementsByTagName("temperatures") .item(0);

                Node maxTempElement = tempElement.getChildNodes().item(1);
                System.out.println("maxTemp: " + maxTempElement.getTextContent());

                Node minTempElement = tempElement.getChildNodes().item(3);
                System.out.println("minTemp: " + minTempElement.getTextContent());

                System.out.println("===========================================================");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
