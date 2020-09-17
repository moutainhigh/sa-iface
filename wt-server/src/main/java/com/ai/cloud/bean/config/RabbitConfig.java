package com.ai.cloud.bean.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author xiaoniu 2018/3/12.
 */
@Component
@RefreshScope
public class RabbitConfig {

    @Value("${ic.rabbit.address}")
    private String address;

    @Value("${ic.rabbit.username}")
    private String username;

    @Value("${ic.rabbit.password}")
    private String password;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}