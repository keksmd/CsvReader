// src/main/java/com/example/csvreader/config/ExecutorTestConfig.java
package com.example.csvreader.c;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;

@Slf4j
@TestConfiguration
public class ExecutorTestConfig {


    @Bean
    @Profile("test")
    public ExecutorService csvTestLoaderExecutor() {
        int threads = 4; // Значение по умолчанию



        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threads);
        executor.setMaxPoolSize(threads);
        executor.setThreadNamePrefix("CsvLoader-");
        executor.initialize();

        return executor.getThreadPoolExecutor();
    }
}
