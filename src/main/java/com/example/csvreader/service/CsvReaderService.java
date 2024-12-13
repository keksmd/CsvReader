package com.example.csvreader.service;


import com.example.csvreader.data.dto.EntityDto;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Slf4j
@RequiredArgsConstructor
@Service
public class CsvReaderService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private final EntityLoaderService entityLoaderService;
    private final CSVParser parser;
  private final ThreadPoolTaskExecutor executorServiceConfig;

    public void loadCsv(String filePath, int chunkSize) throws IOException, CsvValidationException {
        List<EntityDto> chunk = new ArrayList<>(chunkSize);
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             CSVReader csvReader = new CSVReaderBuilder(br).withCSVParser(parser).build();){
             ExecutorService executorService = executorServiceConfig.getThreadPoolExecutor() ;

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                if (line.length < 4) {
                    log.error("Ошибка формата строки {}", Arrays.toString(line));
                    continue;
                }
                try {
                    EntityDto dto = new EntityDto();
                    dto.setCode(line[0]);
                    dto.setName(line[1]);
                    dto.setDescription(line[2]);
                    dto.setStartDate(LocalDateTime.parse(line[3], DATE_FORMATTER));
                    dto.setModifiedOn(LocalDateTime.now());
                    chunk.add(dto);
                } catch (Exception e) {
                    log.error("Ошибка парсинга : {}",e.getLocalizedMessage());
                    continue;
                }
                if (chunk.size() >= chunkSize) {
                    List<EntityDto> chunkToProcess = new ArrayList<>(chunk);
                    executorService.submit(() ->entityLoaderService.loadEntityList(chunkToProcess));
                    chunk.clear();
                }
            }
            if (!chunk.isEmpty()) {
                List<EntityDto> chunkToProcess = new ArrayList<>(chunk);
                executorService.submit(() ->entityLoaderService.loadEntityList(chunkToProcess));
            }
        }
    }
}
