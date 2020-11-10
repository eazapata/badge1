package com.fbmoll.eazapata.dataaccess.fileconverter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import java.util.List;

public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        try {
            final String path = ".fbmoll";
            final String fileNameXml = String.format("%s\\%s\\example4.xml", System.getProperty("user.home"), path);

            String csv = (args[0]);
            CSVFile csvFile = new CSVFile();
            List<String[]> csvContent = csvFile.readCSV(csv);
            Document doc = XMLFile.createXMLFile(csvContent, "Users");
            XMLFile.saveXMLContent(doc, fileNameXml);

            /*HashMap<String, String> atributes = new HashMap<String, String>();
            Row row = new Row();
            atributes = row.getAtributes();
            System.out.println();*/

        } catch (Exception e) {
            log.error("Arguments not found.");
        }

    }
}
