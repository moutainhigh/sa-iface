package com.ai.cloud.client.pojo.goods;

import lombok.Data;

import java.util.List;

@Data
public class SalesListReq {
    private List<String> salesIds;
    private String provinceCode;
}
