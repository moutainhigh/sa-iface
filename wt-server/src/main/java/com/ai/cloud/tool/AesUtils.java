package com.ai.cloud.tool;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.net.URLDecoder;

/**
 * @author xyd
 */
public class AesUtils {
    private String defaultKey = "a47bEA5c02655520";
    private SecretKey secretKey;
    private Cipher c;
    private byte[] cipherByte;

    public AesUtils() throws Exception {
        secretKey = new SecretKeySpec(defaultKey.getBytes("UTF-8"), "AES");
        c = Cipher.getInstance("AES");
    }

    public AesUtils(String key) throws Exception {
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

    public static void main(String[] args) throws Exception {
String a = "ep0vjOXcEWyoK06n5MkGalVynJZVsDSTLBECUeoAx4vDIqIn/qT3KmSGGgFPu9nl";
        String p= "SM4iJdF3IqWeliJFMuCPOn3yljnAjhSM9QW1GI92WIE%253D%250D%250A";
        AesUtils aesCryptor = new AesUtils("yIBkJwo3ZQ5yxm6z");

        //System.out.println(aesCryptor.decrypt(a));
        System.out.println(aesCryptor.decrypt(URLDecoder.decode(URLDecoder.decode(p,"UTF-8"),"UTF-8")));

    }
}
