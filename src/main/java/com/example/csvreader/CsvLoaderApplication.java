package com.example.csvreader;

import com.example.csvreader.service.CsvReaderService;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@RequiredArgsConstructor
@SpringBootApplication
@Slf4j
public class CsvLoaderApplication implements CommandLineRunner {
    private final CsvReaderService csvReaderService;

    public static void main(String[] args) {
        SpringApplication.run(CsvLoaderApplication.class, args);
    }


    @Override
    public void run(String... args) {
        if (args.length < 1) {
            log.error("Не указан путь к CSV-файлу.");
            return;
        }
        String filePath = args[0];
        int chunkSize = args.length >= 3 ? Integer.parseInt(args[2]) : 10000;
        try {
            csvReaderService.loadCsv(filePath, chunkSize);
          } catch (IOException | CsvValidationException e) {
            log.error("Ошибка при загрузке CSV: {}", e.getMessage());
        }
    }


}

