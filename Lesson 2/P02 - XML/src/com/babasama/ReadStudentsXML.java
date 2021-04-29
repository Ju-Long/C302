package com.babasama;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ReadStudentsXML {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File xmlFile = new File("students.xml");
            Document document = builder.parse(xmlFile);

            // Get document root element "modules"
            Element rootElement = document.getDocumentElement();
            // Task 1: Get all "module" elements from rootElement
            NodeList studentNodeList = rootElement.getElementsByTagName("student");

            for (int i = 0; i < studentNodeList.getLength(); i++) {
                if (studentNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element studentElement = (Element) studentNodeList.item(i);
                    System.out.println("Student Name: " + studentElement.getAttribute("name"));

                    NodeList contactsNodeList = studentElement.getElementsByTagName("contacts");
                    System.out.println("# of contacts: " + contactsNodeList.getLength());
                    for (int n = 0; n < contactsNodeList.getLength(); n++) {
                        if (contactsNodeList.item(n).getNodeType() == Node.ELEMENT_NODE) {
                            Element contactElement = (Element) contactsNodeList.item(n);
                            System.out.println("Contact: " + contactElement.getTextContent());
                        }
                    }

                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
