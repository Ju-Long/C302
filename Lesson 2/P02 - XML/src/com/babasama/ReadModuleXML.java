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
import java.util.ArrayList;

public class ReadModuleXML {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File xmlFile = new File("module_c302.xml");
            Document document = builder.parse(xmlFile);

            // Get document root element "module"
            Element rootElement = document.getDocumentElement();
            System.out.println("Root Element: " + rootElement.getTagName());

            // Get attribute for "module" element
            String moduleCode = rootElement.getAttribute("code");
            System.out.println("Module Code: " + moduleCode);

            // Get "title" element
            NodeList titleNodeList = document.getElementsByTagName("title");
            Node titleNode = titleNodeList.item(0);
            System.out.println("Title: " + titleNode.getTextContent());

            // Get "year" element
            NodeList yearNodeList = document.getElementsByTagName("year");
            Node yearNode = yearNodeList.item(0);
            System.out.println("Year: " + yearNode.getTextContent());

            Element titleElement = (Element) titleNode;
            String language = titleElement.getAttribute("lang");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
