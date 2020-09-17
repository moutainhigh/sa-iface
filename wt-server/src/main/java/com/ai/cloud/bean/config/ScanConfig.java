package com.ai.cloud.bean.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 扫码配置类
 *
 * @author zhaanping
 * @date 2020-03-21
 */
@Configuration
@RefreshScope
@Data
public class ScanConfig {

    @Value("${wxpublic.aesKey}")
    private String aesKey;

    @Value("${env.test:false}")
    private Boolean testEnv;
}
