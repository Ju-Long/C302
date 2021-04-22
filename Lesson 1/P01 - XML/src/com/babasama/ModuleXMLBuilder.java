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
import java.util.ArrayList;

public class ModuleXMLBuilder {

    public static void main(String[] args) {
	    try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.newDocument();

            Element rootElement = document.createElement("module");
            rootElement.setAttribute("code", "C302");
            document.appendChild(rootElement);

            Element titleElement = document.createElement("title");
            titleElement.setTextContent("Web Service");
            rootElement.appendChild(titleElement);

            Element yearElement = document.createElement("year");
            yearElement.setTextContent("3");
            rootElement.appendChild(yearElement);

            saveXML(document, "module.xml");

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
