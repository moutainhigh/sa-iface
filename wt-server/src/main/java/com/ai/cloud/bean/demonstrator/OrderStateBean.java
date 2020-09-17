package com.ai.cloud.bean.demonstrator;

import lombok.Data;

import java.util.List;

/**
 * @author dingjp 2020/6/15
 */
@Data
public class OrderStateBean {

    private String rspCode;
    private String rspDesc;
    private String orderId;
    private String uuid;
    private List<String> goodsInfo;
    private String orderState;
    private String amount;
    private String lgtsNo;
    private String lgtsCompanyCode;
    private String operateName;
    private String operatePhone;
    private String operateTime;
}
