package com.ai.cloud.service.homepage;

import com.ai.cloud.base.exception.BusinessException;
import com.ai.cloud.base.lang.Rmap;
import com.ai.cloud.bean.goods.GoodsInfo;
import com.ai.cloud.bean.goods.GoodsSearchReq;
import com.ai.cloud.service.goods.QueryGoodsListService;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.n3r.eql.Eql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ai.cloud.constant.DSName.MSQ;

@Service

public class HomePageService {
    private static final Logger logger = LoggerFactory.getLogger(HomePageService.class);

    private static final String ALL_CITYS = "000";
    private static final String CHANNEL_NAME_KEY = "wo_channel_name";

    @Autowired
    QueryGoodsListService queryGoodsListService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public Map queryPageInfo(String provinceCode, String cityCode, String channelId) {
        logger.info("沃厅-首页查询开始-省份:{},地市", provinceCode, cityCode);
        //查询首页信息
        Map allPageData = queryPageData(provinceCode);
        if (MapUtils.isEmpty(allPageData)) {
            logger.info("沃厅-首页查询为空-省份:{}", provinceCode);
            return Maps.newHashMap();
        }
        List<String> allCitys = Splitter.on("|").trimResults().splitToList(Rmap.getStr(allPageData, "applyArea"));

        if (!allCitys.contains(cityCode) && !allCitys.contains(ALL_CITYS)) {
            logger.info("沃厅-首页查询-该地市无权限-省份:{},地市:{}", provinceCode, cityCode);
            throw new BusinessException("0001", "未开通云厅推广页面");
        }

        //整合首页信息
        Map dataInfo = dealData(allPageData, provinceCode, cityCode, channelId);
        return dataInfo;
    }

    public Map dealData(Map dataMap, String provinceCode, String cityCode, String channelId) {
        //用于存储轮播图信息
        List<Map> bannerList = dealBannerData(provinceCode, Rmap.getStr(dataMap, "conFig"), cityCode);
        logger.info("沃厅-首页查询-banner信息:{},省份:{},地市:{}", JSON.toJSONString(bannerList), provinceCode, cityCode);
        //用于存储首页分类信息
        List<Map> homePageList = homePageSorted(dataMap, provinceCode);
        logger.info("沃厅-首页查询-首页分类信息:{},省份:{},地市:{}", JSON.toJSONString(homePageList), provinceCode, cityCode);
        //用于存储广告位
        Map adMap = Maps.newHashMap();
        adMap.put("adPicture", Rmap.getStr(dataMap, "adPicture"));
        adMap.put("adUrl", Rmap.getStr(dataMap, "adUrl"));
        logger.info("沃厅-首页查询-广告位:{},省份:{},地市:{}", JSON.toJSONString(adMap), provinceCode, cityCode);
        //商品信息
        List<GoodsInfo> goodsList = queryGoodsInfo(provinceCode, Rmap.getStr(dataMap, "goodsId"), cityCode);
        logger.info("沃厅-首页查询-商品信息:{},省份:{},地市:{}", JSON.toJSONString(goodsList), provinceCode, cityCode);
        //渠道名称
        String channelName = queryChannelName(channelId, provinceCode, cityCode);
        logger.info("沃厅-首页查询-渠道名称:{},省份:{},地市:{}", channelName, provinceCode, cityCode);
        return Rmap.asMap("bannerList", bannerList, "homePageList", homePageList, "adMap", adMap,
                "goodsList", goodsList, "channelName", channelName);
    }

    public String queryChannelName(String channelId, String provinceCode, String cityCode) {
        String key = CHANNEL_NAME_KEY + channelId;
        String channelName = redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(channelName)) {
            logger.info("沃厅-首页查询-渠道名称(缓存):{},省份:{},地市:{}", channelName, provinceCode, cityCode);
            return channelName;
        }
        //渠道名称
        channelName = new Eql().selectFirst("queryChannelName").params(channelId, provinceCode).returnType(String.class).execute();
        redisTemplate.opsForValue().set(key, channelName, 6, TimeUnit.HOURS);
        return channelName;
    }

    public List<GoodsInfo> queryGoodsInfo(String provinceCode, String goodsId, String cityCode) {
        GoodsSearchReq reqBean = new GoodsSearchReq();
        reqBean.setGoodsId(goodsId.replace("|", ","));
        reqBean.setProvinceCode(provinceCode);
        logger.info("沃厅-首页查询-商品信息参数:{},省份:{},地市:{}", reqBean.getGoodsId(), provinceCode, cityCode);
        List<GoodsInfo> goodsInfo = queryGoodsListService.queryAllGoods(reqBean);
        logger.info("沃厅-首页查询-商品信息:{},省份:{},地市:{}", JSON.toJSONString(goodsInfo), provinceCode, cityCode);
        if (CollectionUtils.isEmpty(goodsInfo)) {
            logger.info("沃厅-首页查询-商品信息为空-省份:{},地市:{}", provinceCode, cityCode);
            return Lists.newArrayList();
        }
        return goodsInfo;
    }

    public List<Map> dealBannerData(String provinceCode, String configId, String cityCode) {
        logger.info("沃厅-首页查询-轮播图信息-configId:{},地市:{}", configId, cityCode);
        List<Map> dataList = new Eql(MSQ).select("queryBannerInfo").params(configId).returnType(Map.class).execute();
        logger.info("沃厅-首页查询-轮播图信息:{},省份:{}", JSON.toJSONString(dataList), provinceCode);
        if (CollectionUtils.isEmpty(dataList)) {
            logger.info("沃厅-首页查询-轮播图信息为空-省份:{},地市:{}", provinceCode, cityCode);
            return Lists.newArrayList();
        }
        return dataList;
    }

    public List<Map> homePageSorted(Map homePage, String provinceCode) {
        List<String> pageSorted = Splitter.on("|").trimResults().splitToList(Rmap.getStr(homePage, "homePagSorted"));
        logger.info("沃厅-首页查询-首页的分类:{},省份:{}", JSON.toJSONString(pageSorted), provinceCode);
        List<Map> pageSortedList = new Eql().select("queryPageSorted").params(Rmap.asMap("pageSortedList", pageSorted)).
                returnType(Map.class).execute();
        logger.info("沃厅-首页查询-首页的分类(叙述):{},省份:{}", JSON.toJSONString(pageSorted), provinceCode);
        return pageSortedList;
    }

    public Map queryPageData(String provinceCode) {
        Map pageDataList = new Eql(MSQ).selectFirst("queryPageInfo").params(provinceCode).returnType(Map.class).execute();
        logger.info("沃厅-首页查询-查询的数据:{},省份:{}", JSON.toJSONString(pageDataList), provinceCode);
        return pageDataList;
    }
}
