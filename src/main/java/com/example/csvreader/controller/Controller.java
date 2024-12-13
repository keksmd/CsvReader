package com.example.csvreader.controller;

import com.example.csvreader.service.CsvReaderService;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/csv/reader")
@RestController
@Slf4j
@RequiredArgsConstructor
public class Controller {
    private final CsvReaderService csvReaderService;
    private final ThreadPoolTaskExecutor poolTaskExecutor;

    @GetMapping()
    public void run(@RequestParam String filePath, @RequestParam int chunkSize, @RequestParam int threadCount) {
        poolTaskExecutor.setCorePoolSize(threadCount);
        poolTaskExecutor.setMaxPoolSize(threadCount);
        try {
            csvReaderService.loadCsv(filePath, chunkSize);
        } catch (IOException | CsvValidationException e) {
            log.error("Ошибка при загрузке CSV: {}", e.getMessage());
        }
    }
}
