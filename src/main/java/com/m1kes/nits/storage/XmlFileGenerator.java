package com.m1kes.nits.storage;


import com.m1kes.nits.objects.Extension;
import com.m1kes.nits.utils.CoreUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XmlFileGenerator
{
    private static SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH-mm");

    public static List<String> generateXml(String output_dir, List<Extension> agents)
    {
        List<String> generatedFileNames = new ArrayList<>();
        String filePath = output_dir + File.separator + "Exported "+ df.format(new Date()) + ".xml";

        CoreUtils.log("Output DIR : " + output_dir);
        CoreUtils.log("Exported File : " + filePath);

        try
        {
            Document doc = generateDoc(agents);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //Set format for output XML
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);

            //Create the Output folder if not existing
            File directory = new File(output_dir);
            if (!directory.exists()) {
                directory.mkdir();
            }

            //Create the actual xml output file
            File file = new File(filePath);
            generatedFileNames.add(filePath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //Save the output to the file.
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
            System.out.println("File saved!");
        }
        catch (ParserConfigurationException | TransformerException ex)
        {
            ex.printStackTrace();
        }
        return generatedFileNames;
    }


    private static Document generateDoc(List<Extension> agents) throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("YealinkIPPhoneDirectory");
        doc.appendChild(rootElement);
        for (Extension a : agents)
        {
            Element staff = doc.createElement("DirectoryEntry");
            rootElement.appendChild(staff);
            Element firstname = doc.createElement("Name");

            firstname.appendChild(doc.createTextNode(a.getName().replace("&", "and")));

            staff.appendChild(firstname);

            for(String number : a.getNumbers()){
                Element lastname = doc.createElement("Telephone");
                lastname.appendChild(doc.createTextNode(number.trim()));
                staff.appendChild(lastname);
            }

            rootElement.appendChild(staff);
        }
        return doc;
    }

}