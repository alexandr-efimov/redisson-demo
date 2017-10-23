package com.example.reddisontest;

import org.redisson.Redisson;
import org.redisson.RedissonNode;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.RedissonNodeConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexandr.efimov@sigma.software on 10/23/2017.
 */
@org.springframework.context.annotation.Configuration
public class AppConfiguration {

    /**
     * addresses - in host:port format.
     */
    @Value("${redisson.address}")
    private String redisAddress;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useReplicatedServers().addNodeAddress(redisAddress);

        RedissonClient redissonClient = Redisson.create(config);

        RedissonNodeConfig nodeConfig = new RedissonNodeConfig(config);
        Map<String, Integer> workers = new HashMap<String, Integer>();
        workers.put("ex-service", 1);
        workers.put("scheduled-service", 1);
        nodeConfig.setExecutorServiceWorkers(workers);
        Map<String, Integer> executorServiceWorkers = nodeConfig.getExecutorServiceWorkers();
        System.out.println("Workers: " + executorServiceWorkers);

        RedissonNode node = RedissonNode.create(nodeConfig);
        node.start();

        return redissonClient;
    }
}
