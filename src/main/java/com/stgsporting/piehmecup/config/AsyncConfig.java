package com.stgsporting.piehmecup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // Core number of threads
        executor.setMaxPoolSize(20); // Maximum number of threads
        executor.setQueueCapacity(50); // Queue capacity
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }
}