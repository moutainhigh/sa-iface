package com.ai.cloud.web.controller.search;

import com.ai.cloud.base.exception.BusinessException;
import com.ai.cloud.base.lang.Rmap;
import com.ai.cloud.bean.base.BaseRsp;
import com.ai.cloud.constant.RspCodeEnum;
import com.ai.cloud.service.search.PopularSearchService;
import com.ai.cloud.tool.RspHelp;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 热门搜索 Controller
 *
 * @author zhaanping
 * @date 2020-03-19
 */
@Slf4j
@RestController
public class PopularSearchController {

    @Autowired
    PopularSearchService popularSearchService;

    @Autowired
    RspHelp rspHelp;

    /**
     * 热门搜索
     *
     * @param a 分享及二维码 的 加密参数a，例子：a=aes.encode(d=发展人id&c=渠道id&p=省份&e=地市)
     * @return
     */
    @RequestMapping("/wt/popularSearch")
    public BaseRsp popularSearch(@RequestParam("a") String a) {
        try {
            if (StringUtils.isEmpty(a)) {
                return rspHelp.fail(RspCodeEnum.PARAM_ERROR.getCode(), "请求参数不能为空");
            }

            List<Map> psInfoList = popularSearchService.popularSearch(a);
            log.info("码上购-沃厅-查询热门搜索，请求参数：{}，热门搜索：{}", a, JSON.toJSONString(psInfoList));

            return rspHelp.success(Rmap.asMap("psInfoList", psInfoList));
        } catch (BusinessException be) {
            log.error("码上购-沃厅-查询热门搜索，请求参数：{}，业务异常：{}|{}", a, be.getMessageCode(), be.getMessage());
            return rspHelp.fail(be.getMessageCode(), be.getMessage());
        } catch (Exception e) {
            log.error("码上购-沃厅-查询热门搜索，请求参数：{}，异常", a, e);
            return rspHelp.fail(RspCodeEnum.ERROR.getCode(), "系统异常");
        }
    }
}
