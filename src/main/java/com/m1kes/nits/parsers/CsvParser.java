package com.m1kes.nits.parsers;

import com.m1kes.nits.objects.Extension;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

    public static List<Extension> readCSVNew(String sourceFile) throws IOException {
        List<Extension> extensions = new ArrayList<>();

        try (
                Reader reader = Files.newBufferedReader(Paths.get(sourceFile));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing Values by Column Index

                String name = "";
                List<String> numbers = new ArrayList<>();
                for (int i = 0; i < csvRecord.size(); i++) {

                    if (i == 0) {
                        name = csvRecord.get(i);
                    } else {
                        numbers.add(csvRecord.get(i));
                    }

                }

                System.out.println("Record No - " + csvRecord.getRecordNumber());
                System.out.println("---------------");
                System.out.println("Name : " + name);
                System.out.println("Numbers : " + numbers.toString());
                System.out.println("---------------\n\n");

                extensions.add(new Extension(name,numbers));
            }
        }


        for (Extension a : extensions) {
            System.out.println(a);
        }
        return extensions;
    }
}