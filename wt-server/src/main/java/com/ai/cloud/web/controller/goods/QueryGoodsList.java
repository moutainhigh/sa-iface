package com.ai.cloud.web.controller.goods;

import com.ai.cloud.auth.annotation.Auth;
import com.ai.cloud.auth.bean.AuthBean;
import com.ai.cloud.auth.component.impl.MsgoAuth;
import com.ai.cloud.base.exception.AppException;
import com.ai.cloud.bean.base.BaseRsp;
import com.ai.cloud.bean.goods.GoodsInfo;
import com.ai.cloud.bean.goods.GoodsSearchReq;
import com.ai.cloud.bean.goods.GoodsSearchRsp;
import com.ai.cloud.service.goods.QueryGoodsListService;
import com.ai.cloud.service.index.WtIndexService;
import com.ai.cloud.tool.AesUtils;
import com.ai.cloud.tool.RspHelp;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
public class QueryGoodsList {

    @Autowired
    MsgoAuth msgoAuth;

    @Autowired
    QueryGoodsListService service;

    @Autowired
    WtIndexService initService;

    @Autowired
    RspHelp rspHelp;

    @Auth
    @RequestMapping(value = "/wo/goodsList")
    public BaseRsp queryGoodsList(HttpServletRequest req, GoodsSearchReq searchBean) {

        AuthBean authBean = msgoAuth.getUserInfo(req);
        dealSearchBean(authBean.getProvinceCode(), searchBean);

        searchBean = service.switchParam(searchBean);

        List<GoodsInfo> goodsList = service.queryAllGoods(searchBean);

        GoodsSearchRsp rsp = new GoodsSearchRsp();

        dealResponse(authBean, rsp);
        rsp.setGoodsList(goodsList);

        return rspHelp.success(rsp);
    }

    @RequestMapping(value = "/wo/goodsListOt")
    public BaseRsp queryGoodsListOt(HttpServletRequest req, GoodsSearchReq searchBean) {

        dealDevInfo(searchBean);

        dealSearchBean(searchBean.getProvinceCode(), searchBean);

        searchBean = service.switchParam(searchBean);

        List<GoodsInfo> goodsList = service.queryAllGoods(searchBean);

        GoodsSearchRsp rsp = new GoodsSearchRsp();

        rsp.setGoodsList(goodsList);

        return rspHelp.success(rsp);
    }

    private void dealDevInfo(GoodsSearchReq searchBean) {
        String aesStr = searchBean.getA();

        if(StringUtils.isEmpty(aesStr)) {
            log.error("发展人参数为空,参数:{}", searchBean);
            throw new AppException("发展人参数为空");
        }

        String devStr = service.decodeAes(aesStr);

        if(StringUtils.isEmpty(devStr)) {
            log.error("发展人解密失败,参数:{}", searchBean);
            throw new AppException("发展人解密失败");
        }


        if(4 != devStr.split("&").length) {
            log.error("发展人参数格式有误,参数:{},发展人:{}", searchBean, devStr);
            throw new AppException("发展人参数格式有误");
        }

        String provinceCode = devStr.split("&")[2].split("=")[1];
        String cityCode = devStr.split("&")[3].split("=")[1];
        String channelId = devStr.split("&")[1].split("=")[1];
        searchBean.setProvinceCode(provinceCode);

        // 判断渠道是否已配置沃厅
        if(!initService.isWotingOpen(provinceCode, cityCode, channelId)) {
            log.error("渠道未配置沃厅,参数:{}", devStr);
            throw new AppException("渠道未配置云厅");
        }

        // 判断所属地市是否已发布推广页面
        if(!initService.isWtPageOpen(provinceCode, cityCode)) {
            log.error("所属地市未发布推广页面,参数:{}", devStr);
            throw new AppException("所属地市未发布推广页面");
        }
    }

    private void dealResponse(AuthBean authBean, GoodsSearchRsp rsp) {
        rsp.setProvinceCode(authBean.getProvinceCode());
        rsp.setCityCode(authBean.getEparchyCode());
        rsp.setChannelId(authBean.getChannelId());
        rsp.setChannelName(authBean.getChannelName());
        rsp.setDevId(authBean.getDevId());
        rsp.setDevName(authBean.getDevName());
        rsp.setPersonIcon(service.getPersonIcon(authBean.getUid()));
        rsp.setA(service.getAesDev(authBean.getProvinceCode(),authBean.getEparchyCode(),authBean.getDevId(), authBean.getChannelId()));
    }

    private void dealSearchBean(String province, GoodsSearchReq searchBean) {

        log.info("商品搜索服务-请求参数:{}", JSON.toJSONString(searchBean));

        if(StringUtils.isNotEmpty(searchBean.getKeyword())) {
            searchBean.setKeyword(searchBean.getKeyword().trim());
            searchBean.setBusItem("");
            searchBean.setTermBrand("");
            searchBean.setTermType("");
        }

        searchBean.setProvinceCode(province);
        searchBean.setPriceSort(StringUtils.isEmpty(searchBean.getPriceSort())? "0" : searchBean.getPriceSort());
        searchBean.setPageSize(StringUtils.isEmpty(searchBean.getPageSize())? "10" : searchBean.getPageSize());
        searchBean.setPageNum(StringUtils.isEmpty(searchBean.getPageNum())? "1" : searchBean.getPageNum());

    }
}