package com.python012.screenshottool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListGenerator {

    // FileList类可寻找指定路径下所有指定文件后缀名的文件，并生成文件列表 － List<File>

    FileListGenerator(File path) { // 如果未指定文件后缀名
        if (path.isDirectory() && path.isAbsolute()) {
            this.entryPath = path;
        } else {
            throw new IllegalArgumentException("The given path is not absolute directory!!");
        }
        this.specifySuffix = false; // 没有传入指定后缀名的参数，所以将specifySuffix设为false
        fileList = new ArrayList<File>(); // 初始化文件列表
    }

    FileListGenerator(File path, String fileNameSuffix) { // 如果指定了文件后缀名
        if (path.isDirectory() && path.isAbsolute()) {
            this.entryPath = path;
        } else {
            throw new IllegalArgumentException("The given path is not absolute directory!!");
        }
        this.specifySuffix = true;
        this.fileNameSuffix = fileNameSuffix;
        fileList = new ArrayList<File>(); // 初始化文件列表
    }

    private File entryPath; // 搜索文件的入口路径
    private List<File> fileList; // 对象类型为File的列表，该列表的初始化在构造方法中完成
    private boolean specifySuffix; // 是否指定搜索目标文件的后缀名，以开启搜索特定文件功能
    private String fileNameSuffix;

    public List<File> getFileList() {
        this.fillFileList(entryPath);
        return fileList;
    }

    private void fillFileList(File absolutePath) {
        // 寻找指定后缀名或者普通文件，并填充文件列表fileList
        File[] fileListInDir = absolutePath.listFiles(); // ，用以保存指定路径下所有文件&目录列表，注意目录也是File类型

        if (this.specifySuffix) {
            for (int i = 0; i < fileListInDir.length; i++) {
                if (fileListInDir[i].isFile()) { // 判断当前项是否是文件，或者是目录
                    if (fileListInDir[i].getAbsolutePath().endsWith(this.fileNameSuffix)) {
                        this.fileList.add(fileListInDir[i]);
                    }
                } else {
                    this.fillFileList(fileListInDir[i]); // 如果数组fileListInDir中的当前项不是文件，而是目录时，递归调用方法fillFileList()
                }
            }
        } else {
            for (File x : fileListInDir) { // 如果不指定文件后缀名的话，寻找所有的文件的过程就简单了...
                if (x.isFile()) {
                    this.fileList.add(x);
                } else {
                    this.fillFileList(x);
                }
            }
        }

        fileListInDir = null; // 此时该临时文件列表已完成使用
    }
}