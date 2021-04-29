package com.babasama;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ReadModulesXML {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File xmlFile = new File("modules.xml");
            Document document = builder.parse(xmlFile);

            // Get document root element "modules"
            Element rootElement = document.getDocumentElement();
            System.out.println("Root Element: " + rootElement.getTagName() + "\n");

            // Task 1: Get all "module" elements from rootElement
            NodeList moduleNodeList = rootElement.getElementsByTagName("module");

            for (int i=0; i<moduleNodeList.getLength(); i++) {
                if (moduleNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    // Task 2: Get module node from the node list
                    Node moduleNode = moduleNodeList.item(i);
                    System.out.println("Current Element: " + moduleNode.getNodeName());

                    // Task 3: Get "code" attribute from module element
                    Element moduleElement = (Element) moduleNodeList.item(i);
                    System.out.println("Module Code: " + moduleElement.getAttribute("code"));

                    // Get "title" element
                    NodeList titleNodeList = moduleElement.getElementsByTagName("title");
                    Node titleNode = titleNodeList.item(0);
                    System.out.println("Title: " + titleNode.getTextContent());

                    // Task 4: Get "year" element
                    NodeList yearNodeList = moduleElement.getElementsByTagName("year");
                    Node yearNode = yearNodeList.item(0);
                    System.out.println("Year: " + yearNode.getTextContent());

                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
