package com.ai.cloud.client.pojo;

/**
 * 订单序列响应 实体类
 *
 * @author zhaanping
 * @date 2019-10-16
 */
public class OrderSeqRsp {
    /**
     * 订单编码（16位数字）
     */
    private String orderId;

    /**
     * 订单编号（12位数字）
     */
    private String orderNo;

    /**
     * 响应编码
     */
    private String rspCode;

    /**
     * 响应描述
     */
    private String rspDesc;

    /**
     * 流水号
     */
    private String traceId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspDesc() {
        return rspDesc;
    }

    public void setRspDesc(String rspDesc) {
        this.rspDesc = rspDesc;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return "OrderSeqRsp{" +
                "orderId='" + orderId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", rspCode='" + rspCode + '\'' +
                ", rspDesc='" + rspDesc + '\'' +
                ", traceId='" + traceId + '\'' +
                '}';
    }
}
