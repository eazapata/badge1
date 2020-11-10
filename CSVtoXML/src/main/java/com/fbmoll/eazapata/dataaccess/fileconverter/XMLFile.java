package com.fbmoll.eazapata.dataaccess.fileconverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class XMLFile {

   private static Logger log = LoggerFactory.getLogger(XMLFile.class);

    public static Document createXMLFile(List<String[]> csvContent, String root) {

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement(root);
            doc.appendChild(rootElement);

            for (int i = 0; i < csvContent.size(); i++) {
                Element user = doc.createElement("Row");
                for (int j = 0; j < csvContent.get(i).length; j++) {

                    // Tenemos este if porque en nuestra estructura csv la primera fila son los campos
                    if (i == 0) i++;

                    Element element = doc.createElement(csvContent.get(0)[j]);
                    user.appendChild(element);
                    Node textNode = doc.createTextNode(csvContent.get(i)[j]);
                    element.appendChild(textNode);
                }
                rootElement.appendChild(user);
            }
            return doc;
        } catch (Exception e) {
            log.error("Creating XML file", e);
            return null;
        }
    }

    public static void saveXMLContent(Document doc, String path) {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);

        } catch (Exception e) {
            log.error("Creating XML file", e);
        }

    }


}
