package com.ai.cloud.base.boot;

import com.ai.cloud.bean.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoniu 2018/3/5.
 */
@Configuration
public class RedisClusterConfig {

    @Autowired
    private RedisConfig redisConfig;


    /**
     * redisCluster配置
     *
     * @return
     */
    @Bean
    public RedisClusterConfiguration redisClusterConfiguration() {
        Map<String, Object> source = new HashMap<String, Object>();
        source.put("spring.redis.cluster.nodes", redisConfig.getNodes());
        source.put("spring.redis.cluster.timeout", redisConfig.getConnectionTimeout());
        return new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
    }

    /**
     * jedis 连接池
     * @return
     */
    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfig.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getMaxWaitMills());
        jedisPoolConfig.setMinIdle(redisConfig.getMinIdle());
        jedisPoolConfig.setTestOnBorrow(redisConfig.isTestOnBorrow());
        jedisPoolConfig.setTestOnReturn(redisConfig.isTestOnReturn());
        return jedisPoolConfig;
    }

    @Bean
    public
    RedisConnectionFactory connectionFactory() {

        JedisConnectionFactory rc = new JedisConnectionFactory(redisClusterConfiguration(), jedisPoolConfig());
        rc.setPassword(redisConfig.getPasswd());
        return rc;
    }

    @Bean
    RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        // just used StringRedisTemplate for simplicity here.
        return new StringRedisTemplate(factory);
    }


}
