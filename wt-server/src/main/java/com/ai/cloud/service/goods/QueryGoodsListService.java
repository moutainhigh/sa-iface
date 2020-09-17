package com.ai.cloud.service.goods;

import com.ai.cloud.bean.goods.GoodsInfo;
import com.ai.cloud.bean.goods.GoodsSearchReq;
import com.ai.cloud.bean.goods.SearchRsp;
import com.ai.cloud.tool.AesUtils;
import com.ai.cloud.tool.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.n3r.eql.Eql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class QueryGoodsListService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${goods.search.url:http://132.38.2.88/SearchApp/woGoodsSetList/search}")
    private String reqUrl;

    //头像url
    @Value("${res.avatorUrlPrefix:}")
    private String avatorUrlPrefix;

    @Value("${wxpublic.aesKey}")
    private String aesKey;

    @Value("${env.test:false}")
    private boolean isTest;

    private static final String SEAH_KEY_SWITH = "search_key_swith_all";

    private static final String NAMESPACE_ROOT = "msgo2:";

    private static final String AVATOR_SUFFIX = "jpeg";

    private static final String RES_PREFIX_DEMO = "http://demo.mall.10010.com:8104/test/mall/res";

    private static final String RES_PREFIX = "https://res.mall.10010.cn/mall/res";

    /**
     * 头像版本号
     */
    private static final String KEY_USER_AVATOR_VERSION = NAMESPACE_ROOT + "user:avator:version";

    public List<GoodsInfo> queryAllGoods(GoodsSearchReq reqBean) {

        try {
            Response response = HttpUtil.toJsonStringPostSend(JSON.toJSONString(reqBean), reqUrl, 3).execute();

            if (!response.isSuccessful()) {
                return null;
            }
            String resp = response.body().string();

            log.info("商品搜索服务-请求参数:{}，请求地址:{}，返回：{}", JSON.toJSONString(reqBean), reqUrl, resp);

            SearchRsp goodsRsp = JSON.parseObject(resp, SearchRsp.class);

            if (!"0000".equals(goodsRsp.getRspCode())) {
                return null;
            }

            List<GoodsInfo> goodsItem = goodsRsp.getGoodsItems();

            goodsItem.stream().forEach(a -> a.setPhotoLink((isTest ? RES_PREFIX_DEMO : RES_PREFIX).concat(a.getPhotoLink())));

            return goodsItem;

        } catch (Exception e) {
            log.info("商品搜索服务-请求参数:{}，异常:{}", e);
            return null;
        }
    }

    public GoodsSearchReq switchParam(GoodsSearchReq searchBean) {

        if(StringUtils.isEmpty(searchBean.getKeyword())) {
            return searchBean;
        }

        List<Map> switchMap = getSwitchParam();

        Optional<Map> optional = switchMap.stream().filter(a -> MapUtils.getString(a, "para_code2").equals(searchBean.getKeyword())).findFirst();

        optional.ifPresent(a -> {
            String type = MapUtils.getString(a, "para_code5");
            String keyword = MapUtils.getString(a, "para_code2");
            if("termType".equals(type)) {
                searchBean.setTermType(keyword);
                searchBean.setKeyword("");

            }else if("termBrand".equals(type)) {
                searchBean.setTermBrand(keyword);
                searchBean.setKeyword("");

            }else if("busItem".equals(type)) {
                searchBean.setBusItem(keyword);
                searchBean.setKeyword("");

            }
        });
        return searchBean;
    }



    /**
     * 查询菜单表
     */
    public List<Map> getSwitchParam() {

        //查询在缓存中数据
        String redisMenuListStr = redisTemplate.opsForValue().get(SEAH_KEY_SWITH);
        if (StringUtils.isNotEmpty(redisMenuListStr)) {
            return JSON.parseArray(redisMenuListStr, Map.class);

        }
        List<Map> keywordMap = new Eql().select("queryKeywordMap").returnType(Map.class).execute();
        //数据写入缓存并设置失效时间为1800秒
        redisTemplate.opsForValue().set(SEAH_KEY_SWITH, JSON.toJSONString(keywordMap), 1800, TimeUnit.SECONDS);
        return keywordMap;
    }

    public static void main(String[] args) throws Exception{

        AesUtils aesCryptor = new AesUtils("yIBkJwo3ZQ5yxm6z");
        String encode = "wkZMKQFpviVwlimdY37EpPhTQ9TsaVq6O3pA2OaZqAHY+VZA3gHDsVt4xd3nsc0J";
        String aesStr = URLDecoder.decode(encode, "UTF-8").replace(" ", "+");
        System.out.println(aesCryptor.decrypt(aesStr));
        System.out.println(URLEncoder.encode(encode, "UTF-8"));
    }

    public String getPersonIcon(String uid) {
        String version = getUserAvatorVersion(uid);
        if (StringUtils.isBlank(version)) {
            return "";
        }
        return StringUtils.removeEnd(avatorUrlPrefix, File.separator) + File.separator + getAvatorName(uid) + "?v=" + version;

    }


    /**
     * 获取头像版本号码
     *
     * @param uid
     * @return
     */
    public String getUserAvatorVersion(String uid) {
        try {
            return (String) redisTemplate.opsForHash().get(KEY_USER_AVATOR_VERSION, uid);
        } catch (Exception e) {
            return null;
        }
    }

    private String getAvatorName(String uid) {
        return uid + "." + AVATOR_SUFFIX;
    }

    public String getAesDev(String provinceCode, String eparchyCode, String devId, String channelId) {
        try{
            AesUtils aesCryptor = new AesUtils(aesKey);
            String devStr = "d=".concat(devId).concat("&c=").concat(channelId).concat("&p=").concat(provinceCode).
                    concat("&e=").concat(eparchyCode);
            String aesStr = aesCryptor.encrypt(devStr);
            return URLEncoder.encode(aesStr, "UTF-8");
        }catch (Exception e) {
            log.error("商品搜索服务-加密失败:{}", e);
            return "";
        }

    }

    public String decodeAes(String aesStr) {
        try{
            AesUtils aesCryptor = new AesUtils(aesKey);

            return aesCryptor.decrypt(URLDecoder.decode(aesStr,"UTF-8").replace(" ", "+"));
        }catch (Exception e) {
            log.error("商品搜索服务-解密失败:{}", e);
            return "";
        }
    }

}
