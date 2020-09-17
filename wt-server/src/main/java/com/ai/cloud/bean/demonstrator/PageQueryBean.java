package com.ai.cloud.bean.demonstrator;

import lombok.Data;

/**
 * 演示机分页查询 实体类
 *
 * @author zhaanping
 * @date 2020-06-12
 */
@Data
public class PageQueryBean {

    /**
     * 渠道编码
     */
    private String channelId;

    /**
     * 基于 referenceId 分页，传下一页页码，每次返回当前页页码，从1开始计数
     */
    private String referenceId;

    /**
     * 开始索引
     */
    private int startIndex;

    /**
     * 每页数量
     */
    private int pageSize;

}
