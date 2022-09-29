package com.example.bswj.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AESUtils {

    public static String aesDecrypt(String str, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"));
        byte[] bytes = Base64.decodeBase64(str);
        bytes = cipher.doFinal(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }




    public static void main(String[] args) throws Exception{
        String s1 = aesDecrypt("E4M54v2CbwnbdG+quqWwgFGI5dgx3shx2gGZRiihvkQQLgbH12Y9/dJXO1/7H7QLL3H9fstismlYMLQrZxShEyknFJcLG96HbG4Cx/7gq4YMXgZJDI9Qvm1sH6H4arIHaPTSbHTkfaYo7fo6Sc3lwBMOpJHi33Os5u7DobPmqkzkuyoRxbTD4mZaSYleDcYuouQTdma+rubH5PPzg0+R09XsEHWkgF6cc+Ylh2w0N6590eJDNdQvoI4m7eSiWQCJo5nN5zXj/2QeQcYwIfdpmQ==",
                "1234567890123456");
        System.out.println("s1 == " + s1);
    }

}
