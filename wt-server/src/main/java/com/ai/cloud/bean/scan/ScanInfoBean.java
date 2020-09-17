package com.ai.cloud.bean.scan;

import lombok.Data;

/**
 * 扫码用户信息
 *
 * @author zhaanping
 * @date 2020-03-20
 */
@Data
public class ScanInfoBean {

    /**
     * 推广类型：0：分享；1：扫码；
     * <p>
     * 可用分享及二维码 的 明文参数 s
     */
    private String popularizeType;

    /**
     * 消息渠道类型(暂时没有微信和支付宝)：
     * <p>
     * 0：分享；1：手厅扫码；2：微信扫码；3：支付宝扫码 ；
     * <p>
     * 暂时 可用分享及二维码 的 明文参数 s ，以后有微信和支付宝再看情况。
     */
    private String scanChannelType;

    /**
     * 分享及二维码 的 加密参数 a
     * <p>
     * d：发展人id
     * c：渠道id
     * p：省份
     * e：地市
     * <p>
     * 加密key使用wxpublic.aesKey
     * <p>
     * a=aes.encode(d=发展人id&c=渠道id&p=省份&e=地市)
     */
    private String a;

    /**
     * 手厅 的 加密参数 p（扫码必传）
     * <p>
     * lg: 是否登录 0:未登录 1:已登录
     * sq: 进店码 保证唯一即可
     * no: 手机号 (登录时必传)
     * nm: 昵称 (登录时必传)
     * <p>
     * 加密key使用wxpublic.aesKey
     * <p>
     * 登录
     * p=AES.encode(lg=1&sq=77166616636&no=18511838352&nm=测试)
     * <p>
     * 未登录
     * p=AES.encode(lg=0&sq=77166616636)
     */
    private String p;

    /**
     * 设备品牌
     */
    private String phoneBrand;

    /**
     * 设备型号
     */
    private String phoneType;

    /**
     * 设备Imei（扫码必传）
     */
    private String phoneImei;
}
