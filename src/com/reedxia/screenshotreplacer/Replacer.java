package com.reedxia.screenshotreplacer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Replacer {

    Replacer(BufferedImage statusBarImage, BufferedImage screenshotImage) {

        // 构造函数，需要两个image
        this.setStatusBarImage(statusBarImage);
        this.setScreenshotImage(screenshotImage);

        wOfNewScreen = screenshot.getWidth(); // 合并后新图的宽，也即待修改图片screenshot的宽
        hOfStatusBar = statusBarImage.getHeight(); // 状态栏图的高，合并后的图的上半部分
        hOfScreenshot = screenshot.getHeight(); // 合并后新图的高

        if (wOfNewScreen != statusBarImage.getWidth() || hOfStatusBar >= hOfScreenshot) {

            // 状态栏图和待修改图screenshot的宽必须相同，同时状态栏图的高不应大于screenshot
<<<<<<< Updated upstream
            throw new IllegalArgumentException("The two images don't have same wight or status bar image has longer height!!");
=======
            throw new IllegalArgumentException("The two images don't have same width or status bar image has longer height!!");
>>>>>>> Stashed changes
        }
    }

    private int wOfNewScreen; // 合并后新图的宽
    private int hOfStatusBar; // 上半部分的图的高，也即较小的图的高
    private int hOfScreenshot; // 大图的高，也即合并后新图的高

    // 初始化Replacer类的实例所必需的两个参数，分别为一个小图（状态栏图）和一个大图
    private BufferedImage statusBarImage;
    private BufferedImage screenshot;

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

    public void outputNewScreen(File newScreenshotFile) {

        try {
            // 初始化合并后的新图
            BufferedImage newScreenshot = new BufferedImage(wOfNewScreen, hOfScreenshot, screenshot.getType());
            // 设置新图的上半部分的RGB数据
            newScreenshot.setRGB(0, 0, wOfNewScreen, hOfStatusBar, this.getStatusBarRGBArray(), 0, wOfNewScreen);
            // 测试数据
            System.out.println(hOfScreenshot);
            System.out.println(hOfStatusBar);
            // 设置新图的下半部分的RGB数据
            newScreenshot.setRGB(0, hOfStatusBar, wOfNewScreen, hOfScreenshot - hOfStatusBar, this.getBottomPartRGBArray(), 0, wOfNewScreen);
            // 
            ImageIO.write(newScreenshot, "png", newScreenshotFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        File fileOne = new File("/Users/reed/Desktop/small.png");
        File fileTwo = new File("/Users/reed/Desktop/big.png");
        Replacer replacer = new Replacer(ImageIO.read(fileOne), ImageIO.read(fileTwo));

        replacer.outputNewScreen(new File("/Users/reed/Desktop/out.png"));
    }
}
