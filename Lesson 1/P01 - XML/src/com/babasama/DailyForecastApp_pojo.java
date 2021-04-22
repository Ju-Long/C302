package com.babasama;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class DailyForecastApp_pojo {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.newDocument();

            ArrayList<DailyForecast> data = new ArrayList<>();
            data.add(new DailyForecast("2018-04-01", "Sunny", 32, 27, 3));

            Element rootElement = document.createElement("DailyForecastsData");
            document.appendChild(rootElement);

            for (DailyForecast i : data) {
                Element dailyForecastElement = document.createElement("dailyForecast");

                Element dateElement = document.createElement("date");
                dateElement.setTextContent(i.getDate());
                dailyForecastElement.appendChild(dateElement);

                Element descriptionElement = document.createElement("description");
                descriptionElement.setTextContent(i.getDescription());
                dailyForecastElement.appendChild(descriptionElement);

                Element temperaturesElement = document.createElement("temperatures");
                Element maxTempElement = document.createElement("maxTemp");
                maxTempElement.setAttribute("unit", "C");
                maxTempElement.setTextContent(i.getMaxTemp());
                temperaturesElement.appendChild(maxTempElement);
                Element minTempElement = document.createElement("minTemp");
                minTempElement.setAttribute("unit", "C");
                minTempElement.setTextContent(i.getMinTemp());
                temperaturesElement.appendChild(minTempElement);
                dailyForecastElement.appendChild(temperaturesElement);

                Element windSpeedElement = document.createElement("windSpeed");
                windSpeedElement.setAttribute("unit", "kph");
                windSpeedElement.setTextContent(i.getWindSpeed());
                dailyForecastElement.appendChild(windSpeedElement);

                rootElement.appendChild(dailyForecastElement);
            }
            saveXML(document, "dailyForecast_pojo.xml");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }

    private static boolean saveXML(Document document, String filename) {
        TransformerFactory factory = TransformerFactory.newInstance();
        boolean result = true;

        try {
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");

            Source src = new DOMSource(document);
            Result dest = new StreamResult(new File(filename));
            transformer.transform(src, dest);

            System.out.println(filename + " created successfully...");

        } catch (TransformerConfigurationException e) {
            result = false;
        } catch (TransformerException e) {
            result = false;
        }

        return result;
    }
}
