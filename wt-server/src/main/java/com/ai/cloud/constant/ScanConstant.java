package com.ai.cloud.constant;

import com.ai.cloud.base.lang.Rmap;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * 扫码 常量类
 *
 * @author zhaanping
 * @date 2020-03-21
 */
public interface ScanConstant {

    /**
     * 热门搜索 redis key
     */
    String POPULAR_SEARCH_KEY = "wt_popularSearch_";
    /**
     * 缓存时间
     */
    int CACHE_TIME = 300;

    /**
     * 解密类型
     */
    interface DecryptType {
        String DEV_INFO = "1";
        String ST_SCAN_INFO = "2";
        Map<String, String> TYPE_MAP = Rmap.asMap(DEV_INFO, "发展人信息", ST_SCAN_INFO, "手厅扫码信息");
    }

    /**
     * 返回编码
     * 0000：操作成功；9999：系统异常；
     * 3001：请求参数错误；4001：沃厅开通权限，校验异常；
     * 3002：解密异常；3003：发展人信息错误；
     * 3004：手厅扫码信息错误；3005：推送扫码信息异常；
     */
    interface RspCode {
        String SUCCESS = "0000";
        String SYS_ERR = "9999";
        String REQ_PARAM_ERR = "3001";
        String WT_AUTH_ERR = "4001";

        String DECRYPT_ERR = "3002";
        String DEV_INFO_ERR = "3003";
        String ST_INFO_ERR = "3004";
        String SEND_MSG_ERR = "3005";
    }

    /**
     * 消息渠道类型：0:分享 1：手厅扫码 2：微信扫码 3：支付宝扫码
     */
    interface ScanChannelType {
        String SHARE = "0";
        String ST_QR = "1";
        String WX_QR = "2";
        String ZFB_QR = "3";
        List<String> SCAN_CHANNEL_TYPE_LIST = Lists.newArrayList(SHARE, ST_QR, WX_QR, ZFB_QR);
    }

    /**
     * 二维码类型：1：沃店 2：商品
     */
    interface QrCodeType {
        String WO_MART = "1";
        String GOODS = "2";
        List<String> QR_CODE_TYPE_LIST = Lists.newArrayList(WO_MART, GOODS);
    }

    /**
     * 推广类型：
     * 前端传过来：0：分享；1：扫码；
     * 推送消息时：2：分享；1：扫码；
     */
    interface PopularizeType {
        String SHARE = "0";
        String QR = "1";
        List<String> POPULARIZE_TYPE_LIST = Lists.newArrayList(SHARE, QR);
        // 推送消息时，需要转换下
        String MSG_SHARE = "2";
        String MSG_QR = "1";
        Map<String, String> MSG_POPULARIZE_TYPE_MAP = Rmap.asMap(SHARE, MSG_SHARE, QR, MSG_QR);
    }

    /**
     * 登录状态 0：未登录 1：已登录
     */
    interface LoginState {
        String NO = "0";
        String YES = "1";
        List<String> LOGIN_STATE_LIST = Lists.newArrayList(NO, YES);
    }
}
