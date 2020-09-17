package com.ai.cloud.tool;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 
 * @author renlei
 *
 */
public class AES {

    public static final String CHAR_ENCODING = "UTF-8";
    public static final String AES = "AES";
    public static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";
//

//    系统  c8002313db99c1d1

//    集成  c8002313db99c1d1
    /**
     * @Description: 加密方法
     * @param data
     *            需要加密的内容
     * @param key
     *            加密密码
     * @return byte[]
     * @throws
     */
    private static byte[] encrypt(byte[] data, byte[] key) {
        if (key.length != 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, AES);
            SecretKeySpec seckey = new SecretKeySpec(secretKey.getEncoded(), AES);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, seckey);// 初始化
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * @Description: 解密方法
     * @param data
     *            待解密内容
     * @param key
     *            解密密钥
     * @return byte[]
     * @throws
     */
    private static byte[] decrypt(byte[] data, byte[] key) {
        if (key.length != 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, AES);
            SecretKeySpec seckey = new SecretKeySpec(secretKey.getEncoded(), AES);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, seckey);// 初始化
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    /**
     * 加密，输出Base64密文
     *
     * @param data
     * @param key
     * @return
     */
    public static String encryptBase64(String data, String key) {
        try {
            byte[] valueByte = encrypt(data.getBytes(CHAR_ENCODING), key.getBytes(CHAR_ENCODING));
            return new String(Base64.encode(valueByte));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * 解密，输入Base64密文
     *
     * @param data
     * @param key
     * @return
     */
    public static String decryptBase64(String data, String key) {
        try {
            byte[] valueByte = decrypt(Base64.decode(data.getBytes()), key.getBytes(CHAR_ENCODING));
            return new String(valueByte, CHAR_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    public static void main(String[] args) {
        String aesKey = "55153a41d9444c8c";

        System.out.println(URLEncoder.encode(encryptBase64("18618365193", "c8002313db99c1d1")));
        String dedata = decryptBase64(URLDecoder.decode("NPgKVoXnfh18mPzwKkvs4Q%3D%3D"), "c8002313db99c1d1");
        System.out.println(dedata);
    }
}
