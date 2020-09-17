package com.ai.cloud.base.boot;

import com.ai.cloud.auth.interceptor.MsgoAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

    @Bean
    public MsgoAuthInterceptor getMsgoAuthInter() {
        return new MsgoAuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getMsgoAuthInter()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
