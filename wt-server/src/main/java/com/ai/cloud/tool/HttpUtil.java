package com.ai.cloud.tool;

import com.ai.cloud.base.exception.BusinessException;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wave
 * @create 2018-09-13 12:04
 **/
public class HttpUtil<T> {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * post 请求
     *
     * @param str
     * @param targetAddr
     * @param timeout
     * @param <T>
     * @return
     */
    public static <T> Call toJsonStringPostSend(T str, String targetAddr, long timeout) {
        MediaType json = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(json, str.toString());
        Request request = new Request.Builder()
                .addHeader("Accept", "*/*")
                .addHeader("Connection", "Keep-Alive")
                .addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)")
                .url(targetAddr)
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(timeout, TimeUnit.SECONDS)
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .build();
        return client.newCall(request);
    }


    /**
     * get 请求
     *
     * @param targetAddr
     * @return
     */
    public static Call toJsonStringGetSend(String targetAddr) {
        Request request = new Request.Builder()
                .url(targetAddr)
                .get()
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(2, TimeUnit.SECONDS)
                .build();
        return client.newCall(request);
    }

    /**
     * 发送POST请求
     *
     * @param url        目的地址
     * @param parameters 请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendPost(String url, Map<String, String> parameters) throws Exception{
        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        StringBuffer sb = new StringBuffer();// 处理请求参数
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(URLEncoder.encode(parameters.get(name), "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {

                    sb.append(name).append("=").append(URLEncoder.encode(parameters.get(name), "UTF-8"))
                            .append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            logger.info("码上购 - 沃支付 - 接口调用请求参数：{}", params);
            // 创建URL对象
            URL connURL = new URL(url);
            // 打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection) connURL.openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 设置超时时间
            httpConn.setConnectTimeout(3000);
            httpConn.setReadTimeout(3000);
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.info("码上购 - 沃支付 - 接口调用异常：{}", e);
            throw new BusinessException("7777","调用是否开户或实名接口异常",e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                logger.info("码上购 - 沃支付 - 接口调用异常：{}", ex);
            }
        }
        return result;
    }
}
