package com.ai.cloud.cache;

import com.ai.cloud.base.lang.Rmap;
import com.ai.cloud.bean.user.CheckVersionBean;
import com.ai.cloud.service.auth.AuthService;
import com.ai.cloud.service.user.VersionQueryService;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang.StringUtils.isBlank;

@Component
public class VersionCache {
    private static final Splitter splitter = Splitter.on("$").trimResults();
    private static final Joiner joiner = Joiner.on("$").useForNull("");

    public static final Map DEFAULT_VERSION = Rmap.asMap("supportTo", "2.1.4");

    @Autowired
    AuthService authService;


    @Autowired
    VersionQueryService versionQueryService;


    private LoadingCache<String, Map> versionCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build(
                    new CacheLoader<String, Map>() {
                        @Override
                        public Map load(String versionKey) {
                            if (isBlank(versionKey)) {
                                return DEFAULT_VERSION;
                            }
                            List<String> params = splitter.splitToList(versionKey);
                            if (CollectionUtils.isEmpty(params) || params.size() != 3) {
                                return DEFAULT_VERSION;
                            }

                            return authService.getSupportVersion(params.get(0), params.get(1), params.get(2));
                        }
                    });


    /**
     * 最新版本 缓存 30分钟有效
     */
    private LoadingCache<String, CheckVersionBean> recentVersionCache = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build(
                    new CacheLoader<String, CheckVersionBean>() {
                        @Override
                        public CheckVersionBean load(String versionKey) {
                            if (isBlank(versionKey)) {
                                return null;
                            }
                            List<String> params = splitter.splitToList(versionKey);
                            if (CollectionUtils.isEmpty(params) || params.size() != 3) {
                                return null;
                            }

                            return versionQueryService.queryRecentVersion(params.get(0), params.get(1), params.get(2));
                        }
                    });


    public Map getSupportVersion(String suitType, String provinceCode, String eparchyCode) {
        String versionKey = joiner.join(suitType, provinceCode, eparchyCode);
        return versionCache.getUnchecked(versionKey);
    }

    public CheckVersionBean queryRecentVersion(String suitType, String provinceCode, String eparchyCode) {
        String versionKey = joiner.join(suitType, provinceCode, eparchyCode);
        return recentVersionCache.getUnchecked(versionKey);
    }

}
