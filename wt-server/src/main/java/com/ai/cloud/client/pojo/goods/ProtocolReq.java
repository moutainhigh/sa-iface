package com.ai.cloud.client.pojo.goods;


import lombok.Data;


/**
 * @author ：yandq
 * @description：请求协议参数
 * @date ：Born in 2019/10/9 20:12
 */
@Data
public class ProtocolReq {

    private String provinceCode;
    private String cityCode;
    private String activityType;
    private String machineType = "01";
    private String resourceType = "A000004V000002";
    private String netType = "A000003V000003";
}
