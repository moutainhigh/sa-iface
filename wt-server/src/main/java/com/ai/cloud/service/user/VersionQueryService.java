package com.ai.cloud.service.user;

import com.ai.cloud.bean.user.CheckVersionBean;
import com.ai.cloud.bean.user.VersionBean;
import org.n3r.eql.Eql;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Geafan
 * @Date: 2018/3/22 15:49
 */
@Component
public class VersionQueryService {

    /**
     * 根据机型查询最新发布版本
     *
     * @param suitType
     * @param provinceCode
     * @param eparchyCode
     * @return
     */
    public CheckVersionBean queryRecentVersion(String suitType, String provinceCode, String eparchyCode) {
        return new Eql().params(suitType, provinceCode, eparchyCode).selectFirst("queryRecentVersion").returnType(CheckVersionBean.class).execute();
    }

    /**
     * 根据版本id和机型查询当前版本详情
     *
     * @param versionBean
     * @return
     */
    public VersionBean queryFunction(VersionBean versionBean) {
        return new Eql().params(versionBean).selectFirst("queryFunction").returnType(VersionBean.class).execute();
    }

    /**
     * 查询功能介绍列表
     * @param suitType
     * @return
     */
    public List<VersionBean> queryFuncList(String suitType) {
        return new Eql().params(suitType).select("queryFunctionList").returnType(VersionBean.class).execute();
    }
}
