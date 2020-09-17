package com.ai.cloud.bean.scan;

import lombok.Builder;
import lombok.Data;

/**
 * 扫码消息推送 实体类
 *
 * @author zhaanping
 * @date 2020-03-20
 */
@Data
@Builder
public class ScanMessageBean {
    /**
     * 省份编码（渠道归属省份
     */
    private String provinceCode;
    /**
     * 地市编码（渠道归属地市）
     */
    private String cityCode;
    /**
     * 消息原始ID
     * <p>
     * 分享时，需要后台生成
     */
    private String scanMessageId;
    /**
     * 消息时间 格式：yyyy-MM-dd HH:mm:ss
     */
    private String scanMessageTime;
    /**
     * 消息渠道类型：0:分享 1：手厅扫码 2：微信扫码 3：支付宝扫码
     * (暂时没有微信和支付宝)
     */
    private String scanChannelType;
    /**
     * 二维码类型：1：沃店 2：商品
     */
    private String qrCodeType;
    /**
     * 推广类型：1：扫码 2：分享
     */
    private String popularizeType;
    /**
     * 登录状态 0：未登录 1：已登录
     */
    private String loginState;
    /**
     * 客户昵称
     */
    private String custNickName;
    /**
     * 客户联系电话
     */
    private String custMobilePhone;
    /**
     * 客户设备品牌（例：华为）
     */
    private String custPhoneBrand;
    /**
     * 客户设备型号（例：P30）
     */
    private String custPhoneType;
    /**
     * 客户手机唯一码
     */
    private String custPhoneImei;
    /**
     * 扫码商品ID
     * <p>
     * 沃店 可不传
     */
    private String goodsId;
    /**
     * 扫码商品名称
     * <p>
     * 沃店 可不传
     */
    private String goodsName;
    /**
     * 扫码商品图标（列表用小图标）
     * <p>
     * 沃店 可不传
     */
    private String goodsIcon;
    /**
     * 二维码沃店渠道ID
     */
    private String channelId;
    /**
     * 二维码发展人ID
     */
    private String scanDeveloperId;
}
