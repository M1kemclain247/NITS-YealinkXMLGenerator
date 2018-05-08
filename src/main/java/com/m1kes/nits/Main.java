package com.m1kes.nits;

import com.m1kes.nits.objects.Extension;
import com.m1kes.nits.parsers.CsvParser;
import com.m1kes.nits.storage.XmlFileGenerator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

            try {

                File file = new File("settings.json");
                if(!file.exists()) file.createNewFile();

                Object obj = new JSONParser().parse(new FileReader( "settings.json"));
                JSONObject jsonObject = (JSONObject)obj;

                JSONObject settings = (JSONObject)jsonObject.get("export-options");

                String source =  getRunPath() +  (String)settings.get("inputFile");
                String destination =  getRunPath() + (String)settings.get("outputFolder");

                List<Extension> agents = CsvParser.readCSVNew(source);
                for (Extension a : agents) {
                    System.out.println(a);
                }
                XmlFileGenerator.generateXml(destination, agents);

            } catch (ParseException e) {
                e.printStackTrace();
           } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static String getRunPath(){
        try {
            return new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent() + File.separator;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "";
        }
    }

}
