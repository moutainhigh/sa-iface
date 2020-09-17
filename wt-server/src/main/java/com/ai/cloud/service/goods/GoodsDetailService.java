package com.ai.cloud.service.goods;

import com.ai.cloud.auth.bean.AuthBean;
import com.ai.cloud.base.exception.BusinessException;
import com.ai.cloud.base.lang.Rmap;
import com.ai.cloud.client.GoodsQueryClient;
import com.ai.cloud.client.pojo.goods.DataResponse;
import com.ai.cloud.client.pojo.goods.GoodsRsp;
import com.ai.cloud.client.pojo.goods.GoodsStockReq;
import com.ai.cloud.client.pojo.goods.PhotoReq;
import com.ai.cloud.tool.AesUtils;
import com.ailk.ecs.esf.base.sec.utils.Base64Utils;
import com.alibaba.fastjson.JSON;

import java.net.URLEncoder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * @Auther JY
 * @Date 2020/3/16
 * DON'T REGRET ANYTHING
 */
@Slf4j
@Component
public class GoodsDetailService {

    @Value("${wxpublic.aesKey:is9Ubm0iH4uMQ0wE}")
    private String aesKey;
    @Autowired
    GoodsQueryClient goodsQueryClient;

    public Map goodsDetail(String goodsId, AuthBean authBean) {
        Map map = new HashMap();

        //查库存
        DataResponse<Map> goodsNum = goodsQueryClient.qryStock(new GoodsStockReq(goodsId, authBean.getProvinceCode(), authBean.getEparchyCode()));
        dealResponseData(goodsId, goodsNum, "商品库存");
        map.put("goodsStock", dealStock(goodsNum.getData()));

        //查商品图片
        DataResponse<List<Map>> photos = goodsQueryClient.queryGoodsPhoto(new PhotoReq(asList(goodsId), asList("D")));
        dealResponseData(goodsId, photos, "商品图片");
        map.put("goodsId", goodsId);
        map.put("goodsPhoto", dealPhoto(photos.getData()));

        //查商品介绍
        DataResponse<Map> introduce = goodsQueryClient.queryIntroduce(goodsId);
        dealResponseData(goodsId, introduce, "商品介绍");
        map.put("pageUrl", dealPageUrl(introduce.getData()));

        //商品基础信息
        DataResponse<GoodsRsp> goodsInfo = goodsQueryClient.queryGoodsInfo(goodsId);
        dealResponseData(goodsId, goodsInfo, "商品基础信息");
        map.put("goodsName", goodsInfo.getData().getGoodsName());
        map.put("goodsPrice", String.format("%.2f", Float.parseFloat(goodsInfo.getData().getFavorablePrice())/1000));
        map.put("goodsTitle", goodsInfo.getData().getGoodsLable());
        map.put("encryptData", encryptData(authBean));
        return map;
    }

    /**
     * 判断接口返回数据
     *
     * @param data
     * @param come
     * @param <T>
     */
    public <T> void dealResponseData(String goodsId, DataResponse<T> data, String come) {
        log.info("码上购-沃厅-商品详情-入参：{}," + come + "接口返回：{}", goodsId, JSON.toJSONString(data));
        if (!"0000".equals(data.getCode())) {
            throw new BusinessException(come + "接口异常");
        }
        if (data.getData() == null) {
            throw new BusinessException(come + "接口返回数据为空");
        }
    }

    public String dealPageUrl(Map map) {
        String pageUrl = null;
        try {
            pageUrl = new String(Base64Utils.decodeBytes(Rmap.getStr(map, "goodsIntroduce")), "GBK");
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException("pageUrl转码错误");
        }
        return pageUrl;
    }

    public Long dealStock(Map map) {
        Long num = 0L;
        List<Map> stockList = JSON.parseObject(JSON.toJSONString(map.get("modelsList")), List.class);
        for (Map stock : stockList) {
            num = num + MapUtils.getLong(stock, "articleAmount");
        }
        return num;
    }

    public String[] dealPhoto(List<Map> list) {
        String[] str = new String[list.size()];
        int i = 0;
        for (Map map : list) {
            str[i] = Rmap.getStr(map, "goodsPhoto");
            i += 1;
        }
        return str;
    }

    public String encryptData(AuthBean authBean) {
        StringBuffer sb = new StringBuffer();
        String data = sb.append("d=").append(authBean.getDevId()).append("&c=").append(authBean.getChannelId())
                .append("&p=").append(authBean.getProvinceCode()).append("&c=").append(authBean.getEparchyCode()).toString();
        log.info("码上购-沃厅-商品详情-账号：{},加密前数据：{},key:{}", authBean.getPhoneNo(), data, aesKey);
        String aesStr = null;
        try {
            AesUtils aesCryptor = new AesUtils(aesKey);
            aesStr = aesCryptor.encrypt(data);
            aesStr = URLEncoder.encode(aesStr, "UTF-8");
        } catch (Exception e) {
            throw new BusinessException("加密错误");
        }
        return aesStr;
    }

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        String data = sb.append("d=").append("18511838352").append("&c=").append("1111af117")
                .append("&p=").append("11").append("&c=").append("110").toString();
        String aesStr = null;
        try {
            AesUtils aesCryptor = new AesUtils("is9Ubm0iH4uMQ0wE");
            aesStr = aesCryptor.encrypt(data);
            aesStr = URLEncoder.encode(aesStr, "UTF-8");
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new BusinessException("加密错误");
        }
        System.out.println(aesStr);
    }

}
