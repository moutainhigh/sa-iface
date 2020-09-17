package com.ai.cloud.tool;

/**
 * Copyright (c) 2005-2012 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * 支持HMAC-SHA1消息签名 �?DES/AES对称加密的工具类. 支持Hex与Base64两种编码方式.
 * 
 * @author calvin
 */
public class CryptosUtil {
    private static final String AES = "AES";
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";
    private static final int DEFAULT_AES_KEYSIZE = 128;
    private static final int DEFAULT_IVSIZE = 16;

    private static SecureRandom random = new SecureRandom();

    /**
     * 使用AES加密原始字符�?
     * 
     * @param input
     *            原始输入字符数组
     * @param key
     *            符合AES要求的密�?
     */
    public static byte[] aesEncrypt(byte[] input, byte[] key) {
        return aes(input, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 使用AES加密原始字符�?
     * 
     * @param input
     *            原始输入字符数组
     * @param key
     *            符合AES要求的密�?
     * @param iv
     *            初始向量
     */
    public static byte[] aesEncrypt(byte[] input, byte[] key, byte[] iv) {
        return aes(input, key, iv, Cipher.ENCRYPT_MODE);
    }

    /**
     * 使用AES解密字符�? 返回原始字符�?
     * 
     * @param input
     *            Hex编码的加密字符串
     * @param key
     *            符合AES要求的密�?
     */
    public static String aesDecrypt(byte[] input, byte[] key) {
        byte[] decryptResult = aes(input, key, Cipher.DECRYPT_MODE);
        return new String(decryptResult);
    }

    /**
     * 使用AES解密字符�? 返回原始字符�?
     * 
     * @param input
     *            Hex编码的加密字符串
     * @param key
     *            符合AES要求的密�?
     * @param iv
     *            初始向量
     */
    public static String aesDecrypt(byte[] input, byte[] key, byte[] iv) {
        byte[] decryptResult = aes(input, key, iv, Cipher.DECRYPT_MODE);
        return new String(decryptResult);
    }

    /**
     * 使用AES加密或解密无编码的原始字节数�? 返回无编码的字节数组结果.
     * 
     * @param input
     *            原始字节数组
     * @param key
     *            符合AES要求的密�?
     * @param mode
     *            Cipher.ENCRYPT_MODE �?Cipher.DECRYPT_MODE
     */
    private static byte[] aes(byte[] input, byte[] key, int mode) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(mode, secretKey);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException e) {
            // throw Exceptions.unchecked(e);
            return null;
        }
    }

    /**
     * 使用AES加密或解密无编码的原始字节数�? 返回无编码的字节数组结果.
     * 
     * @param input
     *            原始字节数组
     * @param key
     *            符合AES要求的密�?
     * @param iv
     *            初始向量
     * @param mode
     *            Cipher.ENCRYPT_MODE �?Cipher.DECRYPT_MODE
     */
    private static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, AES);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(AES_CBC);
            cipher.init(mode, secretKey, ivSpec);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException e) {
            // throw Exceptions.unchecked(e);
            return null;
        }
    }

    /**
     * 生成AES密钥,返回字节数组, 默认长度�?28�?16字节).
     */
    public static byte[] generateAesKey() {
        return generateAesKey(DEFAULT_AES_KEYSIZE);
    }

    /**
     * 生成AES密钥,可�?长度�?28,192,256�?
     */
    public static byte[] generateAesKey(int keysize) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
            keyGenerator.init(keysize);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException e) {
            // throw Exceptions.unchecked(e);
            return null;
        }
    }

    /**
     * 生成随机向量,默认大小为cipher.getBlockSize(), 16字节.
     */
    public static byte[] generateIV() {
        byte[] bytes = new byte[DEFAULT_IVSIZE];
        random.nextBytes(bytes);
        return bytes;
    }

    public static byte[] aesEncryptByKey(byte[] input, byte[] key) {
        return aes(input, key, hexDecode("1a52eb4565be8628a807403d67dce79d"),
                Cipher.ENCRYPT_MODE);
    }

    public static String aesDecryptByKey(byte[] input, byte[] key) {
        byte[] decryptResult = aes(input, key,
                hexDecode("1a52eb4565be8628a807403d67dce79d"),
                Cipher.DECRYPT_MODE);
        return new String(decryptResult);

    }

    public static String usernumberEncrypted(String usernumber) {
        String hexEncode = hexEncode(aesEncrypt(usernumber.getBytes(),
                hexDecode("f6b0d3f905bf02939b4f6d29f257c2ab"),
                hexDecode("1a42eb4565be8628a807403d67dce78d")));
        return hexEncode;
    }

    public static String usernumberDecrypt(String usernumber) {
        String hexEncode = aesDecrypt(hexDecode(usernumber),
                hexDecode("f6b0d3f905bf02939b4f6d29f257c2ab"),
                hexDecode("1a42eb4565be8628a807403d67dce78d"));
        return hexEncode;
    }

    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    /**
     * Hex编码.
     */
    public static String hexEncode(byte[] input) {
        return Hex.encodeHexString(input);
    }

    /**
     * Hex解码.
     */
    public static byte[] hexDecode(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            throw new IllegalStateException("Hex Decoder exception", e);
        }
    }

    /**
     * Base64编码.
     */
    public static String base64Encode(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    /**
     * Base64编码, URL安全(将Base64中的URL非法字符如+,/=转为其他字符, 见RFC3548).
     */
    public static String base64UrlSafeEncode(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    /**
     * Base64解码.
     */
    public static byte[] base64Decode(String input) {
        return Base64.decodeBase64(input);
    }

    /**
     * URL 编码, Encode默认为UTF-8.
     */
    public static String urlEncode(String input) {
        try {
            return URLEncoder.encode(input, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(
                    "Unsupported Encoding Exception", e);
        }
    }

    /**
     * URL 解码, Encode默认为UTF-8.
     */
    public static String urlDecode(String input) {
        try {
            return URLDecoder.decode(input, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(
                    "Unsupported Encoding Exception", e);
        }
    }

    /**
     * Html 转码.
     */
    public static String htmlEscape(String html) {
        return StringEscapeUtils.escapeHtml(html);
    }

    /**
     * Html 解码.
     */
    public static String htmlUnescape(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml(htmlEscaped);
    }

    /**
     * Xml 转码.
     */
    public static String xmlEscape(String xml) {
        return StringEscapeUtils.escapeXml(xml);
    }

    /**
     * Xml 解码.
     */
    public static String xmlUnescape(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    /**
     * 随机向量：1a42eb4565be8628a807403d67dce78d 密钥：f6b0d3f905bf02939b4f6d29f257c2ab
     */
    public static void main(String[] args) {
        // 加密
        System.out.println(EncodeUtils.hexEncode(aesEncrypt(
                "18610676365".getBytes(),
                EncodeUtils.hexDecode("f6b0d3f905bf02939b4f6d29f257c2ab"),
                EncodeUtils.hexDecode("1a42eb4565be8628a807403d67dce78d"))));
        // 解密
        // System.out.println(aesEncrypt("18610676365".getBytes(),
        // "f6b0d3f905bf02939b4f6d29f257c2ab".getBytes(),
        // "1a42eb4565be8628a807403d67dce78d".getBytes()));
        System.out.println(aesDecrypt(
                EncodeUtils.hexDecode("62542ddfbf62702cdac6c27c67a423af"),
                EncodeUtils.hexDecode("f6b0d3f905bf02939b4f6d29f257c2ab"),
                EncodeUtils.hexDecode("1a42eb4565be8628a807403d67dce78d")));

        // System.out.println(EncodeUtils.hexEncode(aesEncrypt(
        // "连上6天班".getBytes(),
        // EncodeUtils.hexDecode("f6b0d3f905bf02939b4f6d29f257c2ab"),
        // EncodeUtils.hexDecode("1a42eb4565be8628a807403d67dce78d"))));
    }
}
