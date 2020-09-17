package com.ai.cloud.client.pojo.goods;

import lombok.Data;

/**
 * @author ：yandq
 * @description：
 * @date ：Born in 2019/10/10 19:59
 */
@Data
public class ProtocolInfoReq {

    private String goodsId;

    private String cityCode;

    public ProtocolInfoReq(String goodsId,String cityCode){
        this.cityCode = cityCode;
        this.goodsId = goodsId;
    }
}
