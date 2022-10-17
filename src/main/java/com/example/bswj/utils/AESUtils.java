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
}