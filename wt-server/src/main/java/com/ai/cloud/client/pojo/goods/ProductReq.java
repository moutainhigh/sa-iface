package com.ai.cloud.client.pojo.goods;


import lombok.Data;

import java.util.List;

/**
 * @author wzx
 * @date 2019/10/12 11:18
 */
@Data
public class ProductReq {
    private String goodsId;

    private List<String> attrList;

}
