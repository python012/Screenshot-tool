/*
 * 
 *  Screenshot tool v1.0
 * 
 */


package com.reedxia.screenshotreplacer;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        
        // 待修改的图所在的文件夹的绝对路径，该文件夹里的所有png文件（包括子目录中的）将被修改
        String screenshotDirectory = "/Users/reed/Desktop/test";
        
        // 状态栏图片的绝对路径，必须是路径＋完整文件名(status_bar.png)，必须是png格式文件
        String statusBarImageAbosolutePath = "/Users/reed/Desktop/small.png";
        
        // 被修改的图片不会被删除，生成好的图片将会放在同一文件夹下，同时文件名会加上前缀 "new_"
        
        FileList screenshotFileList = new FileList(new File(screenshotDirectory));
        
        System.out.println("Start to modify the screenshots.");
        for(File x:screenshotFileList.getPNGList()) {
            Replacer replacer = new Replacer(new File(statusBarImageAbosolutePath), x);
            replacer.outputNewScreen();
            replacer = null;
        }
        System.out.println("Done.");
        screenshotFileList = null;
    }

}
