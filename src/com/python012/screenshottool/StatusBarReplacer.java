package com.python012.screenshottool;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class StatusBarReplacer {

    StatusBarReplacer(File statusBarImageFile, File screenshotImageFile, int count, String fileNameSuffix) {
        
        System.out.println("## Working on file#" + Integer.toString(count) + " - " + screenshotImageFile.getAbsolutePath());
        
        // 如果参数的文件名不是.png文件结尾，将抛出参数错误异常
//        if (!statusBarImageFile.getAbsolutePath().endsWith(".png") || !screenshotImageFile.getAbsolutePath().endsWith(".png")) {
//            throw new IllegalArgumentException("The given files are not .png file!");
//        }
        
        // 构造方法，需要两个image
        try {
            this.setStatusBarImage(ImageIO.read(statusBarImageFile));
            this.setScreenshotImage(ImageIO.read(screenshotImageFile));
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }

        wOfNewScreen = screenshot.getWidth(); // 合并后新图的宽，也即待修改图片screenshot的宽
        hOfStatusBar = statusBarImage.getHeight(); // 状态栏图的高，合并后的图的上半部分
        hOfScreenshot = screenshot.getHeight(); // 合并后新图的高

        if (wOfNewScreen != statusBarImage.getWidth() || hOfStatusBar >= hOfScreenshot) {
            // 状态栏图和待修改图screenshot的宽必须相同，同时状态栏图的高不应大于screenshot
            throw new IllegalArgumentException("The two images don't have same width or status bar image has longer height!");
        }
        
        // 构造合并后的新图的绝对路径，同时给文件名加上前缀 "new_"
        abosolutePathOfNewScreen = screenshotImageFile.getParent() + "/" + "new_" + screenshotImageFile.getName();
        this.fileNameSuffix = fileNameSuffix;
    }

    private int wOfNewScreen; // 合并后新图的宽
    private int hOfStatusBar; // 上半部分的图的高，也即较小的图的高
    private int hOfScreenshot; // 大图的高，也即合并后新图的高

    // 初始化Replacer类的实例所必需的两个参数，分别为一个小图（状态栏图）和一个大图
    private BufferedImage statusBarImage;
    private BufferedImage screenshot;
    
    private String fileNameSuffix;
    
    private String abosolutePathOfNewScreen;

    private void setStatusBarImage(BufferedImage newStatusBarImage) {
        this.statusBarImage = newStatusBarImage;
    }

    private void setScreenshotImage(BufferedImage orginalScreenshot) {
        this.screenshot = orginalScreenshot;
    }

    public int getNewScreenHeight() {
        return this.screenshot.getHeight();
    }

    public int getScreenshotWidth() {
        return this.screenshot.getWidth();
    }

    public int getStatusBarHeight() {
        return this.statusBarImage.getHeight();
    }

    private int[] getBottomPartRGBArray() {
        // 获取即将合并的新图的，下半部分的RGB数据数组
        int[] screenshotArray = new int[wOfNewScreen * hOfScreenshot];
        screenshotArray = screenshot.getRGB(0, hOfStatusBar, wOfNewScreen, hOfScreenshot - hOfStatusBar, screenshotArray, 0, wOfNewScreen);
        return screenshotArray;
    }

    private int[] getStatusBarRGBArray() {
        // 获取即将合并的新图的，上半部分（状态栏）的RGB数据数组
        int[] statusBarImageArray = new int[this.getStatusBarHeight() * this.getScreenshotWidth()];
        return statusBarImage.getRGB(0, 0, wOfNewScreen, hOfStatusBar, statusBarImageArray, 0, wOfNewScreen);
    }

    public void outputNewScreen() {

        try {
            // 初始化合并后的新图
            BufferedImage newScreenshot = new BufferedImage(wOfNewScreen, hOfScreenshot, screenshot.getType());
            // 设置新图的上半部分的RGB数据
            newScreenshot.setRGB(0, 0, wOfNewScreen, hOfStatusBar, this.getStatusBarRGBArray(), 0, wOfNewScreen);

            // 设置新图的下半部分的RGB数据
            newScreenshot.setRGB(0, hOfStatusBar, wOfNewScreen, hOfScreenshot - hOfStatusBar, this.getBottomPartRGBArray(), 0, wOfNewScreen);
            
            ImageIO.write(newScreenshot, this.fileNameSuffix, new File(abosolutePathOfNewScreen));
            System.out.println("    Modify status bar - Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}