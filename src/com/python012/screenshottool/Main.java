package com.python012.screenshottool;

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
            String fileNameSuffix = p.getProperty("fileNameSuffix");

            FileListGenerator screenshotFileList = new FileListGenerator(new File(entryDirectory), ("." + fileNameSuffix));

            System.out.println("Start to modify the screenshots.");
            int count = 1;
            for (File x : screenshotFileList.getFileList()) {
                StatusBarReplacer replacer = new StatusBarReplacer(new File(statusBarFilePath), x, count, fileNameSuffix);
                replacer.outputNewScreen();
                count++;
                replacer = null;
                
                ImageFormater.transformFormate(x);
            }

            System.out.println("Done.");
            screenshotFileList = null;
            inputStream.close();

        } catch (FileNotFoundException e) {
            System.out.print("\"config.propertie\" is not found.");
            e.printStackTrace();    
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}