package com.example.bswj.utils;

import com.alibaba.excel.util.FileUtils;
import net.coobird.thumbnailator.Thumbnails;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class ImgUU {

    /**
     * 根据指定大小压缩图片
     * @param imageBytes  源图片字节数组
     * @param desFileSize 指定图片大小，单位kb
     * @param imageId     影像编号
     * @return 压缩质量后的图片字节数组
     */
    public static byte[] compressPicForScale(byte[] imageBytes, long desFileSize, String imageId) {
        if (imageBytes == null || imageBytes.length <= 0 || imageBytes.length < desFileSize * 1024) {
            return imageBytes;
        }
        long srcSize = imageBytes.length;
        double accuracy = getAccuracy(srcSize / 1024);
        try {
            while (imageBytes.length > desFileSize * 1024) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
                Thumbnails.of(inputStream)
                        .scale(accuracy)
                        .outputQuality(accuracy)
                        .toOutputStream(outputStream);
                imageBytes = outputStream.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageBytes;
    }

    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 900) {
            accuracy = 0.85;
        } else if (size < 2047) {
            accuracy = 0.6;
        } else if (size < 3275) {
            accuracy = 0.44;
        } else {
            accuracy = 0.4;
        }
        return accuracy;
    }



    /**
     * url资源转化为file流
     * @param url
     * @return
     */
    public static File urlToFile(URL url) {
        InputStream is = null;
        File file = null;
        FileOutputStream fos = null;
        try {
            file = File.createTempFile("tmp", null);
            URLConnection urlConn = null;
            urlConn = url.openConnection();
            is = urlConn.getInputStream();
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[4096];
            int length;
            while ((length = is.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            return file;
        } catch (IOException e) {
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
    }


    public static void main(String[] args) throws Exception{
        //commpressPicForScale("D:\\789.jpg", "D:\\778899.jpg");

        byte[] bytes = FileUtils.readFileToByteArray(new File("D:\\xkk\\qq.jpg"));
        bytes = compressPicForScale(bytes, 200, "x");// 图片小于200kb
        ByteArrayInputStream byteInput = new ByteArrayInputStream(bytes);
        FileUtils.writeToFile(new File("D:\\xkk\\qqx.jpg"), byteInput);
    }
}