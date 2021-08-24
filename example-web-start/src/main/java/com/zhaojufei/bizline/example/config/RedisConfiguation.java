package com.zhaojufei.bizline.example.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.util.JedisURIHelper;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * 操作Redis的配置类
 * </ul>
 *
 * @author 陈立
 */
@Configuration
@ConditionalOnProperty(name = "takeout.sophon.conf.redis.flag", havingValue = "true", matchIfMissing = false)
@Slf4j
public class RedisConfiguation {
    // 最大连接数
    @Value(value = "${redis.pool.maxTotal:8}")
    private int maxTotal;

    //  最大空闲连接数
    @Value(value = "${redis.pool.maxIdle:8}")
    private int maxIdle;

    //    初始化连接数
    @Value(value = "${redis.pool.minIdle:1}")
    private int minIdle;

    //   最大等待时间
    @Value(value = "${redis.pool.maxWaitMillis:5000}")
    private int maxWaitMillis;

    // 对拿到的connection进行validateObject校验
    @Value(value = "${redis.pool.testOnBorrow:true}")
    private boolean testOnBorrow;

    @Value(value = "${redis.master.urls}")
    private String urls;

    @Value(value = "${redis.master.timeout}")
    private int timeout;


    /**
     * 创建redisTemplate
     * 所有序列化k/v都会默认使用{@link org.springframework.data.redis.serializer.JdkSerializationRedisSerializer}
     * 默认的序列化类用使用byte数组进行存储， 在使用redis可视化客户端看到都是乱码不直观
     * 使用StringRedisSerializer序列化Key，  Jackson2JsonRedisSerializer序列化值， 可以观察到对象结构层次
     *
     * @return
     */
    @Bean
    @Qualifier("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        Jackson2JsonRedisSerializer<Object> valueSerializer = jackson2JsonRedisSerializer();
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);
        return redisTemplate;
    }

    /**
     * 定义序列化类
     *
     * @return
     */
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory connectionFactory = null;
        try {
            connectionFactory = new JedisConnectionFactory(jedisPoolConfig());
            URI uri = new URI(urls);
            connectionFactory.setHostName(uri.getHost());
            connectionFactory.setPort(uri.getPort());
            connectionFactory.setDatabase(JedisURIHelper.getDBIndex(uri));
            connectionFactory.setPassword(JedisURIHelper.getPassword(uri));
        } catch (Exception e) {
            log.error("Redis连接出现异常", e);
        }
        return connectionFactory;
    }

    @Bean
    public JedisPool jedisPool() {
        log.info("redis_urls:{}", urls);
        JedisPool pool = null;
        try {
            URI uri = new URI(urls);
            pool = new JedisPool(jedisPoolConfig(), uri, timeout);
            return pool;
        } catch (URISyntaxException e) {
            log.error("url配置错误，{}", e);
        }
        return pool;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setTestOnBorrow(testOnBorrow);
        return config;
    }


}
