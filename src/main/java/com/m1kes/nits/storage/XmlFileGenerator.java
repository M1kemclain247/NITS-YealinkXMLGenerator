package com.m1kes.nits.storage;


import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.m1kes.nits.objects.Extension;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlFileGenerator
{
    private static SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH-mm");

    public static List<String> generateXml(String destinationFile, List<Extension> agents)
    {
        List<String> generatedFileNames = new ArrayList<>();

        String directoryName = destinationFile;

        String filePath = directoryName + File.separator + "Exported "+ df.format(new Date()) + ".xml";
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("YealinkIPPhoneDirectory");
            doc.appendChild(rootElement);
            for (Extension a : agents)
            {
                Element staff = doc.createElement("DirectoryEntry");
                rootElement.appendChild(staff);

                String[] numbers = a.getNumber().split(",");

                Element firstname = doc.createElement("Name");
                firstname.appendChild(doc.createTextNode(a.getName().replace("&", "and")));
                staff.appendChild(firstname);
                for (int i = 0; i < numbers.length; i++)
                {
                    Element lastname = doc.createElement("Telephone");
                    lastname.appendChild(doc.createTextNode(numbers[i]));
                    staff.appendChild(lastname);
                }
                rootElement.appendChild(staff);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);

            File directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File(filePath);

            generatedFileNames.add(filePath);
            if (!file.exists()) {
                try
                {
                    file.createNewFile();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
            System.out.println("File saved!");
        }
        catch (ParserConfigurationException pce)
        {
            pce.printStackTrace();
        }
        catch (TransformerException tfe)
        {
            tfe.printStackTrace();
        }
        return generatedFileNames;
    }
}