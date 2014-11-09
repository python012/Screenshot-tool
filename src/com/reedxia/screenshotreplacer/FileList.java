package com.reedxia.screenshotreplacer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileList {
    
    // FileList类可寻找指定路径下所有的PNG文件，并生成列表 － List<File>

//    public static void main(String[] args) {
//        FileList a = new FileList(new File("/Users/reed/Desktop"));
//        for (File x : a.getPNGList()) {
//            System.out.println(x.getAbsolutePath());
//            System.out.println(x.getParent());
//            System.out.println(x.getName());
//            System.out.println("----------------------");
//
//        }
//    }

    FileList(File path) {
        // 构造方法，参数是一个类型为File的参数，必须是一个有效的的目录的绝对路径，否则将抛出异常IllegalArgumentException
        if (path.isDirectory() && path.isAbsolute()) {
            this.entryPath = path;
        } else {
            throw new IllegalArgumentException("The given path is not absolute directory!!");
        }
        pngFileList = new ArrayList<File>(); // 初始化png文件列表
    }

    private File entryPath; // 寻找后缀名是.png文件的入口路径，也即实例化FileList类所传入构造方法的path参数
    private List<File> pngFileList; // 对象类型为File的列表，即将把找到的所有png文件添加到该列表，该列表的初始化在构造方法中完成

    public List<File> getPNGList() {
        // 寻找PNG文件，填充PNG文件列表 － pngFileList，并返回该文件列表
        this.fillPNGList(entryPath);
        return pngFileList;
    }

    private void fillPNGList(File absolutePath) {
        //寻找指定路径下的所有png文件，并添加到pngFileList文件列表，
        //对该路径下的子目录继续执行本方法
        File[] fileListInDir = absolutePath.listFiles(); //，用以保存指定路径下所有文件&目录列表，注意目录也是File类型

        for (int i = 0; i < fileListInDir.length; i++) {
            if (fileListInDir[i].isFile()) {
                if (fileListInDir[i].getAbsolutePath().endsWith(".png")) {
                    this.pngFileList.add(fileListInDir[i]);
                }
            } else {
                this.fillPNGList(fileListInDir[i]);
            }
        }
        fileListInDir = null; // 此时该临时文件列表已完成使用
    }
}