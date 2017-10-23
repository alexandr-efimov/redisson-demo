package com.example.reddisontest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by alexandr.efimov@sigma.software on 10/23/2017.
 */
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {

    private final RedissonClient redissonClient;

    @GetMapping("/executionservice")
    public void testExecutionService() throws ExecutionException, InterruptedException {
        log.info("Executor service test");

        RExecutorService executorService = redissonClient.getExecutorService("ex-service");
        RExecutorFuture<?> future = executorService.submit(new RunnableTask());
        log.info("Executor service, future: {}", future);
    }

    @GetMapping("/scheduledexecutionservice")
    public void testScheduledExecutionService() {
        log.info("Scheduled executor service test");

        RScheduledExecutorService executorService = redissonClient.getExecutorService("scheduled-service");
        RScheduledFuture<?> rScheduledFuture = executorService.scheduleAsync(new RunnableTask(), 5, TimeUnit.SECONDS);
        log.info("Scheduled service, future: {}", rScheduledFuture);
    }
}
