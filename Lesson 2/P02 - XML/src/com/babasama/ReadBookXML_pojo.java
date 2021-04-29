package com.babasama;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ReadBookXML_pojo {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File xmlFile = new File("book.xml");
            Document document = builder.parse(xmlFile);

            Book book = new Book();

            Element rootElement = document.getDocumentElement();
            System.out.println("Root Element: " + rootElement.getTagName());

            NodeList titleElement = rootElement.getElementsByTagName("title");
            Element langElement = (Element) titleElement.item(0);
            book.setTitle(titleElement.item(0).getTextContent());
            book.setLang(langElement.getAttribute("lang"));

            NodeList authorElement = rootElement.getElementsByTagName("author");
            book.setAuthor(authorElement.item(0).getTextContent());

            NodeList yearElement = rootElement.getElementsByTagName("year");
            book.setYear(yearElement.item(0).getTextContent());

            NodeList priceElement = rootElement.getElementsByTagName("price");
            Element unitElement = (Element) priceElement.item(0);
            book.setUnit(unitElement.getAttribute("unit"));
            book.setPrice(priceElement.item(0).getTextContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
