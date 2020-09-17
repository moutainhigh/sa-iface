package com.ai.cloud.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Crypt {

    private SecretKey secretKey;
    private Cipher c;
    private byte[] cipherByte;

    public Crypt(String key) throws Exception {
        secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        c = Cipher.getInstance("AES");
    }

    public String encrypt(String str) throws Exception {
        c.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] src = str.getBytes("UTF-8");
        cipherByte = c.doFinal(src);
        return toBase64(cipherByte);
    }

    public String decrypt(String str) throws Exception {
        byte[] buff = fromBase64(str);
        c.init(Cipher.DECRYPT_MODE, secretKey);
        cipherByte = c.doFinal(buff);
        return new String(cipherByte, "UTF-8");
    }

    /**
     * 将字节数组转换成BASE64字符串。
     *
     * @param array 字节数组。
     * @return BASE64字符串。
     */
    public String toBase64(byte[] array) {
        return DatatypeConverter.printBase64Binary(array);
    }

    /**
     * 将Base64字符串转换成字节数组。
     *
     * @param s Base64字符串。
     * @return 字节数组。
     */
    public byte[] fromBase64(String s) {
        return DatatypeConverter.parseBase64Binary(s);
    }

    public static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 排序方法
     *
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);

        StringBuilder sbuilder = new StringBuilder();
        for (String str : strArray) {
            sbuilder.append(str);
        }

        return sbuilder.toString();
    }

}
