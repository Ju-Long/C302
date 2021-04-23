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

public class StudentXMLBuilder {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.newDocument();

            ArrayList<String> JohnContact = new ArrayList<>();
            ArrayList<String> MaryContact = new ArrayList<>();
            ArrayList<Student> students = new ArrayList<>();
            JohnContact.add("98711240");
            JohnContact.add("61284612");
            students.add(new Student("John", JohnContact));
            MaryContact.add("81124621");
            students.add(new Student("Mary", MaryContact));

            Element rootElement = document.createElement("student");
            document.appendChild(rootElement);
//            for (Student i: students) {
//                Element studentsElement = document.createElement("students");
//                Element nameElement = document.createElement("name");
//                String studentName = i.getName();
//                nameElement.setTextContent(studentName);
//                rootElement.appendChild(studentsElement);
//                studentsElement.appendChild(nameElement);
//
//                Element contactsElement = document.createElement("contacts");
//                for (String n : i.getContacts()) {
//                    Element contactElement = document.createElement("contact");
//                    contactElement.setTextContent(n);
//                    contactsElement.appendChild(contactElement);
//                }
//                studentsElement.appendChild(contactsElement);
//            }

            for (int i = 0; i < students.size(); i++) {
                Element studentsElement = document.createElement("students");
                Element nameElement = document.createElement("name");
                String studentName = students.get(i).getName();
                nameElement.setTextContent(studentName);
                rootElement.appendChild(studentsElement);
                studentsElement.appendChild(nameElement);

                Element contactsElement = document.createElement("contacts");
                for (int n = 0; n < students.get(i).getContacts().size(); n++) {
                    Element contactElement = document.createElement("contact");
                    contactElement.setTextContent(students.get(i).getContacts().get(n));
                    contactsElement.appendChild(contactElement);
                }
                studentsElement.appendChild(contactsElement);
            }

            saveXML(document, "student.xml");

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
