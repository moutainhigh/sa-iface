package com.ai.cloud.bean.goods;

import lombok.Data;

import java.util.List;

@Data
public class GoodsSearchRsp {

    private String provinceCode;

    private String cityCode;

    private String devId;

    private String devName;

    private String channelName;

    private String channelId;

    private String personIcon;

    private List<GoodsInfo> goodsList;

    private String a;

}
