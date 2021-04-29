package com.babasama;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class ForecastReportingApp {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File xmlFile = new File("daily_forecast.xml");
            Document document = builder.parse(xmlFile);

            Element rootElement = document.getDocumentElement();
            System.out.println(rootElement.getTagName());
            System.out.println("======================");

            Element dateElement = (Element) rootElement.getElementsByTagName("date").item(0);
            System.out.println("Date: " + dateElement.getTextContent());

            Element descElement = (Element) rootElement.getElementsByTagName("description").item(0);
            System.out.println("Description: " + descElement.getTextContent());

            Element windElement = (Element) rootElement.getElementsByTagName("windSpeed").item(0);
            System.out.println("Wind Speed: " + windElement.getTextContent() + windElement.getAttribute("unit"));

            Element tempElement = (Element) rootElement.getElementsByTagName("temperatures").item(0);
            Element maxTempElement = (Element) tempElement.getElementsByTagName("maxTemp").item(0);
            System.out.println("Maximum Temperature: " + maxTempElement.getTextContent() + maxTempElement.getAttribute("unit"));
            Element minTempElement = (Element) tempElement.getElementsByTagName("minTemp").item(0);
            System.out.println("Minimum Temperature: " + minTempElement.getTextContent() + minTempElement.getAttribute("unit"));

            //task 2
            Document outDocument = builder.newDocument();

            Element outRootElement = outDocument.createElement("dailyForecastReport");
            outDocument.appendChild(outRootElement);

            Element outDateElement = outDocument.createElement("date");
            outDateElement.setTextContent(dateElement.getTextContent());
            outRootElement.appendChild(outDateElement);

            Element outSummaryElement = outDocument.createElement("summary");
            outSummaryElement.setTextContent("The weather is Sunny with temperature range of " + minTempElement.getTextContent() + " to " + maxTempElement.getTextContent() + "degree celsius. The wind speed is " + windElement.getTextContent() + windElement.getAttribute("unit"));
            outRootElement.appendChild(outSummaryElement);

            saveXML(outDocument, "daily_forecast_reporting.xml");

        } catch (Exception e) {
            e.printStackTrace();
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
