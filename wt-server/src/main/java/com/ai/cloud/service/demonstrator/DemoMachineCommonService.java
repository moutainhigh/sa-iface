package com.ai.cloud.service.demonstrator;

import com.ai.cloud.auth.bean.AuthBean;
import com.ai.cloud.bean.demonstrator.OrderLogInfoBean;
import com.ai.cloud.bean.demonstrator.PageQueryBean;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.n3r.eql.Eql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.ai.cloud.constant.DSName.*;
import static com.ai.cloud.constant.DemoMachineConstant.*;

/**
 * 演示机公共 Service
 *
 * @author zhaanping
 * @date 2020-06-11
 */
@Component
@Slf4j
public class DemoMachineCommonService {

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 判断登录人是否为 厅长
     *
     * @param authBean
     * @return
     */
    public boolean isDirector(AuthBean authBean) {
        //查询在缓存中数据
        String key = IS_DIRECTOR_REDIS_KEY + authBean.getPhoneNo();
        String isDirector = redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotEmpty(isDirector)) {
            return IS_DIRECTOR.equals(isDirector);
        }

        int isDirectorCount = new Eql(MSQ).selectFirst("isDirector").params(authBean).returnType(Integer.class).execute();

        redisTemplate.opsForValue().set(key, Integer.toString(isDirectorCount), CACHE_TIME, TimeUnit.SECONDS);
        return 1 == isDirectorCount;
    }

    /**
     * 组装分页查询条件
     *
     * @param bean
     */
    public void buildPageQuery(PageQueryBean bean) {
        String currentPage = StringUtils.isNotEmpty(bean.getReferenceId()) ? bean.getReferenceId() : "1";
        int pageSize = bean.getPageSize() != 0 ? bean.getPageSize() : DEFAULT_PAGE_SIZE;
        int startIndex = (Integer.parseInt(currentPage) - 1) * pageSize;

        bean.setReferenceId(currentPage);
        bean.setPageSize(pageSize);
        bean.setStartIndex(startIndex);
    }

    /**
     * 记录日志
     *
     * @param eql
     * @param logBean
     * @return
     */
    public int saveLogInfo(Eql eql, OrderLogInfoBean logBean) {
        return eql.useSqlFile(DemoMachineCommonService.class).insert("saveLogInfo").params(logBean).returnType(Integer.class).execute();
    }

}
