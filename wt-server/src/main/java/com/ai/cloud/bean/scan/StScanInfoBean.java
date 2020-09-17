package com.ai.cloud.bean.scan;

import lombok.Builder;
import lombok.Data;

/**
 * 手厅扫码信息 实体类
 *
 * @author zhaanping
 * @date 2020-03-21
 */
@Data
@Builder
public class StScanInfoBean {

    /**
     * 登录状态 0：未登录 1：已登录
     */
    private String loginState;
    /**
     * 消息原始ID
     */
    private String scanMessageId;
    /**
     * 客户联系电话
     */
    private String custMobilePhone;
    /**
     * 客户昵称
     */
    private String custNickName;

}
