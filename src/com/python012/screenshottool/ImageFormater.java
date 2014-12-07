package com.python012.screenshottool;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageFormater {

    /* ImageFormater将提供一个方法，接受一个File类型的对象为参数
     * 如果该参数是一个文件，则默认它是图片文件，将尝试把它改为BufferedImage.TYPE_3BYTE_BGR格式的
     * 文件，也即用3个8bit来存储RGB数据。如果该参数是一个目录的，则对该目录进行递归调用自身，将对目录
     * 和子目录下所有文件进行处理。
     */
    public static void transformFormate(File file) {
        if (file.isDirectory()) {
            File[] fileList = file.listFiles();
            for (File f : fileList) {
                transformFormate(f);
            }
        } else {
            try {
                BufferedImage image = ImageIO.read(file);
                if (image.getColorModel().getPixelSize() > 24) {
                    BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
                    for (int y = 0; y < image.getHeight(); y++) {
                        for (int x = 0; x < image.getWidth(); x++) {
                            newImage.setRGB(x, y, image.getRGB(x, y));
                        }
                    }
                    image = newImage;
                }
                // 新建字符串变量fileNameSuffix来保存当前文件的后缀名，这里似乎应由全局配置文件来决定，待改善
                String fileNameSuffix = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1);
                ImageIO.write(image, fileNameSuffix, file);
                System.out.println("    Transfer image formate - Done");
            } catch (IOException e) {
                System.out.println("Problem found in - " + file.getAbsolutePath());
                e.printStackTrace();
            }
        }
    }
}
