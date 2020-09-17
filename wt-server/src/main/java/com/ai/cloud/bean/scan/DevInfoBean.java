package com.ai.cloud.bean.scan;

import lombok.Builder;
import lombok.Data;

/**
 * 发展人信息 实体类
 *
 * @author zhaanping
 * @date 2020-03-21
 */
@Data
@Builder
public class DevInfoBean {

    /**
     * 省份编码
     */
    private String provinceCode;
    /**
     * 地市编码
     */
    private String cityCode;
    /**
     * 渠道id
     */
    private String channelId;
    /**
     * 发展人id
     */
    private String developerId;

}
