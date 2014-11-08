package com.reedxia.screenshotreplacer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileList {

    public static void main(String[] args) {
        FileList a = new FileList(new File("/Users/reed/Desktop"));
        for (File x : a.getPNGList()) {
            System.out.println(x.getAbsolutePath());
        }
    }

    FileList(File path) {

        if (path.isDirectory() && path.isAbsolute()) {
            this.entryPath = path;
        } else {
            throw new IllegalArgumentException("The given path is not absolute directory!!");
        }
        
        pngFileList = new ArrayList<File>();
    }

    private File entryPath;
    private List<File> pngFileList;

    public List<File> getPNGList() {

        this.fillPNGList(entryPath);
        return pngFileList;
    }

    private void fillPNGList(File absolutePath) {

        File[] fileListInDir = absolutePath.listFiles();

        for (int i = 0; i < fileListInDir.length; i++) {

            if (fileListInDir[i].isFile()) {

                if (fileListInDir[i].getAbsolutePath().endsWith(".png")) {
                    
                    System.out.println(fileListInDir[i].getAbsolutePath());
                    this.pngFileList.add(fileListInDir[i]);
                }
            } else {

                this.fillPNGList(fileListInDir[i]);
            }
        }
    }
}