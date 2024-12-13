
package com.example.csvreader.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;

@Slf4j
@Configuration
public class ExecutorConfig {

    @Bean
    @Profile("dev")
    public ThreadPoolTaskExecutor csvLoaderExecutor() {
        int threads = 4; // Значение по умолчанию
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threads);
        executor.setMaxPoolSize(threads);
        executor.setThreadNamePrefix("CsvLoader-");
        executor.initialize();
        return executor;
    }

}
