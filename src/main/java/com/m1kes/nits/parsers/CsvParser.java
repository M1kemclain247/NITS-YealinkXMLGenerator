package com.m1kes.nits.parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.m1kes.nits.objects.Extension;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

public class CsvParser
{
    public static void ReadFile(String sourceFile)
    {
        File file = new File(sourceFile);

        List<List<String>> lines = new ArrayList<>();
        String[] values;
        try
        {
            Scanner inputStream = new Scanner(file);
            while (inputStream.hasNext())
            {
                String line = inputStream.next();
                values = line.split(",");

                lines.add(Arrays.asList(values));
            }
            inputStream.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        int lineNo = 1;
        for (List<String> line : lines)
        {
            int columnNo = 1;
            for (String value : line)
            {
                System.out.println("Line " + lineNo + " Column " + columnNo + ": " + value);
                columnNo++;
            }
            lineNo++;
        }
    }

    public static List<Extension> readCSVNew(String sourceFile)
    {
        List<Extension> extensions = new ArrayList<>();

        CellProcessor[] processors = { new Optional(), new Optional() };

        ICsvBeanReader beanReader = null;
        try
        {
            beanReader = new CsvBeanReader(new FileReader(sourceFile), CsvPreference.STANDARD_PREFERENCE);

            String[] header = new String[0];

            header = beanReader.getHeader(true);

            Extension agentBean = null;
            String name;
            while ((agentBean = (Extension)beanReader.read(Extension.class, header, processors)) != null)
            {
                name = agentBean.getName();
                if (name == null) {
                    name = "";
                }
                String number = agentBean.getNumber();
                if (number == null) {
                    number = "";
                }
                extensions.add(new Extension(name, number));
            }
            for (Extension a : extensions) {
                System.out.println(a);
            }
            return extensions;
        }
        catch (FileNotFoundException ex)
        {
            System.err.println("Could not find the CSV file: " + ex);
        }
        catch (IOException ex)
        {
            System.err.println("Error reading the CSV file: " + ex);
        }
        finally
        {
            if (beanReader != null) {
                try
                {
                    beanReader.close();
                }
                catch (IOException ex)
                {
                    System.err.println("Error closing the reader: " + ex);
                }
            }
        }
        return extensions;
    }
}