package com.fbmoll.eazapata.dataaccess.fileconverter;

import Data.CsvContent;
import Utils.FileUtils;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVFile {
    private  Logger log = LoggerFactory.getLogger(CSVFile.class);

    public <T> T  readCSV(String filePath) {

        ArrayList<String[]> csvContent = new ArrayList<>();
        File file = new File(filePath);

        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            CsvContent csvClass = new CsvContent ();
            String[] fileLine;
            int index = 0;

            while ((fileLine = reader.readNext()) != null) {
                fileLine = FileUtils.compareAndReplace(fileLine);
                csvContent.add(fileLine);
                if (csvContent.get(0).length != csvContent.get(index).length ){
                    System.out.println("El archivo indicado no es válido.");
                    fileLine = null;
                }else{

                }
                index++;
            }
            for (int i = 0; i < csvContent.get(0).length ; i++) {
                csvClass.setProperty(i,csvContent.get(0)[i]);
            }
            System.out.println();

        } catch (Exception e) {
            log.error("Can't access to file", e);
        }
        return (T)csvContent;
    }



}

