package com.babasama;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ForecastReportingApp_pojo {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File xmlFile = new File("daily_forecast.xml");
            Document document = builder.parse(xmlFile);

            ForecastReport forecastReport = new ForecastReport();

            Element rootElement = document.getDocumentElement();

            Element dateElement = (Element) rootElement.getElementsByTagName("date").item(0);
            forecastReport.setDate(dateElement.getTextContent());

            Element descElement = (Element) rootElement.getElementsByTagName("description").item(0);
            forecastReport.setDesc(descElement.getTextContent());

            Element windElement = (Element) rootElement.getElementsByTagName("windSpeed").item(0);
            forecastReport.setWindSpeed(Double.parseDouble(windElement.getTextContent()));
            forecastReport.setWindUnit(windElement.getAttribute("unit"));

            Element tempElement = (Element) rootElement.getElementsByTagName("temperatures").item(0);
            Element maxTempElement = (Element) tempElement.getElementsByTagName("maxTemp").item(0);
            forecastReport.setMaxTemp(Double.parseDouble(maxTempElement.getTextContent()));
            forecastReport.setMaxTempUnit(maxTempElement.getAttribute("unit"));
            Element minTempElement = (Element) tempElement.getElementsByTagName("minTemp").item(0);
            forecastReport.setMinTemp(Double.parseDouble(minTempElement.getTextContent()));
            forecastReport.setMinTempUnit(minTempElement.getAttribute("unit"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
