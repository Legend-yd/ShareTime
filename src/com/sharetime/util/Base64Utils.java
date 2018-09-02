package com.sharetime.util;

import com.ctc.wstx.util.StringUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @Author: yd
 * @Date: 2018/7/29 11:05
 * @Version 1.0
 */
public class Base64Utils {


    /**
     * 将图片文件转换成base64字符串，参数为该图片的路径
     *
     * @param imageFile
     * @return java.lang.String
     */
    public String ImageToBase64(String imageFile) {
        if (imageFile == null) {//防止初次注册
            return null;
        }
        InputStream in = null;
        byte[] data = null;

        // 读取图片字节数组
        try {
            in = new FileInputStream(imageFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();

        if (data != null) {
            return "data:image/jpeg;base64," + encoder.encode(data);// 返回Base64编码过的字节数组字符串
        }
        return null;
    }

    /**
     * 将base64解码成图片并保存在传入的路径下
     * 第一个参数为base64 ，第二个参数为路径
     *
     * @param base64, imgFilePath
     * @return boolean
     */
    public boolean Base64ToImage(String base64, String imgFilePath) {
        // 对字节数组字符串进行Base64解码并生成图片
        if (base64 == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(base64);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 用来测试工具类是否成功
     *
     * @param args
     * @return void
     */
    public static void main(String[] args) {

    }

    /**
     * 改变图片的尺寸
     *
     * @param newWidth, newHeight, path
     * @return boolean
     */
    public boolean changeSize(int newWidth, int newHeight, String path) {
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(path));

            //字节流转图片对象
            Image bi = ImageIO.read(in);
            //构建图片流
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            //绘制改变尺寸后的图
            tag.getGraphics().drawImage(bi, 0, 0, newWidth, newHeight, null);
            //输出流
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
            //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            //encoder.encode(tag);
            ImageIO.write(tag, "PNG", out);
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
