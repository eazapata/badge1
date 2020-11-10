package com.fbmoll.eazapata.dataaccess.file;

import Data.CsvContent;
import com.fbmoll.eazapata.dataaccess.fileconverter.CSVFile;
import com.fbmoll.eazapata.dataaccess.fileconverter.XMLFile;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.apache.commons.lang3.StringUtils;


import java.util.ArrayList;


public class CSVtoXMLTests {

    private final String path = ".fbmoll";


    @Test
    void validateCSVContent() {

        String fileName = String.format("%s\\%s\\example.csv", System.getProperty("user.home"), path);

        CSVFile csvFile = new CSVFile();
        ArrayList<String> csvContent = csvFile.readCSV(fileName);
        Assert.notEmpty(csvContent, "list vacio");
    }
    @Test
    void validateXML(){
        String fileName = String.format("%s\\%s\\example3.csv", System.getProperty("user.home"), path);
        CSVFile csvFile = new CSVFile();
        ArrayList<String[]> csvContent = csvFile.readCSV(fileName);
        String fileNameXml = String.format("%s\\%s\\example2.xml", System.getProperty("user.home"), path);
        Document doc = XMLFile.createXMLFile(csvContent,"Users");
        XMLFile.saveXMLContent(doc,fileNameXml);

    }
    @Test
    void validateAttributes(){
        String fileName = String.format("%s\\%s\\example3.csv", System.getProperty("user.home"), path);
        CSVFile csvFile = new CSVFile();
        CsvContent attributes = new CsvContent();
        ArrayList<String[]> csvContent = csvFile.readCSV(fileName);
        boolean sameAttribute = StringUtils.equals(csvContent.get(0)[0], attributes.getProperty(0));
        Assert.isTrue(sameAttribute,"El primer campo no coincide.");
    }
}
