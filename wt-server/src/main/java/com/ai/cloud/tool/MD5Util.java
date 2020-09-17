package com.ai.cloud.tool;


import com.ai.cloud.base.exception.AppException;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要
 * Message Digest Algorithm
 */
public class MD5Util {

    /**
     * @param plain
     * @return
     * @Description:  32位小写MD5
     */
    public static String plainToMd5L32(String plain)  {
        if(StringUtils.isEmpty(plain)){
            return null;
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new AppException("5005","MD5算法异常");
        }
        byte[] bytes = md5.digest(plain.getBytes());
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bytes){
            int bt = b&0xff;
            if (bt < 16){
                stringBuffer.append(0);
            }
            stringBuffer.append(Integer.toHexString(bt));
        }
        return stringBuffer.toString();
    }

    /**
     * @param plain
     * @return
     * @Description: 32位大写MD5
     */
    public static String plainToMd5U32(String plain)  {
        String reStr = plainToMd5L32(plain);
        if (reStr != null){
            reStr = reStr.toUpperCase();
        }
        return reStr;
    }

    /**
     * @param plain
     * @Description: 16位大写MD5
     */
    public static String plainToMd5U16(String plain)  {
        String reStr = plainToMd5L32(plain);
        if (reStr != null){
            reStr = reStr.toUpperCase().substring(8, 24);
        }
        return reStr;
    }

    /**
     * @param plain
     * @return
     * @Description: 16位小写MD5
     */
    public static String plainToMd5L16(String plain)  {
        String reStr = plainToMd5L32(plain);
        if (reStr != null){
            reStr = reStr.substring(8, 24);
        }
        return reStr;
    }

    public static String getMD5(String password) {
        if (password == null) {
            return null;
        }

        String hashtext  = null ;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest( password.getBytes("UTF-8") );

            BigInteger number = new BigInteger(1, messageDigest);
            hashtext  = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
        }
        catch (NoSuchAlgorithmException e) {

        }catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return hashtext;
    }

    public static  void main(String[] args)  {
        System.out.println(MD5Util.plainToMd5L32("a12DZ"));
    }
}
