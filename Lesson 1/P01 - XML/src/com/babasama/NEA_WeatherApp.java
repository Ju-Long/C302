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

public class NEA_WeatherApp {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.newDocument();

            Element rootElement = document.createElement("feed");
            document.appendChild(rootElement);

            Element titleElement = document.createElement("title");
            titleElement.setTextContent("1 Day Forecast");
            rootElement.appendChild(titleElement);

            Element linkElement = document.createElement("link");
            linkElement.setAttribute("href", "http://www.nea.gov.sg/weather_3day.aspx/");
            linkElement.setAttribute("rel", "self");
            rootElement.appendChild(linkElement);
            linkElement = document.createElement("link");
            linkElement.setAttribute("href", "http://www.nea.gov.sg/");
            rootElement.appendChild(linkElement);

            Element updatedElement = document.createElement("updated");
            updatedElement.setTextContent("2018-03-31T10:13:00Z");
            rootElement.appendChild(updatedElement);

            Element authorElement = document.createElement("author");
            Element nameElement = document.createElement("name");
            nameElement.setTextContent("Meteorological Service Singapore");
            authorElement.appendChild(nameElement);
            rootElement.appendChild(authorElement);

            Element idElement = document.createElement("id");
            idElement.setTextContent("urn:uuid:20180331-d399-11d9-b93C-051405003af6");
            rootElement.appendChild(idElement);

            Element contentElement = document.createElement("content");
            contentElement.setAttribute("type", "xhtml");
            Element divElement = document.createElement("div");
            divElement.setAttribute("xmlns", "http://www.w3.org/1999/xhtml");
            Element imgElement = document.createElement("img");
            imgElement.setAttribute("src", "http://www.weather.gov.sg/wp-content/themes/wiptheme/assets/img/icon-fair-warm.png");
            divElement.appendChild(imgElement);
            Element forecaseElement = document.createElement("forecast");
            forecaseElement.setTextContent("Fair and warm.");
            divElement.appendChild(forecaseElement);
            contentElement.appendChild(divElement);
            rootElement.appendChild(contentElement);
            saveXML(document, "NEA_WeatherApp.xml");

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
