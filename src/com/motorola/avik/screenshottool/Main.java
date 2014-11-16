package com.motorola.avik.screenshottool;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        try {
            
            InputStream inputStream = new BufferedInputStream(new FileInputStream("config.properties"));
            Properties p = new Properties();
            p.load(inputStream);

            String statusBarFilePath = p.getProperty("statusBarFilePath");
            String entryDirectory = p.getProperty("entryDirectory");
            String fileNameSuffix = p.getProperty("fileNameSuffix"); // png, jpg

            FileListGenerator screenshotFileList = new FileListGenerator(new File(entryDirectory), ("." + fileNameSuffix));

            System.out.println("Start to modify the screenshots.");
            int count = 1;
            for (File x : screenshotFileList.getFileList()) {
                Replacer replacer = new Replacer(new File(statusBarFilePath), x, count, fileNameSuffix);
                replacer.outputNewScreen();
                count++;
                replacer = null;
            }

            System.out.println("Done.");
            screenshotFileList = null;
            inputStream.close();
            
        } catch (FileNotFoundException e) {
            System.out.print("\"config.propertie\" is not found.");
        } catch (IOException e) {
            System.out.print("Problem on \"p.load(inputStream)\"");
            e.printStackTrace();
        }
    }
}
