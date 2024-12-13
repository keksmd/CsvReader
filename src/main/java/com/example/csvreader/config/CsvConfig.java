package com.example.csvreader.config;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CsvConfig {
    @Bean
    public CSVParser parser() {
        return new CSVParserBuilder()
                .withSeparator(',')
                .withQuoteChar('"')
                .withEscapeChar('\\')
                .withIgnoreQuotations(false)
                .build();
    }
}
