package com.ai.cloud.bean.demonstrator;

import lombok.Data;

/**
 * 订单查询 实体类
 *
 * @author zhaanping
 * @date 2020-06-12
 */
@Data
public class OrderQueryBean extends PageQueryBean {

    /**
     * 申请模式：01：厂商配置；02：折扣买断；03：租赁租用；
     */
    private String applyMode;

    /**
     * 申请时间排序（0：降序；1：升序；为空默认降序排列）
     */
    private String applyTimeSort;

    /**
     * 申请单状态：1：地市待审核；2：地市审核通过；3：地市审核驳回；4：省份审核通过；5：省份审核驳回；6：已完成；
     */
    private String orderState;

}
